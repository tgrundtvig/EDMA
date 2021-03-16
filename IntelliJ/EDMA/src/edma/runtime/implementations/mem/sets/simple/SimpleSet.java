package edma.runtime.implementations.mem.sets.simple;

import java.util.Comparator;
import java.util.Set;

class SimpleSet
{
	public Set<Long> set;
	public Comparator<Long> comp;
	public boolean isOrdered;
	public SimpleSet(	Set<Long> set,
						Comparator<Long> comp,
						boolean isOrdered)
	{
		this.set = set;
		this.comp = comp;
		this.isOrdered = isOrdered;
	}
}
