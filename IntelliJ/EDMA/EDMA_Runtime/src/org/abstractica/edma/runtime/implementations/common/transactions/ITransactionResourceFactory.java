package org.abstractica.edma.runtime.implementations.common.transactions;

import java.io.IOException;

import org.abstractica.edma.metamodel.IMetaDataModel;

public interface ITransactionResourceFactory
{
	public IMetaDataModel getModel();
	public String getName();
	public boolean exists(String name) throws IOException;
	public ITransactionResource create(String name) throws IOException;
	public ITransactionResource get(String name) throws IOException;
	public ITransactionResource getOrCreate(String name) throws IOException;
}
