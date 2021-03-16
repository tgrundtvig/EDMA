package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.StudentIDImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: StudentID
 */
public abstract class StudentID implements Comparable<StudentID>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(39);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The StudentID from the terminal
     */
    public static StudentID fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new StudentIDImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The StudentID from the string representation
     */
    public static StudentID fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new StudentIDImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The StudentID read from the stream
     */
    public static StudentID fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new StudentIDImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The StudentID read from the stream
     */
    public static StudentID fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new StudentIDImpl(res);
    }

    /**
     * Call this method to create a new StudentID value. 
     * @param value  The value of the StudentID to be created.
     * @return       The newly created StudentID value
     */
    public static StudentID create(Long value) throws InvalidValueException
    {
        StudentIDImpl.edma_validate(value);
        return new StudentIDImpl(StudentIDImpl.edma_create(value));
    }

    /**
     * Call this method to test if the provided Long is a valid StudentID
     * @param value  The Long value to be tested
     * @return       true if the provided Long is a valid StudentID
     */
    public static boolean isValidStudentID(Long value)
    {
        try
        {
            StudentIDImpl.edma_validate(value);
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
     * returns the Long value that is stored in this StudentID
     * @return  The Long value stored in this StudentID
     */
    public abstract Long value();
}
