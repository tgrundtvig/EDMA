package org.abstractica.edma.util.concurrency;

public interface ISynchInput<E>
{
	public void put(E item) throws ClosedException;
	public boolean put(E item, long timeout) throws ClosedException;
	public boolean offer(E item) throws ClosedException;
}
