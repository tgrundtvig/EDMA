package edma.runtime.implementations.common.collectionfactory.java;

import java.util.Comparator;

import edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import edma.runtime.implementations.common.collectionfactory.DMap;
import edma.runtime.implementations.common.collectionfactory.DNavigableMap;
import edma.runtime.implementations.common.collectionfactory.DNavigableSet;
import edma.runtime.implementations.common.collectionfactory.DSet;

public class JavaCollectionFactory implements CollectionFactory
{

	@Override
	public <K, V> DMap<K, V> newMap()
	{
		return new DJavaHashMap<>();
	}

	@Override
	public <E> DSet<E> newSet()
	{
		return new DJavaHashSet<>();
	}

	@Override
	public <K extends Comparable<K>, V> DNavigableMap<K, V> newNavigableMap()
	{
		return new DJavaTreeMap<>();
	}

	@Override
	public <K, V> DNavigableMap<K, V> newNavigableMap(Comparator<K> comparator)
	{
		return new DJavaTreeMap<>(comparator);
	}

	@Override
	public <E extends Comparable<E>> DNavigableSet<E> newNavigableSet()
	{
		return new DJavaTreeSet<>();
	}

	@Override
	public <E> DNavigableSet<E> newNavigableSet(Comparator<E> comparator)
	{
		return new DJavaTreeSet<>(comparator);
	}

	@Override
	public void stop()
	{
		// do nothing.
	}
}
