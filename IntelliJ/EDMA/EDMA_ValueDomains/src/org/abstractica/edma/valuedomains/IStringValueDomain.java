package org.abstractica.edma.valuedomains;

public interface IStringValueDomain extends IMetaValueDomain
{
	public Integer getMinLength();
	public Integer getMaxLength();
	public String getRegexp();
}
