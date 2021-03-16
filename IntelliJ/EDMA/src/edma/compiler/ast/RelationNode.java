package edma.compiler.ast;

import java.util.ArrayList;
import java.util.Collection;

public class RelationNode extends NamedAstNode
{
	private String kindA, roleA, kindB, roleB;
	private int relationType;
	private ArrayList<ConstraintNode> constraints;
	private ArrayList<IndexNode> indexes;

	public RelationNode(String fileName,
	                    int line,
	                    String name,
						String kindA,
						String roleA,
						String kindB,
						String roleB,
						int relationType)
	{
		super(fileName, line, name);
		this.kindA = kindA;
		this.roleA = roleA;
		this.kindB = kindB;
		this.roleB = roleB;
		this.relationType = relationType;
	}
	
	public String getKindA() {return kindA;}
	public String getKindB() {return kindB;}
	public String getRoleA() {return roleA;}
	public String getRoleB() {return roleB;}
	
	public int getRelationType() {return relationType;}
	
	public String getRelationTypeAsString()
	{
		switch (relationType)
		{
			case 0: return "---";
			case 1: return ">--";
			case 2: return ">-<";
		}
		return null;
	}
	
	public void addConstraint(ConstraintNode cn)
	{
		getConstraints().add(cn);
	}
	
	public void addIndex(IndexNode in)
	{
		getIndexes().add(in);
	}
	
	public Collection<ConstraintNode> getConstraints()
	{
		if (constraints == null)
			constraints = new ArrayList<ConstraintNode>();
		return constraints;
	}
	
	public Collection<IndexNode> getIndexes()
	{
		if (indexes == null)
			indexes = new ArrayList<IndexNode>();
		return indexes;
	}

	public void print(String tabs)
	{
		System.out.print(tabs + "Relation " + name + " " + kindA + " ");
		if(roleA != null && !roleA.equals("")) System.out.print(": " + roleA
				+ " ");
		if(relationType == 0) System.out.print("--- ");
		else if(relationType == 1) System.out.print(">-- ");
		else if(relationType == 2) System.out.print(">-< ");
		System.out.print(kindB);
		if(roleB != null && !roleB.equals("")) System.out.println(" : " + roleB);
		else System.out.print("\n");
	}
}
