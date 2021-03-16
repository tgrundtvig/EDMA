package org.abstractica.edma.runtime.implementations.common.transactions.atomic.impl;

import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IDeleteConnection;

public class DeleteConnection extends AAtomicUpdate implements IDeleteConnection
{
	private final int relationIndex;
	private final Long a;
	private final Long b;
	
	
	public DeleteConnection(int relationIndex, Long a, Long b)
	{
		this.relationIndex = relationIndex;
		this.a = a;
		this.b = b;
	}

	@Override
	public UpdateType getType()
	{
		return UpdateType.DeleteConnection;
	}

	@Override
	public IAtomicUpdate getInverse()
	{
		return new NewConnection(relationIndex, a, b);
	}

	@Override
	public int getRelationIndex()
	{
		return relationIndex;
	}

	@Override
	public Long getA()
	{
		return a;
	}

	@Override
	public Long getB()
	{
		return b;
	}
	
	@Override
	public IDeleteConnection asDeleteConnection()
	{
		return this;
	}
	
	@Override
	public String toString()
	{
		return "DeleteConnection -> Relation: " + relationIndex + " (" + a + ", " + b + ")";
	}
}
