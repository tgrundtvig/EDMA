package org.abstractica.edma.valuedomains;

public interface IStructField
{
	public String getName();
	public IMetaValueDomain getValueDomain();
	public boolean canBeNull();
}
