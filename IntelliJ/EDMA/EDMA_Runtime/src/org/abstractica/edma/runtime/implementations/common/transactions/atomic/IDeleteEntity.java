package org.abstractica.edma.runtime.implementations.common.transactions.atomic;

public interface IDeleteEntity extends IAtomicUpdate
{
	public int getKindIndex();
	public Long getID();
	public Object[] getValue();
}
