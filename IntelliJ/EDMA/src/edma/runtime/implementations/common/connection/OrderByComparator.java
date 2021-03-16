package edma.runtime.implementations.common.connection;

import java.util.Comparator;

import edma.runtime.implementations.mem.modelstore.IKindStore;
import edma.valuedomains.IMetaValueDomain;

public class OrderByComparator implements Comparator<Long>
{
	private IKindStore kind;
	private IComparator comparator;

	public OrderByComparator(	IKindStore kind,
								int index,
								boolean desc,
								OrderByComparator parentOrder)
	{
		this.kind = kind;
		IComparator parent = null;
		if(parentOrder != null) parent = parentOrder.comparator;
		if(index == 0)
		{
			comparator = new IDComparator(desc, parent);
		}
		else
		{
			comparator = new AttributeComparator(	index,
													kind.getMetaKind()
														.getAttribute(index - 1)
														.getValueDomain(),
													desc,
													parent);
		}

	}

	@Override
	public int compare(Long id1, Long id2)
	{
		Object[] value1 = kind.get(id1).getValue();
		Object[] value2 = kind.get(id2).getValue();
		return comparator.compare(value1, value2, false);
	}
	
	private interface IComparator
	{
		public int compare(Object[] o1, Object[] o2, boolean asParent);
	}

	private class IDComparator implements IComparator
	{
		private IComparator parent;
		private boolean desc;

		private IDComparator(boolean desc, IComparator parent)
		{
			this.desc = desc;
			this.parent = parent;
		}

		@Override
		public int compare(Object[] o1, Object[] o2, boolean asParent)
		{
			if(o1 == o2) return 0;
			if(parent != null)
			{
				int c = parent.compare(o1, o2, true);
				if(c != 0) return c;
			}
			if(desc) return -((Long) o1[0]).compareTo((Long) o2[0]);
			return ((Long) o1[0]).compareTo((Long) o2[0]);
		}
	}

	private class AttributeComparator implements IComparator
	{
		private int index;
		private IMetaValueDomain vd;
		private boolean desc;
		private IComparator parent;

		private AttributeComparator(int index,
									IMetaValueDomain vd,
									boolean desc,
									IComparator parentOrder)
		{
			this.index = index;
			this.vd = vd;
			this.desc = desc;
			this.parent = parentOrder;
		}

		@Override
		public int compare(Object[] o1, Object[] o2, boolean asParent)
		{
			if(o1 == o2) return 0;
			if(parent != null)
			{
				int c = parent.compare(o1, o2, true);
				if(c != 0) return c;
			}
			int c = vd.valueCompare(o1[index], o2[index]);
			if(asParent || c != 0) return desc ? -c : c;
			return ((Long) o1[0]).compareTo((Long) o2[0]);
		}

	}

}
