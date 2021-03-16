package org.abstractica.edma.generator.metamodel.kind;

import java.util.ArrayList;

import org.abstractica.edma.generator.EdmaImport;
import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaMethod;
import org.abstractica.edma.generator.common.JavaMethodSignature;
import org.abstractica.edma.generator.metamodel.AModelGenerator;
import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaKind;
import org.abstractica.edma.util.StringUtil;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.impl.Constraint;

public class KindConstraintsGenerator extends AModelGenerator
{
	private static final String i1 = "    ";
	private static final String i2 = i1 + i1;

	protected KindConstraintsGenerator(	IPackageStructure pkgStruct,
										IMetaDataModel model,
										ArrayList<JavaClass> autoClasses,
										ArrayList<JavaClass> userClasses)
	{
		super(pkgStruct, model);
		int numberOfKinds = model.getNumberOfKinds();
		for(int i = 0; i < numberOfKinds; ++i)
		{
			IMetaKind kind = model.getKind(i);
			int numberOfConstraints = kind.getNumberOfConstraints();
			for(int j = 0; j < numberOfConstraints; ++j)
			{
				Constraint c = kind.getConstraint(j);
				userClasses.add(getUserConstraintClass(kind, c));
			}
		}
		autoClasses.add(getEDMAConstraintClass());
	}

	private JavaClass getEDMAConstraintClass()
	{
		// External constraint class
		JavaClass res = new JavaClass(getKindConstraintsClassName());
		res.setPackage(getKindConstraintsPackage());
		res.addImplements("IKindConstraints");
		res.addField(	"public",
						true,
						true,
						"IKindConstraints",
						"instance",
						"new " + getKindConstraintsClassName() + "()");
		res.addImport(EdmaImport.IKindConstraints);

		JavaMethodSignature checkSig = new JavaMethodSignature(	"checkConstraints",
																"check Kind constraints");
		checkSig.addParameter("Object", "edma_obj", "The kind object to check");
		checkSig.addParameter("int", "edma_index", "Kind value domain index");
		checkSig.setReturnType("Pair<Integer, String>");
		checkSig.addImport(EdmaImport.Pair);
		checkSig.setReturnDescription("A pair of the index of the first constraint that is "
				+ "violated and the error message or <tt>null</tt> if no constraints are violated");
		JavaMethod check = new JavaMethod(checkSig);
		check.appendToBody("switch(edma_index)\n{\n");
		int numberOfKinds = model.getNumberOfKinds();
		for(int kindIndex = 0; kindIndex < numberOfKinds; ++kindIndex)
		{
			IMetaKind kind = model.getKind(kindIndex);
			int numberOfConstraints = kind.getNumberOfConstraints();
			if(numberOfConstraints == 0) continue;
			IMetaValueDomain vd = kind.getValueDomain();
			check.appendToBody(i1 + "case " + kind.getArrayPosition() + ":\n" + i1
					+ "{\n");
			String varName = StringUtil.L(vd.getName());
			check.appendToBody(i2 + getIntfName(vd) + " " + varName + " = new "
					+ getImplName(vd) + "(edma_obj);\n");
			check.addImport(getIntfImport(vd));
			check.addImport(getImplImport(vd));
			check.appendToBody(i2 + "String edma_reason;\n");

			for(int cindex = 0; cindex < numberOfConstraints; ++cindex)
			{
				Constraint c = kind.getConstraint(cindex);
				check.appendToBody(i2 + "edma_reason = "
						+ getConstraintClassName(kind, c.getName()) + ".check"
						+ StringUtil.U(c.getName()) + "(" + varName + ");\n");
				check.addImport(getConstraintImport(kind, c.getName()));
				check.appendToBody(i2
						+ "if(edma_reason != null) return new Pair<Integer, String>("
						+ cindex + ", edma_reason);\n");
			}
			check.appendToBody(i2 + "return null;\n" + i1 + "}\n");

		}
		check.appendToBody(i1 + "default :\n" + i2
				+ "throw new RuntimeException(\"Internal Error!\");\n}\n");
		res.addMethod(check);
		return res;
	}

	private JavaClass getUserConstraintClass(IMetaKind kind, Constraint c)
	{
		JavaClass res = new JavaClass(c.getName());
		res.setPackage(getConstraintPackage(kind, c.getName()));
		String desc = "This class is the implementation class for the "
				+ kind.getName() + " constraint " + c.getName() + "\n"
				+ c.getDescription();
		res.setDescription(desc);
		res.noConstructor();
		JavaMethodSignature sig = new JavaMethodSignature("check"
				+ StringUtil.U(c.getName()), "Checks the " + c.getName()
				+ " constraint for the " + kind.getName() + " kind.\n"
				+ c.getDescription());
		sig.setReturnType("String");
		sig.setReturnDescription("the reason the constraint is violated, or <tt>null</tt> if the constraint is not violated");
		sig.addParameter(	getIntfName(kind.getValueDomain()),
							StringUtil.L(kind.getName()),
							"The instance value to be checked");
		sig.addImport(getIntfImport(kind.getValueDomain()));
		JavaMethod m = new JavaMethod(sig);
		m.setStatic(true);
		m.appendToBody("// Implementation of constraint " + c.getName() + "\n");
		m.appendToBody("// WARNING : Any code outside the following begin and end tags\n");
		m.appendToBody("// will be lost when re-generation occurs.\n");
		m.appendToBody("\n// EDMA_non-generated_code_begin\n\n");
		m.appendToBody("//TODO: Implement the constraint " + c.getName()
				+ " here...\n");
		m.appendToBody("return \"Constraint not implemented. Implement in "
				+ getConstraintImport(kind, c.getName()) + "\";\n");
		m.appendToBody("\n// EDMA_non-generated_code_end\n");
		res.addMethod(m);
		return res;
	}
}
