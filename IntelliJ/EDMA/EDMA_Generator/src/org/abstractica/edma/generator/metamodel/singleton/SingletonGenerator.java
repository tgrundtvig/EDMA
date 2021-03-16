package org.abstractica.edma.generator.metamodel.singleton;

import java.util.ArrayList;

import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaInterface;
import org.abstractica.edma.generator.metamodel.AModelGenerator;
import org.abstractica.edma.metamodel.IMetaDataModel;
import org.abstractica.edma.metamodel.IMetaSingleton;

public class SingletonGenerator extends AModelGenerator
{
	public SingletonGenerator(IPackageStructure pkgStruct, IMetaDataModel model, ArrayList<JavaInterface> interfaces, ArrayList<JavaClass> classes)
	{
		super(pkgStruct, model);

		int size = model.getNumberOfSingletons();
		for(int i = 0; i < size; ++i)
		{
			IMetaSingleton singleton = model.getSingleton(i);
			SingletonViewerGenerator svg = new SingletonViewerGenerator(pkgStruct,
																		model,
																		singleton);
			interfaces.add(svg.getInterface());
			JavaClass impl = svg.getClazz();

			SingletonUpdaterGenerator sug = new SingletonUpdaterGenerator(	pkgStruct,
																			model,
																			singleton,
																			impl);
			interfaces.add(sug.getIntf());
			classes.add(impl);
		}
	}
}
