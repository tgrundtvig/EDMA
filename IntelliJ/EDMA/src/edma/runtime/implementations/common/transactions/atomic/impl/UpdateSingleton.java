package edma.runtime.implementations.common.transactions.atomic.impl;

import edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate;
import edma.runtime.implementations.common.transactions.atomic.IUpdateSingleton;

public class UpdateSingleton extends AAtomicUpdate implements IUpdateSingleton
{
	private final int singletonIndex;
	private final int attributeIndex;
	private final Object oldValue;
	private final Object newValue;
	
	public UpdateSingleton(	int singletonIndex,
							int attributeIndex,
							Object oldValue,
							Object newValue)
	{
		this.singletonIndex = singletonIndex;
		this.attributeIndex = attributeIndex;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}


	@Override
	public UpdateType getType()
	{
		return UpdateType.UpdateSingleton;
	}

	
	@Override
	public IUpdateSingleton asUpdateSingleton()
	{
		return this;
	}

	@Override
	public IAtomicUpdate getInverse()
	{
		return new UpdateSingleton(singletonIndex, attributeIndex, newValue, oldValue);
	}

	@Override
	public int getSingletonIndex()
	{
		return singletonIndex;
	}

	@Override
	public int getAttributeIndex()
	{
		return attributeIndex;
	}

	@Override
	public Object getOldValue()
	{
		return oldValue;
	}

	@Override
	public Object getNewValue()
	{
		return newValue;
	}

}
