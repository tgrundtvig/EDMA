package org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex;

import java.util.Set;

import org.abstractica.edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.DMap;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.DSet;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.HashKey;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.HashUpdate;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManager;

public class EqualIndex
{
	private CollectionFactory cf;
	private DMap<HashKey, DSet<Long>> map;

	public EqualIndex(CollectionFactory cf)
	{
		this.cf = cf;
		map = null;
	}
	
	public boolean isEmpty()
	{
		return (map == null);
	}
	
	public void clear()
	{
		if(map == null) return;
		for(DSet<Long> set : map.values())
		{
			set.delete();
		}
		map.delete();
	}

	public int getEquals(HashKey key, ISetManager setManager, boolean doCopy)
	{
		if(map == null) return setManager.emptySet();
		Set<Long> res = map.get(key);
		if(res == null) return setManager.emptySet();
		return setManager.newSet(res, doCopy);
	}

	public void add(HashKey key, Long ID)
	{
		DSet<Long> set = null;
		if(map == null)
		{
			map = cf.newMap();
		}
		else
		{
			set = map.get(key);
		}
		if(set == null)
		{
			set = cf.newSet();
			map.put(key, set);
		}
		set.add(ID);
	}

	public void remove(HashKey key, Long ID)
	{
		DSet<Long> set = map.get(key);
		set.remove(ID);
		if(set.isEmpty())
		{
			map.remove(key);
			set.delete();
			if(map.isEmpty())
			{
				map.delete();
				map = null;
			}
		}
	}
	
	public void update(HashUpdate upd)
	{
		DSet<Long> set = map.get(upd.oldKey);
		set.remove(upd.ID);
		if(set.isEmpty())
		{
			map.remove(upd.oldKey);
			set.delete();
		}
		set = map.get(upd.newKey);
		if(set == null)
		{
			set = cf.newSet();
			map.put(upd.newKey, set);
		}
		set.add(upd.ID);
	}
	
}
