package com.cphdiving.divewithus.edma.generated.coursereg.remote;

import com.cphdiving.divewithus.edma.generated.coursereg.actions.CreateCourseTypeResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;

/**
 * 
 */
public class RemoteCreateCourseTypeResult implements CreateCourseTypeResult
{
    private int edma_errorCode;
    private String edma_errorMsg;
    private String edma_errorDesc;
    private CourseTypeID id;



    /**
     * Constructor
     * @param edma_errorCode  Error code
     * @param edma_errorMsg   Error message
     * @param edma_errorDesc  Error description
     * @param id              output parameter id
     */
    public RemoteCreateCourseTypeResult(int edma_errorCode, String edma_errorMsg, String edma_errorDesc, CourseTypeID id)
    {
        this.edma_errorCode = edma_errorCode;
        this.edma_errorMsg = edma_errorMsg;
        this.edma_errorDesc = edma_errorDesc;
        this.id = id;
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
     * get output parameter id
     * @return  The output parameter id
     */
    public CourseTypeID getId()
    {
        return id;
    }
}
