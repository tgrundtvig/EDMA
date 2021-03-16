package org.abstractica.edma.generator.metamodel.kind;

import org.abstractica.edma.generator.EdmaImport;
import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaInterface;
import org.abstractica.edma.generator.common.JavaMethod;
import org.abstractica.edma.generator.common.JavaMethodSignature;
import org.abstractica.edma.generator.metamodel.AModelGenerator;
import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaIndex;
import org.abstractica.edma.metamodel.IMetaKind;

public class KindAccessGenerator extends AModelGenerator
{
	private JavaInterface intf;
	private JavaClass impl;
	private IMetaKind kind;

	protected KindAccessGenerator(	IPackageStructure pkgStruct,
									IMetaDataModel model,
									IMetaKind kind)
	{
		super(pkgStruct, model);
		this.kind = kind;
		// Interface and class
		intf = new JavaInterface(	getKindIntfName(kind),
									"Interface for the kind: " + kind.getName());
		intf.setPackage(getKindIntfPackage(kind));
		impl = new JavaClass(getKindImplName(kind));
		impl.setPackage(getKindImplPackage(kind));
		impl.addImplements(intf.getName());
		impl.addImport(getKindIntfImport(kind));

		impl.addConstructorField("int", "edma_kindIndex", "kind index");
		impl.addConstructorField(	"IDataModelView",
									"edma_dmview",
									"Internal runtime interface");
		impl.addImport(EdmaImport.IDataModelView);

		getFromID();
		getAll();

		// Indexes
		int size = kind.getNumberOfIndexes();
		for(int i = 0; i < size; ++i)
		{
			IMetaIndex index = kind.getIndex(i);
			
			IndexGenerator ig = new IndexGenerator(pkgStruct, model, intf, impl, index, kind);
			ig.generateForKindIndex();
			
			/*
			if(index.getIndexType() == IndexType.Unique) getFromUniqueIndex(index);
			else if(index.getIndexType() == IndexType.Equal) equalsIndex(index);
			else if(index.getIndexType() == IndexType.Compare)
			{
				compareIndex(index, "Equals", "equals");
				compareIndex(index, "LessThan", "is less than");
				compareIndex(	index,
								"LessThanOrEqual",
								"is less than or equal to");
				compareIndex(index, "GreaterThan", "is greater than");
				compareIndex(	index,
								"GreaterThanOrEqual",
								"is greater than or equal to");
				compareIndexRange(index);
			}
			else throw new RuntimeException("Unknown index-type: "
					+ index.getIndexType());

			int numberOfAttributes = index.getNumberOfAttributes();
			boolean canBeNull = true;
			for(int j = 0; j < numberOfAttributes; ++j)
			{
				if(!index.getAttribute(j).isOptional())
				{
					canBeNull = false;
					break;
				}
			}
			if(canBeNull) nullIndex(index);
			*/
		}
	}

	public JavaInterface getIntf()
	{
		return intf;
	}

	public JavaClass getImpl()
	{
		return impl;
	}

	private void getFromID()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"getFromID",
															"Get an entity from its id.");
		sig.setReturnType(getViewIntfName(kind));
		sig.setReturnDescription("A viewer on the entity with the given ID or null if none exists.");
		sig.addParameter(	getIntfName(kind.getIDValueDomain()),
							"id",
							"The id of the entity to get");
		sig.addImport(getViewIntfImport(kind));
		sig.addImport(getIntfImport(kind.getIDValueDomain()));
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("IEntity res = edma_dmview.kindGetFromID(edma_kindIndex, id.value());\n");
		m.appendToBody("if(res == null) return null;\n");
		m.appendToBody("return new " + getImplName(kind)
				+ "(res, edma_dmview);\n");
		m.addImport(getImplImport(kind));
		m.addImport(EdmaImport.IEntity);
		impl.addMethod(m);
	}

	private void getAll()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"getAll",
															"Returns the set of all "
																	+ kind.getName()
																	+ " entities.");
		sig.setReturnType(getSetIntfName(kind));
		sig.addImport(getSetIntfImport(kind));
		sig.setReturnDescription("The set of all " + kind.getName()
				+ " entities.");
		intf.addMethod(sig);
		JavaMethod m = new JavaMethod(sig);
		// Implementation
		m.appendToBody("int newSetID = edma_dmview.kindGetAll("
				+ kind.getArrayPosition() + ");\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		m.addImport(getSetImplImport(kind));
		impl.addMethod(m);
	}

	/*
	private void getFromUniqueIndex(IMetaIndex index)
	{
		StringBuilder methodName = new StringBuilder();
		methodName.append("getFrom");
		StringBuilder attDesc = new StringBuilder();

		generateMultiNameAndDesc(methodName, attDesc, index);

		StringBuilder desc = new StringBuilder();
		desc.append("he unique " + StringUtil.L(kind.getName())
				+ " in this set from the unique-index on ");
		desc.append(attDesc.toString());
		desc.append(" or <tt>null</tt> if there is no "
				+ StringUtil.L(kind.getName()) + " with the given ");
		desc.append(attDesc.toString());
		desc.append(" in this set.");
		JavaMethodSignature sig = new JavaMethodSignature(	methodName.toString(),
															"Returns t"
																	+ desc.toString());
		sig.setReturnType(getViewIntfName(kind));
		sig.setReturnDescription("T" + desc.toString());
		addMultiParameters(sig, index);
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("Object[] edma_values = new Object["
				+ index.getNumberOfAttributes() + "];\n");
		assignMultiParameters(m, index);
		m.appendToBody("IEntity res = edma_dmview.getKindIndex("
				+ kind.getArrayPosition() + ", " + index.getArrayPosition()
				+ ").getFromUnique(edma_values);\n");
		m.appendToBody("if(res == null) return null;\n");
		m.appendToBody("return new " + getImplName(kind)
				+ "(res, edma_dmview);\n");
		m.addImport(getViewIntfImport(kind));
		m.addImport(EdmaImport.IEntity);
		impl.addMethod(m);
	}

	private void equalsIndex(IMetaIndex index)
	{
		StringBuilder methodName = new StringBuilder();
		StringBuilder attDesc = new StringBuilder();
		methodName.append("where");

		generateMultiNameAndDesc(methodName, attDesc, index);
		methodName.append("Equals");
		StringBuilder desc = new StringBuilder();
		desc.append("he subset of this " + kind.getName() + "Set where ");
		desc.append(attDesc.toString());
		desc.append(" equals the provided ");
		desc.append(attDesc.toString());
		desc.append(".");
		JavaMethodSignature sig = new JavaMethodSignature(	methodName.toString(),
															"Returns t"
																	+ desc.toString());
		sig.setReturnType(getSetIntfName(kind));
		sig.setReturnDescription("T" + desc.toString());
		addMultiParameters(sig, index);
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("Object[] edma_values = new Object["
				+ index.getNumberOfAttributes() + "];\n");
		assignMultiParameters(m, index);
		m.appendToBody("int newSetID = edma_dmview.getKindIndex("
				+ kind.getArrayPosition() + ", " + index.getArrayPosition()
				+ ").getEquals(edma_values);\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		m.addImport(getViewIntfImport(kind));
		impl.addMethod(m);
	}

	private void compareIndex(IMetaIndex index, String compName, String compDesc)
	{
		StringBuilder methodName = new StringBuilder();
		StringBuilder attDesc = new StringBuilder();
		methodName.append("where");
		generateMultiNameAndDesc(methodName, attDesc, index);

		methodName.append(compName);
		StringBuilder desc = new StringBuilder();
		desc.append("he subset of this " + kind.getName() + "Set where ");
		desc.append(attDesc.toString());
		desc.append(" ");
		desc.append(compDesc);
		desc.append(" the provided ");
		desc.append(attDesc.toString());
		desc.append(".");
		JavaMethodSignature sig = new JavaMethodSignature(	methodName.toString(),
															"Returns t"
																	+ desc.toString());
		sig.setReturnType(getSetIntfName(kind));
		sig.setReturnDescription("T" + desc.toString());
		addMultiParameters(sig, index);
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("Object[] edma_values = new Object["
				+ index.getNumberOfAttributes() + "];\n");
		assignMultiParameters(m, index);
		m.appendToBody("int newSetID = edma_dmview.getKindIndex("
				+ kind.getArrayPosition() + ", " + index.getArrayPosition()
				+ ").get" + compName + "(edma_values);\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		m.addImport(getViewIntfImport(kind));
		impl.addMethod(m);
	}

	private void compareIndexRange(IMetaIndex index)
	{
		StringBuilder methodName = new StringBuilder();
		StringBuilder attDesc = new StringBuilder();
		methodName.append("where");
		generateMultiNameAndDesc(methodName, attDesc, index);
		methodName.append("InRange");
		StringBuilder desc = new StringBuilder();
		desc.append("he subset of this " + kind.getName() + "Set where ");
		desc.append(attDesc.toString());
		int size = index.getNumberOfAttributes();
		if(size > 1)
		{
			desc.append(" are ");
		}
		else
		{
			desc.append(" is ");
		}
		desc.append("in the given range.");
		JavaMethodSignature sig = new JavaMethodSignature(	methodName.toString(),
															"Returns t"
																	+ desc.toString());
		sig.setReturnType(getSetIntfName(kind));
		sig.setReturnDescription("T" + desc.toString());
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = index.getAttribute(i);
			sig.addParameter(	getIntfName(att.getValueDomain()),
								"min" + StringUtil.U(att.getName()),
								"Minimum value for " + att.getName());
			sig.addImport(getIntfImport(att.getValueDomain()));
		}
		sig.addParameter(	"boolean",
							"minInclusive",
							"<tt>true</tt> if the minimum value should be included.");
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = index.getAttribute(i);
			sig.addParameter(	getIntfName(att.getValueDomain()),
								"max" + StringUtil.U(att.getName()),
								"Maximum value for " + att.getName());
			sig.addImport(getIntfImport(att.getValueDomain()));
		}
		sig.addParameter(	"boolean",
							"maxInclusive",
							"<tt>true</tt> if the maximum value should be included.");
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("Object[] edma_minValues = new Object["
				+ index.getNumberOfAttributes() + "];\n");
		m.appendToBody("Object[] edma_maxValues = new Object["
				+ index.getNumberOfAttributes() + "];\n");
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = index.getAttribute(i);
			m.appendToBody("edma_minValues[" + i + "] = min"
					+ StringUtil.U(att.getName()) + ";\n");
			m.appendToBody("edma_maxValues[" + i + "] = max"
					+ StringUtil.U(att.getName()) + ";\n");
		}
		m.appendToBody("int newSetID = edma_dmview.getKindIndex("
				+ kind.getArrayPosition()
				+ ", "
				+ index.getArrayPosition()
				+ ").getRange(edma_minValues, minInclusive, edma_maxValues, maxInclusive);\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		m.addImport(getViewIntfImport(kind));
		impl.addMethod(m);
	}

	private void nullIndex(IMetaIndex index)
	{
		StringBuilder methodName = new StringBuilder();
		StringBuilder attDesc = new StringBuilder();
		methodName.append("where");

		generateMultiNameAndDesc(methodName, attDesc, index);
		if(index.getNumberOfAttributes() > 1)
		{
			methodName.append("AreNull");
		}
		else
		{
			methodName.append("IsNull");
		}

		StringBuilder desc = new StringBuilder();
		desc.append("he subset of this " + kind.getName() + "Set where ");
		desc.append(attDesc.toString());
		if(index.getNumberOfAttributes() > 1)
		{
			desc.append(" are <tt>null</tt>.");
		}
		else
		{
			desc.append(" is <tt>null</tt>.");
		}
		JavaMethodSignature sig = new JavaMethodSignature(	methodName.toString(),
															"Returns t"
																	+ desc.toString());
		sig.setReturnType(getSetIntfName(kind));
		sig.setReturnDescription("T" + desc.toString());
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("int newSetID = edma_dmview.getKindIndex("
				+ kind.getArrayPosition() + ", " + index.getArrayPosition()
				+ ").getWhereNull();\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		impl.addMethod(m);
	}

	private void generateMultiNameAndDesc(	StringBuilder name,
											StringBuilder desc,
											IMetaIndex index)
	{
		int size = index.getNumberOfAttributes();
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = index.getAttribute(i);
			if(i > 0)
			{
				name.append("And");
				if(i < size - 1)
				{
					desc.append(", ");
				}
				else
				{
					desc.append(" and ");
				}
			}
			name.append(StringUtil.U(att.getName()));
			desc.append(att.getName());
		}
	}

	private void addMultiParameters(JavaMethodSignature sig, IMetaIndex index)
	{
		int size = index.getNumberOfAttributes();
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = index.getAttribute(i);
			sig.addParameter(	getIntfName(att.getValueDomain()),
								att.getName(),
								"Value for " + att.getName());
			sig.addImport(getIntfImport(att.getValueDomain()));
		}
	}

	private void assignMultiParameters(JavaMethod m, IMetaIndex index)
	{
		int size = index.getNumberOfAttributes();
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = index.getAttribute(i);
			m.appendToBody("edma_values[" + i + "] = ((IValueInstance) "
					+ att.getName() + ").edma_getValue();\n");
			m.addImport(EdmaImport.IValueInstance);
		}
	}
	*/
}
