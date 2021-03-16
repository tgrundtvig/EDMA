package org.abstractica.edma.compiler;

import java.io.IOException;

import org.abstractica.edma.metamodel.IMetaEnvironment;

public class Compiler
{
	public static IMetaEnvironment compile(String envName, String srcDir)
	{
		DataModelCompiler compiler = new DataModelCompiler();
		try
		{
			return compiler.compile(srcDir, envName);
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
