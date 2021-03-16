package org.abstractica.edma.runtime.implementations.mem.sets.optimized;


public interface ISet extends Comparable<ISet>
{
	public enum SetType
	{
		Real, Union, Intersection, Subtraction
	}
	
	public int getID();

	public SetType type();
	
	public IUnion asUnion();
	
	public IIntersection asIntersection();
	
	public ISubtraction asSubtraction();

	public IRealSet toReal();
	
	public int getMaxSize();
}
