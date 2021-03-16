package org.abstractica.edma.runtime.implementations.common.resource;

import java.io.IOException;

public interface IInputStream
{
	public void beginReads() throws IOException;
	public void endReads() throws IOException;
}
