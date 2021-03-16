package org.abstractica.edma.util;

import java.io.PrintStream;

public class ErrorLog
{
	private static PrintStream out = System.out;
	
	public static void logError(String errorMsg)
	{
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		
		for(int i = 2; i < elements.length; ++i)
		{
			StackTraceElement elem = elements[i];
			out.println(elem.getMethodName() + " " + elem.getClassName());
		}
		out.println(errorMsg);
	}
	
	public static void logException(Exception e)
	{
		logException(null, e);
	}
	
	public static void logException(String description, Exception e)
	{
		out.println("*************************************************************");
		out.println("Class: " + e.getClass().getSimpleName());
		out.println("Message: " + e.getMessage());
		out.println("Cause: " + e.getCause());
	}
	
	public static String getStackTraceAsString(Exception e)
	{
		StringBuilder errorMsg = new StringBuilder();
		for(StackTraceElement elem : e.getStackTrace())
		{
			errorMsg.append(elem.toString());
			errorMsg.append("\n");
		}
		return errorMsg.toString();
	}
	
	public static void setErrorStream(PrintStream outStream)
	{
		out = outStream;
	}
	
	public static void main(String[] args)
	{
		myMethod();
	}
	
	public static void myMethod()
	{
		logError("MyErrorMessage");
		Exception e = new Exception("Exception message");
		logException(e);
	}
}
