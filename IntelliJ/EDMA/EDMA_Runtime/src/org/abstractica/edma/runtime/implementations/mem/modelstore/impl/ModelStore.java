package org.abstractica.edma.runtime.implementations.mem.modelstore.impl;

import java.util.List;

import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaKind;
import org.abstractica.edma.metamodel.IMetaRelation;
import org.abstractica.edma.metamodel.IMetaSingleton;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransaction;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransactionOperations;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IDeleteConnection;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IDeleteEntity;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.INewConnection;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.INewEntity;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IUpdateEntity;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IUpdateSingleton;
import org.abstractica.edma.runtime.implementations.mem.modelstore.Entity;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IKindStore;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IModelStore;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IRelationStore;
import org.abstractica.edma.runtime.implementations.mem.modelstore.ISingletonStore;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IStorageSolution;
import org.abstractica.edma.util.ErrorLog;

public class ModelStore implements IModelStore
{
	private final String name;
	private final IMetaDataModel metaModel;
	private final TransactionBuilder transBuilder;
	private final ISingletonStore[] singletons;
	private final IKindStore[] kinds;
	private final IRelationStore[] relations;
	private volatile long curVersion;

	public ModelStore(	String name,
						IMetaDataModel metaModel,
						IStorageSolution storageSolution)
	{
		this.name = name;
		this.metaModel = metaModel;
		// Transaction builder
		transBuilder = new TransactionBuilder();

		// Singletons
		singletons = new ISingletonStore[metaModel.getNumberOfSingletons()];
		for(int i = 0; i < singletons.length; ++i)
		{
			IMetaSingleton singleton = metaModel.getSingleton(i);
			singletons[i] = storageSolution.getSingletonStorage(singleton,
																transBuilder);
		}

		// Kinds
		kinds = new IKindStore[metaModel.getNumberOfKinds()];
		for(int i = 0; i < kinds.length; ++i)
		{
			IMetaKind kind = metaModel.getKind(i);
			kinds[i] = storageSolution.getKindStorage(kind, transBuilder);
		}

		// Relations
		relations = new IRelationStore[metaModel.getNumberOfRelations()];
		for(int i = 0; i < relations.length; ++i)
		{
			IMetaRelation relation = metaModel.getRelation(i);
			relations[i] = storageSolution.getRelationStorage(	relation,
																transBuilder,
																kinds);
		}
		
		//Hookup kinds with relations
		for(IKindStore kindStore : kinds)
		{
			kindStore.hookUpRelations(relations);
		}
		
		curVersion = 0;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public IMetaDataModel getMetaModel()
	{
		return metaModel;
	}

	@Override
	public ISingletonStore getSingleton(int i)
	{
		return singletons[i];
	}

	@Override
	public IKindStore getKind(int i)
	{
		return kinds[i];
	}

	@Override
	public IRelationStore getRelation(int i)
	{
		return relations[i];
	}

	@Override
	public long getVersion()
	{
		return curVersion;
	}
	
	public void directUpdate(ITransaction t)
	{
		if(curVersion != t.getFromVersion())
		{
			String errorMsg = "Error on update: Current version: " + curVersion + " fromVersion: " + t.getFromVersion();
			ErrorLog.logError(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		for(IAtomicUpdate upd : t.getUpdates())
		{
			switch(upd.getType())
			{
				case NewEntity:
					INewEntity ne = upd.asNewEntity();
					kinds[ne.getKindIndex()].directAdd(new Entity(ne.getValue()));
					break;
				case DeleteEntity:
					IDeleteEntity de = upd.asDeleteEntity();
					kinds[de.getKindIndex()].directDelete((Long) de.getValue()[0]);
					break;
				case UpdateEntity:
					IUpdateEntity ue = upd.asUpdateEntity();
					kinds[ue.getKindIndex()].directUpdate(ue.getID(), ue.getFieldIndexes(), ue.getNewValues());
					break;
				case NewConnection:
					INewConnection nc = upd.asNewConnection();
					relations[nc.getRelationIndex()].directAdd(nc.getA(), nc.getB());
					break;
				case DeleteConnection:
					IDeleteConnection dc = upd.asDeleteConnection();
					relations[dc.getRelationIndex()].directDelete(dc.getA(), dc.getB());
					break;
				case UpdateSingleton:
					IUpdateSingleton us = upd.asUpdateSingleton();
					singletons[us.getSingletonIndex()].directUpdate(us.getAttributeIndex(), us.getNewValue());
				default:
					throw new RuntimeException("Unknown update type: "
							+ upd.getType());

			}
		}
		curVersion = t.getToVersion();
	}
	
	public void directRollback(ITransaction t)
	{
		if(curVersion != t.getToVersion())
		{
			String errorMsg = "Error on rollback: Current version: " + curVersion + " toVersion: " + t.getToVersion();
			ErrorLog.logError(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		List<IAtomicUpdate> updates = t.getUpdates();
		for(int i = updates.size() - 1; i >= 0; --i)
		{
			IAtomicUpdate upd = updates.get(i);
			switch(upd.getType())
			{
				case NewEntity:
					INewEntity ne = upd.asNewEntity();
					kinds[ne.getKindIndex()].directDelete((Long) ne.getValue()[0]);
					break;
				case DeleteEntity:
					IDeleteEntity de = upd.asDeleteEntity();
					kinds[de.getKindIndex()].directAdd(new Entity(de.getValue()));
					break;
				case UpdateEntity:
					IUpdateEntity ue = upd.asUpdateEntity();
					kinds[ue.getKindIndex()].directUpdate(ue.getID(), ue.getFieldIndexes(), ue.getOldValues());
					break;
				case NewConnection:
					INewConnection nc = upd.asNewConnection();
					relations[nc.getRelationIndex()].directDelete(nc.getA(), nc.getB());
					break;
				case DeleteConnection:
					IDeleteConnection dc = upd.asDeleteConnection();
					relations[dc.getRelationIndex()].directAdd(dc.getA(), dc.getB());
					break;
				case UpdateSingleton:
					IUpdateSingleton us = upd.asUpdateSingleton();
					singletons[us.getSingletonIndex()].directUpdate(us.getAttributeIndex(), us.getOldValue());
					break;
				default:
					throw new RuntimeException("Unknown update type: "
							+ upd.getType());

			}
		}
		curVersion = t.getFromVersion();
	}

	

	public void dump(ITransactionOperations out)
	{
		out.beginTransaction("InstanceDump_" + name, 0);
		for(ISingletonStore singleton : singletons)
		{
			singleton.dump(out);
		}
		for(IKindStore kind : kinds)
		{
			kind.dump(out);
		}
		for(IRelationStore relation : relations)
		{
			relation.dump(out);
		}
		out.endTransaction(curVersion);
	}

	@Override
	public void beginTransaction(String name)
	{
		transBuilder.beginTransaction(name, curVersion);
		++curVersion;
	}

	@Override
	public ITransaction commitTransaction()
	{
		return transBuilder.getTransaction();
	}

	@Override
	public void rollbackTransaction()
	{
		directRollback(transBuilder.getTransaction());
	}
	
	
	//ITransactionOperations implementation:
	
	@Override
	public void beginTransaction(String name, long fromVersion)
	{
		if(curVersion != fromVersion) throw new RuntimeException("Wrong version! current version: "
				+ curVersion + " expected version: " + fromVersion);
	}

	@Override
	public void newEntity(int kindIndex, Object[] value)
	{
		kinds[kindIndex].directAdd(new Entity(value));
	}

	@Override
	public void deleteEntity(int kindIndex, Object[] value)
	{
		kinds[kindIndex].directDelete((Long) value[0]);
	}

	@Override
	public void updateEntity(	int kindIndex,
								Long ID,
								int[] fields,
								Object[] oldValues,
								Object[] newValues)
	{
		kinds[kindIndex].directUpdate(ID, fields, newValues);
	}

	@Override
	public void newConnection(int relationIndex, Long a, Long b)
	{
		relations[relationIndex].directAdd(a, b);
	}

	@Override
	public void deleteConnection(int relationIndex, Long a, Long b)
	{
		relations[relationIndex].directDelete(a, b);
	}

	@Override
	public void updateSingleton(int singletonIndex,
								int attributeIndex,
								Object oldValue,
								Object newValue)
	{
		singletons[singletonIndex].directUpdate(attributeIndex, newValue);
	}

	@Override
	public void endTransaction(long newVersion)
	{
		curVersion = newVersion;
	}

	
}
