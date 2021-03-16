package edma.runtime.implementations.common.execution;

import edma.runtime.implementations.common.transactions.ITransaction;
import edma.runtime.intf.IAction;
import edma.runtime.intf.IDataModelUpdate;

public class MTAction extends AMTMethod
{
	private final IAction action;
	private ITransaction transaction;
	
	public MTAction(IAction action)
	{
		super();
		this.action = action;
	}
	
	public void setTransaction(ITransaction transaction)
	{
		this.transaction = transaction;
	}


	public String getName()
	{
		return action.getName();
	}


	public boolean execute(IDataModelUpdate upd)
	{
		return action.execute(upd);
	}
	
	@Override
	public boolean isAction()
	{
		return true;
	}
	
	@Override
	public MTAction asAction()
	{
		return this;
	}

	@Override
	public ITransaction getTransaction()
	{
		return transaction;
	}
}
