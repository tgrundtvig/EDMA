package edma.compiler.ast;

import java.util.ArrayList;

public class DataModelNode extends NamedAstNode
{
	private ArrayList<KindNode> kinds;
	private ArrayList<RelationNode> relations;
	private ArrayList<ConstraintNode> constraints;
	private ArrayList<SingletonNode> singletons;
	private ArrayList<IndexNode> indexes;
	private ArrayList<ActionViewNode> actions, views;
	private ArrayList<AValueDomainNode> valueDomains;
	private ArrayList<InterfaceNode> interfaces;

	public DataModelNode(String fileName, int line, String name)
	{
		super(fileName, line, name);
	}
	
	public void addIndex(IndexNode i)
	{
		getIndexes().add(i);
	}
	
	public ArrayList<IndexNode> getIndexes()
	{
		if (indexes == null)
			indexes = new ArrayList<IndexNode>();
		return indexes;
	}

	public void addKind(KindNode k)
	{
		getKinds().add(k);
	}
	
	public KindNode getKind(String kindName)
	{
		for (KindNode kn : getKinds())
		{
			if(kindName.equals(kn.getName()))
				return kn;
		}
		return null;
	}
	
	public ArrayList<KindNode> getKinds()
	{
		if (kinds == null)
			kinds = new ArrayList<KindNode>();
		return kinds;
	}
	
	public void addValueDomain(AValueDomainNode vd)
	{
		getValueDomains().add(vd);
	}
	
	public AValueDomainNode getValueDomain(String valueDomainName)
	{
		for (AValueDomainNode vd : getValueDomains())
		{
			if (valueDomainName.equals(vd.getName()))
				return vd;
		}
		return null;
	}
	
	public ArrayList<AValueDomainNode> getValueDomains() 
	{
		if (valueDomains == null)
			valueDomains = new ArrayList<AValueDomainNode>();
		return valueDomains;
	}

	public void addRelation(RelationNode r)
	{
		getRelations().add(r);
	}
	
	public RelationNode getRelation(String relationName)
	{
		for (RelationNode rn : getRelations())
		{
			if (relationName.equals(rn.getName()))
				return rn;
		}
		return null;
	}
	
	public ArrayList<RelationNode> getRelations()
	{
		if (relations==null)
			relations = new ArrayList<RelationNode>();
		return relations;
	}

	public void addConstraint(ConstraintNode c)
	{
		getConstraints().add(c);
	}
	
	public ArrayList<ConstraintNode> getConstraints()
	{
		if (constraints==null)
			constraints = new ArrayList<ConstraintNode>();
		return constraints;
	}

	public void addSingleton(SingletonNode s)
	{
		getSingletons().add(s);
	}
	
	public ArrayList<SingletonNode> getSingletons()
	{
		if(singletons == null)
			singletons = new ArrayList<SingletonNode>();
		return singletons;
	}
	
	public void addAction(ActionViewNode avn)
	{
		getActions().add(avn);
	}
	
	public void addView(ActionViewNode avn)
	{
		getViews().add(avn);
	}
	
	public ArrayList<ActionViewNode> getActions() 
	{
		if (actions==null)
			actions = new ArrayList<ActionViewNode>();
		return actions;
	}
	
	public ArrayList<ActionViewNode> getViews()
	{
		if (views==null)
			views = new ArrayList<ActionViewNode>();
		return views;
	}
	
	public ArrayList<InterfaceNode> getInterfaces()
	{
		if (interfaces == null)
			interfaces = new ArrayList<InterfaceNode>();
		return interfaces;
	}
	
	public void addInterface(InterfaceNode iNode)
	{
		getInterfaces().add(iNode);
	}

	public void print(String tabs)
	{
		System.out.println("\n" + tabs + "DataModel " + name + "\n" + tabs + "{");
		
		for(SingletonNode s : getSingletons())
		{
			s.print(tabs + "   ");
		}
		for(KindNode k : getKinds())
		{
			k.print(tabs + "   ");
		}
		for(RelationNode r : getRelations())
		{
			r.print(tabs + "   ");
		}
		for(ConstraintNode c : getConstraints())
		{
			c.print(tabs + "   ");
		}
		System.out.println("\n" + tabs + "}\n");
	}
}
