package org.abstractica.edma.runtime.implementations.common.execution;

import org.abstractica.edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import org.abstractica.edma.runtime.implementations.common.connection.ModelConnection;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransaction;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IModelStore;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManagerFactory;
import org.abstractica.edma.runtime.intf.IDataModelUpdate;
import org.abstractica.edma.runtime.intf.IDataModelView;

public class MTConnectionManager
{
	private CollectionFactory cf;
	private ISetManagerFactory setManagerFactory;
	private IModelStore modelStore;
	private int viewCount;
	
	public MTConnectionManager(ISetManagerFactory setManagerFactory, IModelStore modelStore, CollectionFactory cf)
	{
		this.cf = cf;
		this.setManagerFactory = setManagerFactory;
		this.modelStore = modelStore;
		viewCount = 0;
	}
	
	public IModelStore getModelStore()
	{
		return modelStore;
	}
	
	
	// Views
	public synchronized void incViewCount()
	{
		++viewCount;
	}

	public IDataModelView beginView()
	{
		return new ModelConnection(false, modelStore, setManagerFactory.newSetManager(), cf);
	}

	public synchronized void endView(IDataModelView view)
	{
		view.release();
		--viewCount;
		if(viewCount == 0) notifyAll();
	}

	public synchronized void waitForViewsToFinish()
	{
		while(viewCount > 0)
		{
			try
			{
				wait();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public IDataModelUpdate beginUpdate(String name)
	{
		modelStore.beginTransaction(name);
		return new ModelConnection(true, modelStore, setManagerFactory.newSetManager(), cf);
	}

	public ITransaction endUpdate(IDataModelUpdate update)
	{
		update.release();
		return modelStore.commitTransaction();
	}

	public void cancelUpdate(IDataModelUpdate update)
	{
		update.release();
		modelStore.rollbackTransaction();
	}
}
