package edma.compiler.ast;

import java.util.ArrayList;

public class ValueDomainEnum extends AValueDomainNode
{
	private ArrayList<String> elements;
	
	public ValueDomainEnum(String fileName, int line, String name, String scope)
	{
		super(fileName, line, name, scope);
		elements = new ArrayList<String>();
	}
	
	@Override
	public Type getType()
	{
		return Type.Enum;
	}
	
	public ArrayList<String> getElements()
	{
		return elements;
	}
	
	public void addElement(String e)
	{
		elements.add(e);
	}

	
	public void print(String tabs)
	{
		System.out.print(tabs+"ValueDomain "+getName()+" Enum (");
		boolean fst = true;
		for (String s : elements)
		{
			if (!fst) { System.out.print(", "); }
			System.out.print(s);
			fst=false;
		}
		System.out.println(")");
	}
}
