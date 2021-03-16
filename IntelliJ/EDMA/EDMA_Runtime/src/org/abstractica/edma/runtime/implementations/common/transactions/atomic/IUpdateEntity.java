package org.abstractica.edma.runtime.implementations.common.transactions.atomic;

public interface IUpdateEntity extends IAtomicUpdate
{
	public int getKindIndex();
	public Long getID();
	public int[] getFieldIndexes();
	public Object[] getOldValues();
	public Object[] getNewValues();
}
