package org.abstractica.edma.runtime.implementations.mem.sets;

import java.util.HashSet;
import java.util.Iterator;

public class JavaLongSet implements ILongSet
{
	private HashSet<Long> set;
	
	public JavaLongSet()
	{
		set = new HashSet<>();
	}
	
	public JavaLongSet(ILongSet set)
	{
		this.set = new HashSet<>(set.size());
		for(Long l : set)
		{
			this.set.add(l);
		}
	}

	@Override
	public void add(Long l)
	{
		set.add(l);
	}

	@Override
	public void remove(Long l)
	{
		set.remove(l);
	}

	@Override
	public boolean contains(Long l)
	{
		return set.contains(l);
	}

	@Override
	public int size()
	{
		return set.size();
	}

	@Override
	public Iterator<Long> iterator()
	{
		return set.iterator();
	}

}
