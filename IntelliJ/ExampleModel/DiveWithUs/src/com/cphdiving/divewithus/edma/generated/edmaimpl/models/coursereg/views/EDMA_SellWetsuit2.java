package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.views;

import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseRegVUImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.views.SellWetsuit2UserImpl;
import org.abstractica.edma.runtime.implementations.common.AView;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IView;

/**
 * 
 */
public class EDMA_SellWetsuit2 extends AView implements IView
{
    private SellWetsuit2UserImpl edma_userMethod;



    /**
     * Constructor
     * @param edma_userMethod  User provided method implementation
     */
    public EDMA_SellWetsuit2(SellWetsuit2UserImpl edma_userMethod)
    {
        super("sellWetsuit2");
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
                throw new RuntimeException("Error in sellWetsuit2: Invalid error code: " + e);
        }
        if(e == 0)
        {
            if(edma_userMethod.getTeachers() == null)
            {
                throw new RuntimeException("Error in sellWetsuit2: Output parameter teachers is null");
            }
        }
    }

    /**
     * Returns the user method
     * @return  The user method
     */
    public SellWetsuit2UserImpl getUserMethod()
    {
        return edma_userMethod;
    }
}
