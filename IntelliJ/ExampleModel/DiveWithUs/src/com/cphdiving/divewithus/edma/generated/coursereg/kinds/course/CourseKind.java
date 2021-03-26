package com.cphdiving.divewithus.edma.generated.coursereg.kinds.course;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.Date;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;

/**
 * Interface for the kind: Course
 */
public interface CourseKind
{

    /**
     * Get an entity from its id.
     * @param id  The id of the entity to get
     * @return    A viewer on the entity with the given ID or null if none
     *            exists.
     */
    public CourseViewer getFromID(CourseID id);

    /**
     * Returns an empty set of Course entities.
     * @return  An empty set of Course entities.
     */
    public CourseSet getEmptyCourseSet();

    /**
     * Returns the set of all Course entities.
     * @return  The set of all Course entities.
     */
    public CourseSet getAll();

    /**
     * Returns a CourseSet where startDate equals the provided startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate equals the provided
     *                   startDate.
     */
    public CourseSet getWhereStartDateEquals(Date startDate);

    /**
     * Returns a CourseSet where startDate is less than the provided startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate is less than the provided
     *                   startDate.
     */
    public CourseSet getWhereStartDateLessThan(Date startDate);

    /**
     * Returns a CourseSet where startDate is less than or equal to the
     * provided startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate is less than or equal to
     *                   the provided startDate.
     */
    public CourseSet getWhereStartDateLessThanOrEqual(Date startDate);

    /**
     * Returns a CourseSet where startDate is greater than the provided
     * startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate is greater than the
     *                   provided startDate.
     */
    public CourseSet getWhereStartDateGreaterThan(Date startDate);

    /**
     * Returns a CourseSet where startDate is greater than or equal to the
     * provided startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate is greater than or equal
     *                   to the provided startDate.
     */
    public CourseSet getWhereStartDateGreaterThanOrEqual(Date startDate);

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
    public CourseSet getWhereStartDateInRange(Date minStartDate, boolean minInclusive, Date maxStartDate, boolean maxInclusive);

    /**
     * Returns a CourseSet where status equals the provided status.
     * @param status  Value for status
     * @return        A CourseSet where status equals the provided status.
     */
    public CourseSet getWhereStatusEquals(CourseStatus status);

}
