package com.cphdiving.divewithus.edma.usercode.models.coursereg.views;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.views.TestViewResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Person;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class TestViewUserImpl extends Result implements TestViewResult
{
    private static final int OK = 0;
    private final Email in_email;
    private Person out_person;



    /**
     * Constructor
     * @param in_email  Input parameter 1
     */
    public TestViewUserImpl(Email in_email)
    {
        this.in_email = in_email;
        out_person = null;
    }

    /**
     * Execution of the view
     * @param view  View interface
     * @return      Return 0 if succesful or one of the error codes if not
     */
    public int execute(CourseRegViewer view)
    {
        // Implementation of testView
        // Return one of the following error codes:
        // OK
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin
        throw new RuntimeException("TODO : put your implementation of testView here..");
        
        // EDMA_non-generated_code_end
    }

    /**
     * Returns the output person:Person
     * @return  The output person:Person
     */
    public Person getPerson()
    {
        if(errorCode() != 0) return null;
        return out_person;
    }
}
