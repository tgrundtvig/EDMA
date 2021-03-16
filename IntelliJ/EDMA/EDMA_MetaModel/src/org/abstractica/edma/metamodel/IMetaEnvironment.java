package org.abstractica.edma.metamodel;

import org.abstractica.edma.valuedomains.IValueDomainDefinitions;

public interface IMetaEnvironment
{
	public String getName();
	public IValueDomainDefinitions getValueDomainDefinitions();
	public int getNumberOfDataModels();
	public IMetaDataModel getMetaDataModel(int i);	
}
