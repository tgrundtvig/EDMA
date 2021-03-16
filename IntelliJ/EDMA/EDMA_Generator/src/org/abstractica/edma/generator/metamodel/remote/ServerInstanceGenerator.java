package org.abstractica.edma.generator.metamodel.remote;

import java.util.ArrayList;

import org.abstractica.edma.generator.EdmaImport;
import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaMethod;
import org.abstractica.edma.generator.common.JavaMethodSignature;
import org.abstractica.edma.generator.metamodel.AModelGenerator;
import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaMethod;
import org.abstractica.edma.metamodel.IMetaMethodParameter;
import org.abstractica.edma.util.StringUtil;

public class ServerInstanceGenerator extends AModelGenerator
{
	private static final String i1 = "    ";
	private static final String i2 = i1 + i1;
	private static final String i3 = i2 + i1;

	public ServerInstanceGenerator(	IPackageStructure pkgStruct,
									IMetaDataModel model,
									ArrayList<JavaClass> autoClasses)
	{
		super(pkgStruct, model);
		JavaClass impl = new JavaClass(getServerInstanceImplName());
		impl.setPackage(getServerInstanceImplPackage());
		impl.addImplements("IServerInstance");
		impl.addImport(EdmaImport.IServerInstance);
		impl.addConstructorField(	getAPIIntfName(),
									"edma_inst",
									"The data model instance");
		impl.addImport(getAPIIntfImport());
		call(impl);
		autoClasses.add(impl);
	}

	private void call(JavaClass impl)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"call",
															"call a method");
		sig.addParameter("int", "methodID", "ID of the method to call");
		sig.addParameter(	"DataInput",
							"edma_in",
							"Stream to read input parameters from");
		sig.addParameter(	"DataOutput",
							"edma_out",
							"Stream to write output parameters to");
		sig.setReturnType("void");
		sig.addException("IOException");
		sig.addImport("java.io.DataInput");
		sig.addImport("java.io.DataOutput");
		sig.addImport("java.io.IOException");

		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("switch(methodID)\n{\n");
		int methodID = 0;
		int viewCount = model.getNumberOfViews();
		for(int i = 0; i < viewCount; ++i)
		{
			IMetaMethod view = model.getView(i);
			appendCase(methodID++, view, false, m);
		}
		int actionCount = model.getNumberOfActions();
		for(int i = 0; i < actionCount; ++i)
		{
			IMetaMethod action = model.getAction(i);
			appendCase(methodID++, action, true, m);
		}
		m.appendToBody(i1 + "default:\n");
		m.appendToBody(i2
				+ "throw new RuntimeException(\"Invalid method ID: \" + methodID);\n");
		m.appendToBody("}");
		impl.addMethod(m);
	}

	private void appendCase(int caseNumber,
							IMetaMethod method,
							boolean isAction,
							JavaMethod m)
	{
		m.appendToBody(i1 + "case " + caseNumber + ":\n" + i1 + "{\n");
		// Input parameters...
		int inSize = method.getNumberOfInputParameters();
		for(int j = 0; j < inSize; ++j)
		{
			IMetaMethodParameter param = method.getInputParameter(j);
			m.addImport(getIntfImport(param.getValueDomain()));
			if(param.canBeNull())
			{
				m.appendToBody(i2 + getIntfName(param.getValueDomain())
						+ " in_" + param.getName() + " = null;\n");
				m.appendToBody(i2 + "if(edma_in.readBoolean()) in_"
						+ param.getName() + " = "
						+ getIntfName(param.getValueDomain())
						+ ".fromStreamNoValidate(edma_in);\n");
			}
			else
			{
				m.appendToBody(i2 + getIntfName(param.getValueDomain())
						+ " in_" + param.getName() + " = "
						+ getIntfName(param.getValueDomain())
						+ ".fromStreamNoValidate(edma_in);\n");
			}

		}
		m.appendToBody(i2 + getMethodResultIntfName(method.getName(), isAction)
				+ " edma_res = edma_inst."
				+ getMethodName(method.getName(), isAction) + "(");
		for(int j = 0; j < inSize; ++j)
		{
			IMetaMethodParameter param = method.getInputParameter(j);
			if(j > 0) m.appendToBody(", ");
			m.appendToBody("in_" + param.getName());
		}
		m.appendToBody(");\n");
		m.addImport(getMethodResultIntfImport(method.getName(), isAction));

		// Error code, error message and error description
		m.appendToBody(i2 + "edma_out.writeBoolean(true);\n");
		m.appendToBody(i2 + "edma_out.writeInt(edma_res.errorCode());\n");
		m.appendToBody(i2 + "edma_out.writeUTF(edma_res.errorMessage());\n");
		m.appendToBody(i2 + "if(edma_res.errorDescription() != null)\n" + i2
				+ "{\n");
		m.appendToBody(i3 + "edma_out.writeBoolean(true);\n");
		m.appendToBody(i3 + "edma_out.writeUTF(edma_res.errorDescription());\n"
				+ i2 + "}\n");
		m.appendToBody(i2 + "else\n" + i2 + "{\n");
		m.appendToBody(i3 + "edma_out.writeBoolean(false);\n" + i2 + "}\n");

		// Output parameters
		int outSize = method.getNumberOfOutputParameters();
		for(int j = 0; j < outSize; ++j)
		{
			IMetaMethodParameter param = method.getOutputParameter(j);
			m.appendToBody(i2 + getIntfName(param.getValueDomain()) + " out_"
					+ param.getName() + " = edma_res.get"
					+ StringUtil.U(param.getName()) + "();\n");
			if(param.canBeNull())
			{
				m.appendToBody(i2 + "if(out_" + param.getName() + " == null)\n"
						+ i2 + "{\n");
				m.appendToBody(i3 + "edma_out.writeBoolean(false);\n" + i2
						+ "}\n" + i2 + "else\n" + i2 + "{\n");
				m.appendToBody(i3 + "edma_out.writeBoolean(true);\n");
				m.appendToBody(i3 + "out_" + param.getName()
						+ ".toStream(edma_out);\n" + i2 + "}\n");
			}
			else
			{
				m.appendToBody(i2 + "out_" + param.getName()
						+ ".toStream(edma_out);\n");
			}
			m.addImport(getIntfImport(param.getValueDomain()));
		}
		m.appendToBody(i2 + "break;\n" + i1 + "}\n");
	}
}
