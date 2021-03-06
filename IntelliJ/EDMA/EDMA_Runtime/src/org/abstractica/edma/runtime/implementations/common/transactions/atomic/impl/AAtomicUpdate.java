package org.abstractica.edma.runtime.implementations.common.transactions.atomic.impl;

import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IDeleteConnection;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IDeleteEntity;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.INewConnection;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.INewEntity;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IUpdateEntity;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IUpdateSingleton;

public abstract class AAtomicUpdate implements IAtomicUpdate
{	
	@Override
	public INewEntity asNewEntity()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public IDeleteEntity asDeleteEntity()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public IUpdateEntity asUpdateEntity()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public INewConnection asNewConnection()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public IDeleteConnection asDeleteConnection()
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public IUpdateSingleton asUpdateSingleton()
	{
		throw new UnsupportedOperationException();
	}
}
