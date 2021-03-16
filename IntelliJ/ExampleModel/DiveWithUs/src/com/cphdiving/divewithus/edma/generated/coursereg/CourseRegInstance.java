package com.cphdiving.divewithus.edma.generated.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseReg;

/**
 * This is the instance interface to CourseReg instances. It can be used to
 * start and stop the instance and the API interface can be obtained
 */
public interface CourseRegInstance
{

    /**
     * Used to start the instance, which enables the API
     */
    public void start();

    /**
     * Used to stop the instance, which disables the API
     */
    public void stop();

    /**
     * Used to test if the instance is running
     * @return  <tt>true</tt> if the instance is running otherwise
     *          <tt>false</tt>
     */
    public boolean isRunning();

    /**
     * Get access to the API for the CourseReg instance
     * @return  The API interface for the CourseReg instance
     */
    public CourseReg getAPI();

}
