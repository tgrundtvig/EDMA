package org.abstractica.edma.runtime.implementations.mem.modelstore;

import org.abstractica.edma.runtime.implementations.common.transactions.IAtomicOperations;

public interface ISingletonStore
{
	public void setAttribute(int attributeIndex, Object value);
	public Object getAttribute(int attributeIndex);
	public void directUpdate(int attributeIndex, Object value);
	//Dump content
	public void dump(IAtomicOperations out);
}
