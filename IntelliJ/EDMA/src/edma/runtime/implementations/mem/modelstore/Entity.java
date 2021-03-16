package edma.runtime.implementations.mem.modelstore;

import java.io.Serializable;

import edma.runtime.intf.IEntity;

public class Entity implements IEntity, Serializable
{
	private static final long serialVersionUID = -2141796319067164137L;
	private Object[] value;
	
	public Entity(Object[] value)
	{
		this.value = value;
	}
	
	@Override
	public Long getID()
	{
		return (value[0] == null ? null : ((Long) value[0]));
	}

	@Override
	public Object[] getValue()
	{
		return value;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(!(obj instanceof Entity)) return false;
		Entity ent = (Entity) obj;
		return value[0].equals(ent.value[0]);
	}

	@Override
	public int hashCode()
	{
		return value[0].hashCode();
	}

	@Override
	public Object getAttribute(int i)
	{
		return value[i+1];
	}

	@Override
	public void delete()
	{
		this.value = null;
	}
	
	

}
