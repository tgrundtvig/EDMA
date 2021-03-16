package edma.runtime.implementations.common.transactions.streams;

import java.io.IOException;

import edma.runtime.implementations.common.resource.IInputStream;
import edma.runtime.implementations.common.transactions.ITransaction;


public interface ITransactionInputStream
{
	public IInputStream inputStream();
	public ITransaction readNext() throws IOException;
}
