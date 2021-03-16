package edma.runtime.implementations.mem.sets;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

import edma.runtime.implementations.common.collectionfactory.DSet;

public interface ISetManager
{
	//Creation
	public int emptySet();
	public int newSet(Set<Long> set, boolean doCopy);
	public int transferSet(DSet<Long> set);
	public int newSet2(Collection<Long> Ids); 
	public int union(int setA, int setB);
	public int intersection(int setA, int setB);
	public int subtraction(int setA, int setB);
	public int order(int setID, Comparator<Long> comparator);
	
	//Access
	public Iterator<Long> getIterator(int setID);
	public int getSize(int setID);
	public boolean contains(int setID, Long id);
	public boolean containsAll(int setIDA, int setIDB);
	
	//Release resources
	public void release();
}
