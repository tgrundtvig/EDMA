package org.abstractica.edma.runtime.implementations.common.collectionfactory.java;

import java.util.Comparator;
import java.util.TreeSet;

import org.abstractica.edma.runtime.implementations.common.collectionfactory.DNavigableSet;

public class DJavaTreeSet<E> extends TreeSet<E> implements DNavigableSet<E>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2931406073316180449L;
	
	public DJavaTreeSet()
	{
		super();
	}
	
	public DJavaTreeSet(Comparator<E> comparator)
	{
		super(comparator);
	}

	@Override
	public void delete(){}
}
