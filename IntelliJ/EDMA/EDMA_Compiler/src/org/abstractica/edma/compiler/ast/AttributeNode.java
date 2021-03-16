package org.abstractica.edma.compiler.ast;

public class AttributeNode extends NamedAstNode
{
	private String valueDomain, defaultValue;
	private boolean optional, modifiable;

	public AttributeNode(	String fileName,
	                     	int line,
	                     	String name,
							String valueDomain,
							boolean optional,
							boolean modifiable,
							String defaultValue)
	{
		super(fileName, line, name);
		this.valueDomain = valueDomain;
		this.optional = optional;
		this.modifiable = modifiable;
		this.defaultValue = defaultValue;
	}
	
	public String getValueDomain()
	{
		return valueDomain;
	}

	public boolean isOptional()
	{
		return optional;
	}
	
	public String getDefaultValue()
	{
		return defaultValue;
	}
	
	public boolean isModifiable()
	{
		return modifiable;
	}
	
	public void print(String tabs)
	{
		System.out.println(tabs + name + (optional ? "? : " : " : ")
				+ valueDomain);
	}
}
