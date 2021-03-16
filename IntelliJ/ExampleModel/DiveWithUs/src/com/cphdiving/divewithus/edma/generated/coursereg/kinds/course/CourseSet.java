package com.cphdiving.divewithus.edma.generated.coursereg.kinds.course;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherSet;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseList;
import java.util.Iterator;

/**
 * This is the viewing interface for CourseSet
 */
public interface CourseSet extends Iterable<CourseViewer>
{

    /**
     * Get the size of this set.
     * @return  The size of this set.
     */
    public int size();

    /**
     * Returns <tt>true</tt> if this set contains the entity with the given id.
     * @param id  The id of the entity to test containment of
     * @return    <tt>true</tt> if this set contains the entity with the given
     *            id
     */
    public boolean contains(CourseID id);

    /**
     * Returns <tt>true</tt> if this set contains the given entity.
     * @param entity  The entity to test containment of
     * @return        <tt>true</tt> if this set contains the given entity
     */
    public boolean contains(CourseViewer entity);

    /**
     * Returns <tt>true</tt> if this set contains every entity in the given set.
     * @param courseSet  The CourseSet to test containment of.
     * @return           <tt>true</tt> if this set contains every entity in the
     *                   given set
     */
    public boolean containsAll(CourseSet courseSet);

    /**
     * Creates a copy of this CourseSet at this instance in time and returns it
     * as a list of values
     * @return  A copy of this CourseSet as a list of values from the value
     *          domain CourseList
     */
    public CourseList snapshot();

    /**
     * Returns the union between this set and the given set
     * @param courseSet  The CourseSet to create the union with.
     * @return           The union between this CourseSet and the provided
     *                   CourseSet.
     */
    public CourseSet union(CourseSet courseSet);

    /**
     * Returns the intersection between this set and the given set
     * @param courseSet  The CourseSet to create the intersection with.
     * @return           The intersection between this CourseSet and the
     *                   provided CourseSet.
     */
    public CourseSet intersect(CourseSet courseSet);

    /**
     * Returns the subtraction of the given set from this set. The result set
     * will contain every element from this set, that are not contained in the
     * given set.
     * @param courseSet  The CourseSet to subtract from this set.
     * @return           The subtraction of the provided CourseSet from this
     *                   CourseSet. The result will contain every element from
     *                   this set, that are not contained in the provided set.
     */
    public CourseSet subtract(CourseSet courseSet);

    /**
     * Returns a new set with all the entities from this set that are accepted
     * by the filter.
     * @param filter  The filter
     * @return        A new set with all the entities from this set that are
     *                accepted by the filter.
     */
    public CourseSet filter(CourseFilter filter);

    /**
     * Returns a new set with the same entries but ordered by ID.
     * @return  A new set with the same entries but ordered by ID.
     */
    public CourseSet orderByID();

    /**
     * Returns a new set with the same entries but ordered by ID.
     * @return  A new set with the same entries but ordered by ID.
     */
    public CourseSet orderByIDDesc();

    /**
     * Returns a new set with the same entries but sub ordered by ID.
     * @return  A new set with the same entries but sub ordered by ID.
     */
    public CourseSet subOrderByID();

    /**
     * Returns a new set with the same entries but sub ordered by ID.
     * @return  A new set with the same entries but sub ordered by ID.
     */
    public CourseSet subOrderByIDDesc();

    /**
     * Returns a new set with the same entries but ordered by startDate
     * @return  A new set with the same entries but ordered by startDate
     */
    public CourseSet orderByStartDate();

    /**
     * Returns a new set with the same entries but ordered by startDate
     * @return  A new set with the same entries but ordered by startDate
     */
    public CourseSet orderByStartDateDesc();

    /**
     * Returns a new set with the same entries that is sub ordered by startDate
     * @return  A new set with the same entries that is sub ordered by startDate
     */
    public CourseSet subOrderByStartDate();

    /**
     * Returns a new set with the same entries that is sub ordered by startDate
     * @return  A new set with the same entries that is sub ordered by startDate
     */
    public CourseSet subOrderByStartDateDesc();

    /**
     * Returns a new set with the same entries but ordered by startTime
     * @return  A new set with the same entries but ordered by startTime
     */
    public CourseSet orderByStartTime();

    /**
     * Returns a new set with the same entries but ordered by startTime
     * @return  A new set with the same entries but ordered by startTime
     */
    public CourseSet orderByStartTimeDesc();

    /**
     * Returns a new set with the same entries that is sub ordered by startTime
     * @return  A new set with the same entries that is sub ordered by startTime
     */
    public CourseSet subOrderByStartTime();

    /**
     * Returns a new set with the same entries that is sub ordered by startTime
     * @return  A new set with the same entries that is sub ordered by startTime
     */
    public CourseSet subOrderByStartTimeDesc();

    /**
     * Returns a new set with the same entries but ordered by status
     * @return  A new set with the same entries but ordered by status
     */
    public CourseSet orderByStatus();

    /**
     * Returns a new set with the same entries but ordered by status
     * @return  A new set with the same entries but ordered by status
     */
    public CourseSet orderByStatusDesc();

    /**
     * Returns a new set with the same entries that is sub ordered by status
     * @return  A new set with the same entries that is sub ordered by status
     */
    public CourseSet subOrderByStatus();

    /**
     * Returns a new set with the same entries that is sub ordered by status
     * @return  A new set with the same entries that is sub ordered by status
     */
    public CourseSet subOrderByStatusDesc();

    /**
     * This methods follows the relation CourseTypes
     * @return  The result of following the relation CourseTypes
     */
    public CourseTypeSet asCourseSetGetCourseTypeSet();

    /**
     * This methods follows the relation StudentEnrollment
     * @return  The result of following the relation StudentEnrollment
     */
    public StudentSet asCourseSetGetStudentSet();

    /**
     * This methods follows the relation TeacherEnrollment
     * @return  The result of following the relation TeacherEnrollment
     */
    public TeacherSet asCourseSetGetTeacherSet();

    /**
     * Returns an iterator for this set
     * @return  An iterator for this set
     */
    public Iterator<CourseViewer> iterator();

}
