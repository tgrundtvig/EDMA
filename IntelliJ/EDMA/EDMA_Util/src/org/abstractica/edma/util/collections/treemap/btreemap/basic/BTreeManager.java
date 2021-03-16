package org.abstractica.edma.util.collections.treemap.btreemap.basic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.abstractica.edma.util.cache.ObjectNotFoundException;
import org.abstractica.edma.util.serialize.Serializer;

public class BTreeManager
{
	private final BTreeCache treeCache;
	private final ArrayList<Comparator<Object>> comparatorList;
	private final HashMap<Comparator<Object>, Integer> comparatorMap;
	private final ArrayList<Serializer<Object>> keySerializerList;
	private final HashMap<Serializer<Object>, Integer> keySerializerMap;
	private final ArrayList<Serializer<Object>> valueSerializerList;
	private final HashMap<Serializer<Object>, Integer> valueSerializerMap;

	public BTreeManager(BTreeCache treeCache)
	{
		super();
		this.treeCache = treeCache;
		this.comparatorList = new ArrayList<>();
		this.comparatorMap = new HashMap<>();
		this.keySerializerList = new ArrayList<>();
		this.keySerializerMap = new HashMap<>();
		this.valueSerializerList = new ArrayList<>();
		this.valueSerializerMap = new HashMap<>();
	}

	public BTreeMapImpl createNewTreeMap(	boolean hasValues,
											Comparator<Object> comparator,
											Serializer<Object> keySerializer,
											Serializer<Object> valueSerializer)
	{
		if(comparator == null) comparator = new NaturalComparator();
		BTreeMapImpl res = treeCache.createNewObject(this);
		res.init(	hasValues,
					comparator,
					addComparator(comparator),
					keySerializer,
					addKeySerializer(keySerializer),
					valueSerializer,
					addValueSerializer(valueSerializer));
		return res;
	}

	public BTreeMapImpl loadTreeMap(long id) throws ObjectNotFoundException
	{
		return treeCache.fetch(id, this);
	}

	public void releaseTreeMap(BTreeMapImpl btree)
	{
		treeCache.release(btree);
	}

	public void deleteTreeMap(BTreeMapImpl btree)
	{
		treeCache.delete(btree);
	}

	public BTreeCache getCache()
	{
		return treeCache;
	}

	// Used by BTreeMapImpl
	public Comparator<Object> getComparator(int index)
	{
		return comparatorList.get(index);
	}

	public Serializer<Object> getKeySerializer(int index)
	{
		return keySerializerList.get(index);
	}

	public Serializer<Object> getValueSerializer(int index)
	{
		return valueSerializerList.get(index);
	}

	private int addComparator(Comparator<Object> comparator)
	{
		Integer res = comparatorMap.get(comparator);
		if(res != null) return res;
		res = comparatorList.size();
		comparatorList.add(comparator);
		comparatorMap.put(comparator, res);
		return res;
	}

	private int addKeySerializer(Serializer<Object> keySerializer)
	{
		Integer res = keySerializerMap.get(keySerializer);
		if(res != null) return res;
		res = keySerializerList.size();
		keySerializerList.add(keySerializer);
		keySerializerMap.put(keySerializer, res);
		return res;
	}

	private int addValueSerializer(Serializer<Object> valueSerializer)
	{
		Integer res = valueSerializerMap.get(valueSerializer);
		if(res != null) return res;
		res = valueSerializerList.size();
		valueSerializerList.add(valueSerializer);
		valueSerializerMap.put(valueSerializer, res);
		return res;
	}

	private static class NaturalComparator implements Comparator<Object>
	{
		@SuppressWarnings("unchecked")
		@Override
		public int compare(Object k1, Object k2)
		{
			return ((Comparable<Object>) k1).compareTo(k2);
		}

		@Override
		public boolean equals(Object o)
		{
			if(!(o instanceof NaturalComparator)) return false;
			return true;
		}

		public int hashCode()
		{
			return getClass().hashCode();
		}

	}
}
