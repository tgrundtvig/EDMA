package org.abstractica.edma.runtime.implementations.unused.consistency;

import java.util.HashSet;
import java.util.Set;

import org.abstractica.edma.runtime.implementations.common.transactions.IAtomicOperations;

public class ConsistencyCheck implements IAtomicOperations
{
	private IAtomicOperations writeThrough;
	private Set<EntityID> updatedEntities;
	private Set<ConnectionID> updatedConnections;
	private Set<Integer> updatedSingletons;
	
	public ConsistencyCheck(IAtomicOperations writeThrough)
	{
		this.writeThrough = writeThrough;
		updatedEntities = new HashSet<EntityID>();
		updatedConnections = new HashSet<ConnectionID>();
		updatedSingletons = new HashSet<Integer>();
	}
	
	@Override
	public void newEntity(int kindIndex, Object[] value)
	{
		updatedEntities.add(new EntityID(kindIndex, (Long) value[0]));
		writeThrough.newEntity(kindIndex, value);
	}

	@Override
	public void deleteEntity(int kindIndex, Object[] value)
	{
		updatedEntities.remove(new EntityID(kindIndex, (Long) value[0]));
		writeThrough.deleteEntity(kindIndex, value);
	}

	@Override
	public void updateEntity(	int kindIndex,
								Long ID,
								int[] fields,
								Object[] oldValues,
								Object[] newValues)
	{
		updatedEntities.add(new EntityID(kindIndex, ID));
		writeThrough.updateEntity(kindIndex, ID, fields, oldValues, newValues);
	}

	@Override
	public void newConnection(int relationIndex, Long a, Long b)
	{
		updatedConnections.add(new ConnectionID(relationIndex, a, b));
		writeThrough.newConnection(relationIndex, a, b);
	}

	@Override
	public void deleteConnection(int relationIndex, Long a, Long b)
	{
		updatedConnections.add(new ConnectionID(relationIndex, a, b));
		writeThrough.deleteConnection(relationIndex, a, b);
	}

	@Override
	public void updateSingleton(int singletonIndex,
								int attributeIndex,
								Object oldValue,
								Object newValue)
	{
		updatedSingletons.add(singletonIndex);
		writeThrough.updateSingleton(singletonIndex, attributeIndex, oldValue, newValue);
	}

}

