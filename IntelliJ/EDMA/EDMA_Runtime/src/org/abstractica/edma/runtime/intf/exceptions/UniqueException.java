package org.abstractica.edma.runtime.intf.exceptions;

import org.abstractica.edma.metamodel.IMetaIndex;

public class UniqueException extends Exception
{
	private IMetaIndex violatedIndex;

	public UniqueException(IMetaIndex violatedIndex)
	{
		this.violatedIndex = violatedIndex;
	}
	
	public IMetaIndex getViolatedIndex()
	{
		return violatedIndex;
	}
	
	public String[] getViolatedAttributes()
	{
		String[] res = new String[violatedIndex.getNumberOfAttributes()];
		for(int i = 0; i < res.length; ++i)
		{
			res[i] = violatedIndex.getAttribute(i).getName();
		}
		return res;
	}
	
	public String getDescription()
	{
		StringBuilder res = new StringBuilder();
		int size = violatedIndex.getNumberOfAttributes();
		res.append("Unique index on ");
		if(size > 1) res.append("(");
		for(int i = 0; i < size; ++i)
		{
			if(i > 0) res.append(", ");
			res.append(violatedIndex.getAttribute(i).getName());
		}
		if(size > 1) res.append(")");
		res.append(" has been violated!");
		return res.toString();
	}
	
}
