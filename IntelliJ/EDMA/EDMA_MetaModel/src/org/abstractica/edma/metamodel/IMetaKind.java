package org.abstractica.edma.metamodel;

import org.abstractica.edma.valuedomains.IMetaValueDomain;

public interface IMetaKind extends IMetaSingleton
{	
	//Value domains
	public IMetaValueDomain getIDValueDomain();
	public IMetaValueDomain getListValueDomain();
	
	//Indexes
	public int getNumberOfIndexes();
	public IMetaIndex getIndex(int i);
	public boolean hasUniqueIndex();
	
	//Extension
	public IMetaKind getBaseKind();
	public int getNumberOfExtensions();
	public IMetaKind getExtension(int i);
	
	//Relations
	public int getNumberOfRelationsAsA();
	public IMetaRelation getRelationAsA(int i);
	public int getNumberOfRelationsAsB();
	public IMetaRelation getRelationAsB(int i);
}
