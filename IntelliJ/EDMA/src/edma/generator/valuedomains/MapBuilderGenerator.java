package edma.generator.valuedomains;

import edma.generator.AGenerator;
import edma.generator.EdmaImport;
import edma.generator.IPackageStructure;
import edma.generator.common.JavaClass;
import edma.generator.common.JavaInterface;
import edma.generator.common.JavaMethod;
import edma.generator.common.JavaMethodSignature;
import edma.valuedomains.IMapValueDomain;
import edma.valuedomains.IMetaValueDomain;

class MapBuilderGenerator extends AGenerator
{
	private IMapValueDomain vd;
	private JavaClass builderImpl;
	private JavaInterface builderIntf;
	private JavaMethodSignature newValueSig;
	private JavaMethod newValueMethod;

	public MapBuilderGenerator(IPackageStructure pkgStruct, IMapValueDomain vd)
	{
		super(pkgStruct);
		this.vd = vd;
		newValueSig = new JavaMethodSignature(	"begin",
												"Starts creation of a new "
														+ vd.getName());
		newValueSig.setReturnType(getBuilderIntfName(vd));
		newValueSig.setReturnDescription("Builder interface to build the map");

		newValueMethod = new JavaMethod(newValueSig);
		newValueMethod.appendToBody("return new " + getBuilderImplName(vd)
				+ "();\n");
		newValueMethod.setStatic(true);
		newValueMethod.addImport(getBuilderImplImport(vd));

		builderIntf = new JavaInterface(getBuilderIntfName(vd),
										"Interface to create a map");

		builderImpl = new JavaClass(getBuilderImplName(vd));
		builderImpl.setPackage(getBuilderImplPackage(vd));
		builderImpl.addField("ArrayList<Object>", "entryList");
		builderImpl.addConstructorBodyLine("entryList = new ArrayList<Object>();\n");
		builderImpl.addImplements(getBuilderIntfName(vd));
		builderImpl.addImport("java.util.ArrayList");

		generateAddMethod();
		generateNativeAddMethod();
		generateBuildMethod();
	}

	public void addToValueDomainInterface(JavaClass intf)
	{
		intf.addMethod(newValueMethod);
		intf.addInnerInterface(builderIntf);
	}

	public JavaClass getBuilderImpl()
	{
		return builderImpl;
	}

	private void generateAddMethod()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"addEntry",
															"Adds an entry to the map");
		sig.setReturnType(getBuilderIntfName(vd));
		sig.setReturnDescription("An interface to the builder for chaining method calls");
		sig.addParameter(	getIntfName(vd.getKeyDomain()),
							"key",
							"The key of the entry to be added to the map");
		sig.addParameter(	getIntfName(vd.getValueDomain()),
							"value",
							"The value of the entry to be added to the map");
		builderIntf.addMethod(sig);
		JavaMethod method = new JavaMethod(sig);
		method.appendToBody("if(key == null) throw new NullPointerException();\n");
		method.appendToBody("if(value == null) throw new NullPointerException();\n");
		method.appendToBody("Object[] entry = new Object[2];\n");
		method.appendToBody("entry[0] = ((IValueInstance) key).edma_getValue();\n");
		method.appendToBody("entry[1] = ((IValueInstance) value).edma_getValue();\n");
		method.appendToBody("entryList.add(entry);\n");
		method.appendToBody("return this;\n");
		method.addImport(EdmaImport.IValueInstance);
		method.addImport(getIntfImport(vd.getKeyDomain()));
		method.addImport(getIntfImport(vd.getValueDomain()));
		method.addImport(getBuilderIntfImport(vd));
		builderImpl.addMethod(method);
	}

	private void generateNativeAddMethod()
	{
		IMetaValueDomain keyDomain = vd.getKeyDomain();
		IMetaValueDomain valDomain = vd.getValueDomain();
		if(keyDomain.getBasicType() != null || valDomain.getBasicType() != null) return;
		JavaMethodSignature sig = new JavaMethodSignature(	"addEntry",
															"Adds an entry to the map");
		sig.setReturnType(getBuilderIntfName(valDomain));
		sig.setReturnDescription("An interface to the builder for chaining method calls");

		if(keyDomain.getBasicType() != null)
		{
			sig.addParameter(	keyDomain.getBasicType(),
								"key",
								"The key of the entry to be added to the map");
		}
		else
		{
			sig.addParameter(	getIntfName(keyDomain),
								"key",
								"The key of the entry to be added to the map");
		}

		if(valDomain.getBasicType() != null)
		{
			sig.addParameter(	valDomain.getBasicType(),
								"value",
								"The value of the entry to be added to the map");
		}
		else
		{
			sig.addParameter(	getIntfName(valDomain),
								"value",
								"The value of the entry to be added to the map");
		}

		JavaMethod method = new JavaMethod(sig);
		method.appendToBody("if(key == null) throw new NullPointerException();\n");
		method.appendToBody("if(value == null) throw new NullPointerException();\n");
		method.appendToBody("Object[] entry = new Object[2];\n");
		if(keyDomain.getBasicType() != null)
		{
			if(keyDomain.needValidate())
			{
				sig.addException("InvalidValueException");
				sig.addImport(EdmaImport.InvalidValueException);
				method.appendToBody(getImplName(keyDomain)
						+ ".edma_validate(key);\n");
			}
			method.appendToBody("entry[0] = " + getImplName(keyDomain)
					+ ".edma_create(key);\n");
		}
		else
		{
			method.appendToBody("entry[0] = ((IValueInstance) key).edma_getValue();\n");
		}
		if(valDomain.getBasicType() != null)
		{
			if(valDomain.needValidate())
			{
				sig.addException("InvalidValueException");
				sig.addImport(EdmaImport.InvalidValueException);
				method.appendToBody(getImplName(valDomain)
						+ ".edma_validate(value);\n");
			}
			method.appendToBody("entry[1] = " + getImplName(valDomain)
					+ ".edma_create(value);\n");
		}
		else
		{
			method.appendToBody("entry[1] = ((IValueInstance) value).edma_getValue();\n");
		}
		method.appendToBody("entryList.add(entry);\n");
		method.appendToBody("return this;\n");

		method.addImport(EdmaImport.IValueInstance);
		builderIntf.addMethod(sig);
		builderImpl.addMethod(method);

	}

	private void generateBuildMethod()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"end",
															"Builds the list with the added elements");
		sig.setReturnType(getIntfName(vd));
		sig.setReturnDescription("The builded list");
		builderIntf.addMethod(sig);
		JavaMethod method = new JavaMethod(sig);
		method.addImport(getIntfImport(vd));
		method.appendToBody("Object[] res = new Object[entryList.size()];\n");
		method.appendToBody("int pos = 0;\n");
		method.appendToBody("for(Object o : entryList)\n");
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
		builderImpl.addMethod(method);
	}
}
