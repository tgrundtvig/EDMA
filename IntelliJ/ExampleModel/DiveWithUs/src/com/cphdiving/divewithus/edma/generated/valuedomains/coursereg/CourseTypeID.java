package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseTypeIDImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: CourseTypeID
 */
public abstract class CourseTypeID implements Comparable<CourseTypeID>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(42);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The CourseTypeID from the terminal
     */
    public static CourseTypeID fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new CourseTypeIDImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The CourseTypeID from the string representation
     */
    public static CourseTypeID fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new CourseTypeIDImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The CourseTypeID read from the stream
     */
    public static CourseTypeID fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new CourseTypeIDImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The CourseTypeID read from the stream
     */
    public static CourseTypeID fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new CourseTypeIDImpl(res);
    }

    /**
     * Call this method to create a new CourseTypeID value. 
     * @param value  The value of the CourseTypeID to be created.
     * @return       The newly created CourseTypeID value
     */
    public static CourseTypeID create(Long value) throws InvalidValueException
    {
        CourseTypeIDImpl.edma_validate(value);
        return new CourseTypeIDImpl(CourseTypeIDImpl.edma_create(value));
    }

    /**
     * Call this method to test if the provided Long is a valid CourseTypeID
     * @param value  The Long value to be tested
     * @return       true if the provided Long is a valid CourseTypeID
     */
    public static boolean isValidCourseTypeID(Long value)
    {
        try
        {
            CourseTypeIDImpl.edma_validate(value);
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
     * returns the Long value that is stored in this CourseTypeID
     * @return  The Long value stored in this CourseTypeID
     */
    public abstract Long value();
}
