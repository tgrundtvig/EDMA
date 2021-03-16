package org.abstractica.edma.generator.metamodel.model;

import org.abstractica.edma.generator.EdmaImport;
import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaInterface;
import org.abstractica.edma.generator.common.JavaMethod;
import org.abstractica.edma.generator.common.JavaMethodSignature;
import org.abstractica.edma.generator.metamodel.AModelGenerator;
import org.abstractica.edma.generator.metamodel.kind.EntityBuilderGenerator;
import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaKind;
import org.abstractica.edma.metamodel.IMetaSingleton;
import org.abstractica.edma.util.StringUtil;

public class ModelUpdaterGenerator extends AModelGenerator
{
	private JavaInterface intf;
	private JavaClass clazz;

	public ModelUpdaterGenerator(	IPackageStructure pkgStruct,
	                             	IMetaDataModel model,
									JavaClass implClass)
	{
		super(pkgStruct, model);

		intf = new JavaInterface(	getModelUpdateName(),
									"This is the updating interface for the "
											+ model.getName() + " data model.");
		intf.setPackage(getModelUpdatePackage());
		intf.addExtends(getModelViewName());
		intf.addImport(getModelViewImport());
		clazz = implClass;
		clazz.addImplements(intf.getName());
		clazz.addImport(getModelUpdateImport());
		
		int size = model.getNumberOfSingletons();
		for(int i = 0; i < size; ++i)
		{
			IMetaSingleton singleton = model.getSingleton(i);
			updateSingleton(singleton);
		}

		size = model.getNumberOfKinds();
		for(int i = 0; i < size; ++i)
		{
			IMetaKind kind = model.getKind(i);
			EntityBuilderGenerator kbg = new EntityBuilderGenerator(pkgStruct, model, kind);
			kbg.addToUpdater(intf, clazz);
			newKind(kind);
			deleteKind(kind);
			updateKind(kind);
		}

	}

	private void newKind(IMetaKind kind)
	{
		JavaMethodSignature sig = new JavaMethodSignature("new"
				+ kind.getName(), "Creates a new " + kind.getName()
				+ " from a value in the value domain "
				+ kind.getValueDomain().getName());
		sig.setReturnType(getUpdateIntfName(kind));
		sig.addImport(getUpdateIntfImport(kind));
		sig.setReturnDescription("The newly created "
				+ kind.getName());
		IMetaKind baseKind = kind.getBaseKind();
		if(baseKind != null)
		{
			sig.addParameter(	getViewIntfName(baseKind),
								StringUtil.L(baseKind.getName()),
								"The " + baseKind.getName() + " to extend.");
			sig.addImport(getViewIntfImport(baseKind));
		}
		sig.addParameter(	getIntfName(kind.getValueDomain()),
							StringUtil.L(kind.getName()),
							"The value for the new " + kind.getName() + ".");
		sig.addImport(getIntfImport(kind.getValueDomain()));
		if(kind.hasUniqueIndex())
		{
			sig.addException("UniqueException");
			sig.addImport(EdmaImport.UniqueException);
			sig.addThrowsDescription("Throws a UniqueException, if the new entity violates any of the unique indexes for this kind.");
		}
		intf.addMethod(sig);
		JavaMethod m = new JavaMethod(sig);
		m.addImport(getUpdateIntfImport(kind));
		// Implementation
		m.appendToBody("Object[] edma_tmpValues = (Object[]) ((IValueInstance) "
				+ StringUtil.L(kind.getName())
				+ ").edma_getValue();\n");
		m.appendToBody("Object[] edma_values = new Object[edma_tmpValues.length];\n");
		m.appendToBody("for(int i = 0; i < edma_tmpValues.length; ++i)\n");
		m.appendToBody("{\n");
		m.appendToBody("    edma_values[i] = edma_tmpValues[i];\n");
		m.appendToBody("}\n");
		m.addImport(EdmaImport.IValueInstance);
		m.appendToBody("IEntity edma_entity = edma_dmview.getUpdateInterface().entityNew");
		m.addImport(EdmaImport.IEntity);
		if(kind.hasUniqueIndex()) m.appendToBody("Unique");
		m.appendToBody("(" + kind.getArrayPosition() + ", edma_values);\n");
		m.appendToBody("return new " + getImplName(kind) + "(edma_entity, edma_dmview);\n");
		m.addImport(getImplImport(kind));

		clazz.addMethod(m);
	}

	private void deleteKind(IMetaKind kind)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"delete",
															"Deletes an entity of kind "
																	+ kind.getName());
		sig.setReturnType("boolean");
		sig.setReturnDescription("<tt>true</tt> if the entity existed and was deleted");
		sig.addParameter(	getViewIntfName(kind),
							StringUtil.L(kind.getName()),
							"The "
									+ kind.getName()
									+ " to be deleted. All extensions to this entity will also be deleted.");
		intf.addMethod(sig);
		JavaMethod m = new JavaMethod(sig);
		m.addImport(getViewIntfImport(kind));
		// Implementation
		// Delete all extensions
		m.appendToBody("IDataModelUpdate edma_dmupdate = edma_dmview.getUpdateInterface();\n");
		m.addImport(EdmaImport.IDataModelUpdate);
		int size = kind.getNumberOfExtensions();
		for(int i = 0; i < size; ++i)
		{
			IMetaKind ext = kind.getExtension(i);
			m.appendToBody("//Delete " + ext.getName() + " extension.\n");
			m.appendToBody("edma_dmupdate.entityDelete("
					+ ext.getArrayPosition() + ", "
					+ StringUtil.L(kind.getName()) + ".getID().value());\n");
		}
		m.appendToBody("//Delete the entity.\n");
		m.appendToBody("return edma_dmupdate.entityDelete("
				+ kind.getArrayPosition() + ", " + StringUtil.L(kind.getName())
				+ ".getID().value());\n");
		clazz.addMethod(m);
	}

	private void updateKind(IMetaKind kind)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"update",
															"Updates an entity of kind: "
																	+ kind.getName());
		sig.setReturnType(getUpdateIntfName(kind));
		sig.setReturnDescription("The updater interface for the entities of kind: "
				+ kind.getName());
		sig.addParameter(	getViewIntfName(kind),
							StringUtil.L(kind.getName()),
							"The entity to update");
		sig.addImport(getViewIntfImport(kind));
		sig.addImport(getUpdateIntfImport(kind));
		intf.addMethod(sig);
		JavaMethod m = new JavaMethod(sig);
		// Implementation
		m.appendToBody("return (" + getImplName(kind) + ") "
				+ StringUtil.L(kind.getName()) + ";\n");
		clazz.addMethod(m);
	}
	
	private void updateSingleton(IMetaSingleton singleton)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"update",
															"Updates the singleton: "
																	+ singleton.getName());
		sig.setReturnType(getUpdateIntfName(singleton));
		sig.setReturnDescription("The updater interface for the singleton: "
				+ singleton.getName());
		sig.addParameter(	getViewIntfName(singleton),
							StringUtil.L(singleton.getName()),
							"The singleton to update");
		sig.addImport(getViewIntfImport(singleton));
		sig.addImport(getUpdateIntfImport(singleton));
		intf.addMethod(sig);
		JavaMethod m = new JavaMethod(sig);
		// Implementation
		m.appendToBody("return (" + getImplName(singleton) + ") "
				+ StringUtil.L(singleton.getName()) + ";\n");
		clazz.addMethod(m);
	}

	public JavaInterface getIntf()
	{
		return intf;
	}

	public JavaClass getClazz()
	{
		return clazz;
	}

}
