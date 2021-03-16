package org.abstractica.edma.util.collections.treemap;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.abstractica.edma.util.collections.intf.NavMap;
import org.abstractica.edma.util.collections.intf.ManagableObject;
import org.abstractica.edma.util.collections.intf.OrderedMap;
import org.abstractica.edma.util.collections.treemap.btreemap.basic.BTreeCursor;
import org.abstractica.edma.util.collections.treemap.btreemap.basic.BTreeMapImpl;

public class TreeMapImpl<K, V> implements NavMap<K, V>, ManagableObject
{
	private final BTreeMapImpl btree;
	private final BTreeCursor tmpCursor;
	private Bound<K> lowerBound;
	private Bound<K> upperBound;
	private boolean increasing;
	private boolean readOnly;

	public TreeMapImpl(BTreeMapImpl btree)
	{
		this(btree, null, null, true, false);
	}

	public TreeMapImpl(	BTreeMapImpl btree,
						Bound<K> lowerBound,
						Bound<K> upperBound,
						boolean increasing,
						boolean readOnly)
	{
		super();
		this.btree = btree;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.increasing = increasing;
		this.readOnly = readOnly;
		this.tmpCursor = new BTreeCursor();
	}

	public BTreeMapImpl getBTree()
	{
		return btree;
	}

	@Override
	public long getID()
	{
		return btree.getID();
	}

	@Override
	public boolean isReadOnly()
	{
		return readOnly;
	}

	@Override
	public MapCursor<K, V> getNewCursor()
	{
		return new MapCursorImpl(new BTreeCursor());
	}

	@Override
	public Iterator<Entry<K, V>> iterator()
	{
		MapCursor<K, V> c = getNewCursor();
		if(!setCursorFirst(c)) c = null;
		return new TreeMapIteratorImpl<>(c);
	}

	@Override
	public int size()
	{
		if(lowerBound == null && upperBound == null) return btree.size();
		K lowerKey = null;
		boolean lowerInclusive = false;
		K upperKey = null;
		boolean upperInclusive = false;
		if(lowerBound != null)
		{
			lowerKey = lowerBound.key;
			lowerInclusive = lowerBound.inclusive;
		}
		if(upperBound != null)
		{
			upperKey = upperBound.key;
			upperInclusive = upperBound.inclusive;
		}
		return btree.subMapSize(lowerKey,
								lowerInclusive,
								upperKey,
								upperInclusive);
	}

	@Override
	public void makeReadOnly()
	{
		readOnly = true;
	}

	@Override
	public boolean containsKey(K key)
	{
		if(!inRange(key)) return false;
		return btree.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(K key)
	{
		if(!inRange(key)) return null;
		return (V) btree.getValue(key);
	}

	@SuppressWarnings("unchecked")
	public K getKey(K key)
	{
		if(!inRange(key)) return null;
		return (K) btree.getKey(key);
	}

	@Override
	public TreeMapKeySetViewImpl<K,V> getKeySetView()
	{
		return new TreeMapKeySetViewImpl<>(this);
	}

	@Override
	public boolean setCursorFirst(MapCursor<K, V> cursor)
	{
		if(increasing) return absSetCursorFirst(cursor);
		else return absSetCursorLast(cursor);
	}

	@Override
	public boolean setCursorLast(MapCursor<K, V> cursor)
	{
		if(increasing) return absSetCursorLast(cursor);
		else return absSetCursorFirst(cursor);
	}

	@Override
	public boolean setCursorEqual(MapCursor<K, V> cursor, K key)
	{

		if(!inRange(key)) return false;
		MapCursorImpl c = asMapCursor(cursor);
		if(!btree.setCursorEqual(c.cursor, key)) return false;
		c.lowerBound = lowerBound;
		c.upperBound = upperBound;
		c.increasing = increasing;
		return true;
	}

	@Override
	public boolean setCursorLessThan(	MapCursor<K, V> cursor,
										K key,
										boolean inclusive)
	{
		if(increasing) return absSetCursorLessThan(cursor, key, inclusive);
		else return absSetCursorGreaterThan(cursor, key, inclusive);
	}

	@Override
	public boolean setCursorGreaterThan(MapCursor<K, V> cursor,
										K key,
										boolean inclusive)
	{
		if(increasing) return absSetCursorGreaterThan(cursor, key, inclusive);
		else return absSetCursorLessThan(cursor, key, inclusive);
	}

	@Override
	public boolean setCursorFloor(MapCursor<K, V> cursor, K key)
	{
		return setCursorLessThan(cursor, key, true);
	}

	@Override
	public boolean setCursorCeiling(MapCursor<K, V> cursor, K key)
	{
		return setCursorGreaterThan(cursor, key, true);
	}

	@Override
	public boolean setCursorHigher(MapCursor<K, V> cursor, K key)
	{
		return setCursorGreaterThan(cursor, key, false);
	}

	@Override
	public boolean setCursorLower(MapCursor<K, V> cursor, K key)
	{
		return setCursorLessThan(cursor, key, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Comparator<K> getComparator()
	{
		if(increasing) return (Comparator<K>) btree.getComparator();
		return new Comparator<K>()
		{
			@Override
			public int compare(K key1, K key2)
			{
				return btree.getComparator().compare(key2, key1);
			}
		};
	}

	// Sub maps
	@Override
	public TreeMapImpl<K, V> headMap(K toKey, boolean toInclusive)
	{
		if(increasing) return absHeadMap(toKey, toInclusive);
		else return absTailMap(toKey, toInclusive);
	}

	@Override
	public TreeMapImpl<K, V> tailMap(K fromKey, boolean fromInclusive)
	{
		if(increasing) return absTailMap(fromKey, fromInclusive);
		else return absHeadMap(fromKey, fromInclusive);
	}

	@Override
	public TreeMapImpl<K, V> subMap(K fromKey,
									boolean fromInclusive,
									K toKey,
									boolean toInclusive)
	{
		if(increasing) return absSubMap(fromKey,
										fromInclusive,
										toKey,
										toInclusive);
		else return absSubMap(toKey, toInclusive, fromKey, fromInclusive);
	}

	@Override
	public TreeMapImpl<K, V> reversedMap()
	{
		return new TreeMapImpl<K, V>(	btree,
										lowerBound,
										upperBound,
										!increasing,
										readOnly);
	}

	@Override
	public void clear()
	{
		if(readOnly) throw new IllegalStateException("This is a read only map");
		if(lowerBound == null && upperBound == null)
		{
			btree.clear();
		}
		else
		{
			if(absSetCursorLowerBound(tmpCursor, lowerBound))
			{
				do
				{
					btree.delete(tmpCursor.key);
				}
				while(absMoveNext(tmpCursor));
			}

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public V put(K key, V value)
	{
		if(readOnly) throw new IllegalStateException("This is a read only map");
		if(!inRange(key)) throw new IllegalArgumentException("The key: " + key
				+ " is outside the range " + rangeToString());
		return (V) btree.put(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public V remove(K key)
	{
		if(readOnly) throw new IllegalStateException("This is a read only map");
		if(!inRange(key)) throw new IllegalArgumentException("The key: " + key
				+ " is outside the range " + rangeToString());
		return (V) btree.delete(key);
	}

	// Private helper methods
	@SuppressWarnings("unchecked")
	private boolean absSetCursorFirst(MapCursor<K, V> cursor)
	{
		MapCursorImpl c = asMapCursor(cursor);
		if(lowerBound == null && upperBound == null)
		{
			if(!btree.setCursorFirst(c.cursor)) return false;
			c.lowerBound = null;
			c.upperBound = null;
			c.increasing = increasing;
			return true;
		}
		if(!absSetCursorLowerBound(tmpCursor, upperBound)) return false;
		if(!insideLowerBound((K) tmpCursor.key)) return false;
		c.lowerBound = lowerBound;
		c.upperBound = upperBound;
		c.increasing = increasing;
		c.copyFrom(tmpCursor);
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean absSetCursorLast(MapCursor<K, V> cursor)
	{
		MapCursorImpl c = asMapCursor(cursor);
		if(lowerBound == null && upperBound == null)
		{
			if(!btree.setCursorLast(c.cursor)) return false;
			c.lowerBound = null;
			c.upperBound = null;
			c.increasing = increasing;
			return true;

		}
		if(!absSetCursorUpperBound(tmpCursor, upperBound)) return false;
		if(!insideLowerBound((K) tmpCursor.key)) return false;
		c.lowerBound = lowerBound;
		c.upperBound = upperBound;
		c.increasing = increasing;
		c.copyFrom(tmpCursor);
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean absSetCursorLessThan(	MapCursor<K, V> cursor,
											K key,
											boolean inclusive)
	{
		MapCursorImpl c = asMapCursor(cursor);
		Bound<K> upper = trimUpper(key, inclusive);
		if(!btree.setCursorLessThan(tmpCursor, upper.key, upper.inclusive)) return false;
		if(!insideLowerBound((K) tmpCursor.key)) return false;
		c.lowerBound = lowerBound;
		c.upperBound = upperBound;
		c.increasing = increasing;
		c.copyFrom(tmpCursor);
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean absSetCursorGreaterThan(MapCursor<K, V> cursor,
											K key,
											boolean inclusive)
	{
		MapCursorImpl c = asMapCursor(cursor);
		Bound<K> lower = trimLower(key, inclusive);
		if(!btree.setCursorGreaterThan(tmpCursor, lower.key, lower.inclusive)) return false;
		if(!insideUpperBound((K) tmpCursor.key)) return false;
		c.lowerBound = lowerBound;
		c.upperBound = upperBound;
		c.increasing = increasing;
		c.copyFrom(tmpCursor);
		return true;
	}

	private boolean absSetCursorLowerBound(BTreeCursor cursor, Bound<K> bound)
	{
		if(bound == null) return btree.setCursorFirst(cursor);
		return btree.setCursorGreaterThan(cursor, bound.key, bound.inclusive);
	}

	private boolean absSetCursorUpperBound(BTreeCursor cursor, Bound<K> bound)
	{
		if(bound == null) return btree.setCursorLast(cursor);
		return btree.setCursorLessThan(cursor, bound.key, bound.inclusive);
	}

	private TreeMapImpl<K, V> absHeadMap(K toKey, boolean toInclusive)
	{
		if(!inRange(toKey)) throw new IllegalArgumentException("ToKey: "
				+ toKey + " is outside range: " + rangeToString());
		Bound<K> upper = trimUpper(toKey, toInclusive);
		return new TreeMapImpl<K, V>(	btree,
										lowerBound,
										upper,
										increasing,
										readOnly);
	}

	private TreeMapImpl<K, V> absTailMap(K fromKey, boolean fromInclusive)
	{
		if(!inRange(fromKey)) throw new IllegalArgumentException("FromKey: "
				+ fromKey + " is outside range: " + rangeToString());
		Bound<K> lower = trimLower(fromKey, fromInclusive);
		return new TreeMapImpl<K, V>(	btree,
										lower,
										upperBound,
										increasing,
										readOnly);
	}

	private TreeMapImpl<K, V> absSubMap(K fromKey,
										boolean fromInclusive,
										K toKey,
										boolean toInclusive)
	{
		if(!inRange(fromKey)) throw new IllegalStateException("FromKey: "
				+ fromKey + " is outside range: " + rangeToString());
		if(!inRange(toKey)) throw new IllegalStateException("ToKey: " + toKey
				+ " is outside range: " + rangeToString());
		int c = btree.getComparator().compare(fromKey, toKey);
		if(c > 0) throw new IllegalArgumentException("FromKey: " + fromKey
				+ " is larger than toKey: " + toKey
				+ " under the original comparator.");
		Bound<K> lower = trimLower(fromKey, fromInclusive);
		Bound<K> upper = trimUpper(toKey, toInclusive);
		return new TreeMapImpl<K, V>(btree, lower, upper, increasing, readOnly);
	}

	private boolean inRange(K key)
	{
		return (insideLowerBound(key) && insideUpperBound(key));
	}

	private boolean insideLowerBound(K key)
	{
		if(lowerBound == null) return true;
		int c = btree.getComparator().compare(key, lowerBound.key);
		return lowerBound.inclusive ? (c >= 0) : (c > 0);
	}

	private boolean insideUpperBound(K key)
	{
		if(upperBound == null) return true;
		int c = btree.getComparator().compare(key, upperBound.key);
		return upperBound.inclusive ? (c <= 0) : (c < 0);
	}

	private Bound<K> trimLower(K key, boolean inclusive)
	{
		if(lowerBound == null) return new Bound<>(key, inclusive);
		int c = btree.getComparator().compare(key, lowerBound.key);
		if(c < 0) return lowerBound;
		if(c == 0)
		{
			if(!lowerBound.inclusive || inclusive) return lowerBound;
			return new Bound<>(key, false);
		}
		return new Bound<>(key, inclusive);
	}

	private Bound<K> trimUpper(K key, boolean inclusive)
	{
		if(upperBound == null) return new Bound<>(key, inclusive);
		int c = btree.getComparator().compare(key, upperBound.key);
		if(c > 0) return upperBound;
		if(c == 0)
		{
			if(!upperBound.inclusive || inclusive) return upperBound;
			return new Bound<>(key, false);
		}
		return new Bound<>(key, inclusive);
	}

	private boolean absMoveNext(BTreeCursor c)
	{
		if(upperBound == null) return c.moveNext();
		return c.moveNext(upperBound.key, upperBound.inclusive);
	}

	private String rangeToString()
	{
		StringBuilder res = new StringBuilder();
		if(lowerBound == null) res.append("]-inf;");
		else res.append((lowerBound.inclusive ? "[" : "]") + lowerBound.key
				+ ";");
		if(upperBound == null) res.append("inf[");
		else res.append(upperBound.key + (upperBound.inclusive ? "]" : "["));
		return res.toString();
	}

	private MapCursorImpl asMapCursor(MapCursor<K, V> cursor)
	{
		if(cursor.getOrderedMap() != this) throw new IllegalArgumentException("The cursor is not owned by this map!");
		return (MapCursorImpl) cursor;
	}

	// Private helper classes...
	/*
	 * private static class Bound<K> { public final K key; public final boolean
	 * inclusive;
	 * 
	 * public Bound(K key, boolean inclusive) { super(); this.key = key;
	 * this.inclusive = inclusive; } }
	 */
	private class MapCursorImpl implements MapCursor<K, V>
	{
		private final BTreeCursor cursor;
		private Bound<K> lowerBound;
		private Bound<K> upperBound;
		private boolean increasing;

		public MapCursorImpl(BTreeCursor cursor)
		{
			this.cursor = cursor;
			this.lowerBound = null;
			this.upperBound = null;
			this.increasing = true;
		}

		public void copyFrom(BTreeCursor c)
		{
			cursor.clone(c);
		}

		@Override
		public OrderedMap<K, V> getOrderedMap()
		{
			return TreeMapImpl.this;
		}

		public boolean moveNext()
		{
			if(increasing) return absMoveNext();
			else return absMovePrev();
		}

		public boolean movePrev()
		{
			if(increasing) return absMovePrev();
			else return absMoveNext();
		}

		@SuppressWarnings("unchecked")
		public K getCurrentKey()
		{
			return (K) cursor.getCurrentKey();
		}

		@SuppressWarnings("unchecked")
		public V getCurrentValue()
		{
			return (V) cursor.getCurrentValue();
		}

		private boolean absMoveNext()
		{
			if(upperBound == null) return cursor.moveNext();
			return cursor.moveNext(upperBound.key, upperBound.inclusive);
		}

		private boolean absMovePrev()
		{
			if(lowerBound == null) return cursor.movePrev();
			return cursor.movePrev(lowerBound.key, lowerBound.inclusive);
		}
	}

	private static class TreeMapIteratorImpl<K, V> implements Iterator<Entry<K, V>>
	{
		private MapCursor<K, V> cursor;
		private TreeMapEntry<K, V> next;

		public TreeMapIteratorImpl(MapCursor<K, V> cursor)
		{
			super();
			this.cursor = cursor;
			if(cursor == null)
			{
				next = null;
			}
			else
			{
				next = new TreeMapEntry<>();
				next.init(cursor);
			}
		}

		@Override
		public boolean hasNext()
		{
			return (next != null);
		}

		@Override
		public Entry<K, V> next()
		{
			Entry<K, V> res = next;
			if(cursor.moveNext())
			{
				next = new TreeMapEntry<>();
				next.init(cursor);
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

		private static class TreeMapEntry<K, V> implements Entry<K, V>
		{
			private K key;
			private V value;

			public TreeMapEntry()
			{
				this.key = null;
				this.value = null;
			}

			protected void init(MapCursor<K, V> cursor)
			{
				this.key = cursor.getCurrentKey();
				this.value = cursor.getCurrentValue();
			}

			@Override
			public K getKey()
			{
				return key;
			}

			@Override
			public V getValue()
			{
				return value;
			}

			@Override
			public V setValue(V value)
			{
				throw new UnsupportedOperationException();
			}

			@SuppressWarnings("unchecked")
			public boolean equals(Object o)
			{
				if(!(o instanceof Map.Entry)) return false;
				Map.Entry<K, V> e = (Map.Entry<K, V>) o;
				return eq(key, e.getKey()) && eq(value, e.getValue());
			}

			public int hashCode()
			{
				return (key == null ? 0 : key.hashCode())
						^ (value == null ? 0 : value.hashCode());
			}

			public String toString()
			{
				return "(" + key + ", " + value + ")";
			}

			private static boolean eq(Object o1, Object o2)
			{
				return o1 == null ? o2 == null : o1.equals(o2);
			}
		}
	}
}
