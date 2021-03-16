package edma.runtime.implementations.common.transactions.atomic;

public interface IDeleteConnection extends IAtomicUpdate
{
	public int getRelationIndex();
	public Long getA();
	public Long getB();
}
