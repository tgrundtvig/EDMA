package edma.util.collections.intf;

import edma.util.cache.ObjectNotFoundException;

public interface ObjectManager<O extends ManagableObject, I>
{
	public O createNew(I initializer);
	public void deleteAll();
	public O loadObject(long id) throws ObjectNotFoundException;
	public void release(O obj);
	public void delete(O obj);
}
