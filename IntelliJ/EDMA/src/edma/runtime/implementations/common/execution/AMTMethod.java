package edma.runtime.implementations.common.execution;

import java.io.IOException;

import edma.runtime.implementations.common.persistence.IPersistenceUnit;

public abstract class AMTMethod implements IPersistenceUnit
{
	private boolean signal;
	private String errorMsg;
	
	public AMTMethod()
	{
		signal = false;
		errorMsg = null;
	}
	
	public synchronized void setErrorMsg(String errorMsg)
	{
		this.errorMsg = errorMsg;
	}
	
	public synchronized void waitForPersistence() throws IOException
	{
		while(!signal)
		{
			try
			{
				wait();
			}
			catch(InterruptedException e){e.printStackTrace();}
		}
		if(errorMsg != null){ throw new IOException(errorMsg); }
	}
	
	@Override
	public synchronized void persistenceSuccesfull()
	{
		signal = true;
		notifyAll();
	}

	@Override
	public synchronized void persistenceFailed(String errorMsg)
	{
		if(this.errorMsg == null)
		{	
			this.errorMsg = errorMsg;
		}
		else
		{
			this.errorMsg = this.errorMsg + "\n\n" + errorMsg;
		}
		signal = true;
		notifyAll();
	}
	
	public abstract boolean isAction();
	
	public MTAction asAction()
	{
		throw new UnsupportedOperationException();
	}
	
	public MTView asView()
	{
		throw new UnsupportedOperationException();
	}
	
}
