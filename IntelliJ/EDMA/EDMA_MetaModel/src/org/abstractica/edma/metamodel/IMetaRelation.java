package org.abstractica.edma.metamodel;

import org.abstractica.edma.valuedomains.impl.Constraint;

public interface IMetaRelation
{
	public enum RelationType {OneToOne, OneToOneSame, ManyToOne, ManyToMany, ManyToManySame}
	public String getName();
	public int getArrayPosition();
	
	public RelationType getType();
	public IMetaKind getKindA();
	public IMetaKind getKindB();
	public String getRoleA();
	public String getRoleB();
	public boolean isOwnershipRelation();
	
	//Constraints
	public int getNumberOfConstraints();
	public Constraint getConstraint(int i);
	
	//Indexes
	public int getNumberOfIndexesOnA();
	public IMetaIndex getIndexOnA(int i);
	public boolean hasUniqueIndexOnA();
	
	public int getNumberOfIndexesOnB();
	public IMetaIndex getIndexOnB(int i);
	public boolean hasUniqueIndexOnB();
}
