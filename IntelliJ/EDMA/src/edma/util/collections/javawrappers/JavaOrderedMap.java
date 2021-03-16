package edma.util.collections.javawrappers;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NavigableMap;

import edma.util.collections.intf.OrderedMap;
import edma.util.collections.intf.OrderedSet;

public class JavaOrderedMap<K,V> implements OrderedMap<K,V>
{
	private long id;
	private NavigableMap<K,V> map;
	private boolean readOnly;
	
	public JavaOrderedMap(long id, NavigableMap<K,V> map, boolean readOnly)
	{
		this.id = id;
		this.map = map;
		this.readOnly = readOnly;
	}
	
	@Override
	public boolean isReadOnly()
	{
		return readOnly;
	}

	@Override
	public boolean containsKey(K key)
	{
		return map.containsKey(key);
	}

	@Override
	public V get(K key)
	{
		return map.get(key);
	}

	@Override
	public int size()
	{
		return map.size();
	}

	@Override
	public void makeReadOnly()
	{
		readOnly = true;
	}

	@Override
	public V put(K key, V value)
	{
		if(readOnly) throw new IllegalStateException("This is a read only map!");
		return map.put(key, value);
	}

	@Override
	public V remove(K key)
	{
		if(readOnly) throw new IllegalStateException("This is a read only map!");
		return map.remove(key);
	}

	@Override
	public void clear()
	{
		if(readOnly) throw new IllegalStateException("This is a read only map!");
		map.clear();
	}

	@Override
	public Iterator<Entry<K, V>> iterator()
	{
		return map.entrySet().iterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Comparator<K> getComparator()
	{
		return (Comparator<K>) map.comparator();
	}

	@Override
	public OrderedSet<K> getKeySetView()
	{
		return new JavaOrderedSet<K>(id, map.navigableKeySet(), true);
	}

	@Override
	public OrderedMap<K, V> headMap(K toKey, boolean toInclusive)
	{
		return new JavaOrderedMap<>(id, map.headMap(toKey, toInclusive), readOnly);
	}

	@Override
	public OrderedMap<K, V> tailMap(K fromKey, boolean fromInclusive)
	{
		return new JavaOrderedMap<>(id, map.tailMap(fromKey, fromInclusive), readOnly);
	}

	@Override
	public OrderedMap<K, V> subMap(	K fromKey,
									boolean fromInclusive,
									K toKey,
									boolean toInclusive)
	{
		return new JavaOrderedMap<>(id, map.subMap(fromKey, fromInclusive, toKey, toInclusive), readOnly);
	}

	@Override
	public OrderedMap<K, V> reversedMap()
	{
		return new JavaOrderedMap<>(id, map.descendingMap(), readOnly);
	}

	@Override
	public long getID()
	{
		return id;
	}

}
