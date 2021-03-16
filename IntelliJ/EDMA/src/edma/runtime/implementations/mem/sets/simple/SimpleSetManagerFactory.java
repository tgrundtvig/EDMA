package edma.runtime.implementations.mem.sets.simple;

import edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import edma.runtime.implementations.mem.sets.ISetManager;
import edma.runtime.implementations.mem.sets.ISetManagerFactory;

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
