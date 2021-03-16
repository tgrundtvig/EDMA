package edma.compiler.ast;

import java.util.ArrayList;

import edma.util.Pair;

public class ValueDomainOneOf extends AValueDomainNode
{
	private ArrayList<Pair<String, Integer>> types;
	
	public ValueDomainOneOf(String fileName, int line, String name, String scope)
	{
		super(fileName, line, name, scope);
		types = new ArrayList<Pair<String, Integer>>();
	}
	
	@Override
	public Type getType()
	{
		return Type.OneOf;
	}
	
	@Override
	public void print(String tabs)
	{
		System.out.println(tabs + "ValueDomain " + getName() + " OneOf \n"+tabs+"{\n" + tabs + "   ");
		boolean first = true;
		for (Pair<String, Integer> p : types)
		{
			if (first)
				first = false;
			else
				System.out.println(",\n" + tabs + "   ");
			System.out.println(p.getFirst());
		}
		System.out.println("\n" + tabs + "}\n");
	}

	public void addType(String type, int lineNum)
	{
		types.add(new Pair<String, Integer>(type, lineNum));;
	}
	
	public ArrayList<Pair<String, Integer>> getTypes()
	{
		return types;
	}
	
	public ArrayList<String> getTypeNames()
	{
		ArrayList<String> typeNames = new ArrayList<String>();
		for (Pair<String, Integer> p : types)
		{
			typeNames.add(p.getFirst());
		}
		return typeNames;
	}
	
	public Pair<String, Integer> getType(int i)
	{
		return types.get(i);
	}
	
	public String getTypeName(int i)
	{
		return types.get(i).getFirst();
	}
	
	public int getTypeLine(int i)
	{
		return types.get(i).getLast();
	}


	public int getNumElements()
	{
		return types.size();
	}

}
