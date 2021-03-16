package org.abstractica.edma.util.collections.javawrappers;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.abstractica.edma.util.cache.ObjectNotFoundException;
import org.abstractica.edma.util.collections.intf.OrderedSet;
import org.abstractica.edma.util.collections.intf.OrderedSetManager;

public class JavaOrderedSetManager<K> implements OrderedSetManager<K>
{
	private long lastID;
	private Map<Long, OrderedSet<K>> map;
	
	@Override
	public OrderedSet<K> createNewOrderedSet(Comparator<K> comparator)
	{
		NavigableSet<K> s = new TreeSet<K>();
		OrderedSet<K> res = new JavaOrderedSet<>(lastID++, s, false);
		map.put(res.getID(), res);
		return res;
	}

	@Override
	public OrderedSet<K> loadOrderedSet(long id) throws ObjectNotFoundException
	{
		OrderedSet<K> res = map.get(id);
		if(res == null) throw new ObjectNotFoundException();
		return res;
	}

	@Override
	public void releaseOrderedSet(OrderedSet<K> set)
	{
		//Do nothing
	}

	@Override
	public void deleteOrderedSet(OrderedSet<K> set)
	{
		// TODO Auto-generated method stub
		
	}

}
