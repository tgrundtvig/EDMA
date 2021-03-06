package org.abstractica.edma.valuedomains.impl;

public class Constraint
{
	private final String name;
	private final String description;
	
	public Constraint(String name, String description)
	{
		this.name = name;
		this.description = description;
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}
}
