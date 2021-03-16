package org.abstractica.edma.runtime.implementations.common.transactions.atomic;

public interface IEndTransaction
{
	public long getFromVersion();
	public long getToVersion();
}
