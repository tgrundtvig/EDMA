package org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex;

import java.util.Collection;

public class RelHashUpdate
{
	public final IndexUtil.HashUpdate upd;
	public final Collection<Long> fIDs;
	
	public RelHashUpdate(IndexUtil.HashUpdate upd, Collection<Long> fIDs)
	{
		this.upd = upd;
		this.fIDs = fIDs;
	}
	
}
