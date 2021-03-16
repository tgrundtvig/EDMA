package edma.util.collections.intf;



public interface NavMap<K,V> extends OrderedMap<K,V>
{
	//Expand OrderedMap...
	public NavSet<K> getKeySetView();
	public NavMap<K,V> headMap(K toKey, boolean toInclusive);
	public NavMap<K,V> tailMap(K fromKey, boolean fromInclusive);
	public NavMap<K,V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive);
	public NavMap<K,V> reversedMap();
	
	//Obtain a cursor
	public MapCursor<K,V> getNewCursor();
	
	//Cursor initializing
	public boolean setCursorFirst(MapCursor<K,V> cursor);
	public boolean setCursorLast(MapCursor<K,V> cursor);
	public boolean setCursorEqual(MapCursor<K,V> cursor, K key);
	public boolean setCursorLessThan(MapCursor<K,V> cursor, K key, boolean inclusive);
	public boolean setCursorGreaterThan(MapCursor<K,V> cursor, K key, boolean inclusive);
	public boolean setCursorFloor(MapCursor<K,V> cursor, K key);
	public boolean setCursorCeiling(MapCursor<K,V> cursor, K key);
	public boolean setCursorHigher(MapCursor<K,V> cursor, K key);
	public boolean setCursorLower(MapCursor<K,V> cursor, K key);
	
	public interface MapCursor<K,V>
	{
		public OrderedMap<K,V> getOrderedMap();
		public boolean moveNext();
		public boolean movePrev();
		public K getCurrentKey();
		public V getCurrentValue();
	}
}
