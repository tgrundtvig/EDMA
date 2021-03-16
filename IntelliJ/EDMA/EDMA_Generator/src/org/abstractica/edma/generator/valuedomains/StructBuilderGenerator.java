package org.abstractica.edma.generator.valuedomains;

import java.util.ArrayList;
import java.util.Collection;

import org.abstractica.edma.generator.AGenerator;
import org.abstractica.edma.generator.EdmaImport;
import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaInterface;
import org.abstractica.edma.generator.common.JavaMethod;
import org.abstractica.edma.generator.common.JavaMethodSignature;
import org.abstractica.edma.util.StringUtil;
import org.abstractica.edma.valuedomains.IStructField;
import org.abstractica.edma.valuedomains.IStructValueDomain;

class StructBuilderGenerator extends AGenerator
{
	private IStructValueDomain vd;
	private JavaClass builderImpl;
	private JavaMethodSignature beginBuildSig;
	private JavaMethod beginBuildMethod;
	private Collection<JavaInterface> builderInterfaces;

	public StructBuilderGenerator(	IPackageStructure pkgStruct,
									IStructValueDomain vd)
	{
		super(pkgStruct);
		this.vd = vd;
		builderInterfaces = new ArrayList<JavaInterface>();

		int size = vd.getNumberOfFields();

		// Create the builder class
		builderImpl = new JavaClass(getBuilderImplName(vd));
		builderImpl.setPackage(getBuilderImplPackage(vd));
		builderImpl.addField("Object[]", "edma_value");
		builderImpl.addConstructorBodyLine("edma_value = new Object[" + size
				+ "];\n");
		beginBuildSig = new JavaMethodSignature("create",
												"Starts creation of a new "
														+ vd.getName());
		beginBuildMethod = new JavaMethod(beginBuildSig);
		beginBuildMethod.setStatic(true);
		Integer firstField = getField(0);
		if(firstField == null)
		{
			beginBuildSig.setReturnType(getIntfName(vd));
			beginBuildSig.setReturnDescription("The created " + vd.getName()
					+ " edma_value");
			beginBuildMethod.appendToBody("return new " + getImplName(vd)
					+ "(new Object[0]);\n");
			beginBuildMethod.addImport(getImplImport(vd));
		}
		else
		{
			beginBuildSig.setReturnType(getBuilderIntfName(vd)
					+ StringUtil.U(vd.getField(firstField).getName()));
			beginBuildSig.setReturnDescription("Builder interface to set the "
					+ vd.getField(firstField).getName() + " field");

			beginBuildMethod.appendToBody("return new "
					+ getBuilderImplName(vd) + "();\n");
			beginBuildMethod.addImport(getBuilderImplImport(vd));
		}
		for(int i = 0; i < size; ++i)
		{
			// Create builderInterface
			IStructField field = vd.getField(i);
			JavaInterface buildIntf = new JavaInterface(getBuilderIntfName(vd)
																+ StringUtil.U(field.getName()),
														"Builder interface for setting the "
																+ field.getName()
																+ " field of "
																+ vd.getName());
			builderImpl.addImplements(buildIntf.getName());
			builderImpl.addImport(getBuilderIntfImport(vd)
					+ StringUtil.U(field.getName()));
			createSetValueMethod(i, buildIntf, builderImpl);
			String type = field.getValueDomain().getBasicType();
			if(type != null)
			{
				createSetNativeValueMethod(i, type, buildIntf, builderImpl);
			}

			if(field.canBeNull()) createSetNoValueMethod(	i,
															buildIntf,
															builderImpl);
			builderInterfaces.add(buildIntf);
		}
	}

	public JavaClass getBuilderImpl()
	{
		return builderImpl;
	}
	
	private Integer getField(int field)
	{
		if(field < vd.getNumberOfFields()) return field;
		return null;
	}

	private void createSetValueMethod(	int fieldIndex,
										JavaInterface intf,
										JavaClass impl)
	{
		IStructField field = vd.getField(fieldIndex);
		JavaMethodSignature methodSig = new JavaMethodSignature(field.getName(),
																"sets the "
																		+ field.getName()
																		+ " field.");
		String paramName = field.getName();
		methodSig.addParameter(	getIntfName(field.getValueDomain()),
								paramName,
								"The value to assign to the " + field.getName()
										+ " field");
		JavaMethod method = new JavaMethod(methodSig);
		if(field.canBeNull())
		{
			method.appendToBody("edma_value[" + fieldIndex + "] = (" + paramName
					+ " == null ? null : (((IValueInstance) " + paramName
					+ ").edma_getValue()));\n");
		}
		else
		{
			method.appendToBody("if(" + paramName
					+ " == null) throw new NullPointerException(\"The field " + field.getName() + " in " + vd.getName() + " may not be null\");\n");
			method.appendToBody("edma_value[" + fieldIndex
					+ "] = ((IValueInstance) " + paramName
					+ ").edma_getValue();\n");
		}
		method.addImport(EdmaImport.IValueInstance);
		method.addImport(getIntfImport(field.getValueDomain()));
		setReturnForBuilderMethod(fieldIndex, methodSig, method);
		intf.addMethod(methodSig);
		impl.addMethod(method);
	}

	private void createSetNativeValueMethod(int fieldIndex,
											String nativeType,
											JavaInterface intf,
											JavaClass impl)
	{
		IStructField field = vd.getField(fieldIndex);
		JavaMethodSignature methodSig = new JavaMethodSignature(field.getName(),
																"sets the "
																		+ field.getName()
																		+ " field.");
		String paramName = field.getName();
		methodSig.addParameter(	nativeType,
								paramName,
								"The value to assign to the " + field.getName()
										+ " field");
		JavaMethod method = new JavaMethod(methodSig);

		if(field.getValueDomain().needValidate())
		{
			// Only validate if the value is not null
			methodSig.addException("InvalidValueException");
			methodSig.addImport(EdmaImport.InvalidValueException);
			method.appendToBody("if(" + paramName + " != null) "
					+ getImplName(field.getValueDomain()) + ".edma_validate("
					+ paramName + ");\n");
		}
		if(field.canBeNull())
		{
			method.appendToBody("edma_value[" + fieldIndex + "] = (" + paramName
					+ " == null ? null : ("
					+ getImplName(field.getValueDomain()) + ".edma_create("
					+ paramName + ")));\n");
		}
		else
		{
			method.appendToBody("if(" + paramName
					+ " == null) throw new NullPointerException();\n");
			method.appendToBody("edma_value[" + fieldIndex + "] = "
					+ getImplName(field.getValueDomain()) + ".edma_create("
					+ paramName + ");\n");
		}
		method.addImport(getImplImport(field.getValueDomain()));
		method.addImport(EdmaImport.IValueInstance);
		setReturnForBuilderMethod(fieldIndex, methodSig, method);

		intf.addMethod(methodSig);
		impl.addMethod(method);
	}

	private void createSetNoValueMethod(int fieldIndex,
										JavaInterface intf,
										JavaClass impl)
	{
		IStructField field = vd.getField(fieldIndex);
		JavaMethodSignature methodSig = new JavaMethodSignature("no"
				+ StringUtil.U(field.getName()), "sets the field"
				+ field.getName() + " to null.");
		JavaMethod method = new JavaMethod(methodSig);
		method.appendToBody("edma_value[" + fieldIndex + "] = null;\n");
		method.addImport(EdmaImport.IValueInstance);
		setReturnForBuilderMethod(fieldIndex, methodSig, method);

		intf.addMethod(methodSig);
		impl.addMethod(method);
	}

	private void setReturnForBuilderMethod(	int fieldIndex,
											JavaMethodSignature sig,
											JavaMethod method)
	{
		Integer nextFieldID = getField(fieldIndex + 1);
		if(nextFieldID != null)
		{
			IStructField nextField = vd.getField(nextFieldID);
			sig.setReturnType(getBuilderIntfName(vd)
					+ StringUtil.U(nextField.getName()));
			sig.setReturnDescription("Builder interface for setting the "
					+ nextField.getName() + " field");
			method.addImport(getBuilderIntfImport(vd)
					+ StringUtil.U(nextField.getName()));
			method.appendToBody("return this;\n");
		}
		else
		{
			sig.setReturnType(getIntfName(vd));
			sig.setReturnDescription("The created " + vd.getName() + " value");
			if(vd.needValidate())
			{
				sig.addException("InvalidValueException");
				sig.addImport(EdmaImport.InvalidValueException);
				method.appendToBody(getImplName(vd)
						+ ".edma_validate(edma_value);\n");
			}
			method.appendToBody("return new " + getImplName(vd) + "("
					+ getImplName(vd) + ".edma_create(edma_value));\n");
			method.addImport(getIntfImport(vd));
		}
	}

	public void addToValueDomainInterface(JavaClass intf)
	{
		intf.addMethod(beginBuildMethod);
		intf.addInnerInterfaces(builderInterfaces);
	}
}
