package org.abstractica.edma.util.concurrency.impl;

import java.util.concurrent.TimeoutException;

import org.abstractica.edma.util.concurrency.ClosedException;
import org.abstractica.edma.util.concurrency.ISynchBuffer;
import org.abstractica.edma.util.concurrency.PausedException;

public class SynchArrayQueue<E> implements ISynchBuffer<E>
{
	private String name;
	private Object[] array;
	private int head;
	private int tail;
	private int size;
	private boolean closed;
	private boolean paused;
	
	public SynchArrayQueue(int capacity, String name)
	{
		this.name = name;
		array = new Object[capacity];
		head = capacity - 1;
		tail = capacity - 1;
		size = 0;
		closed = true;
		paused = false;
	}
	
	@Override
	public synchronized void put(E item) throws ClosedException
	{
		while(!closed)
		{
			boolean added = p_add(item);
			if(added)
			{
				notifyAll();
				return;
			}
			try
			{
				System.out.println(name + " is full!");
				wait();
			}
			catch(InterruptedException e){}
		}
		throw new ClosedException();
	}
	
	@Override
	public synchronized boolean put(E item, long timeout) throws ClosedException
	{
		long endTime = System.nanoTime() + (timeout * 1000000);
		while(!closed)
		{
			boolean added = p_add(item);
			if(added)
			{
				notifyAll();
				return true;
			}
			long curTime = System.nanoTime();
			if(curTime > endTime)
			{
				return false;
			}
			try
			{
				long remainingTime = ((endTime - curTime) / 1000000) + 1;
				wait(remainingTime);
			}
			catch(InterruptedException e){}
		}
		throw new ClosedException();
	}

	@Override
	public synchronized boolean offer(E item) throws ClosedException
	{
		if(closed) throw new ClosedException();
		boolean res = p_add(item);
		if(res) notifyAll();
		return res;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized E take() throws ClosedException, PausedException
	{
		while((size > 0 || !closed) && !paused)
		{
			Object res = p_poll();
			if(res != null)
			{
				notifyAll();
				return (E) res;
			}
			try
			{
				wait();
			}
			catch(InterruptedException e){}
		}
		if(size > 0 || !closed) throw new PausedException();
		throw new ClosedException();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized E take(long timeout) throws ClosedException, PausedException, TimeoutException
	{
		long endTime = System.nanoTime() + (timeout * 1000000);
		while((size > 0 || !closed) && !paused)
		{
			Object res = p_poll();
			if(res != null)
			{
				notifyAll();
				return (E) res;
			}
			long curTime = System.nanoTime();
			if(curTime > endTime)
			{
				throw new TimeoutException();
			}
			try
			{
				long remainingTime = ((endTime - curTime) / 1000000) + 1;
				wait(remainingTime);
			}
			catch(InterruptedException e){}
		}
		if(size > 0 || !closed) throw new PausedException();
		throw new ClosedException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized E poll() throws ClosedException
	{
		Object res = p_poll();
		if(res != null)
		{
			notifyAll();
		}
		else if(closed) throw new ClosedException();
		return (E) res;
	}

	@Override
	public synchronized void close()
	{
		closed = true;
		notifyAll();
	}

	@Override
	public synchronized void open()
	{
		closed = false;
		notifyAll();
	}

	@Override
	public synchronized boolean isClosed()
	{
		return closed;
	}
	
	private boolean p_add(Object o)
	{
		if(size == array.length) return false;
		head = (head + 1) % array.length;
		array[head] = o;
		++size;
		return true;
	}
	
	private Object p_poll()
	{
		if(size == 0) return null;
		tail = (tail + 1) % array.length;
		--size;
		return array[tail];
	}

	@Override
	public synchronized void pause()
	{
		paused = true;
		notifyAll();
	}

	@Override
	public synchronized void resume()
	{
		paused = false;
		notifyAll();
	}
}
