package org.abstractica.edma.valuedomains;

import org.abstractica.edma.valuedomains.impl.AMetaValueDomain;

public interface IValueDomainBuilder
{
	public AMetaValueDomain get(String name, String modelName);
}
