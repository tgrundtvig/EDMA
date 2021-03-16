package edma.util.concurrency.impl;

import edma.util.concurrency.ISynchCounter;

public class SynchCounter implements ISynchCounter
{
	private int value;
	
	public SynchCounter(int value)
	{
		this.value = value;
	}

	@Override
	public synchronized void modify(int i)
	{
		value += i;
		notifyAll();
	}

	@Override
	public synchronized void waitFor(int i)
	{
		while(value != i)
		{
			try
			{
				wait();
			}
			catch(InterruptedException e){}
		}
	}

}
