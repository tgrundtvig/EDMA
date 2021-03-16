package org.abstractica.edma.generator.valuedomains;

import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaMethod;
import org.abstractica.edma.generator.common.JavaMethodSignature;
import org.abstractica.edma.valuedomains.IStructField;
import org.abstractica.edma.valuedomains.IStructValueDomain;

class StructGenerator extends BaseValueDomainGenerator
{
	private JavaClass builderImpl;
	private IStructValueDomain svd;

	public StructGenerator(IPackageStructure pkgStruct, IStructValueDomain svd)
	{
		super(pkgStruct, svd);
		this.svd = svd;
		generate();
	}
	
	private void generate()
	{		
		impl.addField("Object[]", "value");
		impl.addConstructorParameter(	"Object",
										"o",
										"The Object that represents this struct value");
		impl.addConstructorBodyLine("value = (Object[]) o;");
		impl.addImplements("IValueInstance");

		int size = svd.getNumberOfFields();
		for(int i = 0; i < size; ++i)
		{
			getFieldMethod(i);
		}

		StructBuilderGenerator sbg = new StructBuilderGenerator(getPackageStructure(), svd);
		sbg.addToValueDomainInterface(intf);
		builderImpl = sbg.getBuilderImpl();
		//impl.addInnerClass(sbg.getBuilderImpl());
	}

	public JavaClass getIntf()
	{
		return intf;
	}

	public JavaClass getImpl()
	{
		return impl;
	}

	public JavaClass getBuilderImpl()
	{
		return builderImpl;
	}

	private void getFieldMethod(int fieldIndex)
	{
		IStructField f = svd.getField(fieldIndex);
		JavaMethodSignature sig = new JavaMethodSignature(	f.getName(),
															"returns the value of the field "
																	+ f.getName());
		sig.setReturnType(getIntfName(f.getValueDomain()));
		sig.addImport(getIntfImport(f.getValueDomain()));
		sig.setReturnDescription("The value of the field " + f.getName());
		JavaMethod method = new JavaMethod(sig);
		if(f.canBeNull()) method.appendToBody("if(value[" + fieldIndex
				+ "] == null) return null;\n");
		method.appendToBody("return new " + getImplName(f.getValueDomain())
				+ "(value[" + fieldIndex + "]);\n");
		method.addImport(getImplImport(f.getValueDomain()));
		impl.addMethod(method);
		intf.addAbstractMethod(sig);
	}
}
