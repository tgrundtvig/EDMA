package edma.runtime.implementations.common.transactions.atomic;


public interface IAtomicUpdate
{
	public enum UpdateType
	{
		NewEntity, DeleteEntity, UpdateEntity, NewConnection, DeleteConnection, UpdateSingleton
	}

	public UpdateType getType();

	public INewEntity asNewEntity();

	public IDeleteEntity asDeleteEntity();

	public IUpdateEntity asUpdateEntity();

	public INewConnection asNewConnection();

	public IDeleteConnection asDeleteConnection();

	public IUpdateSingleton asUpdateSingleton();

	public IAtomicUpdate getInverse();
}
