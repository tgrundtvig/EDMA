package edma.runtime.implementations.unused.consistency;

public class ConnectionID
{
	private final int relationIndex;
	private final long a;
	private final long b;

	public ConnectionID(int relationIndex, long a, long b)
	{
		this.relationIndex = relationIndex;
		this.a = a;
		this.b = b;
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(!(o instanceof ConnectionID)) return false;
		ConnectionID connectionID = (ConnectionID) o;
		return (relationIndex == connectionID.relationIndex
				&& a == connectionID.a && b == connectionID.b);
	}

	@Override
	public int hashCode()
	{
		int res = 17;
		res = res * 31 + relationIndex;
		res = res * 31 + ((int) (a ^ (a >>> 32)));
		res = res * 31 + ((int) (b ^ (b >>> 32)));
		return res;
	}

	public int getRelationIndex()
	{
		return relationIndex;
	}

	public long getA()
	{
		return a;
	}

	public long getB()
	{
		return b;
	}
}
