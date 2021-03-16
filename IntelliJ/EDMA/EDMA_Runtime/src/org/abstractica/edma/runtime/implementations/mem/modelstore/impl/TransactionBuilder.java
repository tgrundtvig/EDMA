package org.abstractica.edma.runtime.implementations.mem.modelstore.impl;

import org.abstractica.edma.runtime.implementations.common.transactions.IAtomicOperations;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransaction;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.impl.DeleteConnection;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.impl.DeleteEntity;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.impl.NewConnection;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.impl.NewEntity;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.impl.UpdateEntity;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.impl.UpdateSingleton;
import org.abstractica.edma.runtime.implementations.common.transactions.impl.Transaction;

public class TransactionBuilder implements IAtomicOperations
{
	private Transaction curTrans;

	public TransactionBuilder()
	{
		curTrans = null;
	}

	public void beginTransaction(String name, long version)
	{
		if(curTrans != null) throw new IllegalStateException();
		curTrans = new Transaction(name, version, version + 1);
	}

	@Override
	public void newEntity(int kindIndex, Object[] value)
	{
		if(curTrans == null) throw new IllegalStateException();
		IAtomicUpdate update = new NewEntity(kindIndex, value);
		curTrans.add(update);
	}

	@Override
	public void deleteEntity(int kindIndex, Object[] value)
	{
		if(curTrans == null) throw new IllegalStateException();
		IAtomicUpdate update = new DeleteEntity(kindIndex, value);
		curTrans.add(update);
	}

	@Override
	public void updateEntity(	int kindIndex,
								Long ID,
								int[] fields,
								Object[] oldValues,
								Object[] newValues)
	{
		if(curTrans == null) throw new IllegalStateException();
		IAtomicUpdate update = new UpdateEntity(kindIndex,
												ID,
												fields,
												oldValues,
												newValues);
		curTrans.add(update);
	}

	@Override
	public void newConnection(int relationIndex, Long a, Long b)
	{
		if(curTrans == null) throw new IllegalStateException();
		IAtomicUpdate update = new NewConnection(relationIndex, a, b);
		curTrans.add(update);
	}

	@Override
	public void deleteConnection(int relationIndex, Long a, Long b)
	{
		if(curTrans == null) throw new IllegalStateException();
		IAtomicUpdate update = new DeleteConnection(relationIndex, a, b);
		curTrans.add(update);
	}

	@Override
	public void updateSingleton(int singletonIndex,
								int attributeIndex,
								Object oldValue,
								Object newValue)
	{
		if(curTrans == null) throw new IllegalStateException();
		IAtomicUpdate update = new UpdateSingleton(	singletonIndex,
													attributeIndex,
													oldValue,
													newValue);
		curTrans.add(update);
	}

	public ITransaction getTransaction()
	{
		if(curTrans == null) throw new IllegalStateException();
		ITransaction res = curTrans;
		curTrans = null;
		return res;
	}
}
