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
import org.abstractica.edma.util.StringUtil;

public class RemoteResultGenerator extends AModelGenerator
{

	public RemoteResultGenerator(	IPackageStructure pkgStruct,
									IMetaDataModel model,
									ArrayList<JavaClass> autoClasses)
	{
		super(pkgStruct, model);
		int viewCount = model.getNumberOfViews();
		for(int i = 0; i < viewCount; ++i)
		{
			IMetaMethod view = model.getView(i);
			autoClasses.add(methodResult(view, false));
		}
		int actionCount = model.getNumberOfActions();
		for(int i = 0; i < actionCount; ++i)
		{
			IMetaMethod action = model.getAction(i);
			autoClasses.add(methodResult(action, true));
		}
	}

	private JavaClass methodResult(IMetaMethod method, boolean isAction)
	{
		JavaClass impl = new JavaClass(getRemoteMethodResultImplName(method));
		impl.setPackage(getRemoteMethodResultImplPackage(method));
		impl.addImplements(getMethodResultIntfName(method.getName(), isAction));
		impl.addImport(getMethodResultIntfImport(method.getName(), isAction));
		//errorCode
		impl.addConstructorField("int", "edma_errorCode", "Error code");
		JavaMethodSignature errorCodeSig = new JavaMethodSignature("errorCode", "Get the error code");
		errorCodeSig.setReturnType("int");
		JavaMethod errorCodeMethod = new JavaMethod(errorCodeSig);
		errorCodeMethod.appendToBody("return edma_errorCode;\n");
		impl.addMethod(errorCodeMethod);
		//errorMessage
		impl.addConstructorField("String", "edma_errorMsg", "Error message");
		JavaMethodSignature errorMsgSig = new JavaMethodSignature("errorMessage", "Get the error message");
		errorMsgSig.setReturnType("String");
		JavaMethod errorMsgMethod = new JavaMethod(errorMsgSig);
		errorMsgMethod.appendToBody("return edma_errorMsg;\n");
		impl.addMethod(errorMsgMethod);
		//errorDescription
		impl.addConstructorField("String", "edma_errorDesc", "Error description");
		JavaMethodSignature errorDescSig = new JavaMethodSignature("errorDescription", "Get the error description");
		errorDescSig.setReturnType("String");
		JavaMethod errorDescMethod = new JavaMethod(errorDescSig);
		errorDescMethod.appendToBody("return edma_errorDesc;\n");
		impl.addMethod(errorDescMethod);
		
		int outSize = method.getNumberOfOutputParameters();
		for(int i = 0; i < outSize; ++i)
		{
			IMetaMethodParameter param = method.getOutputParameter(i);
			impl.addConstructorField(	getIntfName(param.getValueDomain()),
										param.getName(),
										"output parameter " + param.getName());
			impl.addImport(getIntfImport(param.getValueDomain()));
			JavaMethodSignature sig = new JavaMethodSignature("get"
					+ StringUtil.U(param.getName()), "get output parameter "
					+ param.getName());
			sig.setReturnType(getIntfName(param.getValueDomain()));
			sig.setReturnDescription("The output parameter " + param.getName());
			JavaMethod m = new JavaMethod(sig);
			m.appendToBody("return " + param.getName() + ";\n");
			impl.addMethod(m);
		}
		return impl;
	}
}
