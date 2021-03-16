package com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;

/**
 * This is the update interface for CourseType
 */
public interface CourseTypeUpdater extends CourseTypeViewer
{

    /**
     * Connects the required to this CourseType, if it is not already connected.
     *  This method has been generated from the relation:
     *  CourseDependency
     * @param required  The required to be added. May NOT be null.
     * @return          <tt>true</tt> if this courseType was not already
     *                  connected to the specified required
     */
    public boolean addRequired(CourseTypeViewer required);

    /**
     * Removes the required from this CourseType, if it is connected.
     *  This method has been generated from the relation:
     *  CourseDependency
     * @param required  The required to be connected to this courseType. May
     *                  NOT be null.
     * @return          <tt>true</tt> if the specified required was connected
     *                  to this courseType
     */
    public boolean removeRequired(CourseTypeViewer required);

}
