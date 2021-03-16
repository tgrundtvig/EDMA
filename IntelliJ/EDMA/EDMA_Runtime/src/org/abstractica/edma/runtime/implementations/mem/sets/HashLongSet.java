package org.abstractica.edma.runtime.implementations.mem.sets;

import java.util.Iterator;

public class HashLongSet implements ILongSet
{
	private static final int minTableSize = 8;
	private static final float minLoadFactor = 0.3f;
	private static final float maxLoadFactor = 0.9f;
	private static final float preferredLoadFactor = 0.75f;
	private Entry[] table;
	private int size;
	
	public HashLongSet()
	{
		table = new Entry[minTableSize];
		size = 0;
	}
	
	public HashLongSet(ILongSet set)
	{
		size = set.size();
		int tableSize = (int) ((float) size / (float) preferredLoadFactor);
		if(tableSize < minTableSize) tableSize = minTableSize;
		table = new Entry[tableSize];
		for(Long l : set)
		{
			int index = hash(l) % table.length;
			long val = l.longValue();
			Entry e = new Entry(val, table[index]);
			table[index] = e;
		}
	}

	@Override
	public void add(Long l)
	{
		int index = hash(l) % table.length;
		long val = l.longValue();
		Entry cur = table[index];
		while(cur != null)
		{
			if(cur.value == val) return;
			cur = cur.next;
		}
		Entry e = new Entry(val, table[index]);
		table[index] = e;
		++size;
		doubleIfNecessary();
	}

	@Override
	public void remove(Long l)
	{
		int index = hash(l) % table.length;
		long val = l.longValue();
		Entry cur = table[index];
		Entry prev = null;
		while(cur != null)
		{
			if(cur.value == val)
			{
				if(prev == null)
				{
					table[index] = cur.next;
				}
				else
				{
					prev.next = cur.next;
				}
				--size;
				halfIfNecessary();
				return;
			}
			prev = cur;
			cur = cur.next;
		}
	}

	@Override
	public boolean contains(Long l)
	{
		int index = hash(l) % table.length;
		long val = l.longValue();
		Entry cur = table[index];
		while(cur != null)
		{
			if(cur.value == val){ return true; }
			cur = cur.next;
		}
		return false;
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public Iterator<Long> iterator()
	{
		return new LongIterator();
	}

	private void halfIfNecessary()
	{
		float loadFactor = ((float) size) / table.length;
		if(loadFactor < minLoadFactor && table.length > minTableSize)
		{
			resize(table.length / 2);
		}
	}

	private void doubleIfNecessary()
	{
		float loadFactor = ((float) size) / table.length;
		if(loadFactor > maxLoadFactor)
		{
			resize(table.length * 2);
		}
	}

	private void resize(int newSize)
	{
		Entry[] newTable = new Entry[newSize];
		for(int i = 0; i < table.length; ++i)
		{
			Entry e = table[i];
			while(e != null)
			{
				int newIndex = hash(e.value) % newTable.length;
				Entry next = e.next;
				e.next = newTable[newIndex];
				newTable[newIndex] = e;
				e = next;
			}
		}
		table = newTable;
	}

	private int hash(Long l)
	{
		return Math.abs(l.hashCode());
	}

	private static class Entry
	{
		public long value;
		public Entry next;

		public Entry(long value, Entry next)
		{
			this.value = value;
			this.next = next;
		}
	}
	
	private class LongIterator implements Iterator<Long>
	{
		private int curIndex;
		private Entry next;

		public LongIterator()
		{
			curIndex = 0;
			next = null;
			findNext();
		}

		private void findNext()
		{
			if(next != null) next = next.next;
			while(next == null)
			{
				next = table[curIndex++];
				if(curIndex >= table.length) return;
			}
		}

		@Override
		public boolean hasNext()
		{
			return next != null;
		}

		@Override
		public Long next()
		{
			Long res = next.value;
			findNext();
			return res;
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}

	}
}
