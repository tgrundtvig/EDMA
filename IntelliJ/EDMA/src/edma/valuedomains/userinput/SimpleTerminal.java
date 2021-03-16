package edma.valuedomains.userinput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimpleTerminal extends ATerminal
{
	private BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	
	@Override
	public void put(String s)
	{
		System.out.print(s);
		System.out.flush();
	}

	@Override
	public String getString()
	{
		String res;
		try
		{
			res = stdin.readLine();
			return res;
		}
		catch(IOException e)
		{
			throw new RuntimeException("Could not get input!!");
		}
	}
}
