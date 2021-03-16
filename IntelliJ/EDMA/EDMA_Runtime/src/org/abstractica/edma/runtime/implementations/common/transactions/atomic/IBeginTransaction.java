package org.abstractica.edma.runtime.implementations.common.transactions.atomic;

public interface IBeginTransaction extends IAtomicUpdate
{
	public long getFromVersion();
	public long getToVersion();
}
