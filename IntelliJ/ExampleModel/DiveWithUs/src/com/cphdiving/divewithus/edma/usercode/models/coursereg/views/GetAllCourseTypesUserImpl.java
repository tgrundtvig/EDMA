package com.cphdiving.divewithus.edma.usercode.models.coursereg.views;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.views.GetAllCourseTypesResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeList;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class GetAllCourseTypesUserImpl extends Result implements GetAllCourseTypesResult
{
    private static final int OK = 0;
    private CourseTypeList out_courseTypeList;



    /**
     * Constructor
     */
    public GetAllCourseTypesUserImpl()
    {
        out_courseTypeList = null;
    }

    /**
     * Execution of the view
     * @param view  View interface
     * @return      Return 0 if succesful or one of the error codes if not
     */
    public int execute(CourseRegViewer view)
    {
        // Implementation of getAllCourseTypes
        // Return one of the following error codes:
        // OK
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin
        out_courseTypeList = view.getCourseTypeKind().getAll().snapshot();
        return OK;
        
        // EDMA_non-generated_code_end
    }

    /**
     * Returns the output courseTypeList:CourseTypeList
     * @return  The output courseTypeList:CourseTypeList
     */
    public CourseTypeList getCourseTypeList()
    {
        if(errorCode() != 0) return null;
        return out_courseTypeList;
    }
}
