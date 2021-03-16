package org.abstractica.edma.runtime.implementations.common.transactions.streams;

import java.io.IOException;

import org.abstractica.edma.runtime.implementations.common.resource.IOutputStream;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransaction;


public interface ITransactionOutputStream
{
	public IOutputStream outputStream();
	public void write(ITransaction transaction) throws IOException;
}
