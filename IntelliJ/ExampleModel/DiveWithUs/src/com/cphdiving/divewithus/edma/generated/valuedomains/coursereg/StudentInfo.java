package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.Mobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.BootSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.WetsuitSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.StudentInfoBuilderImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.StudentInfoImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: StudentInfo
 */
public abstract class StudentInfo implements Comparable<StudentInfo>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(26);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The StudentInfo from the terminal
     */
    public static StudentInfo fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new StudentInfoImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The StudentInfo from the string representation
     */
    public static StudentInfo fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new StudentInfoImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The StudentInfo read from the stream
     */
    public static StudentInfo fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new StudentInfoImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The StudentInfo read from the stream
     */
    public static StudentInfo fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new StudentInfoImpl(res);
    }

    /**
     * Starts creation of a new StudentInfo
     * @return  Builder interface to set the name field
     */
    public static StudentInfoBuilderName create()
    {
        return new StudentInfoBuilderImpl();
    }



    /**
     * Writes this value to a stream
     * @param out  Interface to data output stream
     */
    public abstract void toStream(DataOutput out) throws IOException;

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
     * returns the value of the field wetsuitSize
     * @return  The value of the field wetsuitSize
     */
    public abstract WetsuitSize wetsuitSize();

    /**
     * returns the value of the field bootSize
     * @return  The value of the field bootSize
     */
    public abstract BootSize bootSize();


    /**
     * Builder interface for setting the name field of StudentInfo
     */
    public interface StudentInfoBuilderName
    {

        /**
         * sets the name field.
         * @param name  The value to assign to the name field
         * @return      Builder interface for setting the email field
         */
        public StudentInfoBuilderEmail name(Name name);

        /**
         * sets the name field.
         * @param name  The value to assign to the name field
         * @return      Builder interface for setting the email field
         */
        public StudentInfoBuilderEmail name(String name) throws InvalidValueException;

    }



    /**
     * Builder interface for setting the email field of StudentInfo
     */
    public interface StudentInfoBuilderEmail
    {

        /**
         * sets the email field.
         * @param email  The value to assign to the email field
         * @return       Builder interface for setting the mobile field
         */
        public StudentInfoBuilderMobile email(Email email);

        /**
         * sets the email field.
         * @param email  The value to assign to the email field
         * @return       Builder interface for setting the mobile field
         */
        public StudentInfoBuilderMobile email(String email) throws InvalidValueException;

    }



    /**
     * Builder interface for setting the mobile field of StudentInfo
     */
    public interface StudentInfoBuilderMobile
    {

        /**
         * sets the mobile field.
         * @param mobile  The value to assign to the mobile field
         * @return        Builder interface for setting the wetsuitSize field
         */
        public StudentInfoBuilderWetsuitSize mobile(Mobile mobile);

        /**
         * sets the mobile field.
         * @param mobile  The value to assign to the mobile field
         * @return        Builder interface for setting the wetsuitSize field
         */
        public StudentInfoBuilderWetsuitSize mobile(String mobile) throws InvalidValueException;

    }



    /**
     * Builder interface for setting the wetsuitSize field of StudentInfo
     */
    public interface StudentInfoBuilderWetsuitSize
    {

        /**
         * sets the wetsuitSize field.
         * @param wetsuitSize  The value to assign to the wetsuitSize field
         * @return             Builder interface for setting the bootSize field
         */
        public StudentInfoBuilderBootSize wetsuitSize(WetsuitSize wetsuitSize);

        /**
         * sets the wetsuitSize field.
         * @param wetsuitSize  The value to assign to the wetsuitSize field
         * @return             Builder interface for setting the bootSize field
         */
        public StudentInfoBuilderBootSize wetsuitSize(String wetsuitSize) throws InvalidValueException;

    }



    /**
     * Builder interface for setting the bootSize field of StudentInfo
     */
    public interface StudentInfoBuilderBootSize
    {

        /**
         * sets the bootSize field.
         * @param bootSize  The value to assign to the bootSize field
         * @return          The created StudentInfo value
         */
        public StudentInfo bootSize(BootSize bootSize);

        /**
         * sets the bootSize field.
         * @param bootSize  The value to assign to the bootSize field
         * @return          The created StudentInfo value
         */
        public StudentInfo bootSize(Integer bootSize) throws InvalidValueException;

    }

}
