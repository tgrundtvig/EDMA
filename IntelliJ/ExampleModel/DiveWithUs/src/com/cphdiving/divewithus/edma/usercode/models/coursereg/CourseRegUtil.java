package com.cphdiving.divewithus.edma.usercode.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.Date;
import com.cphdiving.divewithus.edma.generated.valuedomains.DateAndTime;
import com.cphdiving.divewithus.edma.generated.valuedomains.NotNegInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.*;

import java.util.Set;



public class CourseRegUtil
{
	static public String testCircularDependency(CourseTypeViewer courseType,
	                                            Set<String> visited,
	                                            String res)
	{
		String name = courseType.getName().value();
		res = res + ", " + name;
		if(visited.contains(name)){ return res; }

		CourseTypeSet required = courseType.getRequiredSet();
		if(required.size() > 0)
		{
			visited.add(name);
			for(CourseTypeViewer ct : required)
			{
				String tmp = testCircularDependency(ct, visited, res);
				if(tmp != null) return tmp;
			}
			visited.remove(name);
		}
		return null;
	}

	static public String testStudentRequirements(	StudentViewer student,
													CourseTypeViewer courseType)
	{
		// Test requirements
		CourseTypeSet requiredCourses = courseType.getRequiredSet();
		CourseTypeSet passedCourses = student.getPassedCourseSet();
		for(CourseTypeViewer required : requiredCourses)
		{
			if(!passedCourses.contains(required)){ return required.getName()
																	.value(); }
		}
		return null;
	}

	static public boolean testCourseStatusChange(	String oldStatus,
													String newStatus)
	{
		if(oldStatus.equals("Cancelled") || oldStatus.equals("Completed")) return false;
		if(oldStatus.equals("Created")){ return (newStatus.equals("Cancelled") || newStatus.equals("Ready")); }
		if(oldStatus.equals("Ready")){ return (newStatus.endsWith("Cancelled") || newStatus.equals("Running")); }
		// old status = "Running"
		return (newStatus.endsWith("Cancelled") || newStatus.equals("Completed"));
	}
	
	static public CourseInfoList extractCourseInfoList(CourseSet courseSet)
	{
		CourseInfoList.CourseInfoListBuilder builder = CourseInfoList.begin();
		for(CourseViewer course : courseSet)
		{
			builder.add(extractCourseInfo(course));
		}
		return builder.end();
	}

	static public CourseInfo extractCourseInfo(CourseViewer course)
	{
		CourseName type = course.getCourseType().getName();
		StudentInfoList.StudentInfoListBuilder slb = StudentInfoList.begin();
		for(StudentViewer student : course.getStudentSet())
		{
			PersonViewer person = student.asPerson();
			StudentInfo si = StudentInfo.create()
										.name(person.getName())
										.email(person.getEmail())
										.mobile(person.getMobile())
										.wetsuitSize(student.getWetsuitSize())
										.bootSize(student.getBootSize());
			slb.add(si);
		}
		StudentInfoList students = slb.end();
		TeacherViewer teacher = course.getTeacher();
		TeacherInfo ti = null;
		if(teacher != null)
		{
			PersonViewer person = teacher.asPerson();
			ti = TeacherInfo.create()
				.name(person.getName())
				.email(person.getEmail())
				.mobile(person.getMobile());
		}
		CourseInfo res = CourseInfo.create()
				.id(course.getID())
				.start(DateAndTime.create().date(course.getStartDate()).time(course.getStartTime()))
				.type(type)
				.status(course.getStatus())
				.students(students)
				.teacher(ti);
		return res;
	}
	
	static public CourseProcessResult processCourses(CourseRegUpdater upd, Date date)
	{
		CourseSet courses = upd.getCourseKind().getWhereStartDateEquals(date);
		CourseInfoList.CourseInfoListBuilder cb = CourseInfoList.begin();
		CourseInfoList.CourseInfoListBuilder rb = CourseInfoList.begin();
		for(CourseViewer course : courses)
		{
			CourseInfo ci = extractCourseInfo(course);
			if(course.getStatus().value().equals("Created"))
			{
				cancelCourse(upd, course);
				cb.add(ci);
			}
			else if(course.getStatus().value().equals("Ready"))
			{
				rb.add(ci);
				upd.update(course).beginUpdate().setStatus(CourseStatus.create("Running")).save();
			}
		}
		return CourseProcessResult.create().canceled(cb.end()).running(rb.end());
	}
	
	static public void cancelCourse(CourseRegUpdater upd, CourseViewer course)
	{
		int price = course.getCourseType().getPrice().value();
		for(StudentViewer student : course.getStudentSet())
		{
			deposit(upd, student.asPerson(), price);
		}
		upd.update(course).beginUpdate().setStatus(CourseStatus.create("Canceled")).save();
	}
	
	static public void deposit(CourseRegUpdater upd, PersonViewer person, int amount)
	{
		int balance = person.getBalance().value();
		NotNegInt newBalance = NotNegInt.create(balance + amount);
		upd.update(person).beginUpdate().setBalance(newBalance).save();
	}
}
