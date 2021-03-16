package org.abstractica.edma.generator.metamodel.remote;

import java.util.ArrayList;

import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaMethod;
import org.abstractica.edma.generator.common.JavaMethodSignature;
import org.abstractica.edma.generator.metamodel.AModelGenerator;
import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaMethod;
import org.abstractica.edma.metamodel.IMetaMethodParameter;
import org.abstractica.edma.valuedomains.IMetaValueDomain;

public class ClientInstanceGenerator extends AModelGenerator
{
	private static final String i1 = "    ";
	//private ArrayList<JavaClass> autoClasses;
	//private ArrayList<JavaInterface> autoIntf;

	public ClientInstanceGenerator(	IPackageStructure pkgStruct,
									IMetaDataModel model,
									ArrayList<JavaClass> autoClasses)
	{
		super(pkgStruct, model);
		//this.autoClasses = autoClasses;
		//this.autoIntf = autoIntf;
		JavaClass impl = new JavaClass(getClientInstanceImplName());
		impl.setPackage(getClientInstanceImplPackage());
		impl.addImplements(getAPIIntfName());
		impl.addImport(getAPIIntfImport());
		impl.addConstructorField(	"String",
									"edma_hostname",
									"Name of the server host");
		impl.addConstructorField("int", "edma_port", "port to connect to");

		int methodID = 0;
		int viewCount = model.getNumberOfViews();
		for(int i = 0; i < viewCount; ++i)
		{
			IMetaMethod view = model.getView(i);
			method(methodID++, view, false, impl);
		}

		int actionCount = model.getNumberOfActions();
		for(int i = 0; i < actionCount; ++i)
		{
			IMetaMethod action = model.getAction(i);
			method(methodID++, action, true, impl);
		}

		autoClasses.add(impl);
	}

	private void method(int methodID,
						IMetaMethod method,
						boolean action,
						JavaClass impl)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	getMethodName(	method.getName(),
																			action),
															method.getDescription());
		sig.setReturnType(getMethodResultIntfName(method.getName(), action));
		sig.addImport(getMethodResultIntfImport(method.getName(), action));
		sig.addException("IOException");
		sig.addImport("java.io.IOException");
		//Set<String> vdNames = new HashSet<>();
		//Collection<IMetaValueDomain> valueDomains = new ArrayList<>();
		int inSize = method.getNumberOfInputParameters();
		for(int i = 0; i < inSize; ++i)
		{
			IMetaMethodParameter param = method.getInputParameter(i);
			IMetaValueDomain vd = param.getValueDomain();

			sig.addParameter(	getIntfName(vd),
								param.getName(),
								"Input parameter " + param.getName());
			sig.addImport(getIntfImport(vd));
			/*
			if(!vdNames.contains(vd.getName()))
			{
				valueDomains.add(vd);
			}
			*/
		}
		//new Edma_ValueDomainGenerator(pkgStruct,valueDomains,)
		JavaMethod m = new JavaMethod(sig);
		m.addImport("java.io.BufferedInputStream");
		m.addImport("java.io.BufferedOutputStream");
		m.addImport("java.io.DataInput");
		m.addImport("java.io.DataInputStream");
		m.addImport("java.io.DataOutput");
		m.addImport("java.io.DataOutputStream");
		m.addImport("java.net.Socket");
		for(int i = 0; i < inSize; ++i)
		{
			IMetaMethodParameter param = method.getInputParameter(i);
			if(!param.canBeNull())
			{
				m.appendToBody("if(" + param.getName() + " == null)\n{\n");
				m.appendToBody(i1 + "throw new NullPointerException(\""
						+ param.getName() + " may not be null\");\n}\n");
			}
		}
		m.appendToBody("Socket edma_socket = new Socket(edma_hostname, edma_port);\n");
		m.appendToBody("BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());\n");
		m.appendToBody("DataInput edma_in = new DataInputStream(buf_in);\n");
		m.appendToBody("BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());\n");
		m.appendToBody("DataOutput edma_out = new DataOutputStream(buf_out);\n");
		m.appendToBody("edma_out.writeInt(" + methodID + ");\n");
		for(int i = 0; i < inSize; ++i)
		{
			IMetaMethodParameter param = method.getInputParameter(i);
			if(param.canBeNull())
			{
				m.appendToBody("if(" + param.getName() + " == null)\n{\n");
				m.appendToBody(i1
						+ "edma_out.writeBoolean(false);\n}\nelse\n{\n");
				m.appendToBody(i1 + "edma_out.writeBoolean(true);\n");
				m.appendToBody(i1 + param.getName()
						+ ".toStream(edma_out);\n}\n");
			}
			else
			{
				m.appendToBody(param.getName() + ".toStream(edma_out);\n");
			}
		}
		m.appendToBody("buf_out.flush();\n");
		m.appendToBody("if(!edma_in.readBoolean())\n{\n");
		m.appendToBody(i1 + "String edma_error = edma_in.readUTF();\n");
		m.appendToBody(i1 + "buf_out.close();\n");
		m.appendToBody(i1 + "buf_in.close();\n");
		m.appendToBody(i1 + "edma_socket.close();\n");
		m.appendToBody(i1 + "throw new IOException(edma_error);\n}\n");

		m.appendToBody("int errorCode = edma_in.readInt();\n");
		m.appendToBody("String errorMsg = edma_in.readUTF();\n");
		m.appendToBody("String errorDesc = null;\n");
		m.appendToBody("if(edma_in.readBoolean())\n{\n");
		m.appendToBody(i1 + "errorDesc = edma_in.readUTF();\n");
		m.appendToBody("}\n");

		int outSize = method.getNumberOfOutputParameters();
		for(int i = 0; i < outSize; ++i)
		{
			IMetaMethodParameter param = method.getOutputParameter(i);
			m.addImport(getIntfImport(param.getValueDomain()));
			if(param.canBeNull())
			{
				m.appendToBody(getIntfName(param.getValueDomain()) + " out_"
						+ param.getName() + " = null;\n");
				m.appendToBody("if(edma_in.readBoolean()) out_"
						+ param.getName() + " = "
						+ getIntfName(param.getValueDomain())
						+ ".fromStreamNoValidate(edma_in);\n");
			}
			else
			{
				m.appendToBody(getIntfName(param.getValueDomain()) + " out_"
						+ param.getName() + " = "
						+ getIntfName(param.getValueDomain())
						+ ".fromStreamNoValidate(edma_in);\n");
			}

		}
		m.appendToBody("buf_out.close();\n");
		m.appendToBody("buf_in.close();\n");
		m.appendToBody("edma_socket.close();\n");
		m.appendToBody("return new " + getRemoteMethodResultImplName(method)
				+ "(errorCode, errorMsg, errorDesc");
		for(int i = 0; i < outSize; ++i)
		{
			IMetaMethodParameter param = method.getOutputParameter(i);
			m.appendToBody(", ");
			m.appendToBody("out_" + param.getName());
		}
		m.appendToBody(");\n");
		impl.addMethod(m);
	}
}
