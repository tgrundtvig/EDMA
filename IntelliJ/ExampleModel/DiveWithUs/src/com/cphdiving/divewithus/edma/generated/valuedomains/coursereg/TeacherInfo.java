package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.Mobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.TeacherInfoBuilderImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.TeacherInfoImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: TeacherInfo
 */
public abstract class TeacherInfo implements Comparable<TeacherInfo>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(27);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The TeacherInfo from the terminal
     */
    public static TeacherInfo fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new TeacherInfoImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The TeacherInfo from the string representation
     */
    public static TeacherInfo fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new TeacherInfoImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The TeacherInfo read from the stream
     */
    public static TeacherInfo fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new TeacherInfoImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The TeacherInfo read from the stream
     */
    public static TeacherInfo fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new TeacherInfoImpl(res);
    }

    /**
     * Starts creation of a new TeacherInfo
     * @return  Builder interface to set the name field
     */
    public static TeacherInfoBuilderName create()
    {
        return new TeacherInfoBuilderImpl();
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
     * Builder interface for setting the name field of TeacherInfo
     */
    public interface TeacherInfoBuilderName
    {

        /**
         * sets the name field.
         * @param name  The value to assign to the name field
         * @return      Builder interface for setting the email field
         */
        public TeacherInfoBuilderEmail name(Name name);

        /**
         * sets the name field.
         * @param name  The value to assign to the name field
         * @return      Builder interface for setting the email field
         */
        public TeacherInfoBuilderEmail name(String name) throws InvalidValueException;

    }



    /**
     * Builder interface for setting the email field of TeacherInfo
     */
    public interface TeacherInfoBuilderEmail
    {

        /**
         * sets the email field.
         * @param email  The value to assign to the email field
         * @return       Builder interface for setting the mobile field
         */
        public TeacherInfoBuilderMobile email(Email email);

        /**
         * sets the email field.
         * @param email  The value to assign to the email field
         * @return       Builder interface for setting the mobile field
         */
        public TeacherInfoBuilderMobile email(String email) throws InvalidValueException;

    }



    /**
     * Builder interface for setting the mobile field of TeacherInfo
     */
    public interface TeacherInfoBuilderMobile
    {

        /**
         * sets the mobile field.
         * @param mobile  The value to assign to the mobile field
         * @return        The created TeacherInfo value
         */
        public TeacherInfo mobile(Mobile mobile);

        /**
         * sets the mobile field.
         * @param mobile  The value to assign to the mobile field
         * @return        The created TeacherInfo value
         */
        public TeacherInfo mobile(String mobile) throws InvalidValueException;

    }

}
