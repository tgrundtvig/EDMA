package edma.generator.common;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import edma.util.FileUtil;
import edma.util.StringUtil;

public class JavaWriter
{
	private static final String tab = "    ";
	private static final int maxCols = 80;
	private static boolean generateJavaDoc = true;

	public static void toFile(JavaInterface intf, String srcDir)
	{
		String content = toString(intf);
		String dirName = srcDir + "/" + intf.getPackage().replace('.', '/');
		String fileName = dirName + "/" + intf.getName() + ".java";
		writeFile(content, dirName, fileName);
	}
	
	public static void toFileIfNotExists(JavaInterface intf, String srcDir)
	{
		String content = toString(intf);
		String dirName = srcDir + "/" + intf.getPackage().replace('.', '/');
		String fileName = dirName + "/" + intf.getName() + ".java";
		writeFileIfNotExists(content, dirName, fileName);
	}

	public static void toFile(JavaClass clazz, String srcDir)
	{
		String content = toString(clazz);
		String dirName = srcDir + "/" + clazz.getPackage().replace('.', '/');
		String fileName = dirName + "/" + clazz.getName() + ".java";
		writeFile(content, dirName, fileName);
	}

	public static void toFileIfNotExists(JavaClass clazz, String srcDir)
	{
		String content = toString(clazz);
		String dirName = srcDir + "/" + clazz.getPackage().replace('.', '/');
		String fileName = dirName + "/" + clazz.getName() + ".java";
		writeFileIfNotExists(content, dirName, fileName);
	}
	
	public static void writeUserClass(JavaClass clazz, String srcDir)
	{
		String content = toString(clazz);
		String dirName = srcDir + "/" + clazz.getPackage().replace('.', '/');
		String fileName = dirName + "/" + clazz.getName() + ".java";
		if(!FileUtil.exists(dirName)) FileUtil.makeDirectory(dirName);
		if(!FileUtil.exists(fileName))
		{
			FileUtil.writeFile(fileName, content);
			return;
		}
		TreeSet<String> imports = new TreeSet<String>();
		StringBuilder userCode = new StringBuilder();
		getImportsAndUserCode(FileUtil.readFile(fileName), imports, userCode);
		String lines[] = content.split("\n");
		getImports(lines, imports);
		content = replaceImportsAndUserCode(lines, imports, userCode.toString());
		FileUtil.writeFile(fileName, content);
	}

	public static String toString(JavaInterface intf)
	{
		StringBuilder res = new StringBuilder();
		getJavaInterface("", intf, generateJavaDoc, false, res);
		return res.toString();
	}

	public static String toString(JavaClass clazz)
	{
		StringBuilder res = new StringBuilder();
		getJavaClass("", clazz, generateJavaDoc, false, res);
		return res.toString();
	}

	public static String toString(JavaEnum jenum)
	{
		StringBuilder res = new StringBuilder();
		getEnum("", jenum, generateJavaDoc, false, res);
		return res.toString();
	}
	
	private static void writeFile(String content, String dirName, String fileName)
	{
		if(!FileUtil.exists(dirName)) FileUtil.makeDirectory(dirName);
		FileUtil.writeFile(fileName, content);
	}
	
	private static void writeFileIfNotExists(String content, String dirName, String fileName)
	{
		if(!FileUtil.exists(dirName)) FileUtil.makeDirectory(dirName);
		if(!FileUtil.exists(fileName)) FileUtil.writeFile(fileName, content);
	}
	
	private static String replaceImportsAndUserCode(String[] lines, Set<String> imports, String userCode)
	{
		StringBuilder res = new StringBuilder();
		boolean inUserCode = false;
		boolean inImports = true;
		boolean importsAdded = false;
		for(String line : lines)
		{
			String trimLine = line.trim();
			if(inImports)
			{
				if(trimLine.contains(" class "))
				{
					inImports = false;
					res.append(line + "\n");
				}
				else if(trimLine.startsWith("import "))
				{
					if(!importsAdded)
					{
						for(String imp : imports)
						{
							res.append("import " + imp + ";\n");
						}
						importsAdded = true;
					}
				}
				else
				{
					res.append(line + "\n");
				}
			}
			else if(inUserCode)
			{
				if("// EDMA_non-generated_code_end".equals(trimLine))
				{
					res.append(line + "\n");
					inUserCode = false;
				}
			}
			else
			{
				if("// EDMA_non-generated_code_begin".equals(trimLine))
				{
					res.append(line + "\n");
					res.append(userCode);
					inUserCode = true;
				}
				else
				{
					res.append(line + "\n");
				}
			}
		}
		return res.toString();
	}
	
	private static int getImports(String[] lines, Set<String> imports)
	{
		int res = lines.length;
		for(int i = 0; i < lines.length; ++i)
		{
			String line = lines[i];
			if(line.contains(" class "))
			{
				res = i;
				break;
			}
			if(line.trim().startsWith("import "))
			{
				String sub = line.substring(7).trim();
				int end = sub.indexOf(';');
				imports.add(sub.substring(0, end));
			}
		}
		return res;
	}
	
	private static void getUserCode(String[] lines, int begin, StringBuilder userCode)
	{
		boolean inUserCode = false;
		for(int i = begin; i < lines.length; ++i)
		{
			String line = lines[i];
			if(inUserCode)
			{
				if("// EDMA_non-generated_code_end".equals(line.trim()))
				{
					return;
				}
				userCode.append(line);
				userCode.append("\n");
			}
			else if("// EDMA_non-generated_code_begin".equals(line.trim()))
			{
				inUserCode = true;
			}
		}
		throw new RuntimeException("Tags in user class not working right!");
	}
	
	private static void getImportsAndUserCode(String content, Set<String> imports, StringBuilder userCode)
	{
		String[] lines = content.split("\n");
		int i = getImports(lines, imports);
		getUserCode(lines, i, userCode);
	}

	private static void getJavaClass(	String indent,
										JavaClass clazz,
										boolean javaDoc,
										boolean inner,
										StringBuilder res)
	{
		Collection<String> curImports = new TreeSet<String>();

		if(!inner)
		{
			curImports.addAll(clazz.getImports());
		}
		if(clazz.getPackage() != null && !inner)
		{
			res.append("package " + clazz.getPackage() + ";\n\n");
		}
		for(String imp : curImports)
		{
			res.append("import " + imp + ";\n");
		}
		res.append("\n");
		if(javaDoc)
		{
			res.append(getJavaDocForClass(indent, clazz));
		}
		if(clazz.getScope() != null)
		{
			res.append(indent);
			res.append(clazz.getScope());
			res.append(" ");
		}
		if(clazz.isAbstract()) res.append("abstract ");
		res.append("class " + clazz.getName());
		if(clazz.getExtends().size() > 0)
		{
			res.append(" extends");
			boolean first = true;
			{
				for(String ext : clazz.getExtends())
				{
					if(first)
					{
						first = false;
					}
					else
					{
						res.append(",");
					}
					res.append(" " + ext);
				}
			}
		}
		if(clazz.getImplements().size() > 0)
		{
			res.append(" implements");
			boolean first = true;
			{
				for(String impl : clazz.getImplements())
				{
					if(first)
					{
						first = false;
					}
					else
					{
						res.append(",");
					}
					res.append(" " + impl);
				}
			}
		}
		res.append("\n");
		res.append(indent);
		res.append("{\n");
		String newIndent = indent + tab;

		// Enums...
		for(JavaEnum jenum : clazz.getInnerEnums())
		{
			getEnum(newIndent, jenum, javaDoc, true, res);
		}

		// Fields...
		for(JavaField field : clazz.getFields())
		{
			getJavaField(newIndent, field, javaDoc, res);
		}
		res.append("\n");

		// Static methods
		for(JavaMethod m : clazz.getMethods())
		{
			if(m.isStatic())
			{
				res.append("\n\n");
				getMethod(newIndent, m, javaDoc, res);
			}
		}
		res.append("\n\n");
		// Constructor
		if(clazz.getConstructor() != null) getMethod(	newIndent,
														clazz.getConstructor(),
														javaDoc,
														res);

		// Non-static methods
		for(JavaMethod m : clazz.getMethods())
		{
			if(!m.isStatic())
			{
				res.append("\n\n");
				getMethod(newIndent, m, javaDoc, res);
			}
		}

		// Abstract methods
		for(JavaMethodSignature sig : clazz.getAbstractMethods())
		{
			res.append("\n\n");
			if(javaDoc)
			{
				res.append(getJavaDocForMethod(newIndent, sig));
			}
			getMethodSignature(newIndent, false, true, sig, false, res);
		}

		for(JavaInterface iface : clazz.getInnerInterfaces())
		{
			res.append("\n\n");
			res.append(indent);
			getJavaInterface(indent + tab, iface, javaDoc, true, res);
		}
		for(JavaClass jclass : clazz.getInnerClasses())
		{
			res.append("\n\n");
			res.append(indent);
			getJavaClass(indent + tab, jclass, javaDoc, true, res);
		}
		res.append("\n");
		res.append(indent);
		res.append("}\n");
	}

	private static void getJavaField(	String indent,
										JavaField field,
										boolean javaDoc,
										StringBuilder res)
	{
		res.append(indent);
		res.append(field.getScope());
		if(!"".equals(field.getScope())) res.append(" ");
		if(field.isStatic()) res.append("static ");
		if(field.isFinal()) res.append("final ");
		res.append(field.getType());
		res.append(" ");
		res.append(field.getName());
		if(field.getInitial() != null)
		{
			res.append(" = ");
			res.append(field.getInitial());
		}
		res.append(";\n");
	}

	private static void getJavaInterface(	String indent,
											JavaInterface intf,
											boolean javaDoc,
											boolean inner,
											StringBuilder res)
	{
		if(intf.getPackage() != null && !inner)
		{
			res.append("package " + intf.getPackage() + ";\n\n");
		}

		if(!inner)
		{
			Collection<String> curImports = new TreeSet<String>();
			curImports.addAll(intf.getImports());
			for(String imp : curImports)
			{
				res.append("import " + imp + ";\n");
			}
		}
		res.append("\n");

		if(javaDoc)
		{
			res.append(getJavaDocForInterface(indent, intf));
		}
		res.append(indent);
		res.append(intf.getScope());
		if(!"".equals(intf.getScope())) res.append(" ");
		res.append("interface " + intf.getName());
		if(intf.getExtends().size() > 0)
		{
			res.append(" extends");
			boolean first = true;
			{
				for(String ext : intf.getExtends())
				{
					if(first)
					{
						first = false;
					}
					else
					{
						res.append(",");
					}
					res.append(" " + ext);
				}
			}
		}
		res.append("\n");
		res.append(indent);
		res.append("{\n");
		String newIndent = indent + tab;

		// add enums
		for(JavaEnum jenum : intf.getInnerEnums())
		{
			getEnum(newIndent, jenum, javaDoc, true, res);
		}

		// add methods...
		for(JavaMethodSignature m : intf.getMethods())
		{
			if(m.canAddToInterface())
			{
				res.append("\n");
				if(javaDoc)
				{
					res.append(getJavaDocForMethod(newIndent, m));
				}

				getMethodSignature(newIndent, false, false, m, true, res);
				res.append("\n");
			}
		}

		// add classes...
		for(JavaClass jclass : intf.getInnerClasses())
		{
			getJavaClass(indent + tab, jclass, javaDoc, true, res);
		}

		// add interfaces
		for(JavaInterface iface : intf.getInnerInterfaces())
		{
			getJavaInterface(indent + tab, iface, javaDoc, true, res);
		}

		res.append("\n");
		res.append(indent);
		res.append("}\n");
	}

	private static void getEnum(String indent,
								JavaEnum jenum,
								boolean javaDoc,
								boolean inner,
								StringBuilder res)
	{
		if(jenum.getPackage() != null && !inner)
		{
			res.append("package " + jenum.getPackage() + ";\n\n");
		}

		if(!inner)
		{
			Collection<String> curImports = new TreeSet<String>();
			curImports.addAll(jenum.getImports());
			for(String imp : curImports)
			{
				res.append("import " + imp + ";\n");
			}
		}
		res.append("\n");

		res.append(indent + jenum.getScope() + " enum " + jenum.getName() + " ");
		if(jenum.getImplements().size() > 0)
		{
			res.append("implements ");
			boolean first = true;
			for(String iface : jenum.getImplements())
			{
				if(first) first = false;
				else res.append(", ");
				res.append(iface);
			}
		}
		res.append("\n" + indent + "{\n");
		boolean first = true;
		for(JavaEnumElement element : jenum.getElements())
		{
			if(first) first = false;
			else res.append(",\n");
			getEnumElement(indent + tab, javaDoc, element, res);
		}
		res.append(";\n");

		for(JavaField field : jenum.getFields())
		{
			getJavaField(indent + tab, field, javaDoc, res);
		}

		for(JavaMethod method : jenum.getMethods())
		{
			getMethod(indent, method, javaDoc, res);
		}

		for(JavaEnum innerEnum : jenum.getInnerEnums())
		{
			getEnum(indent + tab, innerEnum, javaDoc, true, res);
		}

		for(JavaInterface iface : jenum.getInnerInterfaces())
		{
			getJavaInterface(indent + tab, iface, javaDoc, true, res);
		}

		for(JavaClass jclass : jenum.getInnerClasses())
		{
			getJavaClass(indent + tab, jclass, javaDoc, true, res);
		}
		res.append(indent + "}\n");
	}

	private static void getEnumElement(	String indent,
										boolean javaDoc,
										JavaEnumElement element,
										StringBuilder res)
	{
		res.append(indent + element.getName());
		if(element.getInitValues().size() > 0)
		{
			res.append("(");
			boolean first = true;
			for(String elem : element.getInitValues())
			{
				if(first) first = false;
				else res.append(", ");
				res.append(elem);
			}
			res.append(")");
		}

		if(element.getFields().size() != 0 || element.getMethods().size() != 0
				|| element.getInnerClasses().size() != 0
				|| element.getInnerInterfaces().size() != 0)
		{
			res.append("\n" + indent + "{\n");
			for(JavaField field : element.getFields())
			{
				res.append(indent + tab + field.getScope() + " "
						+ field.getType() + " " + field.getName() + ";\n");
			}
			for(JavaMethod method : element.getMethods())
			{
				getMethod(indent + tab, method, javaDoc, res);
			}
			for(JavaClass jclass : element.getInnerClasses())
			{
				getJavaClass(indent + tab, jclass, javaDoc, true, res);
			}
			for(JavaInterface iface : element.getInnerInterfaces())
			{
				getJavaInterface(indent + tab, iface, javaDoc, true, res);
			}
			res.append("\n" + indent + "}");
		}
	}

	private static void getMethod(	String indent,
									JavaMethod method,
									boolean javaDoc,
									StringBuilder res)
	{
		if(javaDoc)
		{
			res.append(getJavaDocForMethod(indent, method.getSignature()));
		}
		getMethodSignature(	indent,
							method.isStatic(),
							false,
							method.getSignature(),
							false,
							res);
		res.append("\n");
		res.append(indent);
		res.append("{\n");
		getMethodBody(indent + tab, method.getBody(), res);
		res.append(indent);
		res.append("}");
	}

	private static void getMethodBody(	String indent,
										String body,
										StringBuilder res)
	{
		String[] lines = body.split("\n");
		for(String line : lines)
		{
			res.append(indent);
			res.append(line);
			res.append("\n");
		}
	}

	private static void getMethodSignature(	String indent,
											boolean isStatic,
											boolean isAbstract,
											JavaMethodSignature signature,
											boolean forInterface,
											StringBuilder res)
	{
		if(forInterface && !signature.canAddToInterface()) return;
		// Signature
		res.append(indent);
		res.append(signature.getScope());
		res.append(" ");
		if(isAbstract)
		{
			res.append("abstract ");
		}
		if(!forInterface && isStatic)
		{
			res.append("static ");
		}
		// If it is a constructor the returntype will be null...
		if(signature.getReturnType() != null)
		{
			res.append(signature.getReturnType());
			res.append(" ");
		}
		res.append(signature.getName());
		res.append("(");
		boolean first = true;
		for(JavaParameter param : signature.getParameters())
		{
			if(first)
			{
				first = false;
			}
			else
			{
				res.append(", ");
			}
			res.append(param);
		}
		res.append(")");
		// Exceptions
		if(signature.getExceptions().size() > 0)
		{
			res.append(" throws ");
			first = true;
			for(String exception : signature.getExceptions())
			{
				if(first)
				{
					first = false;
				}
				else
				{
					res.append(", ");
				}
				res.append(exception);
			}
		}
		if(forInterface || isAbstract)
		{
			res.append(";");
		}
	}

	private static String getJavaDocForMethod(	String indent,
												JavaMethodSignature m)
	{
		StringBuilder res = new StringBuilder();
		// Description
		int iAdjust = maxCols - indent.length() - 3;
		res.append(StringUtil.adjust(m.getJavaDocDesc(), iAdjust));
		// Parameters
		int maxNameLength = 0;
		StringBuilder sIndent = new StringBuilder();
		sIndent.append("       "); // "@param ";
		for(JavaParameter param : m.getParameters())
		{
			maxNameLength = Math.max(maxNameLength, param.getName().length());
		}
		for(int i = 0; i < maxNameLength; ++i)
		{
			sIndent.append(" ");
		}
		sIndent.append("  ");
		for(JavaParameter param : m.getParameters())
		{
			res.append("@param ");
			String paramName = param.getName();
			res.append(paramName);
			int space = maxNameLength - paramName.length();
			for(int i = 0; i < space; ++i)
			{
				res.append(" ");
			}
			res.append("  ");
			String tmp = StringUtil.adjust(param.getDescription(), iAdjust
					- sIndent.length());
			tmp = StringUtil.indent("", sIndent.toString(), tmp);
			res.append(tmp);
			// res.append("\n");
		}

		if(m.getReturnType() != null && !m.getReturnType().equals("void"))
		{
			res.append("@return ");
			int space = sIndent.length() - 8;
			for(int i = 0; i < space; ++i)
			{
				res.append(" ");
			}
			String tmp = StringUtil.adjust(m.getJavaDocReturn(), iAdjust
					- sIndent.length());
			res.append(StringUtil.indent("", sIndent.toString(), tmp));
			// res.append("\n");
		}
		for(String throwDesc : m.getThrowsDescriptions())
		{
			res.append("@throws ");
			int space = sIndent.length() - 8;
			for(int i = 0; i < space; ++i)
			{
				res.append(" ");
			}
			String tmp = StringUtil.adjust(	throwDesc,
											iAdjust - sIndent.length());
			res.append(StringUtil.indent("", sIndent.toString(), tmp));
			res.append("\n");
		}
		StringBuilder docRes = new StringBuilder();
		addJavaDocStars(indent, res.toString(), docRes);
		return docRes.toString();
	}

	private static String getJavaDocForInterface(	String indent,
													JavaInterface intf)
	{
		StringBuilder res = new StringBuilder();
		int iAdjust = maxCols - indent.length() - 3;
		String adjusted = StringUtil.adjust(intf.getDescription(), iAdjust);
		addJavaDocStars(indent, adjusted, res);
		return res.toString();
	}

	private static String getJavaDocForClass(String indent, JavaClass clazz)
	{
		StringBuilder res = new StringBuilder();
		int iAdjust = maxCols - indent.length() - 3;
		String adjusted = StringUtil.adjust(clazz.getDescription(), iAdjust);
		addJavaDocStars(indent, adjusted, res);
		return res.toString();
	}

	private static void addJavaDocStars(String indent,
										String description,
										StringBuilder res)
	{
		if(description == null) return;
		res.append(indent);
		res.append("/**\n");
		res.append(StringUtil.indent(indent + " * ", description));
		res.append(indent);
		res.append(" */\n");
	}

}
