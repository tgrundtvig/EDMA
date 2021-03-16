package org.abstractica.edma.metamodel.impl;

import org.abstractica.edma.metamodel.IMetaMethodParameter;
import org.abstractica.edma.valuedomains.IMetaValueDomain;

public class MetaMethodParameter implements IMetaMethodParameter
{
	private String name;
	private String valueDomainName;
	private IMetaValueDomain valueDomain;
	private boolean canBeNull;
	
	public MetaMethodParameter(String name, String valueDomainName, boolean canBeNull)
	{
		this.name = name;
		this.valueDomainName = valueDomainName;
		valueDomain = null;
		this.canBeNull = canBeNull;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public IMetaValueDomain getValueDomain()
	{
		return valueDomain;
	}
	
	public void hookValueDomains(ValueDomainBuilder vdb, String modelName)
	{
		valueDomain = vdb.get(valueDomainName, modelName);
		if(valueDomain == null) throw new RuntimeException("Could not find the value domain: " + valueDomainName);
	}

	@Override
	public boolean canBeNull()
	{
		return canBeNull;
	}

}
