package org.abstractica.edma.metamodel.impl;

import java.util.ArrayList;

import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaKind;
import org.abstractica.edma.metamodel.IMetaMethod;
import org.abstractica.edma.metamodel.IMetaRelation;
import org.abstractica.edma.metamodel.IMetaSingleton;
import org.abstractica.edma.valuedomains.impl.Constraint;

/**
 * This is a test implementation of the MetaDataModel
 * 
 * @author martin
 * 
 */
public class MetaDataModel implements IMetaDataModel
{
	private ArrayList<MetaMethod> actions;
	private ArrayList<MetaMethod> views;
	private ArrayList<MetaKind> kinds;
	private ArrayList<MetaSingleton> singletons;
	private ArrayList<MetaRelation> relations;
	private ArrayList<Constraint> constraints;
	private String packageName;
	private String name;

	public MetaDataModel(String name, String packageName)
	{
		actions = new ArrayList<MetaMethod>();
		views = new ArrayList<MetaMethod>();
		kinds = new ArrayList<MetaKind>();
		relations = new ArrayList<MetaRelation>();
		singletons = new ArrayList<MetaSingleton>();
		constraints = null;
		this.name = name;
		this.packageName = packageName;
	}
	
	public void hookValueDomains(ValueDomainBuilder vdb)
	{
		
		actions.trimToSize();
		views.trimToSize();
		kinds.trimToSize();
		singletons.trimToSize();
		relations.trimToSize();
			
		for(MetaKind kind : kinds)
		{
			kind.hookValueDomains(vdb, name);
		}
		for(MetaSingleton singleton : singletons)
		{
			singleton.hookValueDomains(vdb, name);
		}
		for(MetaMethod method : actions)
		{
			method.hookValueDomains(vdb, name);
		}
		for(MetaMethod method : views)
		{
			method.hookValueDomains(vdb, name);
		}
	}
	
	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}

	@Override
	public String getPackage()
	{
		return packageName;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String getName()
	{
		return name;
	}

	public void addAction(MetaMethod action)
	{
		action.setArrayPosition(actions.size());
		actions.add(action);
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
	public int getNumberOfActions()
	{
		return actions.size();
	}

	@Override
	public IMetaMethod getAction(int i)
	{
		return actions.get(i);
	}

	@Override
	public IMetaMethod getAction(String name)
	{
		for(IMetaMethod a : actions)
		{
			if(a.getName().equals(name)) return a;
		}
		return null;
	}

	public void addView(MetaMethod view)
	{
		view.setArrayPosition(views.size());
		views.add(view);
	}

	@Override
	public int getNumberOfViews()
	{
		return views.size();
	}

	@Override
	public IMetaMethod getView(int i)
	{
		return views.get(i);
	}

	@Override
	public IMetaMethod getView(String name)
	{
		for(IMetaMethod v : views)
		{
			if(v.getName().equals(name)) return v;
		}
		return null;
	}

	public void addKind(MetaKind kind)
	{
		kind.setArrayPosition(kinds.size());
		kinds.add(kind);
	}
	
	public void addSingleton(MetaSingleton singleton)
	{
		singleton.setArrayPosition(singletons.size());
		singletons.add(singleton);
	}

	@Override
	public int getNumberOfKinds()
	{
		return kinds.size();
	}

	@Override
	public MetaKind getKind(int i)
	{
		return kinds.get(i);
	}

	@Override
	public IMetaKind getKind(String name)
	{
		for(IMetaKind k : kinds)
		{
			if(k.getName().equals(name)) return k;
		}
		return null;
	}

	public void addRelation(MetaRelation relation)
	{
		relation.setArrayPosition(relations.size());
		relations.add(relation);
	}

	@Override
	public int getNumberOfRelations()
	{
		return relations.size();
	}

	@Override
	public IMetaRelation getRelation(int i)
	{
		return relations.get(i);
	}

	@Override
	public IMetaRelation getRelation(String name)
	{
		for(IMetaRelation r : relations)
		{
			if(r.getName().equals(name)) return r;
		}
		return null;
	}

	@Override
	public int getNumberOfSingletons()
	{
		return singletons.size();
	}

	@Override
	public IMetaSingleton getSingleton(int i)
	{
		return singletons.get(i);
	}

	@Override
	public IMetaSingleton getSingleton(String name)
	{
		for (MetaSingleton sgt : singletons)
		{
			if (sgt.getName().equals(name))
				return sgt;
		}
		return null;
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
