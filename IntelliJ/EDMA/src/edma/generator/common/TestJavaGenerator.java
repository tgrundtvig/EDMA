package edma.generator.common;

public class TestJavaGenerator
{
	public static void main(String[] args) 
	{
		JavaClass classA = new JavaClass("ClassA");
		JavaMethodSignature toStringSignature = new JavaMethodSignature("toString","");
		JavaMethod toStringA = new JavaMethod(toStringSignature);
		toStringA.appendToBody("return \"Class A\";");
		toStringSignature.javaDocDesc="Javaadaooooooc\nccc\ncc";
		classA.addMethod(toStringA);
		
		classA.addImport("aLib");
		
		JavaClass classBinsideA = new JavaClass("ClassB");
		JavaMethod toStringB = new JavaMethod(toStringSignature);
		toStringB.appendToBody("return \"Class B\";");
		classBinsideA.addMethod(toStringB);
		classBinsideA.addImport("bLib");
		
		JavaClass classCinsideB = new JavaClass("ClassC");
		JavaMethod toStringC = new JavaMethod(toStringSignature);
		toStringC.appendToBody("return \"Class C\";");
		classCinsideB.addMethod(toStringC);
		classCinsideB.addImport("cLib");
		
		
		classA.addInnerClass(classBinsideA);
		classBinsideA.addInnerClass(classCinsideB);
		
		
		
		JavaInterface interfaceA = new JavaInterface("InterfaceA","");
		interfaceA.addImport("iaLib");
		JavaInterface interfaceBinsideA = new JavaInterface("InterfaceB","");
		interfaceA.addInnerInterface(interfaceBinsideA);
		interfaceBinsideA.addImport("ibLib");
		
		classA.addInnerInterface(interfaceA);
		
		JavaClass classDinsideIA = new JavaClass("ClassD");
		JavaMethod toStringD = new JavaMethod(toStringSignature);
		toStringD.appendToBody("return \"Class D\";");
		classDinsideIA.addMethod(toStringD);
		classDinsideIA.addImport("dLib");
		interfaceA.addInnerClass(classDinsideIA);
		
		System.out.println(JavaWriter.toString(interfaceA));
		System.out.println("////////");
		System.out.println(JavaWriter.toString(classA));
		
	}
}
