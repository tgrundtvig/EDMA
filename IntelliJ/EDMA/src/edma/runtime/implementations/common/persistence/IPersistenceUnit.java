package edma.runtime.implementations.common.persistence;

import edma.runtime.implementations.common.transactions.ITransaction;

public interface IPersistenceUnit
{
	public ITransaction getTransaction();
	
	//Callback
	public void persistenceSuccesfull();
	public void persistenceFailed(String errorMsg);
}
