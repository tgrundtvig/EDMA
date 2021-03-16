package org.abstractica.edma.generator.metamodel;

import java.util.HashSet;
import java.util.Set;

import org.abstractica.edma.generator.EdmaImport;
import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaMethod;
import org.abstractica.edma.generator.common.JavaMethodSignature;
import org.abstractica.edma.metamodel.IMetaAttribute;
import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaEnvironment;
import org.abstractica.edma.metamodel.IMetaIndex;
import org.abstractica.edma.metamodel.IMetaKind;
import org.abstractica.edma.metamodel.IMetaMethod;
import org.abstractica.edma.metamodel.IMetaMethodParameter;
import org.abstractica.edma.metamodel.IMetaRelation;
import org.abstractica.edma.metamodel.IMetaSingleton;
import org.abstractica.edma.util.StringUtil;
import org.abstractica.edma.valuedomains.IDoubleValueDomain;
import org.abstractica.edma.valuedomains.IEnumValueDomain;
import org.abstractica.edma.valuedomains.IFloatValueDomain;
import org.abstractica.edma.valuedomains.IIntegerValueDomain;
import org.abstractica.edma.valuedomains.IListValueDomain;
import org.abstractica.edma.valuedomains.ILongValueDomain;
import org.abstractica.edma.valuedomains.IMapValueDomain;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.IOneOfValueDomain;
import org.abstractica.edma.valuedomains.IStringValueDomain;
import org.abstractica.edma.valuedomains.IStructField;
import org.abstractica.edma.valuedomains.IStructValueDomain;
import org.abstractica.edma.valuedomains.IValueDomainDefinitions;
import org.abstractica.edma.valuedomains.impl.Constraint;

public class EnvironmentGenerator
{
	private JavaClass impl;
	private IValueDomainDefinitions definitions;

	public EnvironmentGenerator(IPackageStructure pkgStruct,
	                            IMetaEnvironment environment)
	{
		this.definitions = environment.getValueDomainDefinitions();
		// Build the value store class
		impl = new JavaClass(pkgStruct.getEnvironmentClassName());
		impl.setPackage(pkgStruct.getEnvironmentPackage());
		impl.addField(	"public",
						true,
						true,
						"IMetaEnvironment",
						"environment",
						"generateEnvironment()");

		impl.addImport(EdmaImport.IMetaEnvironment);
		impl.addConstructorParameter(	"IRuntimeFactory",
										"factory",
										"The runtime factory");
		impl.addImport(EdmaImport.IRuntimeFactory);

		int size = environment.getNumberOfDataModels();
		for(int i = 0; i < size; ++i)
		{
			IMetaDataModel model = environment.getMetaDataModel(i);
			String modelFactoryName = pkgStruct.getModelFactoryImplName(model);
			String modelFactoryImport = pkgStruct.getModelFactoryImplPackage(model)
					+ "." + modelFactoryName;
			String varName = "edma_" + model.getName();
			impl.addField(modelFactoryName, varName);
			impl.addImport(modelFactoryImport);
			impl.addConstructorBodyLine(varName
					+ " = new "
					+ modelFactoryName
					+ "(factory.getDataModelInstanceFactory(environment.getMetaDataModel("
					+ i + ")));");
			JavaMethodSignature sig = new JavaMethodSignature("get"
					+ model.getName() + "Factory", "Get access to the " + model.getName()
					+ " data model factory");
			sig.setReturnType(modelFactoryName);
			sig.setReturnDescription("The " + model.getName()
					+ " data model storage");
			JavaMethod m = new JavaMethod(sig);
			m.appendToBody("return " + varName + ";\n");
			impl.addMethod(m);
		}

		// generate definitions
		JavaMethodSignature genDefSig = new JavaMethodSignature("generateEnvironment",
																"generate the environment");
		genDefSig.setReturnType("IMetaEnvironment");

		JavaMethod genEnv = new JavaMethod(genDefSig);
		genEnv.setStatic(true);
		genEnv.appendToBody(generateCodeForValueDomains());
		genEnv.appendToBody("MetaEnvironment edma_environment = new MetaEnvironment(\""
				+ environment.getName() + "\");\n");
		genEnv.addImport(EdmaImport.MetaEnvironment);
		for(int i = 0; i < size; ++i)
		{
			IMetaDataModel model = environment.getMetaDataModel(i);
			addCodeforModel(model, genEnv);
		}
		genEnv.appendToBody("vdb.buildWithEnvironment(edma_environment);\n");
		genEnv.appendToBody("//Hack to make serializeable work...\n");
		genEnv.appendToBody("IndexUtil.setValueDomainDefinitions(edma_environment.getValueDomainDefinitions());\n");
		genEnv.addImport("org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil");
		genEnv.appendToBody("return edma_environment;\n");

		impl.addMethod(genEnv);
	}

	public JavaClass getImpl()
	{
		return impl;
	}

	private void generateConstraints(	String indent,
										Constraint[] constraints,
										StringBuilder res)
	{
		if(constraints != null)
		{
			res.append(indent);
			res.append("ArrayList<Constraint> edma_constraints = new ArrayList<Constraint>("
					+ constraints.length + ");\n");
			for(Constraint con : constraints)
			{
				String name = StringUtil.escapeString(con.getName());
				String desc = StringUtil.escapeString(con.getDescription());
				res.append(indent);
				res.append("edma_constraints.add(new Constraint(\"" + name
						+ "\", \"" + desc + "\"));\n");
			}
		}
		else
		{
			res.append(indent);
			res.append("ArrayList<Constraint> edma_constraints = null;\n");
		}
	}

	private String generateCodeForValueDomains()
	{
		String indent = "    ";
		StringBuilder res = new StringBuilder();
		int size = definitions.getNumberOfValueDomains();
		res.append("ValueDomainBuilder vdb = new ValueDomainBuilder();\n");
		impl.addImport(EdmaImport.ValueDomainBuilder);
		impl.addImport(EdmaImport.Constraint);
		impl.addImport("java.util.ArrayList");
		for(int i = 0; i < size; ++i)
		{
			IMetaValueDomain vd = definitions.getValueDomain(i);
			if(vd.isAutoGenerated()) continue;
			String name = vd.getName();
			String scope = vd.getScope();
			if(scope != null) scope = "\"" + scope + "\"";
			res.append("\n");
			res.append("//" + vd.getEMetaType() + " value domain: " + name
					+ "\n");
			res.append("{\n");
			generateConstraints(indent, vd.getConstraints(), res);
			switch(vd.getEMetaType())
			{
				case Boolean:
				{
					res.append(indent);
					res.append("vdb.newBooleanDomain(\"" + name + "\", "
							+ scope + ", edma_constraints, "
							+ vd.isAutoGenerated() + ");\n");
					break;
				}
				case Integer:
				{
					IIntegerValueDomain ivd = vd.asInteger();
					res.append(indent);
					res.append("vdb.newIntegerDomain(\"" + name + "\", "
							+ scope + ", " + ivd.getMin() + ", " + ivd.getMax()
							+ ", edma_constraints, " + vd.isAutoGenerated()
							+ ");\n");
					break;
				}
				case Long:
				{
					ILongValueDomain lvd = vd.asLong();
					res.append(indent);
					res.append("vdb.newLongDomain(\""
							+ name
							+ "\", "
							+ scope
							+ ", "
							+ (lvd.getMin() == null ? "null"
									: (lvd.getMin() + "L"))
							+ ", "
							+ (lvd.getMax() == null ? "null"
									: (lvd.getMax() + "L"))
							+ ", edma_constraints, " + vd.isAutoGenerated()
							+ ");\n");
					break;
				}
				case Float:
				{
					IFloatValueDomain fvd = vd.asFloat();
					res.append(indent);
					res.append("vdb.newFloatDomain(\""
							+ name
							+ "\", "
							+ scope
							+ ", "
							+ (fvd.getMin() == null ? "null"
									: (fvd.getMin() + "f"))
							+ ", "
							+ (fvd.getMax() == null ? "null"
									: (fvd.getMax() + "f"))
							+ ", edma_constraints, " + vd.isAutoGenerated()
							+ ");\n");
					break;
				}
				case Double:
				{
					IDoubleValueDomain dvd = vd.asDouble();
					res.append(indent);
					res.append("vdb.newDoubleDomain(\""
							+ name
							+ "\", "
							+ scope
							+ ", "
							+ (dvd.getMin() == null ? "null"
									: (dvd.getMin() + "d"))
							+ ", "
							+ (dvd.getMax() == null ? "null"
									: (dvd.getMax() + "d"))
							+ ", edma_constraints, " + vd.isAutoGenerated()
							+ ");\n");
					break;
				}
				case String:
				{
					IStringValueDomain svd = vd.asString();
					res.append(indent);
					res.append("vdb.newStringDomain(\"" + name + "\", " + scope
							+ ", " + svd.getMinLength() + ", "
							+ svd.getMaxLength() + ", ");
					if(svd.getRegexp() == null)
					{
						res.append("null");
					}
					else
					{
						res.append("\"" + svd.getRegexp() + "\"");
					}
					res.append(", edma_constraints, " + vd.isAutoGenerated()
							+ ");\n");
					break;
				}
				case Enum:
				{
					IEnumValueDomain evd = vd.asEnum();
					res.append(indent);
					res.append("Collection<String> elements = new ArrayList<String>();\n");
					impl.addImport("java.util.Collection");
					impl.addImport("java.util.ArrayList");
					int elementCount = evd.getNumberOfElements();
					for(int j = 0; j < elementCount; ++j)
					{
						res.append(indent);
						res.append("elements.add(\"" + evd.getElement(j)
								+ "\");\n");
					}
					res.append(indent);
					res.append("vdb.newEnumDomain(\"" + name + "\", " + scope
							+ ", elements, edma_constraints, "
							+ vd.isAutoGenerated() + ");\n");
					break;
				}
				case List:
				{
					IListValueDomain lvd = vd.asList();
					res.append(indent);
					res.append("vdb.newListDomain(\"" + name + "\", " + scope
							+ ", \"" + lvd.getElementValueDomain().getName()
							+ "\", " + lvd.getMinSize() + ", "
							+ lvd.getMaxSize() + ", edma_constraints, "
							+ vd.isAutoGenerated() + ");\n");
					break;
				}
				case Struct:
				{
					IStructValueDomain svd = vd.asStruct();
					res.append(indent);
					res.append("Collection<Field> fields = new ArrayList<Field>();\n");
					impl.addImport(EdmaImport.ValueDomainBuilderField);
					impl.addImport("java.util.Collection");
					impl.addImport("java.util.ArrayList");
					int fieldCount = svd.getNumberOfFields();
					for(int j = 0; j < fieldCount; ++j)
					{
						IStructField f = svd.getField(j);
						res.append(indent);
						res.append("fields.add(vdb.newStructField(\""
								+ f.getName() + "\", \""
								+ f.getValueDomain().getName() + "\", "
								+ f.canBeNull() + "));\n");
					}
					res.append(indent);
					res.append("vdb.newStructDomain(\"" + name + "\", " + scope
							+ ", fields, edma_constraints, "
							+ vd.isAutoGenerated() + ");\n");
					break;
				}
				case Map:
				{
					IMapValueDomain mvd = vd.asMap();
					res.append(indent);
					res.append("vdb.newMapDomain(\"" + name + "\", " + scope
							+ ", \"" + mvd.getKeyDomain().getName() + "\", \""
							+ mvd.getValueDomain().getName()
							+ "\", edma_constraints, " + vd.isAutoGenerated()
							+ ");\n");
					break;
				}
				case OneOf:
				{
					IOneOfValueDomain ovd = vd.asOneOf();
					int listSize = ovd.getNumberOfOptions();
					res.append(indent);
					res.append("Collection<String> optionDomains = new ArrayList<String>("
							+ listSize + ");\n");
					for(int j = 0; j < listSize; ++j)
					{
						res.append(indent);
						res.append("optionDomains.add(\"");
						res.append(ovd.getOptionDomain(j).getName());
						res.append("\");\n");
					}
					res.append(indent);
					res.append("vdb.newOneOfDomain(\"" + name + "\", " + scope
							+ ", optionDomains, edma_constraints, "
							+ vd.isAutoGenerated() + ");\n");
					impl.addImport("java.util.Collection");
					impl.addImport("java.util.ArrayList");
					break;
				}
				default:
					throw new RuntimeException("Unknown ValueDomain?");

			}
			res.append("}\n");
		}
		return res.toString();
	}

	private void addCodeforModel(IMetaDataModel model, JavaMethod m)
	{
		String i1 = "    ";
		String i2 = i1 + i1;
		String i3 = i2 + i1;
		int size;
		m.appendToBody("{\n");
		m.appendToBody(i1 + "MetaDataModel edma_model = new MetaDataModel(\""
				+ model.getName() + "\", \"" + model.getPackage() + "\");\n");
		m.addImport(EdmaImport.MetaDataModel);
		// Singletons

		size = model.getNumberOfSingletons();
		for(int i = 0; i < size; ++i)
		{
			IMetaSingleton singleton = model.getSingleton(i);
			m.appendToBody("\n" + i1 + "//Singleton : " + singleton.getName()
					+ "\n");
			m.appendToBody(i1 + "{\n");
			m.appendToBody(i2
					+ "MetaSingleton singleton = new MetaSingleton(\""
					+ singleton.getName() + "\", "
					+ StringUtil.asCode(singleton.publishAs()) + ");\n");
			m.addImport(EdmaImport.MetaSingleton);
			m.addImport(EdmaImport.MetaAttribute);
			int attCount = singleton.getNumberOfAttributes();
			for(int j = 0; j < attCount; ++j)
			{
				IMetaAttribute att = singleton.getAttribute(j);
				m.appendToBody(i2 + "new MetaAttribute(singleton, \""
						+ att.getName() + "\", \"" + att.getValueDomainName()
						+ "\", " + att.isOptional() + ", " + att.isModifiable()
						+ ", " + StringUtil.asCode(att.getDefaultValue())
						+ ");\n");
			}
			m.appendToBody(i2 + "edma_model.addSingleton(singleton);\n");
			m.appendToBody(i1 + "}\n");
		}

		// Kinds
		size = model.getNumberOfKinds();
		for(int i = 0; i < size; ++i)
		{
			IMetaKind kind = model.getKind(i);
			m.appendToBody("\n" + i1 + "// Kind : " + kind.getName() + "\n");
			m.appendToBody(i1
					+ "MetaKind "
					+ StringUtil.L(kind.getName())
					+ " = new MetaKind(\""
					+ kind.getName()
					+ "\", "
					+ StringUtil.asCode(kind.publishAs())
					+ ", "
					+ (kind.getBaseKind() == null ? "null"
							: StringUtil.L(kind.getBaseKind().getName()))
					+ ");\n");
			m.addImport(EdmaImport.MetaKind);
			m.appendToBody(i1 + "{\n");
			int attCount = kind.getNumberOfAttributes();
			for(int j = 0; j < attCount; ++j)
			{
				IMetaAttribute att = kind.getAttribute(j);
				m.appendToBody(i2 + "new MetaAttribute("
						+ StringUtil.L(kind.getName()) + ", \"" + att.getName()
						+ "\", \"" + att.getValueDomainName() + "\", "
						+ att.isOptional() + ", " + att.isModifiable() + ", "
						+ StringUtil.asCode(att.getDefaultValue()) + ");\n");
				m.addImport(EdmaImport.MetaAttribute);
			}
			int indexCount = kind.getNumberOfIndexes();
			for(int j = 0; j < indexCount; ++j)
			{
				IMetaIndex index = kind.getIndex(j);
				attCount = index.getNumberOfAttributes();
				m.appendToBody(i2 + "//Index:  " + index.getIndexType() + " (");
				for(int k = 0; k < attCount; ++k)
				{
					if(k > 0) m.appendToBody(", ");
					m.appendToBody(index.getAttribute(k).getName());
				}
				m.appendToBody(")\n");
				m.appendToBody(i2 + "{\n");
				m.appendToBody(i3
						+ "MetaIndex edma_index = new MetaIndex(IndexType."
						+ index.getIndexType() + ");\n");
				m.addImport(EdmaImport.MetaIndex);
				m.addImport(EdmaImport.IndexType);
				for(int k = 0; k < attCount; ++k)
				{
					IMetaAttribute att = index.getAttribute(k);
					m.appendToBody(i3 + "edma_index.addAttribute("
							+ StringUtil.L(kind.getName()) + ".getAttribute("
							+ att.getArrayPosition() + "));\n");
				}
				m.appendToBody(i3 + StringUtil.L(kind.getName())
						+ ".addIndex(edma_index);\n");
				m.appendToBody(i2 + "}\n");
			}
			m.appendToBody(i1 + "}\n");
			m.appendToBody(i1 + "edma_model.addKind("
					+ StringUtil.L(kind.getName()) + ");\n");
		}

		// Relations
		size = model.getNumberOfRelations();
		if(size > 0) m.appendToBody("\n" + i1 + "//Relations:\n");
		for(int i = 0; i < size; ++i)
		{
			IMetaRelation rel = model.getRelation(i);
			m.appendToBody(i1 + "{\n");
			m.appendToBody(i2 + "MetaRelation mr = new MetaRelation(\""
					+ rel.getName() + "\", "
					+ StringUtil.L(rel.getKindA().getName()) + ", \""
					+ rel.getRoleA() + "\", "
					+ StringUtil.L(rel.getKindB().getName()) + ", \""
					+ rel.getRoleB() + "\", RelationType." + rel.getType()
					+ ");\n");
			m.appendToBody(i2 + "edma_model.addRelation(mr);\n");
			m.addImport(EdmaImport.MetaRelation);
			m.addImport(EdmaImport.RelationType);

			if(rel.getNumberOfIndexesOnA() > 0
					|| rel.getNumberOfIndexesOnB() > 0)
			{
				m.addImport(EdmaImport.MetaIndex);
				m.addImport(EdmaImport.IndexType);
				Set<String> attributeNamesIndexA = new HashSet<String>();
				Set<String> attributeNamesIndexB = new HashSet<String>();
				m.appendToBody(i2 + "{\n");
				for(int j = 0; j < rel.getNumberOfIndexesOnA()
						+ rel.getNumberOfIndexesOnB(); j++)
				{
					boolean onA = (j < rel.getNumberOfIndexesOnA());
					int idxNum = (onA ? j : j - rel.getNumberOfIndexesOnA());
					IMetaIndex idx = (onA ? rel.getIndexOnA(idxNum)
							: rel.getIndexOnB(idxNum));
					String indexVariableName = "metaIndexOn"
							+ (onA ? "A" : "B") + idxNum;
					m.appendToBody(i3 + "MetaIndex " + indexVariableName
							+ " = new MetaIndex(");
					switch(idx.getIndexType())
					{
						case Compare:
							m.appendToBody("IndexType.Compare");
							break;
						case Equal:
							m.appendToBody("IndexType.Equal");
							break;
						case Unique:
							m.appendToBody("IndexType.Unique");
							break;
					}
					m.appendToBody(");\n");
					m.appendToBody(i3 + indexVariableName
							+ ".setKind(mr.getKind" + (onA ? "A" : "B")
							+ "());\n");
					m.appendToBody(i3 + "mr.addIndexOn" + (onA ? "A" : "B")
							+ "(" + indexVariableName + ");\n");
					for(int k = 0; k < idx.getNumberOfAttributes(); k++)
					{
						String attVariableName = idx.getAttribute(k).getName();
						if((onA && !attributeNamesIndexA.contains(attVariableName))
								|| (!onA && !attributeNamesIndexB.contains(attVariableName)))
						{
							String kindName = (onA ? rel.getKindA().getName()
									: rel.getKindB().getName());
							m.appendToBody(i3 + "MetaAttribute att"
									+ attVariableName + " = "
									+ StringUtil.L(kindName)
									+ ".getAttribute(\""
									+ idx.getAttribute(k).getName() + "\");\n");
							if(onA) attributeNamesIndexA.add(attVariableName);
							else attributeNamesIndexB.add(attVariableName);
						}
						m.appendToBody(i3 + indexVariableName
								+ ".addAttribute(att" + attVariableName
								+ ");\n");
					}
				}
				m.appendToBody(i2 + "}\n");
			}
			m.appendToBody(i1 + "}\n");
		}

		// Views
		size = model.getNumberOfViews();
		if(size > 0)
		{
			m.appendToBody("\n" + i1 + "//Views:\n");
			for(int i = 0; i < size; ++i)
			{
				m.appendToBody(i1 + "{\n");
				IMetaMethod view = model.getView(i);
				m.appendToBody(i2
						+ "MetaMethod view = new MetaMethod(\""
						+ view.getName()
						+ "\", \""
						+ view.getDescription()
								.replace("\"", "\\\"")
								.replace("\n", "\\n") + "\");\n");
				m.addImport(EdmaImport.MetaMethod);
				int paramCount = view.getNumberOfInputParameters();
				for(int j = 0; j < paramCount; ++j)
				{
					IMetaMethodParameter param = view.getInputParameter(j);
					m.appendToBody(i2 + "view.addInputParameter(\""
							+ param.getName() + "\", \""
							+ param.getValueDomain().getName() + "\", "
							+ param.canBeNull() + ");\n");
				}
				paramCount = view.getNumberOfOutputParameters();
				for(int j = 0; j < paramCount; ++j)
				{
					IMetaMethodParameter param = view.getOutputParameter(j);
					m.appendToBody(i2 + "view.addOutputParameter(\""
							+ param.getName() + "\", \""
							+ param.getValueDomain().getName() + "\", "
							+ param.canBeNull() + ");\n");
				}
				m.appendToBody(i2 + "edma_model.addView(view);\n");
				m.appendToBody(i1 + "}\n");
			}
		}

		// Actions
		size = model.getNumberOfActions();
		if(size > 0)
		{
			m.appendToBody("\n" + i1 + "//Actions:\n");
			for(int i = 0; i < size; ++i)
			{
				m.appendToBody(i1 + "{\n");
				IMetaMethod action = model.getAction(i);
				m.appendToBody(i2
						+ "MetaMethod action = new MetaMethod(\""
						+ action.getName()
						+ "\", \""
						+ action.getDescription()
								.replace("\"", "\\\"")
								.replace("\n", "\\n") + "\");\n");
				m.addImport(EdmaImport.MetaMethod);
				int paramCount = action.getNumberOfInputParameters();
				for(int j = 0; j < paramCount; ++j)
				{
					IMetaMethodParameter param = action.getInputParameter(j);
					m.appendToBody(i2 + "action.addInputParameter(\""
							+ param.getName() + "\", \""
							+ param.getValueDomain().getName() + "\", "
							+ param.canBeNull() + ");\n");
				}
				paramCount = action.getNumberOfOutputParameters();
				for(int j = 0; j < paramCount; ++j)
				{
					IMetaMethodParameter param = action.getOutputParameter(j);
					m.appendToBody(i2 + "action.addOutputParameter(\""
							+ param.getName() + "\", \""
							+ param.getValueDomain().getName() + "\", "
							+ param.canBeNull() + ");\n");
				}
				m.appendToBody(i2 + "edma_model.addAction(action);\n");
				m.appendToBody(i1 + "}\n");
			}
		}

		// Add model to environment...
		m.appendToBody(i1 + "edma_environment.addMetaDataModel(edma_model);\n");
		m.appendToBody("}\n");
	}

}
