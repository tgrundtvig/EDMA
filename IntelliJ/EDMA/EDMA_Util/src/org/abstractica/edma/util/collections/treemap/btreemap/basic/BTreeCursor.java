package org.abstractica.edma.util.collections.treemap.btreemap.basic;


public class BTreeCursor
{
	public BTreeMapImpl btree;
	public long curNodeID;
	public int index;
	public int expectedModCount;
	public Object key;
	public Object value;
	

	public BTreeCursor(){}
	
	public void clone(BTreeCursor cursor)
	{
		this.btree = cursor.btree;
		this.curNodeID = cursor.curNodeID;
		this.index = cursor.index;
		this.expectedModCount = cursor.expectedModCount;
		this.key = cursor.key;
		this.value = cursor.value;
	}

	public boolean moveNext()
	{
		return moveNext(null, false);
	}

	public boolean movePrev()
	{
		return movePrev(null, false);
	}
	
	public boolean moveNext(Object upperBound, boolean inclusive)
	{
		BTreeLeafNode curNode = btree.loadLeafNode(curNodeID);
		boolean res;
		if(curNode == null)
		{
			res = btree.setCursorHigher(this, key);
			if(res && upperBound != null)
			{
				int c = btree.getComparator().compare(key, upperBound);
				if(inclusive) res = (c <= 0);
				else res = (c < 0);
			}
		}
		else
		{
			res = curNode.moveCursorNext(this, upperBound, inclusive);
			btree.releaseNode(curNode);
		}
		
		return res;
	}

	public boolean movePrev(Object lowerBound, boolean inclusive)
	{
		BTreeLeafNode curNode = btree.loadLeafNode(curNodeID);
		boolean res;
		if(curNode == null)
		{
			res = btree.setCursorLower(this, key);
			if(res && lowerBound != null)
			{
				int c = btree.getComparator().compare(key, lowerBound);
				if(inclusive) res = (c >= 0);
				else res = (c > 0);
			}
		}
		else
		{
			res = curNode.moveCursorPrev(this, lowerBound, inclusive);
			btree.releaseNode(curNode);
		}
		return res;
	}
	
	public Object getCurrentKey()
	{
		return key;
	}
	
	public Object getCurrentValue()
	{
		return value;
	}

	public boolean equals(Object o)
	{
		if(!(o instanceof BTreeCursor)) return false;
		BTreeCursor e = (BTreeCursor) o;
		return eq(key, e.key) && eq(value, e.value);
	}

	public int hashCode()
	{
		return (key == null ? 0 : key.hashCode())
				^ (value == null ? 0 : value.hashCode());
	}
	
	public String toString()
	{
		return "(" + key + ", " + value + ")";
	}
	
	public BTreeMapImpl getTree()
	{
		return btree;
	}

	private static boolean eq(Object o1, Object o2)
	{
		return o1 == null ? o2 == null : o1.equals(o2);
	}
}
