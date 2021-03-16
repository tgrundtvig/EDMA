package org.abstractica.edma.runtime.implementations.common.transactions;

public interface IAtomicOperations
{
	public void newEntity(int kindIndex, Object[] value);
	public void deleteEntity(int kindIndex, Object[] value);
	public void updateEntity(int kindIndex, Long ID, int[] fields, Object[] oldValues, Object[] newValues);
	public void newConnection(int relationIndex, Long a, Long b);
	public void deleteConnection(int relationIndex, Long a, Long b);
	public void updateSingleton(int singletonIndex, int attributeIndex, Object oldValue, Object newValue);
}
