package com.cphdiving.divewithus.edma.generated.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegInstance;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.SchoolInfo;
import java.io.IOException;

/**
 * Instance factory for CourseReg
 */
public interface CourseRegFactory
{

    /**
     * Test the existence of an instance
     * @param name  The name of the instance to test the existence of
     * @return      
     */
    public boolean exists(String name) throws IOException;

    /**
     * Create a new instance
     * @param edma_name   The name of the instance to create
     * @param schoolInfo  The initial values for the singleton SchoolInfo
     * @return            
     */
    public CourseRegInstance newInstance(String edma_name, SchoolInfo schoolInfo) throws IOException;

    /**
     * Get an instance from its name
     * @param name  The name of the instance to get
     * @return      
     */
    public CourseRegInstance getInstance(String name) throws IOException;

    /**
     * Delete an instance
     * @param name  The name of the instance to delete
     * @return      <tt>true</tt> if the instance was deleted
     */
    public boolean deleteInstance(String name) throws IOException;

}
