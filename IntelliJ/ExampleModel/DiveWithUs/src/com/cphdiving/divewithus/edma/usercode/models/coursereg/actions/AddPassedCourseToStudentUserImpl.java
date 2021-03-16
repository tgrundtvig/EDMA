package com.cphdiving.divewithus.edma.usercode.models.coursereg.actions;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.AddPassedCourseToStudentResult;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentID;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.CourseRegUtil;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class AddPassedCourseToStudentUserImpl extends Result implements AddPassedCourseToStudentResult
{
    private static final int OK = 0;
    private static final int STUDENT_DOES_NOT_EXIST = 1;
    private static final int COURSE_TYPE_DOES_NOT_EXIST = 2;
    private static final int STUDENT_HAS_NOT_PASSED_REQUIRED_COURSES = 3;
    private static final int STUDENT_ALREADY_PASSED_COURSE_TYPE = 4;
    private final StudentID in_studentID;
    private final CourseTypeID in_courseTypeID;



    /**
     * Constructor
     * @param in_studentID     Input parameter 1
     * @param in_courseTypeID  Input parameter 2
     */
    public AddPassedCourseToStudentUserImpl(StudentID in_studentID, CourseTypeID in_courseTypeID)
    {
        this.in_studentID = in_studentID;
        this.in_courseTypeID = in_courseTypeID;
    }

    /**
     * Execution of the action
     * @param upd  Update interface
     * @return     Return 0 to commit or one of the error codes to roll back
     */
    public int execute(CourseRegUpdater upd)
    {
        // Implementation of addPassedCourseToStudent
        // Return one of the following error codes:
        // OK
        // STUDENT_DOES_NOT_EXIST
        // COURSE_TYPE_DOES_NOT_EXIST
        // STUDENT_HAS_NOT_PASSED_REQUIRED_COURSES
        // STUDENT_ALREADY_PASSED_COURSE_TYPE
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin

		StudentViewer student = upd.getStudentKind().getFromID(in_studentID);
		if(student == null)
		{
			return STUDENT_DOES_NOT_EXIST;
		}

		CourseTypeViewer courseType = upd.getCourseTypeKind()
											.getFromID(in_courseTypeID);
		if(courseType == null)
		{
			return COURSE_TYPE_DOES_NOT_EXIST;
		}

		String failed = CourseRegUtil.testStudentRequirements(	student,
																courseType);
		if(failed != null)
		{
			return STUDENT_HAS_NOT_PASSED_REQUIRED_COURSES;
		}

		boolean res = upd.update(student).addPassedCourse(courseType);
		if(!res)
		{
			return STUDENT_ALREADY_PASSED_COURSE_TYPE;
		}

		return OK;

        // EDMA_non-generated_code_end
    }
}
