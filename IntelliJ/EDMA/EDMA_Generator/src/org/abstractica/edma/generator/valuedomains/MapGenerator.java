package org.abstractica.edma.generator.valuedomains;

import org.abstractica.edma.generator.EdmaImport;
import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaInterface;
import org.abstractica.edma.generator.common.JavaMethod;
import org.abstractica.edma.generator.common.JavaMethodSignature;
import org.abstractica.edma.valuedomains.IMapValueDomain;

class MapGenerator extends BaseValueDomainGenerator
{
	private JavaClass builderImpl;
	private IMapValueDomain mvd;

	protected MapGenerator(IPackageStructure pkgStruct, IMapValueDomain mvd)
	{
		super(pkgStruct, mvd);
		this.mvd = mvd;
		generate();
	}
	
	private void generate()
	{
		intf.addImplements("Iterable<" + getIntfName(mvd) + "." + mvd.getName()
				+ "Entry>");
		impl.addImport("java.util.Iterator");
		impl.addImport(getIntfImport(mvd.getKeyDomain()));
		impl.addImport(getIntfImport(mvd.getValueDomain()));
		impl.addField("Object[]", "value");
		impl.addConstructorParameter(	"Object",
										"o",
										"The Object that represents this map value");
		impl.addConstructorBodyLine("value = (Object[]) o;");

		iteratorMethod();
		sizeMethod();
		getFromIndexMethod();
		getFromKeyMethod();
		getArrayFromKeyMethod();
		iteratorInnerClass();
		entryInnerClass();
		MapBuilderGenerator mbg = new MapBuilderGenerator(getPackageStructure(), mvd);
		mbg.addToValueDomainInterface(intf);
		builderImpl = mbg.getBuilderImpl();
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
		JavaMethodSignature sig = new JavaMethodSignature(	"iterator",
															"Returns an iterator for this list");
		sig.setReturnType("Iterator<" + mvd.getName() + "Entry>");
		sig.setReturnDescription("an iterator for this map");
		JavaMethod method = new JavaMethod(sig);
		method.appendToBody("return new " + mvd.getName() + "Iterator();\n");
		impl.addMethod(method);
	}

	private void sizeMethod()
	{
		// Add size() method
		JavaMethodSignature sig = new JavaMethodSignature(	"size",
															"Returns the size of this list");
		sig.setReturnType("int");
		sig.setReturnDescription("the size of this list");
		JavaMethod method = new JavaMethod(sig);
		method.appendToBody("return value.length;\n");
		intf.addAbstractMethod(sig);
		impl.addMethod(method);
	}

	private void getFromIndexMethod()
	{
		// Add get(index) method
		JavaMethodSignature sig = new JavaMethodSignature(	"getFromIndex",
															"Returns the entry on a given index in this map");
		sig.addParameter(	"int",
							"index",
							"The index of the entry to be returned");
		sig.setReturnType(mvd.getName() + "Entry");
		sig.setReturnDescription("the entry on the given index in this list");

		JavaMethod method = new JavaMethod(sig);
		method.appendToBody("return new " + mvd.getName()
				+ "EntryImpl(value[index]);\n");
		intf.addAbstractMethod(sig);
		impl.addMethod(method);
	}

	private void getFromKeyMethod()
	{
		// Add get(index) method
		JavaMethodSignature sig = new JavaMethodSignature(	"getValue",
																	"Returns the value of the first entry with the given key or null if there is no entry with the given key");
		sig.addParameter(	getIntfName(mvd.getKeyDomain()),
									"key",
									"The key of the entry to be returned");
		sig.addImport(getIntfImport(mvd.getKeyDomain()));
		sig.setReturnType(getIntfName(mvd.getValueDomain()));
		sig.addImport(getIntfImport(mvd.getValueDomain()));
		sig.setReturnDescription("The value of the first entry with the given key or null if there is no entry with the given key");

		JavaMethod method = new JavaMethod(sig);
		method.appendToBody("IMetaValueDomain keyDomain = ((IValueInstance) key).edma_getDomain();\n");
		method.appendToBody("Object keyObj = ((IValueInstance) key).edma_getValue();\n");

		method.appendToBody("for(Object obj : value)\n{\n");
		method.appendToBody("    Object[] entry = (Object[]) obj;\n");
		method.appendToBody("    if(keyDomain.valueEqual(entry[0], keyObj))\n    {\n");
		method.appendToBody("        return new "
				+ getImplName(mvd.getValueDomain()) + "(entry[1]);\n    }\n}\n");
		method.appendToBody("return null;\n");
		method.addImport(getImplImport(mvd.getValueDomain()));
		method.addImport(EdmaImport.IMetaValueDomain);

		intf.addAbstractMethod(sig);
		impl.addMethod(method);
	}

	private void getArrayFromKeyMethod()
	{
		// Add get(index) method
		JavaMethodSignature sig = new JavaMethodSignature(	"getValues",
																	"Returns an array with all the values of the entries with the given key");
		sig.addParameter(	getIntfName(mvd.getKeyDomain()),
									"key",
									"The key of the entry to be returned");
		sig.setReturnType(getIntfName(mvd.getValueDomain()) + "[]");
		sig.setReturnDescription("An array with all the values of the entries with the given key");

		JavaMethod method = new JavaMethod(sig);
		method.appendToBody("IMetaValueDomain keyDomain = ((IValueInstance) key).edma_getDomain();\n");
		method.appendToBody("ArrayList<" + getIntfName(mvd.getValueDomain())
				+ "> res = new ArrayList<" + getIntfName(mvd.getValueDomain())
				+ ">();\n");
		method.appendToBody("Object keyObj = ((IValueInstance) key).edma_getValue();\n");

		method.appendToBody("for(Object obj : value)\n{\n");
		method.appendToBody("    Object[] entry = (Object[]) obj;\n");
		method.appendToBody("    if(keyDomain.valueEqual(entry[0], keyObj))\n    {\n");
		method.appendToBody("        res.add(new "
				+ getImplName(mvd.getValueDomain())
				+ "(entry[1]));\n    }\n}\n");
		method.appendToBody("int size = res.size();\n");
		method.appendToBody(getIntfName(mvd.getValueDomain())
				+ "[] array = new " + getIntfName(mvd.getValueDomain())
				+ "[size];\n");
		method.appendToBody("for(int i = 0; i < size; ++i)\n{\n");
		method.appendToBody("    array[i] = res.get(i);\n}\n");
		method.appendToBody("return array;\n");
		method.addImport(EdmaImport.IMetaValueDomain);
		method.addImport("java.util.ArrayList");

		intf.addAbstractMethod(sig);
		impl.addMethod(method);
	}

	private void entryInnerClass()
	{
		JavaInterface entryIntf = new JavaInterface(mvd.getName() + "Entry",
													"An entry in the " + mvd.getName()
															+ " map");
		JavaClass entryClass = new JavaClass(mvd.getName() + "EntryImpl");
		entryClass.addImplements(entryIntf.getName());
		entryClass.addField("Object[]", "entry");
		entryClass.addConstructorParameter("Object", "entry", "entry value");
		entryClass.addConstructorBodyLine("this.entry = (Object[]) entry;");
		// get key method
		JavaMethodSignature getKeySig = new JavaMethodSignature("getKey",
																"Returns the key of this map entry");
		getKeySig.setReturnType(getIntfName(mvd.getKeyDomain()));
		getKeySig.addImport(getIntfImport(mvd.getKeyDomain()));
		getKeySig.setReturnDescription("The key of this map entry");
		JavaMethod getKeyMethod = new JavaMethod(getKeySig);
		getKeyMethod.appendToBody("return new " + getImplName(mvd.getKeyDomain())
				+ "(entry[0]);\n");
		getKeyMethod.addImport(getImplImport(mvd.getKeyDomain()));
		entryIntf.addMethod(getKeySig);
		entryClass.addMethod(getKeyMethod);
		// get value method
		JavaMethodSignature getValueSig = new JavaMethodSignature(	"getValue",
																	"Returns the value of this map entry");
		getValueSig.setReturnType(getIntfName(mvd.getValueDomain()));
		getValueSig.addImport(getIntfImport(mvd.getValueDomain()));
		getValueSig.setReturnDescription("The value of this map entry");
		JavaMethod getValueMethod = new JavaMethod(getValueSig);
		getValueMethod.appendToBody("return new "
				+ getImplName(mvd.getValueDomain()) + "(entry[1]);\n");
		getValueMethod.addImport(getImplImport(mvd.getValueDomain()));
		entryIntf.addMethod(getValueSig);
		entryClass.addMethod(getValueMethod);
		intf.addInnerInterface(entryIntf);
		impl.addInnerClass(entryClass);
	}

	private void iteratorInnerClass()
	{
		// Add iterator as inner class
		JavaClass iteratorClass = new JavaClass(mvd.getName() + "Iterator");
		iteratorClass.addField("int", "pos");
		iteratorClass.addConstructorBodyLine("pos = 0;\n");
		iteratorClass.addImplements("Iterator<" + mvd.getName() + "Entry>");
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
		nextSig.setReturnType(mvd.getName() + "Entry");
		nextSig.setReturnDescription("the next element in this iterator");
		JavaMethod next = new JavaMethod(nextSig);
		next.appendToBody("return new " + mvd.getName() + "EntryImpl(value[pos++]);\n");
		iteratorClass.addMethod(next);

		// remove()
		JavaMethodSignature removeSig = new JavaMethodSignature("remove",
																"This operation is not supported, because the map is immutable");
		removeSig.setReturnType("void");
		JavaMethod remove = new JavaMethod(removeSig);
		remove.appendToBody("throw new UnsupportedOperationException();\n");
		remove.addImport("java.lang.UnsupportedOperationException");
		iteratorClass.addMethod(remove);

		impl.addInnerClass(iteratorClass);
	}
}
