package org.abstractica.edma.runtime.implementations.mem.modelstore;

import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransaction;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransactionOperations;

public interface IModelStore extends ITransactionOperations
{
	public IMetaDataModel getMetaModel();

	public String getName();

	public long getVersion();

	// Access to data
	public ISingletonStore getSingleton(int i);

	public IKindStore getKind(int i);

	public IRelationStore getRelation(int i);

	// Dump current version
	public void dump(ITransactionOperations out);

	// Transactions
	public void beginTransaction(String name);

	public ITransaction commitTransaction();

	public void rollbackTransaction();
	
	//Direct updates of the model
	public void directUpdate(ITransaction t);
	public void directRollback(ITransaction t);
}
