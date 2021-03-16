package edma.util.stream;

import java.io.DataOutput;
import java.io.IOException;

public interface DataOutStream extends DataOutput
{
	public void flush() throws IOException;
	public void close() throws IOException;
}
