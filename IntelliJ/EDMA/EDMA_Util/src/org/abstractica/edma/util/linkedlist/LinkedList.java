package org.abstractica.edma.util.linkedlist;

public class LinkedList<T extends Linkable<T>>
{
	private Head<T> head;
	private int size;
	
	
	public LinkedList()
	{
		head = new Head<>();
		size = 0;
	}
	
	public int size()
	{
		return size;
	}
	
	public void insertFirst(Linkable<T> node)
	{
		node.setNext(head.getNext());
		node.setPrev(head);
		head.getNext().setPrev(node);
		head.setNext(node);
		++size;
	}
	
	public void insertLast(Linkable<T> node)
	{
		node.setPrev(head.getPrev());
		node.setNext(head);
		head.getPrev().setNext(node);
		head.setPrev(node);
		++size;
	}
	
	public Linkable<T> removeFirst()
	{
		if(size == 0) return null;
		Linkable<T> res = head.getNext();
		remove(res);
		return res;
	}
	
	public Linkable<T> removeLast()
	{
		if(size == 0) return null;
		Linkable<T> res = head.getPrev();
		remove(res);
		return res;
	}
	
	public void remove(Linkable<T> node)
	{
		node.getPrev().setNext(node.getNext());
		node.getNext().setPrev(node.getPrev());
		node.setNext(node);
		node.setPrev(node);
		--size;
	}


	public static class Head<T> implements Linkable<T>
	{
		private Linkable<T> next;
		private Linkable<T> prev;
		
		public Head()
		{
			next = this;
			prev = this;
		}

		@Override
		public Linkable<T> getNext()
		{
			return next;
		}

		@Override
		public Linkable<T> getPrev()
		{
			return prev;
		}

		@Override
		public void setNext(Linkable<T> next)
		{
			this.next = next;
		}

		@Override
		public void setPrev(Linkable<T> prev)
		{
			this.prev = prev;
		}

		@Override
		public T getActualObject()
		{
			throw new UnsupportedOperationException();
		}
	}
}
