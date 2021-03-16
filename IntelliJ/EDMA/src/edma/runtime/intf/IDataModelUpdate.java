package edma.runtime.intf;

import java.util.Map;

import edma.runtime.intf.exceptions.UniqueException;


public interface IDataModelUpdate extends IDataModelView
{
	// Entity
	public IEntity entityNew(int kind, Object[] values);
	public IEntity entityNewUnique(int kind, Object[] values) throws UniqueException;

	public boolean entityDelete(int kind, Long id);

	public boolean entityUpdate(int kind, Long id, Map<Integer, Object> updates);
	
	public boolean entityUpdateUnique(int kind, Long id, Map<Integer, Object> updates) throws UniqueException;
	
	// Singleton
	public void setSingletonAttribute(int singleton, int attribute, Object value);
	
	//Relations
	
	//Adds the connection (a, b) to the relation if it is not already there
	//Returns false if the connection was already in the relation, otherwise true
	public boolean relationAdd(int relationID, Long a, Long b);
	public boolean relationAddUnique(int relationID, Long a, Long b) throws UniqueException;
	
	//Deletes the connection (a, b) from the relation if it exists
	//returns true if it existed otherwise false
	public boolean relationDelete(int relationID, Long a, Long b);
	
	//Replaces the connection (a, b0) with (a, b) returns b0 if there was a connection (a, b0)
	// otherwise null.
	public Long relationReplaceB(int relationID, Long a, Long b);
	public Long relationReplaceBUnique(int relationID, Long a, Long b) throws UniqueException;
	
	//Deletes all connections (a, x), returns true if any existed
	public boolean relationDeleteAllForA(int relationID, Long a);
	
	//Deletes all connections(x, b), returns true if any existed
	public boolean relationDeleteAllForB(int relationID, Long b);
}
