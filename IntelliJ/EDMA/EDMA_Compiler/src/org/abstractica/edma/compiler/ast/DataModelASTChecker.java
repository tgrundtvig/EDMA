package org.abstractica.edma.compiler.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.abstractica.edma.compiler.BadDefaultValueException;
import org.abstractica.edma.compiler.BadNumberRangeException;
import org.abstractica.edma.compiler.UnknownReferenceException;
import org.abstractica.edma.compiler.ast.AValueDomainNode.Type;
import org.abstractica.edma.compiler.ast.IndexNode.IndexType;
import org.abstractica.edma.compiler.ast.ValueDomainSingle.VdSingleType;

public class DataModelASTChecker
{
	private HashMap<String, AValueDomainNode> vdNodesMap;
	private Pattern errorCodePattern = Pattern.compile("[\\w _]+");

	public void checkDataModelAst(final WorldNode world) throws BadDefaultValueException,
														BadNumberRangeException,
														Exception
	{
		final ArrayList<AValueDomainNode> vdNodes = world.getValueDomains();
		vdNodesMap = new HashMap<String, AValueDomainNode>();
		Set<String> valueDomainNames = new HashSet<String>();
		for(AValueDomainNode vdNode : vdNodes)
		{
			vdNodesMap.put(vdNode.getName(), vdNode);
			valueDomainNames.add(vdNode.getName());
		}

		// Check value domain references in value domains
		checkValueDomainReferences(vdNodes, valueDomainNames);

		// check numeral constraints of value domains
		checkNumeralInvariants(world);

		for(DataModelNode dm : world.getDataModels())
		{
			Set<String> localValueDomainNames = getLocalValueDomainNames(dm);
			
			checkLocalValueDomainReferences(dm, valueDomainNames, localValueDomainNames);
			
			checkExtensions(dm);
			
			// check value domain references in kind attributes
			checkKindAttributeReferences(dm, valueDomainNames, localValueDomainNames);

			// check kind references in relations
			checkRelationReferences(dm);

			// check value domain references in actions and views
			checkActionAndViewReferences(	dm,
											valueDomainNames,
											localValueDomainNames,
											true); // actions
			checkActionAndViewReferences(	dm,
											valueDomainNames,
											localValueDomainNames,
											false); // views
			
			checkIndexReferences(dm); // checks that no index refers to an attribute that has not been defined in the kind
			checkIndexTypes(dm); // checks that no kind has both a COMPARE and EQUAL index
		}

		// Check value domain loops
		checkValueDomainLoops(vdNodes);

	}
	
	private void checkExtensions(DataModelNode dm) throws Exception
	{
		HashSet<String> set = new HashSet<String>();
		
		for (KindNode k : dm.getKinds())
		{
			checkExtensionsRecursively(dm, k, set);
		}
	}
	
	private void checkExtensionsRecursively(DataModelNode dm, KindNode k, Set<String> baseKinds) throws Exception
	{
		if (k.getBaseType() != null)
		{
			if (baseKinds.contains(k.getName()))
			{
				String exceptionText = "File '"+k.getFileName()+"' line "
				        +k.getLine()+": Circular extension detected: ";
				boolean first = true;
				for (String s : baseKinds)
				{
					if (first)
						first = false;
					else
						exceptionText += ", ";
					exceptionText += s;
				}
				exceptionText += " extend each other circularly.";
				throw new Exception(exceptionText);
			}
			baseKinds.add(k.getName());
			checkExtensionsRecursively(dm, dm.getKind(k.getBaseType()), baseKinds);
			baseKinds.remove(k.getName());
		}
	}
	
	private void checkIndexTypes(DataModelNode dm) throws Exception 
	{
		for (KindNode k : dm.getKinds())
		{
			ArrayList<IndexNode> indexes = k.getIndexList();
			boolean throwException = false;
			Set<Set<String>> equalsIndexes = new HashSet<>();
			Set<Set<String>> compareIndexes = new HashSet<>();
			int line=0;
			for (IndexNode i : indexes)
			{
				if (i.getType() == IndexType.Compare)
				{
					Set<String> compareIndex = new HashSet<String>();
					compareIndex.addAll(i.getAttributeList());
					compareIndexes.add(compareIndex);
					if (equalsIndexes.contains(compareIndex))
					{
						throwException = true;
						line = i.getLine();
						break;
					}
				}
				else if (i.getType() == IndexType.Equals)
				{
					Set<String> equalsIndex = new HashSet<String>();
					equalsIndex.addAll(i.getAttributeList());
					equalsIndexes.add(equalsIndex);
					if (compareIndexes.contains(equalsIndex))
					{
						throwException = true;
						line = i.getLine();
						break;
					}
				}
			}
			if (throwException)
			{
				throw new Exception("File '"+k.getFileName()+"' line "
				        +line+": Kind cannot have a Compare index and an " 
						+"Equals index with the same attributes, as the " 
				        +"Equals index then is surplus.");
			}
		}
	}
	
	private void checkIndexReferences(DataModelNode dm) throws UnknownReferenceException
	{
		for (KindNode k : dm.getKinds())
		{
			Set<String > attributeNames = new HashSet<String>();
			for (AttributeNode a : k.getAttributes())
			{
				attributeNames.add(a.getName());
			}
			
			for (IndexNode i : k.getIndexList())
			{
				for (String a : i.getAttributeList())
				{
					if (!attributeNames.contains(a))
					{
						throw new UnknownReferenceException("File '"+i.getFileName()
						    +"', line: " + i.getLine() +": The index specifies an unknown attribute '"+a+"'.");
					}
				}
			}
		}
		for (RelationNode rn : dm.getRelations())
		{
			String kindA = rn.getKindA();
			String kindB = rn.getKindB();
			String roleA = rn.getRoleA();
			String roleB = rn.getRoleB();
			for (IndexNode i : rn.getIndexes())
			{
				KindNode kn;
				if (i.getKind().equals(kindA) &&
				    i.getRole().equals(roleA))
				{
					kn = dm.getKind(kindA);
				}
				else if (i.getKind().equals(kindB) &&
						 i.getRole().equals(roleB))
				{
					kn = dm.getKind(kindB);
				} 
				else
				{
					throw new UnknownReferenceException("File '"+i.getFileName()
					            					    +"', line: " + i.getLine()+": The index is defined on a kind or role '"
					            					    +i.getKind()+(i.getRole().equals("")?"":" : "+i.getRole())
					            					    +"', which is not in the relationship.");
				}
				
				if (kn == null)
				{
					throw new UnknownReferenceException("File '"+i.getFileName()
						+"', line: "+i.getLine()+": The index is defined on an unknown kind '"+i.getKind()+'.');
				}
				for (String s : i.getAttributeList())
				{
					if (kn.getAttribute(s)==null)
					{
						throw new UnknownReferenceException("File '"+i.getFileName()
						    +"', line: " + i.getLine()+": The index is defined on an unknown attribute '"
						    +s+"'.");
					}
				}
			}
		}
	}
	
	private void checkLocalValueDomainReferences(DataModelNode dm,
	                                             Set<String> valueDomainNames,
	                                             Set<String> localValueDomainNames) throws UnknownReferenceException
	{
		Set<String> allValueDomainNames = new HashSet<String>();
		allValueDomainNames.addAll(valueDomainNames);
		allValueDomainNames.addAll(localValueDomainNames);
		checkValueDomainReferences(dm.getValueDomains(),allValueDomainNames);
	}
	
	private void checkNumeralInvariants(WorldNode world) throws BadNumberRangeException
	{
		for(AValueDomainNode vdNode : world.getValueDomains())
		{
			if (vdNode.getType() != Type.Single)
				return;
			ValueDomainSingle vdSingle = (ValueDomainSingle) vdNode;
			VdSingleType basicType = vdSingle.getBasicType();
			boolean minGreaterThanMax = false;
			if(basicType == VdSingleType.Integer)
			{
				Integer min = vdSingle.getMinValueAsInt();
				Integer max = vdSingle.getMaxValueAsInt();
				minGreaterThanMax = (min!=null && max!=null && min > max);
			}
			else if(basicType == VdSingleType.Float)
			{
				Float min = vdSingle.getMinValueAsFloat();
				Float max = vdSingle.getMaxValueAsFloat();
				minGreaterThanMax = (min!=null && max!=null && min > max);
			}
			else if(basicType == VdSingleType.Double)
			{
				Double min = vdSingle.getMinValueAsDouble();
				Double max = vdSingle.getMaxValueAsDouble();
				minGreaterThanMax = (min!=null && max!=null && min > max);
			}
			else if (basicType == VdSingleType.Long)
			{
				Long min = vdSingle.getMinValueAsLong();
				Long max = vdSingle.getMaxValueAsLong();
				minGreaterThanMax = (min!=null && max!=null && min > max);
			}
			else if(basicType == VdSingleType.String)
			{
				Integer min = vdSingle.getMinValueAsInt();
				Integer max = vdSingle.getMaxValueAsInt();
				if(min!=null && min < 0) throw new BadNumberRangeException("File '"
						+vdSingle.getFileName()+"', line "
						+ vdNode.getLine()
						+ ": The ValueDomain '"
						+ vdNode.getName()
						+ "' must have a minimum specified length greater than or equal to 0.");
				minGreaterThanMax = (min!=null && max!=null && min > max);
			}

			if(minGreaterThanMax){ throw new BadNumberRangeException("File '"
					+vdNode.getFileName()+"', line "
					+ vdNode.getLine() + ": The ValueDomain '"
					+ vdNode.getName()
					+ "'s minimum value is larger than its maximum value."); }
		}
	}

	private Set<String> getLocalValueDomainNames(DataModelNode dm)
	{
		Set<String> localVds = new HashSet<String>();
		for(KindNode k : dm.getKinds())
		{
			localVds.add(k.getName());
			localVds.add(k.getName()+"ID");
			localVds.add(k.getName()+"List");
		}
		for(SingletonNode s : dm.getSingletons())
		{
			localVds.add(s.getName());
		}
		for (AValueDomainNode vd : dm.getValueDomains())
		{
			localVds.add(vd.getName());
		}
		return localVds;
	}

	private void checkActionAndViewReferences(	DataModelNode dm,
												Set<String> valueDomainNames,
												Set<String> localValueDomainNames,
												boolean action) throws UnknownReferenceException, Exception
	{
		final ArrayList<ActionViewNode> avNodes;
		if(action)
		{
			avNodes = dm.getActions();
		}
		else
		{
			avNodes = dm.getViews();
		}
		ArrayList<NameAndTypeNode> params = new ArrayList<NameAndTypeNode>();
		
		for(ActionViewNode avNode : avNodes)
		{
			if (avNode.getInput()!=null)
				params.addAll(avNode.getInput());
			if (avNode.getOutput()!=null)
				params.addAll(avNode.getOutput());
			
			if (avNode.getErrorCodes()!=null)
			{
				Set<Integer> errorCodes = new HashSet<Integer>();
				Set<String> errorMessages = new HashSet<String>();
				for (ErrorCode c : avNode.getErrorCodes())
				{
					if (c.getNumber() < 1)
					{
						throw new Exception("File: '" + c.getFileName()+"', line "
							+c.getLine()+": The error code must be greater than 0!");
					}
					if (errorCodes.contains(c.getNumber()))
					{
						throw new Exception("File '"
								+c.getFileName()+"', line "
								+c.getLine() + ": The error code " + c.getNumber()
								+" has already been defined!");
					}

					if (errorMessages.contains(c.getMessage()))
					{
						throw new Exception("File '"
								+c.getFileName()+"', line "
								+c.getLine() + ": The error message '" + c.getMessage()
								+"' has already been defined!");
					}
					
					if (!errorCodePattern.matcher(c.getMessage()).matches())
					{
						throw new Exception("File '"
								+c.getFileName()+"', line "
								+c.getLine() + ": The given error message contains non-word characters.");
					}
					if (c.getMessage().contains(".") ||
						c.getMessage().contains(",") ||
						c.getMessage().contains("'") ||
						c.getMessage().contains("\""))
						throw new Exception("File '"
								+c.getFileName()+"', line "
								+c.getLine() + ": The error code "+c.getNumber()
								+" contains illegal characters. (None of these are allowed: ,.'\"{}()[]");
					errorCodes.add(c.getNumber());
					errorMessages.add(c.getMessage());
				}
			}
			
			for (NameAndTypeNode nameAndType : params)
			{
				String valueDomain = nameAndType.getIdentifier();
				if(valueDomain != null && !valueDomainNames.contains(valueDomain)
						&& !localValueDomainNames.contains(valueDomain))
				{
					throw new UnknownReferenceException("File '"
							+nameAndType.getFileName()+"', line "
							+ nameAndType.getLine() + ": '" + nameAndType.getName()
							+ "' references an unknown Value Domain '" + valueDomain
							+ "'.");
				}
			}
		}
	}

	private void checkKindAttributeReferences(	DataModelNode dm,
												Set<String> valueDomainNames,
												Set<String> localValueDomainNames) throws Exception
	{
		ArrayList<IAttributeContainer> kindsAndSingletons = new ArrayList<IAttributeContainer>();
		kindsAndSingletons.addAll(dm.getKinds());
		kindsAndSingletons.addAll(dm.getSingletons());
		
		for(IAttributeContainer kindOrSgt : kindsAndSingletons)
		{
			Set<String> attributeNames = new HashSet<String>();
			for(AttributeNode attribute : kindOrSgt.getAttributes())
			{
				if (attributeNames.contains(attribute.getName()))
				{
					throw new Exception("File '"+attribute.getFileName()
					                    +"', line " + attribute.getLine()
					                    +": Attribute name '" + attribute.getName()
					                    +"' is already in use");
					
				}
				attributeNames.add(attribute.getName());
				
				if(!valueDomainNames.contains(attribute.getValueDomain()) &&
				   !localValueDomainNames.contains(attribute.getValueDomain())){ throw new UnknownReferenceException("File '"
						+attribute.getFileName()+"', line "
						+ attribute.getLine()
						+ ": Attribute '"
						+ attribute.getName()
						+ "' is of unknown ValueDomain '"
						+ attribute.getValueDomain() + "'."); }
			}
		}

	}

	private void checkRelationReferences(DataModelNode dm) throws UnknownReferenceException
	{
		Set<String> kindNames = new HashSet<String>();
		for(KindNode kind : dm.getKinds())
		{
			kindNames.add(kind.getName());
		}
		boolean error = false;
		String unknownKind = "";
		for(RelationNode relation : dm.getRelations())
		{
			if(!kindNames.contains(relation.getKindA()))
			{
				error = true;
				unknownKind = relation.getKindA();
			}
			else if(!kindNames.contains(relation.getKindB()))
			{
				error = true;
				unknownKind = relation.getKindB();
			}
			if(error){ throw new UnknownReferenceException("File '"
					+relation.getFileName()+"', line "
					+ relation.getLine() + ": Unknown reference to kind '"
					+ unknownKind + "'."); }
		}
	}

	private void checkValueDomainReferences(ArrayList<AValueDomainNode> vdNodes,
											Set<String> valueDomainNames) throws UnknownReferenceException
	{
		for(AValueDomainNode vdNode : vdNodes)
		{
			switch(vdNode.getType())
			{
				case List:
					/*
					 * if (((ValueDomainList)vdNode).getMinSize() < 0) throw new
					 * BadNumberRangeException("Line " + vdNode.getLine() +
					 * ": The ValueDomain '" + vdNode.getName() +
					 * "' must have a minimum specified length greater than or equal to 0."
					 * );
					 */
					String listType = ((ValueDomainList) vdNode).getListType();
					if(!valueDomainNames.contains(listType)) throw getUnknownValueDomainReferenceException(	vdNode.getFileName(),
					                                                                                       	vdNode.getLine(),
																											listType);
					break;
				case Struct:
					ValueDomainStruct vdStructNode = (ValueDomainStruct) vdNode;
					for(int i = 0; i < vdStructNode.getNumElements(); i++)
					{
						String fieldType = vdStructNode.getElementType(i);
						if(!valueDomainNames.contains(fieldType)) throw getUnknownValueDomainReferenceException(vdStructNode.getFileName(),
						                                                                                        vdStructNode.getElementLineNumber(i),
																												fieldType);
					}
					break;
				case Map:
					ValueDomainMap vdMapNode = (ValueDomainMap) vdNode;
					if(!valueDomainNames.contains(vdMapNode.getKey())) throw getUnknownValueDomainReferenceException(	vdNode.getFileName(),
					                                                                                                 	vdNode.getLine(),
																														vdMapNode.getKey());
					if(!valueDomainNames.contains(vdMapNode.getKey())) throw getUnknownValueDomainReferenceException(	vdNode.getFileName(),
					                                                                                                 	vdNode.getLine(),
																														vdMapNode.getValue());
					break;
				case OneOf:
					ValueDomainOneOf vdOneOfNode = (ValueDomainOneOf) vdNode;
					for(int i = 0; i < vdOneOfNode.getNumElements(); i++)
					{
						if(!valueDomainNames.contains(vdOneOfNode.getTypeName(i))) throw getUnknownValueDomainReferenceException(vdOneOfNode.getFileName(),
						                                                                                                         vdOneOfNode.getTypeLine(i),
																																 vdOneOfNode.getTypeName(i));
					}
					break;
			}
		}
	}

	private UnknownReferenceException getUnknownValueDomainReferenceException(	String fileName,
	                                                                          	int line,
																				String type)
	{
		return new UnknownReferenceException("File '"+fileName+"', line " + line
				+ ": Unknown reference to ValueDomain type '" + type + "'.");
	}

	private void checkValueDomainLoops(final ArrayList<AValueDomainNode> vdNodes) throws Exception
	{
		HashSet<String> impossibleSet = new HashSet<String>();
		for(AValueDomainNode vd : vdNodes)
		{
			if(!isPossible(vd, impossibleSet)){ throw new Exception("No possible values can be created for ValueDomain "
					+ vd.getName()); }
		}
	}

	private boolean isPossible(AValueDomainNode vd, Set<String> impossibleSet)
	{
		if(impossibleSet.contains(vd.getName())) return false;
		switch(vd.getType())
		{
			case Single:
			case Enum:
			case Map:
				return true;

			case List:
				return isPossibleList((ValueDomainList) vd, impossibleSet);
			case OneOf:
				return isPossibleOneOf((ValueDomainOneOf) vd, impossibleSet);
			case Struct:
				return isPossibleStruct((ValueDomainStruct) vd, impossibleSet);
		}
		return true;
	}

	private boolean isPossibleList(ValueDomainList vd, Set<String> impossibleSet)
	{
		if(vd.getMinSize() == null || vd.getMinSize().equals("0")) return true;
		
		if(impossibleSet.contains(vd.getName())) return false;
		impossibleSet.add(vd.getName());
		AValueDomainNode n = vdNodesMap.get(vd.getListType());
		boolean res = isPossible(n, impossibleSet);
		impossibleSet.remove(vd.getName());
		return res;
	}

	private boolean isPossibleOneOf(ValueDomainOneOf vd,
									Set<String> impossibleSet)
	{
		impossibleSet.add(vd.getName());
		for(String s : vd.getTypeNames())
		{
			AValueDomainNode vdElem = vdNodesMap.get(s);
			if(isPossible(vdElem, impossibleSet))
			{
				impossibleSet.remove(vd.getName());
				return true;
			}
		}
		return false;
	}

	private boolean isPossibleStruct(	ValueDomainStruct vd,
										Set<String> impossibleSet)
	{
		impossibleSet.add(vd.getName());
		for(int i = 0; i < vd.getNumElements(); i++)
		{
			boolean optional = vd.getElementOptional(i);
			if(!optional)
			{
				if(!isPossible(	vdNodesMap.get(vd.getElementType(i)),
								impossibleSet))
				{
					impossibleSet.remove(vd.getName());
					return false;
				}
			}
		}
		impossibleSet.remove(vd.getName());
		return true;
	}

}
