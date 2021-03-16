package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.views;

import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseRegVUImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.views.GetStudentEnrollmentsUserImpl;
import org.abstractica.edma.runtime.implementations.common.AView;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IView;

/**
 * 
 */
public class EDMA_GetStudentEnrollments extends AView implements IView
{
    private GetStudentEnrollmentsUserImpl edma_userMethod;



    /**
     * Constructor
     * @param edma_userMethod  User provided method implementation
     */
    public EDMA_GetStudentEnrollments(GetStudentEnrollmentsUserImpl edma_userMethod)
    {
        super("getStudentEnrollments");
        this.edma_userMethod = edma_userMethod;
    }

    /**
     * Execution of the view
     * @param edma_dm  Data model view interface
     */
    public void execute(IDataModelView edma_dm)
    {
        int e = edma_userMethod.execute(new CourseRegVUImpl(edma_dm));
        switch(e)
        {
            case 0:
                edma_userMethod.setErrorCode(0, "OK");
                break;
            case 1:
                edma_userMethod.setErrorCode(1, "Student does not exist");
                break;
            default:
                throw new RuntimeException("Error in getStudentEnrollments: Invalid error code: " + e);
        }
        if(e == 0)
        {
            if(edma_userMethod.getCourseList() == null)
            {
                throw new RuntimeException("Error in getStudentEnrollments: Output parameter courseList is null");
            }
        }
    }

    /**
     * Returns the user method
     * @return  The user method
     */
    public GetStudentEnrollmentsUserImpl getUserMethod()
    {
        return edma_userMethod;
    }
}
