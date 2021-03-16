package com.cphdiving.divewithus.edma.generated.coursereg.remote;

import com.cphdiving.divewithus.edma.generated.coursereg.views.SellWetsuitResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonList;

/**
 * 
 */
public class RemoteSellWetsuitResult implements SellWetsuitResult
{
    private int edma_errorCode;
    private String edma_errorMsg;
    private String edma_errorDesc;
    private PersonList teachers;



    /**
     * Constructor
     * @param edma_errorCode  Error code
     * @param edma_errorMsg   Error message
     * @param edma_errorDesc  Error description
     * @param teachers        output parameter teachers
     */
    public RemoteSellWetsuitResult(int edma_errorCode, String edma_errorMsg, String edma_errorDesc, PersonList teachers)
    {
        this.edma_errorCode = edma_errorCode;
        this.edma_errorMsg = edma_errorMsg;
        this.edma_errorDesc = edma_errorDesc;
        this.teachers = teachers;
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
     * get output parameter teachers
     * @return  The output parameter teachers
     */
    public PersonList getTeachers()
    {
        return teachers;
    }
}
