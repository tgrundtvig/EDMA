package org.abstractica.edma.runtime.implementations.common.resource.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.abstractica.edma.runtime.implementations.common.resource.IDataInputStream;
import org.abstractica.edma.runtime.implementations.common.resource.IDataOutputStream;
import org.abstractica.edma.runtime.implementations.common.resource.IDataResource;
import org.abstractica.edma.runtime.implementations.common.resource.IDataResourceFactory;
import org.abstractica.edma.runtime.implementations.common.resource.IInputStream;
import org.abstractica.edma.runtime.implementations.common.resource.IOutputStream;
import org.abstractica.edma.runtime.implementations.common.resource.ISimpleResource;
import org.abstractica.edma.runtime.implementations.common.resource.ISimpleResourceFactory;

public class DataResourceFactory implements IDataResourceFactory
{
	private ISimpleResourceFactory factory;
	
	public DataResourceFactory(ISimpleResourceFactory factory)
	{
		this.factory = factory;
	}

	@Override
	public String getName()
	{
		return factory.getName();
	}

	@Override
	public boolean exists(String name) throws IOException
	{
		return factory.exists(name);
	}

	@Override
	public IDataResource create(String name) throws IOException
	{
		return new DataResource(factory.create(name));
	}

	@Override
	public IDataResource get(String name) throws IOException
	{
		return new DataResource(factory.get(name));
	}

	@Override
	public IDataResource getOrCreate(String name) throws IOException
	{
		return new DataResource(factory.get(name));
	}
	
	private static class DataResource implements IDataResource, IInputStream, IOutputStream, IDataInputStream, IDataOutputStream
	{
		private ISimpleResource resource;
		private DataOutputStream outStream;
		private DataInputStream inStream;
		
		public DataResource(ISimpleResource resource)
		{
			this.resource = resource;
			inStream = null;
			outStream = null;
		}
		
		@Override
		public String getName() throws IOException
		{
			return resource.getName();
		}
		
		@Override
		public boolean isOpenForWrites()
		{
			return outStream != null;
			
		}

		@Override
		public boolean isOpenForReads()
		{
			return inStream != null;
		}
		
		@Override
		public boolean clear() throws IOException
		{
			if(outStream != null || inStream != null) throw new IOException("Illegal access to data resource!");
			return resource.clear();
		}

		@Override
		public boolean delete() throws IOException
		{
			if(outStream != null || inStream != null) throw new IOException("Illegal access to data resource!");
			
			if(resource.delete())
			{
				resource = null;
				return true;
			}
			return false;
		}

		@Override
		public IDataOutputStream getOutputStream() throws IOException
		{
			return this;
		}

		@Override
		public IDataInputStream getInputStream() throws IOException
		{
			return this;
		}
		
		@Override
		public IOutputStream outputStream()
		{
			return this;
		}

		@Override
		public IInputStream inputStream()
		{
			return this;
		}
		
		@Override
		public void beginReads() throws IOException
		{
			if(inStream != null || outStream != null) throw new IOException("Illegal access to data resource!");
			inStream = openForInput();
		}

		@Override
		public void endReads() throws IOException
		{
			if(inStream == null || outStream != null) throw new IOException("Illegal access to data resource!");
			inStream.close();
			inStream = null;
		}

		@Override
		public void beginWrites() throws IOException
		{
			if(inStream != null || outStream != null) throw new IOException("Illegal access to data resource!");
			outStream = openForOutput();
		}

		@Override
		public void flush() throws IOException
		{
			if(outStream == null) throw new IOException("Illegal access to data resource!");
			outStream.flush();
		}

		@Override
		public void endWrites() throws IOException
		{
			if(inStream != null || outStream == null) throw new IOException("Illegal access to data resource!");
			outStream.close();
			outStream = null;
		}
		
		
		private DataOutputStream openForOutput() throws IOException
		{
			OutputStream os = resource.openForOutput();
			BufferedOutputStream bos = new BufferedOutputStream(os);
			return new DataOutputStream(bos);
		}

		private DataInputStream openForInput() throws IOException
		{
			InputStream is = resource.openForInput();
			BufferedInputStream bis = new BufferedInputStream(is);
			return new DataInputStream(bis);
		}
		

		@Override
		public void readFully(byte[] b) throws IOException
		{
			inStream.readFully(b);	
		}

		@Override
		public void readFully(byte[] b, int off, int len) throws IOException
		{
			inStream.readFully(b, off, len);
		}

		@Override
		public int skipBytes(int n) throws IOException
		{
			return inStream.skipBytes(n);
		}

		@Override
		public boolean readBoolean() throws IOException
		{
			return inStream.readBoolean();
		}

		@Override
		public byte readByte() throws IOException
		{
			return inStream.readByte();
		}

		@Override
		public int readUnsignedByte() throws IOException
		{
			return inStream.readUnsignedByte();
		}

		@Override
		public short readShort() throws IOException
		{
			return inStream.readShort();
		}

		@Override
		public int readUnsignedShort() throws IOException
		{
			return inStream.readUnsignedShort();
		}

		@Override
		public char readChar() throws IOException
		{
			return inStream.readChar();
		}

		@Override
		public int readInt() throws IOException
		{
			return inStream.readInt();
		}

		@Override
		public long readLong() throws IOException
		{
			return inStream.readLong();
		}

		@Override
		public float readFloat() throws IOException
		{
			return inStream.readFloat();
		}

		@Override
		public double readDouble() throws IOException
		{
			return inStream.readDouble();
		}

		@SuppressWarnings("deprecation")
		@Override
		public String readLine() throws IOException
		{
			return inStream.readLine();
		}

		@Override
		public String readUTF() throws IOException
		{
			return inStream.readUTF();
		}

		@Override
		public void write(int b) throws IOException
		{
			outStream.write(b);
		}

		@Override
		public void write(byte[] b) throws IOException
		{
			outStream.write(b);
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException
		{
			outStream.write(b, off, len);
			
		}

		@Override
		public void writeBoolean(boolean v) throws IOException
		{
			outStream.writeBoolean(v);
		}

		@Override
		public void writeByte(int v) throws IOException
		{
			outStream.writeByte(v);
		}

		@Override
		public void writeShort(int v) throws IOException
		{
			outStream.writeShort(v);
		}

		@Override
		public void writeChar(int v) throws IOException
		{
			outStream.writeChar(v);
		}

		@Override
		public void writeInt(int v) throws IOException
		{
			outStream.writeInt(v);
		}

		@Override
		public void writeLong(long v) throws IOException
		{
			outStream.writeLong(v);
		}

		@Override
		public void writeFloat(float v) throws IOException
		{
			outStream.writeFloat(v);
		}

		@Override
		public void writeDouble(double v) throws IOException
		{
			outStream.writeDouble(v);
		}

		@Override
		public void writeBytes(String s) throws IOException
		{
			outStream.writeBytes(s);
		}

		@Override
		public void writeChars(String s) throws IOException
		{
			outStream.writeChars(s);
		}

		@Override
		public void writeUTF(String s) throws IOException
		{
			outStream.writeUTF(s);
		}
	}
}
