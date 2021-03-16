package org.abstractica.edma.runtime.implementations.common.resource;

import java.io.IOException;


public interface IDataResource
{
	public String getName() throws IOException;
	public boolean delete() throws IOException;
	public boolean clear() throws IOException;
	public boolean isOpenForWrites();
	public boolean isOpenForReads();
	
	//Appending the resource
	public IDataOutputStream getOutputStream() throws IOException;
	
	//Reading the resource...
	public IDataInputStream getInputStream() throws IOException;
}
