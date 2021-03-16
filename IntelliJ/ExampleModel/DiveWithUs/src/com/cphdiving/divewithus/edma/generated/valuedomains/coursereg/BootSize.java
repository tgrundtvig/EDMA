package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.BootSizeImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: BootSize
 */
public abstract class BootSize implements Comparable<BootSize>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(22);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The BootSize from the terminal
     */
    public static BootSize fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new BootSizeImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The BootSize from the string representation
     */
    public static BootSize fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new BootSizeImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The BootSize read from the stream
     */
    public static BootSize fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new BootSizeImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The BootSize read from the stream
     */
    public static BootSize fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new BootSizeImpl(res);
    }

    /**
     * Call this method to create a new BootSize value. 
     * @param value  The value of the BootSize to be created.
     * @return       The newly created BootSize value
     */
    public static BootSize create(Integer value) throws InvalidValueException
    {
        BootSizeImpl.edma_validate(value);
        return new BootSizeImpl(BootSizeImpl.edma_create(value));
    }

    /**
     * Call this method to test if the provided Integer is a valid BootSize
     * @param value  The Integer value to be tested
     * @return       true if the provided Integer is a valid BootSize
     */
    public static boolean isValidBootSize(Integer value)
    {
        try
        {
            BootSizeImpl.edma_validate(value);
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
     * returns the Integer value that is stored in this BootSize
     * @return  The Integer value stored in this BootSize
     */
    public abstract Integer value();
}
