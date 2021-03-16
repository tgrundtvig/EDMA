package org.abstractica.edma.runtime.implementations.common.transactions.streams;

import org.abstractica.edma.runtime.implementations.common.resource.IOutputStream;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransactionOperations;


public interface ITransactionOperationsOutputStream extends ITransactionOperations
{
	public IOutputStream outputStream();
}
