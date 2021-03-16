package com.cphdiving.divewithus.edma.usercode.models.coursereg.views;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.views.GetCourseTypeResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseType;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class GetCourseTypeUserImpl extends Result implements GetCourseTypeResult
{
    private static final int OK = 0;
    private final CourseID in_courseID;
    private CourseType out_courseType;



    /**
     * Constructor
     * @param in_courseID  Input parameter 1
     */
    public GetCourseTypeUserImpl(CourseID in_courseID)
    {
        this.in_courseID = in_courseID;
        out_courseType = null;
    }

    /**
     * Execution of the view
     * @param view  View interface
     * @return      Return 0 if succesful or one of the error codes if not
     */
    public int execute(CourseRegViewer view)
    {
        // Implementation of getCourseType
        // Return one of the following error codes:
        // OK
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin

        CourseViewer course = view.getCourseKind().getFromID(in_courseID);
        if(course == null) out_courseType = null;
        else
        {
        	out_courseType = course.getCourseType().snapshot();
        }
        return OK;
        
        // EDMA_non-generated_code_end
    }

    /**
     * Returns the output courseType:CourseType
     * @return  The output courseType:CourseType
     */
    public CourseType getCourseType()
    {
        if(errorCode() != 0) return null;
        return out_courseType;
    }
}
