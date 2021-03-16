package org.abstractica.edma.valuedomains.impl;

import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.IValueDomainDefinitions;

/**
 * This class contains all the created ValueDomains, and is used for retrieving
 * them. An instance of this class is created when ValueDomainBuilder.build() is
 * called.
 * 
 */
public class ValueDomainDefinitions implements IValueDomainDefinitions
{
	private final IMetaValueDomain[] valueDomains;

	public ValueDomainDefinitions(IMetaValueDomain[] valueDomains)
	{
		this.valueDomains = valueDomains;
	}

	@Override
	public IMetaValueDomain getValueDomain(int i)
	{
		return valueDomains[i];
	}

	@Override
	public int getNumberOfValueDomains()
	{
		return valueDomains.length;
	}
}
