package org.abstractica.edma.util.cache;

public interface ObjectCache<T extends CacheObject<T, O>, O>
{
	// Get
	public T createNewObject(O owner);
	public T fetch(long id, O owner) throws ObjectNotFoundException;

	// Release
	public void release(T obj);
	public void delete(T obj);
}
