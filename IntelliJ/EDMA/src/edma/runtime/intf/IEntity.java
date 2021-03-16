package edma.runtime.intf;

import java.io.Serializable;

public interface IEntity extends Serializable
{
	public Long getID();
	public Object getAttribute(int i);
	public Object[] getValue(); //getValue()[0] is the ID
	public void delete();
}
