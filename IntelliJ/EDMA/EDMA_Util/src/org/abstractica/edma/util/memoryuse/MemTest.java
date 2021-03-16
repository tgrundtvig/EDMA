package org.abstractica.edma.util.memoryuse;

public class MemTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("System: " + System.getProperty("sun.arch.data.model"));
		MemAnalyzer ma = new MemAnalyzer();
		Integer foo = 3;
		Object v1;
		Object v2;
		Long lfoo = 56L;
		ma.start();
		//String[] array = new String[12];
		Long l = 23L;
		ma.stop();
		System.out.println("Mem used: " + ma.getResult());
		//System.out.println("MyInt: " + myInt1);
		//System.out.println("MyInt: " + myInt2);
	}

}
