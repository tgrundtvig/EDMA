package edma.util;


public class Pair<F, L>
{
	private F first;
	private L last;

	public Pair(F first, L last)
	{
		this.first = first;
		this.last = last;
	}

	public void setFirst(F first)
	{
		this.first = first;
	}

	public void setLast(L last)
	{
		this.last = last;
	}

	public F getFirst()
	{
		return first;
	}

	public L getLast()
	{
		return last;
	}
	
	public boolean equals(Pair<F, L> pair)
	{
		return(first.equals(pair.first) && last.equals(pair.last));
	}
	
	public int hashCode()
	{
		int res = 17;
		res = 31 * res + first.hashCode();
		res = 31 * res + last.hashCode();
		return res;
	}
	
}
