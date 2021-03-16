package org.abstractica.edma.runtime.implementations.common.transactions.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;

import org.abstractica.edma.metamodel.IMetaAttribute;
import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaKind;
import org.abstractica.edma.metamodel.IMetaSingleton;
import org.abstractica.edma.runtime.implementations.common.resource.IDataInputStream;
import org.abstractica.edma.runtime.implementations.common.resource.IDataOutputStream;
import org.abstractica.edma.runtime.implementations.common.resource.IDataResource;
import org.abstractica.edma.runtime.implementations.common.resource.IOutputStream;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransactionOperations;
import org.abstractica.edma.runtime.implementations.common.transactions.ITransactionResource;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate;
import org.abstractica.edma.runtime.implementations.common.transactions.atomic.IAtomicUpdate.UpdateType;
import org.abstractica.edma.runtime.implementations.common.transactions.streams.ITransactionInputStream;
import org.abstractica.edma.runtime.implementations.common.transactions.streams.ITransactionOperationsOutputStream;
import org.abstractica.edma.runtime.implementations.common.transactions.streams.ITransactionOutputStream;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;

class TransactionResource implements ITransactionResource
{
	private IDataResource dataResource;
	private IMetaDataModel model;
	private long startVersion;
	private long curVersion;

	public TransactionResource(	IDataResource dataResource,
								IMetaDataModel model)
	{
		this.dataResource = dataResource;
		this.model = model;
		this.curVersion = -1;
		this.startVersion = -1;
	}
	
	@Override
	public long getMaxVersion()
	{
		return curVersion;
	}

	@Override
	public long getMinVersion()
	{
		return startVersion;
	}

	@Override
	public IMetaDataModel getDataModel() throws IOException
	{
		return model;
	}

	@Override
	public boolean clear() throws IOException
	{
		return dataResource.clear();
	}

	@Override
	public String getName() throws IOException
	{
		return dataResource.getName();
	}

	@Override
	public boolean delete() throws IOException
	{
		if(dataResource.delete())
		{
			dataResource = null;
			model = null;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isOpenForWrites()
	{
		return dataResource.isOpenForWrites();
	}

	@Override
	public boolean isOpenForReads()
	{
		return dataResource.isOpenForReads();
	}
	

	@Override
	public ITransactionOperationsOutputStream getOperationsOutputStream() throws IOException
	{
		IDataOutputStream dataOut = dataResource.getOutputStream();
		return new TOOStream(model, dataOut);
	}

	@Override
	public void getCopy(ITransactionOperations out) throws IOException
	{
		IDataInputStream dataIn = dataResource.getInputStream();
		dataIn.inputStream().beginReads();
		performCopy(out, dataIn);
		dataIn.inputStream().endReads();
	}

	@Override
	public ITransactionInputStream getInputStream() throws IOException
	{
		return new TIStream(model, dataResource.getInputStream());
	}
	
	@Override
	public ITransactionOutputStream getOutputStream() throws IOException
	{
		return new TOStream(getOperationsOutputStream());
	}

	public VersionCount getVersionCount()
	{
		return new VersionCount();
	}

	// **************************************************************************************************
	// *
	// **************************************************************************************************

	public void performCopy(ITransactionOperations out, DataInput in) throws IOException
	{
		String name;
		while(true)
		{
			try
			{
				name = in.readUTF();
			}
			catch(EOFException e)
			{
				return;
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

	private void readDeleteConnection(	ITransactionOperations out,
										DataInput in) throws IOException
	{
		int relIndex = in.readInt();
		Long a = in.readLong();
		Long b = in.readLong();
		out.deleteConnection(relIndex, a, b);
	}

	private void readUpdateSingleton(	ITransactionOperations out,
										DataInput in) throws IOException
	{
		int singletonIndex = in.readInt();
		int attributeIndex = in.readInt();
		IMetaSingleton singleton = model.getSingleton(singletonIndex);
		IMetaAttribute attribute = singleton.getAttribute(attributeIndex);
		Object oldValue = readAttributeValue(attribute, in);
		Object newValue = readAttributeValue(attribute, in);
		out.updateSingleton(singletonIndex,
							attributeIndex,
							oldValue,
							newValue);
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

	private class TOOStream extends TO implements
			ITransactionOperationsOutputStream
	{
		private IOutputStream stream;

		public TOOStream(IMetaDataModel model, IDataOutputStream out)
		{
			super(model, out);
			this.stream = out.outputStream();
		}

		@Override
		public IOutputStream outputStream()
		{
			return stream;
		}
	}

	private class VersionCount implements ITransactionOperations
	{
		@Override
		public void beginTransaction(String name, long fromVersion)
		{
			if(startVersion == -1)
			{
				startVersion = fromVersion;
				curVersion = fromVersion;
			}
			if(curVersion != fromVersion) throw new RuntimeException("Wrong version!");
		}

		@Override
		public void endTransaction(long toVersion)
		{
			curVersion = toVersion;
		}

		@Override
		public void newEntity(int kindIndex, Object[] value)
		{}

		@Override
		public void deleteEntity(int kindIndex, Object[] value)
		{}

		@Override
		public void updateEntity(	int kindIndex,
									Long ID,
									int[] fields,
									Object[] oldValues,
									Object[] newValues)
		{}

		@Override
		public void newConnection(int relationIndex, Long a, Long b)
		{}

		@Override
		public void deleteConnection(int relationIndex, Long a, Long b)
		{}

		@Override
		public void updateSingleton(int singletonIndex,
									int attributeIndex,
									Object oldValue,
									Object newValue)
		{}
	}

	private class TO implements ITransactionOperations
	{
		private IMetaDataModel model;
		private DataOutput out;

		public TO(IMetaDataModel model, DataOutput out)
		{
			this.model = model;
			this.out = out;
		}

		@Override
		public void beginTransaction(String name, long fromVersion)
		{
			if(startVersion == -1)
			{
				startVersion = fromVersion;
				curVersion = fromVersion;
			}
			if(curVersion != fromVersion) throw new RuntimeException("Wrong version!");
			try
			{
				out.writeUTF(name);
				out.writeLong(fromVersion);
			}
			catch(IOException e)
			{
				throw new RuntimeException(e);
			}
		}

		@Override
		public void newEntity(int kindIndex, Object[] value)
		{
			if(out == null) return;
			IMetaKind kind = model.getKind(kindIndex);
			try
			{
				out.writeInt(IAtomicUpdate.UpdateType.NewEntity.ordinal());
				out.writeInt(kindIndex);
				out.writeLong((Long) value[0]);
				int size = kind.getNumberOfAttributes();
				for(int i = 0; i < size; ++i)
				{
					IMetaAttribute att = kind.getAttribute(i);
					writeAttributeValue(att, value[i + 1]);
				}
			}
			catch(IOException e)
			{
				throw new RuntimeException(e);
			}
		}

		@Override
		public void deleteEntity(int kindIndex, Object[] value)
		{
			if(out == null) return;
			IMetaKind kind = model.getKind(kindIndex);
			try
			{
				out.writeInt(IAtomicUpdate.UpdateType.DeleteEntity.ordinal());
				out.writeInt(kindIndex);
				out.writeLong((Long) value[0]);
				int size = kind.getNumberOfAttributes();
				for(int i = 0; i < size; ++i)
				{
					IMetaAttribute att = kind.getAttribute(i);
					writeAttributeValue(att, value[i + 1]);
				}
			}
			catch(IOException e)
			{
				throw new RuntimeException(e);
			}

		}

		@Override
		public void updateEntity(	int kindIndex,
									Long ID,
									int[] fields,
									Object[] oldValues,
									Object[] newValues)
		{
			if(out == null) return;
			IMetaKind kind = model.getKind(kindIndex);
			try
			{
				out.writeInt(IAtomicUpdate.UpdateType.UpdateEntity.ordinal());
				out.writeInt(kindIndex);
				out.writeLong(ID);
				out.writeInt(fields.length);
				for(int i = 0; i < fields.length; ++i)
				{
					out.writeInt(fields[i]);
					IMetaAttribute att = kind.getAttribute(fields[i]);
					writeAttributeValue(att, oldValues[i]);
					writeAttributeValue(att, newValues[i]);
				}
			}
			catch(IOException e)
			{
				throw new RuntimeException(e);
			}

		}

		@Override
		public void newConnection(int relationIndex, Long a, Long b)
		{
			if(out == null) return;
			try
			{
				out.writeInt(IAtomicUpdate.UpdateType.NewConnection.ordinal());
				out.writeInt(relationIndex);
				out.writeLong(a);
				out.writeLong(b);
			}
			catch(IOException e)
			{
				throw new RuntimeException(e);
			}
		}

		@Override
		public void deleteConnection(int relationIndex, Long a, Long b)
		{
			if(out == null) return;
			try
			{
				out.writeInt(IAtomicUpdate.UpdateType.DeleteConnection.ordinal());
				out.writeInt(relationIndex);
				out.writeLong(a);
				out.writeLong(b);
			}
			catch(IOException e)
			{
				throw new RuntimeException(e);
			}
		}

		@Override
		public void updateSingleton(int singletonIndex,
									int attributeIndex,
									Object oldValue,
									Object newValue)
		{
			if(out == null) return;
			IMetaSingleton singleton = model.getSingleton(singletonIndex);
			IMetaAttribute att = singleton.getAttribute(attributeIndex);
			try
			{
				out.writeInt(IAtomicUpdate.UpdateType.UpdateSingleton.ordinal());
				out.writeInt(singletonIndex);
				out.writeInt(attributeIndex);
				writeAttributeValue(att, oldValue);
				writeAttributeValue(att, newValue);
			}
			catch(IOException e)
			{
				throw new RuntimeException(e);
			}
		}

		@Override
		public void endTransaction(long toVersion)
		{
			curVersion = toVersion;
			if(out == null) return;
			try
			{
				out.writeInt(-1);
				out.writeLong(toVersion);
			}
			catch(IOException e)
			{
				throw new RuntimeException(e);
			}
		}

		private void writeAttributeValue(IMetaAttribute att, Object value) throws IOException
		{
			if(att.isOptional())
			{
				if(value == null)
				{
					out.writeBoolean(false);
				}
				else
				{
					out.writeBoolean(true);
					att.getValueDomain().writeValue(value, out);
				}
			}
			else
			{
				att.getValueDomain().writeValue(value, out);
			}
		}
	}
}