package edma.generator.metamodel.singleton;

import edma.generator.EdmaImport;
import edma.generator.IPackageStructure;
import edma.generator.common.JavaClass;
import edma.generator.common.JavaInterface;
import edma.generator.common.JavaMethod;
import edma.generator.common.JavaMethodSignature;
import edma.generator.metamodel.AModelGenerator;
import edma.metamodel.IMetaAttribute;
import edma.metamodel.IMetaDataModel;
import edma.metamodel.IMetaSingleton;
import edma.util.StringUtil;

/**
 * This class generates the Viewer class and interface for any given kind. The
 * following methods should be generated: Kind snapshot(); KindID getID();
 * foreach Relation r follow relation (different names depending on cardinality)
 * 
 */

public class SingletonViewerGenerator extends AModelGenerator
{
	private JavaInterface intf;
	private JavaClass clazz;
	private IMetaSingleton singleton;

	public SingletonViewerGenerator(IPackageStructure pkgStruct, IMetaDataModel model, IMetaSingleton singleton)
	{
		super(pkgStruct, model);
		this.singleton = singleton;

		intf = new JavaInterface(	getViewIntfName(singleton),
									"This is the viewing interface for "
											+ singleton.getName());
		intf.setPackage(getViewIntfPackage(singleton));
		clazz = new JavaClass(getImplName(singleton));
		clazz.setPackage(getImplPackage(singleton));
		clazz.addConstructorField(	"IDataModelView",
									"edma_dmview",
									"Internal runtime interface");
		clazz.addImport(EdmaImport.IDataModelView);

		snapshot();
		int size = singleton.getNumberOfAttributes();
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = singleton.getAttribute(i);
			getAttribute(att);
		}
	}

	private void snapshot()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"snapshot",
															"Create a copy of this "
																	+ singleton.getName()
																	+ " at this instance in time");
		sig.setReturnType(getIntfName(singleton.getValueDomain()));
		sig.setReturnDescription("A copy of this " + singleton.getName());
		sig.addImport(getIntfImport(singleton.getValueDomain()));
		intf.addMethod(sig);
		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		int size = singleton.getNumberOfAttributes();
		m.appendToBody("Object[] res = new Object[" + size + "];\n");
		for(int i = 0; i < size; ++i)
			m.appendToBody("res[" + i
					+ "] = edma_dmview.getSingletonAttribute("
					+ singleton.getArrayPosition() + ", " + i + ");\n");
		m.appendToBody("return new " + getImplName(singleton.getValueDomain()) + "(res);\n");
		m.addImport(getImplImport(singleton.getValueDomain()));
		clazz.addMethod(m);
	}

	private void getAttribute(IMetaAttribute att)
	{
		JavaMethodSignature sig = new JavaMethodSignature("get"
				+ StringUtil.U(att.getName()), "Returns the attribute "
				+ att.getName() + " of this " + singleton.getName());
		sig.setReturnType(getIntfName(att.getValueDomain()));
		sig.setReturnDescription("The value of the attribute " + att.getName());
		sig.addImport(getIntfImport(att.getValueDomain()));
		intf.addMethod(sig);
		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return new " + getImplName(att.getValueDomain())
				+ "(edma_dmview.getSingletonAttribute("
				+ singleton.getArrayPosition() + ", " + att.getArrayPosition()
				+ "));\n");
		m.addImport(getImplImport(att.getValueDomain()));
		clazz.addMethod(m);
	}

	public JavaInterface getInterface()
	{
		return intf;
	}

	public JavaClass getClazz()
	{
		return clazz;
	}
}
