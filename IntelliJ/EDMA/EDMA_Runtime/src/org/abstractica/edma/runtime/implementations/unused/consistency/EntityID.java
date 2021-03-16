package org.abstractica.edma.runtime.implementations.unused.consistency;

public class EntityID
{
	private final int kindIndex;
	private final long id;

	public EntityID(int kindIndex, long id)
	{
		this.kindIndex = kindIndex;
		this.id = id;
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(!(o instanceof EntityID)) return false;
		EntityID entityID = (EntityID) o;
		return (kindIndex == entityID.kindIndex && id == entityID.id);
	}

	@Override
	public int hashCode()
	{
		int res = 17;
		res = res * 31 + kindIndex;
		res = res * 31 + ((int) (id ^ (id >>> 32)));
		return res;
	}

	public int getKindIndex()
	{
		return kindIndex;
	}

	public long getId()
	{
		return id;
	}

}
