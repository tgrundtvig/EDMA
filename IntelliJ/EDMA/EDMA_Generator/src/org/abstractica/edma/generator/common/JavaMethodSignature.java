package org.abstractica.edma.generator.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

public class JavaMethodSignature
{
	//Imports
	private Collection<String> imports;
	
	//JavaDoc
	String javaDocDesc;
	String javaDocReturn;
	
	// returnType is null for a constructor...
	String scope;
	String returnType;
	String name;
	Collection<JavaParameter> parameters;
	Collection<String> exceptions;
	Collection<String> throwsDescriptions;

	public JavaMethodSignature(String name, String description)
	{
		this.name = name;
		imports = new TreeSet<String>();
		javaDocReturn = "";
		javaDocDesc = description;
		scope = "public";
		returnType = null;
		parameters = new ArrayList<JavaParameter>();
		exceptions = new TreeSet<String>();
		throwsDescriptions = new TreeSet<String>();
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setScope(String scope)
	{
		this.scope = scope;
	}

	public void setReturnType(String type)
	{
		this.returnType = type;
	}

	public void setReturnDescription(String description)
	{
		javaDocReturn = description;
	}

	public void addException(String exception)
	{
		exceptions.add(exception);
	}

	public void addParameter(String type, String name, String description)
	{
		parameters.add(new JavaParameter(type, name, description));
	}

	public void addImport(String imp)
	{
		imports.add(imp);
	}

	public Collection<String> getImports()
	{
		return imports;
	}

	public boolean canAddToInterface()
	{
		return (returnType != null && scope.equals("public"));
	}

	public Collection<String> getExceptions()
	{
		return exceptions;
	}

	public String getJavaDocDesc()
	{
		return javaDocDesc;
	}

	public String getJavaDocReturn()
	{
		return javaDocReturn;
	}

	public String getScope()
	{
		return scope;
	}

	public String getReturnType()
	{
		return returnType;
	}

	public String getName()
	{
		return name;
	}

	public Collection<JavaParameter> getParameters()
	{
		return parameters;
	}
	
	public void addThrowsDescription(String desc)
	{
		throwsDescriptions.add(desc);
	}
	
	public Collection<String> getThrowsDescriptions()
	{
		return throwsDescriptions;
	}
	
	public void setDescription(String desc)
	{
		javaDocDesc = desc;
	}

}
