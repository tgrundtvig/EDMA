package edma.runtime.implementations.common.collectionfactory;

import java.util.Comparator;

public interface CollectionFactory
{
	public <K,V> DMap<K,V> newMap();
	public <E> DSet<E> newSet();
	public <K extends Comparable<K>,V> DNavigableMap<K,V> newNavigableMap();
	public <K,V> DNavigableMap<K,V> newNavigableMap(Comparator<K> comparator);
	public <E extends Comparable<E>> DNavigableSet<E> newNavigableSet();
	public <E> DNavigableSet<E> newNavigableSet(Comparator<E> comparator);
	public void stop();
}
