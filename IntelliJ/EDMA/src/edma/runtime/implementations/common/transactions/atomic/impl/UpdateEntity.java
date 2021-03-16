package edma.runtime.implementations.common.transactions.atomic.impl;

import edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate;
import edma.runtime.implementations.common.transactions.atomic.IUpdateEntity;

public class UpdateEntity extends AAtomicUpdate implements IUpdateEntity
{
	private final int kindIndex;
	private final Long id;
	private final int[] fields;
	private final Object[] oldValues;
	private final Object[] newValues;
	
	
	public UpdateEntity(	int kindIndex,
								Long id,
								int[] fields,
								Object[] oldValues,
								Object[] newValues)
	{
		this.kindIndex = kindIndex;
		this.id = id;
		this.fields = fields;
		this.oldValues = oldValues;
		this.newValues = newValues;
	}

	@Override
	public UpdateType getType()
	{
		return UpdateType.UpdateEntity;
	}

	@Override
	public IAtomicUpdate getInverse()
	{
		return new UpdateEntity(kindIndex, id, fields, newValues, oldValues);
	}

	@Override
	public int getKindIndex()
	{
		return kindIndex;
	}

	@Override
	public Long getID()
	{
		return id;
	}

	@Override
	public int[] getFieldIndexes()
	{
		return fields;
	}

	@Override
	public Object[] getOldValues()
	{
		return oldValues;
	}

	@Override
	public Object[] getNewValues()
	{
		return newValues;
	}
	
	@Override
	public IUpdateEntity asUpdateEntity()
	{
		return this;
	}
	
	@Override
	public String toString()
	{
		return "UpdateEntity -> Kind: " + kindIndex + " ID: " + id;
	}

}
