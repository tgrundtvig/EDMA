package org.abstractica.edma.metamodel.impl;

import java.util.ArrayList;

import org.abstractica.edma.metamodel.IMetaAttribute;
import org.abstractica.edma.metamodel.IMetaIndex;

public class MetaIndex implements IMetaIndex
{
	private IndexType type;
	private int arrayPos;
	private ArrayList<MetaAttribute> attributes;
	private MetaKind kind;

	public MetaIndex(IndexType type)
	{
		attributes = new ArrayList<MetaAttribute>();
		this.type = type;
	}
	
	public void setArrayPosition(int pos)
	{
		this.arrayPos = pos;
	}
	
	public int getArrayPosition()
	{
		return arrayPos;
	}
	
	public void addAttribute(MetaAttribute attribute)
	{
		attributes.add(attribute);
	}
	
	public void trimToSize()
	{
		attributes.trimToSize();
	}
	
	public void setKind(MetaKind kind)
	{
		this.kind = kind;
	}
	
	@Override
	public MetaKind getMetaKind()
	{
		return kind;
	}

	@Override
	public int getNumberOfAttributes()
	{
		return attributes.size();
	}

	@Override
	public MetaAttribute getAttribute(int i)
	{
		return attributes.get(i);
	}

	@Override
	public IndexType getIndexType()
	{
		return type;
	}

	@Override
	public boolean canBeNull()
	{
		for(IMetaAttribute att : attributes)
		{
			if(!att.isOptional())
			{
				return false;
			}
		}
		return true;
	}
}
