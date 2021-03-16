package edma.runtime.implementations.common.transactions.impl;

import java.io.IOException;

import edma.metamodel.IMetaDataModel;
import edma.runtime.implementations.common.resource.IDataInputStream;
import edma.runtime.implementations.common.resource.IDataResource;
import edma.runtime.implementations.common.resource.IDataResourceFactory;
import edma.runtime.implementations.common.resource.ISimpleResourceFactory;
import edma.runtime.implementations.common.resource.impl.DataResourceFactory;
import edma.runtime.implementations.common.transactions.ITransactionResource;
import edma.runtime.implementations.common.transactions.ITransactionResourceFactory;

public class TransactionResourceFactory implements ITransactionResourceFactory
{
	private IMetaDataModel model;
	private IDataResourceFactory factory;

	public TransactionResourceFactory(	IMetaDataModel model,
										ISimpleResourceFactory factory)
	{
		this.model = model;
		this.factory = new DataResourceFactory(factory);
	}
	
	@Override
	public IMetaDataModel getModel()
	{
		return model;
	}

	@Override
	public String getName()
	{
		return factory.getName();
	}

	@Override
	public boolean exists(String name) throws IOException
	{
		return factory.exists(name);
	}

	@Override
	public ITransactionResource create(String name) throws IOException
	{
		return new TransactionResource(factory.create(name), model);
	}

	@Override
	public ITransactionResource get(String name) throws IOException
	{
		IDataResource resource = factory.get(name);
		TransactionResource res = new TransactionResource(resource, model);
		IDataInputStream dataIn = resource.getInputStream();
		dataIn.inputStream().beginReads();
		res.performCopy(res.getVersionCount(), dataIn);
		dataIn.inputStream().endReads();
		return res;
	}

	@Override
	public ITransactionResource getOrCreate(String name) throws IOException
	{
		if(exists(name)) return get(name);
		return create(name);
	}
}