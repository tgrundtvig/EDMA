package edma.metamodel.impl;

import java.util.ArrayList;

import edma.metamodel.IMetaAttribute;
import edma.metamodel.IMetaSingleton;
import edma.valuedomains.IMetaValueDomain;
import edma.valuedomains.impl.Constraint;

public class MetaSingleton implements IMetaSingleton
{
	private String name;
	private String publishAs;
	private ArrayList<MetaAttribute> attributes;
	private ArrayList<Constraint> constraints;
	private int arrayPosition;
	private IMetaValueDomain valueDomain;
	
	public MetaSingleton(String name, String publishAs)
	{
		this.name = name;
		this.publishAs = publishAs;
		attributes = new ArrayList<MetaAttribute>();
		constraints = new ArrayList<Constraint>();
	}
	
	public void hookValueDomains(ValueDomainBuilder vdb, String modelName)
	{
		attributes.trimToSize();
		for(MetaAttribute att : attributes)
		{
			att.hookValueDomain(vdb, modelName);
		}
	}
	
	public void setValueDomain(IMetaValueDomain valueDomain)
	{
		this.valueDomain = valueDomain;
	}
	
	public void setArrayPosition(int arrayPos)
	{
		arrayPosition = arrayPos;
	}
	

	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public String publishAs()
	{
		return publishAs;
	}

	@Override
	public int getArrayPosition()
	{
		return arrayPosition;
	}

	@Override
	public int getNumberOfAttributes()
	{
		return attributes.size();
	}
	
	public void addAttribute(MetaAttribute attribute)
	{
		attribute.setArrayPosition(attributes.size());
		attributes.add(attribute);
	}
	
	public void addConstraint(Constraint constraint)
	{
		constraints.add(constraint);
	}

	@Override
	public IMetaAttribute getAttribute(int i)
	{
		return attributes.get(i);
	}

	@Override
	public IMetaAttribute getAttribute(String name)
	{
		for (MetaAttribute att : attributes)
			if (att.getName().equals(name)) return att;
		return null;
	}

	@Override
	public IMetaValueDomain getValueDomain()
	{
		return valueDomain;
	}

	@Override
	public int getNumberOfConstraints()
	{
		return constraints.size();
	}

	@Override
	public Constraint getConstraint(int i)
	{
		return constraints.get(i);
	}

	

}
