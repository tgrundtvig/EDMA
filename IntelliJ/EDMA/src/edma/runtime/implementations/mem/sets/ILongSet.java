package edma.runtime.implementations.mem.sets;

import java.util.Iterator;

public interface ILongSet extends Iterable<Long>
{
	public void add(Long l);
	public void remove(Long l);
	public boolean contains(Long l);
	public int size();
	public Iterator<Long> iterator();
}
