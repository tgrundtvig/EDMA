package edma.metamodel;

import edma.valuedomains.IMetaValueDomain;



public interface IMetaAttribute
{
	public IMetaKind getKind();
	public String getName();
	public int getArrayPosition();
	public String getValueDomainName();
	public IMetaValueDomain getValueDomain();
	public boolean isOptional();
	public boolean isModifiable();
	public String getDefaultValue();
	
	public boolean isPartOfUnique();
}
