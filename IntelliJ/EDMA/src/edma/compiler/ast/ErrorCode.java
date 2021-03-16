package edma.compiler.ast;

public class ErrorCode extends AstNode
{
	private int errorNumber;
	private String errorMessage;
	public ErrorCode(String fileName, int line, int i, String s)
	{
		super(fileName, line);
		errorNumber = i;
		if (s == null)
			errorMessage = "";
		else
			errorMessage = s;
	}
	
	public int getNumber() { return errorNumber; }
	public String getMessage() { return errorMessage; }
}
