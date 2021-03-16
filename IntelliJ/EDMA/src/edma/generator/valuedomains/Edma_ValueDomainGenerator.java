package edma.generator.valuedomains;

import java.util.ArrayList;
import java.util.Collection;

import edma.generator.AGenerator;
import edma.generator.IPackageStructure;
import edma.generator.common.JavaClass;
import edma.valuedomains.IMetaValueDomain;
import edma.valuedomains.IMetaValueDomain.EMetaType;

public class Edma_ValueDomainGenerator extends AGenerator
{
	private final Collection<IMetaValueDomain> valueDomains;
	private ArrayList<JavaClass> autoGeneratedClasses;
	private ArrayList<JavaClass> userGeneratedClasses;

	public Edma_ValueDomainGenerator(	IPackageStructure pkgStruct,
										Collection<IMetaValueDomain> valueDomains,
										ArrayList<JavaClass> autoClasses,
										ArrayList<JavaClass> userClasses)
	{
		super(pkgStruct);
		this.valueDomains = valueDomains;
		autoGeneratedClasses = autoClasses;
		userGeneratedClasses = userClasses;
		createClasses();
	}

	private void createClasses()
	{
		for(IMetaValueDomain vd : valueDomains)
		{
			if(vd.getBasicType() != null)
			{
				BasicGenerator bg = new BasicGenerator(	getPackageStructure(),
														vd);
				autoGeneratedClasses.add(bg.getIntf());
				autoGeneratedClasses.add(bg.getImpl());
			}
			else
			{
				if(vd.getEMetaType() == EMetaType.List)
				{
					ListGenerator lg = new ListGenerator(	getPackageStructure(),
															vd.asList());
					autoGeneratedClasses.add(lg.getIntf());
					autoGeneratedClasses.add(lg.getImpl());
					autoGeneratedClasses.add(lg.getBuilderImpl());
				}
				else if(vd.getEMetaType() == EMetaType.Struct)
				{
					StructGenerator sg = new StructGenerator(	getPackageStructure(),
																vd.asStruct());
					autoGeneratedClasses.add(sg.getIntf());
					autoGeneratedClasses.add(sg.getImpl());
					if(vd.asStruct().getNumberOfFields() > 0)
					{
						autoGeneratedClasses.add(sg.getBuilderImpl());
					}
				}
				else if(vd.getEMetaType() == EMetaType.Map)
				{
					MapGenerator mg = new MapGenerator(	getPackageStructure(),
														vd.asMap());
					autoGeneratedClasses.add(mg.getIntf());
					autoGeneratedClasses.add(mg.getImpl());
					autoGeneratedClasses.add(mg.getBuilderImpl());
				}
				else if(vd.getEMetaType() == EMetaType.OneOf)
				{
					OneOfGenerator og = new OneOfGenerator(	getPackageStructure(),
															vd.asOneOf());
					autoGeneratedClasses.add(og.getIntf());
					autoGeneratedClasses.add(og.getImpl());
				}
				else throw new RuntimeException("Unknown value domain type: "
						+ vd.getEMetaType());
			}
		}

		ConstraintGenerator cg = new ConstraintGenerator(	getPackageStructure(),
															valueDomains);
		autoGeneratedClasses.add(cg.getExternalConstraintsClass());
		userGeneratedClasses.addAll(cg.getUserConstraintClasses());
	}

}