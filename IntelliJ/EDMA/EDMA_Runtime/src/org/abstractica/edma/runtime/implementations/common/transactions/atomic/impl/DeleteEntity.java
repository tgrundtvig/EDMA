package org.abstractica.edma.runtime.implementations.common.transactions.atomic.impl;

import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IDeleteEntity;

public class DeleteEntity extends AAtomicUpdate implements IDeleteEntity
{
	private final int kindIndex;
	private final Object[] value;

	public DeleteEntity(int kindIndex, Object[] value)
	{
		super();
		this.kindIndex = kindIndex;
		this.value = value;
	}

	@Override
	public UpdateType getType()
	{
		return UpdateType.DeleteEntity;
	}

	@Override
	public IAtomicUpdate getInverse()
	{
		return new NewEntity(kindIndex, value);
	}

	@Override
	public int getKindIndex()
	{
		return kindIndex;
	}

	@Override
	public Long getID()
	{
		return (Long) value[0];
	}

	@Override
	public Object[] getValue()
	{
		return value;
	}
	
	@Override
	public IDeleteEntity asDeleteEntity()
	{
		return this;
	}
	
	@Override
	public String toString()
	{
		return "DeleteEntity -> Kind: " + kindIndex + " ID: " + getID();
	}
}
