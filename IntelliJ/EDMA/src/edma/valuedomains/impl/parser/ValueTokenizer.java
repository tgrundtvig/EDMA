package edma.valuedomains.impl.parser;

import edma.valuedomains.impl.parser.ValueToken.TokenType;

public class ValueTokenizer
{
	public static final char BEGIN = '(';
	public static final char END = ')';
	public static final char SEPERATOR = ',';
	private String s;
	private int curPos;
	private ValueToken nextToken;

	public ValueTokenizer(String s)
	{
		this.s = s;
		curPos = 0;
		nextToken = null;
	}
	
	public ValueToken getNextToken()
	{
		if(nextToken != null)
		{
			ValueToken res = nextToken;
			nextToken = null;
			return res;
		}
		while(curPos < s.length() && Character.isWhitespace(s.charAt(curPos))){++curPos;}
		if(curPos == s.length()) return new ValueToken(TokenType.EOF, null);
		char c = s.charAt(curPos);
		switch(c)
		{
			case BEGIN:
				++curPos;
				return new ValueToken(TokenType.Begin, null);
			case END:
				++curPos;
				return new ValueToken(TokenType.End, null);
			case SEPERATOR:
				++curPos;
				return new ValueToken(TokenType.Seperator, null);
			case '"':
				return new ValueToken(TokenType.String, parseString());
			default: 
				return new ValueToken(TokenType.Value, parseValue());
		}
	}
	
	public void takeBack(ValueToken token)
	{
		if(nextToken != null) throw new RuntimeException("Only one token can be taken back!!");
		nextToken = token;
	}
	
	private String parseString()
	{
		StringBuilder res = new StringBuilder();
		
		while(true)
		{
			++curPos;
			if(curPos == s.length()) throw new RuntimeException("Error parsing string at: " + curPos);
			char c = s.charAt(curPos);
			switch(c)
			{
				case '"':
					++curPos;
					return res.toString();
				case '\\':
					++curPos;
					if(curPos == s.length()) throw new RuntimeException("Error parsing string at pos : " + curPos);
					c = s.charAt(curPos);
					switch(c)
					{
						case 't':
							res.append('\t');
							break;
						case 'n':
							res.append('\n');
							break;
						case '\'':
							res.append('\'');
							break;
						case '"':
							res.append('"');
							break;
						case '\\':
							res.append('\\');
							break;
						default:
							throw new RuntimeException("Illegal escape: \\" + c + " at pos " + curPos);
					}
					break;
				default:
					res.append(c);
			}		
		}
	}
	
	private String parseValue()
	{
		int begin = curPos;
		int end = begin;
		while(true)
		{
			++curPos;
			if(curPos == s.length())
			{
				end = curPos;
				break;
			}
			char c = s.charAt(curPos);
			if(c == SEPERATOR || c == BEGIN || c == END || Character.isWhitespace(c))
			{
				end = curPos;
				break;
			}
		}
		return s.substring(begin, end);
	}
	
}
