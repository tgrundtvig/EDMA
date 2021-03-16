package org.abstractica.edma.runtime.implementations.mem.sets.optimized;

import java.util.Set;

public abstract class ABaseSet implements ISet
{
	private int maxSize;
	private int id;
	private IRealSet realSet;
	private Set<ISet> parents;
	
	public ABaseSet(int id, int maxSize)
	{
		this.id = id;
		this.maxSize = maxSize;
		this.realSet = null;
	}
	
	public ABaseSet(int id, int maxSize, IRealSet realSet)
	{
		this.id = id;
		this.maxSize = maxSize;
		this.realSet = realSet;
	}
	
	@Override
	public final int compareTo(ISet o)
	{
		if(this == o) return 0;
		int c = maxSize - o.getMaxSize();
		if(c != 0) return c;
		return id - o.getID();
	}
	
	@Override
	public final boolean equals(Object o)
	{
		if(this == o) return true;
		if(!(o instanceof ISet)) return false;
		ISet other = (ISet) o;
		return id == other.getID();
	}
	
	@Override
	public final int hashCode()
	{
		return id;
	}
	
	@Override
	public final int getID()
	{
		return id;
	}

	@Override
	public final SetType type()
	{
		if(realSet != null) return SetType.Real;
		return getType();
	}
	public abstract SetType getType();

	@Override
	public IUnion asUnion()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public IIntersection asIntersection()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public ISubtraction asSubtraction()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public final IRealSet toReal()
	{
		if(realSet == null)
		{
			realSet = generateRealSet();
			maxSize = realSet.getSet().size();
		}
		return realSet;
	}
	
	public abstract IRealSet generateRealSet();

	@Override
	public final int getMaxSize()
	{
		return maxSize;
	}

}
