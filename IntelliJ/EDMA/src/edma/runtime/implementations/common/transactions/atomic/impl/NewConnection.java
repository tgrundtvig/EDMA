package edma.runtime.implementations.common.transactions.atomic.impl;

import edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate;
import edma.runtime.implementations.common.transactions.atomic.INewConnection;

public class NewConnection extends AAtomicUpdate implements INewConnection
{
	private final int relationIndex;
	private final Long a;
	private final Long b;
	
	
	
	public NewConnection(int relationIndex, Long a, Long b)
	{
		this.relationIndex = relationIndex;
		this.a = a;
		this.b = b;
	}

	@Override
	public UpdateType getType()
	{
		return UpdateType.NewConnection;
	}

	@Override
	public IAtomicUpdate getInverse()
	{
		return new DeleteConnection(relationIndex, a, b);
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
	public INewConnection asNewConnection()
	{
		return this;
	}
	
	@Override
	public String toString()
	{
		return "NewConnection -> Relation: " + relationIndex + " (" + a + ", " + b + ")";
	}

}
