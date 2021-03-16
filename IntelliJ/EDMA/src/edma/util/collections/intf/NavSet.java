package edma.util.collections.intf;



public interface NavSet<E> extends OrderedSet<E>
{
	// Sub sets
	public NavSet<E> headSet(E toKey, boolean toInclusive);
	public NavSet<E> tailSet(E fromKey, boolean fromInclusive);
	public NavSet<E> subSet(E fromKey, boolean fromInclusive, E toKey, boolean toInclusive);
	public NavSet<E> reversedSet();
	
	//Obtain cursor
	public SetCursor<E> getNewCursor();
	
	//Initialize cursor
	public boolean setCursorFirst(SetCursor<E> cursor);
	public boolean setCursorLast(SetCursor<E> cursor);
	public boolean setCursorEqual(SetCursor<E> cursor, E key);
	public boolean setCursorLessThan(SetCursor<E> cursor, E key, boolean inclusive);
	public boolean setCursorGreaterThan(SetCursor<E> cursor, E key, boolean inclusive);
	public boolean setCursorFloor(SetCursor<E> cursor, E key);
	public boolean setCursorCeiling(SetCursor<E> cursor, E key);
	public boolean setCursorHigher(SetCursor<E> cursor, E key);
	public boolean setCursorLower(SetCursor<E> cursor, E key);
	
	public interface SetCursor<K>
	{
		public OrderedSet<K> getOrderedSet();
		public boolean moveNext();
		public boolean movePrev();
		public K getCurrent();
	}
}
