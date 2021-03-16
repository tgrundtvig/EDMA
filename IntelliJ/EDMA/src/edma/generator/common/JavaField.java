package edma.generator.common;


public class JavaField
{
	private String scope;
	private String type;
	private String name;
	private boolean isStatic;
	private boolean isFinal;
	private String initial;
	
	public JavaField(String type, String name)
    {
	    this.scope = "private";
	    this.type = type;
	    this.name = name;
	    this.isStatic = false;
    }
	
	public JavaField(String scope, String type, String name)
    {
	    this.scope = scope;
	    this.type = type;
	    this.name = name;
	    this.isStatic = false;
    }
	
	public JavaField(String scope, boolean isStatic, boolean isFinal, String type, String name, String initial)
    {
	    this.scope = scope;
	    this.type = type;
	    this.name = name;
	    this.isStatic = isStatic;
	    this.isFinal = isFinal;
	    this.initial = initial;
    }
	
	public String getInitial()
	{
		return initial;
	}
	
	public boolean isStatic()
	{
		return isStatic;
	}
	
	public boolean isFinal()
	{
		return isFinal;
	}
	
	public String getScope()
    {
    	return scope;
    }

	public String getType()
    {
    	return type;
    }

	public String getName()
    {
    	return name;
    }
	
	public String toString(String indent)
	{
		StringBuilder res = new StringBuilder();
		
		res.append(indent);
		res.append(scope);
		res.append(" ");
		res.append(type);
		res.append(" ");
		res.append(name);
		res.append(";\n");
		return res.toString();
	}
	
}
