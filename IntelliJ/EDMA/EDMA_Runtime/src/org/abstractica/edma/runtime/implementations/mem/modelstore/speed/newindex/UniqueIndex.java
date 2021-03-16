package org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex;

import org.abstractica.edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.DMap;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.DSet;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.HashKey;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.HashUpdate;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManager;

public class UniqueIndex
{
	private CollectionFactory cf;
	private DMap<HashKey, Long> map;
	private DSet<Long> nullSet;

	public UniqueIndex(CollectionFactory cf)
	{
		this.cf = cf;
		map = null;
		nullSet = null;
	}
	
	public boolean isEmpty()
	{
		return (map == null && nullSet == null);
	}
	
	public void clear()
	{
		if(map != null)
		{
			map.delete();
			map = null;
		}
		if(nullSet != null)
		{
			nullSet.delete();
			nullSet = null;
		}
	}

	public boolean isUnique(HashKey key)
	{
		if(map == null) return true;
		if(key.isNull()) return true;
		return (map.get(key) == null);
	}

	public Long getFromUnique(HashKey key)
	{
		if(map == null) return null;
		return map.get(key);
	}

	public int getWhereNull(ISetManager setManager, boolean doCopy)
	{
		if(nullSet == null) return setManager.emptySet();
		return setManager.newSet(nullSet, doCopy);
	}

	public void add(HashKey key, Long ID)
	{
		if(key.isNull()) addNull(ID);
		else
		{
			if(map == null) map = cf.newMap();
			map.put(key, ID);
		}
	}
	
	private void addNull(Long ID)
	{
		if(nullSet == null) nullSet = cf.newSet();
		nullSet.add(ID);
	}

	public void remove(HashKey key, Long ID)
	{
		if(key.isNull()) removeNull(ID);
		else
		{
			map.remove(key);
			if(map.isEmpty())
			{
				map.delete();
				map = null;
			}
		}
	}
	
	private void removeNull(Long ID)
	{
		nullSet.remove(ID);
		if(nullSet.isEmpty())
		{
			nullSet.delete();
			nullSet = null;
		}
	}
	
	public void update(HashUpdate upd)
	{
		if(upd.oldKey.isNull())
		{
			removeNull(upd.ID);
			add(upd.newKey, upd.ID);
			return;
		}
		map.remove(upd.oldKey);
		if(upd.newKey.isNull())
		{
			if(map.isEmpty())
			{
				map.delete();
				map = null;
			}
			addNull(upd.ID);
			return;
		}
		map.put(upd.newKey, upd.ID);
	}
	
}
