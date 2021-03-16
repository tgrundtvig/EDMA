package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.Mobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.NotNegInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.PersonBuilderImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.PersonImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: Person
 */
public abstract class Person implements Comparable<Person>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(35);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The Person from the terminal
     */
    public static Person fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new PersonImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The Person from the string representation
     */
    public static Person fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new PersonImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The Person read from the stream
     */
    public static Person fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new PersonImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The Person read from the stream
     */
    public static Person fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new PersonImpl(res);
    }

    /**
     * Starts creation of a new Person
     * @return  Builder interface to set the ID field
     */
    public static PersonBuilderID create()
    {
        return new PersonBuilderImpl();
    }



    /**
     * Writes this value to a stream
     * @param out  Interface to data output stream
     */
    public abstract void toStream(DataOutput out) throws IOException;

    /**
     * returns the value of the field ID
     * @return  The value of the field ID
     */
    public abstract PersonID ID();

    /**
     * returns the value of the field name
     * @return  The value of the field name
     */
    public abstract Name name();

    /**
     * returns the value of the field email
     * @return  The value of the field email
     */
    public abstract Email email();

    /**
     * returns the value of the field mobile
     * @return  The value of the field mobile
     */
    public abstract Mobile mobile();

    /**
     * returns the value of the field balance
     * @return  The value of the field balance
     */
    public abstract NotNegInt balance();


    /**
     * Builder interface for setting the ID field of Person
     */
    public interface PersonBuilderID
    {

        /**
         * sets the ID field.
         * @param ID  The value to assign to the ID field
         * @return    Builder interface for setting the name field
         */
        public PersonBuilderName ID(PersonID ID);

        /**
         * sets the ID field.
         * @param ID  The value to assign to the ID field
         * @return    Builder interface for setting the name field
         */
        public PersonBuilderName ID(Long ID) throws InvalidValueException;

        /**
         * sets the fieldID to null.
         * @return  Builder interface for setting the name field
         */
        public PersonBuilderName noID();

    }



    /**
     * Builder interface for setting the name field of Person
     */
    public interface PersonBuilderName
    {

        /**
         * sets the name field.
         * @param name  The value to assign to the name field
         * @return      Builder interface for setting the email field
         */
        public PersonBuilderEmail name(Name name);

        /**
         * sets the name field.
         * @param name  The value to assign to the name field
         * @return      Builder interface for setting the email field
         */
        public PersonBuilderEmail name(String name) throws InvalidValueException;

    }



    /**
     * Builder interface for setting the email field of Person
     */
    public interface PersonBuilderEmail
    {

        /**
         * sets the email field.
         * @param email  The value to assign to the email field
         * @return       Builder interface for setting the mobile field
         */
        public PersonBuilderMobile email(Email email);

        /**
         * sets the email field.
         * @param email  The value to assign to the email field
         * @return       Builder interface for setting the mobile field
         */
        public PersonBuilderMobile email(String email) throws InvalidValueException;

    }



    /**
     * Builder interface for setting the mobile field of Person
     */
    public interface PersonBuilderMobile
    {

        /**
         * sets the mobile field.
         * @param mobile  The value to assign to the mobile field
         * @return        Builder interface for setting the balance field
         */
        public PersonBuilderBalance mobile(Mobile mobile);

        /**
         * sets the mobile field.
         * @param mobile  The value to assign to the mobile field
         * @return        Builder interface for setting the balance field
         */
        public PersonBuilderBalance mobile(String mobile) throws InvalidValueException;

    }



    /**
     * Builder interface for setting the balance field of Person
     */
    public interface PersonBuilderBalance
    {

        /**
         * sets the balance field.
         * @param balance  The value to assign to the balance field
         * @return         The created Person value
         */
        public Person balance(NotNegInt balance);

        /**
         * sets the balance field.
         * @param balance  The value to assign to the balance field
         * @return         The created Person value
         */
        public Person balance(Integer balance) throws InvalidValueException;

    }

}
