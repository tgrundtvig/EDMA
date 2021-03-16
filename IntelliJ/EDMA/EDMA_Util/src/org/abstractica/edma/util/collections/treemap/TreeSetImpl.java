package org.abstractica.edma.util.collections.treemap;

import java.util.Comparator;
import java.util.Iterator;

import org.abstractica.edma.util.collections.intf.NavSet;
import org.abstractica.edma.util.collections.intf.ManagableObject;
import org.abstractica.edma.util.collections.intf.OrderedSet;
import org.abstractica.edma.util.collections.treemap.btreemap.basic.BTreeCursor;
import org.abstractica.edma.util.collections.treemap.btreemap.basic.BTreeMapImpl;

public class TreeSetImpl<K> implements NavSet<K>, ManagableObject
{
	private final BTreeMapImpl btree;
	private final BTreeCursor tmpCursor;
	private Bound<K> lowerBound;
	private Bound<K> upperBound;
	private boolean increasing;
	private boolean readOnly;

	public TreeSetImpl(BTreeMapImpl btree)
	{
		this(btree, null, null, true, false);
	}

	public TreeSetImpl(	BTreeMapImpl btree,
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
	public SetCursor<K> getNewCursor()
	{
		return new SetCursorImpl(new BTreeCursor());
	}

	@Override
	public Iterator<K> iterator()
	{
		SetCursor<K> c = getNewCursor();
		if(!setCursorFirst(c)) c = null;
		return new TreeSetIteratorImpl<>(c);
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
	public boolean contains(K key)
	{
		if(!inRange(key)) return false;
		return btree.containsKey(key);
	}
	
	@Override
	public boolean setCursorFirst(SetCursor<K> cursor)
	{
		if(increasing) return absSetCursorFirst(cursor);
		else return absSetCursorLast(cursor);
	}

	@Override
	public boolean setCursorLast(SetCursor<K> cursor)
	{
		if(increasing) return absSetCursorLast(cursor);
		else return absSetCursorFirst(cursor);
	}

	@Override
	public boolean setCursorEqual(SetCursor<K> cursor, K key)
	{

		if(!inRange(key)) return false;
		SetCursorImpl c = asSetCursor(cursor);
		if(!btree.setCursorEqual(c.cursor, key)) return false;
		c.lowerBound = lowerBound;
		c.upperBound = upperBound;
		c.increasing = increasing;
		return true;
	}

	@Override
	public boolean setCursorLessThan(	SetCursor<K> cursor,
										K key,
										boolean inclusive)
	{
		if(increasing) return absSetCursorLessThan(cursor, key, inclusive);
		else return absSetCursorGreaterThan(cursor, key, inclusive);
	}

	@Override
	public boolean setCursorGreaterThan(SetCursor<K> cursor,
										K key,
										boolean inclusive)
	{
		if(increasing) return absSetCursorGreaterThan(cursor, key, inclusive);
		else return absSetCursorLessThan(cursor, key, inclusive);
	}

	@Override
	public boolean setCursorFloor(SetCursor<K> cursor, K key)
	{
		return setCursorLessThan(cursor, key, true);
	}

	@Override
	public boolean setCursorCeiling(SetCursor<K> cursor, K key)
	{
		return setCursorGreaterThan(cursor, key, true);
	}

	@Override
	public boolean setCursorHigher(SetCursor<K> cursor, K key)
	{
		return setCursorGreaterThan(cursor, key, false);
	}

	@Override
	public boolean setCursorLower(SetCursor<K> cursor, K key)
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
	public TreeSetImpl<K> headSet(K toKey, boolean toInclusive)
	{
		if(increasing) return absHeadSet(toKey, toInclusive);
		else return absTailSet(toKey, toInclusive);
	}

	@Override
	public TreeSetImpl<K> tailSet(K fromKey, boolean fromInclusive)
	{
		if(increasing) return absTailSet(fromKey, fromInclusive);
		else return absHeadSet(fromKey, fromInclusive);
	}

	@Override
	public TreeSetImpl<K> subSet(	K fromKey,
									boolean fromInclusive,
									K toKey,
									boolean toInclusive)
	{
		if(increasing) return absSubSet(fromKey, fromInclusive, toKey, toInclusive);
		else return absSubSet(toKey, toInclusive, fromKey, fromInclusive);
	}

	@Override
	public TreeSetImpl<K> reversedSet()
	{
		return new TreeSetImpl<K>(btree, lowerBound, upperBound, !increasing, readOnly);
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

	@Override
	public boolean add(K key)
	{
		if(readOnly) throw new IllegalStateException("This is a read only set");
		if(!inRange(key)) throw new IllegalArgumentException("The key: " + key
				+ " is outside the range " + rangeToString());
		return (btree.put(key, null) == null);
	}

	@Override
	public boolean remove(K key)
	{
		if(readOnly) throw new IllegalStateException("This is a read only set");
		if(!inRange(key)) throw new IllegalArgumentException("The key: " + key
				+ " is outside the range " + rangeToString());
		return (btree.delete(key) != null);
	}

	// Private helper methods

	@SuppressWarnings("unchecked")
	private boolean absSetCursorFirst(SetCursor<K> cursor)
	{
		SetCursorImpl c = asSetCursor(cursor);
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
	private boolean absSetCursorLast(SetCursor<K> cursor)
	{
		SetCursorImpl c = asSetCursor(cursor);
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
	private boolean absSetCursorLessThan(	SetCursor<K> cursor,
											K key,
											boolean inclusive)
	{
		SetCursorImpl c = asSetCursor(cursor);
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
	private boolean absSetCursorGreaterThan(SetCursor<K> cursor,
											K key,
											boolean inclusive)
	{
		SetCursorImpl c = asSetCursor(cursor);
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
	
	private TreeSetImpl<K> absHeadSet(K toKey, boolean toInclusive)
	{
		if(!inRange(toKey)) throw new IllegalArgumentException("ToKey: "
				+ toKey + " is outside range: " + rangeToString());
		Bound<K> upper = trimUpper(toKey, toInclusive);
		return new TreeSetImpl<K>(btree, lowerBound, upper, increasing, readOnly);
	}
	
	private TreeSetImpl<K> absTailSet(K fromKey, boolean fromInclusive)
	{
		if(!inRange(fromKey)) throw new IllegalArgumentException("FromKey: "
				+ fromKey + " is outside range: " + rangeToString());
		Bound<K> lower = trimLower(fromKey, fromInclusive);
		return new TreeSetImpl<K>(btree, lower, upperBound, increasing, readOnly);
	}
	
	private TreeSetImpl<K> absSubSet(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive)
	{	
		if(!inRange(fromKey)) throw new IllegalStateException("FromKey: "
				+ fromKey + " is outside range: " + rangeToString());
		if(!inRange(toKey)) throw new IllegalStateException("ToKey: "
				+ toKey + " is outside range: " + rangeToString());
		int c = btree.getComparator().compare(fromKey, toKey);
		if(c > 0) throw new IllegalArgumentException("FromKey: " + fromKey + " is larger than toKey: " + toKey + " under the original comparator.");
		Bound<K> lower = trimLower(fromKey, fromInclusive);
		Bound<K> upper = trimUpper(toKey, toInclusive);
		return new TreeSetImpl<K>(btree, lower, upper, increasing, readOnly);
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

	private SetCursorImpl asSetCursor(SetCursor<K> cursor)
	{
		if(cursor.getOrderedSet() != this) throw new IllegalArgumentException("The cursor is not owned by this set!");
		return (SetCursorImpl) cursor;
	}

	// Private helper classes...
	private class SetCursorImpl implements SetCursor<K>
	{
		private final BTreeCursor cursor;
		private Bound<K> lowerBound;
		private Bound<K> upperBound;
		private boolean increasing;

		public SetCursorImpl(BTreeCursor cursor)
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
		public OrderedSet<K> getOrderedSet()
		{
			return TreeSetImpl.this;
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
		public K getCurrent()
		{
			return (K) cursor.getCurrentKey();
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

	private static class TreeSetIteratorImpl<K> implements Iterator<K>
	{
		private SetCursor<K> cursor;
		private K next;

		public TreeSetIteratorImpl(SetCursor<K> cursor)
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
}