package org.abstractica.edma.generator.valuedomains;

import java.util.ArrayList;
import java.util.Collection;

import org.abstractica.edma.generator.AGenerator;
import org.abstractica.edma.generator.EdmaImport;
import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaMethod;
import org.abstractica.edma.generator.common.JavaMethodSignature;
import org.abstractica.edma.util.StringUtil;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.impl.Constraint;

public class ConstraintGenerator extends AGenerator
{
	private static final String i1 = "    ";
	private static final String i2 = i1 + i1;

	private final ArrayList<JavaClass> constraintClasses;
	private final JavaClass externalConstraints;

	public ConstraintGenerator(	IPackageStructure pkgStruct,
								Collection<IMetaValueDomain> valueDomains)
	{
		super(pkgStruct);
		constraintClasses = new ArrayList<JavaClass>();

		// External constrint class
		externalConstraints = new JavaClass(getExternalConstraintsClassName());
		externalConstraints.setPackage(getExternalConstraintsPackage());
		externalConstraints.addImplements("IExternalConstraints");
		externalConstraints.addField(	"public",
										true,
										true,
										"IExternalConstraints",
										"instance",
										"new "
												+ getExternalConstraintsClassName()
												+ "()");
		externalConstraints.addImport(EdmaImport.IExternalConstraints);
		// public int checkConstraints(Object value, int valueDomainIndex);
		JavaMethodSignature checkSig = new JavaMethodSignature(	"checkConstraints",
																"check external constraints");
		checkSig.addParameter("Object", "edma_obj", "The value object to check");
		checkSig.addParameter("int", "edma_index", "Value domain index");
		checkSig.setReturnType("Pair<Integer, String>");
		checkSig.addImport(EdmaImport.Pair);
		checkSig.setReturnDescription("A pair of the index of the first constraint that is "
				+ "violated and the error message or <tt>null</tt> if no constraints are violated");
		JavaMethod check = new JavaMethod(checkSig);
		check.appendToBody("switch(edma_index)\n{\n");
		for(IMetaValueDomain vd : valueDomains)
		{
			if(vd.getConstraints() == null) continue;
			check.appendToBody(i1 + "case " + vd.getIndex() + ":\n" + i1
					+ "{\n");
			String varName = StringUtil.L(vd.getName());
			check.appendToBody(i2 + getIntfName(vd) + " " + varName
					+ " = new " + getImplName(vd) + "(edma_obj);\n");
			check.addImport(getIntfImport(vd));
			check.addImport(getImplImport(vd));
			check.appendToBody(i2 + "String edma_reason;\n");
			Constraint[] constraints = vd.getConstraints();
			int numberOfConstraints = constraints.length;
			for(int cindex = 0; cindex < numberOfConstraints; ++cindex)
			{
				Constraint c = constraints[cindex];
				check.appendToBody(i2 + "edma_reason = "
						+ getConstraintClassName(vd, c.getName()) + ".check"
						+ StringUtil.U(c.getName()) + "(" + varName + ");\n");
				check.addImport(getConstraintImport(vd, c.getName()));
				check.appendToBody(i2
						+ "if(edma_reason != null) return new Pair<Integer, String>("
						+ cindex + ", edma_reason);\n");
			}
			check.appendToBody(i2 + "return null;\n" + i1 + "}\n");

		}
		check.appendToBody(i1 + "default :\n" + i2
				+ "throw new RuntimeException(\"Internal Error!\");\n}\n");
		externalConstraints.addMethod(check);

		// User classes
		for(IMetaValueDomain vd : valueDomains)
		{
			if(vd.getConstraints() == null) continue;

			for(Constraint c : vd.getConstraints())
			{
				JavaClass userClass = new JavaClass(getConstraintClassName(	vd,
																			c.getName()));
				String desc = "This class is the implementation class for the "
						+ vd.getName() + " constraint " + c.getName() + "\n"
						+ c.getDescription();
				userClass.setDescription(desc);
				userClass.setPackage(getConstraintPackage(vd, c.getName()));
				userClass.noConstructor();
				JavaMethodSignature sig = new JavaMethodSignature("check"
						+ StringUtil.U(c.getName()), "Checks the "
						+ c.getName() + " constraint for the " + vd.getName()
						+ " value domain.\n" + c.getDescription());
				sig.setReturnType("String");
				sig.setReturnDescription("the reason the constraint is violated, or <tt>null</tt> if the constraint is not violated");
				sig.addParameter(	getIntfName(vd),
									StringUtil.L(vd.getName()),
									"The instance value to be checked");
				sig.addImport(getIntfImport(vd));
				JavaMethod m = new JavaMethod(sig);
				m.setStatic(true);
				m.appendToBody("// Implementation of constraint " + c.getName()
						+ "\n");
				m.appendToBody("// WARNING : Any code outside the following begin and end tags\n");
				m.appendToBody("// will be lost when re-generation occurs.\n");
				m.appendToBody("\n// EDMA_non-generated_code_begin\n\n");
				m.appendToBody("\"TODO: Implement the constraint "
						+ c.getName() + " here...\";\n");
				m.appendToBody("\n// EDMA_non-generated_code_end\n");
				userClass.addMethod(m);
				constraintClasses.add(userClass);
			}
		}
	}

	public Collection<JavaClass> getUserConstraintClasses()
	{
		return constraintClasses;
	}

	public JavaClass getExternalConstraintsClass()
	{
		return externalConstraints;
	}
}
