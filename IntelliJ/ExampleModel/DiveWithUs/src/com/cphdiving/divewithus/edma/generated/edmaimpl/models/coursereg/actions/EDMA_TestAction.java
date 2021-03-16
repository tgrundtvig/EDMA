package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.actions;

import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseRegVUImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.actions.TestActionUserImpl;
import org.abstractica.edma.runtime.implementations.common.AAction;
import org.abstractica.edma.runtime.intf.IAction;
import org.abstractica.edma.runtime.intf.IDataModelUpdate;

/**
 * 
 */
public class EDMA_TestAction extends AAction implements IAction
{
    private TestActionUserImpl edma_userMethod;



    /**
     * Constructor
     * @param edma_userMethod  User provided method implementation
     */
    public EDMA_TestAction(TestActionUserImpl edma_userMethod)
    {
        super("testAction");
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
            default:
                throw new RuntimeException("Error in testAction: Invalid error code: " + e);
        }
        return (e == 0);
    }

    /**
     * Returns the user method
     * @return  The user method
     */
    public TestActionUserImpl getUserMethod()
    {
        return edma_userMethod;
    }
}
