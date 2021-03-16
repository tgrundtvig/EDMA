package org.abstractica.edma.util;

public class StringUtil
{
	
	public static String adjust(String input, int maxWidth)
	{
		if(input == null) return "";
		int curLine = 0;
		StringBuilder res = new StringBuilder();
		String[] lines = input.split("\\n");
		for(String line : lines)
		{
			if(line.length() <= maxWidth)
			{
				res.append(line);
				res.append("\n");
			}
			else
			{
				curLine = 0;
				String[] words = line.split("\\s");
				for(String word : words)
				{
					if((curLine + word.length() + 1) <= maxWidth)
					{
						if(curLine > 0)
						{
							res.append(" ");
						}
						res.append(word);
						curLine += 1 + word.length();
					}
					else
					{
						res.append("\n");
						res.append(word);
						curLine = word.length();
					}
				}
				res.append("\n");
			}
		}
		return res.toString();
	}
	
	public static String indent(String indent, String s)
	{
		StringBuilder res = new StringBuilder();
		String[] lines = s.split("\n");
		for (String line : lines)
		{
			res.append(indent);
			res.append(line);
			res.append("\n");
		}
		return res.toString();
	}
	
	public static String indent(String indentFirstLine, String indent, String s)
	{
		StringBuilder res = new StringBuilder();
		String[] lines = s.split("\n");
		for(int i = 0; i < lines.length; ++i)
		{
			if(i == 0)
			{
				res.append(indentFirstLine);
			}
			else
			{
				res.append(indent);
			}
			res.append(lines[i]);
			res.append("\n");
		}
		return res.toString();
	}
	
	public static String U(String s)
	{
		return s.substring(0, 1).toUpperCase() + s.substring(1);	 
	}
	
	public static String L(String s)
	{
		return s.substring(0, 1).toLowerCase() + s.substring(1);	 
	}
	
	public static String asCode(String s)
	{
		if(s == null) return "null";
		return "\"" + s.replace("\"", "\\\"") + "\"";
	}
	
	public static String escapeString(String s)
	{
		return s.replace("\\", "\\\\")
				.replace("\"", "\\\"")
				.replace("\'", "\\\'")
				.replace("\n", "\\n")
				.replace("\t", "\\t");
	}
}
