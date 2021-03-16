package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.TeacherIDImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: TeacherID
 */
public abstract class TeacherID implements Comparable<TeacherID>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(36);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The TeacherID from the terminal
     */
    public static TeacherID fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new TeacherIDImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The TeacherID from the string representation
     */
    public static TeacherID fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new TeacherIDImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The TeacherID read from the stream
     */
    public static TeacherID fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new TeacherIDImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The TeacherID read from the stream
     */
    public static TeacherID fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new TeacherIDImpl(res);
    }

    /**
     * Call this method to create a new TeacherID value. 
     * @param value  The value of the TeacherID to be created.
     * @return       The newly created TeacherID value
     */
    public static TeacherID create(Long value) throws InvalidValueException
    {
        TeacherIDImpl.edma_validate(value);
        return new TeacherIDImpl(TeacherIDImpl.edma_create(value));
    }

    /**
     * Call this method to test if the provided Long is a valid TeacherID
     * @param value  The Long value to be tested
     * @return       true if the provided Long is a valid TeacherID
     */
    public static boolean isValidTeacherID(Long value)
    {
        try
        {
            TeacherIDImpl.edma_validate(value);
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
     * returns the Long value that is stored in this TeacherID
     * @return  The Long value stored in this TeacherID
     */
    public abstract Long value();
}
