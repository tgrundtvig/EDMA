package org.abstractica.edma.runtime.implementations.common.transactions.atomic.impl;

import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.INewEntity;

public class NewEntity extends AAtomicUpdate implements INewEntity
{
	private final int kindIndex;
	private final Object[] value;

	public NewEntity(int kindIndex, Object[] value)
	{
		this.kindIndex = kindIndex;
		this.value = value;
	}

	@Override
	public UpdateType getType()
	{
		return UpdateType.NewEntity;
	}

	@Override
	public IAtomicUpdate getInverse()
	{
		return new DeleteEntity(kindIndex, value);
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
	public INewEntity asNewEntity()
	{
		return this;
	}
	
	@Override
	public String toString()
	{
		return "NewEntity -> Kind: " + kindIndex + " ID: " + getID();
	}

}
