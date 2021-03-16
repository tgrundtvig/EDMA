package edma.compiler.ast;

public class NamedAstNode extends AstNode
{
	protected String name;

	public NamedAstNode(String fileName, int line, String name)
	{
		super(fileName, line);
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
}
