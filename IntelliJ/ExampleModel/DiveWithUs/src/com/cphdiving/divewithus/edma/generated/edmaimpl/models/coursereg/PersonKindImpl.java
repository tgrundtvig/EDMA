package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonKind;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonViewer;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.PersonSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.PersonVUImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.Mobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonID;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.valuedomains.IValueInstance;

/**
 * 
 */
public class PersonKindImpl implements PersonKind
{
    private int edma_kindIndex;
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param edma_kindIndex  kind index
     * @param edma_dmview     Internal runtime interface
     */
    public PersonKindImpl(int edma_kindIndex, IDataModelView edma_dmview)
    {
        this.edma_kindIndex = edma_kindIndex;
        this.edma_dmview = edma_dmview;
    }

    /**
     * Get an entity from its id.
     * @param id  The id of the entity to get
     * @return    A viewer on the entity with the given ID or null if none
     *            exists.
     */
    public PersonViewer getFromID(PersonID id)
    {
        IEntity res = edma_dmview.kindGetFromID(edma_kindIndex, id.value());
        if(res == null) return null;
        return new PersonVUImpl(res, edma_dmview);
    }

    /**
     * Returns an empty set of Person entities.
     * @return  An empty set of Person entities.
     */
    public PersonSet getEmptyPersonSet()
    {
        int newSetID = edma_dmview.kindGetEmptySet(0);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns the set of all Person entities.
     * @return  The set of all Person entities.
     */
    public PersonSet getAll()
    {
        int newSetID = edma_dmview.kindGetAll(0);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns the unique person from the unique-index on email or
     * <tt>null</tt> if there is no person with the given email.
     * @param email  Value for email
     * @return       The unique person from the unique-index on email or
     *               <tt>null</tt> if there is no person with the given email.
     */
    public PersonViewer getFromEmail(Email email)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) email).edma_getValue();
        IEntity res = edma_dmview.getKindIndex(0, 0).getFromUnique(edma_values);
        if(res == null) return null;
        return new PersonVUImpl(res, edma_dmview);
    }

    /**
     * Returns the unique person from the unique-index on mobile or
     * <tt>null</tt> if there is no person with the given mobile.
     * @param mobile  Value for mobile
     * @return        The unique person from the unique-index on mobile or
     *                <tt>null</tt> if there is no person with the given mobile.
     */
    public PersonViewer getFromMobile(Mobile mobile)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) mobile).edma_getValue();
        IEntity res = edma_dmview.getKindIndex(0, 1).getFromUnique(edma_values);
        if(res == null) return null;
        return new PersonVUImpl(res, edma_dmview);
    }

    /**
     * Returns a PersonSet where name equals the provided name.
     * @param name  Value for name
     * @return      A PersonSet where name equals the provided name.
     */
    public PersonSet getWhereNameEquals(Name name)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) name).edma_getValue();
        int newSetID = edma_dmview.getKindIndex(0, 2).getEquals(edma_values);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a PersonSet where name is less than the provided name.
     * @param name  Value for name
     * @return      A PersonSet where name is less than the provided name.
     */
    public PersonSet getWhereNameLessThan(Name name)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) name).edma_getValue();
        int newSetID = edma_dmview.getKindIndex(0, 2).getLessThan(edma_values);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a PersonSet where name is less than or equal to the provided
     * name.
     * @param name  Value for name
     * @return      A PersonSet where name is less than or equal to the
     *              provided name.
     */
    public PersonSet getWhereNameLessThanOrEqual(Name name)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) name).edma_getValue();
        int newSetID = edma_dmview.getKindIndex(0, 2).getLessThanOrEqual(edma_values);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a PersonSet where name is greater than the provided name.
     * @param name  Value for name
     * @return      A PersonSet where name is greater than the provided name.
     */
    public PersonSet getWhereNameGreaterThan(Name name)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) name).edma_getValue();
        int newSetID = edma_dmview.getKindIndex(0, 2).getGreaterThan(edma_values);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a PersonSet where name is greater than or equal to the provided
     * name.
     * @param name  Value for name
     * @return      A PersonSet where name is greater than or equal to the
     *              provided name.
     */
    public PersonSet getWhereNameGreaterThanOrEqual(Name name)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) name).edma_getValue();
        int newSetID = edma_dmview.getKindIndex(0, 2).getGreaterThanOrEqual(edma_values);
        return new PersonSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns the subset of this PersonSet where name is in the given range.
     * @param minName       Minimum value for name
     * @param minInclusive  <tt>true</tt> if the minimum value should be
     *                      included.
     * @param maxName       Maximum value for name
     * @param maxInclusive  <tt>true</tt> if the maximum value should be
     *                      included.
     * @return              The subset of this PersonSet where name is in the
     *                      given range.
     */
    public PersonSet getWhereNameInRange(Name minName, boolean minInclusive, Name maxName, boolean maxInclusive)
    {
        Object[] edma_minValues = new Object[1];
        Object[] edma_maxValues = new Object[1];
        edma_minValues[0] = ((IValueInstance) minName).edma_getValue();
        edma_maxValues[0] = ((IValueInstance) maxName).edma_getValue();
        int newSetID = edma_dmview.getKindIndex(0, 2).getRange(edma_minValues, minInclusive, edma_maxValues, maxInclusive);
        return new PersonSetImpl(newSetID, edma_dmview);
    }
}
