package edma.generator;

import java.util.ArrayList;

import edma.compiler.Compiler;
import edma.generator.common.JavaClass;
import edma.generator.common.JavaInterface;
import edma.generator.common.JavaWriter;
import edma.generator.metamodel.EnvironmentGenerator;
import edma.generator.metamodel.model.ModelGenerator;
import edma.generator.valuedomains.Edma_ValueDomainGenerator;
import edma.metamodel.IMetaDataModel;
import edma.metamodel.IMetaEnvironment;
import edma.util.FileUtil;
import edma.valuedomains.IMetaValueDomain;

public class EdmaGenerator
{
	private IPackageStructure pkgStruct;
	private String javaSrcDir;
	private String edmaSrcDir;
	private String envName;

	public EdmaGenerator(	String environmentName,
							String edmaSrcDir,
							String javaSrcDir,
							String rootPackage)
	{
		this.javaSrcDir = javaSrcDir;
		this.edmaSrcDir = edmaSrcDir;
		this.envName = environmentName;
		this.pkgStruct = new EdmaPackageStructure(rootPackage + "."
				+ environmentName.toLowerCase(), environmentName);
	}

	public void clean()
	{
		System.out.println("Deleting generated directories...");
		for(String pck : pkgStruct.getPackagesToDeleteOnClean())
		{
			String dirName = javaSrcDir + "/" + pck.replace('.', '/');
			FileUtil.deleteDirectory(dirName);
		}
	}

	public void make()
	{
		System.out.println("Gathering and compiling input files...");
		IMetaEnvironment env = Compiler.compile(envName, edmaSrcDir);
		if(env == null) return;
		ArrayList<JavaInterface> autoGeneratedInterfaces = new ArrayList<JavaInterface>();
		ArrayList<JavaClass> autoGeneratedClasses = new ArrayList<JavaClass>();
		ArrayList<JavaClass> userGeneratedClasses = new ArrayList<JavaClass>();
		System.out.println("Generating environment generator...");
		EnvironmentGenerator eg = new EnvironmentGenerator(pkgStruct, env);
		autoGeneratedClasses.add(eg.getImpl());
		System.out.println("Generating value domain classes...");
		int count = env.getValueDomainDefinitions().getNumberOfValueDomains();
		ArrayList<IMetaValueDomain> domains = new ArrayList<>();
		for(int i = 0; i < count; ++i)
		{
			domains.add(env.getValueDomainDefinitions().getValueDomain(i));
		}
		new Edma_ValueDomainGenerator(	pkgStruct,
										domains,
										autoGeneratedClasses,
										userGeneratedClasses);
		int modelCount = env.getNumberOfDataModels();
		for(int i = 0; i < modelCount; ++i)
		{
			IMetaDataModel model = env.getMetaDataModel(i);
			System.out.println("Generating interfaces and classes for data model: "
					+ model.getName() + "...");
			new ModelGenerator(	pkgStruct,
								model,
								autoGeneratedInterfaces,
								autoGeneratedClasses,
								userGeneratedClasses);
		}
		clean();
		System.out.println("Writing generated java files...");
		for(JavaInterface intf : autoGeneratedInterfaces)
		{
			JavaWriter.toFile(intf, javaSrcDir);
		}
		for(JavaClass clazz : autoGeneratedClasses)
		{
			JavaWriter.toFile(clazz, javaSrcDir);
		}
		for(JavaClass clazz : userGeneratedClasses)
		{
			JavaWriter.writeUserClass(clazz, javaSrcDir);
		}
		System.out.println("All done!");
	}
}