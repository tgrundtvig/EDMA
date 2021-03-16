package edma.valuedomains.userinput;

public interface ITerminal
{
	public void put(String s);
	public String getString();
	public int getInt(int min, int max);
	public int getInt();
	public long getLong();
	public float getFloat();
	public double getDouble();
	public boolean getYesNo();
	public boolean getBoolean();
}
