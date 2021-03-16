package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseStatusImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: CourseStatus
 */
public abstract class CourseStatus implements Comparable<CourseStatus>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(25);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The CourseStatus from the terminal
     */
    public static CourseStatus fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new CourseStatusImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The CourseStatus from the string representation
     */
    public static CourseStatus fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new CourseStatusImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The CourseStatus read from the stream
     */
    public static CourseStatus fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new CourseStatusImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The CourseStatus read from the stream
     */
    public static CourseStatus fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new CourseStatusImpl(res);
    }

    /**
     * Call this method to create a new CourseStatus value. 
     * @param value  The value of the CourseStatus to be created.
     * @return       The newly created CourseStatus value
     */
    public static CourseStatus create(String value) throws InvalidValueException
    {
        CourseStatusImpl.edma_validate(value);
        return new CourseStatusImpl(CourseStatusImpl.edma_create(value));
    }

    /**
     * Call this method to test if the provided String is a valid CourseStatus
     * @param value  The String value to be tested
     * @return       true if the provided String is a valid CourseStatus
     */
    public static boolean isValidCourseStatus(String value)
    {
        try
        {
            CourseStatusImpl.edma_validate(value);
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
     * returns the String value that is stored in this CourseStatus
     * @return  The String value stored in this CourseStatus
     */
    public abstract String value();
}
