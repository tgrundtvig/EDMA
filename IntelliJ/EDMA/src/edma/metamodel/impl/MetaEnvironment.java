package edma.metamodel.impl;

import java.util.ArrayList;

import edma.metamodel.IMetaEnvironment;
import edma.valuedomains.IValueDomainDefinitions;

public class MetaEnvironment implements IMetaEnvironment
{
	private String name;
	private ArrayList<MetaDataModel> metaDataModels;
	private IValueDomainDefinitions valueDomainDefinitions;
	
	public MetaEnvironment(String name)
	{
		this.name = name;
		metaDataModels = new ArrayList<MetaDataModel>();
		valueDomainDefinitions = null;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public IValueDomainDefinitions getValueDomainDefinitions()
	{
		return valueDomainDefinitions;
	}
	
	public void setValueDomainDefinitions(IValueDomainDefinitions valueDomainDefinitions)
	{
		this.valueDomainDefinitions = valueDomainDefinitions;
	}
	
	public void addMetaDataModel(MetaDataModel model)
	{
		metaDataModels.add(model);
	}
	
	public void hookValueDomains(ValueDomainBuilder vdb)
	{
		metaDataModels.trimToSize();
		for(MetaDataModel model : metaDataModels)
		{
			model.hookValueDomains(vdb);
		}
		
	}

	@Override
	public MetaDataModel getMetaDataModel(int i)
	{
		return metaDataModels.get(i);
	}

	@Override
	public int getNumberOfDataModels()
	{
		return metaDataModels.size();
	}
}
