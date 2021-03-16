package edma.runtime.implementations.common.transactions.streams;

import edma.runtime.implementations.common.resource.IOutputStream;
import edma.runtime.implementations.common.transactions.ITransactionOperations;


public interface ITransactionOperationsOutputStream extends ITransactionOperations
{
	public IOutputStream outputStream();
}
