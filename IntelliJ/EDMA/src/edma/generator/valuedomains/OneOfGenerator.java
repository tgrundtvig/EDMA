package edma.generator.valuedomains;

import edma.generator.EdmaImport;
import edma.generator.IPackageStructure;
import edma.generator.common.JavaClass;
import edma.generator.common.JavaMethod;
import edma.generator.common.JavaMethodSignature;
import edma.util.StringUtil;
import edma.valuedomains.IMetaValueDomain;
import edma.valuedomains.IOneOfValueDomain;

class OneOfGenerator extends BaseValueDomainGenerator
{
	private IOneOfValueDomain ovd;

	protected OneOfGenerator(IPackageStructure pkgStruct, IOneOfValueDomain ovd)
	{
		super(pkgStruct, ovd);
		this.ovd = ovd;
		generate();
	}
	
	private void generate()
	{		
		int size = ovd.getNumberOfOptions();
		for(int i = 0; i < size; ++i)
		{
			impl.addImport(getIntfImport(ovd.getOptionDomain(i)));
		}

		impl.addExtends(intf.getName());
		impl.addImplements("IValueInstance");
		impl.addImport(EdmaImport.IValueInstance);
		impl.addField("Object[]", "value");
		impl.addConstructorParameter(	"Object",
										"o",
										"The Object that represents this OneOf value");
		impl.addConstructorBodyLine("value = (Object[]) o;");

		addIsMethods();
		addAsMethods();
		addCreateMethods();
	}

	public JavaClass getIntf()
	{
		return intf;
	}

	public JavaClass getImpl()
	{
		return impl;
	}

	private void addCreateMethods()
	{
		// add newMethods
		int size = ovd.getNumberOfOptions();
		for(int i = 0; i < size; ++i)
		{
			IMetaValueDomain optionVD = ovd.getOptionDomain(i);
			JavaMethodSignature sig = new JavaMethodSignature(	"create",
																"Creates a new "
																		+ ovd.getName()
																		+ " with the internal value domain "
																		+ optionVD.getName());
			String paramName = StringUtil.L(optionVD.getName());
			sig.addParameter(	getIntfName(optionVD),
								paramName,
								"The internal value that this " + ovd.getName()
										+ " will get");
			sig.addImport(getIntfImport(optionVD));
			sig.setReturnType(getIntfName(ovd));
			sig.setReturnDescription("The created value");

			JavaMethod m = new JavaMethod(sig);
			m.setStatic(true);
			m.appendToBody("Object[] edma_pair = new Object[2];\n");
			m.appendToBody("edma_pair[0] = " + i + ";\n");
			m.appendToBody("edma_pair[1] = ((IValueInstance) "
					+ paramName + ").edma_getValue();\n");
			m.appendToBody("return new " + getImplName(ovd) + "(edma_pair);\n");
			m.addImport(getImplImport(ovd));

			m.addImport(EdmaImport.IValueInstance);

			intf.addMethod(m);
		}
	}

	private void addAsMethods()
	{
		int size = ovd.getNumberOfOptions();
		for(int i = 0; i < size; ++i)
		{
			IMetaValueDomain optionVD = ovd.getOptionDomain(i);
			JavaMethodSignature sig = new JavaMethodSignature(	"as" + optionVD.getName(),
																"Returns this value as a value from the value domain "
																		+ optionVD.getName()
																		+ " or throws an UnsupportedOperationException if this value is not from the value domain "
																		+ optionVD.getName());
			sig.setReturnType(getIntfName(optionVD));
			sig.addImport(getIntfImport(optionVD));
			sig.setReturnDescription("This value as a value from the value domain "
					+ optionVD.getName());

			JavaMethod m = new JavaMethod(sig);
			m.appendToBody("Integer index = (Integer) value[0];\n");
			m.appendToBody("if(index.intValue() != " + i
					+ ") throw new UnsupportedOperationException();\n");
			m.appendToBody("return new " + getImplName(optionVD) + "(value[1]);\n");
			m.addImport(getImplImport(optionVD));
			intf.addAbstractMethod(sig);
			impl.addMethod(m);
		}
	}

	private void addIsMethods()
	{
		int size = ovd.getNumberOfOptions();
		for(int i = 0; i < size; ++i)
		{
			IMetaValueDomain optionVD = ovd.getOptionDomain(i);
			JavaMethodSignature sig = new JavaMethodSignature(	"is" + optionVD.getName(),
																"Returns <tt>true</tt> if this value is a value from the value domain "
																		+ optionVD.getName());
			sig.setReturnType("boolean");
			sig.setReturnDescription("<tt>true</tt> if this value is a value from the value domain "
					+ optionVD.getName());

			JavaMethod m = new JavaMethod(sig);
			m.appendToBody("Integer index = (Integer) value[0];\n");
			m.appendToBody("return (index.intValue() == " + i + ");\n");
			intf.addAbstractMethod(sig);
			impl.addMethod(m);
		}
	}
}
