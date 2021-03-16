package org.abstractica.edma.util.linkedlist;

public interface Linkable<T>
{
	public Linkable<T> getNext();
	public Linkable<T> getPrev();
	public void setNext(Linkable<T> next);
	public void setPrev(Linkable<T> prev);
	public T getActualObject();
}
