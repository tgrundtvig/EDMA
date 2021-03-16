package com.cphdiving.divewithus.edma.generated.coursereg.remote;

import com.cphdiving.divewithus.edma.generated.coursereg.views.GetAllCourseTypesResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeList;

/**
 * 
 */
public class RemoteGetAllCourseTypesResult implements GetAllCourseTypesResult
{
    private int edma_errorCode;
    private String edma_errorMsg;
    private String edma_errorDesc;
    private CourseTypeList courseTypeList;



    /**
     * Constructor
     * @param edma_errorCode  Error code
     * @param edma_errorMsg   Error message
     * @param edma_errorDesc  Error description
     * @param courseTypeList  output parameter courseTypeList
     */
    public RemoteGetAllCourseTypesResult(int edma_errorCode, String edma_errorMsg, String edma_errorDesc, CourseTypeList courseTypeList)
    {
        this.edma_errorCode = edma_errorCode;
        this.edma_errorMsg = edma_errorMsg;
        this.edma_errorDesc = edma_errorDesc;
        this.courseTypeList = courseTypeList;
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
     * get output parameter courseTypeList
     * @return  The output parameter courseTypeList
     */
    public CourseTypeList getCourseTypeList()
    {
        return courseTypeList;
    }
}
