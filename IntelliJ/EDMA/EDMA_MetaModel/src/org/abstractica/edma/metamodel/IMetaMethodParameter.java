package org.abstractica.edma.metamodel;

import org.abstractica.edma.valuedomains.IMetaValueDomain;

public interface IMetaMethodParameter
{
	public String getName();
	public IMetaValueDomain getValueDomain();
	public boolean canBeNull();
}
