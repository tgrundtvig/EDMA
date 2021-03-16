package org.abstractica.edma.runtime.implementations.mem.sets.simple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

import org.abstractica.edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.DNavigableSet;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.DSet;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.Deletable;
import org.abstractica.edma.runtime.implementations.mem.sets.ISetManager;

public class SimpleSetManager implements ISetManager
{
	private static final Set<Long> emptySet = Collections.emptySet();
	private ArrayList<SimpleSet> sets;
	private ArrayList<Deletable> managedSets;
	private CollectionFactory cf;
	
	public SimpleSetManager(CollectionFactory cf)
	{
		this.cf = cf;
		sets = new ArrayList<>();
		managedSets = new ArrayList<>();
	}
	
	@Override
	public int emptySet()
	{
		return -1;
	}

	@Override
	public int newSet(Set<Long> set, boolean doCopy)
	{
		if(set == null || set.size() == 0) return -1;
		if(doCopy)
		{
			DSet<Long> copy = cf.newSet();
			copy.addAll(set);
			managedSets.add(copy);
			return addSet(new SimpleSet(copy, null, false));
		}
		return addSet(new SimpleSet(set, null, false));
	}
	
	@Override
	public int newSet2(Collection<Long> Ids)
	{
		DSet<Long> set = cf.newSet();
		set.addAll(Ids);
		managedSets.add(set);
		return addSet(new SimpleSet(set, null, false));
	}
	
	@Override
	public int transferSet(DSet<Long> set)
	{
		managedSets.add(set);
		return addSet(new SimpleSet(set, null, false));
	}

	@Override
	public int union(int setA, int setB)
	{
		if(setA == setB || setB == -1) return setA;
		if(setA == -1) return setB;
		SimpleSet a = sets.get(setA);
		SimpleSet b = sets.get(setB);
		DSet<Long> tmp = cf.newSet();
		managedSets.add(tmp);
		tmp.addAll(a.set);
		tmp.addAll(b.set);
		return addSet(new SimpleSet(tmp, null, false));
	}

	@Override
	public int intersection(int setA, int setB)
	{
		if(setA == setB) return setA;
		if(setA == -1 || setB == -1) return -1;
		Set<Long> a = sets.get(setA).set;
		Set<Long> b = sets.get(setB).set;
		if(a.size() > b.size())
		{
			Set<Long> tmp = a;
			a = b;
			b = tmp;
		}
		DSet<Long> res = cf.newSet();
		managedSets.add(res);
		for(Long id : a)
		{
			if(b.contains(id)) res.add(id);
		}
		if(res.size() == 0)
		{
			return -1;
		}
		return addSet(new SimpleSet(res, null, false));
	}

	@Override
	public int subtraction(int setA, int setB)
	{
		if(setA == setB || setA == -1) return -1;
		if(setB == -1) return setA;
		Set<Long> a = sets.get(setA).set;
		Set<Long> b = sets.get(setB).set;
		DSet<Long> res = cf.newSet();
		managedSets.add(res);
		for(Long id : a)
		{
			if(!b.contains(id)) res.add(id);
		}
		if(res.size() == 0)
		{
			return -1;
		}
		return addSet(new SimpleSet(res, null, false));
	}

	@Override
	public int order(int setID, Comparator<Long> comparator)
	{
		if(setID == -1) return -1;
		SimpleSet set = sets.get(setID);
		return addSet(new SimpleSet(set.set, comparator, false));
	}
	
	@Override
	public Iterator<Long> getIterator(int setID)
	{
		if(setID == -1) return emptySet.iterator();
		SimpleSet set = sets.get(setID);
		if(set.comp != null && !set.isOrdered)
		{
			DNavigableSet<Long> tmp = cf.newNavigableSet(set.comp);
			tmp.addAll(set.set);
			managedSets.add(tmp);
			set.set = tmp;
			set.isOrdered = true;
		}
		return set.set.iterator();
	}

	@Override
	public int getSize(int setID)
	{
		if(setID == -1) return 0;
		SimpleSet set = sets.get(setID);
		return set.set.size();
	}

	@Override
	public boolean contains(int setID, Long id)
	{
		if(setID == -1) return false;
		SimpleSet set = sets.get(setID);
		return set.set.contains(id);
	}
	
	@Override
	public boolean containsAll(int setIDA, int setIDB)
	{
		if(setIDB == -1) return true;
		if(setIDA == -1) return false;
		SimpleSet setA = sets.get(setIDA);
		SimpleSet setB = sets.get(setIDB);
		return setA.set.containsAll(setB.set);
	}
	
	private int addSet(SimpleSet set)
	{
		int res = sets.size();
		sets.add(set);
		return res;
	}

	@Override
	public void release()
	{
		for(Deletable set : managedSets)
		{
			set.delete();
		}
	}
}
