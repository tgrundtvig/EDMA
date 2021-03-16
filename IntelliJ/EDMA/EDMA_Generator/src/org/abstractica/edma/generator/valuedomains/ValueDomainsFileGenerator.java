package org.abstractica.edma.generator.valuedomains;

import java.util.ArrayList;

import org.abstractica.edma.generator.IPackageStructure;
import org.abstractica.edma.generator.ISourcePath;
import org.abstractica.edma.generator.common.JavaClass;
import org.abstractica.edma.generator.common.JavaWriter;
import org.abstractica.edma.util.FileUtil;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.IValueDomainDefinitions;

public class ValueDomainsFileGenerator
{
	private IPackageStructure pkgStruct;
	private ISourcePath src;
	public ValueDomainsFileGenerator(	IPackageStructure pkgStruct,
										ISourcePath src)
	{
		super();
		this.pkgStruct = pkgStruct;
		this.src = src;
	}
	
	public void clean()
	{
		String srcPath = src.getSourcePath();
		String[] deletePackages = pkgStruct.getPackagesToDeleteOnClean();
		for(String pkg : deletePackages)
		{
			FileUtil.deleteDirectory(packageToDirectory(pkg, srcPath));
		}
	}
	
	public void make(IValueDomainDefinitions definitions)
	{
		String srcPath = src.getSourcePath();
		ArrayList<JavaClass> autoClasses = new ArrayList<JavaClass>();
		ArrayList<JavaClass> userClasses = new ArrayList<JavaClass>();
		int count = definitions.getNumberOfValueDomains();
		ArrayList<IMetaValueDomain> domains = new ArrayList<>();
		for(int i = 0; i < count; ++i)
		{
			domains.add(definitions.getValueDomain(i));
		}
		new Edma_ValueDomainGenerator(pkgStruct, domains, autoClasses, userClasses);
		for(JavaClass clazz : autoClasses)
		{
			JavaWriter.toFile(clazz, srcPath);
		}
		for(JavaClass clazz : userClasses)
		{
			JavaWriter.toFileIfNotExists(clazz, srcPath);
		}
	}
	
	public void cleanAndMake(IValueDomainDefinitions definitions)
	{
		clean();
		make(definitions);
	}
	
	private String packageToDirectory(String pkg, String src)
	{
		return src + "/" + pkg.replace('.', '/'); 
	}
	
}
