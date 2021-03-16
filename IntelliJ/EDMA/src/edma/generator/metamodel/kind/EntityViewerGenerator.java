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
import edma.metamodel.IMetaIndex;
import edma.metamodel.IMetaKind;
import edma.metamodel.IMetaRelation;
import edma.metamodel.IMetaRelation.RelationType;
import edma.util.StringUtil;

/**
 * This class generates the Viewer class and interface for any given kind. The
 * following methods should be generated: Kind snapshot(); KindID getID();
 * foreach Relation r follow relation (different names depending on cardinality)
 * 
 */

public class EntityViewerGenerator extends AModelGenerator
{
	private JavaInterface intf;
	private JavaClass impl;
	private IMetaKind kind;

	public EntityViewerGenerator(	IPackageStructure pkgStruct,
									IMetaDataModel model,
									IMetaKind kind)
	{
		super(pkgStruct, model);
		this.kind = kind;

		intf = new JavaInterface(	getViewIntfName(kind),
									"This is the viewing interface for "
											+ kind.getName());
		intf.setPackage(getViewIntfPackage(kind));
		impl = new JavaClass(getImplName(kind));
		impl.setPackage(getImplPackage(kind));
		impl.addImplements(intf.getName());
		impl.addImport(getViewIntfImport(kind));
		impl.addConstructorField(	"protected",
									"IEntity",
									"edma_entity",
									"Internal entity");
		impl.addImport(EdmaImport.IEntity);
		impl.addConstructorField(	"IDataModelView",
									"edma_dmview",
									"Internal runtime interface");
		impl.addImport(EdmaImport.IDataModelView);

		snapshot();
		getID();
		int size = kind.getNumberOfAttributes();
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = kind.getAttribute(i);
			getAttribute(att);
		}
		extensionUp();
		extensionDown();

		size = kind.getNumberOfRelationsAsA();
		for(int i = 0; i < size; ++i)
		{
			IMetaRelation rel = kind.getRelationAsA(i);
			relation(rel, true);
		}
		size = kind.getNumberOfRelationsAsB();
		for(int i = 0; i < size; ++i)
		{
			IMetaRelation rel = kind.getRelationAsB(i);
			relation(rel, false);
		}
		
		equals();
		hash();
	}

	private void equals()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"equals",
															"Returns <tt>true</tt> if this entity has the same ID as the provided entity");
		sig.addParameter("Object", "o", "The object to compare with");
		sig.setReturnType("boolean");
		sig.setReturnDescription("<tt>true</tt> if this entity has the same ID as the provided entity");
		intf.addMethod(sig);
		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		String viewer = getViewIntfName(kind);
		m.appendToBody("if(!(o instanceof " + viewer + ")) return false;\n");
		m.appendToBody(viewer + " other = (" + viewer + ") o;\n");
		m.appendToBody("return getID().equals(other.getID());\n");
		impl.addMethod(m);
	}
	
	private void hash()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"hashCode",
															"Returns the hash code of this entity");
		sig.setReturnType("int");
		sig.setReturnDescription("The hash code of this entity");
		intf.addMethod(sig);
		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return getID().hashCode();\n");
		impl.addMethod(m);
	}


	private void snapshot()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"snapshot",
															"Create a copy of this "
																	+ kind.getName()
																	+ " at this instance in time");
		sig.setReturnType(getIntfName(kind.getValueDomain()));
		sig.setReturnDescription("A copy of this " + kind.getName()
				+ " entity as a value  from the value domain "
				+ getIntfName(kind.getValueDomain()));
		sig.addImport(getIntfImport(kind.getValueDomain()));
		intf.addMethod(sig);
		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		int size = kind.getNumberOfAttributes() + 1; // Attributes + ID
		m.appendToBody("Object[] res = new Object[" + size + "];\n");
		m.appendToBody("Object[] internal = edma_entity.getValue();\n");
		for(int i = 0; i < size; ++i)
			m.appendToBody("res[" + i + "] = internal[" + i + "];\n");
		m.appendToBody("return new " + getImplName(kind.getValueDomain())
				+ "(res);\n");
		m.addImport(getImplImport(kind.getValueDomain()));
		impl.addMethod(m);
	}

	private void getID()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"getID",
															"Returns the ID of this "
																	+ kind.getName());
		sig.setReturnType(getIntfName(kind.getIDValueDomain()));
		sig.setReturnDescription("The ID of this " + kind.getName());
		sig.addImport(getIntfImport(kind.getIDValueDomain()));
		intf.addMethod(sig);
		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return new " + getImplName(kind.getIDValueDomain())
				+ "(edma_entity.getID());\n");
		m.addImport(getImplImport(kind.getIDValueDomain()));
		impl.addMethod(m);
	}

	private void getAttribute(IMetaAttribute att)
	{
		JavaMethodSignature sig = new JavaMethodSignature("get"
				+ StringUtil.U(att.getName()), "Returns the attribute "
				+ att.getName() + " of this " + kind.getName());
		sig.setReturnType(getIntfName(att.getValueDomain()));
		sig.setReturnDescription("The value of the attribute " + att.getName());
		sig.addImport(getIntfImport(att.getValueDomain()));
		intf.addMethod(sig);
		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return new " + getImplName(att.getValueDomain())
				+ "(edma_entity.getValue()[" + (att.getArrayPosition() + 1)
				+ "]);\n");
		m.addImport(getImplImport(att.getValueDomain()));
		impl.addMethod(m);
	}

	public void extensionUp()
	{
		IMetaKind base = kind.getBaseKind();
		if(base == null) return;
		JavaMethodSignature sig = new JavaMethodSignature(	"as"
																	+ base.getName(),
															"Views this "
																	+ kind.getName()
																	+ " as its base kind "
																	+ base.getName()
																	+ ". Will never return <tt>null</tt> since "
																	+ kind.getName()
																	+ " is always an extension of "
																	+ base.getName());
		sig.setReturnType(getViewIntfName(base));
		sig.setReturnDescription("The " + base.getName() + " view of this "
				+ kind.getName());
		sig.addImport(getViewIntfImport(base));
		intf.addMethod(sig);
		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("IEntity base = edma_dmview.kindGetFromID("
				+ base.getArrayPosition() + ", edma_entity.getID());\n");
		m.appendToBody("return new " + getImplName(base)
				+ "(base, edma_dmview);\n");
		m.addImport(getImplImport(base));
		impl.addMethod(m);
	}

	public void extensionDown()
	{
		int size = kind.getNumberOfExtensions();
		for(int i = 0; i < size; ++i)
		{
			IMetaKind ext = kind.getExtension(i);
			JavaMethodSignature sig = new JavaMethodSignature("as"
					+ ext.getName(), "Views this " + kind.getName()
					+ " as its extension kind " + ext.getName()
					+ ". May return <tt>null</tt> if this " + kind.getName()
					+ " is not extended to " + ext.getName());
			sig.setReturnType(getViewIntfName(ext));
			sig.addImport(getViewIntfImport(ext));
			sig.setReturnDescription("The " + ext.getName() + " view of this "
					+ kind.getName() + " or <tt>null</tt> if this "
					+ kind.getName() + " is not extended to " + ext.getName());
			intf.addMethod(sig);
			// Implementation...
			JavaMethod m = new JavaMethod(sig);
			m.appendToBody("IEntity ext = edma_dmview.kindGetFromID("
					+ ext.getArrayPosition() + ", edma_entity.getID());\n");
			m.appendToBody("if(ext == null) return null;\n");
			m.appendToBody("return new " + getImplName(ext)
					+ "(ext, edma_dmview);\n");
			m.addImport(getImplImport(ext));
			impl.addMethod(m);
		}
	}

	private void relation(IMetaRelation rel, boolean asA)
	{
		IMetaKind kindThis;
		IMetaKind kindOther;
		String roleThis;
		String roleOther;
		if(!asA
				&& (rel.getType() == RelationType.ManyToManySame || rel.getType() == RelationType.OneToOneSame)) return;
		boolean returnSet = true;
		if(asA)
		{
			kindThis = rel.getKindA();
			kindOther = rel.getKindB();
			roleThis = rel.getRoleA();
			roleOther = rel.getRoleB();
			switch(rel.getType())
			{
				case ManyToOne:
				case OneToOne:
				case OneToOneSame:
					returnSet = false;
					break;

			}
		}
		else
		{
			kindThis = rel.getKindB();
			kindOther = rel.getKindA();
			roleThis = rel.getRoleB();
			roleOther = rel.getRoleA();
			if(rel.getType() == RelationType.OneToOne) returnSet = false;
		}
		String methodName = "get" + StringUtil.U(roleOther);
		if(returnSet)
		{
			methodName += "Set";
		}
		if(!StringUtil.U(roleThis).equals(kindThis.getName()))
		{
			methodName = "as" + StringUtil.U(roleThis)
					+ StringUtil.U(methodName);
		}
		// TODO: create a better description...
		String desc = "This methods follows the relation " + rel.getName();
		String returnDesc;
		JavaMethodSignature sig = new JavaMethodSignature(methodName, desc);
		if(returnSet)
		{
			sig.setReturnType(getSetIntfName(kindOther));
			sig.addImport(getSetIntfImport(kindOther));
			// TODO: create better return desc...
			returnDesc = "The result of following the relation "
					+ rel.getName();
		}
		else
		{
			sig.setReturnType(getViewIntfName(kindOther));
			sig.addImport(getViewIntfImport(kindOther));
			// TODO: create better return desc...
			returnDesc = "The result of following the relation "
					+ rel.getName();
		}
		sig.setReturnDescription(returnDesc);
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		if(returnSet)
		{
			m.appendToBody("int setID = edma_dmview.relation");
			m.appendToBody(asA ? "AsAGetBSet(" : "AsBGetASet(");
			m.appendToBody(rel.getArrayPosition() + ", edma_entity.getID());\n");

			m.appendToBody("return new " + getSetImplName(kindOther)
					+ "(setID, edma_dmview);\n");
			m.addImport(getSetImplImport(kindOther));
		}
		else
		{
			m.appendToBody("Long resID = edma_dmview.relation");
			m.appendToBody(asA ? "AsAGetB(" : "AsBGetA(");
			m.appendToBody(rel.getArrayPosition() + ", edma_entity.getID());\n");
			m.appendToBody("if(resID == null) return null;\n");
			m.appendToBody("IEntity entity = edma_dmview.kindGetFromID("
					+ kindOther.getArrayPosition() + ", resID);\n");
			m.appendToBody("return new " + getImplName(kindOther)
					+ "(entity, edma_dmview);\n");
			m.addImport(getImplImport(kindOther));
		}
		impl.addMethod(m);

		// Indexes
		if(asA)
		{
			int size = rel.getNumberOfIndexesOnB();
			for(int i = 0; i < size; ++i)
			{
				IMetaIndex index = rel.getIndexOnB(i);
				IndexGenerator ig = new IndexGenerator(	pkgStruct,
														model,
														intf,
														impl,
														index,
														kindOther);
				String rt = roleThis;
				if(StringUtil.U(rt).equals(kindThis.getName())) rt = null;
				ig.generateForRelationIndex(rel.getArrayPosition(),
											"B",
											rt,
											roleOther,
											"edma_entity.getID()");
			}
		}
		else
		{
			int size = rel.getNumberOfIndexesOnA();
			for(int i = 0; i < size; ++i)
			{
				IMetaIndex index = rel.getIndexOnA(i);
				IndexGenerator ig = new IndexGenerator(	pkgStruct,
														model,
														intf,
														impl,
														index,
														kindOther);
				String rt = roleThis;
				if(StringUtil.U(rt).equals(kindThis.getName())) rt = null;
				ig.generateForRelationIndex(rel.getArrayPosition(),
											"A",
											rt,
											roleOther,
											"edma_entity.getID()");
			}
		}

	}

	public JavaInterface getInterface()
	{
		return intf;
	}

	public JavaClass getClazz()
	{
		return impl;
	}
}
