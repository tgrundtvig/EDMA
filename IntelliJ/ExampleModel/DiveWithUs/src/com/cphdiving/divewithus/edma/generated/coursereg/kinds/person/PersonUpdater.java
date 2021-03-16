package com.cphdiving.divewithus.edma.generated.coursereg.kinds.person;

import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.Mobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.NotNegInt;
import org.abstractica.edma.runtime.intf.exceptions.UniqueException;

/**
 * This is the update interface for Person
 */
public interface PersonUpdater extends PersonViewer
{

    /**
     * Begin attribute updates on this Person
     * @return  An attribute update interface for chaining attribute updates
     */
    public PersonAttUpd beginUpdate();

    /**
     * Interface for updating attributes on an entity of kind: Person
     */
    public interface PersonAttUpd
    {

        /**
         * Sets the attribute name on this Person
         * @param name  The value to set
         * @return      Interface for chaining attribute updates
         */
        public PersonAttUpd setName(Name name);

        /**
         * Sets the attribute email on this Person
         * @param email  The value to set
         * @return       Interface for chaining attribute updates
         */
        public PersonAttUpdUnique setEmail(Email email);

        /**
         * Sets the attribute mobile on this Person
         * @param mobile  The value to set
         * @return        Interface for chaining attribute updates
         */
        public PersonAttUpdUnique setMobile(Mobile mobile);

        /**
         * Sets the attribute balance on this Person
         * @param balance  The value to set
         * @return         Interface for chaining attribute updates
         */
        public PersonAttUpd setBalance(NotNegInt balance);

        /**
         * Saves the changes to the data model.
         * @return  
         */
        public boolean save();

    }

    /**
     * Interface for updating attributes on an entity of kind: Person
     */
    public interface PersonAttUpdUnique
    {

        /**
         * Sets the attribute name on this Person
         * @param name  The value to set
         * @return      Interface for chaining attribute updates
         */
        public PersonAttUpdUnique setName(Name name);

        /**
         * Sets the attribute email on this Person
         * @param email  The value to set
         * @return       Interface for chaining attribute updates
         */
        public PersonAttUpdUnique setEmail(Email email);

        /**
         * Sets the attribute mobile on this Person
         * @param mobile  The value to set
         * @return        Interface for chaining attribute updates
         */
        public PersonAttUpdUnique setMobile(Mobile mobile);

        /**
         * Sets the attribute balance on this Person
         * @param balance  The value to set
         * @return         Interface for chaining attribute updates
         */
        public PersonAttUpdUnique setBalance(NotNegInt balance);

        /**
         * Saves the changes to the data model.
         * @return  
         */
        public boolean save() throws UniqueException;

    }

}
