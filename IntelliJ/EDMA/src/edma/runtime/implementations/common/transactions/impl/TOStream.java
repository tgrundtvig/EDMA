package edma.runtime.implementations.common.transactions.impl;

import java.io.IOException;

import edma.runtime.implementations.common.resource.IOutputStream;
import edma.runtime.implementations.common.transactions.ITransaction;
import edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate;
import edma.runtime.implementations.common.transactions.atomic.IDeleteConnection;
import edma.runtime.implementations.common.transactions.atomic.IDeleteEntity;
import edma.runtime.implementations.common.transactions.atomic.INewConnection;
import edma.runtime.implementations.common.transactions.atomic.INewEntity;
import edma.runtime.implementations.common.transactions.atomic.IUpdateEntity;
import edma.runtime.implementations.common.transactions.atomic.IUpdateSingleton;
import edma.runtime.implementations.common.transactions.streams.ITransactionOperationsOutputStream;
import edma.runtime.implementations.common.transactions.streams.ITransactionOutputStream;

public class TOStream implements ITransactionOutputStream
{
	private ITransactionOperationsOutputStream out;
	
	public TOStream(ITransactionOperationsOutputStream out)
	{
		this.out = out;
	}

	@Override
	public void write(ITransaction transaction) throws IOException
	{
		out.beginTransaction(	transaction.getName(),
								transaction.getFromVersion());
		for(IAtomicUpdate upd : transaction.getUpdates())
		{

			switch(upd.getType())
			{
				case NewEntity:
					INewEntity ne = upd.asNewEntity();
					out.newEntity(ne.getKindIndex(), ne.getValue());
					break;
				case DeleteEntity:
					IDeleteEntity de = upd.asDeleteEntity();
					out.deleteEntity(de.getKindIndex(), de.getValue());
					break;
				case UpdateEntity:
					IUpdateEntity ue = upd.asUpdateEntity();
					out.updateEntity(	ue.getKindIndex(),
										ue.getID(),
										ue.getFieldIndexes(),
										ue.getOldValues(),
										ue.getNewValues());
					break;
				case NewConnection:
					INewConnection nc = upd.asNewConnection();
					out.newConnection(	nc.getRelationIndex(),
										nc.getA(),
										nc.getB());
					break;
				case DeleteConnection:
					IDeleteConnection dc = upd.asDeleteConnection();
					out.deleteConnection(	dc.getRelationIndex(),
											dc.getA(),
											dc.getB());
					break;
				case UpdateSingleton:
					IUpdateSingleton us = upd.asUpdateSingleton();
					out.updateSingleton(us.getSingletonIndex(),
										us.getAttributeIndex(),
										us.getOldValue(),
										us.getNewValue());
					break;
				default:
					throw new RuntimeException("Unknown update type "
							+ upd.getType());
			}
		}
		out.endTransaction(transaction.getToVersion());
	}

	@Override
	public IOutputStream outputStream()
	{
		return out.outputStream();
	}


}
