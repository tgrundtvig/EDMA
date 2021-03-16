package edma.generator.metamodel.model;

import edma.generator.EdmaImport;
import edma.generator.IPackageStructure;
import edma.generator.common.JavaClass;
import edma.generator.common.JavaInterface;
import edma.generator.common.JavaMethod;
import edma.generator.common.JavaMethodSignature;
import edma.generator.metamodel.AModelGenerator;
import edma.metamodel.IMetaDataModel;
import edma.metamodel.IMetaKind;
import edma.metamodel.IMetaSingleton;

public class ModelViewerGenerator extends AModelGenerator
{
	private JavaInterface intf;
	private JavaClass impl;

	public ModelViewerGenerator(IPackageStructure pkgStruct, IMetaDataModel model)
	{
		super(pkgStruct, model);

		intf = new JavaInterface(	getModelViewName(),
									"This is the viewing interface for the "
											+ model.getName() + " data model.");
		intf.setPackage(getModelViewPackage());
		impl = new JavaClass(getModelImplName());
		impl.setPackage(getModelImplPackage());

		constructor();
		int size = model.getNumberOfSingletons();
		for(int i = 0; i < size; ++i)
		{
			IMetaSingleton singleton = model.getSingleton(i);
			getSingleton(singleton);
		}
		
		size = model.getNumberOfKinds();
		for(int i = 0; i < size; ++i)
		{
			IMetaKind kind = model.getKind(i);
			getKind(kind);
		}

	}

	private void constructor()
	{
		impl.addConstructorField(	"IDataModelView",
									"edma_dmview",
									"The internal runtime interface");
		impl.addImport(EdmaImport.IDataModelView);
	}
	
	private void getKind(IMetaKind kind)
	{
		JavaMethodSignature sig = new JavaMethodSignature("get" + getKindIntfName(kind), "Returns the interface to the kind " + kind.getName());
		sig.setReturnType(getKindIntfName(kind));
		sig.setReturnDescription("The interface to the kind " + kind.getName());
		sig.addImport(getKindIntfImport(kind));
		intf.addMethod(sig);
		JavaMethod m = new JavaMethod(sig);
		// Implementation
		m.appendToBody("return new " + getKindImplName(kind)
				+ "(" + kind.getArrayPosition() + ", edma_dmview);\n");
		m.addImport(getKindImplImport(kind));
		impl.addMethod(m);
	}
	
	private void getSingleton(IMetaSingleton singleton)
	{
		JavaMethodSignature sig = new JavaMethodSignature("get"
				+ singleton.getName(), "Returns the singleton " + singleton.getName()
				+ ".");
		sig.setReturnType(getViewIntfName(singleton));
		sig.addImport(getViewIntfImport(singleton));
		sig.setReturnDescription("The singleton " + singleton.getName() + ".");
		intf.addMethod(sig);
		JavaMethod m = new JavaMethod(sig);
		
		// Implementation
		m.appendToBody("return new " + getImplName(singleton) + "(edma_dmview);\n");
		m.addImport(getImplImport(singleton));
		impl.addMethod(m);
	}

	public JavaInterface getIntf()
	{
		return intf;
	}

	public JavaClass getClazz()
	{
		return impl;
	}

}
