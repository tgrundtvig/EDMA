package edma.generator.metamodel.kind;

import java.util.ArrayList;

import edma.generator.IPackageStructure;
import edma.generator.common.JavaClass;
import edma.generator.common.JavaInterface;
import edma.generator.common.JavaMethodSignature;
import edma.generator.metamodel.AModelGenerator;
import edma.metamodel.IMetaDataModel;
import edma.metamodel.IMetaKind;
import edma.util.StringUtil;

/**
 * This class is responsible for generating kind-related interfaces and classes.
 * It does so by delegating the generation to the classes: KindViewerGenerator,
 * KindUpdaterGenerator, KindSetViewerGenerator, KindSetUpdaterGenerator,
 * KindAttributeUpdaterGenerator.
 * 
 */
public class KindGenerator extends AModelGenerator
{
	public KindGenerator(	IPackageStructure pkgStruct,
							IMetaDataModel model,
							ArrayList<JavaInterface> interfaces,
							ArrayList<JavaClass> autoClasses,
							ArrayList<JavaClass> userClasses)
	{
		super(pkgStruct, model);

		// Kind interfaces and classes
		int size = model.getNumberOfKinds();
		for(int i = 0; i < size; ++i)
		{
			IMetaKind kind = model.getKind(i);
			KindAccessGenerator accessGenerator = new KindAccessGenerator(	pkgStruct,
																			model,
																			kind);
			interfaces.add(accessGenerator.getIntf());
			autoClasses.add(accessGenerator.getImpl());

			// EntityViewer
			EntityViewerGenerator viewerGenerator = new EntityViewerGenerator(	pkgStruct,
																				model,
																				kind);
			interfaces.add(viewerGenerator.getInterface());
			JavaClass entityImpl = viewerGenerator.getClazz();
			autoClasses.add(entityImpl);

			// EntityUpdater
			EntityUpdaterGenerator updaterGenerator = new EntityUpdaterGenerator(	pkgStruct,
																					model,
																					kind,
																					entityImpl);
			interfaces.add(updaterGenerator.getInterface());

			// EntitySetViewer
			EntitySetViewerGenerator setViewerGenerator = new EntitySetViewerGenerator(	pkgStruct,
																						model,
																						kind);
			interfaces.add(setViewerGenerator.getInterface());
			JavaClass entitySetImpl = setViewerGenerator.getClazz();
			autoClasses.add(entitySetImpl);

			/*
			// Constraints
			new KindConstraintsGenerator(	pkgStruct,
											model,
											autoClasses,
											userClasses);
			*/

			// Create filter interface
			JavaInterface filter = new JavaInterface(	getFilterIntfName(kind),
														"Filter interface for the "
																+ kind.getName()
																+ " kind.");
			filter.setPackage(getFilterIntfPackage(kind));
			JavaMethodSignature accept = new JavaMethodSignature(	"accept",
																	"Returns <tt>true</tt> if the entity is accepted by the filter.");
			accept.addParameter(getViewIntfName(kind),
								StringUtil.L(kind.getName()),
								"The entity to be tested by the filter.");
			accept.setReturnType("boolean");
			accept.setReturnDescription("<tt>true</tt> if the entity is accepted by the filter.");
			filter.addMethod(accept);
			interfaces.add(filter);
		}
	}
}
