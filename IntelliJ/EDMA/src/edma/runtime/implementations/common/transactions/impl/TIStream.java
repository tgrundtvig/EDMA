package edma.runtime.implementations.common.transactions.impl;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;

import edma.metamodel.IMetaAttribute;
import edma.metamodel.IMetaDataModel;
import edma.metamodel.IMetaKind;
import edma.metamodel.IMetaSingleton;
import edma.runtime.implementations.common.resource.IDataInputStream;
import edma.runtime.implementations.common.resource.IInputStream;
import edma.runtime.implementations.common.transactions.ITransaction;
import edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate;
import edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate.UpdateType;
import edma.runtime.implementations.common.transactions.atomic.impl.DeleteConnection;
import edma.runtime.implementations.common.transactions.atomic.impl.DeleteEntity;
import edma.runtime.implementations.common.transactions.atomic.impl.NewConnection;
import edma.runtime.implementations.common.transactions.atomic.impl.NewEntity;
import edma.runtime.implementations.common.transactions.atomic.impl.UpdateEntity;
import edma.runtime.implementations.common.transactions.atomic.impl.UpdateSingleton;
import edma.runtime.implementations.common.transactions.streams.ITransactionInputStream;
import edma.valuedomains.exceptions.InvalidValueException;

public class TIStream implements ITransactionInputStream
{
	private final IMetaDataModel model;
	private final IDataInputStream in;

	public TIStream(IMetaDataModel model, IDataInputStream in)
	{
		this.model = model;
		this.in = in;
	}

	@Override
	public IInputStream inputStream()
	{
		return in.inputStream();
	}

	@Override
	public ITransaction readNext() throws IOException
	{
		String name;
		try
		{
			name = in.readUTF();
		}
		catch(EOFException e)
		{
			return null;
		}
		Transaction res = new Transaction(name, in.readLong());
		int ordinal = in.readInt();
		while(ordinal != -1)
		{
			res.add(readAtomicUpdate(ordinal, in));
			ordinal = in.readInt();
		}
		res.setToVersion(in.readLong());
		return res;
	}

	private Object readAttributeValue(IMetaAttribute att, DataInput in) throws IOException
	{
		if(!att.isOptional() || in.readBoolean()) try
		{
			return att.getValueDomain().readValue(in, null);
		}
		catch(InvalidValueException e)
		{
			throw new IOException(e);
		}
		return null;
	}

	private IAtomicUpdate readNewEntity(DataInput in) throws IOException
	{
		int kindIndex = in.readInt();
		Long id = in.readLong();
		IMetaKind kind = model.getKind(kindIndex);
		int size = kind.getNumberOfAttributes();
		Object[] value = new Object[size + 1];
		value[0] = id;
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = kind.getAttribute(i);
			value[i + 1] = readAttributeValue(att, in);
		}
		return new NewEntity(kindIndex, value);
	}

	private IAtomicUpdate readDeleteEntity(DataInput in) throws IOException
	{
		int kindIndex = in.readInt();
		Long id = in.readLong();
		IMetaKind kind = model.getKind(kindIndex);
		int size = kind.getNumberOfAttributes();
		Object[] value = new Object[size + 1];
		value[0] = id;
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = kind.getAttribute(i);
			value[i + 1] = readAttributeValue(att, in);
		}
		return new DeleteEntity(kindIndex, value);
	}

	private IAtomicUpdate readUpdateEntity(DataInput in) throws IOException
	{
		int kindIndex = in.readInt();
		Long id = in.readLong();
		IMetaKind kind = model.getKind(kindIndex);
		int size = in.readInt();
		int[] fieldIndexes = new int[size];
		Object[] oldValues = new Object[size];
		Object[] newValues = new Object[size];
		for(int i = 0; i < size; ++i)
		{
			int index = in.readInt();
			fieldIndexes[i] = index;
			IMetaAttribute att = kind.getAttribute(index);
			oldValues[i] = readAttributeValue(att, in);
			newValues[i] = readAttributeValue(att, in);
		}
		return new UpdateEntity(kindIndex,
								id,
								fieldIndexes,
								oldValues,
								newValues);
	}

	private IAtomicUpdate readNewConnection(DataInput in) throws IOException
	{
		int relIndex = in.readInt();
		Long a = in.readLong();
		Long b = in.readLong();
		return new NewConnection(relIndex, a, b);
	}

	private IAtomicUpdate readDeleteConnection(DataInput in) throws IOException
	{
		int relIndex = in.readInt();
		Long a = in.readLong();
		Long b = in.readLong();
		return new DeleteConnection(relIndex, a, b);
	}

	private IAtomicUpdate readUpdateSingleton(DataInput in) throws IOException
	{
		int singletonIndex = in.readInt();
		int attributeIndex = in.readInt();
		IMetaSingleton singleton = model.getSingleton(singletonIndex);
		IMetaAttribute attribute = singleton.getAttribute(attributeIndex);
		Object oldValue = readAttributeValue(attribute, in);
		Object newValue = readAttributeValue(attribute, in);
		return new UpdateSingleton(	singletonIndex,
									attributeIndex,
									oldValue,
									newValue);
	}

	private IAtomicUpdate readAtomicUpdate(int ordinal, DataInput in) throws IOException
	{
		UpdateType type = UpdateType.values()[ordinal];
		switch(type)
		{
			case NewEntity:
				return readNewEntity(in);
			case DeleteEntity:
				return readDeleteEntity(in);
			case UpdateEntity:
				return readUpdateEntity(in);
			case NewConnection:
				return readNewConnection(in);
			case DeleteConnection:
				return readDeleteConnection(in);
			case UpdateSingleton:
				return readUpdateSingleton(in);
			default:
				throw new RuntimeException("Unknown UpdateType: "
						+ type);
		}
	}
}