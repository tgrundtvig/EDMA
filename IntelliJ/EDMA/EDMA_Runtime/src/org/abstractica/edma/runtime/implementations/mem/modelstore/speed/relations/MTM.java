package org.abstractica.edma.runtime.implementations.mem.modelstore.speed.relations;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.abstractica.edma.metamodel.IMetaIndex;
import org.abstractica.edma.metamodel.IMetaRelation;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.DMap;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.DSet;
import org.abstractica.edma.runtime.implementations.common.transactions.IAtomicOperations;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IKindStore;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IRelIndex;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IRelationStore;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.IRelBaseIndex;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.RelCompareIndex;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.RelEqualIndex;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.RelUniqueIndex;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManager;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.runtime.intf.exceptions.UniqueException;

public class MTM implements IRelationStore
{
	private CollectionFactory cf;
	private IMetaRelation relation;
	private IAtomicOperations updateListener;
	private DMap<Long, DSet<Long>> mapAtoBSet;
	private DMap<Long, DSet<Long>> mapBtoASet;
	private IRelBaseIndex[] indexesOnA;
	private IRelBaseIndex[] indexesOnB;
	private IKindStore kindA;
	private IKindStore kindB;

	public MTM(	IMetaRelation relation,
				IAtomicOperations updateListener,
				IKindStore kindA,
				IKindStore kindB,
				CollectionFactory cf)
	{
		this.cf = cf;
		this.relation = relation;
		this.updateListener = updateListener;
		mapAtoBSet = cf.newMap();
		mapBtoASet = cf.newMap();
		this.kindA = kindA;
		this.kindB = kindB;
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
						throw new RuntimeException("Unknown index type: "
								+ metaIndex.getIndexType());
				}
			}
		}
		int numberOfIndexesOnB = relation.getNumberOfIndexesOnB();
		if(numberOfIndexesOnB > 0)
		{
			indexesOnB = new IRelBaseIndex[numberOfIndexesOnB];
			for(int i = 0; i < numberOfIndexesOnB; ++i)
			{
				IMetaIndex metaIndex = relation.getIndexOnB(i);
				switch(metaIndex.getIndexType())
				{
					case Unique:
						indexesOnB[i] = new RelUniqueIndex(metaIndex, cf);
						break;
					case Equal:
						indexesOnB[i] = new RelEqualIndex(metaIndex, cf);
						break;
					case Compare:
						indexesOnB[i] = new RelCompareIndex(metaIndex, cf);
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
		return setManager.newSet(mapAtoBSet.get(a), doCopy);
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
			Set<Long> bSet = mapAtoBSet.get(a);
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
			Set<Long> aSet = mapBtoASet.get(b);
			if(aSet != null) res.addAll(aSet);
		}
		return setManager.transferSet(res);
	}

	@Override
	public boolean add(Long a, Long b)
	{
		addAToBMap(a, b);
		boolean res = addBToAMap(a, b);
		if(res)
		{
			addToIndexes(a, b);
			updateListener.newConnection(relation.getArrayPosition(), a, b);
		}
		return res;
	}

	@Override
	public boolean addUnique(Long a, Long b) throws UniqueException
	{
		// Check if it is already there...
		DSet<Long> aSet = mapBtoASet.get(b);
		if(aSet != null && aSet.contains(a)) return false;
		// Check for uniqueness...
		checkUniqueness(a, b);
		return add(a, b);
	}

	@Override
	public boolean delete(Long a, Long b)
	{
		DSet<Long> aSet = mapBtoASet.get(b);
		if(aSet == null || !aSet.contains(a)) return false;
		if(aSet.size() == 1)
		{
			mapBtoASet.remove(b);
			aSet.delete();
		}
		else aSet.remove(a);
		removeBfromAMap(a, b);
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
		DSet<Long> bSet = mapAtoBSet.get(a);
		if(bSet == null) return false;
		mapAtoBSet.remove(a);
		removeFromIndexesAllForA(a, bSet);
		for(Long b : bSet)
		{
			removeAfromBMap(a, b);
			updateListener.deleteConnection(relation.getArrayPosition(), a, b);
		}
		bSet.delete();
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
			removeBfromAMap(a, b);
			updateListener.deleteConnection(relation.getArrayPosition(), a, b);
		}
		aSet.delete();
		return true;
	}

	@Override
	public void directAdd(Long a, Long b)
	{
		addAToBMap(a, b);
		addBToAMap(a, b);
		addToIndexes(a, b);
	}

	@Override
	public void directDelete(Long a, Long b)
	{
		removeAfromBMap(a, b);
		removeBfromAMap(a, b);
		removeFromIndexes(a, b);
	}

	@Override
	public void dump(IAtomicOperations out)
	{
		int relationIndex = relation.getArrayPosition();
		for(Entry<Long, DSet<Long>> entry : mapAtoBSet.entrySet())
		{
			Long a = entry.getKey();
			Set<Long> bSet = entry.getValue();
			for(Long b : bSet)
			{
				out.newConnection(relationIndex, a, b);
			}
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
		return indexesOnB[indexID];
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

	private void removeBfromAMap(Long a, Long b)
	{
		DSet<Long> bSet = mapAtoBSet.get(a);
		if(bSet.size() == 1)
		{
			mapAtoBSet.remove(a);
			bSet.delete();
	
		}
		else bSet.remove(b);
	}

	private boolean addAToBMap(Long a, Long b)
	{
		DSet<Long> aSet = mapBtoASet.get(b);
		if(aSet == null)
		{
			aSet = cf.newSet();
			mapBtoASet.put(b, aSet);
		}
		return aSet.add(a);
	}

	private boolean addBToAMap(Long a, Long b)
	{
		DSet<Long> bSet = mapAtoBSet.get(a);
		if(bSet == null)
		{
			bSet = cf.newSet();
			mapAtoBSet.put(a, bSet);
		}
		return bSet.add(b);
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
		if(indexesOnB != null)
		{
			IEntity entityB = kindB.get(b);
			for(int i = 0; i < indexesOnB.length; ++i)
			{
				indexesOnB[i].add(a, entityB);
			}
		}
	}

	private void checkUniqueness(Long a, Long b) throws UniqueException
	{
		if(indexesOnA != null)
		{
			IEntity entityA = kindA.get(a);
			for(int i = 0; i < indexesOnA.length; ++i)
			{
				indexesOnA[i].testUniqueness(b, entityA);
			}
		}
		if(indexesOnB != null)
		{
			IEntity entityB = kindB.get(b);
			for(int i = 0; i < indexesOnB.length; ++i)
			{
				indexesOnB[i].testUniqueness(a, entityB);
			}
		}
	}

	private void removeFromIndexes(Long a, Long b)
	{
		// Update indexes
		if(indexesOnA != null)
		{
			IEntity entityA = kindA.get(a);
			for(int i = 0; i < indexesOnA.length; ++i)
			{
				indexesOnA[i].remove(b, entityA);
			}
		}
		if(indexesOnB != null)
		{
			IEntity entityB = kindB.get(b);
			for(int i = 0; i < indexesOnB.length; ++i)
			{
				indexesOnB[i].remove(a, entityB);
			}
		}
	}

	private void removeFromIndexesAllForA(Long a, Collection<Long> bSet)
	{
		if(indexesOnA != null)
		{
			IEntity entityA = kindA.get(a);
			for(int i = 0; i < indexesOnA.length; ++i)
			{
				indexesOnA[i].remove(bSet, entityA);
			}
		}
		if(indexesOnB != null)
		{
			for(int i = 0; i < indexesOnB.length; ++i)
			{
				indexesOnB[i].removeAll(a);
			}
		}
	}

	private void removeFromIndexesAllForB(Long b, Collection<Long> aSet)
	{
		if(indexesOnB != null)
		{
			IEntity entityB = kindB.get(b);
			for(int i = 0; i < indexesOnB.length; ++i)
			{
				indexesOnB[i].remove(aSet, entityB);
			}
		}
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
		Collection<Long> bIDs = mapAtoBSet.get(entity.getID());
		if(bIDs == null) return null;
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
		if(indexesOnB == null) return null;
		Collection<Long> aIDs = mapBtoASet.get(entity.getID());
		if(aIDs == null) return null;
		Object[] res = new Object[indexesOnB.length];
		for(int i = 0; i < indexesOnB.length; ++i)
		{
			IRelBaseIndex index = indexesOnB[i];
			res[i] = index.extractUpdate(aIDs, entity, updates);
		}
		return res;
	}

	@Override
	public void testUniquenessOnB(Object update) throws UniqueException
	{
		if(update == null) return;
		Object[] updates = (Object[]) update;
		for(int i = 0; i < indexesOnB.length; ++i)
		{
			IRelBaseIndex index = indexesOnB[i];
			index.testUniqueness(updates[i]);
		}
	}

	@Override
	public void doUpdateOnB(Object update)
	{
		if(update == null) return;
		Object[] updates = (Object[]) update;
		for(int i = 0; i < indexesOnB.length; ++i)
		{
			IRelBaseIndex index = indexesOnB[i];
			index.doUpdate(updates[i]);
		}
	}

	@Override
	public void directUpdateOnB(IEntity entity, Map<Integer, Object> updates)
	{
		Object upd = extractIndexUpdateOnB(entity, updates);
		doUpdateOnB(upd);
	}
}
