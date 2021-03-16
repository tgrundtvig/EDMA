package com.cphdiving.divewithus.edma.generated.coursereg.remote;

import com.cphdiving.divewithus.edma.generated.coursereg.views.TestViewResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Person;

/**
 * 
 */
public class RemoteTestViewResult implements TestViewResult
{
    private int edma_errorCode;
    private String edma_errorMsg;
    private String edma_errorDesc;
    private Person person;



    /**
     * Constructor
     * @param edma_errorCode  Error code
     * @param edma_errorMsg   Error message
     * @param edma_errorDesc  Error description
     * @param person          output parameter person
     */
    public RemoteTestViewResult(int edma_errorCode, String edma_errorMsg, String edma_errorDesc, Person person)
    {
        this.edma_errorCode = edma_errorCode;
        this.edma_errorMsg = edma_errorMsg;
        this.edma_errorDesc = edma_errorDesc;
        this.person = person;
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
     * get output parameter person
     * @return  The output parameter person
     */
    public Person getPerson()
    {
        return person;
    }
}
