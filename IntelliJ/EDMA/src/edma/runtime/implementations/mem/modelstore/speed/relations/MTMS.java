package edma.runtime.implementations.mem.modelstore.speed.relations;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edma.metamodel.IMetaIndex;
import edma.metamodel.IMetaRelation;
import edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import edma.runtime.implementations.common.collectionfactory.DMap;
import edma.runtime.implementations.common.collectionfactory.DSet;
import edma.runtime.implementations.common.transactions.IAtomicOperations;
import edma.runtime.implementations.mem.modelstore.IKindStore;
import edma.runtime.implementations.mem.modelstore.IRelIndex;
import edma.runtime.implementations.mem.modelstore.IRelationStore;
import edma.runtime.implementations.mem.modelstore.speed.newindex.IRelBaseIndex;
import edma.runtime.implementations.mem.modelstore.speed.newindex.RelCompareIndex;
import edma.runtime.implementations.mem.modelstore.speed.newindex.RelEqualIndex;
import edma.runtime.implementations.mem.modelstore.speed.newindex.RelUniqueIndex;
import edma.runtime.implementations.mem.sets.ISetManager;
import edma.runtime.intf.IEntity;
import edma.runtime.intf.exceptions.UniqueException;

public class MTMS implements IRelationStore
{
	private CollectionFactory cf;
	private IMetaRelation relation;
	private IAtomicOperations updateListener;
	private DMap<Long, DSet<Long>> map;
	private IRelBaseIndex[] indexes;
	private IKindStore kind;

	public MTMS(IMetaRelation relation,
				IAtomicOperations updateListener,
				IKindStore kind,
				CollectionFactory cf)
	{
		this.cf = cf;
		this.updateListener = updateListener;
		this.relation = relation;
		map = cf.newMap();
		this.kind = kind;
		int numberOfIndexesOnA = relation.getNumberOfIndexesOnA();
		if(numberOfIndexesOnA > 0)
		{
			indexes = new IRelBaseIndex[numberOfIndexesOnA];
			for(int i = 0; i < numberOfIndexesOnA; ++i)
			{
				IMetaIndex metaIndex = relation.getIndexOnA(i);
				switch(metaIndex.getIndexType())
				{
					case Unique:
						indexes[i] = new RelUniqueIndex(metaIndex, cf);
						break;
					case Equal:
						indexes[i] = new RelEqualIndex(metaIndex, cf);
						break;
					case Compare:
						indexes[i] = new RelCompareIndex(metaIndex, cf);
						break;
					default:
						throw new RuntimeException("Unknown index type: "
								+ metaIndex.getIndexType());
				}
			}
		}
	}

	@Override
	public int getKindA()
	{
		return relation.getKindA().getArrayPosition();
	}

	@Override
	public int getKindB()
	{
		return relation.getKindB().getArrayPosition();
	}

	@Override
	public Long asAGetB(Long a)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Long asBGetA(Long b)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int asAGetBSet(Long a, ISetManager setManager, boolean doCopy)
	{
		return setManager.newSet(map.get(a), doCopy);
	}

	@Override
	public int asBGetASet(Long b, ISetManager setManager, boolean doCopy)
	{
		return setManager.newSet(map.get(b), doCopy);
	}

	@Override
	public int asASetGetBSet(int aSetID, ISetManager setManager, boolean doCopy)
	{
		DSet<Long> res = cf.newSet();
		Iterator<Long> it = setManager.getIterator(aSetID);
		while(it.hasNext())
		{
			Long a = it.next();
			Set<Long> bSet = map.get(a);
			if(bSet != null) res.addAll(bSet);
		}
		return setManager.transferSet(res);
	}

	@Override
	public int asBSetGetASet(int bSetID, ISetManager setManager, boolean doCopy)
	{
		DSet<Long> res = cf.newSet();
		Iterator<Long> it = setManager.getIterator(bSetID);
		while(it.hasNext())
		{
			Long b = it.next();
			Set<Long> aSet = map.get(b);
			if(aSet != null) res.addAll(aSet);
		}
		return setManager.transferSet(res);
	}

	@Override
	public boolean add(Long a, Long b)
	{
		DSet<Long> bSet = map.get(a);
		if(bSet != null)
		{
			if(bSet.contains(b)) return false;
		}
		else
		{
			bSet = cf.newSet();
			map.put(a, bSet);
		}
		bSet.add(b);

		DSet<Long> aSet = map.get(b);
		if(aSet == null)
		{
			aSet = cf.newSet();
			map.put(b, aSet);
		}
		aSet.add(a);
		addToIndexes(a, b);
		updateListener.newConnection(relation.getArrayPosition(), a, b);
		return true;
	}

	@Override
	public boolean addUnique(Long a, Long b) throws UniqueException
	{
		// Check if it is already there...
		DSet<Long> aSet = map.get(b);
		if(aSet != null && aSet.contains(a)) return false;
		// Check for uniqueness...
		checkUniqueness(a, b);
		return add(a, b);
	}

	@Override
	public boolean delete(Long a, Long b)
	{
		DSet<Long> bSet = map.get(a);
		if(bSet == null || !bSet.contains(b)) return false;
		if(bSet.size() == 1)
		{
			map.remove(a);
			bSet.delete();
		}
		else bSet.remove(b);
		DSet<Long> aSet = map.get(b);
		if(aSet != null)
		{
			if(aSet.size() == 1)
			{
				map.remove(b);
				aSet.delete();
			}
			else aSet.remove(a);
		}
		removeFromIndexes(a, b);
		updateListener.deleteConnection(relation.getArrayPosition(), a, b);
		return true;
	}

	@Override
	public Long replaceB(Long a, Long b)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Long replaceBUnique(Long a, Long b) throws UniqueException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean deleteAllForA(Long a)
	{
		DSet<Long> bSet = map.get(a);
		if(bSet == null) return false;
		map.remove(a);
		for(Long b : bSet)
		{
			DSet<Long> aSet = map.get(b);
			if(aSet != null)
			{
				aSet.remove(a);
				if(aSet.isEmpty())
				{
					map.remove(b);
					aSet.delete();
				}
			}
			removeFromIndexes(a, b);
			updateListener.deleteConnection(relation.getArrayPosition(), a, b);
		}
		bSet.delete();
		return true;
	}

	@Override
	public boolean deleteAllForB(Long b)
	{
		DSet<Long> aSet = map.get(b);
		if(aSet == null) return false;
		map.remove(b);
		for(Long a : aSet)
		{
			DSet<Long> bSet = map.get(a);
			if(bSet != null)
			{
				bSet.remove(b);
				if(bSet.isEmpty())
				{
					map.remove(a);
					bSet.delete();
				}
			}
			removeFromIndexes(a, b);
			updateListener.deleteConnection(relation.getArrayPosition(), a, b);
		}
		aSet.delete();
		return true;
	}

	@Override
	public void directAdd(Long a, Long b)
	{
		DSet<Long> bSet = map.get(a);
		if(bSet == null)
		{
			bSet = cf.newSet();
			map.put(a, bSet);
		}
		bSet.add(b);

		DSet<Long> aSet = map.get(b);
		if(aSet == null)
		{
			aSet = cf.newSet();
			map.put(b, aSet);
		}
		aSet.add(a);
		addToIndexes(a, b);
	}

	@Override
	public void directDelete(Long a, Long b)
	{
		DSet<Long> bSet = map.get(a);
		if(bSet.size() == 1)
		{
			map.remove(a);
			bSet.delete();
		}
		else bSet.remove(b);
		DSet<Long> aSet = map.get(b);
		if(aSet != null)
		{
			if(aSet.size() == 1)
			{
				map.remove(b);
				aSet.delete();
			}
			else aSet.remove(a);
		}
		removeFromIndexes(a, b);
	}

	@Override
	public void dump(IAtomicOperations out)
	{
		int relationIndex = relation.getArrayPosition();
		for(Entry<Long, DSet<Long>> entry : map.entrySet())
		{
			Long a = entry.getKey();
			DSet<Long> bSet = entry.getValue();
			for(Long b : bSet)
			{
				if(a.longValue() <= b.longValue()) out.newConnection(	relationIndex,
																		a,
																		b);
			}
		}
	}

	@Override
	public IRelIndex getIndexOnA(int indexID)
	{
		return indexes[indexID];
	}

	@Override
	public IRelIndex getIndexOnB(int indexID)
	{
		throw new UnsupportedOperationException();
	}

	private void addToIndexes(Long a, Long b)
	{
		if(indexes != null)
		{
			boolean same = a.equals(b);
			IEntity entityA = kind.get(a);
			IEntity entityB = kind.get(b);
			for(int i = 0; i < indexes.length; ++i)
			{
				indexes[i].add(b, entityA);
				if(!same) indexes[i].add(a, entityB);
			}
		}
	}
	
	private void checkUniqueness(Long a, Long b) throws UniqueException
	{
		if(indexes != null)
		{
			boolean same = a.equals(b);
			IEntity entityA = kind.get(a);
			IEntity entityB = kind.get(b);
			for(int i = 0; i < indexes.length; ++i)
			{
				indexes[i].testUniqueness(b, entityA);
				if(!same) indexes[i].testUniqueness(a, entityB);
			}
		}
	}

	private void removeFromIndexes(Long a, Long b)
	{
		if(indexes != null)
		{
			boolean same = a.equals(b);
			IEntity entityA = kind.get(a);
			IEntity entityB = kind.get(b);
			for(int i = 0; i < indexes.length; ++i)
			{
				indexes[i].remove(b, entityA);
				if(!same) indexes[i].remove(a, entityB);
			}
		}
	}

	@Override
	public Object extractIndexUpdateOnA(IEntity entity,
										Map<Integer, Object> updates)
	{
		if(indexes == null) return null;
		Collection<Long> fIDs = map.get(entity.getID());
		if(fIDs == null) return null;
		Object[] res = new Object[indexes.length];
		for(int i = 0; i < indexes.length; ++i)
		{
			IRelBaseIndex index = indexes[i];
			res[i] = index.extractUpdate(fIDs, entity, updates);
		}
		return res;
	}

	@Override
	public void testUniquenessOnA(Object update) throws UniqueException
	{
		if(update == null) return;
		Object[] updates = (Object[]) update;
		for(int i = 0; i < indexes.length; ++i)
		{
			IRelBaseIndex index = indexes[i];
			index.testUniqueness(updates[i]);
		}
	}

	@Override
	public void doUpdateOnA(Object update)
	{
		if(update == null) return;
		Object[] updates = (Object[]) update;
		for(int i = 0; i < indexes.length; ++i)
		{
			IRelBaseIndex index = indexes[i];
			index.doUpdate(updates[i]);
		}
	}

	@Override
	public void directUpdateOnA(IEntity entity, Map<Integer, Object> updates)
	{
		Object upd = extractIndexUpdateOnA(entity, updates);
		doUpdateOnA(upd);
	}
	@Override
	public Object extractIndexUpdateOnB(IEntity entity,
										Map<Integer, Object> updates)
	{
		return null;
	}

	@Override
	public void testUniquenessOnB(Object update) throws UniqueException
	{}

	@Override
	public void doUpdateOnB(Object update)
	{}

	@Override
	public void directUpdateOnB(IEntity entity, Map<Integer, Object> updates)
	{}
}
