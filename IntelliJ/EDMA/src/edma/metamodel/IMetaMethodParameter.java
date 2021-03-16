package edma.metamodel;

import edma.valuedomains.IMetaValueDomain;

public interface IMetaMethodParameter
{
	public String getName();
	public IMetaValueDomain getValueDomain();
	public boolean canBeNull();
}
