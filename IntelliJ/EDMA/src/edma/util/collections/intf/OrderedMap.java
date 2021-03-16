package edma.util.collections.intf;

import java.util.Comparator;

public interface OrderedMap<K,V> extends BaseMap<K,V>
{	
	public Comparator<K> getComparator();
	public OrderedSet<K> getKeySetView();

	//SubMaps
	public OrderedMap<K,V> headMap(K toKey, boolean toInclusive);
	public OrderedMap<K,V> tailMap(K fromKey, boolean fromInclusive);
	public OrderedMap<K,V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive);
	public OrderedMap<K,V> reversedMap();
}
