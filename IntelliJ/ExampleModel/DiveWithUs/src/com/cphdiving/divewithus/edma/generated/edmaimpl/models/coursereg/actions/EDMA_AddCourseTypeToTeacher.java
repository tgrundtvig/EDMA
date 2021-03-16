package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.actions;

import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseRegVUImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.actions.AddCourseTypeToTeacherUserImpl;
import org.abstractica.edma.runtime.implementations.common.AAction;
import org.abstractica.edma.runtime.intf.IAction;
import org.abstractica.edma.runtime.intf.IDataModelUpdate;

/**
 * 
 */
public class EDMA_AddCourseTypeToTeacher extends AAction implements IAction
{
    private AddCourseTypeToTeacherUserImpl edma_userMethod;



    /**
     * Constructor
     * @param edma_userMethod  User provided method implementation
     */
    public EDMA_AddCourseTypeToTeacher(AddCourseTypeToTeacherUserImpl edma_userMethod)
    {
        super("addCourseTypeToTeacher");
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
                edma_userMethod.setErrorCode(1, "Course type does not exist");
                break;
            case 2:
                edma_userMethod.setErrorCode(2, "Teacher does not exist");
                break;
            case 3:
                edma_userMethod.setErrorCode(3, "Teacher already has course type");
                break;
            default:
                throw new RuntimeException("Error in addCourseTypeToTeacher: Invalid error code: " + e);
        }
        return (e == 0);
    }

    /**
     * Returns the user method
     * @return  The user method
     */
    public AddCourseTypeToTeacherUserImpl getUserMethod()
    {
        return edma_userMethod;
    }
}
