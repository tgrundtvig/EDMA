package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.SchoolInfoBuilderImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.SchoolInfoImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: SchoolInfo
 */
public abstract class SchoolInfo implements Comparable<SchoolInfo>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(32);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The SchoolInfo from the terminal
     */
    public static SchoolInfo fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new SchoolInfoImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The SchoolInfo from the string representation
     */
    public static SchoolInfo fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new SchoolInfoImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The SchoolInfo read from the stream
     */
    public static SchoolInfo fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new SchoolInfoImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The SchoolInfo read from the stream
     */
    public static SchoolInfo fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new SchoolInfoImpl(res);
    }

    /**
     * Starts creation of a new SchoolInfo
     * @return  Builder interface to set the name field
     */
    public static SchoolInfoBuilderName create()
    {
        return new SchoolInfoBuilderImpl();
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
     * Builder interface for setting the name field of SchoolInfo
     */
    public interface SchoolInfoBuilderName
    {

        /**
         * sets the name field.
         * @param name  The value to assign to the name field
         * @return      The created SchoolInfo value
         */
        public SchoolInfo name(Name name);

        /**
         * sets the name field.
         * @param name  The value to assign to the name field
         * @return      The created SchoolInfo value
         */
        public SchoolInfo name(String name) throws InvalidValueException;

    }

}
