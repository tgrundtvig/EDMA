package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseReg;
import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegInstance;
import org.abstractica.edma.runtime.intf.IDataModelInstance;

/**
 * 
 */
public class CourseRegInstImpl implements CourseRegInstance
{
    private CourseRegAPIImpl api;



    /**
     * Constructor
     * @param edma_dmi  The data model instance
     */
    public CourseRegInstImpl(IDataModelInstance edma_dmi)
    {
        this.api = new CourseRegAPIImpl(edma_dmi);
    }

    /**
     * Used to start the instance, which enables the API
     */
    public void start()
    {
        api.edma_getDMI().start();
    }

    /**
     * Used to stop the instance, which disables the API
     */
    public void stop()
    {
        api.edma_getDMI().stop();
    }

    /**
     * Used to test if the instance is running
     * @return  <tt>true</tt> if the instance is running otherwise
     *          <tt>false</tt>
     */
    public boolean isRunning()
    {
        return api.edma_getDMI().isRunning();
    }

    /**
     * Get access to the API for the CourseReg instance
     * @return  The API interface for the CourseReg instance
     */
    public CourseReg getAPI()
    {
        return api;
    }
}
