package edma.runtime.implementations.common;

import edma.runtime.intf.IAction;
import edma.runtime.intf.IDataModelUpdate;

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
