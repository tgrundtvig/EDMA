package edma.generator.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class JavaEnum
{
	private JavaClass parent;
	private Collection<JavaEnumElement> elements;
	
	public JavaEnum(String name)
	{
		parent = new JavaClass(name);
		elements = new ArrayList<JavaEnumElement>();
	}
	
	public String getName()
	{
		return parent.getName();
	}

	public void setScope(String scope)
	{
		parent.setScope(scope);
	}

	public String getScope()
	{
		return parent.getScope();
	}

	public void setPackage(String pck)
	{
		parent.setPackage(pck);
	}

	public String getPackage()
	{
		return parent.getPackage();
	}
	
	public void setDescription(String desc) 
	{
		parent.setDescription(desc);
	}
	
	public String getDescription()
	{
		return parent.getDescription();
	}
	
	public void addImport(String imp)
	{
		parent.addImport(imp);
	}
	
	public void removeImport(String imp)
	{
		parent.removeImport(imp);
	}
	
	public String popImport()
	{
		return parent.popImport();
	}
	
	/*
	public Collection<String> getAllInnerImports()
	{
		TreeSet<String> res = new TreeSet<String>();
		res.addAll(parent.getAllInnerImports());
		for (JavaEnumElement elem : elements)
		{
			res.addAll(elem.getImports());
			res.addAll(elem.getAllInnerImports());
		}
		return res;
	}
	*/
	
	public Collection<String> getImports()
	{
		HashSet<String> res = new HashSet<String>();
		res.addAll(parent.getImports());
		for (JavaEnumElement elem : elements)
		{
			res.addAll(elem.getImports());
		}
		return res;
	}
	
	public void addImplements(String sInterface)
	{
		parent.addImplements(sInterface);
	}
	
	public Collection<String> getImplements()
	{
		return parent.getImplements();
	}
	
	public void addField(String type, String name)
	{
		parent.addField(type,name);
	}
	
	public void addField(String scope, String type, String name)
	{
		parent.addField(scope, type, name);
	}
	
	public void addField(	String scope,
							boolean isStatic,
							boolean isFinal,
							String type,
							String name,
							String initial)
	{
		parent.addField(scope, isStatic, isFinal, type, name, initial);
	}

	public Collection<JavaField> getFields()
	{
		return parent.getFields();
	}
	
	public void addElement(String elem)
	{
		elements.add(new JavaEnumElement(elem));
	}
	
	public void addElement(JavaEnumElement elem)
	{
		elements.add(elem);
	}
	
	public Collection<JavaEnumElement> getElements()
	{
		return elements;
	}
	
	public void addInnerClass(JavaClass jclass)
	{
		parent.addInnerClass(jclass);
	}
	
	public Collection<JavaClass> getInnerClasses()
	{
		return parent.getInnerClasses();
	}
	
	public void addInnerInterface(JavaInterface iface)
	{
		parent.addInnerInterface(iface);
	}
	
	public Collection<JavaInterface> getInnerInterfaces()
	{
		return parent.getInnerInterfaces();
	}
	
	public void addInnerEnum(JavaEnum jenum)
	{
		parent.addInnerEnum(jenum);
	}
	
	public void addInnerEnums(Collection<JavaEnum> jenums)
	{
		parent.addInnerEnums(jenums);
	}
	
	public Collection<JavaEnum> getInnerEnums()
	{
		return parent.getInnerEnums();
	}
	
	public void addMethod(JavaMethod method)
	{
		parent.addMethod(method);
	}
	
	public void addMethods(Collection<JavaMethod> methods)
	{
		parent.addMethods(methods);
	}
	
	public Collection<JavaMethod> getMethods()
	{
		return parent.getMethods();
	}
}
