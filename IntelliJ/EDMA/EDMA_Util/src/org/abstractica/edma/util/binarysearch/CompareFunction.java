package org.abstractica.edma.util.binarysearch;

public interface CompareFunction<K, E>
{
	public int compare(K k, E e);
}
