package org.abstractica.edma.generator.metamodel;

import org.abstractica.edma.generator.AGenerator;
import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaKind;
import org.abstractica.edma.metamodel.IMetaMethod;
import org.abstractica.edma.metamodel.IMetaSingleton;

public abstract class AModelGenerator extends AGenerator
{
	protected IMetaDataModel model;

	protected AModelGenerator(IPackageStructure pkgStruct, IMetaDataModel model)
	{
		super(pkgStruct);
		this.model = model;
	}
	
	//Kind
	
	//Intf
	protected String getKindIntfName(IMetaKind kind)
	{
		return pkgStruct.getKindIntfName(model, kind);
	}
	
	protected String getKindIntfPackage(IMetaKind kind)
	{
		return pkgStruct.getKindIntfPackage(model, kind);
	}
	
	protected String getKindIntfImport(IMetaKind kind)
	{
		return getKindIntfPackage(kind) + "." + getKindIntfName(kind);
	}

	//Impl
	protected String getKindImplName(IMetaKind kind)
	{
		return pkgStruct.getKindImplName(model, kind);
	}
	
	protected String getKindImplPackage(IMetaKind kind)
	{
		return pkgStruct.getKindImplPackage(model, kind);
	}
	
	protected String getKindImplImport(IMetaKind kind)
	{
		return getKindIntfPackage(kind) + "." + getKindIntfName(kind);
	}
	
	// Entity View
	protected String getViewIntfName(IMetaSingleton singleton)
	{
		return pkgStruct.getViewIntfName(model, singleton);
	}

	protected String getViewIntfPackage(IMetaSingleton singleton)
	{
		return pkgStruct.getViewIntfPackage(model, singleton);
	}

	protected String getViewIntfImport(IMetaSingleton singleton)
	{
		return getViewIntfPackage(singleton) + "." + getViewIntfName(singleton);
	}

	// Entity Update
	protected String getUpdateIntfName(IMetaSingleton singleton)
	{
		return pkgStruct.getUpdateIntfName(model, singleton);
	}

	protected String getUpdateIntfPackage(IMetaSingleton singleton)
	{
		return pkgStruct.getUpdateIntfPackage(model, singleton);
	}

	protected String getUpdateIntfImport(IMetaSingleton singleton)
	{
		return getUpdateIntfPackage(singleton) + "."
				+ getUpdateIntfName(singleton);
	}

	// Impl

	protected String getImplName(IMetaSingleton singleton)
	{
		return pkgStruct.getImplName(model, singleton);
	}

	protected String getImplPackage(IMetaSingleton singleton)
	{
		return pkgStruct.getImplPackage(model, singleton);
	}

	protected String getImplImport(IMetaSingleton singleton)
	{
		return getImplPackage(singleton) + "." + getImplName(singleton);
	}

	// Attribute updates
	// Intf
	protected String getAttUpdIntfName(IMetaSingleton singleton)
	{
		return pkgStruct.getAttUpdIntfName(model, singleton);
	}

	protected String getAttUpdUniqueIntfName(IMetaSingleton singleton)
	{
		return pkgStruct.getAttUpdUniqueIntfName(model, singleton);
	}
	
	// Impl
	protected String getAttUpdImplName(IMetaSingleton singleton)
	{
		return pkgStruct.getAttUpdImplName(model, singleton);
	}

	protected String getAttUpdUniqueImplName(IMetaSingleton singleton)
	{
		return pkgStruct.getAttUpdUniqueImplName(model, singleton);
	}

	// Sets

	// Set view
	protected String getSetIntfName(IMetaSingleton singleton)
	{
		return pkgStruct.getSetIntfName(model, singleton);
	}

	protected String getSetIntfPackage(IMetaSingleton singleton)
	{
		return pkgStruct.getSetIntfPackage(model, singleton);
	}

	protected String getSetIntfImport(IMetaSingleton singleton)
	{
		return getSetIntfPackage(singleton) + "." + getSetIntfName(singleton);
	}

	protected String getSetIteratorImplName(IMetaSingleton singleton)
	{
		return pkgStruct.getSetIteratorImplName(model, singleton);
	}

	// Set impl
	protected String getSetImplName(IMetaSingleton singleton)
	{
		return pkgStruct.getSetImplName(model, singleton);
	}

	protected String getSetImplPackage(IMetaSingleton singleton)
	{
		return pkgStruct.getSetImplPackage(model, singleton);
	}

	protected String getSetImplImport(IMetaSingleton singleton)
	{
		return getSetImplPackage(singleton) + "." + getSetImplName(singleton);
	}

	// Filter
	public String getFilterIntfName(IMetaSingleton singleton)
	{
		return pkgStruct.getFilterIntfName(model, singleton);
	}

	public String getFilterIntfPackage(IMetaSingleton singleton)
	{
		return pkgStruct.getFilterIntfPackage(model, singleton);
	}

	public String getFilterIntfImport(IMetaSingleton singleton)
	{
		return getFilterIntfPackage(singleton) + "."
				+ getFilterIntfName(singleton);
	}

	// Model
	public String getModelViewName()
	{
		return pkgStruct.getModelViewName(model);
	}

	public String getModelViewPackage()
	{
		return pkgStruct.getModelViewPackage(model);
	}

	public String getModelViewImport()
	{
		return getModelViewPackage() + "." + getModelViewName();
	}

	public String getModelUpdateName()
	{
		return pkgStruct.getModelUpdateName(model);
	}

	public String getModelUpdatePackage()
	{
		return pkgStruct.getModelUpdatePackage(model);
	}

	public String getModelUpdateImport()
	{
		return getModelUpdatePackage() + "." + getModelUpdateName();
	}

	public String getModelImplName()
	{
		return pkgStruct.getModelImplName(model);
	}

	public String getModelImplPackage()
	{
		return pkgStruct.getModelImplPackage(model);
	}

	public String getModelImplImport()
	{
		return getModelImplPackage() + "." + getModelImplName();
	}
	

	// Actions and views
	public String getMethodName(String name, boolean action)
	{
		return pkgStruct.getMethodName(model, name, action);
	}
	
	public String getMethodEdmaClassName(String name, boolean action)
	{
		return pkgStruct.getMethodEdmaClassName(model, name, action);
	}

	public String getMethodEdmaClassPackage(String name, boolean action)
	{
		return pkgStruct.getMethodEdmaClassPackage(model, name, action);
	}
	
	public String getMethodEdmaClassImport(String name, boolean action)
	{
		return getMethodEdmaClassPackage(name, action) + "." + getMethodEdmaClassName(name, action);
	}
	
	public String getMethodUserClassName(String name, boolean action)
	{
		return pkgStruct.getMethodUserClassName(model, name, action);
	}

	public String getMethodUserClassPackage(String name, boolean action)
	{
		return pkgStruct.getMethodUserClassPackage(model, name, action);
	}
	
	public String getMethodUserClassImport(String name, boolean action)
	{
		return getMethodUserClassPackage(name, action) + "." + getMethodUserClassName(name, action);
	}
	
	public String getMethodResultIntfName(String name, boolean action)
	{
		return pkgStruct.getMethodResultIntfName(model, name, action);
	}

	public String getMethodResultIntfPackage(String name, boolean action)
	{
		return pkgStruct.getMethodResultIntfPackage(model, name, action);
	}
	
	public String getMethodResultIntfImport(String name, boolean action)
	{
		return getMethodResultIntfPackage(name, action) + "." + getMethodResultIntfName(name, action);
	}
	
	//Constraints
	public String getConstraintClassName(IMetaSingleton singleton, String constraintName)
	{
		return constraintName;
	}
	
	public String getConstraintPackage(IMetaSingleton singleton, String constraintName)
	{
		return pkgStruct.getConstraintPackage(model, singleton, constraintName);
	}
	
	public String getConstraintImport(IMetaSingleton singleton, String constraintName)
	{
		return getConstraintPackage(singleton, constraintName) + "." + getConstraintClassName(singleton, constraintName);
	}
	
	public String getKindConstraintsClassName()
	{
		return pkgStruct.getKindConstraintsClassName(model);
	}
	
	public String getKindConstraintsPackage()
	{
		return pkgStruct.getKindConstraintsPackage(model);
	}
	
	public String getKindConstraintsImport()
	{
		return getKindConstraintsPackage() + "." + getKindConstraintsClassName();
	}

	// Model external interface
	public String getAPIIntfName()
	{
		return pkgStruct.getModelAPIIntfName(model);
	}

	public String getAPIIntfPackage()
	{
		return pkgStruct.getModelAPIPackage(model);
	}
	
	public String getAPIIntfImport()
	{
		return getAPIIntfPackage() + "." + getAPIIntfName();
	}
	
	public String getAPIImplName()
	{
		return pkgStruct.getAPIImplName(model);
	}

	public String getAPIImplPackage()
	{
		return pkgStruct.getAPIImplPackage(model);
	}
	
	public String getAPIImplImport()
	{
		return getAPIImplPackage() + "." + getAPIImplName();
	}
	
	public String getInstanceIntfName()
	{
		return pkgStruct.getModelInstanceIntfName(model);
	}
	
	public String getInstanceIntfPackage()
	{
		return pkgStruct.getModelInstanceIntfPackage(model);
	}
	
	public String getInstanceIntfImport()
	{
		return getInstanceIntfPackage() + "." + getInstanceIntfName();
	}
	
	public String getInstanceImplName()
	{
		return pkgStruct.getModelInstanceImplName(model);
	}
	
	public String getInstanceImplPackage()
	{
		return pkgStruct.getModelInstanceImplPackage(model);
	}
	
	public String getInstanceImplImport()
	{
		return getInstanceImplPackage() + "." + getInstanceImplName();
	}
	
	public String getModelFactoryIntfName()
	{
		return pkgStruct.getModelFactoryIntfName(model);
	}
	
	public String getModelFactoryIntfPackage()
	{
		return pkgStruct.getModelFactoryIntfPackage(model);
	}
	
	public String getModelFactoryIntfImport()
	{
		return getModelFactoryIntfPackage() + "." + getModelFactoryIntfName();
	}
	
	public String getModelFactoryImplName()
	{
		return pkgStruct.getModelFactoryImplName(model);
	}
	
	public String getModelFactoryImplPackage()
	{
		return pkgStruct.getModelFactoryImplPackage(model);
	}
	
	public String getModelFactoryImplImport()
	{
		return getModelFactoryImplPackage() + "." + getModelFactoryImplName();
	}
	
	//Remote
	public String getServerInstanceImplName()
	{
		return pkgStruct.getServerInstanceImplName(model);
	}
	
	public String getServerInstanceImplPackage()
	{
		return pkgStruct.getServerInstanceImplPackage(model);
	}
	
	public String getServerInstanceImplImport()
	{
		return getServerInstanceImplPackage() + "." + getServerInstanceImplName();
	}
	
	public String getClientInstanceImplName()
	{
		return pkgStruct.getClientInstanceImplName(model);
	}
	
	public String getClientInstanceImplPackage()
	{
		return pkgStruct.getClientInstanceImplPackage(model);
	}
	
	public String getClientInstanceImplImport()
	{
		return getClientInstanceImplPackage() + "." + getClientInstanceImplName();
	}
	
	public String getRemoteMethodResultImplName(IMetaMethod method)
	{
		return pkgStruct.getRemoteMethodResultImplName(model, method);
	}
	
	public String getRemoteMethodResultImplPackage(IMetaMethod method)
	{
		return pkgStruct.getRemoteMethodResultImplPackage(model, method);
	}
	
	public String getRemoteMethodResultImplImport(IMetaMethod method)
	{
		return getRemoteMethodResultImplPackage(method) + "." + getRemoteMethodResultImplName(method);
	}
	
	//Test class
	public String getTestClassName()
	{
		return pkgStruct.getTestClassName(model);
	}
	
	public String getTestClassPackage()
	{
		return pkgStruct.getTestClassPackage(model);
	}
	
	public String getTestClassImport()
	{
		return getTestClassPackage() + "." + getTestClassName();
	}
}
