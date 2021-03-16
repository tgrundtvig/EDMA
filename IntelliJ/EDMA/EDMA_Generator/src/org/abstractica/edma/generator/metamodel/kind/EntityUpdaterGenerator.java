package org.abstractica.edma.generator.metamodel.kind;

import org.abstractica.edma.generator.EdmaImport;
import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaInterface;
import org.abstractica.edma.generator.common.JavaMethod;
import org.abstractica.edma.generator.common.JavaMethodSignature;
import org.abstractica.edma.generator.metamodel.AModelGenerator;
import org.abstractica.edma.metamodel.IMetaAttribute;
import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaKind;
import org.abstractica.edma.metamodel.IMetaRelation;
import org.abstractica.edma.util.StringUtil;

public class EntityUpdaterGenerator extends AModelGenerator
{
	private JavaInterface intf;
	private JavaClass clazz;
	private IMetaKind kind;

	public EntityUpdaterGenerator(	IPackageStructure pkgStruct,
									IMetaDataModel model,
									IMetaKind kind,
									JavaClass impl)
	{
		super(pkgStruct, model);
		this.kind = kind;
		intf = new JavaInterface(	getUpdateIntfName(kind),
									"This is the update interface for "
											+ kind.getName());
		intf.addExtends(getViewIntfName(kind));
		intf.setPackage(getUpdateIntfPackage(kind));
		clazz = impl;
		clazz.addImplements(intf.getName());
		clazz.addImport(getUpdateIntfImport(kind));

		EntityAttributeUpdaterGenerator kaug = new EntityAttributeUpdaterGenerator(	pkgStruct,
																					model,
																					kind);
		

		int size = kind.getNumberOfAttributes();
		boolean canUpdate = false;
		boolean unique = false;
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = kind.getAttribute(i);
			if(att.isModifiable())
			{
				canUpdate = true;
				if(att.isPartOfUnique())
				{
					unique = true;
				}
			}
		}
		if(canUpdate) beginAttributeUpdate();
		if(canUpdate)
		{
			intf.addInnerInterface(kaug.getUpdateInterface());
			clazz.addInnerClass(kaug.getUpdateClass());
			if(unique)
			{
				intf.addInnerInterface(kaug.getUpdateInterfaceUnique());
				clazz.addInnerClass(kaug.getUpdateClassUnique());
			}
		}

		size = kind.getNumberOfRelationsAsA();
		for(int i = 0; i < size; ++i)
		{
			IMetaRelation asARel = kind.getRelationAsA(i);
			relationUpdate(asARel);
		}
	}

	private void beginAttributeUpdate()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"beginUpdate",
															"Begin attribute updates on this "
																	+ kind.getName());
		sig.setReturnType(getAttUpdIntfName(kind));
		sig.setReturnDescription("An attribute update interface for chaining attribute updates");
		intf.addMethod(sig);

		// Implementation
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return new " + getAttUpdImplName(kind)
				+ "(edma_entity.getID(), edma_dmview.getUpdateInterface());\n");
		clazz.addMethod(m);
	}

	private void relationUpdate(IMetaRelation asARel)
	{
		switch(asARel.getType())
		{
			case OneToOne:
			case OneToOneSame:
			case ManyToOne:
			{
				boolean unique = asARel.hasUniqueIndexOnA();
				String name = "set";
				String desc = "Sets the " + asARel.getRoleB() + " for this "
						+ kind.getName();
				if(!kind.getName().equals(StringUtil.U(asARel.getRoleA())))
				{
					name = "as" + StringUtil.U(asARel.getRoleA()) + "Set";
					desc += " acting as a " + asARel.getRoleA();
				}
				desc += ". If another " + asARel.getRoleB()
						+ " is already set, it will be replaced.\n";
				desc += " This method has been generated from the relation:\n "
						+ asARel.getName();
				JavaMethodSignature sig = new JavaMethodSignature(name
						+ StringUtil.U(asARel.getRoleB()), desc);
				sig.setReturnType(getViewIntfName(asARel.getKindB()));
				sig.setReturnDescription("The previous " + asARel.getRoleB()
						+ " connected to this " + asARel.getRoleA()
						+ " or null if none was connected.");
				sig.addParameter(	getViewIntfName(asARel.getKindB()),
									asARel.getRoleB(),
									"The "
											+ asARel.getRoleB()
											+ " to be set. Use null to remove any previous connection.");
				sig.addImport(getViewIntfImport(asARel.getKindB()));
				// Check for UniqueException
				if(unique)
				{
					sig.addException("UniqueException");
					sig.addImport(EdmaImport.UniqueException);
				}

				intf.addMethod(sig);
				// Implementation
				JavaMethod m = new JavaMethod(sig);
				m.appendToBody("Long b_ID = null;\n");
				m.appendToBody("if(" + asARel.getRoleB() + " != null)\n{\n");
				m.appendToBody("    b_ID = ((" + getImplName(asARel.getKindB())
						+ ") " + asARel.getRoleB()
						+ ").edma_entity.getID();\n}\n");
				m.appendToBody("Long res = edma_dmview.getUpdateInterface().relationReplaceB");
				if(unique) m.appendToBody("Unique");
				m.appendToBody("(" + asARel.getArrayPosition()
						+ ", edma_entity.getID(), b_ID);\n");
				m.appendToBody("if(res == null) return null;\n");
				m.appendToBody("IEntity ent = edma_dmview.kindGetFromID("
						+ asARel.getKindB().getArrayPosition() + ", res);\n");
				m.appendToBody("return new " + getImplName(asARel.getKindB())
						+ "(ent, edma_dmview);\n");
				m.addImport(getImplImport(asARel.getKindB()));
				clazz.addMethod(m);
				break;
			}
			case ManyToMany:
			case ManyToManySame:
			{
				boolean unique = asARel.hasUniqueIndexOnA()
						|| asARel.hasUniqueIndexOnB();
				// Add method
				String name = "add";
				String desc = "Connects the " + asARel.getRoleB() + " to this "
						+ kind.getName();
				if(!kind.getName().equals(StringUtil.U(asARel.getRoleA())))
				{
					name = "as" + StringUtil.U(asARel.getRoleA()) + "Add";
					desc += " acting as a " + asARel.getRoleA();
				}
				desc += ", if it is not already connected.\n";
				desc += " This method has been generated from the relation:\n "
						+ asARel.getName();
				JavaMethodSignature sig = new JavaMethodSignature(name
						+ StringUtil.U(asARel.getRoleB()), desc);
				sig.setReturnType("boolean");
				sig.setReturnDescription("<tt>true</tt> if this "
						+ asARel.getRoleA()
						+ " was not already connected to the specified "
						+ asARel.getRoleB());
				sig.addParameter(	getViewIntfName(asARel.getKindB()),
									asARel.getRoleB(),
									"The " + asARel.getRoleB()
											+ " to be added. May NOT be null.");
				sig.addImport(getViewIntfImport(asARel.getKindB()));
				// Check for UniqueException
				if(unique)
				{
					sig.addException("UniqueException");
					sig.addImport(EdmaImport.UniqueException);
				}

				intf.addMethod(sig);
				// Implementation
				JavaMethod m = new JavaMethod(sig);
				m.appendToBody(getImplName(asARel.getKindB()) + " B = ("
						+ getImplName(asARel.getKindB()) + ") "
						+ asARel.getRoleB() + ";\n");
				m.appendToBody("return edma_dmview.getUpdateInterface().relationAdd");
				if(unique) m.appendToBody("Unique");
				m.appendToBody("(" + asARel.getArrayPosition()
						+ ", edma_entity.getID(), B.edma_entity.getID());\n");
				m.addImport(getImplImport(asARel.getKindB()));
				clazz.addMethod(m);

				// Remove method
				name = "remove";
				desc = "Removes the " + asARel.getRoleB() + " from this "
						+ kind.getName();
				if(!kind.getName().equals(StringUtil.U(asARel.getRoleA())))
				{
					name = "as" + StringUtil.U(asARel.getRoleA()) + "Remove";
					desc += " acting as a " + asARel.getRoleA();
				}
				desc += ", if it is connected.\n";
				desc += " This method has been generated from the relation:\n "
						+ asARel.getName();
				sig = new JavaMethodSignature(name
						+ StringUtil.U(asARel.getRoleB()), desc);
				sig.setReturnType("boolean");
				sig.setReturnDescription("<tt>true</tt> if the specified "
						+ asARel.getRoleB() + " was connected to this "
						+ asARel.getRoleA());
				sig.addParameter(	getViewIntfName(asARel.getKindB()),
									asARel.getRoleB(),
									"The " + asARel.getRoleB()
											+ " to be connected to this "
											+ asARel.getRoleA()
											+ ". May NOT be null.");
				sig.addImport(getViewIntfImport(asARel.getKindB()));
				intf.addMethod(sig);
				// Implementation
				m = new JavaMethod(sig);
				m.appendToBody(getImplName(asARel.getKindB()) + " edma_b = ("
						+ getImplName(asARel.getKindB()) + ") "
						+ asARel.getRoleB() + ";\n");
				m.appendToBody("return edma_dmview.getUpdateInterface().relationDelete("
						+ asARel.getArrayPosition()
						+ ", edma_entity.getID(), edma_b.edma_entity.getID());\n");
				m.addImport(getImplImport(asARel.getKindB()));
				clazz.addMethod(m);
				break;
			}
			default:
				throw new RuntimeException("Unknown relation type: "
						+ asARel.getType());
		}
	}

	public JavaInterface getInterface()
	{
		return intf;
	}
}
