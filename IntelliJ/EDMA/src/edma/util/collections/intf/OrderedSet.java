package edma.util.collections.intf;

import java.util.Comparator;

public interface OrderedSet<E> extends BaseSet<E>
{
	public Comparator<E> getComparator();
	
	//Sub sets
	public OrderedSet<E> headSet(E toKey, boolean toInclusive);
	public OrderedSet<E> tailSet(E fromKey, boolean fromInclusive);
	public OrderedSet<E> subSet(E fromKey, boolean fromInclusive, E toKey, boolean toInclusive);
	public OrderedSet<E> reversedSet();
}
