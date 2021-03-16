package org.abstractica.edma.runtime.implementations.common.persistence;

import org.abstractica.edma.runtime.implementations.common.transactions.ITransaction;

public interface IPersistenceUnit
{
	public ITransaction getTransaction();
	
	//Callback
	public void persistenceSuccesfull();
	public void persistenceFailed(String errorMsg);
}
