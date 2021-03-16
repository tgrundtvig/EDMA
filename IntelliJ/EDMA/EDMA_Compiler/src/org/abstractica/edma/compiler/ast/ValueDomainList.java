package org.abstractica.edma.compiler.ast;

public class ValueDomainList extends AValueDomainNode
{
	private String listType;
	private String minSize, maxSize;
	
	public ValueDomainList(String fileName,
	                       int line,
	                       String name, 
	                       String listType,
	                       String minSize,
	                       String maxSize,
	                       String scope)
	{
		super(fileName, line, name, scope);	
		this.listType = listType;
		this.minSize = minSize;
		this.maxSize = maxSize;
	}
	
	@Override
	public Type getType()
	{
		return Type.List;
	}
	
	@Override
	public void print(String tabs)
	{
		System.out.print(tabs+"ValueDomain "+
				getName()+ " ListOf<" + listType + ">");
		if (minSize != null && maxSize != null)
			System.out.print("["+minSize+".."+maxSize+"]");
	}
	
	public String getMinSize()
	{
		return minSize;
	}
	
	public String getMaxSize()
	{
		return maxSize;
	}
	
	public String getListType()
	{
		return listType;
	}
}
