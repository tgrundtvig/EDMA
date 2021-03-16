package edma.runtime.implementations.common.transactions;

public interface ITransactionOperations extends IAtomicOperations
{
	public void beginTransaction(String name, long fromVersion);
	public void endTransaction(long toVersion);
}
