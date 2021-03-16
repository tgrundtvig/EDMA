package org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex;

import java.util.ArrayList;

import org.abstractica.edma.runtime.implementations.common.connection.SetProxy;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IKindStore;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IRelIndex;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManager;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.runtime.intf.IIndex;

public class RelIndexWrap implements IIndex
{
	private final IRelIndex index;
	private final Long fID;
	private final IKindStore store;
	private final ISetManager setManager;
	private final ArrayList<SetProxy> sets;
	private final boolean doCopy;
	
	public RelIndexWrap(IRelIndex index,
						Long fID,
						IKindStore store,
						ISetManager setManager,
						ArrayList<SetProxy> sets,
						boolean doCopy)
	{
		this.index = index;
		this.fID = fID;
		this.store = store;
		this.setManager = setManager;
		this.sets = sets;
		this.doCopy = doCopy;
	}

	@Override
	public IEntity getFromUnique(Object[] values)
	{
		Long ID = index.getFromUnique(fID, values);
		if(ID == null) return null;
		return store.get(ID);
	}

	@Override
	public int getWhereNull()
	{
		int res = index.getWhereNull(fID, setManager, doCopy);
		return newSet(res);
	}

	@Override
	public int getEquals(Object[] values)
	{
		int res = index.getEquals(fID, values, setManager, doCopy);
		return newSet(res);
	}

	@Override
	public int getLessThan(Object[] values)
	{
		int res = index.getLessThan(fID, values, false, setManager, doCopy);
		return newSet(res);
	}

	@Override
	public int getLessThanOrEqual(Object[] values)
	{
		int res = index.getLessThan(fID, values, true, setManager, doCopy);
		return newSet(res);
	}

	@Override
	public int getGreaterThan(Object[] values)
	{
		int res = index.getGreaterThan(fID, values, false, setManager, doCopy);
		return newSet(res);
	}

	@Override
	public int getGreaterThanOrEqual(Object[] values)
	{
		int res = index.getGreaterThan(fID, values, true, setManager, doCopy);
		return newSet(res);
	}

	@Override
	public int getRange(Object[] minValues,
						boolean minInclusive,
						Object[] maxValues,
						boolean maxInclusive)
	{
		int res = index.getRange(fID, minValues, minInclusive, maxValues, maxInclusive, setManager, doCopy);
		return newSet(res);
	}
	
	private int newSet(int setID)
	{
		int res = sets.size();
		sets.add(new SetProxy(store, setID, null, setManager));
		return res;
	}

}
