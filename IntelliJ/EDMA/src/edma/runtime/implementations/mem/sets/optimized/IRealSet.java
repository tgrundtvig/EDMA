package edma.runtime.implementations.mem.sets.optimized;

import java.util.Set;

public interface IRealSet extends ISet
{
	public boolean isCopy();
	public Set<Long> getSet();
}
