package edma.util.memoryuse;
public class MemAnalyzer
{
	private long res;
	
	public void start()
	{
		res = getMemoryUse();
	}
	
	public void stop()
	{
		res = getMemoryUse() - res;
	}
	
	public long getResult()
	{
		return res;
	}
	
	private static long getMemoryUse()
	{
		putOutTheGarbage();
		long totalMemory = Runtime.getRuntime().totalMemory();

		putOutTheGarbage();
		long freeMemory = Runtime.getRuntime().freeMemory();

		return (totalMemory - freeMemory);
	}

	private static void putOutTheGarbage()
	{
		collectGarbage();
		collectGarbage();
	}

	private static void collectGarbage()
	{
		try
		{
			System.gc();
			Thread.sleep(100);
			System.runFinalization();
			Thread.sleep(100);
		}
		catch(InterruptedException ex)
		{
			ex.printStackTrace();
		}
	}
}
