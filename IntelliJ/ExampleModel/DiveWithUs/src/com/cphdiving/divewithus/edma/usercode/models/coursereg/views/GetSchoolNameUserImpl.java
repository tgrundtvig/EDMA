package com.cphdiving.divewithus.edma.usercode.models.coursereg.views;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.views.GetSchoolNameResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class GetSchoolNameUserImpl extends Result implements GetSchoolNameResult
{
    private static final int OK = 0;
    private Name out_schoolName;



    /**
     * Constructor
     */
    public GetSchoolNameUserImpl()
    {
        out_schoolName = null;
    }

    /**
     * Execution of the view
     * @param view  View interface
     * @return      Return 0 if succesful or one of the error codes if not
     */
    public int execute(CourseRegViewer view)
    {
        // Implementation of getSchoolName
        // Return one of the following error codes:
        // OK
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin
        
        out_schoolName = view.getSchoolInfo().getName();
        return OK;
        
        // EDMA_non-generated_code_end
    }

    /**
     * Returns the output schoolName:Name
     * @return  The output schoolName:Name
     */
    public Name getSchoolName()
    {
        if(errorCode() != 0) return null;
        return out_schoolName;
    }
}
