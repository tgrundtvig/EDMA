package edma.runtime.implementations.mem.modelstore.speed.newindex;

import java.util.Collection;

import edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil.HashUpdate;

public class RelHashUpdate
{
	public final HashUpdate upd;
	public final Collection<Long> fIDs;
	
	public RelHashUpdate(HashUpdate upd, Collection<Long> fIDs)
	{
		this.upd = upd;
		this.fIDs = fIDs;
	}
	
}
