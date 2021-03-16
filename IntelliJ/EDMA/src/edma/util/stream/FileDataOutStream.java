package edma.util.stream;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileDataOutStream implements DataOutStream
{
	private DataOutputStream out;
	
	public FileDataOutStream(String fileName) throws FileNotFoundException
	{
		this(new File(fileName));
	}
	
	public FileDataOutStream(File f) throws FileNotFoundException
	{
		out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
	}
	
	@Override
	public void write(int b) throws IOException
	{
		out.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException
	{
		out.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException
	{
		out.write(b, off, len);
	}

	@Override
	public void writeBoolean(boolean v) throws IOException
	{
		out.writeBoolean(v);
	}

	@Override
	public void writeByte(int v) throws IOException
	{
		out.writeByte(v);
	}

	@Override
	public void writeShort(int v) throws IOException
	{
		out.writeShort(v);
	}

	@Override
	public void writeChar(int v) throws IOException
	{
		out.writeChar(v);
	}

	@Override
	public void writeInt(int v) throws IOException
	{
		out.writeInt(v);
	}

	@Override
	public void writeLong(long v) throws IOException
	{
		out.writeLong(v);
	}

	@Override
	public void writeFloat(float v) throws IOException
	{
		out.writeFloat(v);
	}

	@Override
	public void writeDouble(double v) throws IOException
	{
		out.writeDouble(v);
	}

	@Override
	public void writeBytes(String s) throws IOException
	{
		out.writeBytes(s);
	}

	@Override
	public void writeChars(String s) throws IOException
	{
		out.writeChars(s);
	}

	@Override
	public void writeUTF(String s) throws IOException
	{
		out.writeUTF(s);
	}

	@Override
	public void flush() throws IOException
	{
		out.flush();
	}

	@Override
	public void close() throws IOException
	{
		out.close();
	}
}
