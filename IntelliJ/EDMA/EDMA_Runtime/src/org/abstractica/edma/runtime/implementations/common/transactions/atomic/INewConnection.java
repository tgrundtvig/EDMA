package org.abstractica.edma.runtime.implementations.common.transactions.atomic;

public interface INewConnection extends IAtomicUpdate
{
	public int getRelationIndex();
	public Long getA();
	public Long getB();
}
