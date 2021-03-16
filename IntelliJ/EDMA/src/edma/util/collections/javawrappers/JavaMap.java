package edma.util.collections.javawrappers;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import edma.util.collections.intf.BaseMap;

public class JavaMap<K,V> implements BaseMap<K,V>
{
	private long id;
	private Map<K,V> map;
	private boolean readOnly;
	
	public JavaMap(long id, Map<K, V> map, boolean readOnly)
	{
		super();
		this.id = id;
		this.map = map;
		this.readOnly = readOnly;
	}

	@Override
	public Iterator<Entry<K, V>> iterator()
	{
		return map.entrySet().iterator();
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
	public long getID()
	{
		return id;
	}

}
