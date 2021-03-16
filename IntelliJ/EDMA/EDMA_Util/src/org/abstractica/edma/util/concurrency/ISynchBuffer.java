package org.abstractica.edma.util.concurrency;

public interface ISynchBuffer<E> extends ISynchInput<E>, ISynchOutput<E>
{
	public void close();
	public void open();
	public boolean isClosed();
	public void pause();
	public void resume();
}
