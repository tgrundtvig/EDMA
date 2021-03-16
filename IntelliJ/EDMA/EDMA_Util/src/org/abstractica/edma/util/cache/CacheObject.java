package org.abstractica.edma.util.cache;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.abstractica.edma.util.linkedlist.Linkable;

public interface CacheObject<T, O> extends Linkable<T>
{
	//ID
	public long getID();
	public void setID(long id);
	
	//Owner
	public O getOwner();
	public void setOwner(O owner);
	
	//Update flag
	public void clearUpdateFlag();
	public void setUpdateFlag();
	public boolean isUpdated();
	
	//Ref count
	public boolean isFree();
	public void incRefCount();
	public void decRefCount();
	public void resetRefCount();
	
	//Swapping in and out
	public void swapOut(DataOutput out) throws IOException;
	public void swapIn(DataInput in) throws IOException;
	
	//Life cycle callbacks
	public void objectCreated();
	public void objectDeleted();
}