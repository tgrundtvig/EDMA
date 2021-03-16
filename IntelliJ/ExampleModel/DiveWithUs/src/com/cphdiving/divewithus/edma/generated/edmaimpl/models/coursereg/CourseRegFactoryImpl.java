package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegFactory;
import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegInstance;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseRegInstImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.SchoolInfo;
import java.io.IOException;
import org.abstractica.edma.runtime.intf.IDataModelInstanceFactory;
import org.abstractica.edma.valuedomains.IValueInstance;

/**
 * 
 */
public class CourseRegFactoryImpl implements CourseRegFactory
{
    private IDataModelInstanceFactory edma_instanceFactory;



    /**
     * Constructor
     * @param edma_instanceFactory  Internal storage interface
     */
    public CourseRegFactoryImpl(IDataModelInstanceFactory edma_instanceFactory)
    {
        this.edma_instanceFactory = edma_instanceFactory;
    }

    /**
     * Test the existence of an instance
     * @param name  The name of the instance to test the existence of
     * @return      
     */
    public boolean exists(String name) throws IOException
    {
        return edma_instanceFactory.exists(name);
    }

    /**
     * Create a new instance
     * @param edma_name   The name of the instance to create
     * @param schoolInfo  The initial values for the singleton SchoolInfo
     * @return            
     */
    public CourseRegInstance newInstance(String edma_name, SchoolInfo schoolInfo) throws IOException
    {
        Object[] edma_initValues = new Object[1];
        edma_initValues[0] = ((IValueInstance) schoolInfo.name()).edma_getValue();
        return new CourseRegInstImpl(edma_instanceFactory.newModelInstance(edma_name, edma_initValues));
    }

    /**
     * Get an instance from its name
     * @param name  The name of the instance to get
     * @return      
     */
    public CourseRegInstance getInstance(String name) throws IOException
    {
        return new CourseRegInstImpl(edma_instanceFactory.getModelInstance(name));
    }

    /**
     * Delete an instance
     * @param name  The name of the instance to delete
     * @return      <tt>true</tt> if the instance was deleted
     */
    public boolean deleteInstance(String name) throws IOException
    {
        return edma_instanceFactory.deleteModelInstance(name);
    }
}
