package edma.runtime.implementations.common;

import edma.runtime.intf.IDataModelView;
import edma.runtime.intf.IView;

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
