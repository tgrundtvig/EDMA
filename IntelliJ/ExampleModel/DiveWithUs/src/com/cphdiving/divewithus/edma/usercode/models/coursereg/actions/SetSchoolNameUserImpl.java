package com.cphdiving.divewithus.edma.usercode.models.coursereg.actions;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.SetSchoolNameResult;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.schoolinfo.SchoolInfoUpdater;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class SetSchoolNameUserImpl extends Result implements SetSchoolNameResult
{
    private static final int OK = 0;
    private final Name in_name;



    /**
     * Constructor
     * @param in_name  Input parameter 1
     */
    public SetSchoolNameUserImpl(Name in_name)
    {
        this.in_name = in_name;
    }

    /**
     * Execution of the action
     * @param upd  Update interface
     * @return     Return 0 to commit or one of the error codes to roll back
     */
    public int execute(CourseRegUpdater upd)
    {
        // Implementation of setSchoolName
        // Return one of the following error codes:
        // OK
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin
    	
        SchoolInfoUpdater su = upd.update(upd.getSchoolInfo());
        su.setName(in_name);
        return OK;
        
        // EDMA_non-generated_code_end
    }
}
