package edma.util.collections.intf;


public interface BaseSet<E> extends Iterable<E>
{
	public long getID();
	public boolean isReadOnly();
	public boolean contains(E item);
	public int size();
	public void makeReadOnly();
	
	public boolean add(E item);
	public boolean remove(E item);
	public void clear();
}
