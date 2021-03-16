package org.abstractica.edma.runtime.remote;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface IServerInstance
{
	public void call(int methodID, DataInput in, DataOutput out) throws IOException;
}
