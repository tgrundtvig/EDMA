package edma.util.concurrency;

public interface ISynchCounter
{
	public void modify(int i);
	public void waitFor(int i);
}
