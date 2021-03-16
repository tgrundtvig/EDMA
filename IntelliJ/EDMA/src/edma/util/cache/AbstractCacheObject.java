package edma.util.cache;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import edma.util.linkedlist.Linkable;

public abstract class AbstractCacheObject<T, O> implements CacheObject<T, O>
{
	private O owner;
	private long id;
	private Linkable<T> hookNext;
	private Linkable<T> hookPrev;
	private int refCount;
	private boolean updated;
	
	public AbstractCacheObject()
	{
		super();
		id = -1L;
		hookNext = this;
		hookPrev = this;
		refCount = 0;
		updated = false;
		owner = null;
	}

	@Override
	public abstract T getActualObject();
	
	@Override
	public abstract void swapOut(DataOutput out) throws IOException;

	@Override
	public abstract void swapIn(DataInput in) throws IOException;

	@Override
	public abstract void objectCreated();

	@Override
	public abstract void objectDeleted();
	
	@Override
	public void setOwner(O owner)
	{
		this.owner = owner;
	}
	
	@Override
	public O getOwner()
	{
		return owner;
	}
	
	@Override
	public Linkable<T> getNext()
	{
		return hookNext;
	}

	@Override
	public Linkable<T> getPrev()
	{
		return hookPrev;
	}

	@Override
	public void setNext(Linkable<T> next)
	{
		this.hookNext = next;
	}

	@Override
	public void setPrev(Linkable<T> prev)
	{
		this.hookPrev = prev;
	}

	@Override
	public long getID()
	{
		return id;
	}

	@Override
	public void setID(long id)
	{
		this.id = id;
	}

	@Override
	public void clearUpdateFlag()
	{
		this.updated = false;
	}
	
	@Override
	public void setUpdateFlag()
	{
		this.updated = true;
	}

	@Override
	public boolean isUpdated()
	{
		return updated;
	}

	@Override
	public boolean isFree()
	{
		return (refCount == 0);
	}

	@Override
	public void incRefCount()
	{
		++refCount;
	}

	@Override
	public void decRefCount()
	{
		--refCount;
		if(refCount < 0) throw new RuntimeException("Refcount is negative!");
	}

	@Override
	public void resetRefCount()
	{
		refCount = 0;
	}
}
