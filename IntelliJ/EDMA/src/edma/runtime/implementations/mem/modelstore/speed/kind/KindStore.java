package edma.runtime.implementations.mem.modelstore.speed.kind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edma.metamodel.IMetaAttribute;
import edma.metamodel.IMetaIndex;
import edma.metamodel.IMetaKind;
import edma.metamodel.IMetaRelation;
import edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import edma.runtime.implementations.common.collectionfactory.DMap;
import edma.runtime.implementations.common.transactions.IAtomicOperations;
import edma.runtime.implementations.mem.modelstore.IKindIndex;
import edma.runtime.implementations.mem.modelstore.IKindStore;
import edma.runtime.implementations.mem.modelstore.IRelationStore;
import edma.runtime.implementations.mem.modelstore.speed.newindex.IKindBaseIndex;
import edma.runtime.implementations.mem.modelstore.speed.newindex.KindCompareIndex;
import edma.runtime.implementations.mem.modelstore.speed.newindex.KindEqualIndex;
import edma.runtime.implementations.mem.modelstore.speed.newindex.KindUniqueIndex;
import edma.runtime.implementations.mem.sets.ISetManager;
import edma.runtime.intf.IEntity;
import edma.runtime.intf.exceptions.UniqueException;

public class KindStore implements IKindStore
{
	private CollectionFactory cf;
	private IAtomicOperations updateListener;
	private long nextID;
	private IMetaKind kind;
	private DMap<Long, IEntity> map;
	private IKindBaseIndex[] kindIndexes;
	private IRelationStore[] asA;
	private IRelationStore[] asB;

	public KindStore(IMetaKind kind, IAtomicOperations updateListener, CollectionFactory cf)
	{
		this.cf = cf;
		this.kind = kind;
		this.updateListener = updateListener;
		nextID = 1;
		map = cf.newMap();
		kindIndexes = new IKindBaseIndex[kind.getNumberOfIndexes()];
		for(int i = 0; i < kindIndexes.length; ++i)
		{
			IMetaIndex index = kind.getIndex(i);
			switch(index.getIndexType())
			{
				case Unique:
					kindIndexes[i] = new KindUniqueIndex(index, cf);
					break;
				case Equal:
					kindIndexes[i] = new KindEqualIndex(index, cf);
					break;
				case Compare:
					kindIndexes[i] = new KindCompareIndex(index, cf);
					break;
				default:
					throw new RuntimeException("Unknown index type: "
							+ index.getIndexType());
			}
		}
	}
	
	@Override
	public void hookUpRelations(IRelationStore[] relations)
	{
		ArrayList<IRelationStore> aList = new ArrayList<>();
		int size = kind.getNumberOfRelationsAsA();
		for(int i = 0; i < size; ++i)
		{
			IMetaRelation rel = kind.getRelationAsA(i);
			if(rel.getNumberOfIndexesOnA() > 0)
			{
				aList.add(relations[rel.getArrayPosition()]);
			}
		}
		asA = new IRelationStore[aList.size()];
		for(int i = 0; i < asA.length; ++i)
		{
			asA[i] = aList.get(i);
		}
		
		ArrayList<IRelationStore> bList = new ArrayList<>();
		size = kind.getNumberOfRelationsAsB();
		for(int i = 0; i < size; ++i)
		{
			IMetaRelation rel = kind.getRelationAsB(i);
			if(rel.getNumberOfIndexesOnB() > 0)
			{
				bList.add(relations[rel.getArrayPosition()]);
			}
		}
		asB = new IRelationStore[bList.size()];
		for(int i = 0; i < asB.length; ++i)
		{
			asB[i] = bList.get(i);
		}
		
	}

	@Override
	public IMetaKind getMetaKind()
	{
		return kind;
	}

	@Override
	public boolean exists(Long ID)
	{
		return map.containsKey(ID);
	}

	@Override
	public IEntity get(Long ID)
	{
		return map.get(ID);
	}

	@Override
	public int getAllIDs(ISetManager setManager, boolean doCopy)
	{
		return setManager.newSet(map.keySet(), doCopy);
	}

	@Override
	public IKindIndex getIndex(int index)
	{
		return kindIndexes[index];
	}

	@Override
	public IEntity add(IEntity e)
	{
		Long ID = e.getID();
		if(ID != null && map.get(ID) != null) 
			throw new RuntimeException( "There already exists an entity of kind "
				+ kind.getName() + " with id = " + ID);
		Object[] value = e.getValue();
		if(ID == null)
		{
			ID = nextID++;
			value[0] = ID;
		}
		else if(nextID <= ID) nextID = ID + 1;
		map.put(ID, e);
		for(IKindBaseIndex index : kindIndexes)
		{
			index.add(e);
		}
		updateListener.newEntity(kind.getArrayPosition(), e.getValue());
		return e;
	}

	@Override
	public IEntity addUnique(IEntity e) throws UniqueException
	{
		for(IKindBaseIndex index : kindIndexes)
		{
			index.testUniqueness(e);
		}
		return add(e);
	}

	@Override
	public boolean update(Long ID, Map<Integer, Object> updates)
	{
		IEntity curEnt = map.get(ID);
		if(curEnt == null) throw new RuntimeException("Updating a non-existing entry!");
		Object[] entityValues = curEnt.getValue();

		// Reduce map by removing updates that update to the same value
		reduceUpdateMap(entityValues, updates);

		for(IKindBaseIndex index : kindIndexes)
		{
			Object upd = index.extractUpdate(curEnt, updates);
			index.doUpdate(upd);
		}
		
		for(IRelationStore rel : asA)
		{
			Object upd = rel.extractIndexUpdateOnA(curEnt, updates);
			rel.doUpdateOnA(upd);
		}
		
		for(IRelationStore rel : asB)
		{
			Object upd = rel.extractIndexUpdateOnB(curEnt, updates);
			rel.doUpdateOnB(upd);
		}
		
		

		// Update the entity
		updateEntity(ID, entityValues, updates);

		return true;
	}

	@Override
	public boolean updateUnique(Long ID, Map<Integer, Object> updates) throws UniqueException
	{
		IEntity curEnt = map.get(ID);
		if(curEnt == null) throw new RuntimeException("Updating a non-existing entry!");
		Object[] entityValues = curEnt.getValue();

		// Reduce map by removing updates that update to the same value
		reduceUpdateMap(entityValues, updates);

		// Run through the updates to see if there is any of the
		// indexes that throws an UniqueException
		int size = kindIndexes.length + asA.length + asB.length;
		Object[] indexUpdates = new Object[size];
		int i = 0;
		for(IKindBaseIndex index : kindIndexes)
		{
			Object upd = index.extractUpdate(curEnt, updates);
			index.testUniqueness(upd);
			indexUpdates[i++] = upd;
		}
		
		for(IRelationStore rel : asA)
		{
			Object upd = rel.extractIndexUpdateOnA(curEnt, updates);
			rel.testUniquenessOnA(upd);
			indexUpdates[i++] = upd;
		}
		
		for(IRelationStore rel : asB)
		{
			Object upd = rel.extractIndexUpdateOnB(curEnt, updates);
			rel.testUniquenessOnB(upd);
			indexUpdates[i++] = upd;
		}
		
		// Now we do the actual updates
		i = 0;
		for(IKindBaseIndex index : kindIndexes)
		{
			index.doUpdate(indexUpdates[i++]);
		}
		
		for(IRelationStore rel : asA)
		{
			rel.doUpdateOnA(indexUpdates[i++]);
		}
		
		for(IRelationStore rel : asB)
		{
			rel.doUpdateOnB(indexUpdates[i++]);
		}

		updateEntity(ID, entityValues, updates);

		return true;
	}

	@Override
	public boolean remove(Long ID)
	{
		IEntity doomed = map.get(ID);
		if(doomed == null) return false;
		map.remove(ID);
		for(IKindBaseIndex index : kindIndexes)
		{
			index.remove(doomed);
		}
		updateListener.deleteEntity(kind.getArrayPosition(), doomed.getValue());
		doomed.delete();
		return true;
	}

	private void reduceUpdateMap(	Object[] curValues,
									Map<Integer, Object> updates)
	{
		Set<Entry<Integer, Object>> entrySet = updates.entrySet();
		Iterator<Entry<Integer, Object>> it = entrySet.iterator();
		while(it.hasNext())
		{
			Entry<Integer, Object> cur = it.next();
			Integer attID = cur.getKey();
			IMetaAttribute att = kind.getAttribute(attID);
			if(att.getValueDomain().valueEqual(	curValues[attID + 1],
												cur.getValue()))
			{
				it.remove();
			}
		}
	}

	private void updateEntity(	Long ID,
								Object[] entityValues,
								Map<Integer, Object> updates)
	{
		// Now we finally can update the entity
		int size = updates.size();
		int[] fields = new int[size];
		Object[] oldValues = new Object[size];
		Object[] newValues = new Object[size];
		int i = 0;
		for(Entry<Integer, Object> entry : updates.entrySet())
		{
			Integer attID = entry.getKey();
			Object val = entry.getValue();
			fields[i] = attID;
			int pos = attID + 1;
			oldValues[i] = entityValues[pos];
			newValues[i] = val;
			entityValues[pos] = val;
			++i;
		}

		// Send the update to the listener:
		updateListener.updateEntity(kind.getArrayPosition(),
									ID,
									fields,
									oldValues,
									newValues);
	}

	@Override
	public void directAdd(IEntity e)
	{
		Long ID = e.getID();
		if(nextID <= ID) nextID = ID + 1;
		map.put(ID, e);
		for(IKindBaseIndex index : kindIndexes)
		{
			index.add(e);
		}
	}

	@Override
	public void directDelete(Long ID)
	{
		IEntity doomed = map.get(ID);
		map.remove(ID);
		for(IKindBaseIndex index : kindIndexes)
		{
			index.remove(doomed);
		}
	}

	@Override
	public void directUpdate(Long ID, int[] fields, Object[] newValues)
	{
		IEntity curEnt = map.get(ID);
		if(kindIndexes.length > 0)
		{
			// Create update map
			Map<Integer, Object> updates = new HashMap<Integer, Object>(fields.length);
			for(int i = 0; i < fields.length; ++i)
			{
				updates.put(fields[i], newValues[i]);
			}
			// Update indexes
			for(IKindBaseIndex index : kindIndexes)
			{
				index.directUpdate(curEnt, updates);
			}
		}
		// Update entity
		Object[] entityValues = curEnt.getValue();
		for(int i = 0; i < fields.length; ++i)
		{
			entityValues[fields[i] + 1] = newValues[i];
		}

	}

	@Override
	public void dump(IAtomicOperations out)
	{
		int kindIndex = kind.getArrayPosition();
		for(Entry<Long, IEntity> entry : map.entrySet())
		{
			Object[] value = entry.getValue().getValue();
			out.newEntity(kindIndex, value);
		}
	}

}
