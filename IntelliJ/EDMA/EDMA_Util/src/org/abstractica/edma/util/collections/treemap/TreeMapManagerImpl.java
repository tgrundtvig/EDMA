package org.abstractica.edma.util.collections.treemap;

import java.util.Comparator;

import org.abstractica.edma.util.cache.ObjectNotFoundException;
import org.abstractica.edma.util.collections.intf.OrderedMap;
import org.abstractica.edma.util.collections.intf.OrderedMapManager;
import org.abstractica.edma.util.collections.treemap.btreemap.basic.BTreeManager;
import org.abstractica.edma.util.serialize.Serializer;

public class TreeMapManagerImpl<K, V> implements OrderedMapManager<K, V>
{
	private BTreeManager btreeManager;
	private Serializer<K> keySerializer;
	private Serializer<V> valueSerializer;

	public TreeMapManagerImpl(	BTreeManager manager,
								Serializer<K> keySerializer,
								Serializer<V> valueSerializer)
	{
		super();
		this.keySerializer = keySerializer;
		this.valueSerializer = valueSerializer;
		this.btreeManager = manager;
	}

	@Override
	@SuppressWarnings("unchecked")
	public OrderedMap<K, V> createNewOrderedMap(Comparator<K> comparator)
	{
		return new TreeMapImpl<>(btreeManager.createNewTreeMap(	true,
																(Comparator<Object>) comparator,
																(Serializer<Object>) keySerializer,
																(Serializer<Object>) valueSerializer));
	}

	@Override
	public OrderedMap<K, V> loadOrderedMap(long id) throws ObjectNotFoundException
	{
		return new TreeMapImpl<>(btreeManager.loadTreeMap(id));
	}

	@Override
	public void releaseOrderedMap(OrderedMap<K, V> map)
	{
		btreeManager.releaseTreeMap(((TreeMapImpl<K, V>) map).getBTree());
	}

	@Override
	public void deleteOrderedMap(OrderedMap<K, V> map)
	{
		btreeManager.deleteTreeMap(((TreeMapImpl<K, V>) map).getBTree());
	}
}
