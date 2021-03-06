package org.abstractica.edma.runtime.implementations.mem.factory;

import java.io.IOException;
import java.util.Enumeration;

import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaSingleton;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import org.abstractica.edma.runtime.implementations.common.persistence.IPersistence;
import org.abstractica.edma.runtime.implementations.common.persistence.impl.ASyncPersistence;
import org.abstractica.edma.runtime.implementations.common.resource.ISimpleResourceFactory;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransactionResource;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransactionResourceFactory;
import org.abstractica.edma.runtime.implementations.common.transactions.impl.TransactionResourceFactory;
import org.abstractica.edma.runtime.implementations.common.transactions.streams.ITransactionOperationsOutputStream;
import org.abstractica.edma.runtime.implementations.mem.instance.DataModelInstance;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManagerFactory;
import org.abstractica.edma.runtime.intf.IDataModelInstance;
import org.abstractica.edma.runtime.intf.IDataModelInstanceFactory;

public class PersistentFactory implements IDataModelInstanceFactory
{
	private CollectionFactory cf;
	private ITransactionResourceFactory resourceFactory;
	private ISetManagerFactory setManagerFactory;

	public PersistentFactory(	CollectionFactory cf,
	                         	IMetaDataModel model,
	                         	ISetManagerFactory setManagerFactory,
	                         	ISimpleResourceFactory simpleResourceFactory)
	{
		this.cf = cf;
		this.resourceFactory = new TransactionResourceFactory(	model,
																simpleResourceFactory);
		this.setManagerFactory = setManagerFactory;

	}

	@Override
	public boolean exists(String name) throws IOException
	{
		return resourceFactory.exists(name);
	}

	@Override
	public IDataModelInstance getModelInstance(String name) throws IOException
	{
		ITransactionResource resource = resourceFactory.get(name);
		IPersistence persistence = new ASyncPersistence(resource, 100);
		return new DataModelInstance(	cf,
		                             	resourceFactory.getModel(),
										name,
										setManagerFactory,
										persistence);
	}

	@Override
	public IDataModelInstance newModelInstance(	String name,
												Object[] singletonValues) throws IOException
	{
		ITransactionResource resource = resourceFactory.create(name);
		IMetaDataModel model = resourceFactory.getModel();
		ITransactionOperationsOutputStream out = resource.getOperationsOutputStream();
		out.outputStream().beginWrites();
		out.beginTransaction("Initializing singletons", 0);
		int index = 0;
		int numberOfSingletons = model.getNumberOfSingletons();
		for(int i = 0; i < numberOfSingletons; ++i)
		{
			IMetaSingleton singleton = model.getSingleton(i);
			int numberOfAttributes = singleton.getNumberOfAttributes();
			for(int j = 0; j < numberOfAttributes; ++j)
			{
				Object val = singletonValues[index++];
				out.updateSingleton(i, j, val, val);
			}
		}
		out.endTransaction(1);
		out.outputStream().endWrites();
		IPersistence persistence = new ASyncPersistence(resource, 100);
		return new DataModelInstance(	cf,
		                             	resourceFactory.getModel(),
										name,
										setManagerFactory,
										persistence);
	}

	@Override
	public boolean deleteModelInstance(String name) throws IOException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Enumeration<String> getInstanceNames() throws IOException
	{
		throw new UnsupportedOperationException();
	}
}
