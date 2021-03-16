package org.abstractica.edma.valuedomains.impl.parser;

public class ValueToken
{
	private TokenType type;
	private String value;
	
	public ValueToken(TokenType type, String value)
	{
		this.type = type;
		this.value = value;
	}

	public TokenType getType()
	{
		return type;
	}
	
	public String getValue()
	{
		return value;
	}
	
	
	@Override
	public String toString()
	{
		switch(type)
		{
			case String:
				return "String: \"" + value + "\"";
			case Value:
				return "Value: " + value;
			default:
				return type.toString();
		}
	}
	
	public enum TokenType {Begin, End, Seperator, String, Value, EOF}
	
	
}
