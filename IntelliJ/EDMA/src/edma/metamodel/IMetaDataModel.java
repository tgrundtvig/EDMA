package edma.metamodel;

import edma.valuedomains.impl.Constraint;


public interface IMetaDataModel
{
	public String getPackage();
	public String getName();
	
	//Actions
	public int getNumberOfActions();
	public IMetaMethod getAction(int i);
	public IMetaMethod getAction(String name);
	
	//Views
	public int getNumberOfViews();
	public IMetaMethod getView(int i);
	public IMetaMethod getView(String name);
	
	//Kinds
	public int getNumberOfKinds();
	public IMetaKind getKind(int i);
	public IMetaKind getKind(String name);
	
	//Singletons
	public int getNumberOfSingletons();
	public IMetaSingleton getSingleton(int i);
	public IMetaSingleton getSingleton(String name);
	
	//Relations
	public int getNumberOfRelations();
	public IMetaRelation getRelation(int i);
	public IMetaRelation getRelation(String name);
	
	//Constraints
	public int getNumberOfConstraints();
	public Constraint getConstraint(int i);
}
