package edma.generator.metamodel.kind;

import edma.generator.EdmaImport;
import edma.generator.IPackageStructure;
import edma.generator.common.JavaClass;
import edma.generator.common.JavaInterface;
import edma.generator.common.JavaMethod;
import edma.generator.common.JavaMethodSignature;
import edma.generator.metamodel.AModelGenerator;
import edma.metamodel.IMetaAttribute;
import edma.metamodel.IMetaDataModel;
import edma.metamodel.IMetaIndex;
import edma.metamodel.IMetaIndex.IndexType;
import edma.metamodel.IMetaKind;
import edma.util.StringUtil;

public class IndexGenerator extends AModelGenerator
{
	private JavaInterface intf;
	private JavaClass impl;
	private IMetaIndex index;
	private IMetaKind kind;

	protected IndexGenerator(	IPackageStructure pkgStruct,
								IMetaDataModel model,
								JavaInterface intf,
								JavaClass impl,
								IMetaIndex index,
								IMetaKind indexKind)
	{
		super(pkgStruct, model);
		this.intf = intf;
		this.impl = impl;
		this.index = index;
		this.kind = indexKind;
	}

	public void generateForKindIndex()
	{
		String getIndexCode = "getKindIndex(" + kind.getArrayPosition() + ", "
				+ index.getArrayPosition() + ")";
		generateIndexMethods(getIndexCode, "get");
	}

	public void generateForRelationIndex(	int relID,
											String AorB,
											String roleThis,
											String roleOther,
											String fIDVarName)
	{
		String getIndexCode = "getRelationIndexOn" + AorB + "(" + relID + ", "
				+ index.getArrayPosition() + ", " + fIDVarName + ")";
		String methodPreName=null;
		if (roleThis == null)
			methodPreName = "get" + StringUtil.U(roleOther);
		else
			methodPreName = "as"+StringUtil.U(roleThis)+"Get" + StringUtil.U(roleOther);
		if(!index.getIndexType().equals(IMetaIndex.IndexType.Unique))
		{
			methodPreName = methodPreName + "Set";
		}
		generateIndexMethods(getIndexCode, methodPreName);
	}

	private void generateIndexMethods(String getIndexCode, String methodPreName)
	{
		if(index.getIndexType() == IndexType.Unique) getFromUniqueIndex(getIndexCode,
																		methodPreName);
		else if(index.getIndexType() == IndexType.Equal) equalsIndex(	getIndexCode,
																		methodPreName);
		else if(index.getIndexType() == IndexType.Compare)
		{
			compareIndex("Equals", "equals", getIndexCode, methodPreName);
			compareIndex(	"LessThan",
							"is less than",
							getIndexCode,
							methodPreName);
			compareIndex(	"LessThanOrEqual",
							"is less than or equal to",
							getIndexCode,
							methodPreName);
			compareIndex(	"GreaterThan",
							"is greater than",
							getIndexCode,
							methodPreName);
			compareIndex(	"GreaterThanOrEqual",
							"is greater than or equal to",
							getIndexCode,
							methodPreName);
			compareIndexRange(getIndexCode, methodPreName);
		}
		else throw new RuntimeException("Unknown index-type: "
				+ index.getIndexType());

		if(index.canBeNull()) nullIndex(getIndexCode, methodPreName);
	}

	private void getFromUniqueIndex(String getIndexCode, String methodPreName)
	{
		StringBuilder methodName = new StringBuilder(methodPreName);
		methodName.append("From");
		StringBuilder attDesc = new StringBuilder();

		generateMultiNameAndDesc(methodName, attDesc, index);

		StringBuilder desc = new StringBuilder();
		desc.append("he unique " + StringUtil.L(kind.getName())
				+ " from the unique-index on ");
		desc.append(attDesc.toString());
		desc.append(" or <tt>null</tt> if there is no "
				+ StringUtil.L(kind.getName()) + " with the given ");
		desc.append(attDesc.toString());
		desc.append(".");
		JavaMethodSignature sig = new JavaMethodSignature(	methodName.toString(),
															"Returns t"
																	+ desc.toString());
		sig.setReturnType(getViewIntfName(kind));
		sig.addImport(getViewIntfImport(kind));
		sig.setReturnDescription("T" + desc.toString());
		addMultiParameters(sig, index);
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("Object[] edma_values = new Object["
				+ index.getNumberOfAttributes() + "];\n");
		assignMultiParameters(m, index);
		m.appendToBody("IEntity res = edma_dmview." + getIndexCode
				+ ".getFromUnique(edma_values);\n");
		m.appendToBody("if(res == null) return null;\n");
		m.appendToBody("return new " + getImplName(kind)
				+ "(res, edma_dmview);\n");
		m.addImport(getImplImport(kind));
		m.addImport(EdmaImport.IEntity);
		impl.addMethod(m);
	}

	private void nullIndex(String getIndexCode, String methodPreName)
	{
		StringBuilder methodName = new StringBuilder(methodPreName);
		StringBuilder attDesc = new StringBuilder();
		methodName.append("Where");

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
		desc.append(kind.getName() + "Set where ");
		desc.append(attDesc.toString());
		if(index.getNumberOfAttributes() > 1)
		{
			desc.append(" are <tt>null</tt>.");
		}
		else
		{
			desc.append(" is <tt>null</tt>.");
		}
		JavaMethodSignature sig = new JavaMethodSignature(	StringUtil.L(methodName.toString()),
															"Returns a "
																	+ desc.toString());
		sig.setReturnType(getSetIntfName(kind));
		sig.setReturnDescription("A " + desc.toString());
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("int newSetID = edma_dmview." + getIndexCode
				+ ".getWhereNull();\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		impl.addMethod(m);
	}

	private void equalsIndex(String getIndexCode, String methodPreName)
	{
		StringBuilder methodName = new StringBuilder(methodPreName);
		StringBuilder attDesc = new StringBuilder();
		methodName.append("Where");
		generateMultiNameAndDesc(methodName, attDesc, index);
		methodName.append("Equals");
		StringBuilder desc = new StringBuilder();
		desc.append(kind.getName() + "Set where ");
		desc.append(attDesc.toString());
		desc.append(" equals the provided ");
		desc.append(attDesc.toString());
		desc.append(".");
		JavaMethodSignature sig = new JavaMethodSignature(	StringUtil.L(methodName.toString()),
															"Returns a "
																	+ desc.toString());
		sig.setReturnType(getSetIntfName(kind));
		sig.setReturnDescription("A " + desc.toString());
		addMultiParameters(sig, index);
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("Object[] edma_values = new Object["
				+ index.getNumberOfAttributes() + "];\n");
		assignMultiParameters(m, index);
		m.appendToBody("int newSetID = edma_dmview." + getIndexCode
				+ ".getEquals(edma_values);\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		m.addImport(getSetImplImport(kind));
		impl.addMethod(m);
	}

	private void compareIndex(	String compName,
								String compDesc,
								String getIndexCode,
								String methodPreName)
	{
		StringBuilder methodName = new StringBuilder(methodPreName);
		StringBuilder attDesc = new StringBuilder();
		methodName.append("Where");
		generateMultiNameAndDesc(methodName, attDesc, index);

		methodName.append(compName);
		StringBuilder desc = new StringBuilder();
		desc.append(kind.getName() + "Set where ");
		desc.append(attDesc.toString());
		desc.append(" ");
		desc.append(compDesc);
		desc.append(" the provided ");
		desc.append(attDesc.toString());
		desc.append(".");
		JavaMethodSignature sig = new JavaMethodSignature(	StringUtil.L(methodName.toString()),
															"Returns a "
																	+ desc.toString());
		sig.setReturnType(getSetIntfName(kind));
		sig.setReturnDescription("A " + desc.toString());
		addMultiParameters(sig, index);
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("Object[] edma_values = new Object["
				+ index.getNumberOfAttributes() + "];\n");
		assignMultiParameters(m, index);
		m.appendToBody("int newSetID = edma_dmview." + getIndexCode + ".get"
				+ compName + "(edma_values);\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		m.addImport(getSetImplImport(kind));
		impl.addMethod(m);
	}

	private void compareIndexRange(String getIndexCode, String methodPreName)
	{
		StringBuilder methodName = new StringBuilder(methodPreName);
		StringBuilder attDesc = new StringBuilder();
		methodName.append("Where");
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
		JavaMethodSignature sig = new JavaMethodSignature(	StringUtil.L(methodName.toString()),
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
			m.appendToBody("edma_minValues[" + i + "] = ((IValueInstance) min"
					+ StringUtil.U(att.getName()) + ").edma_getValue();\n");
			m.appendToBody("edma_maxValues[" + i + "] = ((IValueInstance) max"
					+ StringUtil.U(att.getName()) + ").edma_getValue();\n");
			m.addImport(EdmaImport.IValueInstance);
		}
		m.appendToBody("int newSetID = edma_dmview."
				+ getIndexCode
				+ ".getRange(edma_minValues, minInclusive, edma_maxValues, maxInclusive);\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		m.addImport(getSetImplImport(kind));
		impl.addMethod(m);
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
}
