package edma.compiler.ast;

import java.util.ArrayList;
import java.util.Collection;

public class KindNode extends NamedAstNode implements IAttributeContainer
{
	private ArrayList<AttributeNode> attributes;
	private ArrayList<IndexNode> indexes;
	private Collection<ConstraintNode> constraints;
	private String baseType;
	private String valueDomainName;

	public KindNode(String fileName, int line, String name, String baseType, String valueDomain)
	{
		super(fileName, line, name);
		this.baseType = baseType;
		attributes = new ArrayList<AttributeNode>();
		indexes = new ArrayList<IndexNode>();
		valueDomainName = valueDomain;
	}
	
	@Override
	public AttributeNode getAttribute(String attr)
	{
		for (AttributeNode attributeNode : attributes)
		{
			if (attr.equals(attributeNode.getName()))
				return attributeNode;
		}
		return null;
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
	public String getValueDomainName()
	{
		return valueDomainName;
	}
	
	public void addConstraint(ConstraintNode c)
	{
		getConstraints().add(c);
	}
	
	public Collection<ConstraintNode> getConstraints()
	{
		if (constraints==null)
			constraints = new ArrayList<ConstraintNode>();
		return constraints;
	}
	
	public String getBaseType()
	{
		return baseType;
	}
	
	public void addIndex(IndexNode i)
	{
		indexes.add(i);
	}
	
	public ArrayList<IndexNode> getIndexList()
	{
		return indexes;
	}

	public void print(String tabs)
	{
		System.out.println(tabs + "Kind " + name + 
		                    (baseType != null ? " extends " + baseType : "") +
		                    "\n" + tabs + "{");

		for(AttributeNode a : attributes)
		{
			a.print(tabs + "   ");
		}
		
		for (IndexNode i : indexes)
		{
			i.print(tabs+"   ");
		}
		
		System.out.println(tabs + "}" + "\n");
	}
}
