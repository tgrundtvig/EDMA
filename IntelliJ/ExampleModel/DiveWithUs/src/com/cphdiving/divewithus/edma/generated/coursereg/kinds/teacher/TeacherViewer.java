package com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.PosInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Teacher;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherID;

/**
 * This is the viewing interface for Teacher
 */
public interface TeacherViewer
{

    /**
     * Create a copy of this Teacher at this instance in time
     * @return  A copy of this Teacher entity as a value  from the value domain
     *          Teacher
     */
    public Teacher snapshot();

    /**
     * Returns the ID of this Teacher
     * @return  The ID of this Teacher
     */
    public TeacherID getID();

    /**
     * Returns the attribute salary of this Teacher
     * @return  The value of the attribute salary
     */
    public PosInt getSalary();

    /**
     * Views this Teacher as its base kind Person. Will never return
     * <tt>null</tt> since Teacher is always an extension of Person
     * @return  The Person view of this Teacher
     */
    public PersonViewer asPerson();

    /**
     * This methods follows the relation TeachingAbility
     * @return  The result of following the relation TeachingAbility
     */
    public CourseTypeSet getCourseTypeSet();

    /**
     * This methods follows the relation TeacherEnrollment
     * @return  The result of following the relation TeacherEnrollment
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
