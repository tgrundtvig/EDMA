package org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex;

import java.util.Map;

import org.abstractica.edma.runtime.implementations.mem.modelstore.IKindIndex;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.runtime.intf.exceptions.UniqueException;

public interface IKindBaseIndex extends IKindIndex
{
	public void testUniqueness(IEntity entity) throws UniqueException;
	public void add(IEntity entity);
	public void remove(IEntity entity);
	
	public Object extractUpdate(IEntity entity, Map<Integer, Object> updates);
	public void testUniqueness(Object update) throws UniqueException;
	public void doUpdate(Object update);
	
	public void directUpdate(IEntity entity, Map<Integer, Object> updates);
}
