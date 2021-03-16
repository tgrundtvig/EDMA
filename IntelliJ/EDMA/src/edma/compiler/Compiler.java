package edma.compiler;

import java.io.IOException;

import edma.metamodel.IMetaEnvironment;

public class Compiler
{
	public static IMetaEnvironment compile(String envName, String rootDir)
	{
		DataModelCompiler compiler = new DataModelCompiler();
		try
		{
			return compiler.compile(rootDir, envName);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
