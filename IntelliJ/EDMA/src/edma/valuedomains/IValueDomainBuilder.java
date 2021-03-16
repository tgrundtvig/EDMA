package edma.valuedomains;

import edma.valuedomains.impl.AMetaValueDomain;

public interface IValueDomainBuilder
{
	public AMetaValueDomain get(String name, String modelName);
}
