package com.cphdiving.divewithus.edma;

import java.io.IOException;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegFactory;
import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegInstance;
import com.cphdiving.divewithus.edma.generated.coursereg.test.CourseRegTest;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.SchoolInfo;
import com.cphdiving.divewithus.edma.usercode.GenerateTestData;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.java.JavaCollectionFactory;
import org.abstractica.edma.runtime.implementations.mem.RuntimeFactory;
import org.abstractica.edma.valuedomains.userinput.SimpleTerminal;

public class LocalTest
{
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException
	{
		DiveWithUs dwu = new DiveWithUs(new RuntimeFactory("C:/tmp", new JavaCollectionFactory()));
		//DiveWithUs dwu = new DiveWithUs(new RuntimeFactory(null));
		CourseRegFactory dmf = dwu.getCourseRegFactory();
		CourseRegInstance inst = null;
		if(dmf.exists("1"))
		{
			System.out.println("Loading test data...");
			inst = dmf.getInstance("1");
			inst.start();
			System.out.println("Test data loaded...");
		}
		else
		{
			inst = dmf.newInstance("1", SchoolInfo.create().name("CrazyDivers"));
			inst.start();
			System.out.println("Generating test data...");
			new GenerateTestData(inst.getAPI()).generateData();
			System.out.println("Test data generation completed successfully!");
		}
		CourseRegTest test = new CourseRegTest(inst.getAPI(), new SimpleTerminal());
		test.start();
		inst.stop();
	}

}
