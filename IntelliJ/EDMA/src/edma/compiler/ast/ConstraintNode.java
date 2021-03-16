package edma.compiler.ast;

public class ConstraintNode extends NamedAstNode
{
	private String description;
	private String typeName;
	
	/**
	 * Creates a new constraint node
	 * @param line The line number
	 * @param typeName The name of the ValueDomain, Kind, Singleton or Relation with the constraint
	 * @param name The name of the constraint
	 */
	public ConstraintNode(String fileName, int line, String typeName, String name)
	{
		super(fileName, line, name);
		this.typeName = typeName;
	}
	
	/**
	 * Creates a new constraint node
	 * @param line The line number
	 * @param typeName The name of the ValueDomain, Kind, Singleton or Relation with the constraint
	 * @param name The name of the constraint
	 * @param description The description of the constraint
	 */
	public ConstraintNode(String fileName, int line, String typeName, String name, String description)
	{
		super(fileName, line, name);
		this.typeName = typeName;
		this.description = description;
	}
	
	public String getTypeName()
	{
		return typeName;
	}
	
	public String getDescription()
	{
		return description;
	}
	
//	public String getTypeName()
//	{
//		return typeName;
//	}
	
	public void print(String tabs)
	{
		System.out.print(tabs);
		System.out.print("Constraint " + typeName);
	}
}
