package org.abstractica.edma.runtime.implementations.mem.instance;

import java.io.IOException;

import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaSingleton;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import org.abstractica.edma.runtime.implementations.common.execution.MultiThreadExecuter;
import org.abstractica.edma.runtime.implementations.common.persistence.IPersistence;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransactionOperations;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IModelStore;
import org.abstractica.edma.runtime.implementations.mem.modelstore.impl.ModelStore;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.SpeedStorageSolution;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManagerFactory;
import org.abstractica.edma.runtime.implementations.mem.sets.simple.SimpleSetManagerFactory;
import org.abstractica.edma.runtime.intf.IAction;
import org.abstractica.edma.runtime.intf.IDataModelInstance;
import org.abstractica.edma.runtime.intf.IView;

public class DataModelInstance implements IDataModelInstance
{
	private CollectionFactory cf;
	private IMetaDataModel model;
	private String name;
	private MultiThreadExecuter executor;
	private IPersistence persistence;
	private IModelStore store;
	private ISetManagerFactory setManagerFactory;

	public DataModelInstance(	CollectionFactory cf,
	                         	IMetaDataModel model,
	                         	String name,
	                         	ISetManagerFactory setManagerFactory,
	                         	Object[] singletonValues) throws IOException
	{
		this.cf = cf;
		this.model = model;
		this.name = name;
		this.setManagerFactory = setManagerFactory;
		
	    store = new ModelStore(name, model, new SpeedStorageSolution(cf));
		store.beginTransaction("Initialize singletons...", 0);
		int index = 0;
		int numberOfSingletons = model.getNumberOfSingletons();
		for(int i = 0; i < numberOfSingletons; ++i)
		{
			IMetaSingleton singleton = model.getSingleton(i);
			int numberOfAttributes = singleton.getNumberOfAttributes();
			for(int j = 0; j < numberOfAttributes; ++j)
			{
				store.updateSingleton(i, j, null, singletonValues[index++]);
			}
		}
		store.endTransaction(1);
		this.executor = null;
		this.persistence = null;
	}
	
	public DataModelInstance(	CollectionFactory cf,
	                         	IMetaDataModel model,
	                         	String name,
	                         	ISetManagerFactory setManagerFactory,
	                         	IPersistence persistence) throws IOException
	{
		this.cf = cf;
		this.model = model;
		this.name = name;
		this.setManagerFactory = setManagerFactory;
		this.persistence = persistence;
		this.executor = null;
		this.store = null;
	}

	@Override
	public IMetaDataModel getMetaModel()
	{
		return model;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public long getVersion()
	{
		if(persistence != null) return persistence.getLastPersistedVersion();
		return store.getVersion();
	}

	@Override
	public boolean isRunning()
	{
		return (executor != null);
	}

	@Override
	public void start()
	{
		if(executor != null) throw new RuntimeException("Already running!");
		if(persistence != null)
		{
			store = new ModelStore(name, model, new SpeedStorageSolution(cf));
			try
			{
				persistence.getCopy(store);
				executor = new MultiThreadExecuter(cf, persistence, store, setManagerFactory, 100);
			}
			catch(IOException e)
			{
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		else
		{
			ISetManagerFactory setManagerFactory = new SimpleSetManagerFactory(cf);
			executor = new MultiThreadExecuter(cf, persistence, store, setManagerFactory, 100);
		}
		executor.start();
	}

	@Override
	public void execute(IView view) throws IOException
	{
		if(!isRunning()) throw new IOException("Instance is not running");
		executor.execute(view);
	}

	@Override
	public void execute(IAction action) throws IOException
	{
		if(!isRunning()) throw new IOException("Instance is not running");
		executor.execute(action);
	}

	@Override
	public void stop()
	{
		cf.stop();
		executor.stop();
		if(persistence != null)
		{
			store = null;
		}
		executor = null;
	}

	@Override
	public void dump(ITransactionOperations out) throws IOException
	{
		if(executor != null) executor.dump(out);
		else if(persistence != null)
		{
			ModelStore tmp = new ModelStore(name, model, new SpeedStorageSolution(cf));
			persistence.getCopy(tmp);
			tmp.dump(out);
		}
		else
		{
			store.dump(out);
		}
	}

}
