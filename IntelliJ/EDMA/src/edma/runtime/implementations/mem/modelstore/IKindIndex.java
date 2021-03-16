package edma.runtime.implementations.mem.modelstore;

import edma.runtime.implementations.mem.sets.ISetManager;

public interface IKindIndex
{
	public Long getFromUnique(Object[] values); // Only on unique indexes

	public int getEquals(Object[] values, ISetManager setManager, boolean doCopy); // Only on
																	// compare
																	// and
																	// equals
																	// indexes

	public int getLessThan(	Object[] values,
							boolean inclusive,
							ISetManager setManager,
							boolean doCopy); // Only on compare indexes

	public int getGreaterThan(	Object[] values,
								boolean inclusive,
								ISetManager setManager,
								boolean doCopy); // Only on compare
															// indexes

	public int getRange(Object[] min,
						boolean minInclusive,
						Object[] max,
						boolean maxInclusive,
						ISetManager setManager,
						boolean doCopy); // Only on compare indexes

	public int getWhereNull(ISetManager setManager, boolean doCopy);
}
