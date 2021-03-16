package edma.runtime.implementations.mem.sets.optimized;

import java.util.Set;

public class RealSet extends ABaseSet implements IRealSet
{
	private Set<Long> set;
	private boolean isCopy;
	
	public RealSet(int id, Set<Long> set, boolean isCopy)
	{
		super(id, set.size());
		this.set = set;
		this.isCopy = isCopy;
	}

	@Override
	public Set<Long> getSet()
	{
		return set;
	}

	@Override
	public boolean isCopy()
	{
		return isCopy;
	}

	@Override
	public SetType getType()
	{
		return SetType.Real;
	}

	@Override
	public IRealSet generateRealSet()
	{
		return this;
	}
}
