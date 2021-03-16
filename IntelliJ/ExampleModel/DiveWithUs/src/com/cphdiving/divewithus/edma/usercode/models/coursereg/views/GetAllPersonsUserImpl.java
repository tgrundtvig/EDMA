package com.cphdiving.divewithus.edma.usercode.models.coursereg.views;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.views.GetAllPersonsResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonList;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class GetAllPersonsUserImpl extends Result implements GetAllPersonsResult
{
    private static final int OK = 0;
    private PersonList out_personList;



    /**
     * Constructor
     */
    public GetAllPersonsUserImpl()
    {
        out_personList = null;
    }

    /**
     * Execution of the view
     * @param view  View interface
     * @return      Return 0 if succesful or one of the error codes if not
     */
    public int execute(CourseRegViewer view)
    {
        // Implementation of getAllPersons
        // Return one of the following error codes:
        // OK
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin
        
    	out_personList = view.getPersonKind().getAll().snapshot();
    	return OK;
        
        // EDMA_non-generated_code_end
    }

    /**
     * Returns the output personList:PersonList
     * @return  The output personList:PersonList
     */
    public PersonList getPersonList()
    {
        if(errorCode() != 0) return null;
        return out_personList;
    }
}
