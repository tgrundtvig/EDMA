package edma.runtime.implementations.mem.modelstore.speed.newindex;

import java.util.Map;

import edma.metamodel.IMetaIndex;
import edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.CompareKey;
import edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.CompareUpdate;
import edma.runtime.implementations.mem.sets.ISetManager;
import edma.runtime.intf.IEntity;
import edma.runtime.intf.exceptions.UniqueException;

public class KindCompareIndex implements IKindBaseIndex
{
	private IMetaIndex metaIndex;
	private CompareIndex index;
	
	public KindCompareIndex(IMetaIndex metaIndex, CollectionFactory cf)
	{
		this.metaIndex = metaIndex;
		index = new CompareIndex(cf);
	}
	
	@Override
	public Long getFromUnique(Object[] values)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getEquals(Object[] values, ISetManager setManager, boolean doCopy)
	{
		CompareKey min = IndexUtil.getCompareKeyLow(values, metaIndex);
		CompareKey max = IndexUtil.getCompareKeyHigh(values, metaIndex);
		return index.getRange(min, true, max, true, setManager, doCopy);
	}

	@Override
	public int getLessThan(	Object[] values,
							boolean inclusive,
							ISetManager setManager,
							boolean doCopy)
	{
		if(inclusive)
		{
			CompareKey max = IndexUtil.getCompareKeyHigh(values, metaIndex);
			return index.getLessThan(max, true, setManager, doCopy);
		}
		CompareKey max = IndexUtil.getCompareKeyLow(values, metaIndex);
		return index.getLessThan(max, false, setManager, doCopy);
	}

	@Override
	public int getGreaterThan(	Object[] values,
								boolean inclusive,
								ISetManager setManager,
								boolean doCopy)
	{
		if(inclusive)
		{
			CompareKey min = IndexUtil.getCompareKeyLow(values, metaIndex);
			return index.getGreaterThan(min, true, setManager, doCopy);
		}
		CompareKey min = IndexUtil.getCompareKeyHigh(values, metaIndex);
		return index.getGreaterThan(min, false, setManager, doCopy);
	}

	@Override
	public int getRange(Object[] min,
						boolean minInclusive,
						Object[] max,
						boolean maxInclusive,
						ISetManager setManager,
						boolean doCopy)
	{
		CompareKey minKey;
		CompareKey maxKey;
		if(minInclusive) minKey = IndexUtil.getCompareKeyLow(min, metaIndex);
		else minKey = IndexUtil.getCompareKeyHigh(min, metaIndex);
		if(maxInclusive) maxKey = IndexUtil.getCompareKeyHigh(max, metaIndex);
		else maxKey = IndexUtil.getCompareKeyLow(max, metaIndex);
		return index.getRange(minKey, minInclusive, maxKey, maxInclusive, setManager, doCopy);
	}

	@Override
	public int getWhereNull(ISetManager setManager, boolean doCopy)
	{
		CompareKey nullKey = IndexUtil.getNullCompareKey(metaIndex);
		return index.getLessThan(nullKey, true, setManager, doCopy);
	}

	@Override
	public void testUniqueness(IEntity entity) throws UniqueException
	{}

	@Override
	public void add(IEntity entity)
	{
		CompareKey key = IndexUtil.getCompareKey(entity, metaIndex);
		index.add(key);
	}

	@Override
	public void remove(IEntity entity)
	{
		CompareKey key = IndexUtil.getCompareKey(entity, metaIndex);
		index.remove(key);
	}

	@Override
	public Object extractUpdate(IEntity entity, Map<Integer, Object> updates)
	{
		if(!IndexUtil.needUpdate(updates, metaIndex)) return null;
		return IndexUtil.extractCompareUpdate(entity, updates, metaIndex);
	}

	@Override
	public void testUniqueness(Object update) throws UniqueException
	{}

	@Override
	public void doUpdate(Object update)
	{
		if(update == null) return;
		CompareUpdate upd = (CompareUpdate) update;
		index.update(upd);
	}

	@Override
	public void directUpdate(IEntity entity, Map<Integer, Object> updates)
	{
		CompareUpdate upd = IndexUtil.extractCompareUpdate(entity, updates, metaIndex);
		index.update(upd);
	}

}
