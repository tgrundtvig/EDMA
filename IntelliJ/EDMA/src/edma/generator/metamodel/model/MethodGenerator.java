package edma.generator.metamodel.model;

import java.util.ArrayList;

import edma.generator.EdmaImport;
import edma.generator.IPackageStructure;
import edma.generator.common.JavaClass;
import edma.generator.common.JavaInterface;
import edma.generator.common.JavaMethod;
import edma.generator.common.JavaMethodSignature;
import edma.generator.metamodel.AModelGenerator;
import edma.metamodel.IMetaDataModel;
import edma.metamodel.IMetaErrorCode;
import edma.metamodel.IMetaMethod;
import edma.metamodel.IMetaMethodParameter;
import edma.util.StringUtil;

public class MethodGenerator extends AModelGenerator
{
	private static final String i1 = "    ";
	private static final String i2 = i1 + i1;
	
	private IMetaMethod method;
	private boolean action;

	protected MethodGenerator(	IPackageStructure pkgStruct,
								IMetaDataModel model,
								IMetaMethod method,
								boolean action,
								JavaClass modelImpl,
								JavaInterface modelIntf,
								ArrayList<JavaInterface> autoIntf,
								ArrayList<JavaClass> autoClasses,
								ArrayList<JavaClass> userClasses)
	{
		super(pkgStruct, model);
		this.method = method;
		this.action = action;

		// Result interface
		JavaInterface res_intf = new JavaInterface(	getMethodResultIntfName(method.getName(),
																			action),
													"Access to the result of the method: "
															+ method.getName());
		res_intf.setPackage(getMethodResultIntfPackage(method.getName(), action));
		res_intf.addExtends("IResult");
		res_intf.addImport(EdmaImport.IResult);
		
		// User class
		JavaClass user_impl = new JavaClass(getMethodUserClassName(	method.getName(),
																	action));
		user_impl.setPackage(getMethodUserClassPackage(method.getName(), action));
		user_impl.addImplements(res_intf.getName());
		user_impl.addImport(getMethodResultIntfImport(method.getName(), action));
		user_impl.addExtends("Result");
		user_impl.addImport(EdmaImport.Result);
		user_impl.addField("private", true, true, "int", "OK", "0");
		int numberOfErrors = method.getNumberOfErrorCodes();
		for(int i = 0; i < numberOfErrors; ++i)
		{
			IMetaErrorCode err = method.getErrorCode(i);
			String name = err.getMessage().replace(' ', '_').toUpperCase();
			user_impl.addField("private", true, true, "int", name, Integer.toString(err.getCode()));
		}
		generateUserConstructor(user_impl);
		userExecute(user_impl);
		int size = method.getNumberOfOutputParameters();
		for(int i = 0; i < size; ++i)
		{
			IMetaMethodParameter param = method.getOutputParameter(i);
			getOutput(param, user_impl, res_intf);
		}

		autoIntf.add(res_intf);
		userClasses.add(user_impl);

		// Edma class
		JavaClass edma_impl = new JavaClass(getMethodEdmaClassName(	method.getName(),
																	action));
		edma_impl.setPackage(getMethodEdmaClassPackage(method.getName(), action));

		if(action)
		{
			edma_impl.addExtends("AAction");
			edma_impl.addImport(EdmaImport.AAction);
		}
		else
		{
			edma_impl.addExtends("AView");
			edma_impl.addImport(EdmaImport.AView);
		}

		edma_impl.addConstructorBodyLine("super(\"" + method.getName() + "\");");
		edma_impl.addConstructorField(	getMethodUserClassName(	method.getName(),
																action),
										"edma_userMethod",
										"User provided method implementation");
		edma_impl.addImport(getMethodUserClassImport(method.getName(), action));
		execute(edma_impl);
		getUserMethod(edma_impl);
		autoClasses.add(edma_impl);

		// Model method
		addMethodToModel(modelIntf, modelImpl);
	}

	private void generateUserConstructor(JavaClass impl)
	{
		int size = method.getNumberOfInputParameters();
		for(int i = 0; i < size; ++i)
		{
			IMetaMethodParameter param = method.getInputParameter(i);
			impl.addFinalConstructorField(	"private",
											getIntfName(param.getValueDomain()),
											"in_" + param.getName(),
											"Input parameter " + (i + 1));
			impl.addImport(getIntfImport(param.getValueDomain()));
		}
		size = method.getNumberOfOutputParameters();
		for(int i = 0; i < size; ++i)
		{
			IMetaMethodParameter param = method.getOutputParameter(i);
			impl.addField(	getIntfName(param.getValueDomain()),
							"out_" + param.getName());
			impl.addConstructorBodyLine("out_" + param.getName() + " = null;");
			impl.addImport(getIntfImport(param.getValueDomain()));
		}
	}

	private void userExecute(JavaClass user_impl)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"execute",
															"Execution of the "
																	+ (action ? "action"
																			: "view"));
		JavaMethod m = new JavaMethod(sig);
		sig.setReturnType("int");
		if(action)
		{
			sig.addParameter(getModelUpdateName(), "upd", "Update interface");
			sig.addImport(getModelUpdateImport());
			sig.setReturnDescription("Return 0 to commit or one of the error codes to roll back");
		}
		else
		{
			sig.addParameter(getModelViewName(), "view", "View interface");
			sig.addImport(getModelViewImport());
			sig.setReturnDescription("Return 0 if succesful or one of the error codes if not");
		}
		m.appendToBody("// Implementation of " + method.getName() + "\n");
		
		m.appendToBody("// Return one of the following error codes:\n");
		m.appendToBody("// OK\n");
		int count = method.getNumberOfErrorCodes();
		for(int i = 0; i < count; ++i)
		{
			IMetaErrorCode errorCode = method.getErrorCode(i);
			m.appendToBody("// " + errorCode.getMessage().replace(' ', '_').toUpperCase() + "\n");
		}
		m.appendToBody("\n// If an error needs extra explanation, use: setErrorDescription(\"Extra info\");\n\n");
		m.appendToBody("// WARNING : Any code outside the following begin and end tags\n");
		m.appendToBody("// will be lost when re-generation occurs.\n");
		m.appendToBody("\n// EDMA_non-generated_code_begin\n");
		m.appendToBody("\"TODO : put your implementation of " + method.getName() + " here..\";\n");
		m.appendToBody("\n// EDMA_non-generated_code_end\n");
		user_impl.addMethod(m);
	}

	private void getOutput(	IMetaMethodParameter param,
							JavaClass impl,
							JavaInterface intf)
	{
		JavaMethodSignature sig = new JavaMethodSignature("get"
				+ StringUtil.U(param.getName()), "Returns the output "
				+ param.getName() + ":" + param.getValueDomain().getName());
		sig.setReturnType(getIntfName(param.getValueDomain()));
		sig.setReturnDescription("The output " + param.getName() + ":"
				+ param.getValueDomain().getName());
		sig.addImport(getIntfImport(param.getValueDomain()));
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("if(errorCode() != 0) return null;\n");
		m.appendToBody("return out_" + param.getName() + ";\n");
		impl.addMethod(m);
		intf.addMethod(sig);
	}

	private void execute(JavaClass impl)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"execute",
															"Execution of the "
																	+ (action ? "action"
																			: "view"));
		JavaMethod m = new JavaMethod(sig);
		if(action)
		{
			impl.addImplements("IAction");
			impl.addImport(EdmaImport.IAction);
			sig.addParameter(	"IDataModelUpdate",
								"edma_dm",
								"Data model update interface");
			sig.addImport(EdmaImport.IDataModelUpdate);
			sig.setReturnType("boolean");
			sig.setReturnDescription("Return <tt>true</tt> to commit or <tt>false</tt> to rollback");
			
			m.appendToBody("int e = edma_userMethod.execute(new "
					+ getModelImplName() + "(edma_dm));\n");
			checkAndSetErrorCode(m);
			checkOutputParameters(m);
			m.appendToBody("return (e == 0);\n");
			m.addImport(getModelImplImport());
		}
		else
		{
			impl.addImplements("IView");
			impl.addImport(EdmaImport.IView);
			sig.addParameter(	"IDataModelView",
								"edma_dm",
								"Data model view interface");
			sig.addImport(EdmaImport.IDataModelView);
			sig.setReturnType("void");
			m.appendToBody("int e = edma_userMethod.execute(new " + getModelImplName()
					+ "(edma_dm));\n");
			checkAndSetErrorCode(m);
			checkOutputParameters(m);
			m.addImport(getModelImplImport());
		}
		impl.addMethod(m);
	}

	private void checkOutputParameters(JavaMethod m)
	{
		boolean first = true;
		int size = method.getNumberOfOutputParameters();
		for(int i = 0; i < size; ++i)
		{
			IMetaMethodParameter param = method.getOutputParameter(i);
			if(!param.canBeNull())
			{
				if(first)
				{
					first = false;
					m.appendToBody("if(e == 0)\n{\n");
				}
				m.appendToBody(i1 + "if(edma_userMethod.get"
						+ StringUtil.U(param.getName()) + "() == null)\n" + i1 + "{\n");
				m.appendToBody(i2 + "throw new RuntimeException(\"Error in "
						+ method.getName() + ": Output parameter "
						+ param.getName() + " is null\");\n" + i1 + "}\n");
			}
		}
		if(!first) m.appendToBody("}\n");
	}
	
	private void checkAndSetErrorCode(JavaMethod m)
	{
		m.appendToBody("switch(e)\n{\n");
		m.appendToBody(i1 + "case 0:\n");
		m.appendToBody(i2 + "edma_userMethod.setErrorCode(0, \"OK\");\n");
		m.appendToBody(i2 + "break;\n");
		int size = method.getNumberOfErrorCodes();
		for(int i = 0; i < size; ++i)
		{
			IMetaErrorCode ec = method.getErrorCode(i);
			m.appendToBody(i1 + "case " + ec.getCode() + ":\n");
			m.appendToBody(i2 + "edma_userMethod.setErrorCode(" + ec.getCode() + ", \"" + ec.getMessage() + "\");\n");
			m.appendToBody(i2 + "break;\n");
		}
		m.appendToBody(i1 + "default:\n");
		m.appendToBody(i2 + "throw new RuntimeException(\"Error in " + method.getName() + ": Invalid error code: \" + e);\n");
		m.appendToBody("}\n");
	}

	private void getUserMethod(JavaClass impl)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"getUserMethod",
															"Returns the user method");
		sig.setReturnType(getMethodUserClassName(method.getName(), action));
		sig.setReturnDescription("The user method");
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return edma_userMethod;\n");
		impl.addMethod(m);
	}

	private void addMethodToModel(JavaInterface modelIntf, JavaClass modelImpl)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	getMethodName(	method.getName(),
																			action),
															method.getDescription());
		sig.setReturnType(getMethodResultIntfName(method.getName(), action));
		sig.addImport(getMethodResultIntfImport(method.getName(), action));
		sig.addException("IOException");
		sig.addImport("java.io.IOException");
		JavaMethod m = new JavaMethod(sig);
		// Test input parameters for null
		int size = method.getNumberOfInputParameters();
		for(int i = 0; i < size; ++i)
		{
			IMetaMethodParameter param = method.getInputParameter(i);
			if(!param.canBeNull())
			{
				m.appendToBody("if(" + param.getName() + " == null)\n{\n");
				m.appendToBody("    throw new NullPointerException(\""
						+ param.getName() + " may not be null\");\n}\n");

			}
		}

		m.appendToBody(getMethodUserClassName(method.getName(), action)
				+ " edma_userMethod = new "
				+ getMethodUserClassName(method.getName(), action) + "(");
		m.addImport(getMethodUserClassImport(method.getName(), action));

		for(int i = 0; i < size; ++i)
		{
			IMetaMethodParameter param = method.getInputParameter(i);
			sig.addParameter(	getIntfName(param.getValueDomain()),
								param.getName(),
								"Input parameter " + param.getName());
			sig.addImport(getIntfImport(param.getValueDomain()));
			if(i > 0) m.appendToBody(", ");
			m.appendToBody(param.getName());
		}
		m.appendToBody(");\n");
		m.appendToBody(getMethodEdmaClassName(method.getName(), action)
				+ " edma_method = new "
				+ getMethodEdmaClassName(method.getName(), action)
				+ "(edma_userMethod);\n");
		m.addImport(getMethodEdmaClassImport(method.getName(), action));
		m.appendToBody("edma_dmi.execute(edma_method);\n");
		m.appendToBody("return edma_method.getUserMethod();\n");
		modelIntf.addMethod(sig);
		modelImpl.addMethod(m);
	}
}
