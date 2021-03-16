package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.views;

import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseRegVUImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.views.SellWetsuitUserImpl;
import org.abstractica.edma.runtime.implementations.common.AView;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IView;

/**
 * 
 */
public class EDMA_SellWetsuit extends AView implements IView
{
    private SellWetsuitUserImpl edma_userMethod;



    /**
     * Constructor
     * @param edma_userMethod  User provided method implementation
     */
    public EDMA_SellWetsuit(SellWetsuitUserImpl edma_userMethod)
    {
        super("sellWetsuit");
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
            default:
                throw new RuntimeException("Error in sellWetsuit: Invalid error code: " + e);
        }
        if(e == 0)
        {
            if(edma_userMethod.getTeachers() == null)
            {
                throw new RuntimeException("Error in sellWetsuit: Output parameter teachers is null");
            }
        }
    }

    /**
     * Returns the user method
     * @return  The user method
     */
    public SellWetsuitUserImpl getUserMethod()
    {
        return edma_userMethod;
    }
}
