package edma.compiler.ast;

public class NameAndTypeNode extends AstNode
{
	private String name;
	private String identifier;
	private boolean canBeNull;

	public NameAndTypeNode(String filename, int line, String name, String identifier, boolean canBeNull)
	{
		super(filename, line);
		this.name = name;
		this.identifier = identifier;
		this.canBeNull = canBeNull;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getIdentifier()
	{
		return identifier;
	}
	
	public boolean canBeNull()
	{
		return canBeNull;
	}
	
	public String toString()
	{
		return name + (canBeNull ? "? : " : " : ") + identifier;
	}
}
