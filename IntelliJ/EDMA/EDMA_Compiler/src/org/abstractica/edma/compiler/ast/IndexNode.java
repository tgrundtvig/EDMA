package org.abstractica.edma.compiler.ast;

import java.util.ArrayList;

public class IndexNode extends AstNode
{
	public enum IndexType { Unique, Equals, Compare }
	private IndexType type;
	private ArrayList<String> attributes;
	private String kind;
	private String role; 
	
	public IndexNode(String fileName, int line, IndexType t, String kind, String role) 
	{
		super(fileName, line);
		this.kind = kind;
		this.role = role;
		type = t;
		attributes = new ArrayList<String>();
	}
	
	public void addAttribute(String att) 
	{
		attributes.add(att);
	}
	
	public ArrayList<String> getAttributeList()
	{
		return attributes;
	}
	
	public IndexType getType()
	{
		return type;
	}
	
	public String getKind()
	{
		return kind;
	}
	
	public String getRole()
	{
		return role;
	}
	
	public void print(String tabs)
	{
		System.out.print(tabs);
		if (type == IndexType.Unique)
		{
			System.out.print("Unique");
		} 
		else if (type == IndexType.Equals)
		{
			System.out.print("Equals");
		}
		else 
		{
			System.out.print("Compare");
		}
		
		System.out.print("(");
		
		boolean fst = true;
		for (String a : attributes)
		{
			if (fst)
				fst = false;
			else
				System.out.print(", ");
			
			System.out.println(a);
		}
		
		System.out.print(")");
	}
}
