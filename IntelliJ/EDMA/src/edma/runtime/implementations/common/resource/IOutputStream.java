package edma.runtime.implementations.common.resource;

import java.io.IOException;

public interface IOutputStream
{
	public void beginWrites() throws IOException;
	public void flush() throws IOException;
	public void endWrites() throws IOException;
}
