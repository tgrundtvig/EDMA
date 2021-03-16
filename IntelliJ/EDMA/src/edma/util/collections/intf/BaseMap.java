package edma.util.collections.intf;

import java.util.Map.Entry;


public interface BaseMap<K, V> extends Iterable<Entry<K,V>>
{
	public long getID();
	public boolean isReadOnly();
	public boolean containsKey(K key);
	public V get(K key);
	public int size();
	public void makeReadOnly();
	
	public V put(K key, V value);
	public V remove(K key);
	public void clear();
}
