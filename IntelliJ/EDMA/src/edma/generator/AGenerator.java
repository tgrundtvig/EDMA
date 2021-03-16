package edma.generator;

import edma.util.StringUtil;
import edma.valuedomains.IMetaValueDomain;

public abstract class AGenerator
{
	protected IPackageStructure pkgStruct;

	protected AGenerator(IPackageStructure pkgStruct)
	{
		this.pkgStruct = pkgStruct;
	}

	protected IPackageStructure getPackageStructure()
	{
		return pkgStruct;
	}

	// Value domains
	protected String getEnvironmentPackage()
	{
		return pkgStruct.getEnvironmentPackage();
	}

	protected String getEnvironmentClassName()
	{
		return pkgStruct.getEnvironmentClassName();
	}

	protected String getEnvironmentImport()
	{
		return pkgStruct.getEnvironmentPackage() + "."
				+ pkgStruct.getEnvironmentClassName();
	}

	protected String getValueDomainFromIndexCode(int index)
	{
		return pkgStruct.getEnvironmentClassName()
				+ ".environment.getValueDomainDefinitions().getValueDomain("
				+ index + ")";
	}

	protected String getValueStoreCreateCode(	String objVarName,
												String domainVarName)
	{
		return domainVarName + ".newValue(" + objVarName + ")";
	}

	protected String getIntfPackage(IMetaValueDomain vd)
	{
		return pkgStruct.getIntfPackage(vd);
	}

	protected String getImplPackage(IMetaValueDomain vd)
	{
		return pkgStruct.getImplPackage(vd);
	}

	protected String getIntfImport(IMetaValueDomain vd)
	{
		return getIntfPackage(vd) + "." + getIntfName(vd);
	}

	protected String getImplImport(IMetaValueDomain vd)
	{
		return getImplPackage(vd) + "." + getImplName(vd);
	}

	protected String getIntfName(IMetaValueDomain vd)
	{
		return pkgStruct.getIntfName(vd);
	}

	protected String getImplName(IMetaValueDomain vd)
	{
		return pkgStruct.getImplName(vd);
	}

	protected String getBuilderIntfName(IMetaValueDomain vd)
	{
		return vd.getName() + "Builder";
	}

	protected String getBuilderImplName(IMetaValueDomain vd)
	{
		return vd.getName() + "BuilderImpl";
	}

	protected String getBuilderImplImport(IMetaValueDomain vd)
	{
		return getBuilderImplPackage(vd) + "." + getBuilderImplName(vd);
	}

	protected String getBuilderIntfImport(IMetaValueDomain vd)
	{
		return getIntfImport(vd) + "." + getBuilderIntfName(vd);
	}

	protected String getBuilderImplPackage(IMetaValueDomain vd)
	{
		return getImplPackage(vd);
	}

	// Value domain constraints

	protected String getConstraintPackage(	IMetaValueDomain vd,
											String constraintName)
	{
		return pkgStruct.getConstraintPackage(vd, constraintName);
	}

	protected String getConstraintClassName(IMetaValueDomain vd,
											String constraintName)
	{
		return StringUtil.U(constraintName);
	}

	protected String getConstraintImport(	IMetaValueDomain vd,
											String constraintName)
	{
		return getConstraintPackage(vd, constraintName) + "."
				+ getConstraintClassName(vd, constraintName);
	}

	protected String getExternalConstraintsClassName()
	{
		return pkgStruct.getExternalConstraintsClassName();
	}

	protected String getExternalConstraintsPackage()
	{
		return pkgStruct.getExternalConstraintsPackage();
	}

	protected String getExternalConstraintsImport()
	{
		return getExternalConstraintsPackage() + "."
				+ getExternalConstraintsClassName();
	}
}
