package edma.util.collections.treemap.btreemap.basic;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Comparator;

import edma.util.binarysearch.BinarySearch;
import edma.util.cache.AbstractCacheObject;
import edma.util.collections.treemap.btreemap.basic.BTreeMapImpl.DeleteResult;
import edma.util.collections.treemap.btreemap.basic.BTreeMapImpl.PutResult;
import edma.util.serialize.Serializer;

public class BTreeLeafNode extends AbstractCacheObject<BTreeLeafNode, BTreeMapImpl> implements
		BTreeNode
{
	private final Object[] keys;
	private final Object[] values;

	private int size;
	protected long prev;
	protected long next;
	private int modCount;

	public BTreeLeafNode(int maxSize)
	{
		super();
		keys = new Object[maxSize];
		values = new Object[maxSize];
	}

	// Node type:
	@Override
	public boolean isLeaf()
	{
		return true;
	}

	@Override
	public BTreeLeafNode asLeaf()
	{
		return this;
	}

	@Override
	public BTreeInternalNode asInternal()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getArraySize()
	{
		return size;
	}

	@Override
	public Object getMaxKey()
	{
		return keys[size - 1];
	}

	@Override
	public Object getMinKey()
	{
		return keys[0];
	}

	@Override
	public int subTreeSize(	Object lowerBound,
							boolean lowerInclusive,
							Object upperBound,
							boolean upperInclusive)
	{
		int lowerIndex = 0;
		int upperIndex = size - 1;
		if(lowerBound != null)
		{
			if(lowerInclusive)
			{
				lowerIndex = BinarySearch.getIndexCeiling(	getOwner().getComparator(),
															lowerBound,
															keys,
															0,
															size - 1);
			}
			else
			{
				lowerIndex = BinarySearch.getIndexHigher(	getOwner().getComparator(),
															lowerBound,
															keys,
															0,
															size - 1);
			}
		}
		if(upperBound != null)
		{
			if(upperInclusive)
			{
				upperIndex = BinarySearch.getIndexFloor(getOwner().getComparator(),
														upperBound,
														keys,
														0,
														size - 1);
			}
			else
			{
				upperIndex = BinarySearch.getIndexLower(getOwner().getComparator(),
														upperBound,
														keys,
														0,
														size - 1);
			}
		}
		if(lowerIndex < 0 || upperIndex < 0) return 0;
		return (upperIndex - lowerIndex + 1);
	}

	@Override
	public int subTreeSize()
	{
		return size;
	}

	@Override
	public boolean containsKey(Object key)
	{
		int index = BinarySearch.getIndexEqual(	getOwner().getComparator(),
												key,
												keys,
												0,
												size - 1);
		return (index >= 0);
	}

	@Override
	public Object getValue(Object key)
	{
		if(!getOwner().hasValues()) return null;
		int index = BinarySearch.getIndexEqual(	getOwner().getComparator(),
												key,
												keys,
												0,
												size - 1);
		if(index < 0) return null;
		return values[index];
	}
	
	@Override
	public Object getKey(Object key)
	{
		int index = BinarySearch.getIndexEqual(	getOwner().getComparator(),
												key,
												keys,
												0,
												size - 1);
		if(index < 0) return null;
		return keys[index];
	}

	@Override
	public boolean setCursorFirst(BTreeCursor cursor)
	{
		initCursor(cursor, 0);
		return true;
	}

	@Override
	public boolean setCursorLast(BTreeCursor cursor)
	{
		initCursor(cursor, size - 1);
		return true;
	}

	@Override
	public boolean setCursorEqual(BTreeCursor cursor, Object key)
	{
		int index = BinarySearch.getIndexEqual(	getOwner().getComparator(),
												key,
												keys,
												0,
												size - 1);
		if(index < 0) return false;
		initCursor(cursor, index);
		return true;
	}

	@Override
	public boolean setCursorFloor(BTreeCursor cursor, Object key)
	{
		int index = BinarySearch.getIndexFloor(	getOwner().getComparator(),
												key,
												keys,
												0,
												size - 1);
		if(index < 0) return false;
		initCursor(cursor, index);
		return true;
	}

	@Override
	public boolean setCursorCeiling(BTreeCursor cursor, Object key)
	{
		int index = BinarySearch.getIndexCeiling(	getOwner().getComparator(),
													key,
													keys,
													0,
													size - 1);
		if(index < 0) return false;
		initCursor(cursor, index);
		return true;
	}

	@Override
	public boolean setCursorHigher(BTreeCursor cursor, Object key)
	{
		int index = BinarySearch.getIndexHigher(getOwner().getComparator(),
												key,
												keys,
												0,
												size - 1);
		if(index < 0) return false;
		initCursor(cursor, index);
		return true;
	}

	@Override
	public boolean setCursorLower(BTreeCursor cursor, Object key)
	{
		int index = BinarySearch.getIndexLower(	getOwner().getComparator(),
												key,
												keys,
												0,
												size - 1);
		if(index < 0) return false;
		initCursor(cursor, index);
		return true;
	}

	// Clear
	@Override
	public void clear()
	{
		++modCount;
		this.setUpdateFlag();
	}

	// Put
	@Override
	public void put(PutResult res, Object key, Object value)
	{
		if(size == 0)
		{
			res.overflowNode = null;
			res.oldValue = null;
			keys[0] = key;
			if(getOwner().hasValues()) values[0] = value;
			size = 1;
			modCount++;
			setUpdateFlag();
			return;
		}

		Comparator<Object> comparator = getOwner().getComparator();

		int insertIndex = BinarySearch.getIndexCeiling(	comparator,
														key,
														keys,
														0,
														size - 1);
		if(insertIndex == -1)
		{
			insertIndex = size;
		}
		else if(comparator.compare(key, keys[insertIndex]) == 0)
		{
			// Key exists, so we just replace value...
			res.overflowNode = null;
			if(getOwner().hasValues())
			{
				res.oldValue = values[insertIndex];
				values[insertIndex] = value;
				++modCount;
				this.setUpdateFlag();
			}
			else
			{
				res.oldValue = keys[insertIndex];
			}
			return;
		}
		++modCount;
		this.setUpdateFlag();
		if(size < keys.length)
		{
			doPut(res, insertIndex, key, value);
			return;
		}
		// Node is full, we need to split it
		// TODO: optimize by inserting entry while splitting
		BTreeLeafNode newNode = split();
		int splitIndex = keys.length / 2;
		if(insertIndex <= splitIndex)
		{
			doPut(res, insertIndex, key, value);
		}
		else
		{
			newNode.doPut(res, insertIndex - splitIndex, key, value);
		}
		res.overflowNode = newNode;
		newNode.setUpdateFlag();
	}

	@Override
	public void delete(DeleteResult res, Object key)
	{
		int deleteIndex = BinarySearch.getIndexEqual(	getOwner().getComparator(),
														key,
														keys,
														0,
														size - 1);
		if(deleteIndex == -1) return;
		res.updateMin = (deleteIndex == 0);
		res.updateMax = (deleteIndex == size - 1);
		if(getOwner().hasValues()) res.oldValue = values[deleteIndex];
		else res.oldValue = keys[deleteIndex];
		shrinkLeft(deleteIndex);
		++modCount;
		this.setUpdateFlag();
	}

	@Override
	public void consume(BTreeNode node)
	{
		++modCount;
		setUpdateFlag();
		BTreeLeafNode leaf = node.asLeaf();
		Object[] nodeKeys = leaf.keys;
		if(getOwner().hasValues())
		{
			Object[] nodeValues = leaf.values;
			for(int src = 0; src < leaf.size; ++src)
			{
				int dst = src + size;
				keys[dst] = nodeKeys[src];
				nodeKeys[src] = null;
				values[dst] = nodeValues[src];
				nodeValues[src] = null;
			}
		}
		else
		{
			for(int src = 0; src < leaf.size; ++src)
			{
				int dst = src + size;
				keys[dst] = nodeKeys[src];
				nodeKeys[src] = null;
			}
		}
		size += leaf.size;
		leaf.size = 0;
	}

	@Override
	public String toString(String indent)
	{
		StringBuilder res = new StringBuilder();
		res.append(indent
				+ "*******************************************************\n");
		res.append(indent + "Leaf Node: ID(" + getID() + ") Size(" + size
				+ ") Prev(" + prev + ") Next(" + next + ")\n");
		if(getOwner().hasValues())
		{
			for(int i = 0; i < size; ++i)
			{
				res.append(indent + "    Entry " + i + ":");
				res.append(" (" + keys[i] + ", " + values[i] + ")\n");
			}
		}
		else
		{
			for(int i = 0; i < size; ++i)
			{
				res.append(indent + "    Element " + i + ":");
				res.append(" (" + keys[i] + ")\n");
			}
		}
		res.append(indent
				+ "*******************************************************\n\n");
		return res.toString();
	}

	public boolean moveCursorNext(	BTreeCursor cursor,
									Object upperBound,
									boolean inclusive)
	{
		if(cursor.expectedModCount != modCount)
		{
			if(cursor.btree == getOwner() && setCursorHigher(cursor, cursor.key)) return true;
			return cursor.btree.setCursorHigher(cursor, cursor.key);
		}
		int index = cursor.index + 1;
		if(index < size)
		{
			if(!checkUpperBound(upperBound, inclusive, index)) return false;
			initCursor(cursor, index);
			return true;
		}
		if(next > 0)
		{
			boolean res = false;
			BTreeLeafNode nextNode = getOwner().loadLeafNode(next);
			if(nextNode.checkUpperBound(upperBound, inclusive, 0))
			{
				res = true;
				nextNode.initCursor(cursor, 0);
			}
			getOwner().releaseNode(nextNode);
			return res;
		}
		return false;
	}

	public boolean moveCursorPrev(	BTreeCursor cursor,
									Object lowerBound,
									boolean inclusive)
	{
		if(cursor.expectedModCount != modCount)
		{
			if(cursor.btree == getOwner() && setCursorLower(cursor, cursor.key)) return true;
			return cursor.btree.setCursorLower(cursor, cursor.key);
		}
		int index = cursor.index - 1;
		if(index >= 0)
		{
			if(!checkLowerBound(lowerBound, inclusive, index)) return false;
			initCursor(cursor, index);
			return true;
		}
		if(prev > 0)
		{
			boolean res = false;
			BTreeLeafNode prevNode = getOwner().loadLeafNode(prev);
			if(prevNode.checkLowerBound(lowerBound,
										inclusive,
										prevNode.size - 1))
			{
				res = true;
				prevNode.initCursor(cursor, prevNode.size - 1);
			}
			getOwner().releaseNode(prevNode);
			return res;
		}
		return false;
	}

	// Private helper methods:
	private void doPut(PutResult res, int insertIndex, Object key, Object value)
	{
		res.overflowNode = null;
		res.oldValue = null;
		expandRight(insertIndex);
		keys[insertIndex] = key;
		if(getOwner().hasValues()) values[insertIndex] = value;
		return;
	}

	private void expandRight(int index)
	{
		if(getOwner().hasValues())
		{
			for(int src = size - 1; src >= index; --src)
			{
				int dst = src + 1;
				keys[dst] = keys[src];
				values[dst] = values[src];
			}
		}
		else
		{
			for(int src = size - 1; src >= index; --src)
			{
				int dst = src + 1;
				keys[dst] = keys[src];
			}
		}
		++size;
	}

	private void shrinkLeft(int index)
	{
		if(getOwner().hasValues())
		{
			for(int src = index + 1; src < size; ++src)
			{
				int dst = src - 1;
				keys[dst] = keys[src];
				values[dst] = values[src];
			}
			values[size] = null;
		}
		else
		{
			for(int src = index + 1; src < size; ++src)
			{
				int dst = src - 1;
				keys[dst] = keys[src];
			}
		}
		keys[size] = null;
		--size;

	}

	private BTreeLeafNode split()
	{
		size = size / 2;
		BTreeLeafNode newNode = getOwner().createLeafNode();
		newNode.modCount = modCount;
		newNode.size = size;
		if(getOwner().hasValues())
		{
			for(int dst = 0; dst < size; ++dst)
			{
				int src = dst + size;
				newNode.keys[dst] = keys[src];
				keys[src] = null;
				newNode.values[dst] = values[src];
				values[src] = null;
			}
		}
		else
		{
			for(int dst = 0; dst < size; ++dst)
			{
				int src = dst + size;
				newNode.keys[dst] = keys[src];
				keys[src] = null;
			}
		}
		// Setting up next and prev pointers
		if(next > 0)
		{
			BTreeLeafNode nextNode = getOwner().loadLeafNode(next);
			nextNode.prev = newNode.getID();
			nextNode.setUpdateFlag();
			getOwner().releaseNode(nextNode);
		}
		long tmp = next;
		next = newNode.getID();
		newNode.prev = getID();
		newNode.next = tmp;
		return newNode;
	}

	private void initCursor(BTreeCursor cursor, int index)
	{
		cursor.btree = getOwner();
		cursor.curNodeID = getID();
		cursor.index = index;
		cursor.key = keys[index];
		if(getOwner().hasValues()) cursor.value = values[index];
		else cursor.value = keys[index];
		cursor.expectedModCount = modCount;
	}

	private boolean checkLowerBound(Object bound, boolean inclusive, int index)
	{
		if(bound == null) return true;
		int c = getOwner().getComparator().compare(bound, keys[index]);
		if(inclusive) return (c <= 0);
		return (c < 0);
	}

	private boolean checkUpperBound(Object bound, boolean inclusive, int index)
	{
		if(bound == null) return true;
		int c = getOwner().getComparator().compare(keys[index], bound);
		if(inclusive) return (c <= 0);
		return (c < 0);
	}

	@Override
	public BTreeLeafNode getActualObject()
	{
		return this;
	}

	@Override
	public void swapOut(DataOutput out) throws IOException
	{
		Serializer<Object> keySerializer = getOwner().getKeySerializer();
		Serializer<Object> valueSerializer = getOwner().getValueSerializer();
		out.writeInt(size);
		if(getOwner().hasValues())
		{
			for(int i = 0; i < size; ++i)
			{
				keySerializer.writeToStream(keys[i], out);
				valueSerializer.writeToStream(values[i], out);
			}
		}
		else
		{
			for(int i = 0; i < size; ++i)
			{
				keySerializer.writeToStream(keys[i], out);
			}
		}
		out.writeLong(prev);
		out.writeLong(next);
		out.writeInt(modCount);
	}

	@Override
	public void swapIn(DataInput in) throws IOException
	{
		Serializer<Object> keySerializer = getOwner().getKeySerializer();
		Serializer<Object> valueSerializer = getOwner().getValueSerializer();
		size = in.readInt();
		if(getOwner().hasValues())
		{
			for(int i = 0; i < size; ++i)
			{
				keys[i] = keySerializer.readFromStream(in);
				values[i] = valueSerializer.readFromStream(in);
			}
		}
		else
		{
			for(int i = 0; i < size; ++i)
			{
				keys[i] = keySerializer.readFromStream(in);
			}
		}
		prev = in.readLong();
		next = in.readLong();
		modCount = in.readInt();
	}

	@Override
	public void objectCreated()
	{
		size = 0;
		prev = 0L;
		next = 0L;
	}

	@Override
	public void objectDeleted()
	{
		size = 0;
		prev = 0L;
		next = 0L;
	}
}
