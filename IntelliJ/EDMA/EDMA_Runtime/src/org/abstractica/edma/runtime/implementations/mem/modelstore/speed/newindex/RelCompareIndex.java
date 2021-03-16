package org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex;

import java.util.Collection;
import java.util.Map;

import org.abstractica.edma.metamodel.IMetaIndex;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.DMap;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.CompareKey;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.CompareUpdate;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManager;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.runtime.intf.exceptions.UniqueException;

public class RelCompareIndex implements IRelBaseIndex
{
	private CollectionFactory cf;
	private IMetaIndex metaIndex;
	private DMap<Long, CompareIndex> indexMap;
	
	public RelCompareIndex(IMetaIndex metaIndex, CollectionFactory cf)
	{
		this.cf = cf;
		this.metaIndex = metaIndex;
		this.indexMap = cf.newMap();
	}

	@Override
	public Long getFromUnique(Long fid, Object[] values)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getEquals(	Long fID,
							Object[] values,
							ISetManager setManager,
							boolean doCopy)
	{
		CompareIndex index = indexMap.get(fID);
		if(index == null) return setManager.emptySet();
		CompareKey min = IndexUtil.getCompareKeyLow(values, metaIndex);
		CompareKey max = IndexUtil.getCompareKeyHigh(values, metaIndex);
		return index.getRange(min, true, max, true, setManager, doCopy);
	}

	@Override
	public int getLessThan(	Long fID,
							Object[] values,
							boolean inclusive,
							ISetManager setManager,
							boolean doCopy)
	{
		CompareIndex index = indexMap.get(fID);
		if(index == null) return setManager.emptySet();
		CompareKey key = inclusive ? IndexUtil.getCompareKeyHigh(	values,
																	metaIndex)
				: IndexUtil.getCompareKeyLow(values, metaIndex);
		return index.getLessThan(key, inclusive, setManager, doCopy);
	}

	@Override
	public int getGreaterThan(	Long fID,
								Object[] values,
								boolean inclusive,
								ISetManager setManager,
								boolean doCopy)
	{
		CompareIndex index = indexMap.get(fID);
		if(index == null) return setManager.emptySet();
		CompareKey key = inclusive ? IndexUtil.getCompareKeyLow(values,
																metaIndex)
				: IndexUtil.getCompareKeyHigh(values, metaIndex);
		return index.getGreaterThan(key, inclusive, setManager, doCopy);
	}

	@Override
	public int getRange(Long fID,
						Object[] min,
						boolean minInclusive,
						Object[] max,
						boolean maxInclusive,
						ISetManager setManager,
						boolean doCopy)
	{
		CompareIndex index = indexMap.get(fID);
		if(index == null) return setManager.emptySet();
		CompareKey minKey = minInclusive ? IndexUtil.getCompareKeyLow(min, metaIndex) :
			IndexUtil.getCompareKeyHigh(min, metaIndex);
		CompareKey maxKey = maxInclusive ? IndexUtil.getCompareKeyHigh(max, metaIndex) :
			IndexUtil.getCompareKeyLow(max, metaIndex);
		return index.getRange(minKey, minInclusive, maxKey, maxInclusive, setManager, doCopy);
	}

	@Override
	public int getWhereNull(Long fID, ISetManager setManager, boolean doCopy)
	{
		CompareIndex index = indexMap.get(fID);
		if(index == null) return setManager.emptySet();
		CompareKey nullKey = IndexUtil.getNullCompareKey(metaIndex);
		return index.getLessThan(nullKey, true, setManager, doCopy);
	}

	@Override
	public void testUniqueness(Long fID, IEntity entity) throws UniqueException {}

	@Override
	public void add(Long fID, IEntity entity)
	{
		CompareIndex index = indexMap.get(fID);
		if(index == null)
		{
			index = new CompareIndex(cf);
			indexMap.put(fID, index);
		}
		CompareKey key = IndexUtil.getCompareKey(entity, metaIndex);
		index.add(key);
	}

	@Override
	public void remove(Long fID, IEntity entity)
	{
		CompareKey key = IndexUtil.getCompareKey(entity, metaIndex);
		CompareIndex index = indexMap.get(fID);
		index.remove(key);
		if(index.isEmpty())
		{
			indexMap.remove(fID);
		}
	}

	@Override
	public void remove(Collection<Long> fIDs, IEntity entity)
	{
		CompareKey key = IndexUtil.getCompareKey(entity, metaIndex);
		for(Long fID : fIDs)
		{
			CompareIndex index = indexMap.get(fID);
			index.remove(key);
			if(index.isEmpty())
			{
				indexMap.remove(fID);
			}
		}
	}
	
	@Override
	public void removeAll(Long fID)
	{
		CompareIndex index = indexMap.get(fID);
		if(index != null)
		{
			index.clear();
			indexMap.remove(fID);
		}
	}

	@Override
	public Object extractUpdate(Collection<Long> fIDs,
								IEntity entity,
								Map<Integer, Object> updates)
	{
		if(!IndexUtil.needUpdate(updates, metaIndex)) return null;
		CompareUpdate upd = IndexUtil.extractCompareUpdate(entity, updates, metaIndex);
		return new RelCompareUpdate(upd, fIDs);
	}

	@Override
	public void testUniqueness(Object update) throws UniqueException {}

	@Override
	public void doUpdate(Object update)
	{
		if(update == null) return;
		RelCompareUpdate upd = (RelCompareUpdate) update;
		for(Long fID : upd.fIDs)
		{
			CompareIndex index = indexMap.get(fID);
			index.update(upd.upd);
		}
	}

	@Override
	public void directUpdate(	Collection<Long> fIDs,
								IEntity entity,
								Map<Integer, Object> updates)
	{
		if(!IndexUtil.needUpdate(updates, metaIndex)) return;
		CompareUpdate upd = IndexUtil.extractCompareUpdate(entity, updates, metaIndex);
		for(Long fID : fIDs)
		{
			CompareIndex index = indexMap.get(fID);
			index.update(upd);
		}
	}
}
