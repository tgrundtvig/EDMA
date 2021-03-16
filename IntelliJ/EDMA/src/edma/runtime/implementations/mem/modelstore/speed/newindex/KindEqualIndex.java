package edma.runtime.implementations.mem.modelstore.speed.newindex;

import java.util.Map;

import edma.metamodel.IMetaIndex;
import edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.HashKey;
import edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.HashUpdate;
import edma.runtime.implementations.mem.sets.ISetManager;
import edma.runtime.intf.IEntity;
import edma.runtime.intf.exceptions.UniqueException;

public class KindEqualIndex implements IKindBaseIndex
{
	private IMetaIndex metaIndex;
	private EqualIndex index;
	
	public KindEqualIndex(IMetaIndex metaIndex, CollectionFactory cf)
	{
		this.metaIndex = metaIndex;
		index = new EqualIndex(cf);
	}

	@Override
	public Long getFromUnique(Object[] values)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getEquals(Object[] values, ISetManager setManager, boolean doCopy)
	{
		HashKey key = IndexUtil.getHashKey(values, metaIndex);
		return index.getEquals(key, setManager, doCopy);
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
		HashKey nullKey = IndexUtil.getNullHashKey(metaIndex);
		return index.getEquals(nullKey, setManager, doCopy);
	}

	@Override
	public void testUniqueness(IEntity entity) throws UniqueException
	{}

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
	{}

	@Override
	public void doUpdate(Object update)
	{
		if(update == null) return;
		HashUpdate upd = (HashUpdate) update;
		index.update(upd);
	}

	@Override
	public void directUpdate(IEntity entity, Map<Integer, Object> updates)
	{
		HashUpdate upd = IndexUtil.extractHashUpdate(entity, updates, metaIndex);
		index.update(upd);
	}

}
