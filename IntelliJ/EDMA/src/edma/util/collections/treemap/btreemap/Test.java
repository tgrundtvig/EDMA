package edma.util.collections.treemap.btreemap;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import edma.util.cache.DataStore;
import edma.util.cache.SimpleDiskDataStore;
import edma.util.collections.treemap.btreemap.basic.BTreeCache;
import edma.util.collections.treemap.btreemap.basic.BTreeCursor;
import edma.util.collections.treemap.btreemap.basic.BTreeManager;
import edma.util.collections.treemap.btreemap.basic.BTreeMapImpl;
import edma.util.serialize.Serializer;

public class Test
{

	/**
	 * @param args
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args)
	{
		DataStore dataStore = new SimpleDiskDataStore("C:/tmp/cache");
		BTreeCache btreeCache = new BTreeCache(dataStore, -1, -1, -1, 1024, 1024);
		BTreeManager manager = new BTreeManager(btreeCache);
		BTreeMapImpl t = manager.createNewTreeMap(	true,
													(Comparator) new IntegerComparator(),
													(Serializer) new IntegerSerializer(),
													(Serializer) new StringSerializer());
		int count = 1000000;
		System.out.println("Start creating test list");
		List<Integer> testList = new ArrayList<>(count);
		for(int i = 1; i <= count; ++i)
		{
			testList.add(i);
		}
		System.out.println("Done creating test list");
		Collections.shuffle(testList, new Random(0));
		int added = 0;
		System.out.println("Start adding...");
		for(Integer i : testList)
		{
			t.put(i, "Added " + (++added));
			// System.out.println(t);
		}
		System.out.println("Done adding!");

		int start = 5;
		boolean startInclusive = true;
		int end = 88;
		boolean endInclusive = true;
		int actual = t.subMapSize(start, startInclusive, end, endInclusive);
		System.out.println("SubmapSize of (" + start + " to " + end
				+ ") expected: " + (end - start + 1) + " Actual: " + actual);

		BTreeCursor cursor = new BTreeCursor();
		if(t.setCursorCeiling(cursor, 32))
		{
			do
			{
				System.out.println("(" + cursor.getCurrentKey() + ", "
						+ cursor.getCurrentValue() + ")");
			}
			while(cursor.moveNext(64, true));
		}

		System.out.println("Start random get...");
		Collections.shuffle(testList, new Random(0));
		for(Integer i : testList)
		{
			String val = (String) t.getValue(i);
		}
		System.out.println("Finished random get...");
		/*
		 * 
		 * System.out.println(t);
		 * 
		 * for(int i = 0; i < 100; ++i) { t.delete(testList.get(i)); }
		 * 
		 * t.clear(); System.out.println(t);
		 */
	}

	public static class IntegerComparator implements Comparator<Integer>
	{
		@Override
		public int compare(Integer k, Integer e)
		{
			return k.compareTo(e);
		}

	}

	public static class IntegerSerializer implements Serializer<Integer>
	{
		@Override
		public Integer readFromStream(DataInput in) throws IOException
		{
			return in.readInt();
		}

		@Override
		public void writeToStream(Integer t, DataOutput out) throws IOException
		{
			out.writeInt(t);
		}
	}

	public static class StringSerializer implements Serializer<String>
	{
		@Override
		public String readFromStream(DataInput in) throws IOException
		{
			return in.readUTF();
		}

		@Override
		public void writeToStream(String t, DataOutput out) throws IOException
		{
			out.writeUTF(t);
		}

	}
}
