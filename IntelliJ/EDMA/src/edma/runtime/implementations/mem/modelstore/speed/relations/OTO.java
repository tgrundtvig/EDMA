package edma.runtime.implementations.mem.modelstore.speed.relations;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import edma.metamodel.IMetaRelation;
import edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import edma.runtime.implementations.common.collectionfactory.DMap;
import edma.runtime.implementations.common.collectionfactory.DSet;
import edma.runtime.implementations.common.transactions.IAtomicOperations;
import edma.runtime.implementations.mem.modelstore.IRelIndex;
import edma.runtime.implementations.mem.modelstore.IRelationStore;
import edma.runtime.implementations.mem.sets.ISetManager;
import edma.runtime.intf.IEntity;
import edma.runtime.intf.exceptions.UniqueException;

public class OTO implements IRelationStore
{
	private CollectionFactory cf;
	private IMetaRelation relation;
	private IAtomicOperations updateListener;
	private DMap<Long, Long> mapAtoB;
	private DMap<Long, Long> mapBtoA;

	public OTO(IMetaRelation relation, IAtomicOperations updateListener, CollectionFactory cf)
	{
		this.relation = relation;
		this.updateListener = updateListener;
		mapAtoB = cf.newMap();
		mapBtoA = cf.newMap();
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
		return mapBtoA.get(b);
	}

	@Override
	public int asAGetBSet(Long a, ISetManager setManager, boolean doCopy)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int asBGetASet(Long b, ISetManager setManager, boolean doCopy)
	{
		throw new UnsupportedOperationException();
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
			Long a = mapBtoA.get(b);
			if(a != null) res.add(a);
		}
		return setManager.transferSet(res);
	}

	@Override
	public boolean add(Long a, Long b)
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
			mapBtoA.remove(oldB);
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
		mapBtoA.put(b, a);
		updateListener.newConnection(relation.getArrayPosition(), a, b);
		return oldB;
	}

	@Override
	public boolean deleteAllForA(Long a)
	{
		Long b = mapAtoB.get(a);
		if(b == null) return false;
		mapAtoB.remove(a);
		mapBtoA.remove(b);
		updateListener.deleteConnection(relation.getArrayPosition(), a, b);
		return true;
	}

	@Override
	public boolean deleteAllForB(Long b)
	{
		Long a = mapBtoA.get(b);
		if(a == null) return false;
		mapAtoB.remove(a);
		mapBtoA.remove(b);
		updateListener.deleteConnection(relation.getArrayPosition(), a, b);
		return true;
	}

	@Override
	public void directAdd(Long a, Long b)
	{
		mapAtoB.put(a, b);
		mapBtoA.put(b, a);
	}

	@Override
	public void directDelete(Long a, Long b)
	{
		mapAtoB.remove(a);
		mapBtoA.remove(b);
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
		throw new UnsupportedOperationException();
	}

	@Override
	public IRelIndex getIndexOnB(int indexID)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addUnique(Long a, Long b) throws UniqueException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Long replaceBUnique(Long a, Long b) throws UniqueException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object extractIndexUpdateOnA(IEntity entity,
										Map<Integer, Object> updates)
	{
		return null;
	}

	@Override
	public void testUniquenessOnA(Object update) throws UniqueException
	{}

	@Override
	public void doUpdateOnA(Object update)
	{}

	@Override
	public void directUpdateOnA(IEntity entity, Map<Integer, Object> updates)
	{}

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
