package org.abstractica.edma.metamodel.impl;

import java.util.ArrayList;

import org.abstractica.edma.metamodel.IMetaIndex;
import org.abstractica.edma.metamodel.IMetaIndex.IndexType;
import org.abstractica.edma.metamodel.IMetaRelation;
import org.abstractica.edma.valuedomains.impl.Constraint;

public class MetaRelation implements IMetaRelation
{
	private String name;
	private int arrayPosition;
	private MetaKind kindA;
	private MetaKind kindB;
	private String roleA;
	private String roleB;
	private RelationType type;
	private boolean hasUniqueIndexOnA;
	private boolean hasUniqueIndexOnB;
	private ArrayList<Constraint> constraints;
	private ArrayList<MetaIndex> indexesOnA;
	private ArrayList<MetaIndex> indexesOnB;

	public MetaRelation(String name,
						MetaKind kindA,
						String roleA,
						MetaKind kindB,
						String roleB,
						RelationType type)
	{
		this.name = name;
		this.kindA = kindA;
		this.kindB = kindB;
		this.roleA = roleA;
		this.roleB = roleB;
		this.type = type;
		kindA.addRelationAsA(this);
		kindB.addRelationAsB(this);
		this.constraints = null;
		indexesOnA = new ArrayList<MetaIndex>();
		indexesOnB = new ArrayList<MetaIndex>();
	}

	@Override
	public String getName()
	{
		return name;
	}
	
	public void addConstraint(Constraint constraint)
	{
		if(constraints == null)
		{
			constraints = new ArrayList<Constraint>();
		}
		constraints.add(constraint);
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

	public MetaKind getKindA()
	{
		return kindA;
	}

	public MetaKind getKindB()
	{
		return kindB;
	}

	public String getRoleA()
	{
		return roleA;
	}

	public String getRoleB()
	{
		return roleB;
	}

	public RelationType getType()
	{
		return type;
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
	
	public void addIndexOnA(MetaIndex index)
	{
		if (index.getIndexType() == IndexType.Unique)
			hasUniqueIndexOnA = true;
		
		index.setArrayPosition(indexesOnA.size());
		indexesOnA.add(index);
	}
	
	public void addIndexOnB(MetaIndex index)
	{
		if (index.getIndexType() == IndexType.Unique)
			hasUniqueIndexOnB = true;
		
		index.setArrayPosition(indexesOnB.size());
		indexesOnB.add(index);
	}

	@Override
	public int getNumberOfIndexesOnA()
	{
		if (indexesOnA == null) return 0;
		return indexesOnA.size();
	}

	@Override
	public IMetaIndex getIndexOnA(int i)
	{
		return indexesOnA.get(i);
	}

	@Override
	public boolean hasUniqueIndexOnA()
	{
		return hasUniqueIndexOnA;
	}

	@Override
	public int getNumberOfIndexesOnB()
	{
		if (indexesOnB == null) return 0;
		return indexesOnB.size();
	}

	@Override
	public IMetaIndex getIndexOnB(int i)
	{
		return indexesOnB.get(i);
	}

	@Override
	public boolean hasUniqueIndexOnB()
	{
		return hasUniqueIndexOnB;
	}

	@Override
	public boolean isOwnershipRelation()
	{
		//TODO:
		return false;
	}
	
}
