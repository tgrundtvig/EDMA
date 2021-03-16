package org.abstractica.edma.util.collections.intf;

import java.util.Comparator;

import org.abstractica.edma.util.cache.ObjectNotFoundException;

public interface OrderedSetManager<K>
{
	public OrderedSet<K> createNewOrderedSet(Comparator<K> comparator);
	public OrderedSet<K> loadOrderedSet(long id) throws ObjectNotFoundException;
	public void releaseOrderedSet(OrderedSet<K> set);
	public void deleteOrderedSet(OrderedSet<K> set);
}
