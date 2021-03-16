package org.abstractica.edma.util.concurrency.impl;

import org.abstractica.edma.util.ErrorLog;

public class ExclusiveResourceManager<E>
{
	private E resource;
	private int resourceRequests;
	
	public ExclusiveResourceManager(E resource)
	{
		this.resource = resource;
		this.resourceRequests = 0;
	}
	
	public synchronized E getResource()
	{
		++resourceRequests;
		while(resource == null)
		{
			try
			{
				wait();
			}
			catch(InterruptedException e){}
		}
		--resourceRequests;
		E res = resource;
		resource = null;
		return res;
	}
	
	public synchronized E getResourceYield()
	{
		while(resource == null || resourceRequests > 0)
		{
			try
			{
				wait();
			}
			catch(InterruptedException e){}
		}
		E res = resource;
		resource = null;
		return res;
	}
	
	public synchronized E getResourceIfNotInUse()
	{
		if(resource == null) return null;
		E res = resource;
		resource = null;
		return res;
	}	
	
	public synchronized void returnResource(E resource)
	{
		if(this.resource != null)
		{
			ErrorLog.logError("Exclusive resource manager used wrong");
			throw new RuntimeException("Resource has already been returned!");
		}
		this.resource = resource; 
		notifyAll();
	}
	
	public synchronized int getNumberOfRequests()
	{
		return resourceRequests;
	}
}
