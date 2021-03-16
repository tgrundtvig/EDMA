package edma.generator.metamodel.kind;

import edma.generator.EdmaImport;
import edma.generator.IPackageStructure;
import edma.generator.common.JavaClass;
import edma.generator.common.JavaMethod;
import edma.generator.common.JavaMethodSignature;
import edma.generator.metamodel.AModelGenerator;
import edma.metamodel.IMetaDataModel;
import edma.metamodel.IMetaKind;

public class EntitySetIteratorGenerator extends AModelGenerator
{
	private JavaClass clazz;
	private IMetaKind kind;

	public EntitySetIteratorGenerator(IPackageStructure pkgStruct,
									IMetaDataModel model,
									IMetaKind kind)
	{
		super(pkgStruct, model);
		this.kind = kind;

		clazz = new JavaClass(getSetIteratorImplName(kind));
		clazz.addImplements("Iterator<" + getViewIntfName(kind) + ">");
		clazz.addImport("java.util.Iterator");
		clazz.addImport(getViewIntfImport(kind));
		clazz.addConstructorField(	"Iterator<IEntity>",
									"edma_iterator",
									"The internal iterator");
		clazz.addImport(EdmaImport.IEntity);
		clazz.setDescription("Inner class that defines the iterator");

		hasNext();
		next();
		remove();
	}

	private void hasNext()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"hasNext",
															"Returns <tt>true</tt> if this iterator has more elements");
		sig.setReturnType("boolean");
		sig.setReturnDescription("<tt>true</tt> if this iterator has more elements");

		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return edma_iterator.hasNext();\n");
		clazz.addMethod(m);
	}

	private void next()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"next",
															"Returns the next element from this iterator");
		sig.setReturnType(getViewIntfName(kind));
		sig.setReturnDescription("the next element from this iterator");

		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("IEntity entity = edma_iterator.next();\n");
		m.appendToBody("return new " + getImplName(kind)
				+ "(entity, edma_dmview);\n");
		clazz.addMethod(m);
	}

	private void remove()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"remove",
															"Not supported as this is a read-only iterator");
		sig.setReturnType("void");

		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("throw new UnsupportedOperationException();\n");
		clazz.addMethod(m);
	}

	public JavaClass getClazz()
	{
		return clazz;
	}
}
