package org.abstractica.edma.util.collections.treemap.btreemap.basic;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Comparator;

import org.abstractica.edma.util.binarysearch.BinarySearch;
import org.abstractica.edma.util.cache.AbstractCacheObject;
import org.abstractica.edma.util.collections.treemap.btreemap.basic.BTreeMapImpl.DeleteResult;
import org.abstractica.edma.util.collections.treemap.btreemap.basic.BTreeMapImpl.PutResult;
import org.abstractica.edma.util.serialize.Serializer;

public class BTreeInternalNode extends AbstractCacheObject<BTreeInternalNode, BTreeMapImpl>
		implements BTreeNode
{
	private final Object[] minKeys;
	private final Object[] maxKeys;
	private final long[] childrenIDs;
	private final int[] subTreeSizes;

	private int subTreeSize;
	private int childCount;
	private boolean childrenAreLeafs;

	public BTreeInternalNode(int maxSize)
	{
		this.minKeys = new Object[maxSize];
		this.maxKeys = new Object[maxSize];
		this.childrenIDs = new long[maxSize];
		this.subTreeSizes = new int[maxSize];
	}

	public void createRoot(	BTreeNode oldRoot,
							BTreeNode overFlow)
	{
		maxKeys[0] = oldRoot.getMaxKey();
		minKeys[0] = oldRoot.getMinKey();
		childrenIDs[0] = oldRoot.getID();
		subTreeSizes[0] = oldRoot.subTreeSize();
		maxKeys[1] = overFlow.getMaxKey();
		minKeys[1] = overFlow.getMinKey();
		childrenIDs[1] = overFlow.getID();
		subTreeSizes[1] = overFlow.subTreeSize();
		childCount = 2;
		childrenAreLeafs = oldRoot.isLeaf();
		subTreeSize = subTreeSizes[0] + subTreeSizes[1];
		setUpdateFlag();
	}

	@Override
	public int getArraySize()
	{
		return childCount;
	}

	@Override
	public boolean isLeaf()
	{
		return false;
	}

	@Override
	public BTreeLeafNode asLeaf()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public BTreeInternalNode asInternal()
	{
		return this;
	}

	public Object getMaxKey()
	{
		return maxKeys[childCount - 1];
	}

	public Object getMinKey()
	{
		return minKeys[0];
	}

	@Override
	public int subTreeSize(	Object lowerBound,
								boolean lowerInclusive,
								Object upperBound,
								boolean upperInclusive)
	{
		if(upperBound == null && lowerBound == null) return subTreeSize;
		Comparator<Object> comparator = getOwner().getComparator();
		int lowerIndex = 0;
		int upperIndex = childCount - 1;
		if(lowerBound != null)
		{
			if(lowerInclusive)
			{
				lowerIndex = BinarySearch.getIndexLower(comparator,
														lowerBound,
														maxKeys,
														0,
														childCount - 1);
			}
			else
			{
				lowerIndex = BinarySearch.getIndexFloor(comparator,
														lowerBound,
														maxKeys,
														0,
														childCount - 1);
			}
			++lowerIndex;
			if(lowerIndex == childCount) return 0;
		}
		if(upperBound != null)
		{
			if(upperInclusive)
			{
				upperIndex = BinarySearch.getIndexHigher(	comparator,
															upperBound,
															minKeys,
															0,
															childCount - 1);
			}
			else
			{
				upperIndex = BinarySearch.getIndexCeiling(	comparator,
															upperBound,
															minKeys,
															0,
															childCount - 1);
			}
			if(upperIndex < 0) upperIndex = childCount;
			--upperIndex;
			if(upperIndex < 0) return 0;
		}
		if(lowerIndex == upperIndex)
		{
			BTreeNode childNode = loadChildNode(lowerIndex);
			int res = childNode.subTreeSize(lowerBound, lowerInclusive, upperBound, upperInclusive);
			getOwner().releaseNode(childNode);
			return res;
		}
		
		//Lower
		BTreeNode childNode = loadChildNode(lowerIndex);
		int res = childNode.subTreeSize(lowerBound, lowerInclusive, null, false);
		getOwner().releaseNode(childNode);
		
		//Upper
		childNode = loadChildNode(upperIndex);
		res += childNode.subTreeSize(null, false, upperBound, upperInclusive);
		getOwner().releaseNode(childNode);
		
		//Intermediate
		for(int i = lowerIndex + 1; i < upperIndex; ++i)
		{
			res += subTreeSizes[i];
		}
		
		return res;
	}

	@Override
	public int subTreeSize()
	{
		return subTreeSize;
	}

	@Override
	public boolean containsKey(Object key)
	{
		Comparator<Object> comparator = getOwner().getComparator();
		int index = BinarySearch.getIndexCeiling(	comparator,
													key,
													maxKeys,
													0,
													childCount - 1);
		if(index < 0) return false;
		int c = comparator.compare(key, minKeys[index]);
		if(c == 0) return true;
		if(c < 0) return false;
		// TODO: dont know if this is an optimization??
		c = comparator.compare(key, maxKeys[index]);
		if(c == 0) return true;
		BTreeNode child = loadChildNode(index);
		boolean res = child.containsKey(key);
		getOwner().releaseNode(child);
		return res;
	}

	@Override
	public Object getValue(Object key)
	{
		int index = BinarySearch.getIndexCeiling(	getOwner().getComparator(),
													key,
													maxKeys,
													0,
													childCount - 1);
		if(index < 0) return null;
		BTreeNode child = loadChildNode(index);
		Object res = child.getValue(key);
		getOwner().releaseNode(child);
		return res;
	}
	
	@Override
	public Object getKey(Object key)
	{
		int index = BinarySearch.getIndexCeiling(	getOwner().getComparator(),
													key,
													maxKeys,
													0,
													childCount - 1);
		if(index < 0) return null;
		BTreeNode child = loadChildNode(index);
		Object res = child.getKey(key);
		getOwner().releaseNode(child);
		return res;
	}

	@Override
	public boolean setCursorFirst(BTreeCursor cursor)
	{
		if(childCount == 0) return false;
		BTreeNode child = loadChildNode(0);
		boolean res = child.setCursorFirst(cursor);
		getOwner().releaseNode(child);
		return res;
	}

	@Override
	public boolean setCursorLast(BTreeCursor cursor)
	{
		if(childCount == 0) return false;
		BTreeNode child = loadChildNode(childCount - 1);
		boolean res = child.setCursorLast(cursor);
		getOwner().releaseNode(child);
		return res;
	}

	@Override
	public boolean setCursorEqual(BTreeCursor cursor, Object key)
	{
		int index = BinarySearch.getIndexCeiling(	getOwner().getComparator(),
													key,
													maxKeys,
													0,
													childCount - 1);
		if(index < 0) return false;
		BTreeNode child = loadChildNode(index);
		boolean res = child.setCursorEqual(cursor, key);
		getOwner().releaseNode(child);
		return res;
	}

	@Override
	public boolean setCursorFloor(BTreeCursor cursor, Object key)
	{
		int index = BinarySearch.getIndexFloor(	getOwner().getComparator(),
												key,
												minKeys,
												0,
												childCount - 1);
		if(index < 0) return false;
		BTreeNode child = loadChildNode(index);
		boolean res = child.setCursorFloor(cursor, key);
		getOwner().releaseNode(child);
		return res;
	}

	@Override
	public boolean setCursorCeiling(BTreeCursor cursor, Object key)
	{
		int index = BinarySearch.getIndexCeiling(	getOwner().getComparator(),
													key,
													maxKeys,
													0,
													childCount - 1);
		if(index < 0) return false;
		BTreeNode child = loadChildNode(index);
		boolean res = child.setCursorCeiling(cursor, key);
		getOwner().releaseNode(child);
		return res;
	}

	@Override
	public boolean setCursorHigher(BTreeCursor cursor, Object key)
	{
		int index = BinarySearch.getIndexHigher(getOwner().getComparator(),
												key,
												maxKeys,
												0,
												childCount - 1);
		if(index < 0) return false;
		BTreeNode child = loadChildNode(index);
		boolean res = child.setCursorHigher(cursor, key);
		getOwner().releaseNode(child);
		return res;
	}

	@Override
	public boolean setCursorLower(BTreeCursor cursor, Object key)
	{
		int index = BinarySearch.getIndexLower(	getOwner().getComparator(),
												key,
												minKeys,
												0,
												childCount - 1);
		if(index < 0) return false;
		BTreeNode child = loadChildNode(index);
		boolean res = child.setCursorLower(cursor, key);
		getOwner().releaseNode(child);
		return res;
	}

	public String toString(String indent)
	{
		StringBuilder res = new StringBuilder();
		res.append(indent + "************************************\n");
		res.append(indent + "Internal Node: ID(" + getID() + ") Child count("
				+ childCount + ") SubTreeSize(" + subTreeSize + ")\n");
		for(int i = 0; i < childCount; ++i)
		{
			res.append(indent + "    Child " + i + ":");
			res.append(" ID(" + childrenIDs[i] + ")");
			res.append(" Min(" + minKeys[i] + ")");
			res.append(" Max(" + maxKeys[i] + ")");
			res.append(" SubTreeSize(" + subTreeSizes[i] + ")\n");

		}
		res.append(indent + "************************************\n\n");
		for(int i = 0; i < childCount; ++i)
		{
			BTreeNode child = loadChildNode(i);
			res.append(child.toString("    " + indent));
			getOwner().releaseNode(child);
		}
		return res.toString();
	}

	// Used by tree to reduce height when size is 1:
	public long getFirstChildID()
	{
		return childrenIDs[0];
	}

	public boolean childrenAreLeafs()
	{
		return childrenAreLeafs;
	}

	// Modifying:

	@Override
	public void clear()
	{
		for(int i = 0; i < childCount; ++i)
		{
			BTreeNode child = loadChildNode(i);
			child.clear();
			getOwner().deleteNode(child);
		}
	}

	@Override
	public void put(PutResult res, Object key, Object value)
	{
		Comparator<Object> comparator = getOwner().getComparator();

		int childIndex = BinarySearch.getIndexCeiling(	comparator,
														key,
														maxKeys,
														0,
														childCount - 1);
		if(childIndex == -1)
		{
			childIndex = childCount - 1;
		}

		BTreeNode child = loadChildNode(childIndex);

		child.put(res, key, value);
		// If we actually added e new entry, we need to update the sizes
		if(res.oldValue == null)
		{
			++subTreeSize;
			++subTreeSizes[childIndex];
			setUpdateFlag();
		}
		if(res.overflowNode == null)
		{
			// No overflow
			Object min = child.getMinKey();
			if(comparator.compare(min, minKeys[childIndex]) < 0)
			{
				minKeys[childIndex] = min;
				setUpdateFlag();
			}
			else if(childIndex == childCount - 1)
			{
				Object max = child.getMaxKey();
				if(comparator.compare(max, maxKeys[childIndex]) > 0)
				{
					maxKeys[childIndex] = max;
					setUpdateFlag();
				}
			}
			getOwner().releaseNode(child);
			return;
		}

		// We have an overflow node
		// Update child min and max and subtree size
		maxKeys[childIndex] = child.getMaxKey();
		minKeys[childIndex] = child.getMinKey();
		subTreeSizes[childIndex] = child.subTreeSize();
		int insertIndex = childIndex + 1;

		// We are done with the child node so we release it
		getOwner().releaseNode(child);
		child = null;

		if(childCount < minKeys.length)
		{
			// Insert overflow node...
			doInsertChild(res.overflowNode, insertIndex);
			getOwner().releaseNode(res.overflowNode);
			res.overflowNode = null;
			setUpdateFlag();
			return;
		}

		// Node is full, we need to split it
		// TODO: Optimize by inserting node while splitting...
		BTreeInternalNode newNode = split();
		int splitIndex = minKeys.length / 2;
		if(insertIndex <= splitIndex)
		{
			doInsertChild(res.overflowNode, insertIndex);
		}
		else
		{
			newNode.doInsertChild(res.overflowNode, insertIndex - splitIndex);
		}
		getOwner().releaseNode(res.overflowNode);
		res.overflowNode = newNode;
		setUpdateFlag();
		newNode.setUpdateFlag();
	}

	@Override
	public void delete(DeleteResult res, Object key)
	{
		Comparator<Object> comp = getOwner().getComparator();
		int deleteIndex = BinarySearch.getIndexCeiling(	comp,
														key,
														maxKeys,
														0,
														childCount - 1);
		if(deleteIndex == -1) return;
		if(comp.compare(key, minKeys[deleteIndex]) < 0) return;
		BTreeNode childNode = loadChildNode(deleteIndex);
		int oldSize = childNode.getArraySize();
		childNode.delete(res, key);

		if(res.oldValue == null)
		{
			getOwner().releaseNode(childNode);
			return;
		}
		// We actually deleted an entry...
		--subTreeSize;
		--subTreeSizes[deleteIndex];
		setUpdateFlag();
		boolean childSameSize = (oldSize == childNode.getArraySize());

		// We have deleted a value, test if we need to merge nodes
		int mergeSize = childNode.isLeaf() ? getOwner().getLeafMergeSize()
				: getOwner().getInternalMergeSize();
		if(childSameSize || childNode.getArraySize() >= mergeSize)
		{
			// No merge, test for min and max updates
			if(res.updateMin)
			{
				minKeys[deleteIndex] = childNode.getMinKey();
				if(deleteIndex > 0) res.updateMin = false;
				// setUpdateFlag();
			}
			if(res.updateMax)
			{
				maxKeys[deleteIndex] = childNode.getMaxKey();
				if(deleteIndex < childCount - 1) res.updateMax = false;
				// setUpdateFlag();
			}
			getOwner().releaseNode(childNode);
			return;
		}

		if(childNode.getArraySize() == 0)
		{
			deleteChildNode(deleteIndex, childNode);
			res.updateMin = (deleteIndex == 0);
			res.updateMax = (deleteIndex == childCount);
			return;
		}

		BTreeNode prev = null;
		BTreeNode next = null;
		if(deleteIndex > 0) prev = loadChildNode(deleteIndex - 1);
		if(deleteIndex < childCount - 1) next = loadChildNode(deleteIndex + 1);
		int mergeIndex = -1;
		int bestSize = minKeys.length;
		if(prev != null
				&& prev.getArraySize() + childNode.getArraySize() <= mergeSize)
		{
			mergeIndex = deleteIndex - 1;
			bestSize = prev.getArraySize();
		}
		if(next != null && next.getArraySize() < bestSize
				&& next.getArraySize() + childNode.getArraySize() <= mergeSize)
		{
			mergeIndex = deleteIndex + 1;
			bestSize = next.getArraySize();
		}
		if(mergeIndex == -1)
		{
			// No merge test for min and max updates
			if(res.updateMin)
			{
				minKeys[deleteIndex] = childNode.getMinKey();
				if(deleteIndex > 0) res.updateMin = false;
				// setUpdateFlag();
			}
			if(res.updateMax)
			{
				maxKeys[deleteIndex] = childNode.getMaxKey();
				if(deleteIndex < childCount - 1) res.updateMax = false;
				// setUpdateFlag();
			}
			if(prev != null) getOwner().releaseNode(prev);
			if(next != null) getOwner().releaseNode(next);
			getOwner().releaseNode(childNode);
			return;
		}

		// Now we do a merge...
		if(mergeIndex > deleteIndex)
		{
			// Merge next into childNode
			mergeBIntoA(deleteIndex, childNode, mergeIndex, next);
			// Max is updated for child so if child are last we must update max
			// in parent
			if(deleteIndex == childCount - 1) res.updateMax = true;
			// Update min
			if(res.updateMin)
			{
				minKeys[deleteIndex] = childNode.getMinKey();
				if(deleteIndex > 0) res.updateMin = false;
			}
			if(prev != null) getOwner().releaseNode(prev);
			getOwner().releaseNode(childNode);
			return;
		}
		// Merge this into prev...
		mergeBIntoA(mergeIndex, prev, deleteIndex, childNode);
		if(mergeIndex == childCount - 1) res.updateMax = true;
		if(prev != null) getOwner().releaseNode(prev);
		if(next != null) getOwner().releaseNode(next);
		return;
	}

	@Override
	public void consume(BTreeNode node)
	{
		BTreeInternalNode n = node.asInternal();
		for(int src = 0; src < n.childCount; ++src)
		{
			int dst = src + childCount;
			minKeys[dst] = n.minKeys[src];
			maxKeys[dst] = n.maxKeys[src];
			childrenIDs[dst] = n.childrenIDs[src];
			subTreeSizes[dst] = n.subTreeSizes[src];
		}
		childCount += n.childCount;
		subTreeSize += n.subTreeSize;
		n.subTreeSize = 0;
		n.childCount = 0;
		setUpdateFlag();
	}

	// Private helper functions...

	private void mergeBIntoA(	int indexA,
								BTreeNode nodeA,
								int indexB,
								BTreeNode nodeB)
	{
		nodeA.consume(nodeB);
		// Update A max
		maxKeys[indexA] = nodeA.getMaxKey();
		deleteChildNode(indexB, nodeB);
		// setUpdateFlag();
	}

	private void deleteChildNode(int index, BTreeNode node)
	{
		// If leaf node, rewire next and prev
		if(node.isLeaf())
		{
			BTreeLeafNode leaf = node.asLeaf();
			if(leaf.next > 0)
			{
				BTreeLeafNode nextNode = getOwner().loadLeafNode(leaf.next);
				nextNode.prev = leaf.prev;
				nextNode.setUpdateFlag();
				getOwner().releaseNode(nextNode);
			}
			if(leaf.prev > 0)
			{
				BTreeLeafNode prevNode = getOwner().loadLeafNode(leaf.prev);
				prevNode.next = leaf.next;
				prevNode.setUpdateFlag();
				getOwner().releaseNode(prevNode);
			}
		}

		for(int src = index + 1; src < childCount; ++src)
		{
			int dst = src - 1;
			minKeys[dst] = minKeys[src];
			maxKeys[dst] = maxKeys[src];
			childrenIDs[dst] = childrenIDs[src];
			subTreeSizes[dst] = subTreeSizes[src];
		}
		--childCount;
		minKeys[childCount] = null;
		maxKeys[childCount] = null;
		childrenIDs[childCount] = 0;
		subTreeSizes[childCount] = 0;
		getOwner().deleteNode(node);
		setUpdateFlag();
	}

	private void doInsertChild(BTreeNode child, int insertIndex)
	{
		// Insert overflow node...
		expandRight(insertIndex);
		childrenIDs[insertIndex] = child.getID();
		minKeys[insertIndex] = child.getMinKey();
		maxKeys[insertIndex] = child.getMaxKey();
		subTreeSizes[insertIndex] = child.subTreeSize();
	}

	private BTreeInternalNode split()
	{
		childCount = childCount / 2;
		BTreeInternalNode newNode = getOwner().createInternalNode();
		newNode.childrenAreLeafs = childrenAreLeafs;
		newNode.childCount = childCount;
		newNode.subTreeSize = 0;
		for(int dst = 0; dst < childCount; ++dst)
		{
			int src = dst + childCount;

			newNode.maxKeys[dst] = maxKeys[src];
			maxKeys[src] = null;

			newNode.minKeys[dst] = minKeys[src];
			minKeys[src] = null;

			newNode.childrenIDs[dst] = childrenIDs[src];
			childrenIDs[src] = 0;

			newNode.subTreeSizes[dst] = subTreeSizes[src];
			newNode.subTreeSize += subTreeSizes[src];
			subTreeSize -= subTreeSizes[src];
			subTreeSizes[src] = 0;
		}
		return newNode;
	}

	private void expandRight(int index)
	{
		for(int src = childCount - 1; src >= index; --src)
		{
			int dst = src + 1;
			maxKeys[dst] = maxKeys[src];
			minKeys[dst] = minKeys[src];
			childrenIDs[dst] = childrenIDs[src];
			subTreeSizes[dst] = subTreeSizes[src];
		}
		++childCount;
	}

	private BTreeNode loadChildNode(int childIndex)
	{
		if(childrenAreLeafs) return getOwner().loadLeafNode(childrenIDs[childIndex]);
		return getOwner().loadInternalNode(childrenIDs[childIndex]);
	}

	@Override
	public BTreeInternalNode getActualObject()
	{
		return this;
	}

	@Override
	public void swapOut(DataOutput out) throws IOException
	{
		Serializer<Object> keySerializer = getOwner().getKeySerializer();
		out.writeInt(childCount);
		out.writeInt(subTreeSize);
		out.writeBoolean(childrenAreLeafs);
		for(int i = 0; i < childCount; ++i)
		{
			keySerializer.writeToStream(minKeys[i], out);
			keySerializer.writeToStream(maxKeys[i], out);
			out.writeLong(childrenIDs[i]);
			out.writeInt(subTreeSizes[i]);
		}
	}

	@Override
	public void swapIn(DataInput in) throws IOException
	{
		Serializer<Object> keySerializer = getOwner().getKeySerializer();
		childCount = in.readInt();
		subTreeSize = in.readInt();
		childrenAreLeafs = in.readBoolean();
		for(int i = 0; i < childCount; ++i)
		{
			minKeys[i] = keySerializer.readFromStream(in);
			maxKeys[i] = keySerializer.readFromStream(in);
			childrenIDs[i] = in.readLong();
			subTreeSizes[i] = in.readInt();
		}
	}

	@Override
	public void objectCreated()
	{
		subTreeSize = 0;
		childCount = 0;
		childrenAreLeafs = false;
	}

	@Override
	public void objectDeleted()
	{
		subTreeSize = 0;
		childCount = 0;
		childrenAreLeafs = false;
	}
}
