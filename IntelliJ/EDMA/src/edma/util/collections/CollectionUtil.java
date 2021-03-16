package edma.util.collections;

import java.util.Iterator;

import edma.util.collections.intf.BaseMap;
import edma.util.collections.intf.BaseSet;

public class CollectionUtil
{
	@SuppressWarnings("unchecked")
	public static final <T> BaseSet<T> emptySet()
	{
        return (BaseSet<T>) EMPTY_SET;
    }
	
	@SuppressWarnings("unchecked")
	public static final <K,V> BaseMap<K,V> emptyMap()
	{
        return (BaseMap<K,V>) EMPTY_MAP;
    }
	
	private static EmptyIterator EMPTY_ITERATOR = new EmptyIterator();
	private static EmptySet EMPTY_SET = new EmptySet();
	private static EmptyMap EMPTY_MAP = new EmptyMap();
	
	private static class EmptyIterator implements Iterator<Object>
	{
		@Override
		public boolean hasNext()
		{
			return false;
		}

		@Override
		public Object next()
		{
			throw new IllegalStateException("Next called without checking hasNext()!");
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException("This is the read only empty iterator!");
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	private static class EmptySet implements BaseSet
	{
		@Override
		public Iterator iterator()
		{
			return EMPTY_ITERATOR;
		}

		@Override
		public boolean isReadOnly()
		{
			return true;
		}

		@Override
		public boolean contains(Object item)
		{
			return false;
		}

		@Override
		public int size()
		{
			return 0;
		}
		
		@Override
		public void makeReadOnly(){}

		@Override
		public boolean add(Object item)
		{
			throw new IllegalStateException("This is the empty read only set!");
		}

		@Override
		public boolean remove(Object item)
		{
			throw new IllegalStateException("This is the empty read only set!");
		}

		@Override
		public void clear()
		{
			throw new IllegalStateException("This is the empty read only set!");
		}

		@Override
		public long getID()
		{
			return -1L;
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static class EmptyMap implements BaseMap
	{
		@Override
		public Iterator iterator()
		{
			return EMPTY_ITERATOR;
		}

		@Override
		public boolean isReadOnly()
		{
			return true;
		}

		@Override
		public boolean containsKey(Object key)
		{
			return false;
		}

		@Override
		public Object get(Object key)
		{
			return null;
		}

		@Override
		public int size()
		{
			return 0;
		}
		
		@Override
		public void makeReadOnly(){}

		@Override
		public Object put(Object key, Object value)
		{
			throw new IllegalStateException("This is the empty read only map!");
		}

		@Override
		public Object remove(Object key)
		{
			throw new IllegalStateException("This is the empty read only map!");
		}

		@Override
		public void clear()
		{
			throw new IllegalStateException("This is the empty read only map!");
		}

		@Override
		public long getID()
		{
			return -1L;
		}
		
	}
}
