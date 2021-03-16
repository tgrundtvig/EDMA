package edma.runtime.implementations.common.transactions.impl;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;

import edma.metamodel.IMetaAttribute;
import edma.metamodel.IMetaDataModel;
import edma.metamodel.IMetaKind;
import edma.metamodel.IMetaSingleton;
import edma.runtime.implementations.common.transactions.ITransactionOperations;
import edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate.UpdateType;
import edma.valuedomains.exceptions.InvalidValueException;

public class TransactionInput
{
	private IMetaDataModel model;

	public TransactionInput(IMetaDataModel model)
	{
		this.model = model;
	}

	public long readTransactions(ITransactionOperations out, DataInput in) throws IOException
	{
		return readTransactions(out, in, Long.MAX_VALUE);
	}
	
	public long readTransactions(ITransactionOperations out, DataInput in, long minVersion) throws IOException
	{
		long curVersion = -1;
		String name;
		while(true)
		{
			try
			{
				name = in.readUTF();
			}
			catch(EOFException e)
			{
				return curVersion;
			}

			long fromVersion = in.readLong();
			out.beginTransaction(name, fromVersion);
			
			int ordinal = in.readInt();
			while(ordinal != -1)
			{
				readAtomicUpdate(ordinal, out, in);
				ordinal = in.readInt();
			}
			long toVersion = in.readLong();
			out.endTransaction(toVersion);
			curVersion = toVersion;
			if(toVersion >= minVersion) return curVersion;
		}
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

	private void readNewEntity(ITransactionOperations out, DataInput in) throws IOException
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
		out.newEntity(kindIndex, value);
	}



	private void readDeleteEntity(ITransactionOperations out, DataInput in) throws IOException
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
		out.deleteEntity(kindIndex, value);
	}

	

	private void readUpdateEntity(ITransactionOperations out, DataInput in) throws IOException
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
		out.updateEntity(kindIndex, id, fieldIndexes, newValues, oldValues);
	}

	private void readNewConnection(ITransactionOperations out, DataInput in) throws IOException
	{
		int relIndex = in.readInt();
		Long a = in.readLong();
		Long b = in.readLong();
		out.newConnection(relIndex, a, b);
	}

	private void readDeleteConnection(ITransactionOperations out, DataInput in) throws IOException
	{
		int relIndex = in.readInt();
		Long a = in.readLong();
		Long b = in.readLong();
		out.deleteConnection(relIndex, a, b);
	}


	private void readUpdateSingleton(ITransactionOperations out, DataInput in) throws IOException
	{
		int singletonIndex = in.readInt();
		int attributeIndex = in.readInt();
		IMetaSingleton singleton = model.getSingleton(singletonIndex);
		IMetaAttribute attribute = singleton.getAttribute(attributeIndex);
		Object oldValue = readAttributeValue(attribute, in);
		Object newValue = readAttributeValue(attribute, in);
		out.updateSingleton(singletonIndex, attributeIndex, oldValue, newValue);
	}

	private void readAtomicUpdate(	int ordinal,
									ITransactionOperations out,
									DataInput in) throws IOException
	{
		UpdateType type = UpdateType.values()[ordinal];
		switch(type)
		{
			case NewEntity:
				readNewEntity(out, in);
				return;
			case DeleteEntity:
				readDeleteEntity(out, in);
				return;
			case UpdateEntity:
				readUpdateEntity(out, in);
				return;
			case NewConnection:
				readNewConnection(out, in);
				return;
			case DeleteConnection:
				readDeleteConnection(out, in);
				return;
			case UpdateSingleton:
				readUpdateSingleton(out, in);
				return;
			default:
				throw new RuntimeException("Unknown UpdateType: " + type);
		}
	}
}
