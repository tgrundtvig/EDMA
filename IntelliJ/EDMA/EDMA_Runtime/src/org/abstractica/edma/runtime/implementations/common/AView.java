package org.abstractica.edma.runtime.implementations.common;

import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IView;

public abstract class AView implements IView
{
	private String name;
	
	public AView(String name)
	{
		this.name = name;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public abstract void execute(IDataModelView view);
}
