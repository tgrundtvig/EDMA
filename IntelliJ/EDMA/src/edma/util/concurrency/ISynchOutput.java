package edma.util.concurrency;

import java.util.concurrent.TimeoutException;

public interface ISynchOutput<E>
{
	public E take() throws ClosedException, PausedException;
	public E take(long timeout) throws ClosedException, PausedException, TimeoutException;
	public E poll() throws ClosedException;
}
