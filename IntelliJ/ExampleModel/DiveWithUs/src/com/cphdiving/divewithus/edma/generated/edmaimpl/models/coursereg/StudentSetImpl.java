package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentFilter;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentViewer;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseTypeSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.PersonSetImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.StudentListImpl;
import java.util.ArrayList;
import java.util.Iterator;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IEntity;

/**
 * 
 */
public class StudentSetImpl implements StudentSet
{
    private int setID;
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param setID        The id of this set
     * @param edma_dmview  The internal runtime interface
     */
    public StudentSetImpl(int setID, IDataModelView edma_dmview)
    {
        this.setID = setID;
        this.edma_dmview = edma_dmview;
    }

    /**
     * Get the size of this set.
     * @return  The size of this set.
     */
    public int size()
    {
        return edma_dmview.setGetSize(setID);
    }

    /**
     * Returns <tt>true</tt> if this set contains the entity with the given id.
     * @param id  The id of the entity to test containment of
     * @return    <tt>true</tt> if this set contains the entity with the given
     *            id
     */
    public boolean contains(StudentID id)
    {
        return edma_dmview.setContains(setID, id.value());
    }

    /**
     * Returns <tt>true</tt> if this set contains the given entity.
     * @param entity  The entity to test containment of
     * @return        <tt>true</tt> if this set contains the given entity
     */
    public boolean contains(StudentViewer entity)
    {
        return edma_dmview.setContains(setID, entity.getID().value());
    }

    /**
     * Returns <tt>true</tt> if this set contains every entity in the given set.
     * @param studentSet  The StudentSet to test containment of.
     * @return            <tt>true</tt> if this set contains every entity in
     *                    the given set
     */
    public boolean containsAll(StudentSet studentSet)
    {
        return edma_dmview.setContainsAll(setID, ((StudentSetImpl) studentSet).setID);
    }

    /**
     * Creates a copy of this StudentSet at this instance in time and returns
     * it as a list of values
     * @return  A copy of this StudentSet as a list of values from the value
     *          domain StudentList
     */
    public StudentList snapshot()
    {
        int size = edma_dmview.setGetSize(setID);
        Iterator<IEntity> it = edma_dmview.setGetIterator(setID);
        Object[] res = new Object[size];
        int i = 0;
        while(it.hasNext())
        {
            Object[] entObj = new Object[3];
            Object[] value = it.next().getValue();
            entObj[0] = value[0];
            entObj[1] = value[1];
            entObj[2] = value[2];
            res[i++] = entObj;
        }
        return new StudentListImpl(res);
    }

    /**
     * Returns the union between this set and the given set
     * @param studentSet  The StudentSet to create the union with.
     * @return            The union between this StudentSet and the provided
     *                    StudentSet.
     */
    public StudentSet union(StudentSet studentSet)
    {
        int otherSetID = ((StudentSetImpl) studentSet).setID;
        int newSetID = edma_dmview.setUnion(setID, otherSetID);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns the intersection between this set and the given set
     * @param studentSet  The StudentSet to create the intersection with.
     * @return            The intersection between this StudentSet and the
     *                    provided StudentSet.
     */
    public StudentSet intersect(StudentSet studentSet)
    {
        int otherSetID = ((StudentSetImpl) studentSet).setID;
        int newSetID = edma_dmview.setIntersect(setID, otherSetID);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns the subtraction of the given set from this set. The result set
     * will contain every element from this set, that are not contained in the
     * given set.
     * @param studentSet  The StudentSet to subtract from this set.
     * @return            The subtraction of the provided StudentSet from this
     *                    StudentSet. The result will contain every element from
     *                    this set, that are not contained in the provided set.
     */
    public StudentSet subtract(StudentSet studentSet)
    {
        int otherSetID = ((StudentSetImpl) studentSet).setID;
        int newSetID = edma_dmview.setSubtract(setID, otherSetID);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with all the entities from this set that are accepted
     * by the filter.
     * @param filter  The filter
     * @return        A new set with all the entities from this set that are
     *                accepted by the filter.
     */
    public StudentSet filter(StudentFilter filter)
    {
        ArrayList<Long> res = new ArrayList<Long>();
        for(StudentViewer student : this)
        {
            if(filter.accept(student))
            {
                res.add(student.getID().value());
            }
        }
        int newSetID = edma_dmview.setFromIDs(2, res);
        return new com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Views the Student entries in this set as their base kind Person. The
     * resulting set will always be of the same size as this set, since Student
     * is always an extension of Person
     * @return  The PersonSet view of this StudentSet
     */
    public PersonSet asPersonSet()
    {
        int newSetID = edma_dmview.setExtensionUp(setID, 0);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by ID.
     * @return  A new set with the same entries but ordered by ID.
     */
    public StudentSet orderByID()
    {
        int newSetID = edma_dmview.setOrderByID(setID, false);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by ID.
     * @return  A new set with the same entries but ordered by ID.
     */
    public StudentSet orderByIDDesc()
    {
        int newSetID = edma_dmview.setOrderByID(setID, true);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but sub ordered by ID.
     * @return  A new set with the same entries but sub ordered by ID.
     */
    public StudentSet subOrderByID()
    {
        int newSetID = edma_dmview.setSubOrderByID(setID, false);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but sub ordered by ID.
     * @return  A new set with the same entries but sub ordered by ID.
     */
    public StudentSet subOrderByIDDesc()
    {
        int newSetID = edma_dmview.setSubOrderByID(setID, true);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by wetsuitSize
     * @return  A new set with the same entries but ordered by wetsuitSize
     */
    public StudentSet orderByWetsuitSize()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 0, false);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by wetsuitSize
     * @return  A new set with the same entries but ordered by wetsuitSize
     */
    public StudentSet orderByWetsuitSizeDesc()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 0, true);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by
     * wetsuitSize
     * @return  A new set with the same entries that is sub ordered by
     *          wetsuitSize
     */
    public StudentSet subOrderByWetsuitSize()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 0, false);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by
     * wetsuitSize
     * @return  A new set with the same entries that is sub ordered by
     *          wetsuitSize
     */
    public StudentSet subOrderByWetsuitSizeDesc()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 0, true);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by bootSize
     * @return  A new set with the same entries but ordered by bootSize
     */
    public StudentSet orderByBootSize()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 1, false);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by bootSize
     * @return  A new set with the same entries but ordered by bootSize
     */
    public StudentSet orderByBootSizeDesc()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 1, true);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by bootSize
     * @return  A new set with the same entries that is sub ordered by bootSize
     */
    public StudentSet subOrderByBootSize()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 1, false);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by bootSize
     * @return  A new set with the same entries that is sub ordered by bootSize
     */
    public StudentSet subOrderByBootSizeDesc()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 1, true);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * This methods follows the relation PassedCourses
     * @return  The result of following the relation PassedCourses
     */
    public CourseTypeSet asStudentSetGetPassedCourseSet()
    {
        int newSetID = edma_dmview.relationAsASetGetBSet(2, setID);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * This methods follows the relation StudentEnrollment
     * @return  The result of following the relation StudentEnrollment
     */
    public CourseSet asStudentSetGetCourseSet()
    {
        int newSetID = edma_dmview.relationAsBSetGetASet(3, setID);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns an iterator for this set
     * @return  An iterator for this set
     */
    public Iterator<StudentViewer> iterator()
    {
        return new StudentIterator(edma_dmview.setGetIterator(setID));
    }


    /**
     * Inner class that defines the iterator
     */
    public class StudentIterator implements Iterator<StudentViewer>
    {
        private Iterator<IEntity> edma_iterator;



        /**
         * Constructor
         * @param edma_iterator  The internal iterator
         */
        public StudentIterator(Iterator<IEntity> edma_iterator)
        {
            this.edma_iterator = edma_iterator;
        }

        /**
         * Returns <tt>true</tt> if this iterator has more elements
         * @return  <tt>true</tt> if this iterator has more elements
         */
        public boolean hasNext()
        {
            return edma_iterator.hasNext();
        }

        /**
         * Returns the next element from this iterator
         * @return  the next element from this iterator
         */
        public StudentViewer next()
        {
            IEntity entity = edma_iterator.next();
            return new StudentVUImpl(entity, edma_dmview);
        }

        /**
         * Not supported as this is a read-only iterator
         */
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

}
