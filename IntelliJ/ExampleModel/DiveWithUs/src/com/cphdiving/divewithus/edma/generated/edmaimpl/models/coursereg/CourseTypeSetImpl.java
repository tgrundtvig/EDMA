package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeFilter;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherSet;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseTypeSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.StudentSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.TeacherSetImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseTypeListImpl;
import java.util.ArrayList;
import java.util.Iterator;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IEntity;

/**
 * 
 */
public class CourseTypeSetImpl implements CourseTypeSet
{
    private int setID;
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param setID        The id of this set
     * @param edma_dmview  The internal runtime interface
     */
    public CourseTypeSetImpl(int setID, IDataModelView edma_dmview)
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
    public boolean contains(CourseTypeID id)
    {
        return edma_dmview.setContains(setID, id.value());
    }

    /**
     * Returns <tt>true</tt> if this set contains the given entity.
     * @param entity  The entity to test containment of
     * @return        <tt>true</tt> if this set contains the given entity
     */
    public boolean contains(CourseTypeViewer entity)
    {
        return edma_dmview.setContains(setID, entity.getID().value());
    }

    /**
     * Returns <tt>true</tt> if this set contains every entity in the given set.
     * @param courseTypeSet  The CourseTypeSet to test containment of.
     * @return               <tt>true</tt> if this set contains every entity in
     *                       the given set
     */
    public boolean containsAll(CourseTypeSet courseTypeSet)
    {
        return edma_dmview.setContainsAll(setID, ((CourseTypeSetImpl) courseTypeSet).setID);
    }

    /**
     * Creates a copy of this CourseTypeSet at this instance in time and
     * returns it as a list of values
     * @return  A copy of this CourseTypeSet as a list of values from the value
     *          domain CourseTypeList
     */
    public CourseTypeList snapshot()
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
        return new CourseTypeListImpl(res);
    }

    /**
     * Returns the union between this set and the given set
     * @param courseTypeSet  The CourseTypeSet to create the union with.
     * @return               The union between this CourseTypeSet and the
     *                       provided CourseTypeSet.
     */
    public CourseTypeSet union(CourseTypeSet courseTypeSet)
    {
        int otherSetID = ((CourseTypeSetImpl) courseTypeSet).setID;
        int newSetID = edma_dmview.setUnion(setID, otherSetID);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns the intersection between this set and the given set
     * @param courseTypeSet  The CourseTypeSet to create the intersection with.
     * @return               The intersection between this CourseTypeSet and
     *                       the provided CourseTypeSet.
     */
    public CourseTypeSet intersect(CourseTypeSet courseTypeSet)
    {
        int otherSetID = ((CourseTypeSetImpl) courseTypeSet).setID;
        int newSetID = edma_dmview.setIntersect(setID, otherSetID);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

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
    public CourseTypeSet subtract(CourseTypeSet courseTypeSet)
    {
        int otherSetID = ((CourseTypeSetImpl) courseTypeSet).setID;
        int newSetID = edma_dmview.setSubtract(setID, otherSetID);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with all the entities from this set that are accepted
     * by the filter.
     * @param filter  The filter
     * @return        A new set with all the entities from this set that are
     *                accepted by the filter.
     */
    public CourseTypeSet filter(CourseTypeFilter filter)
    {
        ArrayList<Long> res = new ArrayList<Long>();
        for(CourseTypeViewer courseType : this)
        {
            if(filter.accept(courseType))
            {
                res.add(courseType.getID().value());
            }
        }
        int newSetID = edma_dmview.setFromIDs(3, res);
        return new com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by ID.
     * @return  A new set with the same entries but ordered by ID.
     */
    public CourseTypeSet orderByID()
    {
        int newSetID = edma_dmview.setOrderByID(setID, false);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by ID.
     * @return  A new set with the same entries but ordered by ID.
     */
    public CourseTypeSet orderByIDDesc()
    {
        int newSetID = edma_dmview.setOrderByID(setID, true);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but sub ordered by ID.
     * @return  A new set with the same entries but sub ordered by ID.
     */
    public CourseTypeSet subOrderByID()
    {
        int newSetID = edma_dmview.setSubOrderByID(setID, false);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but sub ordered by ID.
     * @return  A new set with the same entries but sub ordered by ID.
     */
    public CourseTypeSet subOrderByIDDesc()
    {
        int newSetID = edma_dmview.setSubOrderByID(setID, true);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by name
     * @return  A new set with the same entries but ordered by name
     */
    public CourseTypeSet orderByName()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 0, false);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by name
     * @return  A new set with the same entries but ordered by name
     */
    public CourseTypeSet orderByNameDesc()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 0, true);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by name
     * @return  A new set with the same entries that is sub ordered by name
     */
    public CourseTypeSet subOrderByName()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 0, false);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by name
     * @return  A new set with the same entries that is sub ordered by name
     */
    public CourseTypeSet subOrderByNameDesc()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 0, true);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by size
     * @return  A new set with the same entries but ordered by size
     */
    public CourseTypeSet orderBySize()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 1, false);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by size
     * @return  A new set with the same entries but ordered by size
     */
    public CourseTypeSet orderBySizeDesc()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 1, true);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by size
     * @return  A new set with the same entries that is sub ordered by size
     */
    public CourseTypeSet subOrderBySize()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 1, false);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by size
     * @return  A new set with the same entries that is sub ordered by size
     */
    public CourseTypeSet subOrderBySizeDesc()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 1, true);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by price
     * @return  A new set with the same entries but ordered by price
     */
    public CourseTypeSet orderByPrice()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 2, false);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by price
     * @return  A new set with the same entries but ordered by price
     */
    public CourseTypeSet orderByPriceDesc()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 2, true);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by price
     * @return  A new set with the same entries that is sub ordered by price
     */
    public CourseTypeSet subOrderByPrice()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 2, false);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by price
     * @return  A new set with the same entries that is sub ordered by price
     */
    public CourseTypeSet subOrderByPriceDesc()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 2, true);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * This methods follows the relation CourseDependency
     * @return  The result of following the relation CourseDependency
     */
    public CourseTypeSet asCourseTypeSetGetRequiredSet()
    {
        int newSetID = edma_dmview.relationAsASetGetBSet(0, setID);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * This methods follows the relation CourseDependency
     * @return  The result of following the relation CourseDependency
     */
    public CourseTypeSet asRequiredSetGetCourseTypeSet()
    {
        int newSetID = edma_dmview.relationAsBSetGetASet(0, setID);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * This methods follows the relation CourseTypes
     * @return  The result of following the relation CourseTypes
     */
    public CourseSet asCourseTypeSetGetCourseSet()
    {
        int newSetID = edma_dmview.relationAsBSetGetASet(1, setID);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * This methods follows the relation PassedCourses
     * @return  The result of following the relation PassedCourses
     */
    public StudentSet asPassedCourseSetGetStudentSet()
    {
        int newSetID = edma_dmview.relationAsBSetGetASet(2, setID);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * This methods follows the relation TeachingAbility
     * @return  The result of following the relation TeachingAbility
     */
    public TeacherSet asCourseTypeSetGetTeacherSet()
    {
        int newSetID = edma_dmview.relationAsBSetGetASet(4, setID);
        return new TeacherSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns an iterator for this set
     * @return  An iterator for this set
     */
    public Iterator<CourseTypeViewer> iterator()
    {
        return new CourseTypeIterator(edma_dmview.setGetIterator(setID));
    }


    /**
     * Inner class that defines the iterator
     */
    public class CourseTypeIterator implements Iterator<CourseTypeViewer>
    {
        private Iterator<IEntity> edma_iterator;



        /**
         * Constructor
         * @param edma_iterator  The internal iterator
         */
        public CourseTypeIterator(Iterator<IEntity> edma_iterator)
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
        public CourseTypeViewer next()
        {
            IEntity entity = edma_iterator.next();
            return new CourseTypeVUImpl(entity, edma_dmview);
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
