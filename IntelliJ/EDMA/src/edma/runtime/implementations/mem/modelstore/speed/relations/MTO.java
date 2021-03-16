package edma.runtime.implementations.mem.modelstore.speed.relations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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

public class MTO implements IRelationStore
{
	private CollectionFactory cf;
	private IMetaRelation relation;
	private IAtomicOperations updateListener;
	private DMap<Long, Long> mapAtoB;
	private DMap<Long, DSet<Long>> mapBtoASet;
	private IRelBaseIndex[] indexesOnA;
	private IKindStore kindA;

	public MTO(IMetaRelation relation, IAtomicOperations updateListener, IKindStore kindA, CollectionFactory cf)
	{
		this.cf = cf;
		this.updateListener = updateListener;
		this.relation = relation;
		mapAtoB = cf.newMap();
		mapBtoASet = cf.newMap();
		this.kindA = kindA;
		int numberOfIndexesOnA = relation.getNumberOfIndexesOnA();
		if(numberOfIndexesOnA > 0)
		{
			indexesOnA = new IRelBaseIndex[numberOfIndexesOnA];
			for(int i = 0; i < numberOfIndexesOnA; ++i)
			{
				IMetaIndex metaIndex = relation.getIndexOnA(i);
				switch(metaIndex.getIndexType())
				{
					case Unique:
						indexesOnA[i] = new RelUniqueIndex(metaIndex, cf);
						break;
					case Equal:
						indexesOnA[i] = new RelEqualIndex(metaIndex, cf);
						break;
					case Compare:
						indexesOnA[i] = new RelCompareIndex(metaIndex, cf);
						break;
					default:
						throw new RuntimeException("Unknown index type: " + metaIndex.getIndexType());		
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
		return mapAtoB.get(a);
	}

	@Override
	public Long asBGetA(Long b)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int asAGetBSet(Long a, ISetManager setManager, boolean doCopy)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int asBGetASet(Long b, ISetManager setManager, boolean doCopy)
	{
		return setManager.newSet(mapBtoASet.get(b), doCopy);
	}

	@Override
	public int asASetGetBSet(int aSetID, ISetManager setManager, boolean doCopy)
	{
		DSet<Long> res = cf.newSet();
		Iterator<Long> it = setManager.getIterator(aSetID);
		while(it.hasNext())
		{
			Long a = it.next();
			Long b = mapAtoB.get(a);
			if(b != null) res.add(b);
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
			DSet<Long> aSet = mapBtoASet.get(b);
			if(aSet != null) res.addAll(aSet);
		}
		return setManager.transferSet(res);
	}

	@Override
	public boolean add(Long a, Long b)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean addUnique(Long a, Long b) throws UniqueException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(Long a, Long b)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Long replaceB(Long a, Long b)
	{
		Long oldB = mapAtoB.get(a);
		if(oldB != null)
		{
			if(oldB.equals(b)) return oldB;
			removeAfromBMap(a, oldB);
			removeFromIndexes(a, oldB);
			updateListener.deleteConnection(relation.getArrayPosition(),
											a,
											oldB);
			if(b == null)
			{
				mapAtoB.remove(a);
				return oldB;
			}
		}
		else if(b == null) return null;
		mapAtoB.put(a, b);
		addAToBMap(a, b);
		addToIndexes(a, b);
		updateListener.newConnection(relation.getArrayPosition(), a, b);
		return oldB;
	}
	
	@Override
	public Long replaceBUnique(Long a, Long b) throws UniqueException
	{
		Long oldB = mapAtoB.get(a);
		if(oldB != null)
		{
			if(oldB.equals(b)) return oldB;
		}
		testUniqueness(a, b);
		return replaceB(a, b);
	}

	@Override
	public boolean deleteAllForA(Long a)
	{
		Long oldB = mapAtoB.get(a);
		if(oldB == null) return false;
		mapAtoB.remove(a);
		removeAfromBMap(a, oldB);
		removeFromIndexes(a, oldB);
		updateListener.deleteConnection(relation.getArrayPosition(), a, oldB);
		return true;
	}

	@Override
	public boolean deleteAllForB(Long b)
	{
		DSet<Long> aSet = mapBtoASet.get(b);
		if(aSet == null) return false;
		mapBtoASet.remove(b);
		removeFromIndexesAllForB(b, aSet);
		for(Long a : aSet)
		{
			mapAtoB.remove(a);
			updateListener.deleteConnection(relation.getArrayPosition(), a, b);
		}
		aSet.delete();
		return true;
	}

	@Override
	public void directAdd(Long a, Long b)
	{
		addAToBMap(a, b);
		mapAtoB.put(a, b);
		addToIndexes(a, b);
	}

	@Override
	public void directDelete(Long a, Long b)
	{
		removeAfromBMap(a, b);
		mapAtoB.remove(a);
		removeFromIndexes(a, b);
	}

	@Override
	public void dump(IAtomicOperations out)
	{
		int relationIndex = relation.getArrayPosition();
		for(Entry<Long, Long> entry : mapAtoB.entrySet())
		{
			Long a = entry.getKey();
			Long b = entry.getValue();
			out.newConnection(relationIndex, a, b);
		}
	}
	
	@Override
	public IRelIndex getIndexOnA(int indexID)
	{
		return indexesOnA[indexID];
	}

	@Override
	public IRelIndex getIndexOnB(int indexID)
	{
		throw new UnsupportedOperationException();
	}
	
	private void removeAfromBMap(Long a, Long b)
	{
		DSet<Long> aSet = mapBtoASet.get(b);
		if(aSet.size() == 1)
		{
			mapBtoASet.remove(b);
			aSet.delete();
		}
		else aSet.remove(a);
	}

	private void addAToBMap(Long a, Long b)
	{
		DSet<Long> aSet = mapBtoASet.get(b);
		if(aSet == null)
		{
			aSet = cf.newSet();
			mapBtoASet.put(b, aSet);
		}
		aSet.add(a);
	}

	
	private void addToIndexes(Long a, Long b)
	{
		if(indexesOnA != null)
		{
			IEntity entityA = kindA.get(a);
			for(int i = 0; i < indexesOnA.length; ++i)
			{
				indexesOnA[i].add(b, entityA);
			}
		}
	}
	
	private void testUniqueness(Long a, Long b) throws UniqueException
	{
		if(indexesOnA != null)
		{
			IEntity entityA = kindA.get(a);
			for(int i = 0; i < indexesOnA.length; ++i)
			{
				indexesOnA[i].testUniqueness(b, entityA);
			}
		}
	}

	private void removeFromIndexes(Long a, Long b)
	{
		if(indexesOnA != null)
		{
			IEntity entityA = kindA.get(a);
			for(int i = 0; i < indexesOnA.length; ++i)
			{
				indexesOnA[i].remove(b, entityA);
			}
		}
	}
	
	private void removeFromIndexesAllForB(Long b, Collection<Long> aSet)
	{
		if(indexesOnA != null)
		{
			for(int i = 0; i < indexesOnA.length; ++i)
			{
				indexesOnA[i].removeAll(b);
			}
		}
	}

	@Override
	public Object extractIndexUpdateOnA(IEntity entity,
										Map<Integer, Object> updates)
	{
		if(indexesOnA == null) return null;
		Long bID = mapAtoB.get(entity.getID());
		if(bID == null) return null;
		Collection<Long> bIDs = new ArrayList<Long>(1);
		bIDs.add(bID);
		Object[] res = new Object[indexesOnA.length];
		for(int i = 0; i < indexesOnA.length; ++i)
		{
			IRelBaseIndex index = indexesOnA[i];
			res[i] = index.extractUpdate(bIDs, entity, updates);
		}
		return res;
	}

	@Override
	public void testUniquenessOnA(Object update) throws UniqueException
	{
		if(update == null) return;
		Object[] updates = (Object[]) update;
		for(int i = 0; i < indexesOnA.length; ++i)
		{
			IRelBaseIndex index = indexesOnA[i];
			index.testUniqueness(updates[i]);
		}
	}

	@Override
	public void doUpdateOnA(Object update)
	{
		if(update == null) return;
		Object[] updates = (Object[]) update;
		for(int i = 0; i < indexesOnA.length; ++i)
		{
			IRelBaseIndex index = indexesOnA[i];
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
