package org.abstractica.edma.runtime.implementations.common.execution;

import org.abstractica.edma.runtime.implementations.common.transactions.ITransaction;
import org.abstractica.edma.runtime.intf.IAction;
import org.abstractica.edma.runtime.intf.IDataModelUpdate;

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
