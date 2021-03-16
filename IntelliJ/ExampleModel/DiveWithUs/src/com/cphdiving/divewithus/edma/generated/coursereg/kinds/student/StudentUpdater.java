package com.cphdiving.divewithus.edma.generated.coursereg.kinds.student;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;

/**
 * This is the update interface for Student
 */
public interface StudentUpdater extends StudentViewer
{

    /**
     * Connects the passedCourse to this Student, if it is not already
     * connected.
     *  This method has been generated from the relation:
     *  PassedCourses
     * @param passedCourse  The passedCourse to be added. May NOT be null.
     * @return              <tt>true</tt> if this student was not already
     *                      connected to the specified passedCourse
     */
    public boolean addPassedCourse(CourseTypeViewer passedCourse);

    /**
     * Removes the passedCourse from this Student, if it is connected.
     *  This method has been generated from the relation:
     *  PassedCourses
     * @param passedCourse  The passedCourse to be connected to this student.
     *                      May NOT be null.
     * @return              <tt>true</tt> if the specified passedCourse was
     *                      connected to this student
     */
    public boolean removePassedCourse(CourseTypeViewer passedCourse);

}
