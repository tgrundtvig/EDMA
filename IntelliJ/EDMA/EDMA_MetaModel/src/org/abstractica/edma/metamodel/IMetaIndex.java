package org.abstractica.edma.metamodel;

public interface IMetaIndex
{
	public enum IndexType {Unique, Equal, Compare};
	public IndexType getIndexType();
	public int getArrayPosition();
	public int getNumberOfAttributes();
	public IMetaAttribute getAttribute(int i);
	public IMetaKind getMetaKind();
	public boolean canBeNull();
}
