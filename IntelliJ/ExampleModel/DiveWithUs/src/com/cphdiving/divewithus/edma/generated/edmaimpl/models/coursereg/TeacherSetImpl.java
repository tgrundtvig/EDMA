package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherFilter;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherViewer;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseTypeSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.PersonSetImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.TeacherListImpl;
import java.util.ArrayList;
import java.util.Iterator;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IEntity;

/**
 * 
 */
public class TeacherSetImpl implements TeacherSet
{
    private int setID;
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param setID        The id of this set
     * @param edma_dmview  The internal runtime interface
     */
    public TeacherSetImpl(int setID, IDataModelView edma_dmview)
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
    public boolean contains(TeacherID id)
    {
        return edma_dmview.setContains(setID, id.value());
    }

    /**
     * Returns <tt>true</tt> if this set contains the given entity.
     * @param entity  The entity to test containment of
     * @return        <tt>true</tt> if this set contains the given entity
     */
    public boolean contains(TeacherViewer entity)
    {
        return edma_dmview.setContains(setID, entity.getID().value());
    }

    /**
     * Returns <tt>true</tt> if this set contains every entity in the given set.
     * @param teacherSet  The TeacherSet to test containment of.
     * @return            <tt>true</tt> if this set contains every entity in
     *                    the given set
     */
    public boolean containsAll(TeacherSet teacherSet)
    {
        return edma_dmview.setContainsAll(setID, ((TeacherSetImpl) teacherSet).setID);
    }

    /**
     * Creates a copy of this TeacherSet at this instance in time and returns
     * it as a list of values
     * @return  A copy of this TeacherSet as a list of values from the value
     *          domain TeacherList
     */
    public TeacherList snapshot()
    {
        int size = edma_dmview.setGetSize(setID);
        Iterator<IEntity> it = edma_dmview.setGetIterator(setID);
        Object[] res = new Object[size];
        int i = 0;
        while(it.hasNext())
        {
            Object[] entObj = new Object[2];
            Object[] value = it.next().getValue();
            entObj[0] = value[0];
            entObj[1] = value[1];
            res[i++] = entObj;
        }
        return new TeacherListImpl(res);
    }

    /**
     * Returns the union between this set and the given set
     * @param teacherSet  The TeacherSet to create the union with.
     * @return            The union between this TeacherSet and the provided
     *                    TeacherSet.
     */
    public TeacherSet union(TeacherSet teacherSet)
    {
        int otherSetID = ((TeacherSetImpl) teacherSet).setID;
        int newSetID = edma_dmview.setUnion(setID, otherSetID);
        return new TeacherSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns the intersection between this set and the given set
     * @param teacherSet  The TeacherSet to create the intersection with.
     * @return            The intersection between this TeacherSet and the
     *                    provided TeacherSet.
     */
    public TeacherSet intersect(TeacherSet teacherSet)
    {
        int otherSetID = ((TeacherSetImpl) teacherSet).setID;
        int newSetID = edma_dmview.setIntersect(setID, otherSetID);
        return new TeacherSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns the subtraction of the given set from this set. The result set
     * will contain every element from this set, that are not contained in the
     * given set.
     * @param teacherSet  The TeacherSet to subtract from this set.
     * @return            The subtraction of the provided TeacherSet from this
     *                    TeacherSet. The result will contain every element from
     *                    this set, that are not contained in the provided set.
     */
    public TeacherSet subtract(TeacherSet teacherSet)
    {
        int otherSetID = ((TeacherSetImpl) teacherSet).setID;
        int newSetID = edma_dmview.setSubtract(setID, otherSetID);
        return new TeacherSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with all the entities from this set that are accepted
     * by the filter.
     * @param filter  The filter
     * @return        A new set with all the entities from this set that are
     *                accepted by the filter.
     */
    public TeacherSet filter(TeacherFilter filter)
    {
        ArrayList<Long> res = new ArrayList<Long>();
        for(TeacherViewer teacher : this)
        {
            if(filter.accept(teacher))
            {
                res.add(teacher.getID().value());
            }
        }
        int newSetID = edma_dmview.setFromIDs(1, res);
        return new com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.TeacherSetImpl(newSetID, edma_dmview);
    }

    /**
     * Views the Teacher entries in this set as their base kind Person. The
     * resulting set will always be of the same size as this set, since Teacher
     * is always an extension of Person
     * @return  The PersonSet view of this TeacherSet
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
    public TeacherSet orderByID()
    {
        int newSetID = edma_dmview.setOrderByID(setID, false);
        return new TeacherSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by ID.
     * @return  A new set with the same entries but ordered by ID.
     */
    public TeacherSet orderByIDDesc()
    {
        int newSetID = edma_dmview.setOrderByID(setID, true);
        return new TeacherSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but sub ordered by ID.
     * @return  A new set with the same entries but sub ordered by ID.
     */
    public TeacherSet subOrderByID()
    {
        int newSetID = edma_dmview.setSubOrderByID(setID, false);
        return new TeacherSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but sub ordered by ID.
     * @return  A new set with the same entries but sub ordered by ID.
     */
    public TeacherSet subOrderByIDDesc()
    {
        int newSetID = edma_dmview.setSubOrderByID(setID, true);
        return new TeacherSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by salary
     * @return  A new set with the same entries but ordered by salary
     */
    public TeacherSet orderBySalary()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 0, false);
        return new TeacherSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by salary
     * @return  A new set with the same entries but ordered by salary
     */
    public TeacherSet orderBySalaryDesc()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 0, true);
        return new TeacherSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by salary
     * @return  A new set with the same entries that is sub ordered by salary
     */
    public TeacherSet subOrderBySalary()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 0, false);
        return new TeacherSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by salary
     * @return  A new set with the same entries that is sub ordered by salary
     */
    public TeacherSet subOrderBySalaryDesc()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 0, true);
        return new TeacherSetImpl(newSetID, edma_dmview);
    }

    /**
     * This methods follows the relation TeachingAbility
     * @return  The result of following the relation TeachingAbility
     */
    public CourseTypeSet asTeacherSetGetCourseTypeSet()
    {
        int newSetID = edma_dmview.relationAsASetGetBSet(4, setID);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * This methods follows the relation TeacherEnrollment
     * @return  The result of following the relation TeacherEnrollment
     */
    public CourseSet asTeacherSetGetCourseSet()
    {
        int newSetID = edma_dmview.relationAsBSetGetASet(5, setID);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns an iterator for this set
     * @return  An iterator for this set
     */
    public Iterator<TeacherViewer> iterator()
    {
        return new TeacherIterator(edma_dmview.setGetIterator(setID));
    }


    /**
     * Inner class that defines the iterator
     */
    public class TeacherIterator implements Iterator<TeacherViewer>
    {
        private Iterator<IEntity> edma_iterator;



        /**
         * Constructor
         * @param edma_iterator  The internal iterator
         */
        public TeacherIterator(Iterator<IEntity> edma_iterator)
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
        public TeacherViewer next()
        {
            IEntity entity = edma_iterator.next();
            return new TeacherVUImpl(entity, edma_dmview);
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
