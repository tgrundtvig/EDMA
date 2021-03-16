package edma.runtime.implementations.common.transactions.atomic;

public interface IUpdateSingleton extends IAtomicUpdate
{
	public int getSingletonIndex();
	public int getAttributeIndex();
	public Object getOldValue();
	public Object getNewValue();
}
