package com.cphdiving.divewithus.edma.generated.coursereg.remote;

import com.cphdiving.divewithus.edma.generated.coursereg.views.GetSchoolNameResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;

/**
 * 
 */
public class RemoteGetSchoolNameResult implements GetSchoolNameResult
{
    private int edma_errorCode;
    private String edma_errorMsg;
    private String edma_errorDesc;
    private Name schoolName;



    /**
     * Constructor
     * @param edma_errorCode  Error code
     * @param edma_errorMsg   Error message
     * @param edma_errorDesc  Error description
     * @param schoolName      output parameter schoolName
     */
    public RemoteGetSchoolNameResult(int edma_errorCode, String edma_errorMsg, String edma_errorDesc, Name schoolName)
    {
        this.edma_errorCode = edma_errorCode;
        this.edma_errorMsg = edma_errorMsg;
        this.edma_errorDesc = edma_errorDesc;
        this.schoolName = schoolName;
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
     * get output parameter schoolName
     * @return  The output parameter schoolName
     */
    public Name getSchoolName()
    {
        return schoolName;
    }
}
