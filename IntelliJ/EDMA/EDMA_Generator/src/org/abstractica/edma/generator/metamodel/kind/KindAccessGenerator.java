package org.abstractica.edma.generator.metamodel.kind;

import org.abstractica.edma.generator.EdmaImport;
import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaInterface;
import org.abstractica.edma.generator.common.JavaMethod;
import org.abstractica.edma.generator.common.JavaMethodSignature;
import org.abstractica.edma.generator.metamodel.AModelGenerator;
import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaIndex;
import org.abstractica.edma.metamodel.IMetaKind;

public class KindAccessGenerator extends AModelGenerator
{
	private JavaInterface intf;
	private JavaClass impl;
	private IMetaKind kind;

	protected KindAccessGenerator(	IPackageStructure pkgStruct,
									IMetaDataModel model,
									IMetaKind kind)
	{
		super(pkgStruct, model);
		this.kind = kind;
		// Interface and class
		intf = new JavaInterface(	getKindIntfName(kind),
									"Interface for the kind: " + kind.getName());
		intf.setPackage(getKindIntfPackage(kind));
		impl = new JavaClass(getKindImplName(kind));
		impl.setPackage(getKindImplPackage(kind));
		impl.addImplements(intf.getName());
		impl.addImport(getKindIntfImport(kind));

		impl.addConstructorField("int", "edma_kindIndex", "kind index");
		impl.addConstructorField(	"IDataModelView",
									"edma_dmview",
									"Internal runtime interface");
		impl.addImport(EdmaImport.IDataModelView);

		getFromID();
		getEmptySet();
		getAll();


		// Indexes
		int size = kind.getNumberOfIndexes();
		for(int i = 0; i < size; ++i)
		{
			IMetaIndex index = kind.getIndex(i);
			
			IndexGenerator ig = new IndexGenerator(pkgStruct, model, intf, impl, index, kind);
			ig.generateForKindIndex();
		}
	}

	public JavaInterface getIntf()
	{
		return intf;
	}

	public JavaClass getImpl()
	{
		return impl;
	}

	private void getFromID()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"getFromID",
															"Get an entity from its id.");
		sig.setReturnType(getViewIntfName(kind));
		sig.setReturnDescription("A viewer on the entity with the given ID or null if none exists.");
		sig.addParameter(	getIntfName(kind.getIDValueDomain()),
							"id",
							"The id of the entity to get");
		sig.addImport(getViewIntfImport(kind));
		sig.addImport(getIntfImport(kind.getIDValueDomain()));
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("IEntity res = edma_dmview.kindGetFromID(edma_kindIndex, id.value());\n");
		m.appendToBody("if(res == null) return null;\n");
		m.appendToBody("return new " + getImplName(kind)
				+ "(res, edma_dmview);\n");
		m.addImport(getImplImport(kind));
		m.addImport(EdmaImport.IEntity);
		impl.addMethod(m);
	}

	private void getEmptySet()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"getEmpty" + kind.getName() + "Set",
				"Returns an empty set of "
						+ kind.getName()
						+ " entities.");
		sig.setReturnType(getSetIntfName(kind));
		sig.addImport(getSetIntfImport(kind));
		sig.setReturnDescription("An empty set of " + kind.getName()
				+ " entities.");
		intf.addMethod(sig);
		JavaMethod m = new JavaMethod(sig);
		// Implementation
		m.appendToBody("int newSetID = edma_dmview.kindGetEmptySet("
				+ kind.getArrayPosition() + ");\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		m.addImport(getSetImplImport(kind));
		impl.addMethod(m);
	}

	private void getAll()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"getAll",
															"Returns the set of all "
																	+ kind.getName()
																	+ " entities.");
		sig.setReturnType(getSetIntfName(kind));
		sig.addImport(getSetIntfImport(kind));
		sig.setReturnDescription("The set of all " + kind.getName()
				+ " entities.");
		intf.addMethod(sig);
		JavaMethod m = new JavaMethod(sig);
		// Implementation
		m.appendToBody("int newSetID = edma_dmview.kindGetAll("
				+ kind.getArrayPosition() + ");\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		m.addImport(getSetImplImport(kind));
		impl.addMethod(m);
	}
}
