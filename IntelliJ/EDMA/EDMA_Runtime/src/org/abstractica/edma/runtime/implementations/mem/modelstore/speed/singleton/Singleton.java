package org.abstractica.edma.runtime.implementations.mem.modelstore.speed.singleton;

import org.abstractica.edma.metamodel.IMetaAttribute;
import org.abstractica.edma.metamodel.IMetaSingleton;
import org.abstractica.edma.runtime.implementations.common.transactions.IAtomicOperations;
import org.abstractica.edma.runtime.implementations.mem.modelstore.ISingletonStore;

public class Singleton implements ISingletonStore
{
	private IMetaSingleton singleton;
	private Object[] values;
	private IAtomicOperations updateListener;
	
	public Singleton(IMetaSingleton singleton, IAtomicOperations updateListener)
	{
		this.singleton = singleton;
		this.updateListener = updateListener;
		values = new Object[singleton.getNumberOfAttributes()];
	}
	
	@Override
	public void setAttribute(int attributeIndex, Object newValue)
	{
		IMetaAttribute att = singleton.getAttribute(attributeIndex);
		Object oldValue = values[attributeIndex];
		if(att.getValueDomain().valueEqual(oldValue, newValue)) return;
		values[attributeIndex] = newValue;
		updateListener.updateSingleton(singleton.getArrayPosition(), attributeIndex, oldValue, newValue);
	}

	@Override
	public Object getAttribute(int attributeIndex)
	{
		return values[attributeIndex];
	}

	@Override
	public void directUpdate(int attributeIndex, Object value)
	{
		values[attributeIndex] = value;
	}

	@Override
	public void dump(IAtomicOperations out)
	{
		int singletonIndex = singleton.getArrayPosition();
		for(int i = 0; i < values.length; ++i)
		{
			Object newValue = values[i];
			if(newValue != null) out.updateSingleton(singletonIndex, i, null, newValue);
		}
		
	}

}
