package com.cphdiving.divewithus.edma.usercode.models.coursereg.actions;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.TestActionResult;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonID;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class TestActionUserImpl extends Result implements TestActionResult
{
    private static final int OK = 0;



    /**
     * Constructor
     */
    public TestActionUserImpl()
    {
        
    }

    /**
     * Execution of the action
     * @param upd  Update interface
     * @return     Return 0 to commit or one of the error codes to roll back
     */
    public int execute(CourseRegUpdater upd)
    {
        // Implementation of testAction
        // Return one of the following error codes:
        // OK
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin
    	PersonViewer p = upd.getPersonKind().getFromID(PersonID.create(1L));
    	//PersonViewer p = upd.getPersonKind().getFromEmail(Email.create("tgrundtvig@gmail.com"));
    	upd.delete(p);
    	Email email = p.getEmail();
    	System.out.println(email);
    	/*
        upd.update(p).beginUpdate()
//        	.setMobile(Mobile.create("12345678"))
        	.setName(Name.create("Holger Nielsen"))
        	.save();
        */
        
    	
    	
        return OK;
        // EDMA_non-generated_code_end
    }
}
