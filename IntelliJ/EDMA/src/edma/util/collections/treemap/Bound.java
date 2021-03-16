package edma.util.collections.treemap;

public class Bound<K>
{
	public final K key;
	public final boolean inclusive;

	public Bound(K key, boolean inclusive)
	{
		super();
		this.key = key;
		this.inclusive = inclusive;
	}
}
