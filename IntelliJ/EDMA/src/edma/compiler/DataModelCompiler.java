package edma.compiler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import edma.compiler.ast.AValueDomainNode;
import edma.compiler.ast.AValueDomainNode.Type;
import edma.compiler.ast.ActionViewNode;
import edma.compiler.ast.AttributeNode;
import edma.compiler.ast.ConstraintNode;
import edma.compiler.ast.DataModelASTChecker;
import edma.compiler.ast.DataModelNode;
import edma.compiler.ast.ErrorCode;
import edma.compiler.ast.IndexNode;
import edma.compiler.ast.KindNode;
import edma.compiler.ast.NameAndTypeNode;
import edma.compiler.ast.RelationNode;
import edma.compiler.ast.SingletonNode;
import edma.compiler.ast.ValueDomainEnum;
import edma.compiler.ast.ValueDomainList;
import edma.compiler.ast.ValueDomainMap;
import edma.compiler.ast.ValueDomainOneOf;
import edma.compiler.ast.ValueDomainSingle;
import edma.compiler.ast.ValueDomainSingle.VdSingleType;
import edma.compiler.ast.ValueDomainStruct;
import edma.compiler.ast.WorldNode;
import edma.metamodel.IMetaEnvironment;
import edma.metamodel.IMetaIndex;
import edma.metamodel.IMetaRelation.RelationType;
import edma.metamodel.impl.MetaAttribute;
import edma.metamodel.impl.MetaDataModel;
import edma.metamodel.impl.MetaEnvironment;
import edma.metamodel.impl.MetaIndex;
import edma.metamodel.impl.MetaKind;
import edma.metamodel.impl.MetaMethod;
import edma.metamodel.impl.MetaRelation;
import edma.metamodel.impl.MetaSingleton;
import edma.metamodel.impl.ValueDomainBuilder;
import edma.metamodel.impl.ValueDomainBuilder.Field;
import edma.valuedomains.impl.Constraint;


public class DataModelCompiler
{
	/**
	 * Compiles the files in the given directory, with the given package name, and a search depth of 10 directories.
	 * @param directory The directory containing the .edma files to compile
	 * @param packageName The resulting package name
	 * @return IMetaEnvironment
	 * @throws IOException If no files are found in the given directory, or if there are file access problems.
	 * @throws ParseException If a syntax errors exist in any of the given .edma files.
	 */
	public IMetaEnvironment compile(String directory, String packageName) throws IOException, ParseException
	{
		return compile(directory, packageName, 10);
	}

	/**
	 * Compiles the files in the given directory, with the given package name, and a search depth of 10 directories.
	 * @param directory The directory containing the .edma files to compile
	 * @param packageName The resulting package name
	 * @param searchDepth The search depth when searching recursively on the file system for .edma files.
	 * @return IMetaEnvironment
	 * @throws IOException If no files are found in the given directory, or if there are file access problems.
	 * @throws ParseException If a syntax errors exist in any of the given .edma files.
	 */
	public IMetaEnvironment compile(String directory, String packageName, int searchDepth) throws IOException, ParseException 
	{
		File f;
		f = new File(directory);
		if(!f.exists())
		{
			System.err.println("File or directory does not exist!");
			return null;
		}
		
		LinkedList<File> files = getFilesFromDirRecursive(new File(directory), new FileFilterEdma(), searchDepth);
		
		ArrayList<WorldNode> worldNodes = new ArrayList<WorldNode>();
		for (File file : files)
		{
			WorldNode world = getWorldFromFile(file); 
			worldNodes.add(world);
		}
		
		WorldNode fullWorld = mergeWorldNodes(worldNodes);
		
		if (fullWorld == null)
		{
			throw new IOException("No files found in directory: "+directory);
		}
		
		return compile(fullWorld, packageName);
	}
	
	private LinkedList<File> getFilesFromDirRecursive(File f, FileFilter filter, int depth)
	{
		LinkedList<File> list = new LinkedList<File>();
		if (depth == 0) return list;
		//File[] files = f.listFiles(new FileFilterEdma());
		String[] files = f.list();
		for (String str : files)
		{
			File tmp = new File(f.getPath()+File.separator+str);
			if (filter.accept(tmp))
			{
				list.add(tmp);
			}
			else if (tmp.isDirectory())
			{
				list.addAll(getFilesFromDirRecursive(tmp, filter, depth-1));
			}
		}
		return list;
	}
	
	private WorldNode mergeWorldNodes(ArrayList<WorldNode> worldNodes)
	{
		if (worldNodes.size() == 0)
			return null;
		if (worldNodes.size() == 1)
			return worldNodes.get(0);
		
		WorldNode mainWorld = worldNodes.get(0);
		for (int i = 1; i < worldNodes.size(); i++)
		{
			WorldNode w = worldNodes.get(i);
			for (AValueDomainNode vd : w.getValueDomains())
			{
				mainWorld.addValueDomain(vd);
			}
		}
		
		for (int i = 1; i < worldNodes.size(); i++)
		{
			WorldNode wn = worldNodes.get(i);
			ArrayList<DataModelNode> dms = wn.getDataModels();
			for (int j = 0; j < dms.size(); j++)
			{
				DataModelNode dmn = dms.get(j);
				String dmName = dmn.getName();
				DataModelNode correspondingDmNode = mainWorld.getDataModelByName(dmName);
				
				if (correspondingDmNode == null) // If there is no such dm in the main world, add it 
				{
					mainWorld.addDataModel(dmn);
				}
				else // otherwise, add all the stuff from the newly found dm, to the corresponding dm in the main world
				{
					for (KindNode k : dmn.getKinds())
						correspondingDmNode.addKind(k);
					for (SingletonNode s : dmn.getSingletons())
						correspondingDmNode.addSingleton(s);
					for (RelationNode r : dmn.getRelations())
						correspondingDmNode.addRelation(r);
					for (ActionViewNode a : dmn.getActions())
						correspondingDmNode.addAction(a);
					for (ActionViewNode v : dmn.getViews())
						correspondingDmNode.addView(v);
					for (ConstraintNode c : dmn.getConstraints())
						correspondingDmNode.addConstraint(c);
					for (AValueDomainNode vn : dmn.getValueDomains())
						correspondingDmNode.addValueDomain(vn);
				}
			}
		}
		return mainWorld;
	}
	
	private WorldNode getWorldFromFile(File f) throws IOException, ParseException
	{
		//return getWorldFromString(getFileContents(f), f.getName());
		String s = getFileContents(f);
		ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
		DataModelParser parser = new DataModelParser(is);
		WorldNode world = parser.newWorld(f.getName());
		parser.Input(world);
		return world;
	}
	
	private String getFileContents(File f) throws IOException
	{
		StringBuilder str = new StringBuilder();
		BufferedReader r = new BufferedReader(new FileReader(f));
		String line;
		do
		{
			line = r.readLine();
			if (line != null)
				str.append(line+"\n");
		} while (line!=null);
		r.close();
		return str.toString();
	}
	
	private MetaEnvironment compile(WorldNode world, String packageName)
	{
		ArrayList<MetaDataModel> models = new ArrayList<MetaDataModel>();
		try
		{
			DataModelASTChecker checker = new DataModelASTChecker();
			checker.checkDataModelAst(world);

			ValueDomainBuilder vdBuilder = new ValueDomainBuilder();

			for(AValueDomainNode n : world.getValueDomains())
			{
				addValueDomainNode(n, vdBuilder);
			}

			MetaEnvironment environment = new MetaEnvironment(packageName);
			
			ArrayList<DataModelNode> dataModels = world.getDataModels();
			for(DataModelNode dmNode : dataModels)
			{
				MetaDataModel model = new MetaDataModel(dmNode.getName(),
														packageName);
				addSingletons(dmNode, model);
				addKinds(dmNode, model);
				addRelations(dmNode, model);

				addConstraints(dmNode, model);
				
				addLocalValueDomains(dmNode, vdBuilder);

				addActions(dmNode, model);
				addViews(dmNode, model);

				models.add(model);
				environment.addMetaDataModel(model);
			}
			vdBuilder.buildWithEnvironment(environment);

			return environment;
		}
		catch(ParseException e)
		{
			int line = e.currentToken.beginLine;
			System.err.println("Syntax error at line " + line + ": " + "... "
					+ e.currentToken.image);
			e.printStackTrace();
		}
		catch(UnknownReferenceException e)
		{
			e.printStackTrace();
		}
		catch(BadNumberRangeException e)
		{
			e.printStackTrace();
		}
		catch(BadDefaultValueException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private void addConstraints(DataModelNode dmNode, MetaDataModel model)
	{
		for (ConstraintNode cn : dmNode.getConstraints())
		{
			Constraint c = new Constraint(cn.getName(),cn.getDescription());
			model.addConstraint(c);
			
		}
	}

	private void addLocalValueDomains(DataModelNode dmNode, ValueDomainBuilder vdBuilder)
	{
		for (AValueDomainNode vdNode : dmNode.getValueDomains())
		{
			addValueDomainNode(vdNode, vdBuilder);
		}
	}
	
	private void addActions(DataModelNode dmNode, MetaDataModel metaDataModel)
	{
		addMethod(dmNode, metaDataModel, true);
	}
	
	private void addViews(DataModelNode dmNode, MetaDataModel metaDataModel)
	{
		addMethod(dmNode, metaDataModel, false);
	}
	
	private void addMethod(DataModelNode dmNode, MetaDataModel metaDataModel, boolean action)
	{
		for(ActionViewNode operation : (action?dmNode.getActions() : dmNode.getViews()))
		{
			MetaMethod method = new MetaMethod(	operation.getName(),
												operation.getDescription());
			if (operation.getInput()!=null)
			{
				for (NameAndTypeNode param : operation.getInput())
				{
					method.addInputParameter(param.getName(), param.getIdentifier(), param.canBeNull());
				}				
			}
			if (operation.getOutput()!=null)
			{
				for (NameAndTypeNode param : operation.getOutput())
				{
					method.addOutputParameter(param.getName(), param.getIdentifier(), param.canBeNull());
				}
			}
			if (operation.getErrorCodes()!=null)
			{
				for (ErrorCode ec : operation.getErrorCodes())
				{
					method.addErrorCode(ec.getNumber(), ec.getMessage());
				}
			}
			if (action)
				metaDataModel.addAction(method);
			else
				metaDataModel.addView(method);
		}
	}

	private void addRelations(DataModelNode dmNode, MetaDataModel metaDataModel)
	{
		for(RelationNode relation : dmNode.getRelations())
		{
			String kindA = relation.getKindA(), 
				   roleA = relation.getRoleA(), 
				   kindB = relation.getKindB(), 
				   roleB = relation.getRoleB(), 
				   name = relation.getName();
			MetaKind metaKindA = (MetaKind) metaDataModel.getKind(kindA), 
					 metaKindB = (MetaKind) metaDataModel.getKind(kindB);
			boolean same = (kindA.equals(kindB) && roleA.equals(roleB));
			RelationType type = null;
			switch(relation.getRelationType())
			{
				case 0:
					type = (same ? RelationType.OneToOneSame
							: RelationType.OneToOne);
					break;
				case 1:
					type = RelationType.ManyToOne;
					break;
				case 2:
					type = (same ? RelationType.ManyToManySame
							: RelationType.ManyToMany);
					break;
			}
			MetaRelation metaRelation = new MetaRelation(	name,
															metaKindA,
															roleA,
															metaKindB,
															roleB,
															type);
			
			for (ConstraintNode cn : relation.getConstraints())
			{
				Constraint constraint = new Constraint(cn.getName(),cn.getDescription());
				metaRelation.addConstraint(constraint);
			}
			
			for (IndexNode in : relation.getIndexes())
			{
				MetaIndex metaIndex = new MetaIndex(getMetaIndexTypeFromNode(in));
				
				boolean indexOnA = in.getKind().equals(kindA) && in.getRole().equals(roleA);
				boolean indexOnB = in.getKind().equals(kindB) && in.getRole().equals(roleB);
				
				if (indexOnA)
				{
					metaRelation.addIndexOnA(metaIndex);
					metaIndex.setKind(metaKindA);
				}
				else if (indexOnB)
				{
					metaRelation.addIndexOnB(metaIndex);
					metaIndex.setKind(metaKindB);
				}
				else
				{
					throw new RuntimeException("This should never happen. Relation index error. Kind: '"
												+in.getKind()+"', role: '"+in.getRole()+"'.");
				}
				
				for (String att : in.getAttributeList())
				{
					if (type == RelationType.ManyToOne || 
							type == RelationType.ManyToMany || 
							type == RelationType.ManyToManySame)
					{
						if (indexOnA)
							metaIndex.addAttribute(metaKindA.getAttribute(att));
						else
							metaIndex.addAttribute(metaKindB.getAttribute(att));
					}
				}
			}
			
			metaDataModel.addRelation(metaRelation);
		}
	}

	private void addSingletons(	DataModelNode dmNode,
								MetaDataModel metaDataModel)
	{
		for(SingletonNode singleton : dmNode.getSingletons())
		{
			MetaSingleton metaSingleton = new MetaSingleton(singleton.getName(),
															singleton.getValueDomainName());
			for(AttributeNode attribute : singleton.getAttributes())
			{
				getMetaAttributeFromNode(attribute, metaSingleton);
			}
			metaDataModel.addSingleton(metaSingleton);
		}
	}

	private void addKinds(	DataModelNode dmNode,
							MetaDataModel model)
	{
		// First pass - add all
		Map<String, MetaKind> kindMap = new HashMap<String, MetaKind>();
		Map<String, MetaAttribute> attributeMap = new HashMap<String, MetaAttribute>();
		for(KindNode kind : dmNode.getKinds())
		{
			MetaKind metaKind = new MetaKind(	kind.getName(),
												kind.getValueDomainName(),
												null);
			for(AttributeNode attribute : kind.getAttributes())
			{
				MetaAttribute metaAttribute = getMetaAttributeFromNode(attribute, metaKind);
				attributeMap.put(kind.getName()+"_"+metaAttribute.getName(), metaAttribute);
			}
			kindMap.put(kind.getName(), metaKind);
			model.addKind(metaKind);
			
			for (ConstraintNode cn : kind.getConstraints())
			{
				Constraint c = new Constraint(cn.getName(), cn.getDescription());
				metaKind.addConstraint(c);
			}
		}
		// Second pass - add bases.
		for(KindNode kind : dmNode.getKinds())
		{
			MetaKind metaKind = kindMap.get(kind.getName());
			if(kind.getBaseType() != null && !kind.getBaseType().isEmpty())
			{
				MetaKind base = kindMap.get(kind.getBaseType());
				metaKind.setBaseKind(base);
			}

			for(IndexNode idx : kind.getIndexList())
			{
				MetaIndex metaIndex = new MetaIndex(getMetaIndexTypeFromNode(idx));
				metaKind.addIndex(metaIndex);
				metaIndex.setKind(metaKind);
				for(String idxAtt : idx.getAttributeList())
				{
					metaIndex.addAttribute(attributeMap.get(kind.getName()+"_"+idxAtt));
				}
			}
		}
	}
	
	/**
	 * Returns a MetaIndex (MetaModel) from an IndexNode (AST), but without MetaAttributes inside.
	 * @param idx
	 * @return
	 */
	private IMetaIndex.IndexType getMetaIndexTypeFromNode(IndexNode idx)
	{
		switch (idx.getType())
		{
			case Compare:
				return IMetaIndex.IndexType.Compare;
			case Equals:
				return IMetaIndex.IndexType.Equal;
			case Unique:
				return MetaIndex.IndexType.Unique;
			default:
				return null;
		}
	}
	
	private MetaAttribute getMetaAttributeFromNode(AttributeNode att, MetaSingleton metaSingleton)
	{
		return new MetaAttribute(	metaSingleton,
		                         	att.getName(),
									att.getValueDomain(),
									att.isOptional(),
									att.isModifiable(),
									att.getDefaultValue());
	}

	private MetaAttribute getMetaAttributeFromNode(AttributeNode att, MetaKind metaKind)
	{
		return new MetaAttribute(	metaKind,
		                         	att.getName(),
									att.getValueDomain(),
									att.isOptional(),
									att.isModifiable(),
									att.getDefaultValue());
	}

	private void addValueDomainNode(AValueDomainNode vdNode,
									ValueDomainBuilder vdBuilder)
	{
		if(vdNode.getType() == Type.Enum)
		{
			addEnumValueDomain((ValueDomainEnum) vdNode, vdBuilder);
		}
		else if(vdNode.getType() == Type.Struct)
		{
			addStructValueDomain((ValueDomainStruct) vdNode, vdBuilder);
		}
		else if(vdNode.getType() == Type.List)
		{
			addListValueDomain((ValueDomainList) vdNode, vdBuilder);
		}
		else if(vdNode.getType() == Type.Single)
		{
			addSingleValueDomain((ValueDomainSingle) vdNode, vdBuilder);
		}
		else if(vdNode.getType() == Type.Map)
		{
			addMapValueDomain((ValueDomainMap) vdNode, vdBuilder);
		}
		else if(vdNode.getType() == Type.OneOf)
		{
			addOneOfValueDomain((ValueDomainOneOf) vdNode, vdBuilder);
		}
	}

	private ArrayList<Constraint> getConstraintsFromValueDomainNode(AValueDomainNode vdNode)
	{
		ArrayList<Constraint> constraints = new ArrayList<Constraint>();
		for(ConstraintNode conNode : vdNode.getConstraints())
		{
			constraints.add(new Constraint(	conNode.getName(),
											conNode.getDescription()));
		}
		return constraints;
	}

	private void addEnumValueDomain(ValueDomainEnum vdNode,
									ValueDomainBuilder vdBuilder)
	{
		ArrayList<String> enumValues = new ArrayList<String>();

		for(String e : vdNode.getElements())
		{
			enumValues.add(e);
		}

		ArrayList<Constraint> constraints = getConstraintsFromValueDomainNode(vdNode);
		vdBuilder.newEnumDomain(vdNode.getName(),
		                        vdNode.getScope(), 
		                        enumValues, 
		                        constraints,
		                        false);
	}

	private void addStructValueDomain(	ValueDomainStruct vdNode,
										ValueDomainBuilder vdBuilder)
	{
		ArrayList<Field> fields = new ArrayList<Field>();
		for(int i = 0; i < vdNode.getNumElements(); i++)
		{
			fields.add(vdBuilder.newStructField(vdNode.getElementName(i),
												vdNode.getElementType(i),
												vdNode.getElementOptional(i)));
		}
		ArrayList<Constraint> constraints = getConstraintsFromValueDomainNode(vdNode);
		vdBuilder.newStructDomain(vdNode.getName(),
		                          vdNode.getScope(), 
		                          fields, 
		                          constraints,
		                          false);
	}

	private void addListValueDomain(ValueDomainList vdNode,
									ValueDomainBuilder vdBuilder)
	{
		ArrayList<Constraint> constraints = getConstraintsFromValueDomainNode(vdNode);
		String strMin = vdNode.getMinSize();
		String strMax = vdNode.getMaxSize();
		Integer min = null, max = null;
		if (strMin != null)
		{
			if (!strMin.equals("MIN"))
			{
				min = Integer.parseInt(strMin);
			}
		}
		if (strMax != null)
		{
			if (!strMax.equals("MAX"))
			{
				max = Integer.parseInt(strMax);
			}
		}
		vdBuilder.newListDomain(vdNode.getName(),
		                        vdNode.getScope(),
								vdNode.getListType(),
								min,
								max,
								constraints,
								false);
	}

	private void addSingleValueDomain(	ValueDomainSingle vdNode,
										ValueDomainBuilder vdBuilder)
	{
		VdSingleType basicType = vdNode.getBasicType();
		ArrayList<Constraint> constraints = getConstraintsFromValueDomainNode(vdNode);
		if(basicType == VdSingleType.String)
		{
			vdBuilder.newStringDomain(	vdNode.getName(),
			                          	vdNode.getScope(),
										vdNode.getMinValueAsInt(),
										vdNode.getMaxValueAsInt(),
										vdNode.getRegex(),
										constraints,
										false);
		}
		else if(basicType == VdSingleType.Integer)
		{
			vdBuilder.newIntegerDomain(vdNode.getName(), 
			                           vdNode.getScope(), 
			                           vdNode.getMinValueAsInt(),
			                           vdNode.getMaxValueAsInt(),
			                           constraints,
			                           false);
		}
		else if(basicType == VdSingleType.Long)
		{
			vdBuilder.newLongDomain(vdNode.getName(), 
			                        vdNode.getScope(),
			                        vdNode.getMinValueAsLong(),
			                        vdNode.getMaxValueAsLong(),
			                        constraints,
			                        false);
		}
		else if(basicType == VdSingleType.Double)
		{
			vdBuilder.newDoubleDomain(vdNode.getName(), 
			                          vdNode.getScope(), 
			                          vdNode.getMinValueAsDouble(), 
			                          vdNode.getMaxValueAsDouble(), 
			                          constraints,
			                          false);
		}
		else if(basicType == VdSingleType.Float)
		{
			vdBuilder.newFloatDomain(vdNode.getName(), 
			                         vdNode.getScope(),
			                         vdNode.getMinValueAsFloat(), 
			                         vdNode.getMaxValueAsFloat(), 
			                         constraints,
			                         false);
		}
		else if(basicType == VdSingleType.Boolean)
		{
			vdBuilder.newBooleanDomain(vdNode.getName(), 
			                           vdNode.getScope(),
			                           constraints,
			                           false);
		}
	}

	private void addMapValueDomain(	ValueDomainMap vdNode,
									ValueDomainBuilder vdBuilder)
	{
		ArrayList<Constraint> constraints = getConstraintsFromValueDomainNode(vdNode);
		vdBuilder.newMapDomain(	vdNode.getName(),
		                       	vdNode.getScope(),
								vdNode.getKey(),
								vdNode.getValue(),
								constraints,
								false);
	}

	private void addOneOfValueDomain(	ValueDomainOneOf vdNode,
										ValueDomainBuilder vdBuilder)
	{
		ArrayList<Constraint> constraints = getConstraintsFromValueDomainNode(vdNode);
		vdBuilder.newOneOfDomain(	vdNode.getName(),
		                         	vdNode.getScope(),
									vdNode.getTypeNames(),
									constraints,
									false);
	}

}
