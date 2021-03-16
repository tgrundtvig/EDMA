package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseNameImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: CourseName
 */
public abstract class CourseName implements Comparable<CourseName>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(23);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The CourseName from the terminal
     */
    public static CourseName fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new CourseNameImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The CourseName from the string representation
     */
    public static CourseName fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new CourseNameImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The CourseName read from the stream
     */
    public static CourseName fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new CourseNameImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The CourseName read from the stream
     */
    public static CourseName fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new CourseNameImpl(res);
    }

    /**
     * Call this method to create a new CourseName value. 
     * @param value  The value of the CourseName to be created.
     * @return       The newly created CourseName value
     */
    public static CourseName create(String value) throws InvalidValueException
    {
        CourseNameImpl.edma_validate(value);
        return new CourseNameImpl(CourseNameImpl.edma_create(value));
    }

    /**
     * Call this method to test if the provided String is a valid CourseName
     * @param value  The String value to be tested
     * @return       true if the provided String is a valid CourseName
     */
    public static boolean isValidCourseName(String value)
    {
        try
        {
            CourseNameImpl.edma_validate(value);
        }
        catch(InvalidValueException e)
        {
            return false;
        }
        return true;
    }



    /**
     * Writes this value to a stream
     * @param out  Interface to data output stream
     */
    public abstract void toStream(DataOutput out) throws IOException;

    /**
     * returns the String value that is stored in this CourseName
     * @return  The String value stored in this CourseName
     */
    public abstract String value();
}
