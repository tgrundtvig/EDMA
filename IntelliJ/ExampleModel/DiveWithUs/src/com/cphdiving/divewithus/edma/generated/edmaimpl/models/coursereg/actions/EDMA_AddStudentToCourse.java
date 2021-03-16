package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.actions;

import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseRegVUImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.actions.AddStudentToCourseUserImpl;
import org.abstractica.edma.runtime.implementations.common.AAction;
import org.abstractica.edma.runtime.intf.IAction;
import org.abstractica.edma.runtime.intf.IDataModelUpdate;

/**
 * 
 */
public class EDMA_AddStudentToCourse extends AAction implements IAction
{
    private AddStudentToCourseUserImpl edma_userMethod;



    /**
     * Constructor
     * @param edma_userMethod  User provided method implementation
     */
    public EDMA_AddStudentToCourse(AddStudentToCourseUserImpl edma_userMethod)
    {
        super("addStudentToCourse");
        this.edma_userMethod = edma_userMethod;
    }

    /**
     * Execution of the action
     * @param edma_dm  Data model update interface
     * @return         Return <tt>true</tt> to commit or <tt>false</tt> to
     *                 rollback
     */
    public boolean execute(IDataModelUpdate edma_dm)
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
            case 2:
                edma_userMethod.setErrorCode(2, "Course does not exist");
                break;
            case 3:
                edma_userMethod.setErrorCode(3, "Course has wrong status");
                break;
            case 4:
                edma_userMethod.setErrorCode(4, "Student is already on course");
                break;
            case 5:
                edma_userMethod.setErrorCode(5, "Course is full");
                break;
            case 6:
                edma_userMethod.setErrorCode(6, "Student has not passed required course");
                break;
            case 7:
                edma_userMethod.setErrorCode(7, "Student does not have enough credit");
                break;
            default:
                throw new RuntimeException("Error in addStudentToCourse: Invalid error code: " + e);
        }
        return (e == 0);
    }

    /**
     * Returns the user method
     * @return  The user method
     */
    public AddStudentToCourseUserImpl getUserMethod()
    {
        return edma_userMethod;
    }
}
