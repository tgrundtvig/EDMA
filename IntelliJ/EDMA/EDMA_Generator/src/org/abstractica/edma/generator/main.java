package org.abstractica.edma.generator;

public class main
{
	public static void main(String[] args)
	{
		if(args.length != 2)
		{
			String msg =    "Usage:\n" +
							"1 - Create a new IntelliJ Project.\n" +
							"2 - Import the EDMA_Runtime.jar in your project.\n" +
							"3 - Create a folder in the root dir of the project named \"edmasrc\".\n" +
							"4 - Put your EDMA source files into the \"edmasrc\" dir.\n" +
							"5 - Copy EDMA_Generator.jar into the project folder.\n" +
							"6 - Run: \"Java -jar EDMA_Generator <ProjectName> <package>\" in the project dir.\n" +
							"7 - Build the project in IntelliJ.\n" +
							"8 - Implement your views and actions in the generated user files.\n" +
							"9 - You can update your EDMA source files and rerun the generator at any time.\n" +
							"    The code in your views and actions will remain (as long as you keep it\n" +
							"    inside the provided tags)\n";
			System.out.println(msg);
		}
		else
		{
			String usrDir = System.getProperty("user.dir");
			EdmaGenerator gen = new EdmaGenerator(args[0], usrDir+"/edmasrc", usrDir+"/src", args[1]);
			gen.make();
		}
	}
}
