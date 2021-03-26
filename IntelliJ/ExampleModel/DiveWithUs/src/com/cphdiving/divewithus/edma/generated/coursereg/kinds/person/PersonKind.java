package com.cphdiving.divewithus.edma.generated.coursereg.kinds.person;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.Mobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonID;

/**
 * Interface for the kind: Person
 */
public interface PersonKind
{

    /**
     * Get an entity from its id.
     * @param id  The id of the entity to get
     * @return    A viewer on the entity with the given ID or null if none
     *            exists.
     */
    public PersonViewer getFromID(PersonID id);

    /**
     * Returns an empty set of Person entities.
     * @return  An empty set of Person entities.
     */
    public PersonSet getEmptyPersonSet();

    /**
     * Returns the set of all Person entities.
     * @return  The set of all Person entities.
     */
    public PersonSet getAll();

    /**
     * Returns the unique person from the unique-index on email or
     * <tt>null</tt> if there is no person with the given email.
     * @param email  Value for email
     * @return       The unique person from the unique-index on email or
     *               <tt>null</tt> if there is no person with the given email.
     */
    public PersonViewer getFromEmail(Email email);

    /**
     * Returns the unique person from the unique-index on mobile or
     * <tt>null</tt> if there is no person with the given mobile.
     * @param mobile  Value for mobile
     * @return        The unique person from the unique-index on mobile or
     *                <tt>null</tt> if there is no person with the given mobile.
     */
    public PersonViewer getFromMobile(Mobile mobile);

    /**
     * Returns a PersonSet where name equals the provided name.
     * @param name  Value for name
     * @return      A PersonSet where name equals the provided name.
     */
    public PersonSet getWhereNameEquals(Name name);

    /**
     * Returns a PersonSet where name is less than the provided name.
     * @param name  Value for name
     * @return      A PersonSet where name is less than the provided name.
     */
    public PersonSet getWhereNameLessThan(Name name);

    /**
     * Returns a PersonSet where name is less than or equal to the provided
     * name.
     * @param name  Value for name
     * @return      A PersonSet where name is less than or equal to the
     *              provided name.
     */
    public PersonSet getWhereNameLessThanOrEqual(Name name);

    /**
     * Returns a PersonSet where name is greater than the provided name.
     * @param name  Value for name
     * @return      A PersonSet where name is greater than the provided name.
     */
    public PersonSet getWhereNameGreaterThan(Name name);

    /**
     * Returns a PersonSet where name is greater than or equal to the provided
     * name.
     * @param name  Value for name
     * @return      A PersonSet where name is greater than or equal to the
     *              provided name.
     */
    public PersonSet getWhereNameGreaterThanOrEqual(Name name);

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
    public PersonSet getWhereNameInRange(Name minName, boolean minInclusive, Name maxName, boolean maxInclusive);

}
