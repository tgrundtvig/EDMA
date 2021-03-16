package org.abstractica.edma.runtime.implementations.common.resource.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.abstractica.edma.runtime.implementations.common.resource.ISimpleResource;
import org.abstractica.edma.runtime.implementations.common.resource.ISimpleResourceFactory;

public class FileResourceFactory implements ISimpleResourceFactory
{
	private String name;
	private String path;
	
	
	public FileResourceFactory(String name, String path)
	{
		this.name = name;
		this.path = path;
	}


	@Override
	public String getName()
	{
		return name;
	}
	
	
	@Override
	public boolean exists(String name) throws IOException
	{
		return file(name).exists();
	}



	@Override
	public ISimpleResource create(String name) throws IOException
	{
		File f = file(name);
		if(f.exists()) throw new IOException(name + " already exists");
		return new FileResource(f, name);
	}



	@Override
	public ISimpleResource get(String name) throws IOException
	{
		File f = file(name);
		if(!f.exists()) throw new IOException(name + " does not exist");
		return new FileResource(f, name);
	}



	@Override
	public ISimpleResource getOrCreate(String name) throws IOException
	{
		File f = file(name);
		if(!f.exists()) f.createNewFile();
		return new FileResource(f, name);
	}
	
	private File file(String name)
	{
		return new File(path + "/" + name.toLowerCase() + ".data");
	}
	
	private static class FileResource implements ISimpleResource
	{
		private File file;
		private String name;
		
		
		public FileResource(File file, String name)
		{
			this.file = file;
			this.name = name;
		}
		
		@Override
		public String getName() throws IOException
		{
			return name;
		}

		@Override
		public boolean delete() throws IOException
		{
			if(file.delete())
			{
				name = null;
				file = null;
				return true;
			}
			return false;
		}
		
		@Override
		public boolean clear() throws IOException
		{
			return file.createNewFile();
		}

		@Override
		public OutputStream openForOutput() throws IOException
		{
			return new FileOutputStream(file, true);
		}

		@Override
		public InputStream openForInput() throws IOException
		{
			return new FileInputStream(file);
		}
	}
}
