package edma.generator.valuedomains;

import edma.generator.EdmaImport;
import edma.generator.IPackageStructure;
import edma.generator.common.JavaClass;
import edma.generator.common.JavaMethod;
import edma.generator.common.JavaMethodSignature;
import edma.valuedomains.IMetaValueDomain;

class BasicGenerator extends BaseValueDomainGenerator
{
	public BasicGenerator(IPackageStructure pkgStruct, IMetaValueDomain vd)
	{
		super(pkgStruct, vd);
		generate();
	}
	
	private void generate()
	{
		impl.addConstructorField("Object", "value", "The internal value");
		getValueMethod();
		addBasicNewMethod(intf);
		addBasicIsValidMethod(intf);	
	}

	private void getValueMethod()
	{
		JavaMethodSignature getValueSig = new JavaMethodSignature(	"value",
																	"returns the "
																			+ vd.getBasicType()
																			+ " value that is stored in this "
																			+ vd.getName());
		getValueSig.setReturnType(vd.getBasicType());
		getValueSig.setReturnDescription("The " + vd.getBasicType()
				+ " value stored in this " + vd.getName());

		JavaMethod getValueMethod = new JavaMethod(getValueSig);
		getValueMethod.appendToBody("return (" + vd.getBasicType() + ") value;");

		intf.addAbstractMethod(getValueSig);
		impl.addMethod(getValueMethod);
	}

	private void addBasicNewMethod(JavaClass intf)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"create",
															"Call this method to create a new "
																	+ vd.getName()
																	+ " value. ");
		sig.addParameter(vd.getBasicType(), "value", "The value of the " + vd.getName()
				+ " to be created.");
		sig.setReturnType(vd.getName());
		sig.setReturnDescription("The newly created " + vd.getName() + " value");
		if(vd.needValidate())
		{
			sig.addException("InvalidValueException");
			sig.addImport(EdmaImport.InvalidValueException);
		}
		JavaMethod method = new JavaMethod(sig);
		method.setStatic(true);
		if(vd.needValidate()) method.appendToBody(getImplName(vd) + ".edma_validate(value);\n");
		method.appendToBody("return new " + getImplName(vd) + "(" + getImplName(vd) + ".edma_create(value));\n");
		method.addImport(getImplImport(vd));
		intf.addMethod(method);
	}

	private void addBasicIsValidMethod(JavaClass intf)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"isValid" + vd.getName(),
															"Call this method to test if the provided "
																	+ vd.getBasicType()
																	+ " is a valid "
																	+ vd.getName());
		sig.addParameter(vd.getBasicType(), "value", "The " + vd.getBasicType() + " value to be tested");
		sig.setReturnType("boolean");
		sig.setReturnDescription("true if the provided " + vd.getBasicType()
				+ " is a valid " + vd.getName());

		JavaMethod method = new JavaMethod(sig);
		method.setStatic(true);
		if(vd.needValidate())
		{
			method.appendToBody("try\n{\n");
			method.appendToBody("    " + vd.getName() + "Impl.edma_validate(value);\n}\n");
			method.appendToBody("catch(InvalidValueException e)\n{\n    return false;\n}\n");
			method.appendToBody("return true;\n");
			method.addImport(EdmaImport.InvalidValueException);
		}
		else
		{
			method.appendToBody("return true;\n");
		}
		intf.addMethod(method);
	}
}
