package org.abstractica.edma.runtime.implementations.mem.modelstore;

import java.util.Map;

import org.abstractica.edma.metamodel.IMetaKind;
import org.abstractica.edma.runtime.implementations.common.transactions.IAtomicOperations;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManager;
import org.abstractica.edma.runtime.implementations.mem.sets.optimized.ISet;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.runtime.intf.exceptions.UniqueException;

public interface IKindStore
{
	// View
	public IMetaKind getMetaKind();

	public boolean exists(Long ID);

	public IEntity get(Long ID);

	public int getAllIDs(ISetManager setManager, boolean doCopy);

	public int getEmptySet(ISetManager setManager);

	public IKindIndex getIndex(int index);

	// Update
	public IEntity add(IEntity e);

	public IEntity addUnique(IEntity e) throws UniqueException;

	public boolean update(Long ID, Map<Integer, Object> updates);

	public boolean updateUnique(Long ID, Map<Integer, Object> updates) throws UniqueException;

	public boolean remove(Long ID);

	// Direct add, delete and update
	public void directAdd(IEntity e);

	public void directDelete(Long ID);

	public void directUpdate(Long ID, int[] fields, Object[] newValues);

	// Dump content
	public void dump(IAtomicOperations out);
	
	//Hookup
	public void hookUpRelations(IRelationStore[] relations);
}
