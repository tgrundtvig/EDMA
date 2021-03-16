package edma.runtime.implementations.common.transactions.impl;

import java.util.ArrayList;
import java.util.List;

import edma.runtime.implementations.common.transactions.ITransaction;
import edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate;

public class Transaction implements ITransaction
{
	private final String name;
	private final long fromVersion;
	private long toVersion;
	private ArrayList<IAtomicUpdate> list;
	
	public Transaction(String name, long fromVersion)
	{
		this.name = name;
		this.fromVersion = fromVersion;
		list = new ArrayList<IAtomicUpdate>();
	}
	
	public Transaction(String name, long fromVersion, long toVersion)
	{
		this.name = name;
		this.fromVersion = fromVersion;
		this.toVersion = toVersion;
		list = new ArrayList<IAtomicUpdate>();
	}
	
	public void add(IAtomicUpdate update)
	{
		list.add(update);
	}
	
	public void setToVersion(long toVersion)
	{
		this.toVersion = toVersion;
	}
	
	@Override
	public List<IAtomicUpdate> getUpdates()
	{
		return list;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public long getFromVersion()
	{
		return fromVersion;
	}

	@Override
	public long getToVersion()
	{
		return toVersion;
	}
}
