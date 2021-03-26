package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.Mobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.NotNegInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Person;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Person.PersonBuilderBalance;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Person.PersonBuilderEmail;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Person.PersonBuilderID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Person.PersonBuilderMobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Person.PersonBuilderName;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.PersonIDImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.EmailImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.MobileImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.NameImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.NotNegIntImpl;
import org.abstractica.edma.valuedomains.IValueInstance;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;

/**
 * 
 */
public class PersonBuilderImpl implements PersonBuilderBalance, PersonBuilderEmail, PersonBuilderID, PersonBuilderMobile, PersonBuilderName
{
    private Object[] edma_value;



    /**
     * Constructor
     */
    public PersonBuilderImpl()
    {
        edma_value = new Object[5];
    }

    /**
     * sets the ID field.
     * @param ID  The value to assign to the ID field
     * @return    Builder interface for setting the name field
     */
    public PersonBuilderName ID(PersonID ID)
    {
        edma_value[0] = (ID == null ? null : (((IValueInstance) ID).edma_getValue()));
        return this;
    }

    /**
     * sets the ID field.
     * @param ID  The value to assign to the ID field
     * @return    Builder interface for setting the name field
     */
    public PersonBuilderName ID(Long ID) throws InvalidValueException
    {
        if(ID != null) PersonIDImpl.edma_validate(ID);
        edma_value[0] = (ID == null ? null : (PersonIDImpl.edma_create(ID)));
        return this;
    }

    /**
     * sets the fieldID to null.
     * @return  Builder interface for setting the name field
     */
    public PersonBuilderName noID()
    {
        edma_value[0] = null;
        return this;
    }

    /**
     * sets the name field.
     * @param name  The value to assign to the name field
     * @return      Builder interface for setting the email field
     */
    public PersonBuilderEmail name(Name name)
    {
        if(name == null) throw new NullPointerException("The field name in the Person ValueDomain may not be null");
        edma_value[1] = ((IValueInstance) name).edma_getValue();
        return this;
    }

    /**
     * sets the name field.
     * @param name  The value to assign to the name field
     * @return      Builder interface for setting the email field
     */
    public PersonBuilderEmail name(String name) throws InvalidValueException
    {
        if(name != null) NameImpl.edma_validate(name);
        if(name == null) throw new NullPointerException("The field name in the Person ValueDomain may not be null");
        edma_value[1] = NameImpl.edma_create(name);
        return this;
    }

    /**
     * sets the email field.
     * @param email  The value to assign to the email field
     * @return       Builder interface for setting the mobile field
     */
    public PersonBuilderMobile email(Email email)
    {
        if(email == null) throw new NullPointerException("The field email in the Person ValueDomain may not be null");
        edma_value[2] = ((IValueInstance) email).edma_getValue();
        return this;
    }

    /**
     * sets the email field.
     * @param email  The value to assign to the email field
     * @return       Builder interface for setting the mobile field
     */
    public PersonBuilderMobile email(String email) throws InvalidValueException
    {
        if(email != null) EmailImpl.edma_validate(email);
        if(email == null) throw new NullPointerException("The field email in the Person ValueDomain may not be null");
        edma_value[2] = EmailImpl.edma_create(email);
        return this;
    }

    /**
     * sets the mobile field.
     * @param mobile  The value to assign to the mobile field
     * @return        Builder interface for setting the balance field
     */
    public PersonBuilderBalance mobile(Mobile mobile)
    {
        if(mobile == null) throw new NullPointerException("The field mobile in the Person ValueDomain may not be null");
        edma_value[3] = ((IValueInstance) mobile).edma_getValue();
        return this;
    }

    /**
     * sets the mobile field.
     * @param mobile  The value to assign to the mobile field
     * @return        Builder interface for setting the balance field
     */
    public PersonBuilderBalance mobile(String mobile) throws InvalidValueException
    {
        if(mobile != null) MobileImpl.edma_validate(mobile);
        if(mobile == null) throw new NullPointerException("The field mobile in the Person ValueDomain may not be null");
        edma_value[3] = MobileImpl.edma_create(mobile);
        return this;
    }

    /**
     * sets the balance field.
     * @param balance  The value to assign to the balance field
     * @return         The created Person value
     */
    public Person balance(NotNegInt balance)
    {
        if(balance == null) throw new NullPointerException("The field balance in the Person ValueDomain may not be null");
        edma_value[4] = ((IValueInstance) balance).edma_getValue();
        return new PersonImpl(PersonImpl.edma_create(edma_value));
    }

    /**
     * sets the balance field.
     * @param balance  The value to assign to the balance field
     * @return         The created Person value
     */
    public Person balance(Integer balance) throws InvalidValueException
    {
        if(balance != null) NotNegIntImpl.edma_validate(balance);
        if(balance == null) throw new NullPointerException("The field balance in the Person ValueDomain may not be null");
        edma_value[4] = NotNegIntImpl.edma_create(balance);
        return new PersonImpl(PersonImpl.edma_create(edma_value));
    }
}
