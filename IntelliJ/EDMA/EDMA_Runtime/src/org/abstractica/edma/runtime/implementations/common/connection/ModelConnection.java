package org.abstractica.edma.runtime.implementations.common.connection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaKind;
import org.abstractica.edma.metamodel.IMetaRelation;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.DSet;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransactionOperations;
import org.abstractica.edma.runtime.implementations.mem.modelstore.Entity;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IKindIndex;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IKindStore;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IModelStore;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IRelIndex;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IRelationStore;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.KindIndexWrap;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.RelIndexWrap;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManager;
import org.abstractica.edma.runtime.intf.IDataModelUpdate;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.runtime.intf.IIndex;
import org.abstractica.edma.runtime.intf.exceptions.UniqueException;

public class ModelConnection implements IDataModelUpdate
{
	private final CollectionFactory cf;
	private final boolean canUpdate;
	private final IModelStore modelStore;
	private final ArrayList<SetProxy> sets;
	private final ISetManager setManager;

	public ModelConnection(	boolean canUpdate,
							IModelStore modelStore,
							ISetManager setManager,
							CollectionFactory cf)
	{
		this.cf = cf;
		this.canUpdate = canUpdate;
		this.modelStore = modelStore;
		this.setManager = setManager;
		sets = new ArrayList<SetProxy>();
	}

	// *******************************************************************************************
	// View interface
	// *******************************************************************************************

	@Override
	public String getName()
	{
		return modelStore.getName();
	}

	@Override
	public long getVersion()
	{
		return modelStore.getVersion();
	}

	@Override
	public IMetaDataModel getMetaModel()
	{
		return modelStore.getMetaModel();
	}
	
	@Override
	public void dump(ITransactionOperations out)
	{
		modelStore.dump(out);
	}

	@Override
	public Object getSingletonAttribute(int singleton, int attribute)
	{
		return modelStore.getSingleton(singleton).getAttribute(attribute);
	}

	@Override
	public int setExtensionUp(int set, int kind)
	{
		SetProxy s = sets.get(set);
		return newSet(modelStore.getKind(kind), s.setID, null);
	}

	@Override
	public int setExtensionDown(int set, int kind)
	{
		IKindStore kindStore = modelStore.getKind(kind);
		SetProxy s = sets.get(set);
		DSet<Long> res = cf.newSet();
		Iterator<Long> it = s.idIterator();
		while(it.hasNext())
		{
			Long l = it.next();
			if(kindStore.exists(l)) res.add(l);
		}
		return newSet(kindStore, setManager.transferSet(res), null);
	}

	@Override
	public int kindGetAll(int kind)
	{
		IKindStore kindStore = modelStore.getKind(kind);
		return newSet(kindStore, kindStore.getAllIDs(setManager, canUpdate), null);
	}

	@Override
	public int setIntersect(int setA, int setB)
	{
		SetProxy a = sets.get(setA);
		SetProxy b = sets.get(setB);
		if(a.kind != b.kind) throw new RuntimeException("Intersection of mismatched sets!");
		int res = setManager.intersection(a.setID, b.setID);
		return newSet(a.kind, res, null);
	}

	@Override
	public int setUnion(int setA, int setB)
	{
		SetProxy a = sets.get(setA);
		SetProxy b = sets.get(setB);
		if(a.kind != b.kind) throw new RuntimeException("Union of mismatched sets!");
		int res = setManager.union(a.setID, b.setID);
		return newSet(a.kind, res, null);
	}

	@Override
	public int setSubtract(int setA, int setB)
	{
		SetProxy a = sets.get(setA);
		SetProxy b = sets.get(setB);
		if(a.kind != b.kind) throw new RuntimeException("Subtraction of mismatched sets!");
		int res = setManager.subtraction(a.setID, b.setID);
		return newSet(a.kind, res, a.order);
	}

	@Override
	public IEntity kindGetFromID(int kind, Long id)
	{
		return modelStore.getKind(kind).get(id);
	}
	
	@Override
	public IIndex getKindIndex(int kind, int indexID)
	{
		IKindStore kindStore = modelStore.getKind(kind);
		IKindIndex kindIndex = kindStore.getIndex(indexID);
		return new KindIndexWrap(kindIndex, kindStore, setManager, sets, canUpdate);
	}
	
	@Override
	public IIndex getRelationIndexOnA(int relID, int indexID, Long bID)
	{
		IRelationStore relStore = modelStore.getRelation(relID);
		IKindStore kindStore = modelStore.getKind(relStore.getKindA());
		IRelIndex relIndex = relStore.getIndexOnA(indexID);
		return new RelIndexWrap(relIndex, bID, kindStore, setManager, sets, canUpdate);
	}
	
	@Override
	public IIndex getRelationIndexOnB(int relID, int indexID, Long aID)
	{
		IRelationStore relStore = modelStore.getRelation(relID);
		IKindStore kindStore = modelStore.getKind(relStore.getKindB());
		IRelIndex relIndex = relStore.getIndexOnB(indexID);
		return new RelIndexWrap(relIndex, aID, kindStore, setManager, sets, canUpdate);
	}

	@Override
	public int setFromIDs(int kind, Iterable<Long> ids)
	{
		DSet<Long> tmp = cf.newSet();
		for(Long l : ids)
		{
			tmp.add(l);
		}
		int res = setManager.transferSet(tmp);
		return newSet(modelStore.getKind(kind), res, null);
	}

	@Override
	public int setOrderByID(int set, boolean desc)
	{
		SetProxy s = sets.get(set);
		SetProxy newSet = s.orderByID(desc);
		int res = sets.size();
		sets.add(newSet);
		return res;
	}

	@Override
	public int setSubOrderByID(int set, boolean desc)
	{
		SetProxy s = sets.get(set);
		SetProxy newSet = s.subOrderByID(desc);
		int res = sets.size();
		sets.add(newSet);
		return res;
	}

	@Override
	public int setOrderBy(int set, int attribute, boolean desc)
	{
		SetProxy s = sets.get(set);
		SetProxy newSet = s.orderBy(attribute, desc);
		int res = sets.size();
		sets.add(newSet);
		return res;
	}

	@Override
	public int setSubOrderBy(int set, int attribute, boolean desc)
	{
		SetProxy s = sets.get(set);
		SetProxy newSet = s.subOrderBy(attribute, desc);
		int res = sets.size();
		sets.add(newSet);
		return res;
	}

	@Override
	public int setGetSize(int set)
	{
		SetProxy s = sets.get(set);
		return s.size();
	}

	@Override
	public boolean setContains(int set, Long id)
	{
		SetProxy s = sets.get(set);
		return s.contains(id);
	}
	
	@Override
	public boolean setContainsAll(int setA, int setB)
	{
		SetProxy sA = sets.get(setA);
		SetProxy sB = sets.get(setB);
		return sA.containsAll(sB);
	}

	@Override
	public Iterator<IEntity> setGetIterator(int set)
	{
		return sets.get(set).iterator();
	}

	@Override
	public Long relationAsAGetB(int relationIndex, Long entityA)
	{
		return modelStore.getRelation(relationIndex).asAGetB(entityA);
	}

	@Override
	public Long relationAsBGetA(int relationIndex, Long entityB)
	{
		return modelStore.getRelation(relationIndex).asBGetA(entityB);
	}

	@Override
	public int relationAsAGetBSet(int relationIndex, Long entityA)
	{
		IRelationStore rel = modelStore.getRelation(relationIndex);
		int res = rel.asAGetBSet(entityA, setManager, canUpdate);
		return newSet(modelStore.getKind(rel.getKindB()), res, null);
	}

	@Override
	public int relationAsBGetASet(int relationIndex, Long entityB)
	{
		IRelationStore rel = modelStore.getRelation(relationIndex);
		int res = rel.asBGetASet(entityB, setManager,canUpdate);
		return newSet(modelStore.getKind(rel.getKindA()), res, null);
	}

	@Override
	public int relationAsASetGetBSet(int relationIndex, int setA)
	{
		SetProxy a = sets.get(setA);
		IRelationStore rel = modelStore.getRelation(relationIndex);
		int res = rel.asASetGetBSet(a.setID, setManager, canUpdate);
		return newSet(modelStore.getKind(rel.getKindB()), res, null);
	}

	@Override
	public int relationAsBSetGetASet(int relationIndex, int setB)
	{
		SetProxy b = sets.get(setB);
		IRelationStore rel = modelStore.getRelation(relationIndex);
		int res = rel.asBSetGetASet(b.setID, setManager, canUpdate);
		return newSet(modelStore.getKind(rel.getKindA()), res, null);
	}

	@Override
	public IDataModelUpdate getUpdateInterface()
	{
		return this;
	}

	// *******************************************************************************************
	// Update interface
	// *******************************************************************************************

	@Override
	public IEntity entityNew(int kind, Object[] values)
	{
		return modelStore.getKind(kind).add(new Entity(values));
	}

	@Override
	public IEntity entityNewUnique(int kind, Object[] values) throws UniqueException
	{
		return modelStore.getKind(kind).addUnique(new Entity(values));
	}

	@Override
	public boolean entityDelete(int kind, Long ID)
	{
		IMetaKind metaKind = modelStore.getKind(kind).getMetaKind();
		// Delete all connections to this entity:
		int size = metaKind.getNumberOfRelationsAsA();
		for(int i = 0; i < size; ++i)
		{
			IMetaRelation metaRel = metaKind.getRelationAsA(i);
			modelStore.getRelation(metaRel.getArrayPosition())
						.deleteAllForA(ID);
		}
		size = metaKind.getNumberOfRelationsAsB();
		for(int i = 0; i < size; ++i)
		{
			IMetaRelation metaRel = metaKind.getRelationAsB(i);
			IRelationStore relStore = modelStore.getRelation(metaRel.getArrayPosition());
			if(metaRel.isOwnershipRelation())
			{
				int ownedKind = relStore.getKindA();
				switch(metaRel.getType())
				{
					case OneToOne:
						Long ownedID = relStore.asBGetA(ID);
						entityDelete(ownedKind, ownedID);
						break;
					case ManyToOne:
						int ownedSet = relStore.asBGetASet(ID, setManager, true);
						Iterator<Long> it = setManager.getIterator(ownedSet);
						while(it.hasNext())
						{
							ownedID = it.next();
							entityDelete(ownedKind, ownedID);
						}
						break;
					default:
						throw new RuntimeException("Wrong relation type for ownership relation!");
				}
			}
			else relStore.deleteAllForB(ID);
		}
		boolean res = modelStore.getKind(kind).remove(ID);
		
		// Delete extensions (this is commented out because it is now done by the generated code)
//		size = metaKind.getNumberOfExtensions();
//		for(int i = 0; i < size; ++i)
//		{
//			IMetaKind ext = metaKind.getExtension(i);
//			entityDelete(ext.getArrayPosition(), ID);
//		}
		return res;
	}

	@Override
	public boolean entityUpdate(int kind, Long ID, Map<Integer, Object> updates)
	{
		return modelStore.getKind(kind).update(ID, updates);
	}

	@Override
	public boolean entityUpdateUnique(	int kind,
										Long ID,
										Map<Integer, Object> updates) throws UniqueException
	{
		return modelStore.getKind(kind).updateUnique(ID, updates);
	}

	@Override
	public void setSingletonAttribute(int singleton, int attribute, Object value)
	{
		modelStore.getSingleton(singleton).setAttribute(attribute, value);
	}

	@Override
	public boolean relationAdd(int relationID, Long a, Long b)
	{
		return modelStore.getRelation(relationID).add(a, b);
	}
	
	@Override
	public boolean relationAddUnique(int relationID, Long a, Long b) throws UniqueException
	{
		return modelStore.getRelation(relationID).addUnique(a, b);
	}

	@Override
	public boolean relationDelete(int relationID, Long a, Long b)
	{
		return modelStore.getRelation(relationID).delete(a, b);
	}

	@Override
	public Long relationReplaceB(int relationID, Long a, Long b)
	{
		return modelStore.getRelation(relationID).replaceB(a, b);
	}
	
	@Override
	public Long relationReplaceBUnique(int relationID, Long a, Long b) throws UniqueException
	{
		return modelStore.getRelation(relationID).replaceBUnique(a, b);
	}

	@Override
	public boolean relationDeleteAllForA(int relationID, Long a)
	{
		return modelStore.getRelation(relationID).deleteAllForA(a);
	}

	@Override
	public boolean relationDeleteAllForB(int relationID, Long b)
	{
		return modelStore.getRelation(relationID).deleteAllForB(b);
	}

	// *******************************************************************************************
	// Private area
	// *******************************************************************************************

	private int newSet(IKindStore kind, int setID, OrderByComparator order)
	{
		int res = sets.size();
		sets.add(new SetProxy(kind, setID, order, setManager));
		return res;
	}

	@Override
	public void release()
	{
		setManager.release();
	}
}
