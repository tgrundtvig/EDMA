package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseFilter;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherSet;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseTypeSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.StudentSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.TeacherSetImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseListImpl;
import java.util.ArrayList;
import java.util.Iterator;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IEntity;

/**
 * 
 */
public class CourseSetImpl implements CourseSet
{
    private int setID;
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param setID        The id of this set
     * @param edma_dmview  The internal runtime interface
     */
    public CourseSetImpl(int setID, IDataModelView edma_dmview)
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
    public boolean contains(CourseID id)
    {
        return edma_dmview.setContains(setID, id.value());
    }

    /**
     * Returns <tt>true</tt> if this set contains the given entity.
     * @param entity  The entity to test containment of
     * @return        <tt>true</tt> if this set contains the given entity
     */
    public boolean contains(CourseViewer entity)
    {
        return edma_dmview.setContains(setID, entity.getID().value());
    }

    /**
     * Returns <tt>true</tt> if this set contains every entity in the given set.
     * @param courseSet  The CourseSet to test containment of.
     * @return           <tt>true</tt> if this set contains every entity in the
     *                   given set
     */
    public boolean containsAll(CourseSet courseSet)
    {
        return edma_dmview.setContainsAll(setID, ((CourseSetImpl) courseSet).setID);
    }

    /**
     * Creates a copy of this CourseSet at this instance in time and returns it
     * as a list of values
     * @return  A copy of this CourseSet as a list of values from the value
     *          domain CourseList
     */
    public CourseList snapshot()
    {
        int size = edma_dmview.setGetSize(setID);
        Iterator<IEntity> it = edma_dmview.setGetIterator(setID);
        Object[] res = new Object[size];
        int i = 0;
        while(it.hasNext())
        {
            Object[] entObj = new Object[4];
            Object[] value = it.next().getValue();
            entObj[0] = value[0];
            entObj[1] = value[1];
            entObj[2] = value[2];
            entObj[3] = value[3];
            res[i++] = entObj;
        }
        return new CourseListImpl(res);
    }

    /**
     * Returns the union between this set and the given set
     * @param courseSet  The CourseSet to create the union with.
     * @return           The union between this CourseSet and the provided
     *                   CourseSet.
     */
    public CourseSet union(CourseSet courseSet)
    {
        int otherSetID = ((CourseSetImpl) courseSet).setID;
        int newSetID = edma_dmview.setUnion(setID, otherSetID);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns the intersection between this set and the given set
     * @param courseSet  The CourseSet to create the intersection with.
     * @return           The intersection between this CourseSet and the
     *                   provided CourseSet.
     */
    public CourseSet intersect(CourseSet courseSet)
    {
        int otherSetID = ((CourseSetImpl) courseSet).setID;
        int newSetID = edma_dmview.setIntersect(setID, otherSetID);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns the subtraction of the given set from this set. The result set
     * will contain every element from this set, that are not contained in the
     * given set.
     * @param courseSet  The CourseSet to subtract from this set.
     * @return           The subtraction of the provided CourseSet from this
     *                   CourseSet. The result will contain every element from
     *                   this set, that are not contained in the provided set.
     */
    public CourseSet subtract(CourseSet courseSet)
    {
        int otherSetID = ((CourseSetImpl) courseSet).setID;
        int newSetID = edma_dmview.setSubtract(setID, otherSetID);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with all the entities from this set that are accepted
     * by the filter.
     * @param filter  The filter
     * @return        A new set with all the entities from this set that are
     *                accepted by the filter.
     */
    public CourseSet filter(CourseFilter filter)
    {
        ArrayList<Long> res = new ArrayList<Long>();
        for(CourseViewer course : this)
        {
            if(filter.accept(course))
            {
                res.add(course.getID().value());
            }
        }
        int newSetID = edma_dmview.setFromIDs(4, res);
        return new com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by ID.
     * @return  A new set with the same entries but ordered by ID.
     */
    public CourseSet orderByID()
    {
        int newSetID = edma_dmview.setOrderByID(setID, false);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by ID.
     * @return  A new set with the same entries but ordered by ID.
     */
    public CourseSet orderByIDDesc()
    {
        int newSetID = edma_dmview.setOrderByID(setID, true);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but sub ordered by ID.
     * @return  A new set with the same entries but sub ordered by ID.
     */
    public CourseSet subOrderByID()
    {
        int newSetID = edma_dmview.setSubOrderByID(setID, false);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but sub ordered by ID.
     * @return  A new set with the same entries but sub ordered by ID.
     */
    public CourseSet subOrderByIDDesc()
    {
        int newSetID = edma_dmview.setSubOrderByID(setID, true);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by startDate
     * @return  A new set with the same entries but ordered by startDate
     */
    public CourseSet orderByStartDate()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 0, false);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by startDate
     * @return  A new set with the same entries but ordered by startDate
     */
    public CourseSet orderByStartDateDesc()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 0, true);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by startDate
     * @return  A new set with the same entries that is sub ordered by startDate
     */
    public CourseSet subOrderByStartDate()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 0, false);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by startDate
     * @return  A new set with the same entries that is sub ordered by startDate
     */
    public CourseSet subOrderByStartDateDesc()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 0, true);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by startTime
     * @return  A new set with the same entries but ordered by startTime
     */
    public CourseSet orderByStartTime()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 1, false);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by startTime
     * @return  A new set with the same entries but ordered by startTime
     */
    public CourseSet orderByStartTimeDesc()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 1, true);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by startTime
     * @return  A new set with the same entries that is sub ordered by startTime
     */
    public CourseSet subOrderByStartTime()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 1, false);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by startTime
     * @return  A new set with the same entries that is sub ordered by startTime
     */
    public CourseSet subOrderByStartTimeDesc()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 1, true);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by status
     * @return  A new set with the same entries but ordered by status
     */
    public CourseSet orderByStatus()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 2, false);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by status
     * @return  A new set with the same entries but ordered by status
     */
    public CourseSet orderByStatusDesc()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 2, true);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by status
     * @return  A new set with the same entries that is sub ordered by status
     */
    public CourseSet subOrderByStatus()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 2, false);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by status
     * @return  A new set with the same entries that is sub ordered by status
     */
    public CourseSet subOrderByStatusDesc()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 2, true);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * This methods follows the relation CourseTypes
     * @return  The result of following the relation CourseTypes
     */
    public CourseTypeSet asCourseSetGetCourseTypeSet()
    {
        int newSetID = edma_dmview.relationAsASetGetBSet(1, setID);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * This methods follows the relation StudentEnrollment
     * @return  The result of following the relation StudentEnrollment
     */
    public StudentSet asCourseSetGetStudentSet()
    {
        int newSetID = edma_dmview.relationAsASetGetBSet(3, setID);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * This methods follows the relation TeacherEnrollment
     * @return  The result of following the relation TeacherEnrollment
     */
    public TeacherSet asCourseSetGetTeacherSet()
    {
        int newSetID = edma_dmview.relationAsASetGetBSet(5, setID);
        return new TeacherSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns an iterator for this set
     * @return  An iterator for this set
     */
    public Iterator<CourseViewer> iterator()
    {
        return new CourseIterator(edma_dmview.setGetIterator(setID));
    }


    /**
     * Inner class that defines the iterator
     */
    public class CourseIterator implements Iterator<CourseViewer>
    {
        private Iterator<IEntity> edma_iterator;



        /**
         * Constructor
         * @param edma_iterator  The internal iterator
         */
        public CourseIterator(Iterator<IEntity> edma_iterator)
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
        public CourseViewer next()
        {
            IEntity entity = edma_iterator.next();
            return new CourseVUImpl(entity, edma_dmview);
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
