package org.abstractica.edma.util.collections.treemap.btreemap.basic;

import java.util.Comparator;

public interface BTreeMap
{
	public long getID();
	public int size();
	public int subMapSize(	Object lowerBound,
							boolean lowerInclusive,
							Object upperBound,
							boolean upperInclusive);

	public boolean containsKey(Object key);
	public Object getValue(Object key);
	public Object getKey(Object key);
	public boolean setCursorFirst(BTreeCursor cursor);
	public boolean setCursorLast(BTreeCursor cursor);
	public boolean setCursorEqual(BTreeCursor cursor, Object key);	
	public boolean setCursorLessThan(BTreeCursor cursor, Object key, boolean inclusive);	
	public boolean setCursorGreaterThan(BTreeCursor cursor, Object key, boolean inclusive);
	public boolean setCursorFloor(BTreeCursor cursor, Object key);
	public boolean setCursorCeiling(BTreeCursor cursor, Object key);
	public boolean setCursorHigher(BTreeCursor cursor, Object key);
	public boolean setCursorLower(BTreeCursor cursor, Object key);
	public Comparator<Object> getComparator();
	
	//Modifying
	public void clear();
	public Object put(Object key, Object value);
	public Object delete(Object key);
}
