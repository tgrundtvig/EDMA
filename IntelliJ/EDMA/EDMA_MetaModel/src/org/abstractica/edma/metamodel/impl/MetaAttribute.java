package org.abstractica.edma.metamodel.impl;

import org.abstractica.edma.metamodel.IMetaAttribute;
import org.abstractica.edma.metamodel.IMetaIndex;
import org.abstractica.edma.metamodel.IMetaKind;
import org.abstractica.edma.metamodel.IMetaRelation;
import org.abstractica.edma.valuedomains.IMetaValueDomain;

public class MetaAttribute implements IMetaAttribute
{
	private String name;
	private String valueDomainName;
	private IMetaValueDomain valueDomain;
	
	private IMetaKind kind;
	
	private int arrayPos;
	private boolean isOptional;
	private boolean isModifiable;
	private Boolean isPartOfUnique;
	private String defaultValue;

	public MetaAttribute(	MetaKind kind,
	                     	String name,
							String valueDomainName,
							boolean isOptional,
							boolean isModifiable,
							String defaultValue)
	{
		this.kind = kind;
		this.name = name;
		this.valueDomainName = valueDomainName;
		this.isOptional = isOptional;
		this.isModifiable = isModifiable;
		this.isPartOfUnique = null;
		this.defaultValue = defaultValue;
		this.valueDomain = null;
		kind.addAttribute(this);
	}
	
	public MetaAttribute(	MetaSingleton singleton,
	                     	String name,
							String valueDomainName,
							boolean isOptional,
							boolean isModifiable,
							String defaultValue)
	{
		this.kind = null;
		this.name = name;
		this.valueDomainName = valueDomainName;
		this.isOptional = isOptional;
		this.isModifiable = isModifiable;
		this.isPartOfUnique = null;
		this.defaultValue = defaultValue;
		this.valueDomain = null;
		singleton.addAttribute(this);
	}

	
	public void hookValueDomain(ValueDomainBuilder vdb, String modelName)
	{
		valueDomain = vdb.get(valueDomainName, modelName);
		if(valueDomain == null) throw new RuntimeException("Could not find the value domain: " + valueDomainName);
	}
	
	public void setArrayPosition(int pos)
	{
		this.arrayPos = pos;
	}
	
	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public int getArrayPosition()
	{
		return arrayPos;
	}

	@Override
	public boolean isOptional()
	{
		return isOptional;
	}

	@Override
	public boolean isModifiable()
	{
		return isModifiable;
	}

	@Override
	public String getValueDomainName()
	{
		return valueDomainName;
	}
	
	@Override
	public IMetaValueDomain getValueDomain()
	{
		return valueDomain;
	}


	@Override
	public boolean isPartOfUnique()
	{
		if(isPartOfUnique != null) return isPartOfUnique.booleanValue();
		boolean res = false;
		int indexCount = kind.getNumberOfIndexes();
		for(int i = 0; i < indexCount; ++i)
		{
			IMetaIndex index = kind.getIndex(i);
			if(index.getIndexType() == IMetaIndex.IndexType.Unique)
			{
				int numAttributes = index.getNumberOfAttributes();
				for(int j = 0; j < numAttributes; ++j)
				{
					IMetaAttribute att = index.getAttribute(j);
					if(this == att)
					{
						res = true;
						break;
					}
				}
				if(res) break;
			}
		}
		if(!res)
		{
			int relCount = kind.getNumberOfRelationsAsA();
			for(int i = 0; i < relCount; ++i)
			{
				IMetaRelation rel = kind.getRelationAsA(i);
				indexCount = rel.getNumberOfIndexesOnA();
				for(int j = 0; j < indexCount; ++j)
				{
					IMetaIndex index = rel.getIndexOnA(j);
					if(index.getIndexType() == IMetaIndex.IndexType.Unique)
					{
						int numAttributes = index.getNumberOfAttributes();
						for(int k = 0; k < numAttributes; ++k)
						{
							IMetaAttribute att = index.getAttribute(k);
							if(this == att)
							{
								res = true;
								break;
							}
						}
						if(res)break;
					}
				}
				if(res) break;
			}
		}
		if(!res)
		{
			int relCount = kind.getNumberOfRelationsAsB();
			for(int i = 0; i < relCount; ++i)
			{
				IMetaRelation rel = kind.getRelationAsB(i);
				indexCount = rel.getNumberOfIndexesOnB();
				for(int j = 0; j < indexCount; ++j)
				{
					IMetaIndex index = rel.getIndexOnB(j);
					if(index.getIndexType() == IMetaIndex.IndexType.Unique)
					{
						int numAttributes = index.getNumberOfAttributes();
						for(int k = 0; k < numAttributes; ++k)
						{
							IMetaAttribute att = index.getAttribute(k);
							if(this == att)
							{
								res = true;
								break;
							}
						}
						if(res)break;
					}
				}
				if(res) break;
			}
		}
		isPartOfUnique = res;
		return res;
	}

	@Override
	public String getDefaultValue()
	{
		return defaultValue;
	}


	@Override
	public IMetaKind getKind()
	{
		return kind;
	}

}
