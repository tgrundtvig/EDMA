package org.abstractica.edma.generator;

import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaKind;
import org.abstractica.edma.metamodel.IMetaMethod;
import org.abstractica.edma.metamodel.IMetaRelation;
import org.abstractica.edma.metamodel.IMetaSingleton;
import org.abstractica.edma.util.StringUtil;
import org.abstractica.edma.valuedomains.IMetaValueDomain;

public class EdmaPackageStructure implements IPackageStructure
{
	private final String root;
	private final String env;

	public EdmaPackageStructure(String root, String env)
	{
		this.root = root;
		this.env = env;
	}

	@Override
	public String getEnvironmentClassName()
	{
		return env;
	}

	@Override
	public String getEnvironmentPackage()
	{
		return root + ".generated";
	}

	@Override
	public String[] getPackagesToDeleteOnClean()
	{
		String[] res = new String[1];
		res[0] = root + ".generated";
		return res;
	}

	@Override
	public String getIntfPackage(IMetaValueDomain vd)
	{
		if(vd.getScope() == null){ return root + ".generated.valuedomains"; }
		return root + ".generated.valuedomains." + vd.getScope().toLowerCase();
	}

	@Override
	public String getImplPackage(IMetaValueDomain vd)
	{
		if(vd.getScope() == null){ return root + ".generated.valuedomains.impl"; }
		return root + ".generated.valuedomains." + vd.getScope().toLowerCase()
				+ ".impl";
	}

	@Override
	public String getIntfName(IMetaValueDomain vd)
	{
		return vd.getName();
	}

	@Override
	public String getImplName(IMetaValueDomain vd)
	{
		return vd.getName() + "Impl";
	}

	@Override
	public String getConstraintPackage(IMetaValueDomain vd, String constraint)
	{
		return root + ".usercode.valueconstraints."
				+ vd.getName().toLowerCase();
	}

	@Override
	public String getExternalConstraintsClassName()
	{
		return "EDMA_ExternalConstraints";
	}

	@Override
	public String getExternalConstraintsPackage()
	{
		return root + ".generated.valuedomains.external";
	}

	@Override
	public String getMainValueStoreName()
	{
		return "Edma_Values";
	}

	@Override
	public String getMainValueStorePackage()
	{
		return root + ".generated.valuedomains.valuestore";
	}

	@Override
	public String getKindIntfName(IMetaDataModel model, IMetaKind kind)
	{
		return kind.getName() + "Kind";
	}

	@Override
	public String getKindIntfPackage(IMetaDataModel model, IMetaKind kind)
	{
		return root + ".generated." + model.getName().toLowerCase() + ".kinds."
				+ kind.getName().toLowerCase();
	}

	@Override
	public String getKindImplName(IMetaDataModel model, IMetaKind kind)
	{
		return kind.getName() + "KindImpl";
	}

	@Override
	public String getKindImplPackage(IMetaDataModel model, IMetaKind kind)
	{
		return root + ".generated.edmaimpl.models."
				+ model.getName().toLowerCase();
	}

	@Override
	public String getViewIntfName(IMetaDataModel model, IMetaSingleton singleton)
	{
		return singleton.getName() + "Viewer";
	}

	@Override
	public String getViewIntfPackage(	IMetaDataModel model,
										IMetaSingleton singleton)
	{
		return root + ".generated." + model.getName().toLowerCase() + ".kinds."
				+ singleton.getName().toLowerCase();
	}

	@Override
	public String getUpdateIntfName(IMetaDataModel model,
									IMetaSingleton singleton)
	{
		return singleton.getName() + "Updater";
	}

	@Override
	public String getUpdateIntfPackage(	IMetaDataModel model,
										IMetaSingleton singleton)
	{
		return root + ".generated." + model.getName().toLowerCase() + ".kinds."
				+ singleton.getName().toLowerCase();
	}

	@Override
	public String getImplName(IMetaDataModel model, IMetaSingleton singleton)
	{
		return singleton.getName() + "VUImpl";
	}

	@Override
	public String getImplPackage(IMetaDataModel model, IMetaSingleton singleton)
	{
		return root + ".generated.edmaimpl.models."
				+ model.getName().toLowerCase();
	}

	// Constraints
	@Override
	public String getConstraintPackage(	IMetaDataModel model,
										IMetaSingleton singleton,
										String constraintName)
	{
		return root + ".usercode.models." + model.getName().toLowerCase()
				+ ".constraints." + singleton.getName().toLowerCase();
	}

	@Override
	public String getConstraintPackage(	IMetaDataModel model,
										IMetaRelation relation,
										String constraintName)
	{
		return root + ".usercode.models." + model.getName().toLowerCase()
				+ ".constraints." + relation.getName().toLowerCase();
	}

	@Override
	public String getConstraintPackage(	IMetaDataModel model,
										String constraintName)
	{
		return root + ".usercode.models." + model.getName().toLowerCase()
				+ ".constraints";
	}

	@Override
	public String getKindConstraintsClassName(IMetaDataModel model)
	{
		return "KindConstraints";
	}

	@Override
	public String getKindConstraintsPackage(IMetaDataModel model)
	{
		return root + ".generated.edmaimpl.models."
				+ model.getName().toLowerCase() + ".constraints";
	}

	@Override
	public String getAttUpdIntfName(IMetaDataModel model,
									IMetaSingleton singleton)
	{
		return singleton.getName() + "AttUpd";
	}

	@Override
	public String getAttUpdUniqueIntfName(	IMetaDataModel model,
											IMetaSingleton singleton)
	{
		return singleton.getName() + "AttUpdUnique";
	}

	@Override
	public String getAttUpdImplName(IMetaDataModel model,
									IMetaSingleton singleton)
	{
		return singleton.getName() + "AttUpdImpl";
	}

	@Override
	public String getAttUpdUniqueImplName(	IMetaDataModel model,
											IMetaSingleton singleton)
	{
		return singleton.getName() + "AttUpdUniqueImpl";
	}

	@Override
	public String getSetIntfName(IMetaDataModel model, IMetaSingleton singleton)
	{
		return singleton.getName() + "Set";
	}

	@Override
	public String getSetIntfPackage(IMetaDataModel model,
									IMetaSingleton singleton)
	{
		return root + ".generated." + model.getName().toLowerCase() + ".kinds."
				+ singleton.getName().toLowerCase();
	}

	@Override
	public String getSetImplName(IMetaDataModel model, IMetaSingleton singleton)
	{
		return singleton.getName() + "SetImpl";
	}

	@Override
	public String getSetImplPackage(IMetaDataModel model,
									IMetaSingleton singleton)
	{
		return root + ".generated.edmaimpl.models."
				+ model.getName().toLowerCase();
	}

	@Override
	public String getSetIteratorImplName(	IMetaDataModel model,
											IMetaSingleton singleton)
	{
		return singleton.getName() + "Iterator";
	}

	@Override
	public String getFilterIntfName(IMetaDataModel model,
									IMetaSingleton singleton)
	{
		return singleton.getName() + "Filter";
	}

	@Override
	public String getFilterIntfPackage(	IMetaDataModel model,
										IMetaSingleton singleton)
	{
		return root + ".generated." + model.getName().toLowerCase() + ".kinds."
				+ singleton.getName().toLowerCase();
	}

	@Override
	public String getModelViewName(IMetaDataModel model)
	{
		return model.getName() + "Viewer";
	}

	@Override
	public String getModelViewPackage(IMetaDataModel model)
	{
		return root + ".generated." + model.getName().toLowerCase();
	}

	@Override
	public String getModelUpdateName(IMetaDataModel model)
	{
		return model.getName() + "Updater";
	}

	@Override
	public String getModelUpdatePackage(IMetaDataModel model)
	{
		return root + ".generated." + model.getName().toLowerCase();
	}

	@Override
	public String getModelImplName(IMetaDataModel model)
	{
		return model.getName() + "VUImpl";
	}

	@Override
	public String getModelImplPackage(IMetaDataModel model)
	{
		return root + ".generated.edmaimpl.models."
				+ model.getName().toLowerCase();
	}

	@Override
	public String getMethodName(IMetaDataModel model,
								String name,
								boolean action)
	{
		return name;
	}

	@Override
	public String getMethodEdmaClassName(	IMetaDataModel model,
											String name,
											boolean action)
	{
		return "EDMA_" + StringUtil.U(name);
	}

	@Override
	public String getMethodEdmaClassPackage(IMetaDataModel model,
											String name,
											boolean action)
	{
		return root + ".generated.edmaimpl.models."
				+ model.getName().toLowerCase()
				+ (action ? ".actions" : ".views");
	}

	@Override
	public String getMethodUserClassName(	IMetaDataModel model,
											String name,
											boolean action)
	{
		return StringUtil.U(name) + "UserImpl";
	}

	@Override
	public String getMethodResultIntfName(	IMetaDataModel model,
											String name,
											boolean action)
	{
		return StringUtil.U(name) + "Result";
	}

	@Override
	public String getMethodResultIntfPackage(	IMetaDataModel model,
												String name,
												boolean action)
	{
		return root + ".generated." + model.getName().toLowerCase()
				+ (action ? ".actions" : ".views");
	}

	@Override
	public String getMethodUserClassPackage(IMetaDataModel model,
											String name,
											boolean action)
	{
		return root + ".usercode.models." + model.getName().toLowerCase()
				+ (action ? ".actions" : ".views");
	}

	// Model instance

	@Override
	public String getModelAPIIntfName(IMetaDataModel model)
	{
		return model.getName();
	}

	@Override
	public String getModelAPIPackage(IMetaDataModel model)
	{
		return root + ".generated." + model.getName().toLowerCase();
	}

	@Override
	public String getAPIImplName(IMetaDataModel model)
	{
		return model.getName() + "APIImpl";
	}

	@Override
	public String getAPIImplPackage(IMetaDataModel model)
	{
		return root + ".generated.edmaimpl.models."
				+ model.getName().toLowerCase();
	}

	@Override
	public String getModelInstanceIntfName(IMetaDataModel model)
	{
		return model.getName() + "Instance";
	}

	@Override
	public String getModelInstanceIntfPackage(IMetaDataModel model)
	{
		return root + ".generated." + model.getName().toLowerCase();
	}

	public String getModelInstanceImplName(IMetaDataModel model)
	{
		return model.getName() + "InstImpl";
	}

	public String getModelInstanceImplPackage(IMetaDataModel model)
	{
		return root + ".generated.edmaimpl.models."
				+ model.getName().toLowerCase();
	}

	@Override
	public String getModelFactoryIntfName(IMetaDataModel model)
	{
		return model.getName() + "Factory";
	}

	@Override
	public String getModelFactoryIntfPackage(IMetaDataModel model)
	{
		return root + ".generated." + model.getName().toLowerCase();
	}

	@Override
	public String getModelFactoryImplName(IMetaDataModel model)
	{
		return model.getName() + "FactoryImpl";
	}

	@Override
	public String getModelFactoryImplPackage(IMetaDataModel model)
	{
		return root + ".generated.edmaimpl.models."
				+ model.getName().toLowerCase();
	}

	// Remote access

	@Override
	public String getServerInstanceImplName(IMetaDataModel model)
	{
		return model.getName() + "ServerInstance";
	}

	@Override
	public String getServerInstanceImplPackage(IMetaDataModel model)
	{
		return root + ".generated." + model.getName().toLowerCase() + ".remote";
	}

	@Override
	public String getClientInstanceImplName(IMetaDataModel model)
	{
		return model.getName() + "ClientInstance";
	}

	@Override
	public String getClientInstanceImplPackage(IMetaDataModel model)
	{
		return root + ".generated." + model.getName().toLowerCase() + ".remote";
	}

	@Override
	public String getRemoteMethodResultImplName(IMetaDataModel model,
												IMetaMethod method)
	{
		return "Remote" + StringUtil.U(method.getName()) + "Result";
	}

	@Override
	public String getRemoteMethodResultImplPackage(	IMetaDataModel model,
													IMetaMethod method)
	{
		return root + ".generated." + model.getName().toLowerCase() + ".remote";
	}

	@Override
	public String getTestClassName(IMetaDataModel model)
	{
		return model.getName() + "Test";
	}

	@Override
	public String getTestClassPackage(IMetaDataModel model)
	{
		return root + ".generated." + model.getName().toLowerCase() + ".test";
	}

}
