package org.abstractica.edma.generator.common;

import java.util.Collection;
import java.util.TreeSet;

public class JavaMethod
{
	private JavaMethodSignature signature;
	private Collection<String> imports;
	private StringBuilder body;
	private boolean isStatic;
	
	public JavaMethod(JavaMethodSignature signature)
	{
		this.signature = signature;
		imports = new TreeSet<String>();
		body = new StringBuilder();
		isStatic = false;
	}

	public String getBody()
	{
		return body.toString();
	}

	public void appendToBody(String s)
	{
		this.body.append(s);
	}
	
	public void appendToBody(int i)
	{
		this.body.append(i);
	}
	
	public void appendToBody(boolean b)
	{
		this.body.append(b);
	}

	public JavaMethodSignature getSignature()
	{
		return signature;
	}

	public Collection<String> getImports()
	{
		return imports;
	}
	
	public boolean isStatic()
	{
		return isStatic;
	}
	
	public void setStatic(boolean s)
	{
		isStatic = s;
	}
	
	public void addImport(String imp)
	{
		imports.add(imp);
	}
}
