package edma.runtime.implementations.common.execution;

import edma.runtime.implementations.common.transactions.ITransaction;
import edma.runtime.intf.IDataModelView;
import edma.runtime.intf.IView;

public class MTView extends AMTMethod
{
	private boolean executeSignal;
	private final IView view;
	private MTConnectionManager conMan;

	public MTView(IView view)
	{
		super();
		this.view = view;
		executeSignal = false;
		conMan = null;
	}

	public synchronized void waitForExecuteSignal()
	{
		while(!executeSignal)
		{
			try
			{
				wait();
			}
			catch(InterruptedException e){e.printStackTrace();}
		}
	}
	
	public void doExecute()
	{
		IDataModelView dmView = conMan.beginView();
		try
		{
			view.execute(dmView);
		}
		catch(Exception e)
		{
			StringBuilder errorMsg = new StringBuilder("Exception caught during view execute: \n"
					+ e.toString() + "\n");
			for(StackTraceElement elem : e.getStackTrace())
			{
				errorMsg.append(elem.toString());
				errorMsg.append("\n");
			}
			setErrorMsg(errorMsg.toString());
		}
		conMan.endView(dmView);
	}
	
	public synchronized void signalExecute(MTConnectionManager conMan)
	{
		this.conMan = conMan;
		executeSignal = true;
		notifyAll();
	}
	
	@Override
	public boolean isAction()
	{
		return false;
	}
	
	@Override
	public MTView asView()
	{
		return this;
	}

	@Override
	public ITransaction getTransaction()
	{
		return null;
	}	
}
