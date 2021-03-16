package edma.util.stream;

import java.io.DataInput;
import java.io.IOException;

public interface DataInStream extends DataInput
{
	public void close() throws IOException;
}
