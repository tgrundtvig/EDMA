package edma.valuedomains.userinput;

public abstract class ATerminal implements ITerminal
{
	@Override
	public int getInt(int min, int max)
	{
		while(true)
		{
			try
			{
				String val = getString();
				int res = Integer.parseInt(val);
				if(res >= min && res <= max) return res;
			}
			catch(NumberFormatException e)
			{

			}
			put("You must enter an integer in the range [" + min + ", " + max
					+ "] :");
		}
	}

	@Override
	public int getInt()
	{
		while(true)
		{
			try
			{
				String val = getString();
				return Integer.parseInt(val);
			}
			catch(NumberFormatException e)
			{
				put("You must enter an integer :");
			}
		}
	}

	@Override
	public long getLong()
	{
		while(true)
		{
			try
			{
				String val = getString();
				return Long.parseLong(val);
			}
			catch(NumberFormatException e)
			{
				put("You must enter a long :");
			}
		}
	}

	@Override
	public float getFloat()
	{
		while(true)
		{
			try
			{
				String val = getString();
				return Float.parseFloat(val);
			}
			catch(NumberFormatException e)
			{
				put("You must enter a float :");
			}
		}
	}

	@Override
	public double getDouble()
	{
		while(true)
		{
			try
			{
				String val = getString();
				return Double.parseDouble(val);
			}
			catch(NumberFormatException e)
			{
				put("You must enter a double :");
			}
		}
	}

	@Override
	public boolean getYesNo()
	{
		while(true)
		{
			String s = getString();
			if("y".equalsIgnoreCase(s) || "yes".equalsIgnoreCase(s))
			{
				return true;
			}
			else if("n".equalsIgnoreCase(s)
					|| "no".equalsIgnoreCase(s))
			{
				return false;
			}
			else
			{
				put("Please try again: ");
			}
		}
	}

	@Override
	public boolean getBoolean()
	{
		while(true)
		{
			String s = getString();
			if("t".equalsIgnoreCase(s) || "true".equalsIgnoreCase(s))
			{
				return true;
			}
			else if("f".equalsIgnoreCase(s)
					|| "false".equalsIgnoreCase(s))
			{
				return false;
			}
			else
			{
				put("Please try again: ");
			}
		}
	}
}
