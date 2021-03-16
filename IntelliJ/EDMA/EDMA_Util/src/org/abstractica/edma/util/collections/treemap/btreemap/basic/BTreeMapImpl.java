package org.abstractica.edma.util.collections.treemap.btreemap.basic;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Comparator;

import org.abstractica.edma.util.cache.AbstractCacheObject;
import org.abstractica.edma.util.cache.ObjectNotFoundException;
import org.abstractica.edma.util.serialize.Serializer;

public class BTreeMapImpl extends
		AbstractCacheObject<BTreeMapImpl, BTreeManager>
		implements BTreeMap
{
	private final PutResult reuseablePutResult;
	private final DeleteResult reuseableDeleteResult;

	private Comparator<Object> comparator;
	private Serializer<Object> keySerializer;
	private Serializer<Object> valueSerializer;

	private int comparatorIndex;
	private int keySerializerIndex;
	private int valueSerializerIndex;

	private boolean hasValues;
	private int size;
	private long rootID;
	private boolean rootIsLeaf;

	public BTreeMapImpl()
	{
		super();
		this.reuseablePutResult = new PutResult();
		this.reuseableDeleteResult = new DeleteResult();
	}

	public void init(	boolean hasValues,
	                 	Comparator<Object> comparator,
						int comparatorIndex,
						Serializer<Object> keySerializer,
						int keySerializerIndex,
						Serializer<Object> valueSerializer,
						int valueSerializerIndex)
	{
		this.hasValues = hasValues;
		this.comparator = comparator;
		this.keySerializer = keySerializer;
		this.valueSerializer = valueSerializer;
		this.comparatorIndex = comparatorIndex;
		this.keySerializerIndex = keySerializerIndex;
		this.valueSerializerIndex = valueSerializerIndex;
	}

	public boolean hasValues()
	{
		return hasValues;
	}

	public int getLeafMergeSize()
	{
		return getOwner().getCache().getLeafNodeMergeSize();
	}

	public int getInternalMergeSize()
	{
		return getOwner().getCache().getInternalNodeMergeSize();
	}

	public Serializer<Object> getKeySerializer()
	{
		return keySerializer;
	}

	public Serializer<Object> getValueSerializer()
	{
		return valueSerializer;
	}

	public void clear()
	{
		BTreeNode rootNode = getRoot();
		if(rootNode == null) return;
		rootNode.clear();
		deleteNode(rootNode);
		rootID = 0;
		size = 0;
		setUpdateFlag();
	}

	public Object put(Object key, Object value)
	{
		doPut(reuseablePutResult, key, value);
		if(reuseablePutResult.oldValue == null)
		{
			++size;
			setUpdateFlag();
		}
		return reuseablePutResult.oldValue;
	}

	private void doPut(PutResult res, Object key, Object value)
	{
		BTreeNode rootNode = getRoot();
		if(rootNode == null)
		{
			// This is an empty tree so we create a new leaf node as root.
			rootIsLeaf = true;
			BTreeLeafNode newRoot = createLeafNode();
			rootID = newRoot.getID();
			newRoot.put(res, key, value);
			releaseNode(newRoot);
			size = 0;
			return;
		}
		rootNode.put(res, key, value);
		if(res.overflowNode == null)
		{
			releaseNode(rootNode);
			return;
		}

		// Overflow, so we create a new root
		BTreeInternalNode newRoot = createInternalNode();
		newRoot.createRoot(rootNode, res.overflowNode);
		rootIsLeaf = false;
		rootID = newRoot.getID();
		releaseNode(newRoot);
		releaseNode(rootNode);
		releaseNode(res.overflowNode);
		setUpdateFlag();
	}

	public Object delete(Object key)
	{
		BTreeNode rootNode = getRoot();
		if(rootNode == null) return null;
		reuseableDeleteResult.updateMin = false;
		reuseableDeleteResult.updateMax = false;
		reuseableDeleteResult.oldValue = null;
		rootNode.delete(reuseableDeleteResult, key);
		if(reuseableDeleteResult.oldValue != null)
		{
			--size;
			setUpdateFlag();
			if(size == 0)
			{
				deleteNode(rootNode);
				rootID = 0;
				return reuseableDeleteResult.oldValue;
			}
			if(!rootNode.isLeaf())
			{
				BTreeInternalNode internalRoot = rootNode.asInternal();
				if(internalRoot.getArraySize() == 1)
				{
					rootID = internalRoot.getFirstChildID();
					rootIsLeaf = internalRoot.childrenAreLeafs();
					deleteNode(internalRoot);
					return reuseableDeleteResult.oldValue;
				}
			}
		}
		releaseNode(rootNode);
		return reuseableDeleteResult.oldValue;
	}

	public int size()
	{
		return size;
	}

	@Override
	public int subMapSize(	Object lowerBound,
							boolean lowerInclusive,
							Object upperBound,
							boolean upperInclusive)
	{
		BTreeNode rootNode = getRoot();
		if(rootNode == null) return 0;
		return rootNode.subTreeSize(lowerBound,
									lowerInclusive,
									upperBound,
									upperInclusive);
	}

	public boolean containsKey(Object key)
	{
		return (getKey(key) != null);
	}

	public Object getValue(Object key)
	{
		BTreeNode rootNode = getRoot();
		if(rootNode == null) return null;
		Object res = rootNode.getValue(key);
		releaseNode(rootNode);
		return res;
	}
	
	public Object getKey(Object key)
	{
		BTreeNode rootNode = getRoot();
		if(rootNode == null) return null;
		Object res = rootNode.getKey(key);
		releaseNode(rootNode);
		return res;
	}

	public boolean setCursorFirst(BTreeCursor cursor)
	{
		BTreeNode rootNode = getRoot();
		if(rootNode == null) return false;
		return rootNode.setCursorFirst(cursor);
	}

	public boolean setCursorLast(BTreeCursor cursor)
	{
		BTreeNode rootNode = getRoot();
		if(rootNode == null) return false;
		return rootNode.setCursorLast(cursor);
	}

	public boolean setCursorEqual(BTreeCursor cursor, Object key)
	{
		BTreeNode rootNode = getRoot();
		if(rootNode == null) return false;
		boolean res = rootNode.setCursorEqual(cursor, key);
		releaseNode(rootNode);
		return res;
	}

	@Override
	public boolean setCursorLessThan(	BTreeCursor cursor,
										Object key,
										boolean inclusive)
	{
		if(inclusive)
		{
			return setCursorFloor(cursor, key);
		}
		else
		{
			return setCursorLower(cursor, key);
		}
	}

	@Override
	public boolean setCursorGreaterThan(BTreeCursor cursor,
										Object key,
										boolean inclusive)
	{
		if(inclusive)
		{
			return setCursorCeiling(cursor, key);
		}
		else
		{
			return setCursorHigher(cursor, key);
		}
	}

	public boolean setCursorFloor(BTreeCursor cursor, Object key)
	{
		BTreeNode rootNode = getRoot();
		if(rootNode == null) return false;
		boolean res = rootNode.setCursorFloor(cursor, key);
		releaseNode(rootNode);
		return res;
	}

	public boolean setCursorCeiling(BTreeCursor cursor, Object key)
	{
		BTreeNode rootNode = getRoot();
		if(rootNode == null) return false;
		boolean res = rootNode.setCursorCeiling(cursor, key);
		releaseNode(rootNode);
		return res;
	}

	public boolean setCursorHigher(BTreeCursor cursor, Object key)
	{
		BTreeNode rootNode = getRoot();
		if(rootNode == null) return false;
		boolean res = rootNode.setCursorHigher(cursor, key);
		releaseNode(rootNode);
		return res;
	}

	public boolean setCursorLower(BTreeCursor cursor, Object key)
	{
		BTreeNode rootNode = getRoot();
		if(rootNode == null) return false;
		boolean res = rootNode.setCursorLower(cursor, key);
		releaseNode(rootNode);
		return res;
	}

	public Comparator<Object> getComparator()
	{
		return comparator;
	}

	// Node create and delete
	public BTreeLeafNode createLeafNode()
	{
		return getOwner().getCache().createNewLeafNode(this);
	}

	public BTreeInternalNode createInternalNode()
	{
		return getOwner().getCache().createNewInternalNode(this);
	}

	public void releaseNode(BTreeNode node)
	{
		if(node.isLeaf())
		{
			getOwner().getCache().releaseLeafNode(node.asLeaf());
		}
		else
		{
			getOwner().getCache().releaseInternalNode(node.asInternal());
		}
	}

	public void deleteNode(BTreeNode node)
	{
		if(node.isLeaf())
		{
			getOwner().getCache().deleteLeafNode(node.asLeaf());
		}
		else
		{
			getOwner().getCache().deleteInternalNode(node.asInternal());
		}
	}

	public BTreeLeafNode loadLeafNode(long nodeID)
	{
		try
		{
			return getOwner().getCache().loadLeafNode(nodeID, this);
		}
		catch(ObjectNotFoundException e)
		{
			return null;
		}
	}

	public BTreeInternalNode loadInternalNode(long nodeID)
	{
		try
		{
			return getOwner().getCache().loadInternalNode(nodeID, this);
		}
		catch(ObjectNotFoundException e)
		{
			return null;
		}
	}

	private BTreeNode getRoot()
	{
		if(rootID == 0) return null;
		if(rootIsLeaf) return loadLeafNode(rootID);
		return loadInternalNode(rootID);
	}

	@Override
	public String toString()
	{
		BTreeNode rootNode = getRoot();
		if(rootNode != null)
		{
			String res = "************************************************\n"
					+ "BTree: Size(" + size + ")\n"
					+ "************************************************\n"
					+ rootNode.toString("");
			releaseNode(rootNode);
			return res;
		}
		return "Empty tree";
	}

	@Override
	public BTreeMapImpl getActualObject()
	{
		return this;
	}

	@Override
	public void swapOut(DataOutput out) throws IOException
	{
		out.writeInt(keySerializerIndex);
		out.writeInt(valueSerializerIndex);
		out.writeInt(comparatorIndex);
		out.writeBoolean(hasValues);
		out.writeInt(size);
		out.writeLong(rootID);
		out.writeBoolean(rootIsLeaf);
	}

	@Override
	public void swapIn(DataInput in) throws IOException
	{
		keySerializerIndex = in.readInt();
		valueSerializerIndex = in.readInt();
		comparatorIndex = in.readInt();
		hasValues = in.readBoolean();
		size = in.readInt();
		rootID = in.readLong();
		rootIsLeaf = in.readBoolean();
		// TODO: resolve indexes
	}

	@Override
	public void objectCreated()
	{

	}

	@Override
	public void objectDeleted()
	{
		comparator = null;
		keySerializer = null;
		valueSerializer = null;
	}

	public static class PutResult
	{
		public BTreeNode overflowNode;
		public Object oldValue;
	}

	public static class DeleteResult
	{
		public Object oldValue; // Null if nothing was deleted
		public boolean updateMin;
		public boolean updateMax;
	}
}
