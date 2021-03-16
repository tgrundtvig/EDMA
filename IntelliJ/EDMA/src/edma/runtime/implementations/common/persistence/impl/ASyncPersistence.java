package edma.runtime.implementations.common.persistence.impl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import edma.runtime.implementations.common.persistence.IPersistence;
import edma.runtime.implementations.common.persistence.IPersistenceUnit;
import edma.runtime.implementations.common.transactions.ITransaction;
import edma.runtime.implementations.common.transactions.ITransactionOperations;
import edma.runtime.implementations.common.transactions.ITransactionResource;
import edma.runtime.implementations.common.transactions.streams.ITransactionOutputStream;
import edma.util.ErrorLog;
import edma.util.concurrency.ClosedException;
import edma.util.concurrency.ISynchBuffer;
import edma.util.concurrency.PausedException;
import edma.util.concurrency.impl.SynchArrayQueue;

public class ASyncPersistence implements IPersistence, Runnable
{
	private volatile long lastPersistedVersion;
	private boolean isRunning;
	private boolean pause;
	private ISynchBuffer<IPersistenceUnit> inputBuffer;
	ITransactionResource resource;
	ITransactionOutputStream out;
	private long lastAction;

	public ASyncPersistence(ITransactionResource resource, int bufferSize)
			throws IOException
	{
		this.inputBuffer = new SynchArrayQueue<IPersistenceUnit>(	bufferSize,
																	"ASynchFilePersistence: inputBuffer");
		this.isRunning = false;
		this.lastPersistedVersion = resource.getMaxVersion();
		this.resource = resource;
		this.out = resource.getOutputStream();
	}

	@Override
	public long getLastPersistedVersion()
	{
		return lastPersistedVersion;
	}

	@Override
	public void persist(IPersistenceUnit unit) throws IOException
	{
		try
		{
			inputBuffer.put(unit);
		}
		catch(ClosedException e)
		{
			unit.persistenceFailed("Persistence is closed down");
			throw new IOException("Persistence is closed!");
		}
	}

	public synchronized void start()
	{
		if(isRunning) return;
		inputBuffer.open();
		new Thread(this, "ASyncFilePersistence").start();
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

	private void burstWrite() throws IOException
	{
		//System.out.println("Enter burst");

		IPersistenceUnit m;
		try
		{
			while(true)
			{
				m = inputBuffer.take(5000);
				ITransaction t = m.getTransaction();

				if(t == null)
				{
					m.persistenceSuccesfull();
					long curTime = System.nanoTime();
					long sinceLastAction = curTime - lastAction;
					if(sinceLastAction > 5000000000L){ return; }
				}
				else
				{
					out.write(t);
					m.persistenceSuccesfull();
					lastAction = System.nanoTime();
				}
			}
		}
		catch(ClosedException e)
		{
			//System.out.println("InputBuffer Closed while burstWriting!");
		}
		catch(PausedException e)
		{
			//System.out.println("Caught PausedException in burst.");
		}
		catch(TimeoutException e)
		{
			//System.out.println("Caught TimeoutException in burst.");
		}
		catch(IOException e)
		{
			ErrorLog.logError("Error writing to resource...");
			throw e;
		}
		finally
		{
			try
			{
				//System.out.println("Closing outputStream in finally....");
				out.outputStream().endWrites();
			}
			catch(IOException e)
			{
				ErrorLog.logError("Error on closing resource!!!");
			}
		}
	}

	private synchronized void doPause()
	{
		//System.out.println("Starting pause...");
		isRunning = false;
		notifyAll();
		while(pause)
		{
			try
			{
				wait();
			}
			catch(InterruptedException e1)
			{}
		}
		isRunning = true;
		notifyAll();
		//System.out.println("Finish pause pause...");
	}

	@Override
	public void run()
	{
		//System.out.println("Persistence has started!");
		IPersistenceUnit m = null;
		synchronized(this)
		{
			isRunning = true;
			notifyAll();
		}
		while(true)
		{
			try
			{
				m = inputBuffer.take();
				ITransaction t = m.getTransaction();
				if(t == null)
				{
					m.persistenceSuccesfull();
					continue;
				}

				// Open for write and enter burst mode...
				//System.out.println("Opening output stream...");
				out.outputStream().beginWrites();
				out.write(t);
				m.persistenceSuccesfull();
				burstWrite();
			}
			catch(PausedException e)
			{
				doPause();
			}
			catch(ClosedException e)
			{
				break;
			}
			catch(IOException e)
			{

				System.out.println("IOException during write to resource....");
				m.persistenceFailed("Persistence failed due to IOException");
				inputBuffer.close();
				while(true)
				{
					try
					{
						m = inputBuffer.take();
						m.persistenceFailed("Persistence failed due to IOException");
					}
					catch(ClosedException e2)
					{
						break;
					}
					catch(PausedException e3)
					{
						System.out.println("Pause called during closing down due to IOError... resuming close down");
						synchronized(this)
						{
							out = null;
						}
						inputBuffer.resume();
					}

				}
			}
		}
		//System.out.println("Persistence stopped...");
		synchronized(this)
		{
			isRunning = false;
			notifyAll();
		}
	}

	@Override
	public synchronized void getCopy(ITransactionOperations out) throws IOException
	{
		pause = true;
		inputBuffer.pause();
		while(isRunning)
		{
			try
			{
				wait();
			}
			catch(InterruptedException e)
			{}
		}
		if(out != null)
		{
			resource.getCopy(out);
		}
		inputBuffer.resume();
		pause = false;
		notifyAll();
	}
}
