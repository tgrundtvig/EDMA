package org.abstractica.edma.util.cache;

import java.io.IOException;

import org.abstractica.edma.util.stream.DataInStream;
import org.abstractica.edma.util.stream.DataOutStream;

public interface DataStore
{
	public long getNewID();
	public DataOutStream save(long id) throws IOException;
	public DataInStream load(long id) throws IOException, ObjectNotFoundException;
	public void delete(long id) throws IOException;
}
