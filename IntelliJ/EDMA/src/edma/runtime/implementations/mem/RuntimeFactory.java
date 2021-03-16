package edma.runtime.implementations.mem;

import edma.metamodel.IMetaDataModel;
import edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import edma.runtime.implementations.common.collectionfactory.java.JavaCollectionFactory;
import edma.runtime.implementations.common.resource.ISimpleResourceFactory;
import edma.runtime.implementations.common.resource.impl.FileResourceFactory;
import edma.runtime.implementations.mem.factory.PersistentFactory;
import edma.runtime.implementations.mem.sets.ISetManagerFactory;
import edma.runtime.implementations.mem.sets.simple.SimpleSetManagerFactory;
import edma.runtime.intf.IDataModelInstanceFactory;
import edma.runtime.intf.IRuntimeFactory;

public class RuntimeFactory implements IRuntimeFactory
{
	private CollectionFactory cf;
	private String root;
	
	public RuntimeFactory(String root)
	{
		this(root, new JavaCollectionFactory());
	}
	
	public RuntimeFactory(String root, CollectionFactory cf)
	{
		this.cf = cf;
		this.root = root;
	}

	@Override
	public IDataModelInstanceFactory getDataModelInstanceFactory(IMetaDataModel model)
	{
		ISetManagerFactory setManagerFactory = new SimpleSetManagerFactory(cf);
		ISimpleResourceFactory resourceFactory = new FileResourceFactory("TestFileResourceFactory", root);
		return new PersistentFactory(cf, model, setManagerFactory, resourceFactory);
	}

}
