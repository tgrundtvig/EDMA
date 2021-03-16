package org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex;

import java.io.Serializable;
import java.util.Map;

import org.abstractica.edma.metamodel.IMetaAttribute;
import org.abstractica.edma.metamodel.IMetaIndex;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.valuedomains.IValueDomainDefinitions;

public class IndexUtil
{
	private static IValueDomainDefinitions vdd;
	
	public static void setValueDomainDefinitions(IValueDomainDefinitions def)
	{
		vdd = def;
	}

	public static Object[] extractValues(IEntity entity, IMetaIndex index)
	{
		int size = index.getNumberOfAttributes();
		Object[] res = new Object[size];
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = index.getAttribute(i);
			res[i] = entity.getAttribute(att.getArrayPosition());
		}
		return res;
	}

	public static boolean needUpdate(	Map<Integer, Object> updates,
										IMetaIndex index)
	{
		int size = index.getNumberOfAttributes();
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = index.getAttribute(i);
			if(updates.containsKey(att.getArrayPosition())) return true;
		}
		return false;
	}

	public static HashUpdate extractHashUpdate(	IEntity oldEntity,
												Map<Integer, Object> updates,
												IMetaIndex index)
	{
		int size = index.getNumberOfAttributes();
		Object[] oldValues = new Object[size];
		Object[] newValues = new Object[size];
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = index.getAttribute(i);
			Integer attIndex = att.getArrayPosition();
			oldValues[i] = oldEntity.getAttribute(attIndex);
			newValues[i] = updates.containsKey(attIndex) ? updates.get(attIndex)
					: oldValues[i];
		}
		HashKey oldKey = new HashKey(index, oldValues);
		HashKey newKey = new HashKey(index, newValues);
		return new HashUpdate(oldEntity.getID(), oldKey, newKey);
	}

	public static CompareUpdate extractCompareUpdate(	IEntity oldEntity,
														Map<Integer, Object> updates,
														IMetaIndex index)
	{
		int size = index.getNumberOfAttributes();
		Object[] oldValues = new Object[size];
		Object[] newValues = new Object[size];
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = index.getAttribute(i);
			Integer attIndex = att.getArrayPosition();
			oldValues[i] = oldEntity.getAttribute(attIndex);
			newValues[i] = updates.containsKey(attIndex) ? updates.get(attIndex)
					: oldValues[i];
		}
		CompareKey oldKey = new CompareKey(index, oldEntity.getID(), oldValues);
		CompareKey newKey = new CompareKey(index, oldEntity.getID(), newValues);
		return new CompareUpdate(oldKey, newKey);
	}

	public static int valueHashCode(int vdIndex, Object value)
	{
		return vdd.getValueDomain(vdIndex).valueHashCode(value);
	}

	public static boolean valueEqual(int vdIndex, Object a, Object b)
	{
		return vdd.getValueDomain(vdIndex).valueEqual(a, b);
	}

	public static int valueCompare(int vdIndex, Object a, Object b)
	{
		return vdd.getValueDomain(vdIndex).valueCompare(a, b);
	}

	public static HashKey getHashKey(Object[] values, IMetaIndex index)
	{
		return new HashKey(index, values);
	}

	public static CompareKey getNullCompareKey(IMetaIndex index)
	{
		Object[] values = new Object[index.getNumberOfAttributes()];
		return new CompareKey(index, Long.MAX_VALUE, values);
	}

	public static HashKey getHashKey(IEntity entity, IMetaIndex index)
	{
		return new HashKey(index, extractValues(entity, index));
	}

	public static HashKey getNullHashKey(IMetaIndex index)
	{
		Object[] values = new Object[index.getNumberOfAttributes()];
		return new HashKey(index, values);
	}

	public static CompareKey getCompareKeyLow(Object[] values, IMetaIndex index)
	{
		return new CompareKey(index, Long.MIN_VALUE, values);
	}

	public static CompareKey getCompareKeyHigh(Object[] values, IMetaIndex index)
	{
		return new CompareKey(index, Long.MAX_VALUE, values);
	}

	public static CompareKey getCompareKey(IEntity entity, IMetaIndex index)
	{
		return new CompareKey(index, entity.getID(), extractValues(	entity,
																	index));
	}

	private static int[] getValueDomainIndexes(IMetaIndex index)
	{
		int[] res = new int[index.getNumberOfAttributes()];
		for(int i = 0; i < res.length; ++i)
		{
			res[i] = index.getAttribute(i).getValueDomain().getIndex();
		}
		return res;
	}

	public static class HashKey implements Serializable
	{
		private static final long serialVersionUID = 317321733886090051L;
		private int hash;
		private Object[] values;
		private int[] valueDomainIndexes;

		private HashKey(IMetaIndex index, Object[] values)
		{
			this.valueDomainIndexes = getValueDomainIndexes(index);
			this.values = values;
			int hash = 17;
			for(int i = 0; i < values.length; ++i)
			{
				hash = 31 * hash
						+ valueHashCode(valueDomainIndexes[i], values[i]);
			}
			this.hash = hash;
		}

		@Override
		public int hashCode()
		{
			return hash;
		}

		@Override
		public boolean equals(Object o)
		{
			if(this == o) return true;
			// if(!(o instanceof Key)) return false; (not needed since we will
			// only compare to other keys)
			HashKey k = (HashKey) o;
			for(int i = 0; i < values.length; ++i)
			{
				if(!valueEqual(valueDomainIndexes[i], values[i], k.values[i])) return false;
			}
			return true;
		}

		public boolean isNull()
		{
			for(int i = 0; i < values.length; ++i)
			{
				if(values[i] != null) return false;
			}
			return true;
		}
	}

	public static class CompareKey implements Comparable<CompareKey>, Serializable
	{
		private static final long serialVersionUID = -6620977704856505109L;
		private Long ID;
		private Object[] values;
		private int[] valueDomainIndexes;

		private CompareKey(IMetaIndex index, Long ID, Object[] values)
		{
			this.valueDomainIndexes = getValueDomainIndexes(index);
			this.ID = ID;
			this.values = values;

		}

		public Long getID()
		{
			return ID;
		}

		@Override
		public int compareTo(CompareKey o)
		{
			for(int i = 0; i < values.length; ++i)
			{
				int c = valueCompare(	valueDomainIndexes[i],
										values[i],
										o.values[i]);
				if(c != 0) return c;
			}
			return ID.compareTo(o.ID);
		}
	}

	public static class HashUpdate
	{
		public final Long ID;
		public final HashKey oldKey;
		public final HashKey newKey;

		public HashUpdate(Long ID, HashKey oldKey, HashKey newKey)
		{
			this.ID = ID;
			this.oldKey = oldKey;
			this.newKey = newKey;
		}
	}

	public static class CompareUpdate
	{
		public final CompareKey oldKey;
		public final CompareKey newKey;

		public CompareUpdate(CompareKey oldKey, CompareKey newKey)
		{
			this.oldKey = oldKey;
			this.newKey = newKey;
		}

	}
}
