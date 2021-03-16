package edma.metamodel.impl;

import java.util.ArrayList;

import edma.metamodel.IMetaIndex.IndexType;
import edma.metamodel.IMetaKind;
import edma.metamodel.IMetaRelation;
import edma.valuedomains.IMetaValueDomain;
import edma.valuedomains.impl.Constraint;

public class MetaKind implements IMetaKind
{
	private ArrayList<MetaAttribute> attributes;
	private ArrayList<MetaIndex> indexes;
	private ArrayList<MetaKind> extensions;
	private ArrayList<MetaRelation> relationsAsA;
	private ArrayList<MetaRelation> relationsAsB;
	private IMetaValueDomain valueDomain;
	private IMetaValueDomain idValueDomain;
	private IMetaValueDomain listValueDomain;
	private MetaKind baseKind;
	private String name;
	private String publishAs;
	private int arrayPosition;
	private boolean hasUniqueIndex;
	private ArrayList<Constraint> constraints;

	public MetaKind(String name, String publishAs, MetaKind baseKind)
	{
		attributes = new ArrayList<MetaAttribute>();
		indexes = new ArrayList<MetaIndex>();
		extensions = new ArrayList<MetaKind>();
		relationsAsA = new ArrayList<MetaRelation>();
		relationsAsB = new ArrayList<MetaRelation>();
		constraints = null;
		this.baseKind = baseKind;
		this.name = name;
		this.publishAs = publishAs;
		hasUniqueIndex = false;
		if(baseKind != null)
		{
			baseKind.extensions.add(this);
		}
	}
	
	public void hookValueDomains(ValueDomainBuilder vdb, String modelName)
	{
		attributes.trimToSize();
		indexes.trimToSize();
		extensions.trimToSize();
		relationsAsA.trimToSize();
		relationsAsB.trimToSize();
		for(MetaAttribute att : attributes)
		{
			att.hookValueDomain(vdb, modelName);
		}
		for(MetaIndex index : indexes)
		{
			index.trimToSize();
		}
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
	
	public void setArrayPosition(int pos)
	{
		this.arrayPosition = pos;
	}

	@Override
	public int getArrayPosition()
	{
		return arrayPosition;
	}

	public void addAttribute(MetaAttribute attribute)
	{
		attribute.setArrayPosition(attributes.size());
		attributes.add(attribute);
	}
	
	public void addConstraint(Constraint constraint)
	{
		if(constraints == null)
		{
			constraints = new ArrayList<Constraint>();
		}
		constraints.add(constraint);
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
	public MetaAttribute getAttribute(String name)
	{
		for(MetaAttribute a : attributes)
		{
			if(a.getName().equals(name)) return a;
		}
		return null;
	}

	public void addIndex(MetaIndex index)
	{
		if (index.getIndexType() == IndexType.Unique)
			hasUniqueIndex = true;

		index.setArrayPosition(indexes.size());
		indexes.add(index);
		index.setKind(this);
	}

	@Override
	public int getNumberOfIndexes()
	{
		return indexes.size();
	}

	@Override
	public MetaIndex getIndex(int i)
	{
		return indexes.get(i);
	}

	@Override
	public MetaKind getBaseKind()
	{
		return baseKind;
	}
	
	public void setBaseKind(MetaKind kind)
	{
		baseKind = kind;
		baseKind.extensions.add(this);
	}

	public void addExtension(MetaKind kind)
	{
		extensions.add(kind);
	}
	
	public void addRelationAsA(MetaRelation rel)
	{
		relationsAsA.add(rel);
	}
	
	public void addRelationAsB(MetaRelation rel)
	{
		relationsAsB.add(rel);
	}

	@Override
	public int getNumberOfExtensions()
	{
		return extensions.size();
	}

	@Override
	public MetaKind getExtension(int i)
	{
		return extensions.get(i);
	}

	@Override
	public int getNumberOfRelationsAsA()
	{
		return relationsAsA.size();
	}

	@Override
	public IMetaRelation getRelationAsA(int i)
	{
		return relationsAsA.get(i);
	}

	@Override
	public int getNumberOfRelationsAsB()
	{
		return relationsAsB.size();
	}

	@Override
	public IMetaRelation getRelationAsB(int i)
	{
		return relationsAsB.get(i);
	}

	@Override
	public boolean hasUniqueIndex()
	{
		return hasUniqueIndex;
	}

	@Override
	public IMetaValueDomain getValueDomain()
	{
		return valueDomain;
	}

	@Override
	public IMetaValueDomain getIDValueDomain()
	{
		return idValueDomain;
	}

	@Override
	public IMetaValueDomain getListValueDomain()
	{
		return listValueDomain;
	}

	public void setValueDomain(IMetaValueDomain valueDomain)
	{
		this.valueDomain = valueDomain;
	}

	public void setIdValueDomain(IMetaValueDomain idValueDomain)
	{
		this.idValueDomain = idValueDomain;
	}
	
	public void setListValueDomain(IMetaValueDomain listValueDomain)
	{
		this.listValueDomain = listValueDomain;
	}

	@Override
	public int getNumberOfConstraints()
	{
		if(constraints == null) return 0;
		return constraints.size();
	}

	@Override
	public Constraint getConstraint(int i)
	{
		return constraints.get(i);
	}
	
	
}
