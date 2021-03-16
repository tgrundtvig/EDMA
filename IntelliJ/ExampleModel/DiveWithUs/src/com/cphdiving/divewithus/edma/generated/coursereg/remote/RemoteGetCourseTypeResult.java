package com.cphdiving.divewithus.edma.generated.coursereg.remote;

import com.cphdiving.divewithus.edma.generated.coursereg.views.GetCourseTypeResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseType;

/**
 * 
 */
public class RemoteGetCourseTypeResult implements GetCourseTypeResult
{
    private int edma_errorCode;
    private String edma_errorMsg;
    private String edma_errorDesc;
    private CourseType courseType;



    /**
     * Constructor
     * @param edma_errorCode  Error code
     * @param edma_errorMsg   Error message
     * @param edma_errorDesc  Error description
     * @param courseType      output parameter courseType
     */
    public RemoteGetCourseTypeResult(int edma_errorCode, String edma_errorMsg, String edma_errorDesc, CourseType courseType)
    {
        this.edma_errorCode = edma_errorCode;
        this.edma_errorMsg = edma_errorMsg;
        this.edma_errorDesc = edma_errorDesc;
        this.courseType = courseType;
    }

    /**
     * Get the error code
     * @return  
     */
    public int errorCode()
    {
        return edma_errorCode;
    }

    /**
     * Get the error message
     * @return  
     */
    public String errorMessage()
    {
        return edma_errorMsg;
    }

    /**
     * Get the error description
     * @return  
     */
    public String errorDescription()
    {
        return edma_errorDesc;
    }

    /**
     * get output parameter courseType
     * @return  The output parameter courseType
     */
    public CourseType getCourseType()
    {
        return courseType;
    }
}
