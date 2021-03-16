package com.cphdiving.divewithus.edma.usercode.models.coursereg.actions;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.SetTeacherOnCourseResult;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherID;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class SetTeacherOnCourseUserImpl extends Result implements SetTeacherOnCourseResult
{
    private static final int OK = 0;
    private static final int TEACHER_DOES_NOT_EXIST = 1;
    private static final int COURSE_DOES_NOT_EXIST = 2;
    private static final int COURSE_HAS_WRONG_STATUS = 3;
    private static final int COURSE_ALREADY_HAS_A_TEACHER = 4;
    private static final int TEACHER_ALREADY_ON_THIS_COURSE = 5;
    private static final int TEACHER_CAN_NOT_TEACH_THIS_COURSE = 6;
    private final TeacherID in_teacherID;
    private final CourseID in_courseID;



    /**
     * Constructor
     * @param in_teacherID  Input parameter 1
     * @param in_courseID   Input parameter 2
     */
    public SetTeacherOnCourseUserImpl(TeacherID in_teacherID, CourseID in_courseID)
    {
        this.in_teacherID = in_teacherID;
        this.in_courseID = in_courseID;
    }

    /**
     * Execution of the action
     * @param upd  Update interface
     * @return     Return 0 to commit or one of the error codes to roll back
     */
    public int execute(CourseRegUpdater upd)
    {
        // Implementation of setTeacherOnCourse
        // Return one of the following error codes:
        // OK
        // TEACHER_DOES_NOT_EXIST
        // COURSE_DOES_NOT_EXIST
        // COURSE_HAS_WRONG_STATUS
        // COURSE_ALREADY_HAS_A_TEACHER
        // TEACHER_ALREADY_ON_THIS_COURSE
        // TEACHER_CAN_NOT_TEACH_THIS_COURSE
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin

		TeacherViewer teacher = upd.getTeacherKind().getFromID(in_teacherID);
		if(teacher == null)
		{
			return TEACHER_DOES_NOT_EXIST;
		}
		CourseViewer course = upd.getCourseKind().getFromID(in_courseID);
		if(course == null)
		{
			return COURSE_DOES_NOT_EXIST;
		}

		String status = course.getStatus().value();
		if(!(status.equals("Created") || status.equals("Ready")))
		{
			setErrorDescription("Course has status: " + status);
			return COURSE_HAS_WRONG_STATUS;
		}

		TeacherViewer curTeacher = course.getTeacher();
		if(curTeacher != null)
		{
			if(!curTeacher.equals(teacher)) return COURSE_ALREADY_HAS_A_TEACHER;
			else return TEACHER_ALREADY_ON_THIS_COURSE;
		}

		CourseTypeViewer courseType = course.getCourseType();
		if(courseType == null){ throw new RuntimeException("This should never happen: Course has no type"); }

		if(!teacher.getCourseTypeSet().contains(courseType))
		{
			return TEACHER_CAN_NOT_TEACH_THIS_COURSE;
		}

		upd.update(course).setTeacher(teacher);

		// Test if we should update course status
		if(status.equals("Created"))
		{
			int size = course.getStudentSet().size();
			if(size >= courseType.getSize().min().value())
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
