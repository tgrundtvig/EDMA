package org.abstractica.edma.generator.common;

public class JavaParameter
{
	private String type;
	private String name;
	private String description;
		
	public JavaParameter(String type, String name, String description)
	{
		this.type = type;
		this.name = name;
		this.description = description;
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String toString()
	{
		return type + " " + name;
	}
	
}
