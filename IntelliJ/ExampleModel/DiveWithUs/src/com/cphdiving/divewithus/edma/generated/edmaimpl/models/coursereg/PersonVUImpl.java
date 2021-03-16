package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherViewer;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.StudentVUImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.TeacherVUImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.Mobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.NotNegInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Person;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.PersonIDImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.PersonImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.EmailImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.MobileImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.NameImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.NotNegIntImpl;
import java.util.HashMap;
import java.util.Map;
import org.abstractica.edma.runtime.intf.IDataModelUpdate;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.runtime.intf.exceptions.UniqueException;
import org.abstractica.edma.valuedomains.IValueInstance;

/**
 * 
 */
public class PersonVUImpl implements PersonUpdater, PersonViewer
{
    protected IEntity edma_entity;
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param edma_entity  Internal entity
     * @param edma_dmview  Internal runtime interface
     */
    public PersonVUImpl(IEntity edma_entity, IDataModelView edma_dmview)
    {
        this.edma_entity = edma_entity;
        this.edma_dmview = edma_dmview;
    }

    /**
     * Create a copy of this Person at this instance in time
     * @return  A copy of this Person entity as a value  from the value domain
     *          Person
     */
    public Person snapshot()
    {
        Object[] res = new Object[5];
        Object[] internal = edma_entity.getValue();
        res[0] = internal[0];
        res[1] = internal[1];
        res[2] = internal[2];
        res[3] = internal[3];
        res[4] = internal[4];
        return new PersonImpl(res);
    }

    /**
     * Returns the ID of this Person
     * @return  The ID of this Person
     */
    public PersonID getID()
    {
        return new PersonIDImpl(edma_entity.getID());
    }

    /**
     * Returns the attribute name of this Person
     * @return  The value of the attribute name
     */
    public Name getName()
    {
        return new NameImpl(edma_entity.getValue()[1]);
    }

    /**
     * Returns the attribute email of this Person
     * @return  The value of the attribute email
     */
    public Email getEmail()
    {
        return new EmailImpl(edma_entity.getValue()[2]);
    }

    /**
     * Returns the attribute mobile of this Person
     * @return  The value of the attribute mobile
     */
    public Mobile getMobile()
    {
        return new MobileImpl(edma_entity.getValue()[3]);
    }

    /**
     * Returns the attribute balance of this Person
     * @return  The value of the attribute balance
     */
    public NotNegInt getBalance()
    {
        return new NotNegIntImpl(edma_entity.getValue()[4]);
    }

    /**
     * Views this Person as its extension kind Teacher. May return
     * <tt>null</tt> if this Person is not extended to Teacher
     * @return  The Teacher view of this Person or <tt>null</tt> if this Person
     *          is not extended to Teacher
     */
    public TeacherViewer asTeacher()
    {
        IEntity ext = edma_dmview.kindGetFromID(1, edma_entity.getID());
        if(ext == null) return null;
        return new TeacherVUImpl(ext, edma_dmview);
    }

    /**
     * Views this Person as its extension kind Student. May return
     * <tt>null</tt> if this Person is not extended to Student
     * @return  The Student view of this Person or <tt>null</tt> if this Person
     *          is not extended to Student
     */
    public StudentViewer asStudent()
    {
        IEntity ext = edma_dmview.kindGetFromID(2, edma_entity.getID());
        if(ext == null) return null;
        return new StudentVUImpl(ext, edma_dmview);
    }

    /**
     * Returns <tt>true</tt> if this entity has the same ID as the provided
     * entity
     * @param o  The object to compare with
     * @return   <tt>true</tt> if this entity has the same ID as the provided
     *           entity
     */
    public boolean equals(Object o)
    {
        if(!(o instanceof PersonViewer)) return false;
        PersonViewer other = (PersonViewer) o;
        return getID().equals(other.getID());
    }

    /**
     * Returns the hash code of this entity
     * @return  The hash code of this entity
     */
    public int hashCode()
    {
        return getID().hashCode();
    }

    /**
     * Begin attribute updates on this Person
     * @return  An attribute update interface for chaining attribute updates
     */
    public PersonAttUpd beginUpdate()
    {
        return new PersonAttUpdImpl(edma_entity.getID(), edma_dmview.getUpdateInterface());
    }


    /**
     * 
     */
    private class PersonAttUpdImpl implements PersonAttUpd
    {
        private Long entityID;
        private IDataModelUpdate edma_dmupdate;
        private Map<Integer, Object> updateMap;



        /**
         * Constructor
         * @param entityID       ID of the entity to update
         * @param edma_dmupdate  Internal runtime interface
         */
        private PersonAttUpdImpl(Long entityID, IDataModelUpdate edma_dmupdate)
        {
            this.entityID = entityID;
            this.edma_dmupdate = edma_dmupdate;
            this.updateMap = new HashMap<Integer, Object>();
        }

        /**
         * Sets the attribute name on this Person
         * @param name  The value to set
         * @return      Interface for chaining attribute updates
         */
        public PersonAttUpd setName(Name name)
        {
            updateMap.put(0, ((IValueInstance) name).edma_getValue());
            return this;
        }

        /**
         * Sets the attribute email on this Person
         * @param email  The value to set
         * @return       Interface for chaining attribute updates
         */
        public PersonAttUpdUnique setEmail(Email email)
        {
            updateMap.put(1, ((IValueInstance) email).edma_getValue());
            return new PersonAttUpdUniqueImpl(entityID, updateMap, edma_dmupdate);
        }

        /**
         * Sets the attribute mobile on this Person
         * @param mobile  The value to set
         * @return        Interface for chaining attribute updates
         */
        public PersonAttUpdUnique setMobile(Mobile mobile)
        {
            updateMap.put(2, ((IValueInstance) mobile).edma_getValue());
            return new PersonAttUpdUniqueImpl(entityID, updateMap, edma_dmupdate);
        }

        /**
         * Sets the attribute balance on this Person
         * @param balance  The value to set
         * @return         Interface for chaining attribute updates
         */
        public PersonAttUpd setBalance(NotNegInt balance)
        {
            updateMap.put(3, ((IValueInstance) balance).edma_getValue());
            return this;
        }

        /**
         * Saves the changes to the data model.
         * @return  
         */
        public boolean save()
        {
            return edma_dmupdate.entityUpdate(0, entityID, updateMap);
        }
    }



    /**
     * 
     */
    private class PersonAttUpdUniqueImpl implements PersonAttUpdUnique
    {
        private Long entityID;
        private Map<Integer, Object> updateMap;
        private IDataModelUpdate edma_dmupdate;



        /**
         * Constructor
         * @param entityID       ID of the entity to update
         * @param updateMap      Map of updates
         * @param edma_dmupdate  Internal runtime interface
         */
        private PersonAttUpdUniqueImpl(Long entityID, Map<Integer, Object> updateMap, IDataModelUpdate edma_dmupdate)
        {
            this.entityID = entityID;
            this.updateMap = updateMap;
            this.edma_dmupdate = edma_dmupdate;
        }

        /**
         * Sets the attribute name on this Person
         * @param name  The value to set
         * @return      Interface for chaining attribute updates
         */
        public PersonAttUpdUnique setName(Name name)
        {
            updateMap.put(0, ((IValueInstance) name).edma_getValue());
            return this;
        }

        /**
         * Sets the attribute email on this Person
         * @param email  The value to set
         * @return       Interface for chaining attribute updates
         */
        public PersonAttUpdUnique setEmail(Email email)
        {
            updateMap.put(1, ((IValueInstance) email).edma_getValue());
            return this;
        }

        /**
         * Sets the attribute mobile on this Person
         * @param mobile  The value to set
         * @return        Interface for chaining attribute updates
         */
        public PersonAttUpdUnique setMobile(Mobile mobile)
        {
            updateMap.put(2, ((IValueInstance) mobile).edma_getValue());
            return this;
        }

        /**
         * Sets the attribute balance on this Person
         * @param balance  The value to set
         * @return         Interface for chaining attribute updates
         */
        public PersonAttUpdUnique setBalance(NotNegInt balance)
        {
            updateMap.put(3, ((IValueInstance) balance).edma_getValue());
            return this;
        }

        /**
         * Saves the changes to the data model.
         * @return  
         */
        public boolean save() throws UniqueException
        {
            return edma_dmupdate.entityUpdateUnique(0, entityID, updateMap);
        }
    }

}
