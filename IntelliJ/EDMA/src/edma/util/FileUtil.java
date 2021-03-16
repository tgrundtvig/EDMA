package edma.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil
{
	// returns null if file not found...
	public static String readFile(String fileName)
	{
		return readFile(new File(fileName));
	}

	public static String readFile(File f)
	{
		if(!f.exists()) { throw new RuntimeException("Could not find: " + f); }
		try
		{
			StringBuilder res = new StringBuilder();
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			while(line != null)
			{
				res.append(line);
				line = reader.readLine();
				if(line != null)
				{
					res.append("\n");
				}
			}
			reader.close();
			return res.toString();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static void writeFile(String fileName, String content)
	{
		writeFile(new File(fileName), content);
	}

	public static void writeFile(File f, String content)
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			writer.append(content);
			writer.close();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static void appendToFile(File f, String content)
	{
		try
		{
			// Create file
			FileWriter fstream = new FileWriter("out.txt", true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("Hello Java");
			// Close the output stream
			out.close();
		}
		catch(Exception e)
		{	
			// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

	public static void deleteFile(String fileName)
	{
		deleteFile(new File(fileName));
	}

	public static void deleteFile(File f)
	{
		if(f.exists())
		{
			f.delete();
		}
	}

	public static void deleteDirectory(String dir)
	{
		File path = new File(dir);
		deleteDirectory(path);
	}

	public static void deleteDirectory(File path)
	{
		if(path.exists())
		{
			File[] files = path.listFiles();
			for(int i = 0; i < files.length; i++)
			{
				if(files[i].isDirectory())
				{
					deleteDirectory(files[i]);
				}
				else
				{
					files[i].delete();
				}
			}
		}
		path.delete();
	}

	static public void deleteAllSubdirectoriesWithName(String dir, String name)
	{
		File path = new File(dir);
		deleteAllSubdirectoriesWithName(path, name);
	}

	static public void deleteAllSubdirectoriesWithName(File path, String name)
	{
		if(path.exists())
		{
			File[] files = path.listFiles();
			for(int i = 0; i < files.length; i++)
			{
				if(files[i].isDirectory())
				{
					if(name.equals(files[i].getName()))
					{
						deleteDirectory(files[i]);
					}
					else
					{
						deleteAllSubdirectoriesWithName(files[i], name);
					}
				}
			}
		}
	}

	static public void makeDirectory(String dir)
	{
		makeDirectory(new File(dir));
	}

	static public void makeDirectory(File path)
	{
		if(!path.exists())
		{
			path.mkdirs();
		}
	}
	
	static public boolean exists(String f)
	{
		return exists(new File(f));
	}
	
	static public boolean exists(File f)
	{
		return f.exists();
	}
	

	static public String convertStreamToString(InputStream is)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try
		{
			while((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				is.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static String getResource(Object localObject, String resourceName)
	{
		InputStream is = localObject.getClass()
									.getResourceAsStream(resourceName);
		return convertStreamToString(is);
	}
}
