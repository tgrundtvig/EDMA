package com.cphdiving.divewithus.edma.generated.coursereg.kinds.course;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.Date;
import com.cphdiving.divewithus.edma.generated.valuedomains.Time;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Course;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;

/**
 * This is the viewing interface for Course
 */
public interface CourseViewer
{

    /**
     * Create a copy of this Course at this instance in time
     * @return  A copy of this Course entity as a value  from the value domain
     *          Course
     */
    public Course snapshot();

    /**
     * Returns the ID of this Course
     * @return  The ID of this Course
     */
    public CourseID getID();

    /**
     * Returns the attribute startDate of this Course
     * @return  The value of the attribute startDate
     */
    public Date getStartDate();

    /**
     * Returns the attribute startTime of this Course
     * @return  The value of the attribute startTime
     */
    public Time getStartTime();

    /**
     * Returns the attribute status of this Course
     * @return  The value of the attribute status
     */
    public CourseStatus getStatus();

    /**
     * This methods follows the relation CourseTypes
     * @return  The result of following the relation CourseTypes
     */
    public CourseTypeViewer getCourseType();

    /**
     * This methods follows the relation StudentEnrollment
     * @return  The result of following the relation StudentEnrollment
     */
    public StudentSet getStudentSet();

    /**
     * This methods follows the relation TeacherEnrollment
     * @return  The result of following the relation TeacherEnrollment
     */
    public TeacherViewer getTeacher();

    /**
     * Returns <tt>true</tt> if this entity has the same ID as the provided
     * entity
     * @param o  The object to compare with
     * @return   <tt>true</tt> if this entity has the same ID as the provided
     *           entity
     */
    public boolean equals(Object o);

    /**
     * Returns the hash code of this entity
     * @return  The hash code of this entity
     */
    public int hashCode();

}
