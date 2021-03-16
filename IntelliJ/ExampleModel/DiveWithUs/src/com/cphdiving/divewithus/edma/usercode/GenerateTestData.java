package com.cphdiving.divewithus.edma.usercode;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseReg;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.AddCourseTypeRequirementResult;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.CreateCourseResult;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.CreateCourseTypeResult;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.CreatePersonResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.*;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.*;

import java.io.IOException;


public class GenerateTestData
{
	private CourseReg dm;

	// Course types:
	private CourseTypeID poolDiving;
	private CourseTypeID beachDiving;
	private CourseTypeID lakeDiving;
	private CourseTypeID oceanDiving;

	// Persons
	private PersonID peter;
	private PersonID hans;
	private PersonID maria;
	private PersonID niels;
	private PersonID john;
	private PersonID cathrine;

	// Students
	private StudentID peterStudent;
	private StudentID hansStudent;
	private StudentID mariaStudent;
	private StudentID nielsStudent;

	// Teachers
	private TeacherID cathrineTeacher;
	private TeacherID johnTeacher;
	private TeacherID nielsTeacher;
	private TeacherID mariaTeacher;

	// Courses
	private CourseID pool_7_1_1200;
	private CourseID lake_7_1_1200;
	private CourseID pool_7_1_1500;
	private CourseID ocean_7_1_1800;
	private CourseID beach_7_1_1500;
	private CourseID pool_8_1_1200;
	private CourseID lake_8_1_1200;
	private CourseID pool_8_1_1500;
	private CourseID ocean_8_1_1800;
	private CourseID beach_8_1_1500;

	public GenerateTestData(CourseReg dm)
	{
		this.dm = dm;
	}

	public void generateData() throws IOException
	{
		createCourseTypes();
		createCourseTypeRequirements();
		createCourses();
		createPersons();
	}

	public void createPersons() throws IOException
	{
		// Create a new person
		// Status codes:
		// 0 - OK
		// 1 - email already exists
		// 2 - mobile already exists

		peter = createPerson("Peter Hansen", "pjh@gmail.com", "42354675", 0);
		hans = createPerson("Hans Mikkelsen", "hans@gmail.com", "75638693", 0);
		maria = createPerson(	"Maria Lene Nielsen",
								"mln@gmail.com",
								"27693659",
								0);
		niels = createPerson("Niels Henriksen", "nh@gmail.com", "33769475", 0);
		john = createPerson("John Peter Jensen", "jpj@gmail.com", "73869205", 0);
		cathrine = createPerson("Cathrine Jensen",
								"cj@gmail.com",
								"26745863",
								0);
		// Try an existing email...
		createPerson("Georg Nielsen", "pjh@gmail.com", "43579682", 1);
		// Try an existing mobile...
		createPerson("Georg Nielsen", "gn@gmail.com", "27693659", 2);
	}
	
	public void createStudents() throws IOException
	{
		//Create a new student from person
		// Status codes:
		//  0 - OK
		//  1 - Person does not exist
		//  2 - Person is already a student

		peter = createPerson("Peter Hansen", "pjh@gmail.com", "42354675", 0);
		hans = createPerson("Hans Mikkelsen", "hans@gmail.com", "75638693", 0);
		maria = createPerson(	"Maria Lene Nielsen",
								"mln@gmail.com",
								"27693659",
								0);
		niels = createPerson("Niels Henriksen", "nh@gmail.com", "33769475", 0);
		john = createPerson("John Peter Jensen", "jpj@gmail.com", "73869205", 0);
		cathrine = createPerson("Cathrine Jensen",
								"cj@gmail.com",
								"26745863",
								0);
		// Try an existing email...
		createPerson("Georg Nielsen", "pjh@gmail.com", "43579682", 1);
		// Try an existing mobile...
		createPerson("Georg Nielsen", "gn@gmail.com", "27693659", 2);
	}

	public void createCourseTypes() throws IOException
	{
		// Create new course type
		// Status codes:
		// 0 - OK
		// 1 - CourseType already exists

		// Create course types
		poolDiving = createCourseType("Diving in a pool", 4, 6, 800, 0);
		beachDiving = createCourseType("Diving from the beach", 2, 4, 1200, 0);
		lakeDiving = createCourseType("Diving in a lake", 2, 4, 1200, 0);
		oceanDiving = createCourseType("Diving in the ocean", 2, 4, 1200, 0);

		// Try to create one that is already there...
		createCourseType("Diving in a pool", 3, 5, 850, 1);
	}

	public void createCourseTypeRequirements() throws IOException
	{
		// Add a requirement to a course type
		// Status codes:
		// 0 - OK
		// 1 - CourseType does not exist
		// 2 - Required does not exist
		// 3 - Dependency already exists
		// 4 - Circular dependency

		// Set up some requirements
		setRequired(beachDiving, poolDiving, 0);
		setRequired(lakeDiving, poolDiving, 0);
		setRequired(oceanDiving, beachDiving, 0);
		setRequired(oceanDiving, lakeDiving, 0);

		// Try a non existing ID
		CourseTypeID nonExist = CourseTypeID.create(42L);
		setRequired(nonExist, lakeDiving, 1);
		setRequired(lakeDiving, nonExist, 2);

		// Try to create a requirement that is already there
		setRequired(lakeDiving, poolDiving, 3);

		// Try a circular requirement
		setRequired(poolDiving, oceanDiving, 4);
	}

	public void createCourses() throws IOException
	{
		// Create new course
		// Status codes:
		// 0 - OK
		// 1 - CourseType id does not exist
		Date d7_1 = Date.fromString("(2012, 1, 7)");
		Date d8_1 = Date.fromString("(2012, 1, 8)");
		Time t1200 = Time.create().hour(12).min(0).second(0).milliseconds(0);
		Time t1500 = Time.create().hour(15).min(0).second(0).milliseconds(0);
		Time t1800 = Time.create().hour(18).min(0).second(0).milliseconds(0);
		

		pool_7_1_1200 = createCourse(DateAndTime.create().date(d7_1).time(t1200), poolDiving, 0);
		lake_7_1_1200 = createCourse(DateAndTime.create().date(d7_1).time(t1200), lakeDiving, 0);
		pool_7_1_1500 = createCourse(DateAndTime.create().date(d7_1).time(t1500), poolDiving, 0);
		ocean_7_1_1800 = createCourse(DateAndTime.create().date(d7_1).time(t1800), oceanDiving, 0);
		beach_7_1_1500 = createCourse(DateAndTime.create().date(d7_1).time(t1500), beachDiving, 0);
		pool_8_1_1200 = createCourse(DateAndTime.create().date(d8_1).time(t1200), poolDiving, 0);
		lake_8_1_1200 = createCourse(DateAndTime.create().date(d8_1).time(t1200), lakeDiving, 0);
		pool_8_1_1500 = createCourse(DateAndTime.create().date(d8_1).time(t1500), poolDiving, 0);
		ocean_8_1_1800 = createCourse(DateAndTime.create().date(d8_1).time(t1800), oceanDiving, 0);
		beach_8_1_1500 = createCourse(DateAndTime.create().date(d8_1).time(t1500), beachDiving, 0);
		
		//Test for non existing type ID
		CourseTypeID non_exist = CourseTypeID.create(42L);
		CourseID foo = createCourse(DateAndTime.create().date(d7_1).time(t1200), non_exist, 1);
	}

	private PersonID createPerson(	String name,
									String email,
									String mobile,
									int expectedStatusCode) throws IOException
	{
		Name n = Name.create(name);
		Email e = Email.create(email);
		Mobile m = Mobile.create(mobile);
		CreatePersonResult res = dm.createPerson(n, e, m);
		if(res.errorCode() != expectedStatusCode){ throw new RuntimeException("Was expecting status code: "
				+ expectedStatusCode
				+ ", but got status code: "
				+ res.errorCode()); }
		return res.getId();
	}

	private CourseTypeID createCourseType(	String name,
											int min,
											int max,
											int price,
											int expectedStatusCode) throws IOException
	{
		CreateCourseTypeResult res = dm.createCourseType(	CourseName.create(name),
															CourseSize.create()
																		.min(min)
																		.max(max),
															PosInt.create(price));
		if(res.errorCode() != expectedStatusCode){ throw new RuntimeException("Was expecting status code: "
				+ expectedStatusCode
				+ ", but got status code: "
				+ res.errorCode()); }
		return res.getId();
	}

	private void setRequired(	CourseTypeID course,
								CourseTypeID required,
								int expectedStatusCode) throws IOException
	{
		AddCourseTypeRequirementResult res = dm.addCourseTypeRequirement(course, required);
		if(res.errorCode() != expectedStatusCode){ throw new RuntimeException("Was expecting status code: "
				+ expectedStatusCode
				+ ", but got status code: "
				+ res.errorCode()); }
	}

	private CourseID createCourse(	DateAndTime start,
									CourseTypeID typeID,
									int expectedStatusCode) throws IOException
	{
		CreateCourseResult res = dm.createCourse(start, typeID);
		if(res.errorCode() != expectedStatusCode){ throw new RuntimeException("Was expecting status code: "
				+ expectedStatusCode
				+ ", but got status code: "
				+ res.errorCode()); }
		return res.getId();
	}
}
