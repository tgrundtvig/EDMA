package org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex;

import java.util.Collection;

import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.CompareUpdate;

public class RelCompareUpdate
{
	public final CompareUpdate upd;
	public final Collection<Long> fIDs;
	
	public RelCompareUpdate(CompareUpdate upd, Collection<Long> fIDs)
	{
		this.upd = upd;
		this.fIDs = fIDs;
	}
	
}
