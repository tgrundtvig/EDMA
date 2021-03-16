package edma.runtime.implementations.common.resource;

import java.io.IOException;

public interface ISimpleResourceFactory
{
	public String getName();
	public boolean exists(String name) throws IOException;
	public ISimpleResource create(String name) throws IOException;
	public ISimpleResource get(String name) throws IOException;
	public ISimpleResource getOrCreate(String name) throws IOException;
}
