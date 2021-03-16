# EDMA
Effective Data Model Abstraction

**There is a user manual on the language in the report appendix A.**

1. Create a new IntelliJ Project.
1. Import the EDMA_Runtime.jar in your project.
1. Create a folder in the root dir of the project named \"edmasrc\".
1. Put your EDMA source files into the \"edmasrc\" dir.
1. Copy EDMA_Generator.jar into the project folder.
1. Run: "Java -jar EDMA_Generator `<ProjectName>` `<package>`" in the project dir.
1. Build the project in IntelliJ.
1. Implement your views and actions in the generated user files.
1. You can update your EDMA source files and rerun the generator at any time.
   The code in your views and actions will remain (as long as you keep it
   inside the provided tags).
	
	

