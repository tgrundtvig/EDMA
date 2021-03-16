package edma.compiler.ast;

import java.util.ArrayList;

public class ActionViewNode extends NamedAstNode
{
	public enum Type
	{
		Action, View
	}

	private ArrayList<NameAndTypeNode> input, output;
	private String description;
	private Type type;
	private ArrayList<ErrorCode> errorCodes;

	/**
	 * 
	 * @param line
	 * @param name
	 * @param input
	 *            Leave this null for void
	 * @param output
	 *            Leave this null for void
	 * @param description
	 * @param type
	 * @param errorCodes
	 * 			  This may be null
	 */
	public ActionViewNode(	String fileName,
							int line,
							String name,
							ArrayList<NameAndTypeNode> input,
							ArrayList<NameAndTypeNode> output,
							String description,
							Type type,
							ArrayList<ErrorCode> errorCodes)
	{
		super(fileName, line, name);
		this.type = type;
		this.input = input;
		this.output = output;
		this.description = description;
		this.errorCodes = errorCodes;
	}
	
	public int getErrorNumber(int i) 
	{
		return errorCodes.get(i).getNumber();
	}
	
	public String getErrorMessage(int i)
	{
		return errorCodes.get(i).getMessage();
	}

	public boolean isAction()
	{
		return type == Type.Action;
	}

	public boolean isView()
	{
		return type == Type.View;
	}

	public String getDescription()
	{
		return description;
	}

	public ArrayList<NameAndTypeNode> getInput()
	{
		return input;
	}

	public ArrayList<NameAndTypeNode> getOutput()
	{
		return output;
	}
	
	public ArrayList<ErrorCode> getErrorCodes()
	{
		return errorCodes;
	}

	public Type getType()
	{
		return type;
	}

	public void print(String tabs)
	{
		System.out.println("\"" + description + "\"");
		System.out.print(tabs + (isAction() ? "Action " : "View "));
		System.out.println(name + "(" + input + " -> " + output + ")");
	}
}
