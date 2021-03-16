package com.cphdiving.divewithus.edma.usercode.models.coursereg.actions;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.AddStudentToCourseResult;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.NotNegInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentID;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.CourseRegUtil;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class AddStudentToCourseUserImpl extends Result implements AddStudentToCourseResult
{
    private static final int OK = 0;
    private static final int STUDENT_DOES_NOT_EXIST = 1;
    private static final int COURSE_DOES_NOT_EXIST = 2;
    private static final int COURSE_HAS_WRONG_STATUS = 3;
    private static final int STUDENT_IS_ALREADY_ON_COURSE = 4;
    private static final int COURSE_IS_FULL = 5;
    private static final int STUDENT_HAS_NOT_PASSED_REQUIRED_COURSE = 6;
    private static final int STUDENT_DOES_NOT_HAVE_ENOUGH_CREDIT = 7;
    private final StudentID in_studentID;
    private final CourseID in_courseID;



    /**
     * Constructor
     * @param in_studentID  Input parameter 1
     * @param in_courseID   Input parameter 2
     */
    public AddStudentToCourseUserImpl(StudentID in_studentID, CourseID in_courseID)
    {
        this.in_studentID = in_studentID;
        this.in_courseID = in_courseID;
    }

    /**
     * Execution of the action
     * @param upd  Update interface
     * @return     Return 0 to commit or one of the error codes to roll back
     */
    public int execute(CourseRegUpdater upd)
    {
        // Implementation of addStudentToCourse
        // Return one of the following error codes:
        // OK
        // STUDENT_DOES_NOT_EXIST
        // COURSE_DOES_NOT_EXIST
        // COURSE_HAS_WRONG_STATUS
        // STUDENT_IS_ALREADY_ON_COURSE
        // COURSE_IS_FULL
        // STUDENT_HAS_NOT_PASSED_REQUIRED_COURSE
        // STUDENT_DOES_NOT_HAVE_ENOUGH_CREDIT
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin

		CourseViewer course = upd.getCourseKind().getFromID(in_courseID);
		if(course == null){ return COURSE_DOES_NOT_EXIST; }

		String status = course.getStatus().value();
		if(!(status.equals("Created") || status.equals("Ready"))){ return COURSE_HAS_WRONG_STATUS; }

		StudentViewer student = upd.getStudentKind().getFromID(in_studentID);
		if(student == null){ return STUDENT_DOES_NOT_EXIST; }

		CourseTypeViewer courseType = course.getCourseType();
		if(courseType == null){ throw new RuntimeException("Course has no type. This should never happen"); }

		// Test if student is already on course
		StudentSet students = course.getStudentSet();
		if(students.contains(in_studentID)){ return STUDENT_IS_ALREADY_ON_COURSE; }

		// Test student requirements
		String failed = CourseRegUtil.testStudentRequirements(	student,
																courseType);
		if(failed != null)
		{
			setErrorDescription("Missing course: " + failed);
			return STUDENT_HAS_NOT_PASSED_REQUIRED_COURSE;
		}

		// Test if course is full
		int size = students.size();
		int maxSize = course.getCourseType().getSize().max().value();
		if(size >= maxSize){ return COURSE_IS_FULL; }

		// Test if student has enough credit
		int price = course.getCourseType().getPrice().value();
		int credit = student.asPerson().getBalance().value();
		if(price > credit){ return STUDENT_DOES_NOT_HAVE_ENOUGH_CREDIT; }

		// Update credit
		PersonViewer person = student.asPerson();
		upd.update(person)
			.beginUpdate()
			.setBalance(NotNegInt.create(credit - price))
			.save();

		// Add student to course
		upd.update(course).addStudent(student);

		// Test if we should update status...
		if(status.equals("Created"))
		{
			if(course.getTeacher() != null
					&& (size + 1) >= course.getCourseType()
											.getSize()
											.min()
											.value())
			{
				upd.update(course)
					.beginUpdate()
					.setStatus(CourseStatus.create("Ready"))
					.save();
			}
		}
		return OK;

        // EDMA_non-generated_code_end
    }
}
