package com.cphdiving.divewithus.edma.generated.coursereg.kinds.course;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;

/**
 * This is the update interface for Course
 */
public interface CourseUpdater extends CourseViewer
{

    /**
     * Begin attribute updates on this Course
     * @return  An attribute update interface for chaining attribute updates
     */
    public CourseAttUpd beginUpdate();

    /**
     * Sets the courseType for this Course. If another courseType is already
     * set, it will be replaced.
     *  This method has been generated from the relation:
     *  CourseTypes
     * @param courseType  The courseType to be set. Use null to remove any
     *                    previous connection.
     * @return            The previous courseType connected to this course or
     *                    null if none was connected.
     */
    public CourseTypeViewer setCourseType(CourseTypeViewer courseType);

    /**
     * Connects the student to this Course, if it is not already connected.
     *  This method has been generated from the relation:
     *  StudentEnrollment
     * @param student  The student to be added. May NOT be null.
     * @return         <tt>true</tt> if this course was not already connected
     *                 to the specified student
     */
    public boolean addStudent(StudentViewer student);

    /**
     * Removes the student from this Course, if it is connected.
     *  This method has been generated from the relation:
     *  StudentEnrollment
     * @param student  The student to be connected to this course. May NOT be
     *                 null.
     * @return         <tt>true</tt> if the specified student was connected to
     *                 this course
     */
    public boolean removeStudent(StudentViewer student);

    /**
     * Sets the teacher for this Course. If another teacher is already set, it
     * will be replaced.
     *  This method has been generated from the relation:
     *  TeacherEnrollment
     * @param teacher  The teacher to be set. Use null to remove any previous
     *                 connection.
     * @return         The previous teacher connected to this course or null if
     *                 none was connected.
     */
    public TeacherViewer setTeacher(TeacherViewer teacher);

    /**
     * Interface for updating attributes on an entity of kind: Course
     */
    public interface CourseAttUpd
    {

        /**
         * Sets the attribute status on this Course
         * @param status  The value to set
         * @return        Interface for chaining attribute updates
         */
        public CourseAttUpd setStatus(CourseStatus status);

        /**
         * Saves the changes to the data model.
         * @return  
         */
        public boolean save();

    }

}
