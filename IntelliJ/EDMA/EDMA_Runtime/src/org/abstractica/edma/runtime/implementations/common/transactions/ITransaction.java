package org.abstractica.edma.runtime.implementations.common.transactions;

import java.util.List;

import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate;

public interface ITransaction
{
	public String getName();
	public long getFromVersion();
	public long getToVersion();
	public List<IAtomicUpdate> getUpdates();
}
