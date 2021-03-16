package com.cphdiving.divewithus.edma.generated.coursereg.remote;

import com.cphdiving.divewithus.edma.generated.coursereg.actions.CreateTeacherResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherID;

/**
 * 
 */
public class RemoteCreateTeacherResult implements CreateTeacherResult
{
    private int edma_errorCode;
    private String edma_errorMsg;
    private String edma_errorDesc;
    private TeacherID id;



    /**
     * Constructor
     * @param edma_errorCode  Error code
     * @param edma_errorMsg   Error message
     * @param edma_errorDesc  Error description
     * @param id              output parameter id
     */
    public RemoteCreateTeacherResult(int edma_errorCode, String edma_errorMsg, String edma_errorDesc, TeacherID id)
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
    public TeacherID getId()
    {
        return id;
    }
}
