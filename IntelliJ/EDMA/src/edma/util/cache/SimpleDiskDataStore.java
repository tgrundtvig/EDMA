package edma.util.cache;

import java.io.File;
import java.io.FileNotFoundException;

import edma.util.stream.DataInStream;
import edma.util.stream.DataOutStream;
import edma.util.stream.FileDataInStream;
import edma.util.stream.FileDataOutStream;

public class SimpleDiskDataStore implements DataStore
{
	private long idCount;
	private String dir;

	public SimpleDiskDataStore(String dir)
	{
		idCount = 0;
		this.dir = dir;
		if(!this.dir.endsWith("/")) this.dir += "/";
	}

	@Override
	public long getNewID()
	{
		return ++idCount;
	}

	@Override
	public DataOutStream save(long id) throws FileNotFoundException
	{
		return new FileDataOutStream(getFile(id));
	}

	@Override
	public DataInStream load(long id) throws ObjectNotFoundException
	{
		try
		{
			return new FileDataInStream(getFile(id));
		}
		catch(FileNotFoundException e)
		{
			throw new ObjectNotFoundException();
		}
	}

	@Override
	public void delete(long id)
	{
		File f = getFile(id);
		f.delete();
	}
	
	private File getFile(long id)
	{
		return new File(dir + id + ".data");
	}
}
