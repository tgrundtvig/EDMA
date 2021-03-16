package org.abstractica.edma.util.collections.treemap;

import java.util.Comparator;

import org.abstractica.edma.util.cache.ObjectNotFoundException;
import org.abstractica.edma.util.collections.intf.OrderedSet;
import org.abstractica.edma.util.collections.intf.OrderedSetManager;
import org.abstractica.edma.util.collections.treemap.btreemap.basic.BTreeManager;
import org.abstractica.edma.util.serialize.Serializer;

public class TreeSetManagerImpl<K> implements OrderedSetManager<K>
{
	BTreeManager btreeManager;
	private Serializer<K> keySerializer;

	public TreeSetManagerImpl(BTreeManager manager, Serializer<K> keySerializer)
	{
		super();
		this.keySerializer = keySerializer;
		this.btreeManager = manager;
	}

	@SuppressWarnings("unchecked")
	public OrderedSet<K> createNewOrderedSet(Comparator<K> comparator)
	{
		return new TreeSetImpl<>(btreeManager.createNewTreeMap(	false,
																(Comparator<Object>) comparator,
																(Serializer<Object>) keySerializer,
																null));
	}

	@Override
	public OrderedSet<K> loadOrderedSet(long id) throws ObjectNotFoundException
	{
		return new TreeSetImpl<>(btreeManager.loadTreeMap(id));
	}

	@Override
	public void releaseOrderedSet(OrderedSet<K> set)
	{
		btreeManager.releaseTreeMap(((TreeSetImpl<K>) set).getBTree());
	}

	@Override
	public void deleteOrderedSet(OrderedSet<K> set)
	{
		btreeManager.deleteTreeMap(((TreeSetImpl<K>) set).getBTree());
	}

}
