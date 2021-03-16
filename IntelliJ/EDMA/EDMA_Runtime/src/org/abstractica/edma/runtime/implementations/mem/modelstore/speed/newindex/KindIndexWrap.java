package org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex;

import java.util.ArrayList;

import org.abstractica.edma.runtime.implementations.common.connection.SetProxy;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IKindIndex;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IKindStore;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManager;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.runtime.intf.IIndex;

public class KindIndexWrap implements IIndex
{
	private final IKindIndex index;
	private final IKindStore store;
	private final ISetManager setManager;
	private final ArrayList<SetProxy> sets;
	private final boolean doCopy;
	
	public KindIndexWrap(	IKindIndex index,
							IKindStore store,
							ISetManager setManager,
							ArrayList<SetProxy> sets,
							boolean doCopy)
	{
		this.index = index;
		this.store = store;
		this.setManager = setManager;
		this.sets = sets;
		this.doCopy = doCopy;
	}

	@Override
	public IEntity getFromUnique(Object[] values)
	{
		Long ID = index.getFromUnique(values);
		if(ID == null) return null;
		return store.get(ID);
	}

	@Override
	public int getWhereNull()
	{
		int res = index.getWhereNull(setManager, doCopy);
		return newSet(res);
	}

	@Override
	public int getEquals(Object[] values)
	{
		int res = index.getEquals(values, setManager, doCopy);
		return newSet(res);
	}

	@Override
	public int getLessThan(Object[] values)
	{
		int res = index.getLessThan(values, false, setManager, doCopy);
		return newSet(res);
	}

	@Override
	public int getLessThanOrEqual(Object[] values)
	{
		int res = index.getLessThan(values, true, setManager, doCopy);
		return newSet(res);
	}

	@Override
	public int getGreaterThan(Object[] values)
	{
		int res = index.getGreaterThan(values, false, setManager, doCopy);
		return newSet(res);
	}

	@Override
	public int getGreaterThanOrEqual(Object[] values)
	{
		int res = index.getGreaterThan(values, true, setManager, doCopy);
		return newSet(res);
	}

	@Override
	public int getRange(Object[] minValues,
						boolean minInclusive,
						Object[] maxValues,
						boolean maxInclusive)
	{
		int res = index.getRange(minValues, minInclusive, maxValues, maxInclusive, setManager, doCopy);
		return newSet(res);
	}
	
	private int newSet(int setID)
	{
		int res = sets.size();
		sets.add(new SetProxy(store, setID, null, setManager));
		return res;
	}

}
