package edma.runtime.implementations.mem.modelstore.speed.newindex;

import java.util.NavigableMap;

import edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import edma.runtime.implementations.common.collectionfactory.DNavigableMap;
import edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.CompareKey;
import edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.CompareUpdate;
import edma.runtime.implementations.mem.sets.ISetManager;

public class CompareIndex
{
	private CollectionFactory cf;
	private DNavigableMap<CompareKey, Long> map;

	public CompareIndex(CollectionFactory cf)
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
		map.delete();
		map = null;
	}

	public void add(CompareKey key)
	{
		if(map == null) map = cf.newNavigableMap();
		map.put(key, key.getID());
	}

	public void remove(CompareKey key)
	{
		map.remove(key);
		if(map.isEmpty())
		{
			map.delete();
			map = null;
		}
	}

	public void update(CompareUpdate upd)
	{
		map.remove(upd.oldKey);
		map.put(upd.newKey, upd.newKey.getID());
	}

	public int getLessThan(	CompareKey upper,
	                       	boolean inclusive,
							ISetManager setManager,
							boolean doCopy)
	{
		if(map == null) return setManager.emptySet();
		NavigableMap<CompareKey, Long> subMap = map.headMap(upper, inclusive);
		return setManager.newSet2(subMap.values());
	}

	public int getGreaterThan(	CompareKey lower,
								boolean inclusive,
								ISetManager setManager,
								boolean doCopy)
	{
		if(map == null) return setManager.emptySet();
		NavigableMap<CompareKey, Long> subMap = map.tailMap(lower, inclusive);
		return setManager.newSet2(subMap.values());
	}

	public int getRange(CompareKey min,
						boolean minInclusive,
						CompareKey max,
						boolean maxInclusive,
						ISetManager setManager,
						boolean doCopy)
	{
		if(map == null) return setManager.emptySet();
		NavigableMap<CompareKey, Long> subMap = map.subMap(min,
													minInclusive,
													max,
													maxInclusive);
		return setManager.newSet2(subMap.values());
	}
}
