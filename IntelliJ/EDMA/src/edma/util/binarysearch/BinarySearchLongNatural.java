package edma.util.binarysearch;

public class BinarySearchLongNatural
{
	public static int getIndexEqual(long key, long[] array, int begin, int end)
	{
		long val = array[begin];
		if(key < val) return -1;
		if(key == val) return begin;

		val = array[end];
		if(key > val) return -1;
		if(key == val) return end;

		int lower = begin + 1;
		int upper = end - 1;
		while(lower <= upper)
		{
			int middle = (lower + upper) / 2;
			val = array[middle];
			if(key == val) return middle; // key equals array[middle]
			if(key < val)
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

	public static int getIndexLower(long key, long[] array, int begin, int end)
	{
		if(key <= array[begin]) return -1; // key <= array[begin]
		int lower = begin;
		int upper = end;
		int middle;

		// binary search
		while(true)
		{
			int tmp = lower + upper;
			middle = (tmp / 2) + (tmp % 2);
			if(key > array[middle])
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

	public static int getIndexFloor(long key, long[] array, int begin, int end)
	{
		long val = array[begin];
		if(key < val) return -1; // key < array[begin]
		if(key == val) return begin; // key equals array[begin]

		int lower = begin;
		int upper = end;
		int middle;

		// binary search
		while(true)
		{
			int tmp = lower + upper;
			middle = (tmp / 2) + (tmp % 2);
			val = array[middle];
			if(key == val) return middle; // key equals middle
			if(key > val)
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

	public static int getIndexHigher(long key, long[] array, int begin, int end)
	{
		if(key >= array[end]) return -1; // key >= array[end]

		int lower = begin;
		int upper = end;
		int middle;

		// binary search
		while(true)
		{
			middle = (lower + upper) / 2;
			if(key < array[middle])
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

	public static int getIndexCeiling(long key, long[] array, int begin, int end)
	{
		long val = array[end];
		if(key == val) return end; // key equals array[end]
		if(key > val) return -1; // key > array[end]

		int lower = begin;
		int upper = end;
		int middle;

		// binary search
		while(true)
		{
			middle = (lower + upper) / 2;
			val = array[middle];
			if(key == val) return middle; // key equals middle
			if(key < val)
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
