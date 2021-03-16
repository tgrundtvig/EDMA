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
import edma.metamodel.IMetaRelation;
import edma.metamodel.IMetaRelation.RelationType;
import edma.util.StringUtil;

/**
 * This class is responsible for generating the SetViewer for any given kind. It
 * should contain all the necessary set-functions (union, intersection,
 * subtraction), as well as attribute-filtering functions (firstNameEquals(...),
 * ageEquals(...), ageLessThan(...) etc.
 * 
 */
public class EntitySetViewerGenerator extends AModelGenerator
{
	private JavaInterface intf;
	private JavaClass impl;
	private IMetaKind kind;

	public EntitySetViewerGenerator(IPackageStructure pkgStruct,
									IMetaDataModel model,
									IMetaKind kind)
	{
		super(pkgStruct, model);
		this.kind = kind;
		int size;

		intf = new JavaInterface(	getSetIntfName(kind),
									"This is the viewing interface for "
											+ kind.getName() + "Set");
		intf.addExtends("Iterable<" + getViewIntfName(kind) + ">");
		intf.setPackage(getSetIntfPackage(kind));

		impl = new JavaClass(getSetImplName(kind));
		impl.setPackage(getSetImplPackage(kind));
		impl.addConstructorField("int", "setID", "The id of this set");
		impl.addConstructorField(	"IDataModelView",
									"edma_dmview",
									"The internal runtime interface");
		impl.addImport(EdmaImport.IDataModelView);
		impl.addImplements(intf.getName());
		impl.addImport(getSetIntfImport(kind));

		// Methods to create...
		getSize();
		containsID();
		containsEntity();
		containsAll();
		snapshot();
		union();
		intersect();
		subtract();
		filter();
		extensionUp();
		extensionDown();
		orderByID(false);
		orderByID(true);
		subOrderByID(false);
		subOrderByID(true);
		size = kind.getNumberOfAttributes();
		for(int i = 0; i < size; ++i)
		{
			IMetaAttribute att = kind.getAttribute(i);
			orderBy(att, false);
			orderBy(att, true);
			subOrderBy(att, false);
			subOrderBy(att, true);
		}

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

		// Iterator
		iterator();

		EntitySetIteratorGenerator ksig = new EntitySetIteratorGenerator(	pkgStruct,
																			model,
																			kind);
		impl.addInnerClass(ksig.getClazz());
	}

	private void union()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"union",
															"Returns the union between this set and the given set");
		sig.setReturnType(getSetIntfName(kind));
		sig.setReturnDescription("The union between this " + kind.getName()
				+ "Set and the provided " + kind.getName() + "Set.");

		sig.addParameter(getSetIntfName(kind), StringUtil.L(kind.getName())
				+ "Set", "The " + kind.getName()
				+ "Set to create the union with.");

		intf.addMethod(sig);
		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("int otherSetID = ((" + getSetImplName(kind) + ") "
				+ StringUtil.L(kind.getName()) + "Set).setID;\n");
		m.appendToBody("int newSetID = edma_dmview.setUnion(setID, otherSetID);\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		impl.addMethod(m);
	}

	private void intersect()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"intersect",
															"Returns the intersection between this set and the given set");
		sig.setReturnType(getSetIntfName(kind));
		sig.setReturnDescription("The intersection between this "
				+ kind.getName() + "Set and the provided " + kind.getName()
				+ "Set.");

		sig.addParameter(getSetIntfName(kind), StringUtil.L(kind.getName())
				+ "Set", "The " + kind.getName()
				+ "Set to create the intersection with.");

		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("int otherSetID = ((" + getSetImplName(kind) + ") "
				+ StringUtil.L(kind.getName()) + "Set).setID;\n");
		m.appendToBody("int newSetID = edma_dmview.setIntersect(setID, otherSetID);\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		impl.addMethod(m);
	}

	private void subtract()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"subtract",
															"Returns the subtraction of the given set from this set. "
																	+ "The result set will contain every element from this set, "
																	+ "that are not contained in the given set.");
		sig.setReturnType(getSetIntfName(kind));
		sig.setReturnDescription("The subtraction of the provided "
				+ kind.getName() + "Set from this " + kind.getName()
				+ "Set. The result will contain every element from this set, "
				+ "that are not contained in the provided set.");

		sig.addParameter(getSetIntfName(kind), StringUtil.L(kind.getName())
				+ "Set", "The " + kind.getName()
				+ "Set to subtract from this set.");

		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("int otherSetID = ((" + getSetImplName(kind) + ") "
				+ StringUtil.L(kind.getName()) + "Set).setID;\n");
		m.appendToBody("int newSetID = edma_dmview.setSubtract(setID, otherSetID);\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		impl.addMethod(m);
	}

	private void filter()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"filter",
															"Returns a new set with all the entities from this set that are accepted by the filter.");
		sig.setReturnType(getSetIntfName(kind));
		sig.setReturnDescription("A new set with all the entities from this set that are accepted by the filter.");

		sig.addParameter(getFilterIntfName(kind), "filter", "The filter");

		intf.addMethod(sig);

		JavaMethod m = new JavaMethod(sig);
		m.addImport(getFilterIntfImport(kind));
		m.appendToBody("ArrayList<Long> res = new ArrayList<Long>();\n");
		m.appendToBody("for(" + getViewIntfName(kind) + " "
				+ StringUtil.L(kind.getName()) + " : this)\n{\n");
		m.appendToBody("    if(filter.accept(" + StringUtil.L(kind.getName())
				+ "))\n    {\n");
		m.appendToBody("        res.add(" + StringUtil.L(kind.getName())
				+ ".getID().value());\n");
		m.appendToBody("    }\n}\n");
		m.appendToBody("int newSetID = edma_dmview.setFromIDs("
				+ kind.getArrayPosition() + ", res);\n");
		m.appendToBody("return new " + getSetImplImport(kind)
				+ "(newSetID, edma_dmview);\n");
		m.addImport("java.util.ArrayList");
		impl.addMethod(m);
	}

	private void getSize()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"size",
															"Get the size of this set.");
		sig.setReturnType("int");
		sig.setReturnDescription("The size of this set.");
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return edma_dmview.setGetSize(setID);\n");
		impl.addMethod(m);
	}

	private void containsID()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"contains",
															"Returns <tt>true</tt> if this set contains the entity with the given id.");
		sig.setReturnType("boolean");
		sig.setReturnDescription("<tt>true</tt> if this set contains the entity with the given id");
		sig.addParameter(	getIntfName(kind.getIDValueDomain()),
							"id",
							"The id of the entity to test containment of");
		sig.addImport(getViewIntfImport(kind));
		sig.addImport(getIntfImport(kind.getIDValueDomain()));
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return edma_dmview.setContains(setID, id.value());\n");
		impl.addMethod(m);
	}

	private void containsAll()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"containsAll",
															"Returns <tt>true</tt> if this set contains every entity in the given set.");
		sig.setReturnType("boolean");
		sig.setReturnDescription("<tt>true</tt> if this set contains every entity in the given set");
		String paramName = StringUtil.L(kind.getName()) + "Set";
		sig.addParameter(	getSetIntfName(kind),
							paramName,
							"The " + kind.getName()
									+ "Set to test containment of.");
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return edma_dmview.setContainsAll(setID, ((" + getSetImplName(kind) + ") " + paramName + ").setID);\n");
		impl.addMethod(m);
	}

	private void containsEntity()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"contains",
															"Returns <tt>true</tt> if this set contains the given entity.");
		sig.setReturnType("boolean");
		sig.setReturnDescription("<tt>true</tt> if this set contains the given entity");
		sig.addParameter(	getViewIntfName(kind),
							"entity",
							"The entity to test containment of");
		sig.addImport(getViewIntfImport(kind));
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return edma_dmview.setContains(setID, entity.getID().value());\n");
		impl.addMethod(m);
	}

	public void extensionUp()
	{
		IMetaKind base = kind.getBaseKind();
		if(base == null) return;
		JavaMethodSignature sig = new JavaMethodSignature("as" + base.getName()
				+ "Set", "Views the " + kind.getName()
				+ " entries in this set as their base kind " + base.getName()
				+ ". The resulting set will always be of the "
				+ "same size as this set, since " + kind.getName()
				+ " is always an extension of " + base.getName());
		sig.setReturnType(getSetIntfName(base));
		sig.setReturnDescription("The " + base.getName() + "Set view of this "
				+ kind.getName() + "Set");
		sig.addImport(getSetIntfImport(base));
		intf.addMethod(sig);
		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("int newSetID = edma_dmview.setExtensionUp(setID, "
				+ base.getArrayPosition() + ");\n");
		m.appendToBody("return new " + getSetImplName(base)
				+ "(newSetID, edma_dmview);\n");
		m.addImport(getSetImplImport(base));
		impl.addMethod(m);
	}

	public void extensionDown()
	{
		int size = kind.getNumberOfExtensions();
		for(int i = 0; i < size; ++i)
		{
			IMetaKind ext = kind.getExtension(i);
			JavaMethodSignature sig = new JavaMethodSignature("as"
					+ ext.getName() + "Set", "Views this " + kind.getName()
					+ "Set as a set of its extension kind " + ext.getName()
					+ ". May return a smaller set, "
					+ "since only those entries that are extended to "
					+ ext.getName() + " will be included in the result set.");
			sig.setReturnType(getSetIntfName(ext));
			sig.setReturnDescription("The " + ext.getName()
					+ " view of the entries in this set"
					+ ". May return a smaller set, "
					+ "since only those entries that are extended to "
					+ ext.getName() + " will be included in the result set.");
			sig.addImport(getSetIntfImport(ext));
			intf.addMethod(sig);
			// Implementation...
			JavaMethod m = new JavaMethod(sig);
			m.appendToBody("int newSetID = edma_dmview.setExtensionDown(setID, "
					+ ext.getArrayPosition() + ");\n");

			m.appendToBody("return new " + getSetImplName(ext)
					+ "(newSetID, edma_dmview);\n");
			m.addImport(getSetImplImport(ext));
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
		if(asA)
		{
			kindThis = rel.getKindA();
			kindOther = rel.getKindB();
			roleThis = rel.getRoleA();
			roleOther = rel.getRoleB();
		}
		else
		{
			kindThis = rel.getKindB();
			kindOther = rel.getKindA();
			roleThis = rel.getRoleB();
			roleOther = rel.getRoleA();
		}
		String methodName = "get" + StringUtil.U(roleOther) + "Set";

		if(!roleThis.equals(kindThis.getName()))
		{
			methodName = "as" + StringUtil.U(roleThis) + "Set"
					+ StringUtil.U(methodName);
		}
		// TODO: create a better description...
		String desc = "This methods follows the relation " + rel.getName();
		String returnDesc;
		JavaMethodSignature sig = new JavaMethodSignature(methodName, desc);
		sig.setReturnType(getSetIntfName(kindOther));
		sig.addImport(getSetIntfImport(kindOther));
		// TODO: create better return desc...
		returnDesc = "The result of following the relation " + rel.getName();

		sig.setReturnDescription(returnDesc);
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("int newSetID = edma_dmview.relation");
		m.appendToBody(asA ? "AsASetGetBSet(" : "AsBSetGetASet(");
		m.appendToBody(rel.getArrayPosition() + ", setID);\n");
		m.appendToBody("return new " + getSetImplName(kindOther)
				+ "(newSetID, edma_dmview);\n");
		m.addImport(getSetImplImport(kindOther));
		impl.addMethod(m);
	}

	private void snapshot()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"snapshot",
															"Creates a copy of this "
																	+ kind.getName()
																	+ "Set at this instance in time"
																	+ " and returns it as a list of values");
		sig.setReturnType(getIntfName(kind.getListValueDomain()));
		sig.setReturnDescription("A copy of this " + kind.getName()
				+ "Set as a list of values from the value domain "
				+ kind.getListValueDomain().getName());
		sig.addImport(getIntfImport(kind.getListValueDomain()));
		intf.addMethod(sig);
		// Implementation...
		int size = kind.getNumberOfAttributes() + 1;
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("int size = edma_dmview.setGetSize(setID);\n");
		m.appendToBody("Iterator<IEntity> it = edma_dmview.setGetIterator(setID);\n");
		m.appendToBody("Object[] res = new Object[size];\n");
		m.appendToBody("int i = 0;\n");
		m.appendToBody("while(it.hasNext())\n{\n");
		m.appendToBody("    Object[] entObj = new Object[" + size + "];\n");
		m.appendToBody("    Object[] value = it.next().getValue();\n");
		for(int i = 0; i < size; ++i)
		{
			m.appendToBody("    entObj[" + i + "] = value[" + i + "];\n");
		}
		m.appendToBody("    res[i++] = entObj;\n}\n");
		m.appendToBody("return new " + getImplName(kind.getListValueDomain())
				+ "(res);\n");

		m.addImport(EdmaImport.IEntity);
		m.addImport("java.util.Iterator");
		m.addImport(getImplImport(kind.getListValueDomain()));
		impl.addMethod(m);
	}

	private void orderBy(IMetaAttribute att, boolean desc)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"orderBy"
																	+ StringUtil.U(att.getName())
																	+ (desc ? "Desc"
																			: ""),
															"Returns a new set with the same entries but ordered by "
																	+ att.getName());
		sig.setReturnType(getSetIntfName(kind));
		sig.setReturnDescription("A new set with the same entries but ordered by "
				+ att.getName());
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("int newSetID = edma_dmview.setOrderBy(setID, "
				+ att.getArrayPosition() + ", " + desc + ");\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		impl.addMethod(m);
	}

	private void subOrderBy(IMetaAttribute att, boolean desc)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"subOrderBy"
																	+ StringUtil.U(att.getName())
																	+ (desc ? "Desc"
																			: ""),
															"Returns a new set with the same entries that is sub ordered by "
																	+ att.getName());
		sig.setReturnType(getSetIntfName(kind));
		sig.setReturnDescription("A new set with the same entries that is sub ordered by "
				+ att.getName());
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("int newSetID = edma_dmview.setSubOrderBy(setID, "
				+ att.getArrayPosition() + ", " + desc + ");\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		impl.addMethod(m);
	}

	private void orderByID(boolean desc)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"orderByID"
																	+ (desc ? "Desc"
																			: ""),
															"Returns a new set with the same entries but ordered by ID.");
		sig.setReturnType(getSetIntfName(kind));
		sig.setReturnDescription("A new set with the same entries but ordered by ID.");
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("int newSetID = edma_dmview.setOrderByID(setID, " + desc
				+ ");\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		impl.addMethod(m);
	}

	private void subOrderByID(boolean desc)
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"subOrderByID"
																	+ (desc ? "Desc"
																			: ""),
															"Returns a new set with the same entries but sub ordered by ID.");
		sig.setReturnType(getSetIntfName(kind));
		sig.setReturnDescription("A new set with the same entries but sub ordered by ID.");
		intf.addMethod(sig);

		// Implementation...
		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("int newSetID = edma_dmview.setSubOrderByID(setID, "
				+ desc + ");\n");
		m.appendToBody("return new " + getSetImplName(kind)
				+ "(newSetID, edma_dmview);\n");
		impl.addMethod(m);
	}

	private void iterator()
	{
		JavaMethodSignature sig = new JavaMethodSignature(	"iterator",
															"Returns an iterator for this set");
		sig.setReturnType("Iterator<" + getViewIntfName(kind) + ">");
		sig.setReturnDescription("An iterator for this set");
		sig.addImport("java.util.Iterator");
		intf.addMethod(sig);

		JavaMethod m = new JavaMethod(sig);
		m.appendToBody("return new " + getSetIteratorImplName(kind)
				+ "(edma_dmview.setGetIterator(setID));\n");
		impl.addMethod(m);
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
