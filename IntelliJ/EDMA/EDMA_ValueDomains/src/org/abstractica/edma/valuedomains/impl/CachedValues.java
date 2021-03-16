package org.abstractica.edma.valuedomains.impl;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import org.abstractica.edma.valuedomains.IMetaValueDomain;

public class CachedValues
{
	private static final int minTableSize = 8;
	private static final float minLoadFactor = 0.3f;
	private static final float maxLoadFactor = 0.9f;
	private IMetaValueDomain valueDomain;
	private ReferenceQueue<Object> queue;
	private Entry[] table;
	private int size;
	
	public CachedValues(IMetaValueDomain valueDomain)
	{
		this.valueDomain = valueDomain;
		table = new Entry[minTableSize];
		queue = new ReferenceQueue<Object>();
		size = 0;
	}
	
	public synchronized Object get(Object value)
	{
		processQueue();
		resizeIfNecessary();
		int hash = Math.abs(valueDomain.valueHashCode(value));
		int index = getIndex(hash);
		Entry head = table[index];
		if(head != null)
		{
			return head.add(value, hash);
		}
		table[index] = new Entry(value, null, hash);
		++size;
		return value;
	}
	
	public synchronized int size()
	{
		processQueue();
		resizeIfNecessary();
		return size;
	}
	
	private int getIndex(int hash)
	{
		return hash % table.length;
	}
	
	private class Entry extends WeakReference<Object>
	{
		private Entry next;
		private int hash;
		
		private Entry(Object value, Entry next, int hash)
		{
			super(value, queue);
			this.next = next;
			this.hash = hash;
		}
		
		private Entry removeEntry(Entry toBeRemoved)
		{
			if(this == toBeRemoved)
			{
				return next;
			}
			if(next == null) throw new RuntimeException("Should not happen!");
			next = next.removeEntry(toBeRemoved);
			return this;
		}
		
		private Object add(Object value2, int hash)
		{
			Object value1 = get();
			if(value1 != null && valueDomain.valueEqual(value1, value2)) return value1;
			if(next != null) return next.add(value2, hash);
			next = new Entry(value2, null, hash);
			++size;
			return value2;
		}
	}
	
	private void processQueue()
	{
		Entry entry = (Entry) queue.poll();
		while(entry != null)
		{
			int index = getIndex(entry.hash);
			table[index] = table[index].removeEntry(entry);
			--size;
			entry = (Entry) queue.poll();
		}
	}
	
	private void resizeIfNecessary()
	{
		float loadFactor = ((float) size) / table.length;
		if(loadFactor > maxLoadFactor)
		{
			resize(table.length*2);
		}
		if(loadFactor < minLoadFactor && table.length > minTableSize)
		{
			int newSize = table.length;
			do
			{
				newSize /= 2;
				loadFactor *= 2;
			} while(loadFactor < minLoadFactor && newSize > minTableSize);
			resize(newSize);
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
				int newIndex = e.hash % newTable.length;
				Entry next = e.next;
				e.next = newTable[newIndex];
				newTable[newIndex] = e;
				e = next;
			}
		}
		table = newTable;
	}
}
