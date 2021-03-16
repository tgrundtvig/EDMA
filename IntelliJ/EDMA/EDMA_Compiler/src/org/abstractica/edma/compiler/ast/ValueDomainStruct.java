package org.abstractica.edma.compiler.ast;

import java.util.ArrayList;

public class ValueDomainStruct extends AValueDomainNode
{
	private ArrayList<String> elementNames;
	private ArrayList<String> elementTypes;
	private ArrayList<Boolean> elementOptional;
	private ArrayList<Integer> elementLineNumbers;
	
	public ValueDomainStruct(String fileName, int line, String name, String scope)
	{
		super(fileName, line, name, scope);
		elementNames = new ArrayList<String>();
		elementTypes = new ArrayList<String>();
		elementOptional = new ArrayList<Boolean>();
		elementLineNumbers = new ArrayList<Integer>();
	}
	
	@Override
	public Type getType()
	{
		return Type.Struct;
	}
	
	@Override
	public void print(String tabs)
	{
		System.out.println("\n" + tabs + "ValueDomain " + getName());
		System.out.println(tabs + "{");
		for (int i=0; i < elementTypes.size(); i++)
		{
			System.out.print(tabs+"   "+elementNames.get(i)+" : ");
			System.out.println(elementTypes.get(i));
		}
		System.out.println(tabs + "}\n");
	}
	
	public String getElementName(int i)
	{
		return elementNames.get(i);
	}
	
	public String getElementType(int i)
	{
		return elementTypes.get(i);
	}
	
	public boolean getElementOptional(int i)
	{
		return elementOptional.get(i);
	}
	
	public int getElementLineNumber(int i)
	{
		return elementLineNumbers.get(i);
	}
	
	public int getNumElements()
	{
		return elementNames.size();
	}
	
	public void addElement(int lineNum, String name, String type, boolean optional)
	{
		elementNames.add(name);
		elementTypes.add(type);
		elementOptional.add(optional);
		elementLineNumbers.add(lineNum);
	}
}
