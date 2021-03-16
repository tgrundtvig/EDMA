package org.abstractica.edma.generator.metamodel.model;

import java.util.ArrayList;

import org.abstractica.edma.generator.EdmaImport;
import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaInterface;
import org.abstractica.edma.generator.common.JavaMethod;
import org.abstractica.edma.generator.common.JavaMethodSignature;
import org.abstractica.edma.generator.metamodel.AModelGenerator;
import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaSingleton;
import org.abstractica.edma.util.StringUtil;
import org.abstractica.edma.valuedomains.IStructField;
import org.abstractica.edma.valuedomains.IStructValueDomain;

public class ModelFactoryGenerator extends AModelGenerator
{

	protected ModelFactoryGenerator(IPackageStructure pkgStruct,
	                                IMetaDataModel model,
	                                ArrayList<JavaInterface> autoIntf,
	                                ArrayList<JavaClass> autoClasses)
	{
		super(pkgStruct, model);
		JavaInterface factoryIntf = new JavaInterface(	getModelFactoryIntfName(),
														"Instance factory for "
																+ model.getName());
		factoryIntf.setPackage(getModelFactoryIntfPackage());
		JavaClass factoryImpl = new JavaClass(getModelFactoryImplName());
		factoryImpl.setPackage(getModelFactoryImplPackage());
		factoryImpl.addImplements(getModelFactoryIntfName());
		factoryImpl.addImport(getModelFactoryIntfImport());

		factoryImpl.addConstructorField("IDataModelInstanceFactory",
										"edma_instanceFactory",
										"Internal storage interface");
		factoryImpl.addImport(EdmaImport.IDataModelInstanceFactory);
		exists(factoryIntf, factoryImpl);
		newInstance(factoryIntf, factoryImpl);
		getInstance(factoryIntf, factoryImpl);
		deleteInstance(factoryIntf, factoryImpl);
		autoIntf.add(factoryIntf);
		autoClasses.add(factoryImpl);
	}

	private void exists(JavaInterface intf, JavaClass impl)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"exists",
															"Test the existence of an instance");
		sig.addParameter(	"String",
							"name",
							"The name of the instance to test the existence of");
		sig.setReturnType("boolean");
		sig.addException("IOException");
		sig.addImport("java.io.IOException");
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return edma_instanceFactory.exists(name);\n");
		intf.addMethod(sig);
		impl.addMethod(m);
	}

	private void newInstance(JavaInterface intf, JavaClass impl)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"newInstance",
															"Create a new instance");
		sig.addParameter(	"String",
							"edma_name",
							"The name of the instance to create");
		int singletonCount = model.getNumberOfSingletons();
		int singletonValueCount = 0;
		for(int i = 0; i < singletonCount; ++i)
		{
			IMetaSingleton singleton = model.getSingleton(i);
			singletonValueCount += singleton.getNumberOfAttributes();
			sig.addParameter(	getIntfName(singleton.getValueDomain()),
								StringUtil.L(singleton.getName()),
								"The initial values for the singleton "
										+ singleton.getName());
			sig.addImport(getIntfImport(singleton.getValueDomain()));
		}
		sig.setReturnType(getInstanceIntfName());
		sig.addImport(getInstanceIntfImport());
		sig.addException("IOException");
		sig.addImport("java.io.IOException");

		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("Object[] edma_initValues = new Object["
				+ singletonValueCount + "];\n");
		int index = 0;
		for(int i = 0; i < singletonCount; ++i)
		{
			IMetaSingleton singleton = model.getSingleton(i);
			IStructValueDomain vd = singleton.getValueDomain().asStruct();
			int fieldCount = vd.getNumberOfFields();
			for(int j = 0; j < fieldCount; ++j)
			{
				IStructField field = vd.getField(j);
				m.appendToBody("edma_initValues[" + (index++)
						+ "] = ((IValueInstance) "
						+ StringUtil.L(singleton.getName()) + "."
						+ field.getName() + "()).edma_getValue();\n");
			}
		}
		m.addImport(EdmaImport.IValueInstance);
		m.appendToBody("return new "
				+ getInstanceImplName()
				+ "(edma_instanceFactory.newModelInstance(edma_name, edma_initValues));\n");
		m.addImport(getInstanceImplImport());
		intf.addMethod(sig);
		impl.addMethod(m);
	}

	private void getInstance(JavaInterface intf, JavaClass impl)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"getInstance",
															"Get an instance from its name");
		sig.addParameter("String", "name", "The name of the instance to get");
		sig.setReturnType(getInstanceIntfName());
		sig.addImport(getInstanceIntfImport());
		sig.addException("IOException");
		sig.addImport("java.io.IOException");
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return new " + getInstanceImplName()
				+ "(edma_instanceFactory.getModelInstance(name));\n");
		m.addImport(getInstanceImplImport());
		intf.addMethod(sig);
		impl.addMethod(m);
	}

	private void deleteInstance(JavaInterface intf, JavaClass impl)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"deleteInstance",
															"Delete an instance");
		sig.addParameter("String", "name", "The name of the instance to delete");
		sig.setReturnType("boolean");
		sig.setReturnDescription("<tt>true</tt> if the instance was deleted");
		sig.addException("IOException");
		sig.addImport("java.io.IOException");
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return edma_instanceFactory.deleteModelInstance(name);\n");
		intf.addMethod(sig);
		impl.addMethod(m);
	}

}
