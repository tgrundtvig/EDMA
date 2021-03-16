package edma.runtime.implementations.common.collectionfactory.java;

import java.util.Comparator;
import java.util.TreeMap;

import edma.runtime.implementations.common.collectionfactory.DNavigableMap;

public class DJavaTreeMap<K,V> extends TreeMap<K,V> implements DNavigableMap<K,V>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1141276104628426275L;
	
	public DJavaTreeMap()
	{
		super();
	}
	
	public DJavaTreeMap(Comparator<K> comparator)
	{
		super(comparator);
	}

	@Override
	public void delete(){}
}
