package com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherSet;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeList;
import java.util.Iterator;

/**
 * This is the viewing interface for CourseTypeSet
 */
public interface CourseTypeSet extends Iterable<CourseTypeViewer>
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
    public boolean contains(CourseTypeID id);

    /**
     * Returns <tt>true</tt> if this set contains the given entity.
     * @param entity  The entity to test containment of
     * @return        <tt>true</tt> if this set contains the given entity
     */
    public boolean contains(CourseTypeViewer entity);

    /**
     * Returns <tt>true</tt> if this set contains every entity in the given set.
     * @param courseTypeSet  The CourseTypeSet to test containment of.
     * @return               <tt>true</tt> if this set contains every entity in
     *                       the given set
     */
    public boolean containsAll(CourseTypeSet courseTypeSet);

    /**
     * Creates a copy of this CourseTypeSet at this instance in time and
     * returns it as a list of values
     * @return  A copy of this CourseTypeSet as a list of values from the value
     *          domain CourseTypeList
     */
    public CourseTypeList snapshot();

    /**
     * Returns the union between this set and the given set
     * @param courseTypeSet  The CourseTypeSet to create the union with.
     * @return               The union between this CourseTypeSet and the
     *                       provided CourseTypeSet.
     */
    public CourseTypeSet union(CourseTypeSet courseTypeSet);

    /**
     * Returns the intersection between this set and the given set
     * @param courseTypeSet  The CourseTypeSet to create the intersection with.
     * @return               The intersection between this CourseTypeSet and
     *                       the provided CourseTypeSet.
     */
    public CourseTypeSet intersect(CourseTypeSet courseTypeSet);

    /**
     * Returns the subtraction of the given set from this set. The result set
     * will contain every element from this set, that are not contained in the
     * given set.
     * @param courseTypeSet  The CourseTypeSet to subtract from this set.
     * @return               The subtraction of the provided CourseTypeSet from
     *                       this CourseTypeSet. The result will contain every
     *                       element from this set, that are not contained in
     *                       the provided set.
     */
    public CourseTypeSet subtract(CourseTypeSet courseTypeSet);

    /**
     * Returns a new set with all the entities from this set that are accepted
     * by the filter.
     * @param filter  The filter
     * @return        A new set with all the entities from this set that are
     *                accepted by the filter.
     */
    public CourseTypeSet filter(CourseTypeFilter filter);

    /**
     * Returns a new set with the same entries but ordered by ID.
     * @return  A new set with the same entries but ordered by ID.
     */
    public CourseTypeSet orderByID();

    /**
     * Returns a new set with the same entries but ordered by ID.
     * @return  A new set with the same entries but ordered by ID.
     */
    public CourseTypeSet orderByIDDesc();

    /**
     * Returns a new set with the same entries but sub ordered by ID.
     * @return  A new set with the same entries but sub ordered by ID.
     */
    public CourseTypeSet subOrderByID();

    /**
     * Returns a new set with the same entries but sub ordered by ID.
     * @return  A new set with the same entries but sub ordered by ID.
     */
    public CourseTypeSet subOrderByIDDesc();

    /**
     * Returns a new set with the same entries but ordered by name
     * @return  A new set with the same entries but ordered by name
     */
    public CourseTypeSet orderByName();

    /**
     * Returns a new set with the same entries but ordered by name
     * @return  A new set with the same entries but ordered by name
     */
    public CourseTypeSet orderByNameDesc();

    /**
     * Returns a new set with the same entries that is sub ordered by name
     * @return  A new set with the same entries that is sub ordered by name
     */
    public CourseTypeSet subOrderByName();

    /**
     * Returns a new set with the same entries that is sub ordered by name
     * @return  A new set with the same entries that is sub ordered by name
     */
    public CourseTypeSet subOrderByNameDesc();

    /**
     * Returns a new set with the same entries but ordered by size
     * @return  A new set with the same entries but ordered by size
     */
    public CourseTypeSet orderBySize();

    /**
     * Returns a new set with the same entries but ordered by size
     * @return  A new set with the same entries but ordered by size
     */
    public CourseTypeSet orderBySizeDesc();

    /**
     * Returns a new set with the same entries that is sub ordered by size
     * @return  A new set with the same entries that is sub ordered by size
     */
    public CourseTypeSet subOrderBySize();

    /**
     * Returns a new set with the same entries that is sub ordered by size
     * @return  A new set with the same entries that is sub ordered by size
     */
    public CourseTypeSet subOrderBySizeDesc();

    /**
     * Returns a new set with the same entries but ordered by price
     * @return  A new set with the same entries but ordered by price
     */
    public CourseTypeSet orderByPrice();

    /**
     * Returns a new set with the same entries but ordered by price
     * @return  A new set with the same entries but ordered by price
     */
    public CourseTypeSet orderByPriceDesc();

    /**
     * Returns a new set with the same entries that is sub ordered by price
     * @return  A new set with the same entries that is sub ordered by price
     */
    public CourseTypeSet subOrderByPrice();

    /**
     * Returns a new set with the same entries that is sub ordered by price
     * @return  A new set with the same entries that is sub ordered by price
     */
    public CourseTypeSet subOrderByPriceDesc();

    /**
     * This methods follows the relation CourseDependency
     * @return  The result of following the relation CourseDependency
     */
    public CourseTypeSet asCourseTypeSetGetRequiredSet();

    /**
     * This methods follows the relation CourseDependency
     * @return  The result of following the relation CourseDependency
     */
    public CourseTypeSet asRequiredSetGetCourseTypeSet();

    /**
     * This methods follows the relation CourseTypes
     * @return  The result of following the relation CourseTypes
     */
    public CourseSet asCourseTypeSetGetCourseSet();

    /**
     * This methods follows the relation PassedCourses
     * @return  The result of following the relation PassedCourses
     */
    public StudentSet asPassedCourseSetGetStudentSet();

    /**
     * This methods follows the relation TeachingAbility
     * @return  The result of following the relation TeachingAbility
     */
    public TeacherSet asCourseTypeSetGetTeacherSet();

    /**
     * Returns an iterator for this set
     * @return  An iterator for this set
     */
    public Iterator<CourseTypeViewer> iterator();

}
