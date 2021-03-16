package edma.generator.valuedomains;

import edma.generator.AGenerator;
import edma.generator.EdmaImport;
import edma.generator.IPackageStructure;
import edma.generator.common.JavaClass;
import edma.generator.common.JavaMethod;
import edma.generator.common.JavaMethodSignature;
import edma.util.StringUtil;
import edma.valuedomains.IMetaValueDomain;

public abstract class BaseValueDomainGenerator extends AGenerator
{
	protected JavaClass intf;
	protected JavaClass impl;
	protected IMetaValueDomain vd;

	public BaseValueDomainGenerator(IPackageStructure pkgStruct,
									IMetaValueDomain vd)
	{
		super(pkgStruct);
		this.vd = vd;
		intf = new JavaClass(getIntfName(vd));
		intf.setAbstract();
		intf.setPackage(getIntfPackage(vd));
		intf.setDescription("The representation of a value from the value domain: "
				+ vd.getName());
		intf.noConstructor();
		intf.addField(	"protected",
						true,
						true,
						"IMetaValueDomain",
						"edma_domain",
						getValueDomainFromIndexCode(vd.getIndex()));
		intf.addImport(EdmaImport.IMetaValueDomain);
		intf.addImport(getEnvironmentImport());
		impl = new JavaClass(getImplName(vd));
		impl.setPackage(getImplPackage(vd));
		impl.addImport(getIntfImport(vd));
		impl.addExtends(intf.getName());
		impl.setDescription("The implementation of " + vd.getName());

		impl.addImplements("IValueInstance");

		addFromTerminal();
		addGetValueDomain();
		addValidate();
		addCreate();
		addGetValue();
		addEquals();
		addHashCode();
		addToString();
		addFromString();
		addCompareTo();
		addToStream();
		addFromStream();
		addFromStreamNoValidate();
	}

	public JavaClass getIntf()
	{
		return intf;
	}

	public JavaClass getImpl()
	{
		return impl;
	}

	private void addGetValueDomain()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"edma_getDomain",
															"Gets the value domain for this value instance");
		sig.setReturnType("IMetaValueDomain");
		sig.setReturnDescription("The value domain for this value instance");
		// intf.addAbstractMethod(sig);
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return edma_domain;\n");
		m.addImport(EdmaImport.IMetaValueDomain);
		impl.addMethod(m);
	}

	private void addValidate()
	{
		JavaMethodSignature validateSig = new JavaMethodSignature(	"edma_validate",
																	"Check if a value is valid");
		validateSig.addParameter("Object", "value", "The value to validate");
		validateSig.setReturnType("void");
		validateSig.addException("InvalidValueException");
		validateSig.addImport(EdmaImport.InvalidValueException);
		JavaMethod validate = new JavaMethod(validateSig);
		validate.appendToBody("edma_domain.validate(value, "
				+ getExternalConstraintsClassName() + ".instance);\n");
		validate.addImport(EdmaImport.IValueInstance);
		validate.addImport(getExternalConstraintsImport());
		validate.setStatic(true);
		impl.addMethod(validate);
	}

	private void addCreate()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"edma_create",
															"create value without checking");
		sig.addParameter("Object", "value", "The value to check and create");
		sig.setReturnType("Object");
		sig.setReturnDescription("<tt>true</tt> The new created value");
		JavaMethod res = new JavaMethod(sig);
		res.appendToBody("return "
				+ getValueStoreCreateCode("value", "edma_domain") + ";\n");
		res.addImport(EdmaImport.IValueInstance);
		res.setStatic(true);
		impl.addMethod(res);
	}

	private void addGetValue()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"edma_getValue",
															"Access to the general Object value");
		sig.setReturnType("Object");
		sig.setReturnDescription("The value as a general Object");
		JavaMethod res = new JavaMethod(sig);
		res.appendToBody("return value;");
		res.addImport(EdmaImport.IValueInstance);
		impl.addMethod(res);
	}

	private void addHashCode()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"hashCode",
															"Gets the value hash for this value instance");
		sig.setReturnType("int");
		sig.setReturnDescription("The hash for this value instance");
		JavaMethod res = new JavaMethod(sig);
		if(vd.getBasicType() != null)
		{
			res.appendToBody("return value.hashCode();\n");
		}
		else
		{
			impl.addField("int", "edma_hash");
			impl.addConstructorBodyLine("edma_hash = 0;");
			res.appendToBody("if(edma_hash == 0) edma_hash = edma_domain.valueHashCode(value);\n");
			res.appendToBody("return edma_hash;\n");
		}
		impl.addMethod(res);
	}
	
	private void addFromTerminal()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"fromTerminal",
															"Get a value from a terminal");
		sig.addParameter("ITerminal", "terminal", "The terminal to get the value from");
		sig.setReturnType(getIntfName(vd));
		sig.setReturnDescription("The " + vd.getName()
				+ " from the terminal");
		sig.addImport(EdmaImport.IMetaValueDomain);
		sig.addImport(EdmaImport.ITerminal);
		JavaMethod method = new JavaMethod(sig);
		method.appendToBody("ValueDomainInput vdi = new ValueDomainInput(");
		method.appendToBody("terminal, EDMA_ExternalConstraints.instance);\n");
		method.appendToBody("return new " + getImplName(vd) + "(vdi.getValue(edma_domain));\n");
		method.setStatic(true);
		method.addImport(EdmaImport.ValueDomainInput);
		intf.addMethod(method);
	}

	private void addToString()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"toString",
															"Returns this value instance as a string");
		sig.setReturnType("String");
		sig.setReturnDescription("this value instance as a string");
		JavaMethod res = new JavaMethod(sig);
		res.appendToBody("return edma_domain.valueToString(value);\n");
		impl.addMethod(res);
	}
	
	private void addFromString()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"fromString",
															"Get a value from its string representation");
		sig.addParameter("String", "s", "The String to parse");
		sig.setReturnType(getIntfName(vd));
		sig.setReturnDescription("The " + vd.getName()
				+ " from the string representation");
		sig.addException("InvalidValueException");
		sig.addImport(EdmaImport.InvalidValueException);
		JavaMethod method = new JavaMethod(sig);
		method.appendToBody("Object res = edma_domain.valueFromString(s, "
				+ getExternalConstraintsClassName() + ".instance);\n");
		method.appendToBody("return new " + getImplName(vd) + "(res);\n");
		method.addImport(getExternalConstraintsImport());
		method.addImport(getImplImport(vd));
		method.setStatic(true);
		intf.addMethod(method);
	}

	private void addToStream()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"toStream",
															"Writes this value to a stream");
		sig.setReturnType("void");
		sig.addParameter("DataOutput", "out", "Interface to data output stream");
		sig.addException("IOException");
		sig.addImport("java.io.DataOutput");
		sig.addImport("java.io.IOException");
		JavaMethod res = new JavaMethod(sig);
		res.appendToBody("edma_domain.writeValue(((IValueInstance) this).edma_getValue(), out);\n");
		res.addImport(EdmaImport.IValueInstance);
		intf.addAbstractMethod(sig);
		impl.addMethod(res);
	}

	private void addFromStream()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"fromStream",
															"Reads and validates a value from a stream");
		sig.addParameter(	"DataInput",
							"in",
							"A data input interface for the stream to read from");
		sig.addImport("java.io.DataInput");
		sig.setReturnType(getIntfName(vd));
		sig.setReturnDescription("The " + vd.getName()
				+ " read from the stream");
		sig.addException("InvalidValueException");
		sig.addImport(EdmaImport.InvalidValueException);
		sig.addException("IOException");
		sig.addImport("java.io.IOException");
		JavaMethod method = new JavaMethod(sig);
		// Object readValue(DataInput in, IExternalConstraints external)
		method.appendToBody("Object res = edma_domain.readValue(in, "
				+ getExternalConstraintsClassName() + ".instance);\n");
		method.appendToBody("return new " + getImplName(vd) + "(res);\n");
		method.addImport(getExternalConstraintsImport());
		method.addImport(getImplImport(vd));
		method.setStatic(true);
		intf.addMethod(method);
	}

	private void addFromStreamNoValidate()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"fromStreamNoValidate",
															"Reads a value from a stream without validating the value");
		sig.addParameter(	"DataInput",
							"in",
							"A data input interface for the stream to read from");
		sig.addImport("java.io.DataInput");
		sig.setReturnType(getIntfName(vd));
		sig.setReturnDescription("The " + vd.getName()
				+ " read from the stream");
		sig.addException("IOException");
		sig.addImport("java.io.IOException");
		JavaMethod method = new JavaMethod(sig);
		// Object readValue(DataInput in, IExternalConstraints external)
		method.appendToBody("Object res = edma_domain.readValueNoValidate(in);\n");
		method.appendToBody("return new " + getImplName(vd) + "(res);\n");
		method.addImport(getExternalConstraintsImport());
		method.addImport(getImplImport(vd));
		method.setStatic(true);
		intf.addMethod(method);
	}

	private void addEquals()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"equals",
															"Returns <tt>true</tt> if this value equals the given value");
		sig.setReturnType("boolean");
		sig.setReturnDescription("<tt>true</tt> if this value equals the given value");
		sig.addParameter("Object", "o", "Object to test equality with");
		JavaMethod res = new JavaMethod(sig);
		res.appendToBody("if(!(o instanceof IValueInstance)) return false;\n");
		res.appendToBody("IValueInstance inst = (IValueInstance) o;\n");
		res.appendToBody("if(" + vd.getIndex()
				+ " != inst.edma_getDomain().getIndex()) return false;\n");
		res.appendToBody("return edma_domain.valueEqual(value, inst.edma_getValue());\n");
		impl.addMethod(res);
	}

	private void addCompareTo()
	{
		intf.addImplements("Comparable<" + getIntfName(vd) + ">");
		JavaMethodSignature sig = new JavaMethodSignature(	"compareTo",
															"Compare this "
																	+ vd.getName()
																	+ " to another "
																	+ vd.getName());
		sig.setReturnType("int");
		sig.setReturnDescription("A negative integer, zero, or a positive integer as this "
				+ vd.getName()
				+ " is less than, equal to, or greater than the specified "
				+ vd.getName());
		sig.addParameter(getIntfName(vd), StringUtil.L(vd.getName()), "The "
				+ vd.getName() + " to compare with");
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return edma_domain.valueCompare(value, (("
				+ getImplName(vd) + ") " + StringUtil.L(vd.getName())
				+ ").value);\n");
		impl.addMethod(m);
	}
}
