package edma.util.binarysearch;

public class BinarySearchLong
{
	public static int getIndexEqual(CompareFunctionLong comparator,
									long key,
									long[] array,
									int begin,
									int end)
	{
		int c = comparator.compare(key, array[begin]);
		if(c == 0) return begin;
		if(c < 0) return -1;

		c = comparator.compare(key, array[end]);
		if(c == 0) return end;
		if(c > 0) return -1;

		int lower = begin + 1;
		int upper = end - 1;
		while(lower <= upper)
		{
			int middle = (lower + upper) / 2;
			c = comparator.compare(key, array[middle]);
			if(c == 0) return middle; // key equals array[middle]
			if(c < 0)
			{
				// key < array[middle]
				upper = middle - 1;
			}
			else
			{
				// key > array[middle]
				lower = middle + 1;
			}
		}
		return -1;
	}

	public static int getIndexLower(CompareFunctionLong comparator,
									long key,
									long[] array,
									int begin,
									int end)
	{
		int c = comparator.compare(key, array[begin]);
		if(c <= 0) return -1; // key <= array[begin]

		int lower = begin;
		int upper = end;
		int middle;

		// binary search
		while(true)
		{
			int tmp = lower + upper;
			middle = (tmp / 2) + (tmp % 2);
			c = comparator.compare(key, array[middle]);
			if(c > 0)
			{
				// key > middle
				lower = middle;
			}
			else
			{
				// key <= middle
				upper = middle - 1;
			}
			if(lower >= upper){ return lower; }
		}
	}

	public static int getIndexFloor(CompareFunctionLong comparator,
									long key,
									long[] array,
									int begin,
									int end)
	{
		int c = comparator.compare(key, array[begin]);
		if(c == 0) return begin; // key equals array[begin]
		if(c < 0) return -1; // key < array[begin]

		int lower = begin;
		int upper = end;
		int middle;

		// binary search
		while(true)
		{
			int tmp = lower + upper;
			middle = (tmp / 2) + (tmp % 2);
			c = comparator.compare(key, array[middle]);
			if(c == 0) return middle; // key equals middle
			if(c > 0)
			{
				// key > middle
				lower = middle;
			}
			else
			{
				// key <= middle
				upper = middle - 1;
			}
			if(lower >= upper){ return lower; }
		}
	}

	public static int getIndexHigher(	CompareFunctionLong comparator,
										long key,
										long[] array,
										int begin,
										int end)
	{
		int c = comparator.compare(key, array[end]);
		if(c >= 0) return -1; // key >= array[end]

		int lower = begin;
		int upper = end;
		int middle;

		// binary search
		while(true)
		{
			middle = (lower + upper) / 2;
			c = comparator.compare(key, array[middle]);
			if(c < 0)
			{
				// key < middle
				upper = middle;
			}
			else
			{
				// key >= middle
				lower = middle + 1;
			}
			if(lower >= upper){ return upper; }
		}
	}

	public static int getIndexCeiling(	CompareFunctionLong comparator,
										long key,
										long[] array,
										int begin,
										int end)
	{
		int c = comparator.compare(key, array[end]);
		if(c == 0) return end; // key equals array[end]
		if(c > 0) return -1; // key > array[end]

		int lower = begin;
		int upper = end;
		int middle;

		// binary search
		while(true)
		{
			middle = (lower + upper) / 2;
			c = comparator.compare(key, array[middle]);
			if(c == 0) return middle; // key equals middle
			if(c < 0)
			{
				// key < middle
				upper = middle;
			}
			else
			{
				// key > middle
				lower = middle + 1;
			}
			if(lower >= upper){ return upper; }
		}
	}
}
