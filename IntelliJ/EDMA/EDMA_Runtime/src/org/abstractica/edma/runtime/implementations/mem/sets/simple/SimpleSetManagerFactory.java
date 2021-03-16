package org.abstractica.edma.runtime.implementations.mem.sets.simple;

import org.abstractica.edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManager;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManagerFactory;

public class SimpleSetManagerFactory implements ISetManagerFactory
{
	private CollectionFactory cf;
		
	public SimpleSetManagerFactory(CollectionFactory cf)
	{
		super();
		this.cf = cf;
	}


	@Override
	public ISetManager newSetManager()
	{
		return new SimpleSetManager(cf);
	}

}
