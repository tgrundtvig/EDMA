package edma.runtime.implementations.mem.modelstore.speed.newindex;

import java.util.Collection;
import java.util.Map;

import edma.metamodel.IMetaIndex;
import edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import edma.runtime.implementations.common.collectionfactory.DMap;
import edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.HashKey;
import edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.HashUpdate;
import edma.runtime.implementations.mem.sets.ISetManager;
import edma.runtime.intf.IEntity;
import edma.runtime.intf.exceptions.UniqueException;

public class RelEqualIndex implements IRelBaseIndex
{
	private CollectionFactory cf;
	private IMetaIndex metaIndex;
	private DMap<Long, EqualIndex> indexMap;
	
	public RelEqualIndex(IMetaIndex metaIndex, CollectionFactory cf)
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
		EqualIndex index = indexMap.get(fID);
		if(index == null) return setManager.emptySet();
		HashKey key = IndexUtil.getHashKey(values, metaIndex);
		return index.getEquals(key, setManager, doCopy);
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
		EqualIndex index = indexMap.get(fID);
		if(index == null) return setManager.emptySet();
		HashKey key = IndexUtil.getNullHashKey(metaIndex);
		return index.getEquals(key, setManager, doCopy);
	}

	@Override
	public void testUniqueness(Long fID, IEntity entity) throws UniqueException {}

	@Override
	public void add(Long fID, IEntity entity)
	{
		EqualIndex index = indexMap.get(fID);
		if(index == null)
		{
			index = new EqualIndex(cf);
			indexMap.put(fID, index);
		}
		HashKey key = IndexUtil.getHashKey(entity, metaIndex);
		index.add(key, entity.getID());
	}

	@Override
	public void remove(Long fID, IEntity entity)
	{
		EqualIndex index = indexMap.get(fID);
		HashKey key = IndexUtil.getHashKey(entity, metaIndex);
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
		Long ID = entity.getID();
		for(Long fID : fIDs)
		{
			EqualIndex index = indexMap.get(fID);
			index.remove(key, ID);
			if(index.isEmpty())
			{
				indexMap.remove(fID);
			}
		}	
	}
	
	@Override
	public void removeAll(Long fID)
	{
		EqualIndex index = indexMap.get(fID);
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
	public void testUniqueness(Object update) throws UniqueException {}

	@Override
	public void doUpdate(Object update)
	{
		if(update == null) return;
		RelHashUpdate upd = (RelHashUpdate) update;
		for(Long fID : upd.fIDs)
		{
			EqualIndex index = indexMap.get(fID);
			index.update(upd.upd);
		}
	}

	@Override
	public void directUpdate(	Collection<Long> fIDs,
								IEntity entity,
								Map<Integer, Object> updates)
	{
		if(!IndexUtil.needUpdate(updates, metaIndex)) return;
		HashUpdate upd = IndexUtil.extractHashUpdate(entity, updates, metaIndex);
		for(Long fID : fIDs)
		{
			EqualIndex index = indexMap.get(fID);
			index.update(upd);
		}
	}
}
