package edma.compiler.ast;

import java.util.ArrayList;
import java.util.Collection;

public class SingletonNode extends NamedAstNode implements IAttributeContainer
{
	private ArrayList<AttributeNode> attributes;
	private String valueDomainName;
	private ArrayList<ConstraintNode> constraints;

	public SingletonNode(String fileName, int line, String name, String valueDomain)
	{
		super(fileName, line, name);
		attributes = new ArrayList<AttributeNode>();
		valueDomainName = valueDomain;
	}
	
	public Collection<ConstraintNode> getConstraints()
	{
		if (constraints == null)
			constraints = new ArrayList<ConstraintNode>();
		return constraints;
	}
	
	public void addConstraint(ConstraintNode c)
	{
		getConstraints().add(c);
	}
		
	@Override
	public void addAttributes(Collection<AttributeNode> atts)
	{
		attributes.addAll(atts);
	}

	@Override
	public AttributeNode getAttribute(int i)
	{
		return attributes.get(i);
	}
	
	@Override
	public void addAttribute(AttributeNode a)
	{
		attributes.add(a);
	}
	
	@Override
	public ArrayList<AttributeNode> getAttributes()
	{
		return attributes;
	}
	
	@Override
	public String getValueDomainName()
	{
		return valueDomainName;
	}
	
	public void print(String tabs)
	{
		System.out.println(tabs + "Singleton " + name + 
		                    "\n" + tabs + "{");

		for(AttributeNode a : attributes)
		{
			a.print(tabs + "   ");
		}
		System.out.println(tabs + "}\n");
	}

	@Override
	public AttributeNode getAttribute(String att)
	{
		for (AttributeNode a : getAttributes())
			if (a.getName().equals(att))
				return a;
		return null;
			
	}
}
