package edma.compiler.ast;

public class ValueDomainMap extends AValueDomainNode
{
	private String keyType;
	private String valueType;

	public ValueDomainMap( String fileName,
	                       int line,
	                       String name,
	                       String keyType,
	                       String valueType,
	                       String scope)
	{
		super(fileName, line, name, scope);
		this.keyType = keyType;
		this.valueType = valueType;
	}
	
	@Override
	public Type getType()
	{
		return Type.Map;
	}
	
	@Override
	public void print(String tabs)
	{
		System.out.println(tabs + "Map<" + keyType + ", " + valueType+">");
	}
	
	public String getKey()
	{
		return keyType;
	}
	
	public String getValue()
	{
		return valueType;
	}
}
