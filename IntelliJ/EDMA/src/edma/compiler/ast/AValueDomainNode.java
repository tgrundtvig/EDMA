package edma.compiler.ast;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AValueDomainNode extends NamedAstNode
{
	private String scope;
	private Collection<ConstraintNode> constraints;

	public enum Type
	{
		Struct, List, Single, Enum, Map, OneOf
	}
	
	public AValueDomainNode(String fileName, int line, String name, String scope)
	{
		super(fileName, line, name);
		this.scope = scope;
	}
	
	public abstract Type getType();
	
	public abstract void print(String tabs);

	public String getScope()
	{
		return scope;
	}
	
	public void setName(String s)
	{
		this.name = s;
	}

	public void addConstraint(ConstraintNode c)
	{
		getConstraints().add(c);
	}

	public Collection<ConstraintNode> getConstraints()
	{
		if (constraints == null)
			constraints = new ArrayList<ConstraintNode>(5);
		return constraints;
	}
}
