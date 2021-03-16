package org.abstractica.edma.util.collections.intf;

import java.util.Comparator;

import org.abstractica.edma.util.cache.ObjectNotFoundException;

public interface OrderedMapManager<K, V>
{
	public OrderedMap<K, V> createNewOrderedMap(Comparator<K> comparator);
	public OrderedMap<K, V> loadOrderedMap(long id) throws ObjectNotFoundException;
	public void releaseOrderedMap(OrderedMap<K, V> map);
	public void deleteOrderedMap(OrderedMap<K, V> map);
}
