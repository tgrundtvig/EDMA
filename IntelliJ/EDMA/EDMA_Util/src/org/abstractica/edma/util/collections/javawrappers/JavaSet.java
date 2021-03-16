package org.abstractica.edma.util.collections.javawrappers;

import java.util.Iterator;
import java.util.Set;

import org.abstractica.edma.util.collections.intf.BaseSet;

public class JavaSet<E> implements BaseSet<E>
{
	private long id;
	private Set<E> set;
	private boolean readOnly;
	
	public JavaSet(long id, Set<E> set, boolean readOnly)
	{
		super();
		this.id = id;
		this.set = set;
		this.readOnly = readOnly;
	}

	@Override
	public Iterator<E> iterator()
	{
		return set.iterator();
	}

	@Override
	public boolean isReadOnly()
	{
		return readOnly;
	}

	@Override
	public boolean contains(E item)
	{
		return set.contains(item);
	}

	@Override
	public int size()
	{
		return set.size();
	}

	@Override
	public void makeReadOnly()
	{
		readOnly = true;
	}

	@Override
	public boolean add(E item)
	{
		if(readOnly) throw new IllegalStateException("This set is read only!");
		return set.add(item);
	}

	@Override
	public boolean remove(E item)
	{
		if(readOnly) throw new IllegalStateException("This set is read only!");
		return set.remove(item);
	}

	@Override
	public void clear()
	{
		if(readOnly) throw new IllegalStateException("This set is read only!");
		set.clear();
	}

	@Override
	public long getID()
	{
		return id;
	}

}
