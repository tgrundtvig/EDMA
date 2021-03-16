package edma.generator.valuedomains;

import edma.generator.IPackageStructure;
import edma.generator.common.JavaClass;
import edma.generator.common.JavaMethod;
import edma.generator.common.JavaMethodSignature;
import edma.valuedomains.IListValueDomain;

class ListGenerator extends BaseValueDomainGenerator
{
	private JavaClass builderImpl;
	private IListValueDomain lvd;

	protected ListGenerator(IPackageStructure pkgStruct, IListValueDomain lvd)
	{
		super(pkgStruct, lvd);
		this.lvd = lvd;
		generate();
	}
	
	private void generate()
	{
		intf.addImplements("Iterable<" + getIntfName(lvd.getElementValueDomain()) + ">");
		impl.addImport("java.util.Iterator");
		impl.addImport(getIntfImport(lvd.getElementValueDomain()));
		impl.addField("Object[]", "value");
		impl.addConstructorParameter(	"Object",
										"o",
										"The Object that represents this list value");
		impl.addConstructorBodyLine("value = (Object[]) o;");
		iteratorMethod();
		sizeMethod();
		getMethod();
		iteratorInnerClass();
		ListBuilderGenerator lbg = new ListBuilderGenerator(getPackageStructure(), lvd);
		lbg.addToValueDomainInterface(intf);
		builderImpl = lbg.getBuilderImpl();
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

	private void iteratorMethod()
	{
		// Add iterator() method
		JavaMethodSignature sig = new JavaMethodSignature("iterator",
																		"Returns an iterator for this list");
		sig.setReturnType("Iterator<"
				+ getIntfName(lvd.getElementValueDomain()) + ">");
		sig.setReturnDescription("an iterator for this list");
		JavaMethod method = new JavaMethod(sig);
		method.appendToBody("return new " + lvd.getName() + "Iterator();\n");
		impl.addMethod(method);
	}

	private void sizeMethod()
	{
		// Add size() method
		JavaMethodSignature sig = new JavaMethodSignature("size",
																	"Returns the size of this list");
		sig.setReturnType("int");
		sig.setReturnDescription("the size of this list");
		JavaMethod method = new JavaMethod(sig);
		method.appendToBody("return value.length;\n");
		intf.addAbstractMethod(sig);
		impl.addMethod(method);
	}

	private void getMethod()
	{
		// Add get(index) method
		JavaMethodSignature sig = new JavaMethodSignature(	"get",
																	"Returns the element on a given index in this list");
		sig.addParameter(	"int",
									"index",
									"The index of the element to be returned");
		sig.setReturnType(getIntfName(lvd.getElementValueDomain()));
		sig.setReturnDescription("the element on the given index in this list");

		JavaMethod method = new JavaMethod(sig);
		method.appendToBody("return new " + getImplName(lvd.getElementValueDomain())
				+ "(value[index]);\n");
		intf.addAbstractMethod(sig);
		impl.addMethod(method);
	}

	private void iteratorInnerClass()
	{
		// Add iterator as inner class
		JavaClass iteratorClass = new JavaClass(lvd.getName() + "Iterator");
		iteratorClass.addField("int", "pos");
		iteratorClass.addConstructorBodyLine("pos = 0;\n");
		iteratorClass.addImplements("Iterator<"
				+ getIntfName(lvd.getElementValueDomain()) + ">");
		// hasNext()
		JavaMethodSignature hasNextSig = new JavaMethodSignature(	"hasNext",
																	"Returns true if there are more elements in this iterator");
		hasNextSig.setReturnType("boolean");
		hasNextSig.setReturnDescription("true if there are more elements in this iterator");
		JavaMethod hasNext = new JavaMethod(hasNextSig);
		hasNext.appendToBody("return pos < value.length;\n");
		iteratorClass.addMethod(hasNext);
		// next()
		JavaMethodSignature nextSig = new JavaMethodSignature(	"next",
																"returns the next element from this iterator");
		nextSig.setReturnType(getIntfName(lvd.getElementValueDomain()));
		nextSig.setReturnDescription("the next element in this iterator");
		JavaMethod next = new JavaMethod(nextSig);
		next.appendToBody("return new " + getImplName(lvd.getElementValueDomain())
				+ "(value[pos++]);\n");
		iteratorClass.addMethod(next);

		// remove()
		JavaMethodSignature removeSig = new JavaMethodSignature("remove",
																"This operation is not supported, because the list is immutable");
		removeSig.setReturnType("void");
		JavaMethod remove = new JavaMethod(removeSig);
		remove.appendToBody("throw new UnsupportedOperationException();\n");
		remove.addImport("java.lang.UnsupportedOperationException");
		iteratorClass.addMethod(remove);

		impl.addInnerClass(iteratorClass);
	}

	
}
