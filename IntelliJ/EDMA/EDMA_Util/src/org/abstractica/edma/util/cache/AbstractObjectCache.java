package org.abstractica.edma.util.cache;

import java.io.IOError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.abstractica.edma.util.linkedlist.LinkedList;
import org.abstractica.edma.util.stream.DataInStream;
import org.abstractica.edma.util.stream.DataOutStream;

public abstract class AbstractObjectCache<T extends CacheObject<T, O>, O> implements
		ObjectCache<T, O>
{
	private DataStore dataStore;
	private Map<Long, T> map;
	private LinkedList<T> released;
	private LinkedList<T> deleted;
	private int totalObjects;

	public AbstractObjectCache(DataStore backStore)
	{
		this.dataStore = backStore;
		this.map = new HashMap<>();
		this.released = null;
		this.deleted = new LinkedList<>();
		this.totalObjects = 0;
	}
	
	public void createInitialObjects(int minObjects)
	{
		if(minObjects >= 0)
		{
			this.released = new LinkedList<>();
			for(int i = 0; i < minObjects; ++i)
			{
				T obj = newObject();
				++totalObjects;
				deleted.insertFirst(obj);
			}
		}
	}

	public abstract T newObject();

	// Get
	public T createNewObject(O owner)
	{
		T res = getFreeObject(owner);
		long id = dataStore.getNewID();
		res.setID(id);
		res.objectCreated();
		map.put(id, res);
		res.incRefCount();
		return res;
	}

	public T fetch(long id, O owner) throws ObjectNotFoundException
	{
		T res = map.get(id);
		if(res == null)
		{
			if(released == null) throw new ObjectNotFoundException();
			res = getFreeObject(owner);
			try
			{
				DataInStream in = dataStore.load(id);
				res.swapIn(in);
				in.close();
			}
			catch(IOException e)
			{
				throw new IOError(e);
			}
			res.setID(id);
			map.put(id, res);
		}
		else if(res.isFree() && released != null)
		{
			released.remove(res);
		}
		res.incRefCount();
		return res;
	}

	// Release
	public void release(T obj)
	{
		obj.decRefCount();
		if(!obj.isFree()) return;
		if(released != null) released.insertLast(obj);
	}

	public void delete(T obj)
	{
		long id = obj.getID();
		map.remove(id);
		obj.decRefCount();
		if(!obj.isFree()) throw new IllegalStateException("Deleting object that are still referenced!");
		if(released != null)
		{
			try
			{
				dataStore.delete(id);
			}
			catch(IOException e)
			{
				throw new IOError(e);
			}
		}
		deleted.insertFirst(obj);
		obj.objectDeleted();
	}

	private T getFreeObject(O owner)
	{
		T res;
		if(deleted.size() > 0)
		{
			res = deleted.removeFirst().getActualObject();
		}
		else if(released != null && released.size() > 0)
		{
			res = released.removeFirst().getActualObject();
			map.remove(res.getID());
			if(res.isUpdated())
			{
				try
				{
					DataOutStream out = dataStore.save(res.getID());
					res.swapOut(out);
					out.close();
				}
				catch(IOException e)
				{
					throw new IOError(e);
				}
			}
		}
		else
		{
			res = newObject();
			res.resetRefCount();
			++totalObjects;
		}
		res.setOwner(owner);
		res.clearUpdateFlag();
		return res;
	}
}
