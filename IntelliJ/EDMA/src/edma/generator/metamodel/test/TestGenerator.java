package edma.generator.metamodel.test;

import java.util.ArrayList;

import edma.generator.EdmaImport;
import edma.generator.IPackageStructure;
import edma.generator.common.JavaClass;
import edma.generator.common.JavaMethod;
import edma.generator.common.JavaMethodSignature;
import edma.generator.metamodel.AModelGenerator;
import edma.metamodel.IMetaDataModel;
import edma.metamodel.IMetaMethod;
import edma.metamodel.IMetaMethodParameter;
import edma.util.StringUtil;

public class TestGenerator extends AModelGenerator
{
	private static final String i1 = "    ";
	private static final String i2 = i1 + i1;
	private static final String i3 = i1 + i2;
	private static final String i4 = i2 + i2;
	private JavaClass impl;

	public TestGenerator(	IPackageStructure pkgStruct,
							IMetaDataModel model,
							ArrayList<JavaClass> autoClasses)
	{
		super(pkgStruct, model);
		impl = new JavaClass(getTestClassName());
		impl.setPackage(getTestClassPackage());

		impl.addConstructorField(	getAPIIntfName(),
									"edma_dm",
									"Interface to an instance of the "
											+ model.getName() + " data model");
		impl.addConstructorField(	"ITerminal",
									"edma_terminal",
									"Terminal to get value domains from");
		impl.addImport(getAPIIntfImport());
		impl.addImport(EdmaImport.ITerminal);
		start();
		menu();
		readInputLine();
		int actionCount = model.getNumberOfActions();
		for(int i = 0; i < actionCount; ++i)
		{
			IMetaMethod method = model.getAction(i);
			callMethod(method, true);
		}
		int viewCount = model.getNumberOfViews();
		for(int i = 0; i < viewCount; ++i)
		{
			IMetaMethod method = model.getView(i);
			callMethod(method, false);
		}
		autoClasses.add(impl);
	}

	private String getMethodSignature(IMetaMethod mm)
	{
		StringBuilder res = new StringBuilder();
		res.append(mm.getName() + "(");
		int inCount = mm.getNumberOfInputParameters();
		for(int j = 0; j < inCount; ++j)
		{
			IMetaMethodParameter par = mm.getInputParameter(j);
			if(j > 0) res.append(", ");
			res.append(par.getName() + " : " + par.getValueDomain().getName());
		}
		res.append(") -> (");
		int outCount = mm.getNumberOfOutputParameters();
		for(int j = 0; j < outCount; ++j)
		{
			IMetaMethodParameter par = mm.getOutputParameter(j);
			if(j > 0) res.append(", ");
			res.append(par.getName() + " : " + par.getValueDomain().getName());
		}
		res.append(")");
		return res.toString();
	}

	private void menu()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"menu",
															"Prints the menu");
		sig.setReturnType("void");
		sig.setScope("private");
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("edma_terminal.put(\"Welcome to the " + model.getName()
				+ " interactive test!\\n\");\n");
		m.appendToBody("edma_terminal.put(\"************************************"
				+ "************************************\\n\");\n");

		int num = 1;
		m.appendToBody("edma_terminal.put(\"  Actions\\n  -------\\n\");\n");
		int actionCount = model.getNumberOfActions();
		for(int i = 0; i < actionCount; ++i)
		{
			IMetaMethod mm = model.getAction(i);
			m.appendToBody("edma_terminal.put(\"    " + num++ + " - "
					+ getMethodSignature(mm) + "\\n\");\n");
		}
		m.appendToBody("edma_terminal.put(\"  Views\\n  -----\\n\");\n");
		int viewCount = model.getNumberOfViews();
		for(int i = 0; i < viewCount; ++i)
		{
			IMetaMethod mm = model.getView(i);
			m.appendToBody("edma_terminal.put(\"    " + num++ + " - "
					+ getMethodSignature(mm) + "\\n\");\n");
		}
		m.appendToBody("edma_terminal.put(\"Please choose a transaction to call"
				+ " (or type exit to exit):\\n\");\n");
		impl.addMethod(m);
	}

	private void readInputLine()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"readInputLine",
															"Reads a line from stdin");
		sig.setReturnType("String");
		sig.setScope("private");
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("edma_terminal.put(\": \");\n");
		m.appendToBody("return edma_terminal.getString();\n");
		impl.addMethod(m);
	}

	private void start()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"start",
															"Starts the test");
		sig.setReturnType("void");
		sig.addException("IOException");
		sig.addImport("java.io.IOException");
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("while(true)\n{\n");
		m.appendToBody(i1 + "menu();\n");
		m.appendToBody(i1 + "String val = readInputLine();\n");
		m.appendToBody(i1 + "if(\"exit\".equalsIgnoreCase(val)) break;\n");
		m.appendToBody(i1 + "try\n" + i1 + "{\n");
		m.appendToBody(i2 + "int choice = Integer.parseInt(val);\n");

		// Find the method to call....
		m.appendToBody(i2 + "switch(choice)\n" + i2 + "{\n");
		int num = 0;
		int actionCount = model.getNumberOfActions();
		for(int i = 0; i < actionCount; ++i)
		{
			IMetaMethod method = model.getAction(i);
			m.appendToBody(i3 + "case " + (++num) + ":\n");
			m.appendToBody(i4 + "call" + StringUtil.U(method.getName())
					+ "();\n");
			m.appendToBody(i4 + "break;\n");
		}
		int viewCount = model.getNumberOfViews();
		for(int i = 0; i < viewCount; ++i)
		{
			IMetaMethod method = model.getView(i);
			m.appendToBody(i3 + "case " + (++num) + ":\n");
			m.appendToBody(i4 + "call" + StringUtil.U(method.getName())
					+ "();\n");
			m.appendToBody(i4 + "break;\n");
		}
		m.appendToBody(i3 + "default:\n");
		m.appendToBody(i4
				+ "edma_terminal.put(choice + \" does not exist! Please try again!\\n\");\n");
		m.appendToBody(i2 + "}\n");

		// End stuff
		m.appendToBody(i1 + "}\n" + i1 + "catch(NumberFormatException e)\n"
				+ i1 + "{\n");
		m.appendToBody(i2
				+ "edma_terminal.put(val + \" is not a valid input! Please try again!\\n\");\n");
		m.appendToBody(i1 + "}\n");
		m.appendToBody("}\n");
		impl.addMethod(m);
	}

	private void callMethod(IMetaMethod method, boolean isAction)
	{
		JavaMethodSignature sig = new JavaMethodSignature("call"
				+ StringUtil.U(method.getName()), "Calls the method: "
				+ method.getName());
		sig.setReturnType("void");
		sig.setScope("private");
		sig.addException("IOException");
		sig.addImport("java.io.IOException");
		JavaMethod m = new JavaMethod(sig);

		// Input parameters...
		int inputCount = method.getNumberOfInputParameters();
		for(int i = 0; i < inputCount; ++i)
		{
			IMetaMethodParameter par = method.getInputParameter(i);
			m.addImport(getIntfImport(par.getValueDomain()));
			m.appendToBody("\n//Input parameter " + par.getName() + "\n");
			m.appendToBody(getIntfName(par.getValueDomain()) + " in_"
					+ par.getName() + ";\n");
			String indent = "";
			if(par.canBeNull())
			{
				indent = i1;
				m.appendToBody("edma_terminal.put(\"Input parameter "
						+ par.getName()
						+ "? : "
						+ par.getValueDomain().getName()
						+ " is optional.\\n"
						+ "Would you like to enter a value for this input parameter (y/n): \");\n");
				m.appendToBody("if(edma_terminal.getYesNo())\n{\n");

			}
			m.appendToBody(indent + "edma_terminal.put(\"Enter input parameter "
					+ par.getName() + (par.canBeNull() ? "? : " : " : ")
					+ par.getValueDomain().getName() + "\\n\");\n");
			m.appendToBody(indent + "in_" + par.getName() + " = "
					+ getIntfName(par.getValueDomain())
					+ ".fromTerminal(edma_terminal);\n");
			if(par.canBeNull())
			{
				m.appendToBody("}\nelse\n{\n");
				m.appendToBody(i1 + "in_" + par.getName() + " = null;\n");
				m.appendToBody("}\n");
			}
		}

		// Make the call
		m.appendToBody("\n//Make the call\n");
		m.addImport(getMethodResultIntfImport(method.getName(), isAction));
		m.appendToBody(getMethodResultIntfName(method.getName(), isAction)
				+ " res = edma_dm." + getMethodName(method.getName(), isAction)
				+ "(");
		for(int i = 0; i < inputCount; ++i)
		{
			if(i > 0) m.appendToBody(", ");
			IMetaMethodParameter par = method.getInputParameter(i);
			m.appendToBody("in_" + par.getName());
		}
		m.appendToBody(");\n");
		impl.addMethod(m);

		// Print the result
		m.appendToBody("\n//Print the result\n");
		m.appendToBody("edma_terminal.put(\"\\n\\nResult: \" + res.errorCode() + \" - \" + res.errorMessage() + \"\\n\");\n");
		m.appendToBody("if(res.errorDescription() != null)\n{\n");
		m.appendToBody(i1
				+ "edma_terminal.put(\"Extra info: \" + res.errorDescription() + \"\\n\");\n}\n");
		int outputCount = method.getNumberOfOutputParameters();
		for(int i = 0; i < outputCount; ++i)
		{
			IMetaMethodParameter par = method.getOutputParameter(i);
			m.appendToBody("edma_terminal.put(\"\\nOutput parameter "
					+ par.getName() + ":\\n\");\n");
			m.appendToBody("edma_terminal.put(\"  \" + res.get"
					+ StringUtil.U(par.getName()) + "() + \"\\n\");\n");
		}
		m.appendToBody("edma_terminal.put(\"Press return to continue!\\n\");\n");
		m.appendToBody("readInputLine();\n");
	}
}
