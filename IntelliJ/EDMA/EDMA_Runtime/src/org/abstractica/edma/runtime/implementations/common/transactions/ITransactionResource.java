package org.abstractica.edma.runtime.implementations.common.transactions;

import java.io.IOException;

import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.runtime.implementations.common.transactions.streams.ITransactionInputStream;
import org.abstractica.edma.runtime.implementations.common.transactions.streams.ITransactionOperationsOutputStream;
import org.abstractica.edma.runtime.implementations.common.transactions.streams.ITransactionOutputStream;

public interface ITransactionResource
{
	public String getName() throws IOException;
	public IMetaDataModel getDataModel() throws IOException;
	public boolean delete() throws IOException;
	public boolean clear() throws IOException;
	public boolean isOpenForWrites();
	public boolean isOpenForReads();
	
	//Appending to the resource
	public ITransactionOperationsOutputStream getOperationsOutputStream() throws IOException;
	
	//reading the resource...
	public void getCopy(ITransactionOperations out) throws IOException;
	public ITransactionInputStream getInputStream() throws IOException;
	
	
	public ITransactionOutputStream getOutputStream() throws IOException;
	
	public long getMaxVersion();
	public long getMinVersion();
	
	/*
	public void getLessThanOrEqual(long version, ITransactionOperationsOutputStream out);
	public void getGreaterThanOrEqual(long version, ITransactionOperationsOutputStream out);
	public void getRange(long begin, long end, ITransactionOperationsOutputStream out);
	public long getMaxVersion();
	public long getMinVersion();
	*/
}
