package edma.runtime.implementations.mem.modelstore;

import edma.runtime.implementations.mem.sets.ISetManager;

public interface IRelIndex
{
	public Long getFromUnique(Long fID, Object[] values); // Only on unique indexes

	public int getEquals(Long fID, Object[] values, ISetManager setManager, boolean doCopy); // Only on
																	// compare
																	// and
																	// equals
																	// indexes

	public int getLessThan(	Long fID,
	                       	Object[] values,
							boolean inclusive,
							ISetManager setManager,
							boolean doCopy); // Only on compare indexes

	public int getGreaterThan(	Long fID,
	                          	Object[] values,
								boolean inclusive,
								ISetManager setManager,
								boolean doCopy); // Only on compare
															// indexes

	public int getRange(Long fID,
	                    Object[] min,
						boolean minInclusive,
						Object[] max,
						boolean maxInclusive,
						ISetManager setManager,
						boolean doCopy); // Only on compare indexes

	public int getWhereNull(Long fID, ISetManager setManager, boolean doCopy);
}
