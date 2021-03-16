package org.abstractica.edma.runtime.implementations.common.resource;

import java.io.IOException;

public interface IDataResourceFactory
{
	public String getName();
	public boolean exists(String name) throws IOException;
	public IDataResource create(String name) throws IOException;
	public IDataResource get(String name) throws IOException;
	public IDataResource getOrCreate(String name) throws IOException;
}
