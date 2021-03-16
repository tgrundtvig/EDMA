package org.abstractica.edma.generator.metamodel.model;

import java.util.ArrayList;

import org.abstractica.edma.generator.EdmaImport;
import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaInterface;
import org.abstractica.edma.generator.common.JavaMethod;
import org.abstractica.edma.generator.common.JavaMethodSignature;
import org.abstractica.edma.generator.metamodel.AModelGenerator;
import org.abstractica.edma.generator.metamodel.kind.KindGenerator;
import org.abstractica.edma.generator.metamodel.remote.ClientInstanceGenerator;
import org.abstractica.edma.generator.metamodel.remote.RemoteResultGenerator;
import org.abstractica.edma.generator.metamodel.remote.ServerInstanceGenerator;
import org.abstractica.edma.generator.metamodel.singleton.SingletonGenerator;
import org.abstractica.edma.generator.metamodel.test.TestGenerator;
import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaMethod;

public class ModelGenerator extends AModelGenerator
{
	private ArrayList<JavaInterface> interfaces;
	private ArrayList<JavaClass> classes;

	public ModelGenerator(	IPackageStructure pkgStruct,
							IMetaDataModel model,
							ArrayList<JavaInterface> autoIntf,
							ArrayList<JavaClass> autoClasses,
							ArrayList<JavaClass> userClasses)
	{
		super(pkgStruct, model);
		interfaces = autoIntf;
		classes = autoClasses;

		// Remote access
		new RemoteResultGenerator(pkgStruct, model, autoClasses);
		new ClientInstanceGenerator(pkgStruct, model, autoClasses);
		new ServerInstanceGenerator(pkgStruct, model, autoClasses);

		// Test
		new TestGenerator(pkgStruct, model, autoClasses);

		new ModelFactoryGenerator(pkgStruct, model, autoIntf, autoClasses);

		ModelViewerGenerator mvg = new ModelViewerGenerator(pkgStruct, model);
		interfaces.add(mvg.getIntf());

		ModelUpdaterGenerator mug = new ModelUpdaterGenerator(	pkgStruct,
																model,
																mvg.getClazz());
		interfaces.add(mug.getIntf());
		classes.add(mug.getClazz());

		new SingletonGenerator(pkgStruct, model, autoIntf, autoClasses);
		new KindGenerator(pkgStruct, model, autoIntf, autoClasses, userClasses);

		JavaInterface instIntf = new JavaInterface(	getInstanceIntfName(),
													"This is the instance interface to "
															+ model.getName()
															+ " instances. It can be used to start"
															+ " and stop the instance and the API interface"
															+ " can be obtained");
		instIntf.setPackage(getInstanceIntfPackage());

		JavaInterface APIIntf = new JavaInterface(getAPIIntfName(),
															"The external interface for the model: "
																	+ model.getName());
		APIIntf.setPackage(getAPIIntfPackage());

		// Implementation of data model instance
		JavaClass APIImpl = new JavaClass(getAPIImplName());
		APIImpl.setPackage(getAPIImplPackage());
		APIImpl.addImplements(getAPIIntfName());
		APIImpl.addImport(getAPIIntfImport());
		APIImpl.addConstructorField("IDataModelInstance",
									"edma_dmi",
									"Data model instance runtime interface");
		APIImpl.addImport(EdmaImport.IDataModelInstance);
		

		// Access to the DataModelInstance.
		JavaMethodSignature getDMISig = new JavaMethodSignature("edma_getDMI",
																"Access to the internal data model instance");
		getDMISig.setReturnType("IDataModelInstance");
		getDMISig.setReturnDescription("The internal data model instance");
		getDMISig.addImport(EdmaImport.IDataModelInstance);
		JavaMethod getDMIMethod = new JavaMethod(getDMISig);
		getDMIMethod.appendToBody("return edma_dmi;\n");
		APIImpl.addMethod(getDMIMethod);
		
		//Java instance implementation class
		JavaClass instImpl = new JavaClass(getInstanceImplName());
		instImpl.setPackage(getInstanceImplPackage());
		instImpl.addImplements(getInstanceIntfName());
		instImpl.addImport(getInstanceIntfImport());
		instImpl.addField(getAPIImplName(), "api");
		instImpl.addConstructorParameter("IDataModelInstance", "edma_dmi", "The data model instance");
		instImpl.addImport(EdmaImport.IDataModelInstance);
		instImpl.addConstructorBodyLine("this.api = new " + getAPIImplName() + "(edma_dmi);");

		// start
		JavaMethodSignature startSig = new JavaMethodSignature(	"start",
																"Used to start the instance, which enables the API");
		startSig.setReturnType("void");
		JavaMethod start = new JavaMethod(startSig);
		start.appendToBody("api.edma_getDMI().start();\n");
		instIntf.addMethod(startSig);
		instImpl.addMethod(start);

		// stop
		JavaMethodSignature stopSig = new JavaMethodSignature(	"stop",
																"Used to stop the instance, which disables the API");
		stopSig.setReturnType("void");
		JavaMethod stop = new JavaMethod(stopSig);
		stop.appendToBody("api.edma_getDMI().stop();\n");
		instIntf.addMethod(stopSig);
		instImpl.addMethod(stop);

		// isRunning
		JavaMethodSignature isRunningSig = new JavaMethodSignature(	"isRunning",
																	"Used to test if the instance is running");
		isRunningSig.setReturnType("boolean");
		isRunningSig.setReturnDescription("<tt>true</tt> if the instance is running otherwise <tt>false</tt>");
		JavaMethod isRunning = new JavaMethod(isRunningSig);
		isRunning.appendToBody("return api.edma_getDMI().isRunning();\n");
		instIntf.addMethod(isRunningSig);
		instImpl.addMethod(isRunning);

		// getAPI
		JavaMethodSignature getAPISig = new JavaMethodSignature("getAPI",
																"Get access to the API for the "
																		+ model.getName()
																		+ " instance");
		getAPISig.setReturnType(getAPIIntfName());
		getAPISig.setReturnDescription("The API interface for the "
				+ model.getName() + " instance");
		getAPISig.addImport(getAPIIntfImport());
		JavaMethod getAPI = new JavaMethod(getAPISig);
		getAPI.appendToBody("return api;\n");
		instIntf.addMethod(getAPISig);
		instImpl.addMethod(getAPI);

		int size = model.getNumberOfViews();
		for(int i = 0; i < size; ++i)
		{
			IMetaMethod view = model.getView(i);
			new MethodGenerator(pkgStruct,
								model,
								view,
								false,
								APIImpl,
								APIIntf,
								autoIntf,
								autoClasses,
								userClasses);
		}
		size = model.getNumberOfActions();
		for(int i = 0; i < size; ++i)
		{
			IMetaMethod action = model.getAction(i);
			new MethodGenerator(pkgStruct,
								model,
								action,
								true,
								APIImpl,
								APIIntf,
								autoIntf,
								autoClasses,
								userClasses);
		}

		interfaces.add(APIIntf);
		classes.add(APIImpl);
		interfaces.add(instIntf);
		classes.add(instImpl);
	}
}
