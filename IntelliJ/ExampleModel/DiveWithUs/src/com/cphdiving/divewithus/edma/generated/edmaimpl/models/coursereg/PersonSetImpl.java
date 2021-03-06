package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonFilter;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherSet;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.StudentSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.TeacherSetImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.PersonListImpl;
import java.util.ArrayList;
import java.util.Iterator;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IEntity;

/**
 * 
 */
public class PersonSetImpl implements PersonSet
{
    private int setID;
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param setID        The id of this set
     * @param edma_dmview  The internal runtime interface
     */
    public PersonSetImpl(int setID, IDataModelView edma_dmview)
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
    public boolean contains(PersonID id)
    {
        return edma_dmview.setContains(setID, id.value());
    }

    /**
     * Returns <tt>true</tt> if this set contains the given entity.
     * @param entity  The entity to test containment of
     * @return        <tt>true</tt> if this set contains the given entity
     */
    public boolean contains(PersonViewer entity)
    {
        return edma_dmview.setContains(setID, entity.getID().value());
    }

    /**
     * Returns <tt>true</tt> if this set contains every entity in the given set.
     * @param personSet  The PersonSet to test containment of.
     * @return           <tt>true</tt> if this set contains every entity in the
     *                   given set
     */
    public boolean containsAll(PersonSet personSet)
    {
        return edma_dmview.setContainsAll(setID, ((PersonSetImpl) personSet).setID);
    }

    /**
     * Creates a copy of this PersonSet at this instance in time and returns it
     * as a list of values
     * @return  A copy of this PersonSet as a list of values from the value
     *          domain PersonList
     */
    public PersonList snapshot()
    {
        int size = edma_dmview.setGetSize(setID);
        Iterator<IEntity> it = edma_dmview.setGetIterator(setID);
        Object[] res = new Object[size];
        int i = 0;
        while(it.hasNext())
        {
            Object[] entObj = new Object[5];
            Object[] value = it.next().getValue();
            entObj[0] = value[0];
            entObj[1] = value[1];
            entObj[2] = value[2];
            entObj[3] = value[3];
            entObj[4] = value[4];
            res[i++] = entObj;
        }
        return new PersonListImpl(res);
    }

    /**
     * Returns the union between this set and the given set
     * @param personSet  The PersonSet to create the union with.
     * @return           The union between this PersonSet and the provided
     *                   PersonSet.
     */
    public PersonSet union(PersonSet personSet)
    {
        int otherSetID = ((PersonSetImpl) personSet).setID;
        int newSetID = edma_dmview.setUnion(setID, otherSetID);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns the intersection between this set and the given set
     * @param personSet  The PersonSet to create the intersection with.
     * @return           The intersection between this PersonSet and the
     *                   provided PersonSet.
     */
    public PersonSet intersect(PersonSet personSet)
    {
        int otherSetID = ((PersonSetImpl) personSet).setID;
        int newSetID = edma_dmview.setIntersect(setID, otherSetID);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns the subtraction of the given set from this set. The result set
     * will contain every element from this set, that are not contained in the
     * given set.
     * @param personSet  The PersonSet to subtract from this set.
     * @return           The subtraction of the provided PersonSet from this
     *                   PersonSet. The result will contain every element from
     *                   this set, that are not contained in the provided set.
     */
    public PersonSet subtract(PersonSet personSet)
    {
        int otherSetID = ((PersonSetImpl) personSet).setID;
        int newSetID = edma_dmview.setSubtract(setID, otherSetID);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with all the entities from this set that are accepted
     * by the filter.
     * @param filter  The filter
     * @return        A new set with all the entities from this set that are
     *                accepted by the filter.
     */
    public PersonSet filter(PersonFilter filter)
    {
        ArrayList<Long> res = new ArrayList<Long>();
        for(PersonViewer person : this)
        {
            if(filter.accept(person))
            {
                res.add(person.getID().value());
            }
        }
        int newSetID = edma_dmview.setFromIDs(0, res);
        return new com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Views this PersonSet as a set of its extension kind Teacher. May return
     * a smaller set, since only those entries that are extended to Teacher will
     * be included in the result set.
     * @return  The Teacher view of the entries in this set. May return a
     *          smaller set, since only those entries that are extended to
     *          Teacher will be included in the result set.
     */
    public TeacherSet asTeacherSet()
    {
        int newSetID = edma_dmview.setExtensionDown(setID, 1);
        return new TeacherSetImpl(newSetID, edma_dmview);
    }

    /**
     * Views this PersonSet as a set of its extension kind Student. May return
     * a smaller set, since only those entries that are extended to Student will
     * be included in the result set.
     * @return  The Student view of the entries in this set. May return a
     *          smaller set, since only those entries that are extended to
     *          Student will be included in the result set.
     */
    public StudentSet asStudentSet()
    {
        int newSetID = edma_dmview.setExtensionDown(setID, 2);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by ID.
     * @return  A new set with the same entries but ordered by ID.
     */
    public PersonSet orderByID()
    {
        int newSetID = edma_dmview.setOrderByID(setID, false);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by ID.
     * @return  A new set with the same entries but ordered by ID.
     */
    public PersonSet orderByIDDesc()
    {
        int newSetID = edma_dmview.setOrderByID(setID, true);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but sub ordered by ID.
     * @return  A new set with the same entries but sub ordered by ID.
     */
    public PersonSet subOrderByID()
    {
        int newSetID = edma_dmview.setSubOrderByID(setID, false);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but sub ordered by ID.
     * @return  A new set with the same entries but sub ordered by ID.
     */
    public PersonSet subOrderByIDDesc()
    {
        int newSetID = edma_dmview.setSubOrderByID(setID, true);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by name
     * @return  A new set with the same entries but ordered by name
     */
    public PersonSet orderByName()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 0, false);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by name
     * @return  A new set with the same entries but ordered by name
     */
    public PersonSet orderByNameDesc()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 0, true);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by name
     * @return  A new set with the same entries that is sub ordered by name
     */
    public PersonSet subOrderByName()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 0, false);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by name
     * @return  A new set with the same entries that is sub ordered by name
     */
    public PersonSet subOrderByNameDesc()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 0, true);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by email
     * @return  A new set with the same entries but ordered by email
     */
    public PersonSet orderByEmail()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 1, false);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by email
     * @return  A new set with the same entries but ordered by email
     */
    public PersonSet orderByEmailDesc()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 1, true);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by email
     * @return  A new set with the same entries that is sub ordered by email
     */
    public PersonSet subOrderByEmail()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 1, false);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by email
     * @return  A new set with the same entries that is sub ordered by email
     */
    public PersonSet subOrderByEmailDesc()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 1, true);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by mobile
     * @return  A new set with the same entries but ordered by mobile
     */
    public PersonSet orderByMobile()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 2, false);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by mobile
     * @return  A new set with the same entries but ordered by mobile
     */
    public PersonSet orderByMobileDesc()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 2, true);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by mobile
     * @return  A new set with the same entries that is sub ordered by mobile
     */
    public PersonSet subOrderByMobile()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 2, false);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by mobile
     * @return  A new set with the same entries that is sub ordered by mobile
     */
    public PersonSet subOrderByMobileDesc()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 2, true);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by balance
     * @return  A new set with the same entries but ordered by balance
     */
    public PersonSet orderByBalance()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 3, false);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries but ordered by balance
     * @return  A new set with the same entries but ordered by balance
     */
    public PersonSet orderByBalanceDesc()
    {
        int newSetID = edma_dmview.setOrderBy(setID, 3, true);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by balance
     * @return  A new set with the same entries that is sub ordered by balance
     */
    public PersonSet subOrderByBalance()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 3, false);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a new set with the same entries that is sub ordered by balance
     * @return  A new set with the same entries that is sub ordered by balance
     */
    public PersonSet subOrderByBalanceDesc()
    {
        int newSetID = edma_dmview.setSubOrderBy(setID, 3, true);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns an iterator for this set
     * @return  An iterator for this set
     */
    public Iterator<PersonViewer> iterator()
    {
        return new PersonIterator(edma_dmview.setGetIterator(setID));
    }


    /**
     * Inner class that defines the iterator
     */
    public class PersonIterator implements Iterator<PersonViewer>
    {
        private Iterator<IEntity> edma_iterator;



        /**
         * Constructor
         * @param edma_iterator  The internal iterator
         */
        public PersonIterator(Iterator<IEntity> edma_iterator)
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
        public PersonViewer next()
        {
            IEntity entity = edma_iterator.next();
            return new PersonVUImpl(entity, edma_dmview);
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
