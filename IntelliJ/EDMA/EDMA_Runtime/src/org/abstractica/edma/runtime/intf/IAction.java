package org.abstractica.edma.runtime.intf;

public interface IAction
{
	public String getName();
	public boolean execute(IDataModelUpdate upd);
}
