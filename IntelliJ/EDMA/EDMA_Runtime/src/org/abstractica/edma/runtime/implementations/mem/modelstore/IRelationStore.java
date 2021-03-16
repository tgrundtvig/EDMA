package org.abstractica.edma.runtime.implementations.mem.modelstore;

import java.util.Map;

import org.abstractica.edma.runtime.implementations.common.transactions.IAtomicOperations;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManager;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.runtime.intf.exceptions.UniqueException;


public interface IRelationStore
{
	public int getKindA();
	public int getKindB();
	//Viewing
	public Long asAGetB(Long a); //Only --- and >--
	public Long asBGetA(Long b); //Only ---
	public int asAGetBSet(Long a, ISetManager setManager, boolean doCopy); //Only >-<
	public int asBGetASet(Long b, ISetManager setManager, boolean doCopy); //Only >-< and >--
	public int asASetGetBSet(int aSetID, ISetManager setManager, boolean doCopy);
	public int asBSetGetASet(int bSetID, ISetManager setManager, boolean doCopy);
	
	//Indexes
	public IRelIndex getIndexOnA(int indexID);
	public IRelIndex getIndexOnB(int indexID);
	
	//Index updates
	public Object extractIndexUpdateOnA(IEntity entity, Map<Integer, Object> updates);
	public void testUniquenessOnA(Object update) throws UniqueException;
	public void doUpdateOnA(Object update);
	public void directUpdateOnA(IEntity entity, Map<Integer, Object> updates);
	
	public Object extractIndexUpdateOnB(IEntity entity, Map<Integer, Object> updates);
	public void testUniquenessOnB(Object update) throws UniqueException;
	public void doUpdateOnB(Object update);
	public void directUpdateOnB(IEntity entity, Map<Integer, Object> updates);
	
	//Updating
	public boolean add(Long a, Long b);
	public boolean addUnique(Long a, Long b) throws UniqueException;
	public boolean delete(Long a, Long b);
	public Long replaceB(Long a, Long b); //Only --- or >--, returns old b or null if there was no old b
	public Long replaceBUnique(Long a, Long b) throws UniqueException;
	public boolean deleteAllForA(Long a); //returns true if any was deleted
	public boolean deleteAllForB(Long b); //returns true if any was deleted
	
	//Direct adding and deleting
	public void directAdd(Long a, Long b);
	public void directDelete(Long a, Long b);
	
	//Dump content
	public void dump(IAtomicOperations out);
}


