package org.abstractica.edma.runtime.implementations.mem.sets.optimized;

import java.util.Set;

public interface IUnion extends ISet
{
	public Set<ISet> getSets();
}
