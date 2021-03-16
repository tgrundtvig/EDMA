package edma.compiler;

import java.io.File;
import java.io.FileFilter;

public class FileFilterEdma implements FileFilter
{

	@Override
	public boolean accept(File pathname)
	{
		return (pathname.getName().endsWith(".edma"));
	}

}
