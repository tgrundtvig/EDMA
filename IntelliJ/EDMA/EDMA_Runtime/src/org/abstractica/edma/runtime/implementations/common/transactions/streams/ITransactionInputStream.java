package org.abstractica.edma.runtime.implementations.common.transactions.streams;

import java.io.IOException;

import org.abstractica.edma.runtime.implementations.common.resource.IInputStream;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransaction;


public interface ITransactionInputStream
{
	public IInputStream inputStream();
	public ITransaction readNext() throws IOException;
}
