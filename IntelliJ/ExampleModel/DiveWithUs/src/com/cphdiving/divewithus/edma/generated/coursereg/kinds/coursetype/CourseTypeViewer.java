package com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherSet;
import com.cphdiving.divewithus.edma.generated.valuedomains.Date;
import com.cphdiving.divewithus.edma.generated.valuedomains.PosInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseName;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseType;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;

/**
 * This is the viewing interface for CourseType
 */
public interface CourseTypeViewer
{

    /**
     * Create a copy of this CourseType at this instance in time
     * @return  A copy of this CourseType entity as a value  from the value
     *          domain CourseType
     */
    public CourseType snapshot();

    /**
     * Returns the ID of this CourseType
     * @return  The ID of this CourseType
     */
    public CourseTypeID getID();

    /**
     * Returns the attribute name of this CourseType
     * @return  The value of the attribute name
     */
    public CourseName getName();

    /**
     * Returns the attribute size of this CourseType
     * @return  The value of the attribute size
     */
    public CourseSize getSize();

    /**
     * Returns the attribute price of this CourseType
     * @return  The value of the attribute price
     */
    public PosInt getPrice();

    /**
     * This methods follows the relation CourseDependency
     * @return  The result of following the relation CourseDependency
     */
    public CourseTypeSet getRequiredSet();

    /**
     * This methods follows the relation CourseDependency
     * @return  The result of following the relation CourseDependency
     */
    public CourseTypeSet asRequiredGetCourseTypeSet();

    /**
     * This methods follows the relation CourseTypes
     * @return  The result of following the relation CourseTypes
     */
    public CourseSet getCourseSet();

    /**
     * Returns a CourseSet where startDate equals the provided startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate equals the provided
     *                   startDate.
     */
    public CourseSet getCourseSetWhereStartDateEquals(Date startDate);

    /**
     * Returns a CourseSet where startDate is less than the provided startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate is less than the provided
     *                   startDate.
     */
    public CourseSet getCourseSetWhereStartDateLessThan(Date startDate);

    /**
     * Returns a CourseSet where startDate is less than or equal to the
     * provided startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate is less than or equal to
     *                   the provided startDate.
     */
    public CourseSet getCourseSetWhereStartDateLessThanOrEqual(Date startDate);

    /**
     * Returns a CourseSet where startDate is greater than the provided
     * startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate is greater than the
     *                   provided startDate.
     */
    public CourseSet getCourseSetWhereStartDateGreaterThan(Date startDate);

    /**
     * Returns a CourseSet where startDate is greater than or equal to the
     * provided startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate is greater than or equal
     *                   to the provided startDate.
     */
    public CourseSet getCourseSetWhereStartDateGreaterThanOrEqual(Date startDate);

    /**
     * Returns the subset of this CourseSet where startDate is in the given
     * range.
     * @param minStartDate  Minimum value for startDate
     * @param minInclusive  <tt>true</tt> if the minimum value should be
     *                      included.
     * @param maxStartDate  Maximum value for startDate
     * @param maxInclusive  <tt>true</tt> if the maximum value should be
     *                      included.
     * @return              The subset of this CourseSet where startDate is in
     *                      the given range.
     */
    public CourseSet getCourseSetWhereStartDateInRange(Date minStartDate, boolean minInclusive, Date maxStartDate, boolean maxInclusive);

    /**
     * Returns a CourseSet where status and startDate equals the provided
     * status and startDate.
     * @param status     Value for status
     * @param startDate  Value for startDate
     * @return           A CourseSet where status and startDate equals the
     *                   provided status and startDate.
     */
    public CourseSet getCourseSetWhereStatusAndStartDateEquals(CourseStatus status, Date startDate);

    /**
     * Returns a CourseSet where status and startDate is less than the provided
     * status and startDate.
     * @param status     Value for status
     * @param startDate  Value for startDate
     * @return           A CourseSet where status and startDate is less than
     *                   the provided status and startDate.
     */
    public CourseSet getCourseSetWhereStatusAndStartDateLessThan(CourseStatus status, Date startDate);

    /**
     * Returns a CourseSet where status and startDate is less than or equal to
     * the provided status and startDate.
     * @param status     Value for status
     * @param startDate  Value for startDate
     * @return           A CourseSet where status and startDate is less than or
     *                   equal to the provided status and startDate.
     */
    public CourseSet getCourseSetWhereStatusAndStartDateLessThanOrEqual(CourseStatus status, Date startDate);

    /**
     * Returns a CourseSet where status and startDate is greater than the
     * provided status and startDate.
     * @param status     Value for status
     * @param startDate  Value for startDate
     * @return           A CourseSet where status and startDate is greater than
     *                   the provided status and startDate.
     */
    public CourseSet getCourseSetWhereStatusAndStartDateGreaterThan(CourseStatus status, Date startDate);

    /**
     * Returns a CourseSet where status and startDate is greater than or equal
     * to the provided status and startDate.
     * @param status     Value for status
     * @param startDate  Value for startDate
     * @return           A CourseSet where status and startDate is greater than
     *                   or equal to the provided status and startDate.
     */
    public CourseSet getCourseSetWhereStatusAndStartDateGreaterThanOrEqual(CourseStatus status, Date startDate);

    /**
     * Returns the subset of this CourseSet where status and startDate are in
     * the given range.
     * @param minStatus     Minimum value for status
     * @param minStartDate  Minimum value for startDate
     * @param minInclusive  <tt>true</tt> if the minimum value should be
     *                      included.
     * @param maxStatus     Maximum value for status
     * @param maxStartDate  Maximum value for startDate
     * @param maxInclusive  <tt>true</tt> if the maximum value should be
     *                      included.
     * @return              The subset of this CourseSet where status and
     *                      startDate are in the given range.
     */
    public CourseSet getCourseSetWhereStatusAndStartDateInRange(CourseStatus minStatus, Date minStartDate, boolean minInclusive, CourseStatus maxStatus, Date maxStartDate, boolean maxInclusive);

    /**
     * This methods follows the relation PassedCourses
     * @return  The result of following the relation PassedCourses
     */
    public StudentSet asPassedCourseGetStudentSet();

    /**
     * This methods follows the relation TeachingAbility
     * @return  The result of following the relation TeachingAbility
     */
    public TeacherSet getTeacherSet();

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
