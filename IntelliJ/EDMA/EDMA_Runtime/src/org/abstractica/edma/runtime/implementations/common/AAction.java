package org.abstractica.edma.runtime.implementations.common;

import org.abstractica.edma.runtime.intf.IAction;
import org.abstractica.edma.runtime.intf.IDataModelUpdate;

public abstract class AAction implements IAction
{
	private String name;
	
	public AAction(String name)
	{
		this.name = name;
	}
	
	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public abstract boolean execute(IDataModelUpdate upd);
}
