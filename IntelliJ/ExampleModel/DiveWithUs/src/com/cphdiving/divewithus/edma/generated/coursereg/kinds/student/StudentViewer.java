package com.cphdiving.divewithus.edma.generated.coursereg.kinds.student;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.BootSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Student;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.WetsuitSize;

/**
 * This is the viewing interface for Student
 */
public interface StudentViewer
{

    /**
     * Create a copy of this Student at this instance in time
     * @return  A copy of this Student entity as a value  from the value domain
     *          Student
     */
    public Student snapshot();

    /**
     * Returns the ID of this Student
     * @return  The ID of this Student
     */
    public StudentID getID();

    /**
     * Returns the attribute wetsuitSize of this Student
     * @return  The value of the attribute wetsuitSize
     */
    public WetsuitSize getWetsuitSize();

    /**
     * Returns the attribute bootSize of this Student
     * @return  The value of the attribute bootSize
     */
    public BootSize getBootSize();

    /**
     * Views this Student as its base kind Person. Will never return
     * <tt>null</tt> since Student is always an extension of Person
     * @return  The Person view of this Student
     */
    public PersonViewer asPerson();

    /**
     * This methods follows the relation PassedCourses
     * @return  The result of following the relation PassedCourses
     */
    public CourseTypeSet getPassedCourseSet();

    /**
     * This methods follows the relation StudentEnrollment
     * @return  The result of following the relation StudentEnrollment
     */
    public CourseSet getCourseSet();

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
