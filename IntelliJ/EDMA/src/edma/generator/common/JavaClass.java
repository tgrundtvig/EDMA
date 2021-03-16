package edma.generator.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

public class JavaClass
{
	private String pck;
	private String name;
	private String scope;
	private String description;
	private Collection<String> imports;
	private Collection<String> implementsList;
	private Collection<String> extendList;
	private Collection<JavaField> fields;
	private Collection<JavaMethod> methods;
	private Collection<JavaMethodSignature> abstractMethods;
	private Collection<JavaClass> innerClasses;
	private Collection<JavaInterface> innerInterfaces;
	private Collection<JavaEnum> innerEnums;
	private JavaMethod constructor;
	private boolean isAbstract;

	public JavaClass(String name)
	{
		this.scope = "public";
		this.pck = null;
		this.name = name;
		description = null;
		imports = new TreeSet<String>();
		implementsList = new TreeSet<String>();
		extendList = new TreeSet<String>();
		JavaMethodSignature conSign = new JavaMethodSignature(	name,
																"Constructor");
		constructor = new JavaMethod(conSign);
		methods = new ArrayList<JavaMethod>();
		abstractMethods = new ArrayList<JavaMethodSignature>();
		innerClasses = new ArrayList<JavaClass>();
		innerInterfaces = new ArrayList<JavaInterface>();
		isAbstract = false;
	}

	public String getName()
	{
		return name;
	}
	
	public void setConstructorScope(String scope)
	{
		constructor.getSignature().setScope(scope);
	}
	
	public void noConstructor()
	{
		constructor = null;
	}
	
	public void setAbstract()
	{
		isAbstract = true;
	}

	public boolean isAbstract()
	{
		return isAbstract;
	}
	
	public void setPackage(String pck)
	{
		this.pck = pck;
	}

	public String getPackage()
	{
		return pck;
	}

	public void setScope(String scope)
	{
		this.scope = scope;
	}

	public String getScope()
	{
		return scope;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
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
	
	/*public Collection<String> getAllInnerImports()
	{
		for (JavaInterface iface : getInnerInterfaces())
		{
			imports.addAll(iface.getAllInnerImports());
		}
		for (JavaClass jclass : getInnerClasses())
		{
			imports.addAll(jclass.getAllInnerImports());
		}
		for (JavaEnum jenum : getInnerEnums())
		{
			imports.addAll(jenum.getAllInnerImports());
		}
		return imports;
	}*/

	public Collection<String> getImports()
	{
		TreeSet<String> res = new TreeSet<String>();
		res.addAll(imports);
		for(JavaMethod m : getMethods())
		{
			res.addAll(m.getImports());
			res.addAll(m.getSignature().getImports());
		}
		for(JavaMethodSignature sig : getAbstractMethods())
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
		for(JavaEnum jenum : getInnerEnums())
		{
			res.addAll(jenum.getImports());
		}
		return res;
	}

	public void addImplements(String sInterface)
	{
		implementsList.add(sInterface);
	}

	public Collection<String> getImplements()
	{
		return implementsList;
	}

	public void addExtends(String sClass)
	{
		extendList.add(sClass);
	}

	public Collection<String> getExtends()
	{
		return extendList;
	}

	public void addField(String type, String name)
	{
		getFields().add(new JavaField(type, name));
	}

	public void addField(String scope, String type, String name)
	{
		getFields().add(new JavaField(scope, type, name));
	}

	public void addField(	String scope,
							boolean isStatic,
							boolean isFinal,
							String type,
							String name,
							String initial)
	{
		getFields().add(new JavaField(scope, isStatic, isFinal, type, name, initial));
	}

	public Collection<JavaField> getFields()
	{
		if (fields == null)
			fields = new ArrayList<JavaField>();
		return fields;
	}

	public void addConstructorField(String type, String name, String description)
	{
		getFields().add(new JavaField(type, name));
		constructor.getSignature().addParameter(type, name, description);
		constructor.appendToBody("this." + name + " = " + name + ";\n");
	}

	public void addConstructorField(String scope,
									String type,
									String name,
									String description)
	{
		getFields().add(new JavaField(scope, type, name));
		constructor.getSignature().addParameter(type, name, description);
		constructor.appendToBody("this." + name + " = " + name + ";\n");
	}
	
	public void addFinalConstructorField(String scope,
	                                     String type,
	                                     String name,
	                                     String description)
	{
		getFields().add(new JavaField(scope, false, true, type, name, null));
		constructor.getSignature().addParameter(type, name, description);
		constructor.appendToBody("this." + name + " = " + name + ";\n");
	}

	public void addConstructorParameter(String type,
										String name,
										String description)
	{
		constructor.getSignature().addParameter(type, name, description);
	}

	public void addConstructorBodyLine(String line)
	{
		constructor.appendToBody(line);
		constructor.appendToBody("\n");
	}
	
	public void setConstructorDescription(String desc)
	{
		constructor.getSignature().setDescription(desc);
	}

	public JavaMethod getConstructor()
	{
		return constructor;
	}

	public void addMethod(JavaMethod method)
	{
		getMethods().add(method);
	}
	
	public void addAbstractMethod(JavaMethodSignature sig)
	{
		abstractMethods.add(sig);
		isAbstract = true;
	}

	public Collection<JavaMethod> getMethods()
	{
		if (methods == null)
			methods = new ArrayList<JavaMethod>();
		return methods;
	}
	
	public Collection<JavaMethodSignature> getAbstractMethods()
	{
		return abstractMethods;
	}

	public void addMethods(Collection<JavaMethod> methodList)
	{
		getMethods().addAll(methodList);
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
	
	public Collection<JavaEnum> getInnerEnums()
	{
		if (innerEnums == null)
			innerEnums = new ArrayList<JavaEnum>();
		return innerEnums;
	}
	
	public void addInnerEnum(JavaEnum jenum)
	{
		getInnerEnums().add(jenum);
	}
	
	public void addInnerEnums(Collection<JavaEnum> jenums)
	{
		getInnerEnums().addAll(jenums);
	}
}
