package com.cphdiving.divewithus.edma.generated.coursereg.remote;

import com.cphdiving.divewithus.edma.generated.coursereg.views.GetStudentEnrollmentsResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfoList;

/**
 * 
 */
public class RemoteGetStudentEnrollmentsResult implements GetStudentEnrollmentsResult
{
    private int edma_errorCode;
    private String edma_errorMsg;
    private String edma_errorDesc;
    private CourseInfoList courseList;



    /**
     * Constructor
     * @param edma_errorCode  Error code
     * @param edma_errorMsg   Error message
     * @param edma_errorDesc  Error description
     * @param courseList      output parameter courseList
     */
    public RemoteGetStudentEnrollmentsResult(int edma_errorCode, String edma_errorMsg, String edma_errorDesc, CourseInfoList courseList)
    {
        this.edma_errorCode = edma_errorCode;
        this.edma_errorMsg = edma_errorMsg;
        this.edma_errorDesc = edma_errorDesc;
        this.courseList = courseList;
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
     * get output parameter courseList
     * @return  The output parameter courseList
     */
    public CourseInfoList getCourseList()
    {
        return courseList;
    }
}
