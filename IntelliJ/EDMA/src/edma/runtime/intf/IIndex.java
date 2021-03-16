package edma.runtime.intf;

public interface IIndex
{
	public IEntity getFromUnique(Object values[]);
	
	public int getWhereNull();

	public int getEquals(Object values[]);

	public int getLessThan(Object values[]);

	public int getLessThanOrEqual(Object values[]);

	public int getGreaterThan(Object values[]);

	public int getGreaterThanOrEqual(Object values[]);

	public int getRange(Object minValues[],
						boolean minInclusive,
						Object maxValues[],
						boolean maxInclusive);
}
