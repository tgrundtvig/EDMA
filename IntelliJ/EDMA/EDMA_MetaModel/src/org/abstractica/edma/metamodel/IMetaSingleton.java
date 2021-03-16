package org.abstractica.edma.metamodel;

import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.impl.Constraint;

public interface IMetaSingleton
{
	//Basics
	public String getName();
	public int getArrayPosition();
	public String publishAs();
	public IMetaValueDomain getValueDomain();
	
	//Attributes
	public int getNumberOfAttributes();
	public IMetaAttribute getAttribute(int i);
	public IMetaAttribute getAttribute(String name);
	
	//Constraints
	public int getNumberOfConstraints();
	public Constraint getConstraint(int i);
}
