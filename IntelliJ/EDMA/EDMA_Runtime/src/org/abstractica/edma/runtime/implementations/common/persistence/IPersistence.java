package org.abstractica.edma.runtime.implementations.common.persistence;

import java.io.IOException;

import org.abstractica.edma.runtime.implementations.common.transactions.ITransactionOperations;


public interface IPersistence
{
	//Can only be called when running
	public void persist(IPersistenceUnit unit) throws IOException;
	
	public void start();
	public void stop();
	
	public long getLastPersistedVersion();
	
	public void getCopy(ITransactionOperations out) throws IOException;
}
