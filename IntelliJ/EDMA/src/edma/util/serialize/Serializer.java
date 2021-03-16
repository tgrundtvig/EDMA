package edma.util.serialize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface Serializer<T>
{
	public T readFromStream(DataInput in) throws IOException;
	public void writeToStream(T t, DataOutput out) throws IOException;
}
