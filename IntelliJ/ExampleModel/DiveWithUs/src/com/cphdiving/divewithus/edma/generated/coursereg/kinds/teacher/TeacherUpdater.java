package com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;

/**
 * This is the update interface for Teacher
 */
public interface TeacherUpdater extends TeacherViewer
{

    /**
     * Connects the courseType to this Teacher, if it is not already connected.
     *  This method has been generated from the relation:
     *  TeachingAbility
     * @param courseType  The courseType to be added. May NOT be null.
     * @return            <tt>true</tt> if this teacher was not already
     *                    connected to the specified courseType
     */
    public boolean addCourseType(CourseTypeViewer courseType);

    /**
     * Removes the courseType from this Teacher, if it is connected.
     *  This method has been generated from the relation:
     *  TeachingAbility
     * @param courseType  The courseType to be connected to this teacher. May
     *                    NOT be null.
     * @return            <tt>true</tt> if the specified courseType was
     *                    connected to this teacher
     */
    public boolean removeCourseType(CourseTypeViewer courseType);

}
