package org.abstractica.edma.runtime.intf;

import java.io.IOException;
import java.util.Enumeration;


public interface IDataModelInstanceFactory
{	
	public boolean exists(String name) throws IOException;
	
	public IDataModelInstance getModelInstance(String name) throws IOException;

	public IDataModelInstance newModelInstance(String name, Object[] singletonValues) throws IOException;

	public boolean deleteModelInstance(String name) throws IOException;
	
	public Enumeration<String> getInstanceNames() throws IOException;

	// TODO: Add possibility to convert models, search for specific MetaModel
	// instances, clone instances, branch etc.
}
