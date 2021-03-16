package edma.generator.metamodel.kind;

import edma.generator.EdmaImport;
import edma.generator.IPackageStructure;
import edma.generator.common.JavaClass;
import edma.generator.common.JavaInterface;
import edma.generator.common.JavaMethod;
import edma.generator.common.JavaMethodSignature;
import edma.generator.metamodel.AModelGenerator;
import edma.metamodel.IMetaAttribute;
import edma.metamodel.IMetaDataModel;
import edma.metamodel.IMetaKind;
import edma.util.StringUtil;

public class EntityAttributeUpdaterGenerator extends AModelGenerator
{
	private IMetaKind kind;
	private JavaInterface updateIntf;
	private JavaInterface updateIntfUnique;
	private JavaClass updateClass;
	private JavaClass updateClassUnique;

	public EntityAttributeUpdaterGenerator(	IPackageStructure pkgStruct,
											IMetaDataModel model,
											IMetaKind kind)
	{
		super(pkgStruct, model);
		this.kind = kind;
		updateIntf = new JavaInterface(	getAttUpdIntfName(kind),
										"Interface for updating attributes on an entity of kind: "
												+ kind.getName());
		updateIntfUnique = new JavaInterface(	getAttUpdUniqueIntfName(kind),
												"Interface for updating attributes on an entity of kind: "
														+ kind.getName());
		updateClass = new JavaClass(getAttUpdImplName(kind));
		updateClass.addImplements(updateIntf.getName());
		updateClass.addConstructorField("Long",
										"entityID",
										"ID of the entity to update");
		updateClass.addConstructorField("IDataModelUpdate",
										"edma_dmupdate",
										"Internal runtime interface");
		updateClass.addField(	"private",
		                     	"Map<Integer, Object>",
								"updateMap");
		updateClass.addConstructorBodyLine("this.updateMap = new HashMap<Integer, Object>();");
		updateClass.addImport("java.util.Map");
		updateClass.addImport("java.util.HashMap");
		updateClass.addImport(EdmaImport.IDataModelUpdate);
		updateClass.setScope("private");
		updateClass.getConstructor().getSignature().setScope("private");

		updateClassUnique = new JavaClass(getAttUpdUniqueImplName(kind));
		updateClassUnique.addImplements(updateIntfUnique.getName());
		updateClassUnique.addConstructorField(	"Long",
												"entityID",
												"ID of the entity to update");
		updateClassUnique.addConstructorField(	"Map<Integer, Object>",
												"updateMap",
												"Map of updates");
		updateClassUnique.addConstructorField(	"IDataModelUpdate",
												"edma_dmupdate",
												"Internal runtime interface");
		updateClassUnique.addImport("java.util.Map");
		updateClassUnique.addImport(EdmaImport.IDataModelUpdate);
		updateClassUnique.setScope("private");
		updateClassUnique.getConstructor().getSignature().setScope("private");
		int size = kind.getNumberOfAttributes();
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = kind.getAttribute(i);
			attribute(att);
		}
		save();
	}

	private JavaMethodSignature initSave()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"save",
															"Saves the changes to the data model.");
		sig.setReturnType("boolean");
		return sig;
	}

	private void save()
	{
		JavaMethodSignature plain = initSave();
		JavaMethodSignature unique = initSave();
		unique.addException("UniqueException");
		unique.addImport(EdmaImport.UniqueException);

		JavaMethod mPlain = new JavaMethod(plain);
		mPlain.appendToBody("return edma_dmupdate.entityUpdate("
				+ kind.getArrayPosition() + ", entityID, updateMap);\n");
		JavaMethod mUnique = new JavaMethod(unique);
		mUnique.appendToBody("return edma_dmupdate.entityUpdateUnique("
				+ kind.getArrayPosition() + ", entityID, updateMap);\n");

		updateIntf.addMethod(plain);
		updateIntfUnique.addMethod(unique);
		updateClass.addMethod(mPlain);
		updateClassUnique.addMethod(mUnique);
	}

	private JavaMethodSignature init(IMetaAttribute att)
	{
		String methodName = "set" + StringUtil.U(att.getName());
		String methodDesc = "Sets the attribute " + att.getName() + " on this "
				+ kind.getName();
		JavaMethodSignature sig = new JavaMethodSignature(	methodName,
															methodDesc);
		sig.addParameter(	att.getValueDomainName(),
							att.getName(),
							"The value to set");
		sig.setReturnDescription("Interface for chaining attribute updates");
		sig.addImport(getIntfImport(att.getValueDomain()));
		return sig;
	}

	private void attribute(IMetaAttribute att)
	{
		if(!att.isModifiable()) return;
		JavaMethodSignature uniqueSig = init(att);
		uniqueSig.setReturnType(getAttUpdUniqueIntfName(kind));
		JavaMethodSignature plainSig;
		if(att.isPartOfUnique())
		{
			plainSig = uniqueSig;
		}
		else
		{
			plainSig = init(att);
			plainSig.setReturnType(getAttUpdIntfName(kind));
		}
		updateIntf.addMethod(plainSig);
		updateIntfUnique.addMethod(uniqueSig);

		// Implementations
		JavaMethod mUnique = new JavaMethod(uniqueSig);
		mUnique.appendToBody("updateMap.put(" + att.getArrayPosition()
				+ ", ((IValueInstance) " + att.getName()
				+ ").edma_getValue());\n");
		mUnique.addImport(EdmaImport.IValueInstance);
		mUnique.appendToBody("return this;\n");
		JavaMethod mPlain;
		if(att.isPartOfUnique())
		{
			mPlain = new JavaMethod(uniqueSig);
			mPlain.appendToBody("updateMap.put(" + att.getArrayPosition()
					+ ", ((IValueInstance) " + att.getName()
					+ ").edma_getValue());\n");
			mPlain.addImport(EdmaImport.IValueInstance);
			mPlain.appendToBody("return new " + getAttUpdUniqueImplName(kind)
					+ "(entityID, updateMap, edma_dmupdate);\n");
		}
		else
		{
			mPlain = new JavaMethod(plainSig);
			mPlain.appendToBody("updateMap.put(" + att.getArrayPosition()
					+ ", ((IValueInstance) " + att.getName()
					+ ").edma_getValue());\n");
			mPlain.addImport(EdmaImport.IValueInstance);
			mPlain.appendToBody("return this;\n");
		}
		updateClass.addMethod(mPlain);
		updateClassUnique.addMethod(mUnique);
	}

	public JavaInterface getUpdateInterface()
	{
		return updateIntf;
	}

	public JavaInterface getUpdateInterfaceUnique()
	{
		return updateIntfUnique;
	}

	public JavaClass getUpdateClass()
	{
		return updateClass;
	}

	public JavaClass getUpdateClassUnique()
	{
		return updateClassUnique;
	}

}
