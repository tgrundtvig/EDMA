package org.abstractica.edma.generator.metamodel.kind;

import java.util.ArrayList;
import java.util.Collection;

import org.abstractica.edma.generator.EdmaImport;
import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaInterface;
import org.abstractica.edma.generator.common.JavaMethod;
import org.abstractica.edma.generator.common.JavaMethodSignature;
import org.abstractica.edma.generator.metamodel.AModelGenerator;
import org.abstractica.edma.metamodel.IMetaAttribute;
import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaKind;
import org.abstractica.edma.util.StringUtil;

public class EntityBuilderGenerator extends AModelGenerator
{
	private IMetaKind kind;

	private JavaClass builderClass;
	private JavaMethodSignature beginBuildSig[];
	private JavaMethod beginBuildMethod[];
	private Collection<JavaInterface> builderInterfaces;

	public EntityBuilderGenerator(	IPackageStructure pkgStruct,
									IMetaDataModel model,
									IMetaKind kind)
	{
		super(pkgStruct, model);
		this.kind = kind;
		builderInterfaces = new ArrayList<JavaInterface>();
		int size = kind.getNumberOfAttributes();
		IMetaKind base = kind.getBaseKind();
		if(base == null)
		{
			beginBuildSig = new JavaMethodSignature[2];
			beginBuildMethod = new JavaMethod[2];
			createBeginBuildMethod(size, 0, null);
			createBeginBuildMethod(size, 1, null);
		}
		else
		{
			beginBuildSig = new JavaMethodSignature[1];
			beginBuildMethod = new JavaMethod[1];
			createBeginBuildMethod(size, 0, base);
		}

		// Create the builder class
		builderClass = new JavaClass(kind.getName() + "Builder");
		builderClass.addConstructorField(	"IDataModelUpdate",
											"edma_upd",
											"Internal runtime interface");
		builderClass.addImport(EdmaImport.IDataModelUpdate);
		builderClass.addField("Object[]", "value");
		builderClass.addConstructorBodyLine("value = new Object[" + (size + 1)
				+ "];");
		builderClass.addConstructorParameter(	"Long",
												"edma_ID",
												"ID of the entity");
		builderClass.addConstructorBodyLine("value[0] = edma_ID;");
		

		// Create Builder interfaces
		for(int i = 0; i < size; ++i)
		{
			// Create builderInterface
			IMetaAttribute att = kind.getAttribute(i);
			JavaInterface buildIntf = new JavaInterface(kind.getName()
																+ "Builder"
																+ StringUtil.U(att.getName()),
														"Builder interface for setting the "
																+ att.getName()
																+ " attribute");
			builderClass.addImplements(buildIntf.getName());
			createSetValueMethod(i, buildIntf, builderClass);

			if(att.isOptional()) createSetNoValueMethod(i,
														buildIntf,
														builderClass);
			builderInterfaces.add(buildIntf);
		}
	}

	private void createBeginBuildMethod(int size, int i, IMetaKind base)
	{
		// BeginBuildMethod
		beginBuildSig[i] = new JavaMethodSignature(	"new" + kind.getName(),
													"Starts creation of a new "
															+ kind.getName()
															+ " entity.");
		if(base != null)
		{
			beginBuildSig[i].addParameter(	getViewIntfName(base),
											StringUtil.L(base.getName()),
											"The base entity");
		}
		else if(i == 1)
		{
			beginBuildSig[i].addParameter("Long", "ID", "ID of the entity");
		}

		beginBuildMethod[i] = new JavaMethod(beginBuildSig[i]);

		if(size == 0)
		{
			beginBuildSig[i].setReturnType(getUpdateIntfName(kind));
			beginBuildSig[i].setReturnDescription("The created "
					+ kind.getName() + " entity");
			beginBuildMethod[i].appendToBody("IDataModelUpdate edma_upd = edma_dmview.getUpdateInterface();\n");
			beginBuildMethod[i].addImport(EdmaImport.IDataModelUpdate);
			beginBuildMethod[i].appendToBody("Object[] value = new Object[1];\n");
			if(base != null)
			{
				beginBuildMethod[i].appendToBody("value[0] = "
						+ StringUtil.L(kind.getBaseKind().getName())
						+ ".getID().value();\n");
			}
			else if(i == 1)
			{
				beginBuildMethod[i].appendToBody("value[0] = ID;\n");
			}
			beginBuildMethod[i].appendToBody("IEntity edma_entity = edma_upd.entityNew("
					+ kind.getArrayPosition() + ", value);\n");
			beginBuildMethod[i].appendToBody("return new " + getImplName(kind)
					+ "(edma_entity, edma_upd);\n");
			beginBuildMethod[i].addImport(getUpdateIntfImport(kind));
			beginBuildMethod[i].addImport(getImplImport(kind));
		}
		else
		{
			IMetaAttribute firstAtt = kind.getAttribute(0);
			beginBuildSig[i].setReturnType(kind.getName() + "Builder"
					+ StringUtil.U(firstAtt.getName()));
			beginBuildSig[i].setReturnDescription("Builder interface to set the "
					+ firstAtt.getName() + " attribute.");
			beginBuildMethod[i].appendToBody("return new " + kind.getName()
					+ "Builder(edma_dmview.getUpdateInterface()");
			if(kind.getBaseKind() != null)
			{
				beginBuildMethod[i].appendToBody(", "
						+ StringUtil.L(kind.getBaseKind().getName())
						+ ".getID().value());\n");
			}
			else if(i == 1)
			{
				beginBuildMethod[i].appendToBody(", ID);\n");
			}
			else
			{
				beginBuildMethod[i].appendToBody(", null);\n");
			}
		}
	}

	private void createSetValueMethod(	int attIndex,
										JavaInterface intf,
										JavaClass clazz)
	{
		IMetaAttribute att = kind.getAttribute(attIndex);
		JavaMethodSignature methodSig = new JavaMethodSignature(att.getName(),
																"sets the "
																		+ att.getName()
																		+ " attribute.");
		methodSig.addParameter(	getIntfName(att.getValueDomain()),
								att.getName(),
								"The value to assign to the " + att.getName()
										+ " attribute");
		methodSig.addImport(getIntfImport(att.getValueDomain()));
		JavaMethod method = new JavaMethod(methodSig);
		method.appendToBody(extractValueCode(attIndex, att.getName()));
		method.addImport(EdmaImport.IValueInstance);
		setReturnForBuilderMethod(attIndex, methodSig, method);

		intf.addMethod(methodSig);
		clazz.addMethod(method);
	}

	private void createSetNoValueMethod(int attIndex,
										JavaInterface intf,
										JavaClass clazz)
	{
		IMetaAttribute att = kind.getAttribute(attIndex);
		JavaMethodSignature methodSig = new JavaMethodSignature("no"
				+ StringUtil.U(att.getName()), "sets the attribute "
				+ att.getName() + " to null.");
		JavaMethod method = new JavaMethod(methodSig);
		method.appendToBody("value[" + (attIndex + 1) + "] = null;\n");
		method.addImport(EdmaImport.IValueInstance);
		setReturnForBuilderMethod(attIndex, methodSig, method);

		intf.addMethod(methodSig);
		clazz.addMethod(method);
	}

	private String extractValueCode(int attIndex, String valueVar)
	{
		IMetaAttribute att = kind.getAttribute(attIndex);
		StringBuilder res = new StringBuilder();
		if(att.isOptional())
		{
			res.append("value[" + (attIndex + 1) + "] = (" + valueVar
					+ " == null ? null : ((IValueInstance) " + valueVar
					+ ").edma_getValue());\n");

		}
		else
		{
			res.append("if(" + valueVar
					+ " == null) throw new NullPointerException();\n");
			res.append("value[" + (attIndex + 1) + "] = ((IValueInstance) "
					+ valueVar + ").edma_getValue();\n");
		}
		return res.toString();
	}

	private void setReturnForBuilderMethod(	int attIndex,
											JavaMethodSignature methodSig,
											JavaMethod method)
	{
		Integer nextAttIndex = attIndex + 1;
		if(nextAttIndex < kind.getNumberOfAttributes())
		{
			IMetaAttribute nextAtt = kind.getAttribute(nextAttIndex);
			methodSig.setReturnType(kind.getName() + "Builder"
					+ StringUtil.U(nextAtt.getName()));
			methodSig.setReturnDescription("Builder interface for setting the "
					+ nextAtt.getName() + " attribute");
			method.appendToBody("return this;\n");
		}
		else
		{
			methodSig.setReturnType(getUpdateIntfName(kind));
			methodSig.addImport(getUpdateIntfImport(kind));
			methodSig.setReturnDescription("The id of the created "
					+ kind.getName() + " entity");
			if(kind.hasUniqueIndex())
			{
				methodSig.addException("UniqueException");
				methodSig.addImport(EdmaImport.UniqueException);
				method.appendToBody("IEntity edma_entity = edma_upd.entityNewUnique("
						+ kind.getArrayPosition() + ", value);\n");
			}
			else
			{
				method.appendToBody("IEntity edma_entity = edma_upd.entityNew("
						+ kind.getArrayPosition() + ", value);\n");
			}
			method.addImport(EdmaImport.IEntity);
			method.appendToBody("return new " + getImplName(kind)
					+ "(edma_entity, edma_upd);\n");
			method.addImport(getImplImport(kind));
		}
	}

	public void addToUpdater(JavaInterface updaterIntf, JavaClass updaterClass)
	{
		updaterIntf.addMethod(beginBuildSig[0]);
		if(beginBuildSig.length == 2) updaterIntf.addMethod(beginBuildSig[1]);
		updaterIntf.addInnerInterfaces(builderInterfaces);
		updaterClass.addMethod(beginBuildMethod[0]);
		if(beginBuildMethod.length == 2) updaterClass.addMethod(beginBuildMethod[1]);
		updaterClass.addInnerClass(builderClass);
	}
}
