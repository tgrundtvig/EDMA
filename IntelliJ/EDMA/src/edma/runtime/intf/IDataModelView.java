package edma.runtime.intf;

import java.util.Iterator;

import edma.metamodel.IMetaDataModel;
import edma.runtime.implementations.common.transactions.ITransactionOperations;

public interface IDataModelView
{
	//Basic info...
	public String getName();
	public long getVersion();
	public IMetaDataModel getMetaModel();
	
	// Dump current version
	public void dump(ITransactionOperations out);
	
	// Singleton
	public Object getSingletonAttribute(int singleton, int attribute);
	
	//Kinds
	public IEntity kindGetFromID(int kind, Long id);
	
	public int kindGetAll(int kind);
	
	//Indexes
	public IIndex getKindIndex(int kind, int indexID);
	
	public IIndex getRelationIndexOnA(int relID, int indexID, Long bID);
	
	public IIndex getRelationIndexOnB(int relID, int indexID, Long aID);
	
	// Sets
	public int setExtensionUp(int set, int kind);
	
	public int setExtensionDown(int set, int kind);

	public int setIntersect(int setA, int setB);

	public int setUnion(int setA, int setB);

	public int setSubtract(int setA, int setB);
	
	public int setFromIDs(int kind, Iterable<Long> ids);
	
	//Ordering
	public int setOrderByID(int set, boolean desc);
	public int setSubOrderByID(int set, boolean desc);
	
	public int setOrderBy(int set, int attribute, boolean desc);
	public int setSubOrderBy(int set, int attribute, boolean desc);
	
	// access
	public int setGetSize(int set);
	
	public boolean setContains(int set, Long id);
	
	public boolean setContainsAll(int setA, int setB);

	public Iterator<IEntity> setGetIterator(int set);

	// Relations
	public Long relationAsAGetB(int relationIndex, Long entityA);

	public Long relationAsBGetA(int relationIndex, Long entityB);
	
	public int relationAsAGetBSet(int relationIndex, Long entityA);

	public int relationAsBGetASet(int relationIndex, Long entityB);

	public int relationAsASetGetBSet(int relationIndex, int setA);

	public int relationAsBSetGetASet(int relationIndex, int setB);
	
	
	//Access to the update interface
	public IDataModelUpdate getUpdateInterface(); //Throws runtime exception if this is not an update
	
	//Release resources
	public void release();
}