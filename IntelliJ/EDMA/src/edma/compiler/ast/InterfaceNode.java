package edma.compiler.ast;

import java.util.ArrayList;
import java.util.Collection;

public class InterfaceNode extends NamedAstNode
{
	private ArrayList<String> methods;
	
	public InterfaceNode(String fileName, int line, String name)
	{
		super(fileName, line, name);
		
	}
	
	public Collection<String> getActionViewNodes()
	{
		if (methods == null)
			methods = new ArrayList<String>();
		return methods;
	}
	
	public void addActionViewNodeName(String identifier)
	{
		getActionViewNodes().add(identifier);
	}
}
