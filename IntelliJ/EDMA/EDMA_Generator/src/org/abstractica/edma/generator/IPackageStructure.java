package org.abstractica.edma.generator;

import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaKind;
import org.abstractica.edma.metamodel.IMetaMethod;
import org.abstractica.edma.metamodel.IMetaRelation;
import org.abstractica.edma.metamodel.IMetaSingleton;
import org.abstractica.edma.valuedomains.IMetaValueDomain;

public interface IPackageStructure
{
	public String[] getPackagesToDeleteOnClean();

	// Environment
	public String getEnvironmentClassName();

	public String getEnvironmentPackage();

	// Value domains
	public String getIntfPackage(IMetaValueDomain vd);

	public String getImplPackage(IMetaValueDomain vd);

	public String getIntfName(IMetaValueDomain vd);

	public String getImplName(IMetaValueDomain vd);

	public String getConstraintPackage(IMetaValueDomain vd, String constraint);

	public String getExternalConstraintsClassName();

	public String getExternalConstraintsPackage();

	public String getMainValueStoreName();

	public String getMainValueStorePackage();

	// Models

	// Kinds
	// Intf
	public String getKindIntfName(IMetaDataModel model, IMetaKind kind);

	public String getKindIntfPackage(IMetaDataModel model, IMetaKind kind);

	// Impl
	public String getKindImplName(IMetaDataModel model, IMetaKind kind);

	public String getKindImplPackage(IMetaDataModel model, IMetaKind kind);

	// Entities
	public String getViewIntfName(IMetaDataModel model, IMetaSingleton singleton);

	public String getViewIntfPackage(	IMetaDataModel model,
										IMetaSingleton singleton);

	public String getUpdateIntfName(IMetaDataModel model,
									IMetaSingleton singleton);

	public String getUpdateIntfPackage(	IMetaDataModel model,
										IMetaSingleton singleton);

	public String getImplName(IMetaDataModel model, IMetaSingleton singleton);

	public String getImplPackage(IMetaDataModel model, IMetaSingleton singleton);

	// Constraints
	public String getConstraintPackage(	IMetaDataModel model,
										IMetaSingleton singleton,
										String constraintName);

	public String getConstraintPackage(	IMetaDataModel model,
										IMetaRelation relation,
										String constraintName);

	public String getConstraintPackage(	IMetaDataModel model,
										String constraintName);

	public String getKindConstraintsClassName(IMetaDataModel model);

	public String getKindConstraintsPackage(IMetaDataModel model);

	// Attribute update
	public String getAttUpdIntfName(IMetaDataModel model,
									IMetaSingleton singleton);

	public String getAttUpdUniqueIntfName(	IMetaDataModel model,
											IMetaSingleton singleton);

	public String getAttUpdImplName(IMetaDataModel model,
									IMetaSingleton singleton);

	public String getAttUpdUniqueImplName(	IMetaDataModel model,
											IMetaSingleton singleton);

	// KindSet
	public String getSetIntfName(IMetaDataModel model, IMetaSingleton singleton);

	public String getSetIntfPackage(IMetaDataModel model,
									IMetaSingleton singleton);

	public String getSetImplName(IMetaDataModel model, IMetaSingleton singleton);

	public String getSetImplPackage(IMetaDataModel model,
									IMetaSingleton singleton);

	public String getSetIteratorImplName(	IMetaDataModel model,
											IMetaSingleton singleton);

	public String getFilterIntfName(IMetaDataModel model,
									IMetaSingleton singleton);

	public String getFilterIntfPackage(	IMetaDataModel model,
										IMetaSingleton singleton);

	// Model
	public String getModelViewName(IMetaDataModel model);

	public String getModelViewPackage(IMetaDataModel model);

	public String getModelUpdateName(IMetaDataModel model);

	public String getModelUpdatePackage(IMetaDataModel model);

	public String getModelImplName(IMetaDataModel model);

	public String getModelImplPackage(IMetaDataModel model);

	// Model Methods
	public String getMethodName(IMetaDataModel model,
								String name,
								boolean action);

	public String getMethodEdmaClassName(	IMetaDataModel model,
											String name,
											boolean action);

	public String getMethodEdmaClassPackage(IMetaDataModel model,
											String name,
											boolean action);

	public String getMethodUserClassName(	IMetaDataModel model,
											String name,
											boolean action);

	public String getMethodUserClassPackage(IMetaDataModel model,
											String name,
											boolean action);
	
	public String getMethodResultIntfName(	IMetaDataModel model,
	                                           	String name,
	                                           	boolean action);
	
	public String getMethodResultIntfPackage(	IMetaDataModel model,
	                                              	String name,
	                                              	boolean action);

	// Model instance interface
	public String getModelAPIIntfName(IMetaDataModel model);
	public String getModelAPIPackage(IMetaDataModel model);
	public String getAPIImplName(IMetaDataModel model);
	public String getAPIImplPackage(IMetaDataModel model);
	
	public String getModelInstanceIntfName(IMetaDataModel model);
	public String getModelInstanceIntfPackage(IMetaDataModel model);
	public String getModelInstanceImplName(IMetaDataModel model);
	public String getModelInstanceImplPackage(IMetaDataModel model);
	
	
	public String getModelFactoryIntfName(IMetaDataModel model);
	public String getModelFactoryIntfPackage(IMetaDataModel model);
	public String getModelFactoryImplName(IMetaDataModel model);
	public String getModelFactoryImplPackage(IMetaDataModel model);

	
	
	//Remote access
	public String getServerInstanceImplName(IMetaDataModel model);
	public String getServerInstanceImplPackage(IMetaDataModel model);
	public String getClientInstanceImplName(IMetaDataModel model);
	public String getClientInstanceImplPackage(IMetaDataModel model);
	
	public String getRemoteMethodResultImplName(IMetaDataModel model, IMetaMethod method);
	public String getRemoteMethodResultImplPackage(IMetaDataModel model, IMetaMethod method);
	
	//Testing class
	public String getTestClassName(IMetaDataModel model);
	public String getTestClassPackage(IMetaDataModel model);
}
