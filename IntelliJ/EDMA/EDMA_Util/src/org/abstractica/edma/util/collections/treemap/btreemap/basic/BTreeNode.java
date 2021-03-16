package org.abstractica.edma.util.collections.treemap.btreemap.basic;

public interface BTreeNode
{
	// Get the id for this node
	public long getID();

	// The number of elements in subtree
	public int subTreeSize(	Object lowerBound,
							boolean lowerInclusive,
							Object upperBound,
							boolean upperInclusive);

	public int subTreeSize();

	// The size of this nodes internal arrays
	public int getArraySize();

	// Type of node:
	public boolean isLeaf();

	public BTreeLeafNode asLeaf();

	public BTreeInternalNode asInternal();

	// Viewing
	public Object getMaxKey();

	public Object getMinKey();

	public boolean containsKey(Object key);

	public Object getValue(Object key);
	
	public Object getKey(Object key);

	public boolean setCursorFirst(BTreeCursor cursor);

	public boolean setCursorLast(BTreeCursor cursor);

	public boolean setCursorEqual(BTreeCursor cursor, Object key);

	public boolean setCursorFloor(BTreeCursor cursor, Object key);

	public boolean setCursorCeiling(BTreeCursor cursor, Object key);

	public boolean setCursorHigher(BTreeCursor cursor, Object key);

	public boolean setCursorLower(BTreeCursor cursor, Object key);

	public String toString(String indent);

	// Modifying:
	public void clear();

	public void put(BTreeMapImpl.PutResult res, Object key, Object value);

	public void delete(BTreeMapImpl.DeleteResult res, Object key);

	public void consume(BTreeNode node);

	/*
	 * 
	 * // Node create and delete public BTreeLeafNode createLeafNode();
	 * 
	 * public BTreeInternalNode createInternalNode();
	 * 
	 * public void releaseNode(BTreeNode node);
	 * 
	 * public void deleteNode(BTreeNode node);
	 * 
	 * public BTreeLeafNode loadLeafNode(long nodeID);
	 * 
	 * public BTreeInternalNode loadInternalNode(long nodeID);
	 */
}
