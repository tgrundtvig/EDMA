package org.abstractica.edma.runtime.implementations.common.execution;

import java.io.IOException;

import org.abstractica.edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import org.abstractica.edma.runtime.implementations.common.persistence.IPersistence;
import org.abstractica.edma.runtime.implementations.common.persistence.IPersistenceUnit;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransaction;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransactionOperations;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IModelStore;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManagerFactory;
import org.abstractica.edma.runtime.intf.IAction;
import org.abstractica.edma.runtime.intf.IDataModelUpdate;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IView;
import org.abstractica.edma.util.ErrorLog;
import org.abstractica.edma.util.concurrency.ClosedException;
import org.abstractica.edma.util.concurrency.ISynchBuffer;
import org.abstractica.edma.util.concurrency.PausedException;
import org.abstractica.edma.util.concurrency.impl.SynchArrayQueue;

public class MultiThreadExecuter implements Runnable
{
	private boolean isRunning;
	private boolean actionMode;
	private IPersistence persistence;
	private MTConnectionManager conMan;
	private ISynchBuffer<AMTMethod> inputBuffer;

	public MultiThreadExecuter(	CollectionFactory cf,
	                           	IPersistence persistence,
								IModelStore store,
								ISetManagerFactory setManagerFactory,
								int bufferSize)
	{
		isRunning = false;
		actionMode = true;
		this.persistence = persistence;
		conMan = new MTConnectionManager(setManagerFactory, store, cf);
		inputBuffer = new SynchArrayQueue<AMTMethod>(	bufferSize,
														"MethodExecuter input buffer");
	}

	public synchronized void start()
	{
		if(isRunning) return;
		new Thread(this, "MethodExecuter").start();
		while(!isRunning)
		{
			try
			{
				wait();
			}
			catch(InterruptedException e)
			{}
		}
	}

	public synchronized void stop()
	{
		if(!isRunning) return;
		inputBuffer.close();
		while(isRunning)
		{
			try
			{
				wait();
			}
			catch(InterruptedException e)
			{}
		}
	}
	
	public synchronized boolean isRunning()
	{
		return isRunning;
	}

	@Override
	public void run()
	{
		if(persistence != null) persistence.start();
		inputBuffer.open();
		synchronized(this)
		{
			isRunning = true;
			notifyAll();
		}
		//System.out.println("MethodExecuter has been started!");
		while(true)
		{
			try
			{
				AMTMethod m = inputBuffer.take();
				if(m.isAction())
				{
					if(!actionMode)
					{
						conMan.waitForViewsToFinish();
						actionMode = true;
					}
					MTAction action = m.asAction();
					executeAction(action);
				}
				else
				{
					actionMode = false;
					MTView view = m.asView();
					executeView(view);
				}
			}
			catch(ClosedException e)
			{
				break;
			}
			catch(PausedException e)
			{
				ErrorLog.logError("Executor buffer paused.... Should not happen!");
				break;
			}
			catch(IOException e)
			{
				ErrorLog.logError("Executor closing due to IOException!");
				inputBuffer.close();
				while(true)
				{
					try
					{
						AMTMethod m = inputBuffer.take();
						m.persistenceFailed("Persistence failed due to IOException");
					}
					catch(ClosedException e2)
					{
						break;
					}
					catch(PausedException e3)
					{
						ErrorLog.logError("Executor buffer paused.... Should not happen!");
					}
				}
				break;
			}
		}
		if(persistence != null) persistence.stop();
		synchronized(this)
		{
			isRunning = false;
			notifyAll();
		}
		//System.out.println("MethodExecuter has been stopped!");
	}

	private void executeAction(MTAction action) throws IOException
	{
		// Actions are executed in this thread in a sequentially manner.
		// This saves the thread communication overhead in switching to
		// the submitting thread and wait for it to finish the execution.
		IDataModelUpdate upd = conMan.beginUpdate(action.getName());
		boolean res;
		try
		{
			res = action.execute(upd);
		}
		catch(Exception e)
		{
			res = false;
			StringBuilder errorMsg = new StringBuilder("\nException caught during action execute: \n"
					+ e.toString() + "\n");
			for(StackTraceElement elem : e.getStackTrace())
			{
				errorMsg.append(elem.toString());
				errorMsg.append("\n");
			}
			action.setErrorMsg(errorMsg.toString());
		}
		if(res)
		{
			ITransaction transaction = conMan.endUpdate(upd);
			action.setTransaction(transaction);
		}
		else
		{
			conMan.cancelUpdate(upd);
		}
		persist(action);
	}

	private void executeView(MTView view) throws IOException
	{
		conMan.incViewCount();
		view.signalExecute(conMan);
		persist(view);
	}
	
	private void persist(IPersistenceUnit unit) throws IOException
	{
		if(persistence != null)
		{
			persistence.persist(unit);
		}
		else unit.persistenceSuccesfull();
	}

	/*
	public IMetaDataModel getMetaModel()
	{
		return conMan.getMetaModel();
	}

	public String getName()
	{
		return conMan.getInstanceName();
	}

	public long getVersion()
	{
		return conMan.getVersion();
	}
*/
	public void execute(IView view) throws IOException
	{
		try
		{
			MTView mtView = new MTView(view);
			inputBuffer.put(mtView);
			mtView.waitForExecuteSignal();
			mtView.doExecute();
			mtView.waitForPersistence();
		}
		catch(ClosedException e)
		{
			throw new IOException("Executer is closed!");
		}
	}

	public void execute(IAction action) throws IOException
	{
		try
		{
			MTAction mtAction = new MTAction(action);
			inputBuffer.put(mtAction);
			mtAction.waitForPersistence();
		}
		catch(ClosedException e)
		{
			throw new IOException("Executer is closed!");
		}
	}

	public void dump(ITransactionOperations out) throws IOException
	{
		try
		{
			MTView mtView = new MTView(new DumpView(out));
			inputBuffer.put(mtView);
			mtView.waitForExecuteSignal();
			mtView.doExecute();
			mtView.waitForPersistence();
		}
		catch(ClosedException e)
		{
			conMan.getModelStore().dump(out);
		}	
	}
	
	private class DumpView implements IView
	{
		private ITransactionOperations out;
		
		public DumpView(ITransactionOperations out)
		{
			super();
			this.out = out;
		}

		@Override
		public String getName()
		{
			return "Data dump";
		}

		@Override
		public void execute(IDataModelView view)
		{
			view.dump(out);
		}	
	}
}
