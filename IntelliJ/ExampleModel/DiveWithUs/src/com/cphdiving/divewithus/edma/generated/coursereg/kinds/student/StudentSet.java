package com.cphdiving.divewithus.edma.generated.coursereg.kinds.student;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentList;
import java.util.Iterator;

/**
 * This is the viewing interface for StudentSet
 */
public interface StudentSet extends Iterable<StudentViewer>
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
    public boolean contains(StudentID id);

    /**
     * Returns <tt>true</tt> if this set contains the given entity.
     * @param entity  The entity to test containment of
     * @return        <tt>true</tt> if this set contains the given entity
     */
    public boolean contains(StudentViewer entity);

    /**
     * Returns <tt>true</tt> if this set contains every entity in the given set.
     * @param studentSet  The StudentSet to test containment of.
     * @return            <tt>true</tt> if this set contains every entity in
     *                    the given set
     */
    public boolean containsAll(StudentSet studentSet);

    /**
     * Creates a copy of this StudentSet at this instance in time and returns
     * it as a list of values
     * @return  A copy of this StudentSet as a list of values from the value
     *          domain StudentList
     */
    public StudentList snapshot();

    /**
     * Returns the union between this set and the given set
     * @param studentSet  The StudentSet to create the union with.
     * @return            The union between this StudentSet and the provided
     *                    StudentSet.
     */
    public StudentSet union(StudentSet studentSet);

    /**
     * Returns the intersection between this set and the given set
     * @param studentSet  The StudentSet to create the intersection with.
     * @return            The intersection between this StudentSet and the
     *                    provided StudentSet.
     */
    public StudentSet intersect(StudentSet studentSet);

    /**
     * Returns the subtraction of the given set from this set. The result set
     * will contain every element from this set, that are not contained in the
     * given set.
     * @param studentSet  The StudentSet to subtract from this set.
     * @return            The subtraction of the provided StudentSet from this
     *                    StudentSet. The result will contain every element from
     *                    this set, that are not contained in the provided set.
     */
    public StudentSet subtract(StudentSet studentSet);

    /**
     * Returns a new set with all the entities from this set that are accepted
     * by the filter.
     * @param filter  The filter
     * @return        A new set with all the entities from this set that are
     *                accepted by the filter.
     */
    public StudentSet filter(StudentFilter filter);

    /**
     * Views the Student entries in this set as their base kind Person. The
     * resulting set will always be of the same size as this set, since Student
     * is always an extension of Person
     * @return  The PersonSet view of this StudentSet
     */
    public PersonSet asPersonSet();

    /**
     * Returns a new set with the same entries but ordered by ID.
     * @return  A new set with the same entries but ordered by ID.
     */
    public StudentSet orderByID();

    /**
     * Returns a new set with the same entries but ordered by ID.
     * @return  A new set with the same entries but ordered by ID.
     */
    public StudentSet orderByIDDesc();

    /**
     * Returns a new set with the same entries but sub ordered by ID.
     * @return  A new set with the same entries but sub ordered by ID.
     */
    public StudentSet subOrderByID();

    /**
     * Returns a new set with the same entries but sub ordered by ID.
     * @return  A new set with the same entries but sub ordered by ID.
     */
    public StudentSet subOrderByIDDesc();

    /**
     * Returns a new set with the same entries but ordered by wetsuitSize
     * @return  A new set with the same entries but ordered by wetsuitSize
     */
    public StudentSet orderByWetsuitSize();

    /**
     * Returns a new set with the same entries but ordered by wetsuitSize
     * @return  A new set with the same entries but ordered by wetsuitSize
     */
    public StudentSet orderByWetsuitSizeDesc();

    /**
     * Returns a new set with the same entries that is sub ordered by
     * wetsuitSize
     * @return  A new set with the same entries that is sub ordered by
     *          wetsuitSize
     */
    public StudentSet subOrderByWetsuitSize();

    /**
     * Returns a new set with the same entries that is sub ordered by
     * wetsuitSize
     * @return  A new set with the same entries that is sub ordered by
     *          wetsuitSize
     */
    public StudentSet subOrderByWetsuitSizeDesc();

    /**
     * Returns a new set with the same entries but ordered by bootSize
     * @return  A new set with the same entries but ordered by bootSize
     */
    public StudentSet orderByBootSize();

    /**
     * Returns a new set with the same entries but ordered by bootSize
     * @return  A new set with the same entries but ordered by bootSize
     */
    public StudentSet orderByBootSizeDesc();

    /**
     * Returns a new set with the same entries that is sub ordered by bootSize
     * @return  A new set with the same entries that is sub ordered by bootSize
     */
    public StudentSet subOrderByBootSize();

    /**
     * Returns a new set with the same entries that is sub ordered by bootSize
     * @return  A new set with the same entries that is sub ordered by bootSize
     */
    public StudentSet subOrderByBootSizeDesc();

    /**
     * This methods follows the relation PassedCourses
     * @return  The result of following the relation PassedCourses
     */
    public CourseTypeSet asStudentSetGetPassedCourseSet();

    /**
     * This methods follows the relation StudentEnrollment
     * @return  The result of following the relation StudentEnrollment
     */
    public CourseSet asStudentSetGetCourseSet();

    /**
     * Returns an iterator for this set
     * @return  An iterator for this set
     */
    public Iterator<StudentViewer> iterator();

}
