package com.cphdiving.divewithus.edma;

import java.io.IOException;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseReg;
import com.cphdiving.divewithus.edma.generated.coursereg.remote.CourseRegClientInstance;
import com.cphdiving.divewithus.edma.generated.coursereg.test.CourseRegTest;
import org.abstractica.edma.valuedomains.userinput.SimpleTerminal;


public class ClientTest
{
	public static void main(String[] args) throws IOException
	{
		CourseReg dm = new CourseRegClientInstance("localhost", 1867);
		CourseRegTest test = new CourseRegTest(dm, new SimpleTerminal());
		test.start();
	}
}
