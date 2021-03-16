package edma.runtime.implementations.mem.modelstore.speed.newindex;

import java.util.Collection;
import java.util.Map;

import edma.runtime.implementations.mem.modelstore.IRelIndex;
import edma.runtime.intf.IEntity;
import edma.runtime.intf.exceptions.UniqueException;

public interface IRelBaseIndex extends IRelIndex
{
	public void testUniqueness(Long fID, IEntity entity) throws UniqueException;
	public void add(Long fID, IEntity entity);
	public void remove(Long fID, IEntity entity);
	public void remove(Collection<Long> fIDs, IEntity entity);
	public void removeAll(Long fID);

	public Object extractUpdate(Collection<Long> fIDs,
								IEntity entity,
								Map<Integer, Object> updates);
	public void testUniqueness(Object update) throws UniqueException;
	public void doUpdate(Object update);
	public void directUpdate(	Collection<Long> fIDs,
								IEntity entity,
								Map<Integer, Object> updates);
}
