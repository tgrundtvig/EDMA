package org.abstractica.edma.util.collections.treemap;

import java.util.Comparator;
import java.util.Iterator;

import org.abstractica.edma.util.collections.intf.NavSet;
import org.abstractica.edma.util.collections.intf.OrderedSet;
import org.abstractica.edma.util.collections.intf.NavMap;

public class TreeMapKeySetViewImpl<K, V> implements NavSet<K>
{
	private TreeMapImpl<K, V> treeMap;

	public TreeMapKeySetViewImpl(TreeMapImpl<K, V> treeMap)
	{
		super();
		this.treeMap = treeMap;
	}
	
	@Override
	public boolean isReadOnly()
	{
		return false;
	}

	@Override
	public SetCursor<K> getNewCursor()
	{
		return new SetCursorImpl(treeMap.getNewCursor());
	}
	
	@Override
	public Iterator<K> iterator()
	{
		SetCursor<K> c = getNewCursor();
		if(!setCursorFirst(c)) c = null;
		return new SetIteratorImpl<>(c);
	}

	@Override
	public int size()
	{
		return treeMap.size();
	}
	
	@Override
	public void makeReadOnly()
	{
		treeMap.makeReadOnly();
	}

	@Override
	public boolean contains(K key)
	{
		return treeMap.containsKey(key);
	}
	
	public K get(K key)
	{
		return treeMap.getKey(key);
	}

	@Override
	public boolean setCursorFirst(SetCursor<K> cursor)
	{
		NavMap.MapCursor<K,V> c = asOrderedMapCursor(cursor);
		return treeMap.setCursorFirst(c);
	}

	@Override
	public boolean setCursorLast(SetCursor<K> cursor)
	{
		NavMap.MapCursor<K,V> c = asOrderedMapCursor(cursor);
		return treeMap.setCursorLast(c);
	}

	@Override
	public boolean setCursorEqual(SetCursor<K> cursor, K key)
	{
		NavMap.MapCursor<K,V> c = asOrderedMapCursor(cursor);
		return treeMap.setCursorEqual(c, key);
	}

	@Override
	public boolean setCursorLessThan(	SetCursor<K> cursor,
										K key,
										boolean inclusive)
	{
		NavMap.MapCursor<K,V> c = asOrderedMapCursor(cursor);
		return treeMap.setCursorLessThan(c, key, inclusive);
	}

	@Override
	public boolean setCursorGreaterThan(SetCursor<K> cursor,
										K key,
										boolean inclusive)
	{
		NavMap.MapCursor<K,V> c = asOrderedMapCursor(cursor);
		return treeMap.setCursorGreaterThan(c, key, inclusive);
	}

	@Override
	public boolean setCursorFloor(SetCursor<K> cursor, K key)
	{
		NavMap.MapCursor<K,V> c = asOrderedMapCursor(cursor);
		return treeMap.setCursorFloor(c, key);
	}

	@Override
	public boolean setCursorCeiling(SetCursor<K> cursor, K key)
	{
		NavMap.MapCursor<K,V> c = asOrderedMapCursor(cursor);
		return treeMap.setCursorCeiling(c, key);
	}

	@Override
	public boolean setCursorHigher(SetCursor<K> cursor, K key)
	{
		NavMap.MapCursor<K,V> c = asOrderedMapCursor(cursor);
		return treeMap.setCursorHigher(c, key);
	}

	@Override
	public boolean setCursorLower(SetCursor<K> cursor, K key)
	{
		NavMap.MapCursor<K,V> c = asOrderedMapCursor(cursor);
		return treeMap.setCursorLower(c, key);
	}

	@Override
	public Comparator<K> getComparator()
	{
		return (Comparator<K>) treeMap.getComparator();
	}
	
	@Override
	public TreeMapKeySetViewImpl<K,V> headSet(K fromKey, boolean fromInclusive)
	{
		return new TreeMapKeySetViewImpl<>(treeMap.headMap(fromKey, fromInclusive));
	}

	@Override
	public TreeMapKeySetViewImpl<K,V> tailSet(K toKey, boolean toInclusive)
	{
		return new TreeMapKeySetViewImpl<>(treeMap.tailMap(toKey, toInclusive));
	}

	@Override
	public TreeMapKeySetViewImpl<K,V> subSet(K fromKey,
								boolean fromInclusive,
								K toKey,
								boolean toInclusive)
	{
		return new TreeMapKeySetViewImpl<>(treeMap.subMap(fromKey, fromInclusive, toKey, toInclusive));
	}

	@Override
	public TreeMapKeySetViewImpl<K,V> reversedSet()
	{
		return new TreeMapKeySetViewImpl<>(treeMap.reversedMap());
	}

	@Override
	public void clear()
	{
		throw new IllegalStateException("This is a readOnly view of the keys in the underlying map!");
	}

	@Override
	public boolean add(K key)
	{
		throw new IllegalStateException("This is a readOnly view of the keys in the underlying map!");
	}

	@Override
	public boolean remove(K key)
	{
		throw new IllegalStateException("This is a readOnly view of the keys in the underlying map!");
	}
	
	public K add(K item, boolean replace)
	{
		throw new IllegalStateException("This is a readOnly view of the keys in the underlying map!");
	}

	public K remove(K item, boolean onlyIfSameInstance)
	{
		throw new IllegalStateException("This is a readOnly view of the keys in the underlying map!");
	}
	
	@SuppressWarnings("unchecked")
	private NavMap.MapCursor<K,V> asOrderedMapCursor(SetCursor<K> cursor)
	{
		if(cursor.getOrderedSet() != this) throw new IllegalArgumentException("The cursor is not owned by this set!");
		return (NavMap.MapCursor<K,V>) ((SetCursorImpl) cursor).cursor;
	}
	
	private class SetCursorImpl implements SetCursor<K>
	{
		protected NavMap.MapCursor<K,?> cursor;
		
		public SetCursorImpl(NavMap.MapCursor<K,?> cursor)
		{
			this.cursor = cursor;
		}

		@Override
		public boolean moveNext()
		{
			return cursor.moveNext();
		}

		@Override
		public boolean movePrev()
		{
			return cursor.movePrev();
		}

		@Override
		public K getCurrent()
		{
			return cursor.getCurrentKey();
		}

		@Override
		public OrderedSet<K> getOrderedSet()
		{
			return TreeMapKeySetViewImpl.this;
		}
	}
	
	private static class SetIteratorImpl<K> implements Iterator<K>
	{
		private SetCursor<K> cursor;
		private K next;

		public SetIteratorImpl(SetCursor<K> cursor)
		{
			super();
			this.cursor = cursor;
			if(cursor == null)
			{
				next = null;
			}
			else
			{
				next = cursor.getCurrent();
			}
		}

		@Override
		public boolean hasNext()
		{
			return (next != null);
		}

		@Override
		public K next()
		{
			K res = next;
			if(cursor.moveNext())
			{
				next = cursor.getCurrent();
			}
			else
			{
				next = null;
			}
			return res;
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public long getID()
	{
		return treeMap.getID();
	}
}
