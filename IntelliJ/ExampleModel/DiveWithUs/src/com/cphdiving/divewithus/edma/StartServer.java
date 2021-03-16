package com.cphdiving.divewithus.edma;

import java.io.IOException;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegFactory;
import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegInstance;
import com.cphdiving.divewithus.edma.generated.coursereg.remote.CourseRegServerInstance;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.SchoolInfo;
import com.cphdiving.divewithus.edma.usercode.GenerateTestData;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.java.JavaCollectionFactory;
import org.abstractica.edma.runtime.implementations.mem.RuntimeFactory;
import org.abstractica.edma.runtime.remote.Server;



public class StartServer
{
	public static void main(String[] args) throws IOException
	{
		String path = null;
		if (System.getProperty("user.name").equals("martin"))
			path = "/tmp/";
		else 
			path = "C:/tmp/";
		
		DiveWithUs dwu = new DiveWithUs(new RuntimeFactory(path, new JavaCollectionFactory()));
		CourseRegFactory dmf = dwu.getCourseRegFactory();
		CourseRegInstance inst = null;
		if(dmf.exists("1"))
		{
			System.out.println("Loading test data...");
			inst = dmf.getInstance("1");
			System.out.println("Test data loaded...");
		}
		else
		{
			inst = dmf.newInstance("1", SchoolInfo.create().name("CrazyDivers"));
			inst.start();
			GenerateTestData test = new GenerateTestData(inst.getAPI());
			System.out.println("Generating test data...");
			test.generateData();
			System.out.println("Test data generation completed succesfully!");
			inst.stop();
			System.out.println("Instance stopped!");
		}
		inst.start();
		
		int port = 1867;
		Server server = new Server(port, new CourseRegServerInstance(inst.getAPI()));
		server.start();
	}
	
}
