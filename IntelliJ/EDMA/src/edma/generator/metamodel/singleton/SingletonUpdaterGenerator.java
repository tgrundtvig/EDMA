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

public class SingletonUpdaterGenerator extends AModelGenerator
{
	private JavaInterface intf;
	private JavaClass clazz;
	private IMetaSingleton singleton;

	public SingletonUpdaterGenerator(	IPackageStructure pkgStruct,
										IMetaDataModel model,
										IMetaSingleton singleton,
										JavaClass impl)
	{
		super(pkgStruct, model);
		this.singleton = singleton;

		intf = new JavaInterface(	getUpdateIntfName(singleton),
									"This is the update interface for "
											+ singleton.getName());
		intf.addExtends(getViewIntfName(singleton));
		intf.addImport(getViewIntfImport(singleton));
		intf.setPackage(getUpdateIntfPackage(singleton));
		
		clazz = impl;
		clazz.addImplements(getUpdateIntfName(singleton));
		clazz.addImport(getUpdateIntfImport(singleton));

		int size = singleton.getNumberOfAttributes();
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = singleton.getAttribute(i);
			setAttribute(att);
		}
	}

	private void setAttribute(IMetaAttribute att)
	{
		if(!att.isModifiable()) return;
		JavaMethodSignature sig = new JavaMethodSignature("set"
				+ StringUtil.U(att.getName()), "Sets the attribute "
				+ att.getName() + " of this " + singleton.getName());
		sig.setReturnType("void");
		sig.addParameter(	getIntfName(att.getValueDomain()),
							att.getName(),
							"The value to set");
		sig.addImport(getIntfImport(att.getValueDomain()));
		intf.addMethod(sig);
		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("edma_dmview.getUpdateInterface().setSingletonAttribute("
				+ singleton.getArrayPosition()
				+ ", "
				+ att.getArrayPosition()
				+ ", ((IValueInstance) "
				+ att.getName()
				+ ").edma_getValue());\n");
		m.addImport(getImplImport(att.getValueDomain()));
		m.addImport(EdmaImport.IValueInstance);
		clazz.addMethod(m);
	}

	public JavaInterface getIntf()
	{
		return intf;
	}
}
