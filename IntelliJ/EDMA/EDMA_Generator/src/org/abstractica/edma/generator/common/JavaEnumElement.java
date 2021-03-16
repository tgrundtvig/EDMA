package org.abstractica.edma.generator.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

public class JavaEnumElement
{
	private ArrayList<JavaMethod> methods;
	private ArrayList<JavaField> fields;
	private ArrayList<JavaClass> innerClasses;
	private ArrayList<JavaInterface> innerInterfaces;
	private ArrayList<String> initValues;
	private TreeSet<String> imports;
	private String name;
	
	public JavaEnumElement(String name) 
	{
		this.name = name;
		imports = new TreeSet<String>();
	}
	
	public String getName()
	{
		return name;
	}
	
	public void addImport(String imp)
	{
		imports.add(imp);
	}
	
	public void removeImport(String imp)
	{
		imports.remove(imp);
	}
	
	public String popImport()
	{
		String s = imports.first();
		imports.remove(s);
		return s;
	}
	
	public Collection<String> getImports()
	{
		TreeSet<String> res = new TreeSet<String>();
		res.addAll(imports);
		for (JavaClass jclass : getInnerClasses())
		{
			res.addAll(jclass.getImports());
		}
		for (JavaInterface iface : getInnerInterfaces())
		{
			res.addAll(iface.getImports());
		}
		for (JavaMethod method : getMethods())
		{
			res.addAll(method.getImports());
			res.addAll(method.getSignature().getImports());
		}
		return res;
	}
	
	/*
	public Collection<String> getAllInnerImports()
	{
		for (JavaInterface iface : innerInterfaces)
		{
			imports.addAll(iface.getAllInnerImports());
		}
		for (JavaClass jclass : innerClasses)
		{
			imports.addAll(jclass.getAllInnerImports());
		}
		return imports;
	}*/
	
	public Collection<JavaClass> getInnerClasses()
	{
		if (innerClasses == null)
			innerClasses = new ArrayList<JavaClass>();
		return innerClasses;
	}
	
	public void addInnerClass(JavaClass innerClass)
	{
		getInnerClasses().add(innerClass); 
	}
	
	public void addInnerClasses(Collection<JavaClass> jClasses)
	{
		getInnerClasses().addAll(jClasses);
	}
	
	public Collection<JavaInterface> getInnerInterfaces()
	{
		if (innerInterfaces == null)
			innerInterfaces = new ArrayList<JavaInterface>();
		return innerInterfaces;
	}
	
	public void addInnerInterface(JavaInterface iface)
	{
		getInnerInterfaces().add(iface);
	}
	
	public void addInnerInterfaces(Collection<JavaInterface> ifaces)
	{
		getInnerInterfaces().addAll(ifaces);
	}
	
	public Collection<JavaMethod> getMethods()
	{
		if (methods == null)
			methods = new ArrayList<JavaMethod>();
		return methods;
	}
	
	public Collection<JavaField> getFields()
	{
		if (fields == null)
			fields = new ArrayList<JavaField>();
		return fields;
	}
	
	public void addMethod(JavaMethod method)
	{
		getMethods().add(method);
	}
	
	public void addField(JavaField field)
	{
		getFields().add(field);
	}
	
	public void addField(String scope, String type, String name)
	{
		getFields().add(new JavaField(scope, type, name));
	}
	
	public Collection<String> getInitValues()
	{
		if (initValues == null)
			initValues = new ArrayList<String>();
		return initValues;
	}
	
	public void addInitValue(String v)
	{
		getInitValues().add(v);
	}
}
