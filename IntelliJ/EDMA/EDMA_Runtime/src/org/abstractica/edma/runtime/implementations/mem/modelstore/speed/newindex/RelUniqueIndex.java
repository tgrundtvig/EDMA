package org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex;

import java.util.Collection;
import java.util.Map;

import org.abstractica.edma.metamodel.IMetaIndex;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.DMap;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.HashKey;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.HashUpdate;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManager;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.runtime.intf.exceptions.UniqueException;

public class RelUniqueIndex implements IRelBaseIndex
{
	private CollectionFactory cf;
	private IMetaIndex metaIndex;
	private DMap<Long, UniqueIndex> indexMap;
	
	public RelUniqueIndex(IMetaIndex metaIndex, CollectionFactory cf)
	{
		this.cf = cf;
		this.metaIndex = metaIndex;
		this.indexMap = cf.newMap();
	}

	@Override
	public Long getFromUnique(Long fid, Object[] values)
	{
		UniqueIndex index = indexMap.get(fid);
		if(index == null) return null;
		HashKey key = IndexUtil.getHashKey(values, metaIndex);
		return index.getFromUnique(key);
	}

	@Override
	public int getEquals(	Long fid,
							Object[] values,
							ISetManager setManager,
							boolean doCopy)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getLessThan(	Long fid,
							Object[] values,
							boolean inclusive,
							ISetManager setManager,
							boolean doCopy)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getGreaterThan(	Long fid,
								Object[] values,
								boolean inclusive,
								ISetManager setManager,
								boolean doCopy)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRange(Long fid,
						Object[] min,
						boolean minInclusive,
						Object[] max,
						boolean maxInclusive,
						ISetManager setManager,
						boolean doCopy)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getWhereNull(Long fID, ISetManager setManager, boolean doCopy)
	{
		UniqueIndex index = indexMap.get(fID);
		if(index == null) return setManager.emptySet();
		return index.getWhereNull(setManager, doCopy);
	}

	@Override
	public void testUniqueness(Long fID, IEntity entity) throws UniqueException
	{
		UniqueIndex index = indexMap.get(fID);
		if(index == null) return;
		HashKey key = IndexUtil.getHashKey(entity, metaIndex);
		if(!index.isUnique(key)) throw new UniqueException(metaIndex);
	}

	@Override
	public void add(Long fID, IEntity entity)
	{
		UniqueIndex index = indexMap.get(fID);
		if(index == null)
		{
			index = new UniqueIndex(cf);
			indexMap.put(fID, index);
		}
		HashKey key = IndexUtil.getHashKey(entity, metaIndex);
		index.add(key, entity.getID());
	}

	@Override
	public void remove(Long fID, IEntity entity)
	{
		HashKey key = IndexUtil.getHashKey(entity, metaIndex);
		UniqueIndex index = indexMap.get(fID);
		index.remove(key, entity.getID());
		if(index.isEmpty())
		{
			indexMap.remove(fID);
		}
	}

	@Override
	public void remove(Collection<Long> fIDs, IEntity entity)
	{
		HashKey key = IndexUtil.getHashKey(entity, metaIndex);
		for(Long fID : fIDs)
		{
			UniqueIndex index = indexMap.get(fID);
			index.remove(key, entity.getID());
			if(index.isEmpty())
			{
				indexMap.remove(fID);
			}
		}
	}
	
	@Override
	public void removeAll(Long fID)
	{
		UniqueIndex index = indexMap.get(fID);
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
		HashUpdate upd = IndexUtil.extractHashUpdate(entity, updates, metaIndex);
		return new RelHashUpdate(upd, fIDs);
	}

	@Override
	public void testUniqueness(Object update) throws UniqueException
	{
		if(update == null) return;
		RelHashUpdate upd = (RelHashUpdate) update;
		for(Long fID : upd.fIDs)
		{
			UniqueIndex index = indexMap.get(fID);
			if(!index.isUnique(upd.upd.newKey)){ throw new UniqueException(metaIndex); }
		}
	}

	@Override
	public void doUpdate(Object update)
	{
		if(update == null) return;
		RelHashUpdate upd = (RelHashUpdate) update;
		for(Long fID : upd.fIDs)
		{
			UniqueIndex index = indexMap.get(fID);
			index.update(upd.upd);
		}

	}

	@Override
	public void directUpdate(Collection<Long> fIDs, IEntity entity, Map<Integer, Object> updates)
	{
		if(!IndexUtil.needUpdate(updates, metaIndex)) return;
		HashUpdate upd = IndexUtil.extractHashUpdate(entity, updates, metaIndex);
		for(Long fID : fIDs)
		{
			UniqueIndex index = indexMap.get(fID);
			index.update(upd);
		}
	}
}
