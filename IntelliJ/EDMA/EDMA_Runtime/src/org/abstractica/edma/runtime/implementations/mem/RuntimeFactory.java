package org.abstractica.edma.runtime.implementations.mem;

import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.java.JavaCollectionFactory;
import org.abstractica.edma.runtime.implementations.common.resource.ISimpleResourceFactory;
import org.abstractica.edma.runtime.implementations.common.resource.impl.FileResourceFactory;
import org.abstractica.edma.runtime.implementations.mem.factory.PersistentFactory;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManagerFactory;
import org.abstractica.edma.runtime.implementations.mem.sets.simple.SimpleSetManagerFactory;
import org.abstractica.edma.runtime.intf.IDataModelInstanceFactory;
import org.abstractica.edma.runtime.intf.IRuntimeFactory;

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
