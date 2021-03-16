package org.abstractica.edma.util.stream;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileDataInStream implements DataInStream
{
	private DataInputStream in;
	
	public FileDataInStream(String fileName) throws FileNotFoundException
	{
		this(new File(fileName));
	}
	
	public FileDataInStream(File f) throws FileNotFoundException
	{
		in = new DataInputStream(new BufferedInputStream(new FileInputStream(f)));
	}
	
	@Override
	public void readFully(byte[] b) throws IOException
	{
		in.readFully(b);
	}

	@Override
	public void readFully(byte[] b, int off, int len) throws IOException
	{
		in.readFully(b, off, len);
	}

	@Override
	public int skipBytes(int n) throws IOException
	{
		return in.skipBytes(n);
	}

	@Override
	public boolean readBoolean() throws IOException
	{
		return in.readBoolean();
	}

	@Override
	public byte readByte() throws IOException
	{
		return in.readByte();
	}

	@Override
	public int readUnsignedByte() throws IOException
	{
		return in.readUnsignedByte();
	}

	@Override
	public short readShort() throws IOException
	{
		return in.readShort();
	}

	@Override
	public int readUnsignedShort() throws IOException
	{
		return in.readUnsignedShort();
	}

	@Override
	public char readChar() throws IOException
	{
		return in.readChar();
	}

	@Override
	public int readInt() throws IOException
	{
		return in.readInt();
	}

	@Override
	public long readLong() throws IOException
	{
		return in.readLong();
	}

	@Override
	public float readFloat() throws IOException
	{
		return in.readFloat();
	}

	@Override
	public double readDouble() throws IOException
	{
		return in.readDouble();
	}

	@Override
	@Deprecated
	public String readLine() throws IOException
	{
		return in.readLine();
	}

	@Override
	public String readUTF() throws IOException
	{
		return in.readUTF();
	}

	@Override
	public void close() throws IOException
	{
		in.close();
	}

}
