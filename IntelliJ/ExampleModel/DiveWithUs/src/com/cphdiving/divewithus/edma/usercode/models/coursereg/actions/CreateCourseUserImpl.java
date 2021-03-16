package com.cphdiving.divewithus.edma.usercode.models.coursereg.actions;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.CreateCourseResult;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.DateAndTime;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class CreateCourseUserImpl extends Result implements CreateCourseResult
{
    private static final int OK = 0;
    private static final int COURSE_TYPE_DOES_NOT_EXIST = 1;
    private final DateAndTime in_start;
    private final CourseTypeID in_courseTypeID;
    private CourseID out_id;



    /**
     * Constructor
     * @param in_start         Input parameter 1
     * @param in_courseTypeID  Input parameter 2
     */
    public CreateCourseUserImpl(DateAndTime in_start, CourseTypeID in_courseTypeID)
    {
        this.in_start = in_start;
        this.in_courseTypeID = in_courseTypeID;
        out_id = null;
    }

    /**
     * Execution of the action
     * @param upd  Update interface
     * @return     Return 0 to commit or one of the error codes to roll back
     */
    public int execute(CourseRegUpdater upd)
    {
        // Implementation of createCourse
        // Return one of the following error codes:
        // OK
        // COURSE_TYPE_DOES_NOT_EXIST
        
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
		// Create new course
		CourseStatus status = CourseStatus.create("Created");

		CourseUpdater course = upd.newCourse().startDate(in_start.date()).startTime(in_start.time()).status(status);
		course.setCourseType(courseType);
		out_id = course.getID();
		return OK;

        // EDMA_non-generated_code_end
    }

    /**
     * Returns the output id:CourseID
     * @return  The output id:CourseID
     */
    public CourseID getId()
    {
        if(errorCode() != 0) return null;
        return out_id;
    }
}
