package edma.util.collections.javawrappers;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;

import edma.util.collections.intf.OrderedSet;

public class JavaOrderedSet<K> implements OrderedSet<K>
{
	private long id;
	private NavigableSet<K> set;
	private boolean readOnly;

	public JavaOrderedSet(long id, NavigableSet<K> set, boolean readOnly)
	{
		super();
		this.id = id;
		this.set = set;
		this.readOnly = readOnly;
	}

	@Override
	public boolean isReadOnly()
	{
		return readOnly;
	}

	@Override
	public boolean contains(K item)
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
	public boolean add(K item)
	{
		if(readOnly) throw new IllegalStateException("This set is read only!");
		return set.add(item);
	}

	@Override
	public boolean remove(K item)
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
	public Iterator<K> iterator()
	{
		return set.iterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Comparator<K> getComparator()
	{
		return (Comparator<K>) set.comparator();
	}

	@Override
	public OrderedSet<K> headSet(K toKey, boolean toInclusive)
	{
		return new JavaOrderedSet<>(id, set.headSet(toKey, toInclusive), readOnly);
	}

	@Override
	public OrderedSet<K> tailSet(K fromKey, boolean fromInclusive)
	{
		return new JavaOrderedSet<>(id, set.tailSet(fromKey, fromInclusive), readOnly);
	}

	@Override
	public OrderedSet<K> subSet(K fromKey,
								boolean fromInclusive,
								K toKey,
								boolean toInclusive)
	{
		return new JavaOrderedSet<>(id, set.subSet(fromKey, fromInclusive, toKey, toInclusive), readOnly);
	}

	@Override
	public OrderedSet<K> reversedSet()
	{
		return new JavaOrderedSet<>(id, set.descendingSet(), readOnly);
	}

	@Override
	public long getID()
	{
		return id;
	}

}
