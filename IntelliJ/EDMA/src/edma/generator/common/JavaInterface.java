package edma.generator.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

public class JavaInterface
{
	private String pck;
	private Collection<String> imports;
	private String name;
	private String scope;
	private String description;
	private Collection<String> extendsList;
	private Collection<JavaMethodSignature> methods;
	private Collection<JavaClass> innerClasses;
	private Collection<JavaInterface> innerInterfaces;
	private Collection<JavaEnum> innerEnums;
	
	public JavaInterface(String name, String description)
	{
		pck = null;
		imports = new TreeSet<String>();
		this.name = name;
		scope = "public";
		this.description = description;
		extendsList = new TreeSet<String>();
		methods = new ArrayList<JavaMethodSignature>();	
	}

	public String getPackage()
	{
		return pck;
	}
	
	public void setPackage(String pck)
	{
		this.pck = pck;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getScope()
	{
		return scope;
	}
	
	public void setScope(String scope)
	{
		this.scope = scope;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setJavaDocDesc(String javaDocDesc)
	{
		this.description = javaDocDesc;
	}
	
	public Collection<String> getExtends()
	{
		return extendsList;
	}
	
	public void addExtends(String ext)
	{
		extendsList.add(ext);
	}
	
	public Collection<JavaMethodSignature> getMethods()
	{
		return methods;
	}
	
	public void addMethod(JavaMethodSignature method)
	{
		imports.addAll(method.getImports());
		methods.add(method);
	}
	
	public Collection<String> getImports()
	{
		TreeSet<String> res = new TreeSet<String>();
		res.addAll(imports);
		for(JavaMethodSignature sig : methods)
		{
			res.addAll(sig.getImports());
		}
		for(JavaClass clazz : getInnerClasses())
		{
			res.addAll(clazz.getImports());
		}
		for(JavaInterface intf : getInnerInterfaces())
		{
			res.addAll(intf.getImports());
		}
		for (JavaEnum jenum : getInnerEnums())
		{
			res.addAll(jenum.getImports());
		}
		return res;
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
		return ((ArrayList<String>)imports).remove(0);
	}
	
	public void addInnerClass(JavaClass innerClass)
	{
		getInnerClasses().add(innerClass);
	}
	
	public Collection<JavaClass> getInnerClasses()
	{
		if (innerClasses == null)
			innerClasses = new ArrayList<JavaClass>();
		return innerClasses;
	}
	
	public void addInnerClasses(Collection<JavaClass> innerClassList)
	{
		getInnerClasses().addAll(innerClassList);
	}
	
	public void addInnerInterface(JavaInterface innerInterface)
	{
		getInnerInterfaces().add(innerInterface);
	}
	
	public Collection<JavaInterface> getInnerInterfaces() 
	{
		if (innerInterfaces == null)
			innerInterfaces = new ArrayList<JavaInterface>();
		return innerInterfaces;
	}
	
	public void addInnerInterfaces(Collection<JavaInterface> interfaceList)
	{
		getInnerInterfaces().addAll(interfaceList);
	}
	
	public void addInnerEnum(JavaEnum jenum)
	{
		getInnerEnums().add(jenum);
	}
	
	public void addInnerEnums(Collection<JavaEnum> jenums)
	{
		getInnerEnums().addAll(jenums);
	}
	
	public Collection<JavaEnum> getInnerEnums()
	{
		if (innerEnums == null)
			innerEnums = new ArrayList<JavaEnum>();
		return innerEnums;
	}
}
