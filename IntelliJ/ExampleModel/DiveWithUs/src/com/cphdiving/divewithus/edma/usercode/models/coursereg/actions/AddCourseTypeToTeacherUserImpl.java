package com.cphdiving.divewithus.edma.usercode.models.coursereg.actions;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.AddCourseTypeToTeacherResult;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherID;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class AddCourseTypeToTeacherUserImpl extends Result implements AddCourseTypeToTeacherResult
{
    private static final int OK = 0;
    private static final int COURSE_TYPE_DOES_NOT_EXIST = 1;
    private static final int TEACHER_DOES_NOT_EXIST = 2;
    private static final int TEACHER_ALREADY_HAS_COURSE_TYPE = 3;
    private final CourseTypeID in_courseTypeID;
    private final TeacherID in_teacherID;



    /**
     * Constructor
     * @param in_courseTypeID  Input parameter 1
     * @param in_teacherID     Input parameter 2
     */
    public AddCourseTypeToTeacherUserImpl(CourseTypeID in_courseTypeID, TeacherID in_teacherID)
    {
        this.in_courseTypeID = in_courseTypeID;
        this.in_teacherID = in_teacherID;
    }

    /**
     * Execution of the action
     * @param upd  Update interface
     * @return     Return 0 to commit or one of the error codes to roll back
     */
    public int execute(CourseRegUpdater upd)
    {
        // Implementation of addCourseTypeToTeacher
        // Return one of the following error codes:
        // OK
        // COURSE_TYPE_DOES_NOT_EXIST
        // TEACHER_DOES_NOT_EXIST
        // TEACHER_ALREADY_HAS_COURSE_TYPE
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin

		CourseTypeViewer courseType = upd.getCourseTypeKind()
											.getFromID(in_courseTypeID);
		if(courseType == null)
		{
			return COURSE_TYPE_DOES_NOT_EXIST;
		}

		TeacherViewer teacher = upd.getTeacherKind().getFromID(in_teacherID);
		if(teacher == null)
		{
			return TEACHER_DOES_NOT_EXIST;
		}

		boolean res = upd.update(teacher).addCourseType(courseType);
		if(!res)
		{
			return TEACHER_ALREADY_HAS_COURSE_TYPE;
		}

		return OK;
		
        // EDMA_non-generated_code_end
    }
}
