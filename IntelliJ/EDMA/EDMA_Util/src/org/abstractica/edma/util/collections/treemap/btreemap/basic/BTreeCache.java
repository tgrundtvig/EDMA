package org.abstractica.edma.util.collections.treemap.btreemap.basic;

import org.abstractica.edma.util.cache.AbstractObjectCache;
import org.abstractica.edma.util.cache.DataStore;
import org.abstractica.edma.util.cache.ObjectNotFoundException;

public class BTreeCache extends AbstractObjectCache<BTreeMapImpl, BTreeManager>
{
	private final LeafNodeCache leafCache;
	private final InternalNodeCache internalCache;

	public BTreeCache(	DataStore dataStore,
						int minTrees,
						int minInternals,
						int minLeafs,
						int internalSize,
						int leafSize)
	{
		super(dataStore);
		internalCache = new InternalNodeCache(	dataStore,
												minInternals,
												internalSize);
		leafCache = new LeafNodeCache(dataStore, minLeafs, leafSize);
		createInitialObjects(minTrees);
	}

	@Override
	public BTreeMapImpl newObject()
	{
		return new BTreeMapImpl();
	}

	// Leaf nodes...
	public BTreeLeafNode createNewLeafNode(BTreeMapImpl owner)
	{
		return leafCache.createNewObject(owner);
	}

	public BTreeLeafNode loadLeafNode(long id, BTreeMapImpl owner) throws ObjectNotFoundException
	{
		return leafCache.fetch(id, owner);
	}

	public void releaseLeafNode(BTreeLeafNode node)
	{
		leafCache.release(node);
	}

	public void deleteLeafNode(BTreeLeafNode node)
	{
		leafCache.delete(node);
	}

	public int getLeafNodeMergeSize()
	{
		return calculateMergeSize(leafCache.getNodeSize());
	}

	// Internal nodes...
	public BTreeInternalNode createNewInternalNode(BTreeMapImpl owner)
	{
		return internalCache.createNewObject(owner);
	}

	public BTreeInternalNode loadInternalNode(long id, BTreeMapImpl owner) throws ObjectNotFoundException
	{
		return internalCache.fetch(id, owner);
	}

	public void releaseInternalNode(BTreeInternalNode node)
	{
		internalCache.release(node);
	}

	public void deleteInternalNode(BTreeInternalNode node)
	{
		internalCache.delete(node);
	}

	public int getInternalNodeMergeSize()
	{
		return calculateMergeSize(internalCache.getNodeSize());
	}

	private int calculateMergeSize(int size)
	{
		return (size / 4) * 3;
	}

	private static class InternalNodeCache
											extends
											AbstractObjectCache<BTreeInternalNode, BTreeMapImpl>
	{
		private int nodeSize;

		public InternalNodeCache(	DataStore dataStore,
									int minObjects,
									int nodeSize)
		{
			super(dataStore);
			this.nodeSize = nodeSize;
			createInitialObjects(minObjects);
		}

		public int getNodeSize()
		{
			return nodeSize;
		}

		@Override
		public BTreeInternalNode newObject()
		{
			return new BTreeInternalNode(nodeSize);
		}
	}

	private static class LeafNodeCache
										extends
										AbstractObjectCache<BTreeLeafNode, BTreeMapImpl>
	{
		private int nodeSize;

		public LeafNodeCache(DataStore dataStore, int minObjects, int nodeSize)
		{
			super(dataStore);
			this.nodeSize = nodeSize;
			createInitialObjects(minObjects);
		}

		public int getNodeSize()
		{
			return nodeSize;
		}

		@Override
		public BTreeLeafNode newObject()
		{
			return new BTreeLeafNode(nodeSize);
		}
	}

}
