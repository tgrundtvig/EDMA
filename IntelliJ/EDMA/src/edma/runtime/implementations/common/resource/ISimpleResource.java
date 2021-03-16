package edma.runtime.implementations.common.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ISimpleResource
{
	public String getName() throws IOException;
	public boolean delete() throws IOException;
	public boolean clear() throws IOException;
	public OutputStream openForOutput() throws IOException;
	public InputStream openForInput() throws IOException;
}
