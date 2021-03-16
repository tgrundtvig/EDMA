package org.abstractica.edma.compiler.ast;

public class AstNode
{
	protected int line;
	protected String fileName;
	
	public AstNode(String fileName, int line)
	{
		this.fileName = fileName;
		this.line = line;
	}
	
	public int getLine() 
	{
		return line;
	}
	
	public String getFileName()
	{
		return fileName;
	}
}
