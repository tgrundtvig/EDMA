package edma.util;

public class Eq
{
	public static boolean eq(Object a, Object b)
	{
		if(a == b) return true;
		if(a == null || b == null) return false;
		return a.equals(b);
	}
}
