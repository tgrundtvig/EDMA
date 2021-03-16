package org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex;

import java.util.Map;

import org.abstractica.edma.metamodel.IMetaIndex;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.HashKey;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.HashUpdate;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManager;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.runtime.intf.exceptions.UniqueException;

public class KindUniqueIndex implements IKindBaseIndex
{
	private IMetaIndex metaIndex;
	private UniqueIndex index;
	
	public KindUniqueIndex(IMetaIndex metaIndex, CollectionFactory cf)
	{
		this.metaIndex = metaIndex;
		index = new UniqueIndex(cf);
	}
	
	@Override
	public Long getFromUnique(Object[] values)
	{
		HashKey key = IndexUtil.getHashKey(values, metaIndex);
		return index.getFromUnique(key);
	}

	@Override
	public int getEquals(Object[] values, ISetManager setManager, boolean doCopy)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getLessThan(	Object[] values,
							boolean inclusive,
							ISetManager setManager,
							boolean doCopy)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getGreaterThan(	Object[] values,
								boolean inclusive,
								ISetManager setManager,
								boolean doCopy)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRange(Object[] min,
						boolean minInclusive,
						Object[] max,
						boolean maxInclusive,
						ISetManager setManager,
						boolean doCopy)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getWhereNull(ISetManager setManager, boolean doCopy)
	{
		return index.getWhereNull(setManager, doCopy);
	}

	@Override
	public void testUniqueness(IEntity entity) throws UniqueException
	{
		HashKey key = IndexUtil.getHashKey(entity, metaIndex);
		if(!index.isUnique(key)) throw new UniqueException(metaIndex);
	}

	@Override
	public void add(IEntity entity)
	{
		HashKey key = IndexUtil.getHashKey(entity, metaIndex);
		index.add(key, entity.getID());
	}

	@Override
	public void remove(IEntity entity)
	{
		HashKey key = IndexUtil.getHashKey(entity, metaIndex);
		index.remove(key, entity.getID());
	}

	@Override
	public Object extractUpdate(IEntity entity, Map<Integer, Object> updates)
	{
		if(!IndexUtil.needUpdate(updates, metaIndex)) return null;
		return IndexUtil.extractHashUpdate(entity, updates, metaIndex);
	}

	@Override
	public void testUniqueness(Object update) throws UniqueException
	{
		if(update == null) return;
		HashUpdate upd = (HashUpdate) update;
		if(!index.isUnique(upd.newKey)) throw new UniqueException(metaIndex);
	}

	@Override
	public void doUpdate(Object update)
	{
		if(update == null) return;
		index.update((HashUpdate) update);
	}

	@Override
	public void directUpdate(IEntity entity, Map<Integer, Object> updates)
	{
		HashUpdate upd = IndexUtil.extractHashUpdate(entity, updates, metaIndex);
		index.update(upd);
	}

}
