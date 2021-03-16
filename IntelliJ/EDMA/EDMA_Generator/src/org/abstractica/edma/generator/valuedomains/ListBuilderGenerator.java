package org.abstractica.edma.generator.valuedomains;

import org.abstractica.edma.generator.AGenerator;
import org.abstractica.edma.generator.EdmaImport;
import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaInterface;
import org.abstractica.edma.generator.common.JavaMethod;
import org.abstractica.edma.generator.common.JavaMethodSignature;
import org.abstractica.edma.util.StringUtil;
import org.abstractica.edma.valuedomains.IListValueDomain;
import org.abstractica.edma.valuedomains.IMetaValueDomain;

class ListBuilderGenerator extends AGenerator
{
	private IListValueDomain vd;
	private JavaClass builderImpl;
	private JavaInterface builderIntf;
	private JavaMethodSignature newValueSig;
	private JavaMethod newValueMethod;

	public ListBuilderGenerator(IPackageStructure pkgStruct, IListValueDomain vd)
	{
		super(pkgStruct);
		this.vd = vd;
		newValueSig = new JavaMethodSignature(	"begin",
												"Starts creation of a new "
														+ vd.getName());
		newValueSig.setReturnType(getBuilderIntfName(vd));
		newValueSig.setReturnDescription("Builder interface to build the list");

		newValueMethod = new JavaMethod(newValueSig);
		newValueMethod.appendToBody("return new " + getBuilderImplName(vd)
				+ "();\n");
		newValueMethod.setStatic(true);
		newValueMethod.addImport(getBuilderImplImport(vd));

		builderIntf = new JavaInterface(getBuilderIntfName(vd),
										"Interface to create a list");

		builderImpl = new JavaClass(getBuilderImplName(vd));
		builderImpl.addField("ArrayList<Object>", "valueList");
		builderImpl.addConstructorBodyLine("valueList = new ArrayList<Object>();\n");
		builderImpl.addImplements(getBuilderIntfName(vd));
		builderImpl.addImport(getBuilderIntfImport(vd));
		builderImpl.addImport("java.util.ArrayList");
		builderImpl.setPackage(getBuilderImplPackage(vd));

		generateAddMethod();
		generateNativeAddMethod();
		generateBuildMethod();
	}

	public JavaClass getBuilderImpl()
	{
		return builderImpl;
	}

	public void addToValueDomainInterface(JavaClass intf)
	{
		intf.addMethod(newValueMethod);
		intf.addInnerInterface(builderIntf);
	}

	private void generateAddMethod()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"add",
															"Adds an element to the list");
		sig.setReturnType(getBuilderIntfName(vd));
		sig.setReturnDescription("An interface to the builder for chaining method calls");
		String paramName = StringUtil.L(vd.getElementValueDomain().getName());
		sig.addParameter(	getIntfName(vd.getElementValueDomain()),
							paramName,
							"The element to be added to the list");
		builderIntf.addMethod(sig);
		JavaMethod addMethod = new JavaMethod(sig);
		addMethod.appendToBody("if(" + paramName
				+ " == null) throw new NullPointerException();\n");
		addMethod.appendToBody("valueList.add(((IValueInstance) " + paramName
				+ ").edma_getValue());\n");
		addMethod.appendToBody("return this;\n");
		addMethod.addImport(EdmaImport.IValueInstance);
		addMethod.addImport(getIntfImport(vd.getElementValueDomain()));
		builderImpl.addMethod(addMethod);
	}

	private void generateNativeAddMethod()
	{
		IMetaValueDomain elemVD = vd.getElementValueDomain();
		if(elemVD.getBasicType() == null) return;
		JavaMethodSignature sig = new JavaMethodSignature(	"add",
															"Adds an element to the list");
		sig.setReturnType(getBuilderIntfName(vd));
		sig.setReturnDescription("An interface to the builder for chaining method calls");
		String paramName = StringUtil.L(elemVD.getName());

		sig.addParameter(	elemVD.getBasicType(),
							paramName,
							"The element to be added to the list");
		builderIntf.addMethod(sig);

		JavaMethod method = new JavaMethod(sig);
		method.appendToBody("if(" + paramName
				+ " == null) throw new NullPointerException();\n");
		if(elemVD.needValidate())
		{
			sig.addException("InvalidValueException");
			sig.addImport(EdmaImport.InvalidValueException);
			method.appendToBody(getImplName(elemVD) + ".edma_validate("
					+ paramName + ");\n");
		}
		method.appendToBody("valueList.add(" + getImplName(elemVD)
				+ ".edma_create(" + paramName + "));\n");
		method.appendToBody("return this;\n");
		method.addImport(EdmaImport.IValueInstance);
		builderImpl.addMethod(method);
	}

	private void generateBuildMethod()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"end",
															"Builds the list with the added elements");
		sig.setReturnType(getIntfName(vd));
		sig.setReturnDescription("The builded list");

		JavaMethod method = new JavaMethod(sig);
		method.addImport(getIntfImport(vd));
		method.appendToBody("Object[] res = new Object[valueList.size()];\n");
		method.appendToBody("int pos = 0;\n");
		method.appendToBody("for(Object o : valueList)\n");
		method.appendToBody("{\n");
		method.appendToBody("    res[pos++] = o;\n");
		method.appendToBody("}\n");
		if(vd.needValidate())
		{
			sig.addException("InvalidValueException");
			sig.addImport(EdmaImport.InvalidValueException);
			method.appendToBody(getImplName(vd) + ".edma_validate(res);\n");
		}
		method.appendToBody("return new " + getImplName(vd) + "("
				+ getImplName(vd) + ".edma_create(res));\n");
		builderIntf.addMethod(sig);
		builderImpl.addMethod(method);
	}
}
