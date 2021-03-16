package edma.runtime.implementations.common.connection;

import java.util.Iterator;

import edma.runtime.implementations.mem.modelstore.IKindStore;
import edma.runtime.implementations.mem.sets.ISetManager;
import edma.runtime.intf.IEntity;

public class SetProxy
{
	public final ISetManager setManager;
	public final IKindStore kind;
	public final OrderByComparator order;
	public final int setID;

	public SetProxy(IKindStore kind, int setID, OrderByComparator order, ISetManager setManager)
	{
		this.setManager = setManager;
		this.kind = kind;
		this.setID = setID;
		this.order = order;
	}
	
	public int size()
	{
		return setManager.getSize(setID);
	}
	
	public boolean contains(Long id)
	{
		return setManager.contains(setID, id);
	}
	
	public boolean containsAll(SetProxy setB)
	{
		return setManager.containsAll(setID, setB.setID);
	}
	
	public SetProxy orderByID(boolean desc)
	{
		OrderByComparator comp = new OrderByComparator(	kind,
														0,
														desc,
														null);
		int newSetID = setManager.order(setID, comp);
		return new SetProxy(kind, newSetID, comp, setManager);
	}
	
	public SetProxy subOrderByID(boolean desc)
	{
		OrderByComparator comp = new OrderByComparator(	kind,
														0,
														desc,
														order);
		int newSetID = setManager.order(setID, comp);
		return new SetProxy(kind, newSetID, comp, setManager);
	}
	
	public SetProxy orderBy(int attribute, boolean desc)
	{
		OrderByComparator comp = new OrderByComparator(	kind,
														attribute + 1,
														desc,
														null);
		int newSetID = setManager.order(setID, comp);
		return new SetProxy(kind, newSetID, comp, setManager);
	}
	
	public SetProxy subOrderBy(int attribute, boolean desc)
	{
		OrderByComparator comp = new OrderByComparator(	kind,
														attribute + 1,
														desc,
														order);
		int newSetID = setManager.order(setID, comp);
		return new SetProxy(kind, newSetID, comp, setManager);
	}
	
	public Iterator<IEntity> iterator()
	{
		return new SetIterator();
	}
	
	public Iterator<Long> idIterator()
	{
		return setManager.getIterator(setID);
	}
	
	private class SetIterator implements Iterator<IEntity>
	{
		private Iterator<Long> it;

		public SetIterator()
		{
			this.it = setManager.getIterator(setID);
		}

		@Override
		public boolean hasNext()
		{
			return it.hasNext();
		}

		@Override
		public IEntity next()
		{
			Long id = it.next();
			return kind.get(id);
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}

	}
}

