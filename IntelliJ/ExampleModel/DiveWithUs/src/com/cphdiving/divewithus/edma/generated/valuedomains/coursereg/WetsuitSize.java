package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.WetsuitSizeImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: WetsuitSize
 */
public abstract class WetsuitSize implements Comparable<WetsuitSize>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(21);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The WetsuitSize from the terminal
     */
    public static WetsuitSize fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new WetsuitSizeImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The WetsuitSize from the string representation
     */
    public static WetsuitSize fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new WetsuitSizeImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The WetsuitSize read from the stream
     */
    public static WetsuitSize fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new WetsuitSizeImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The WetsuitSize read from the stream
     */
    public static WetsuitSize fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new WetsuitSizeImpl(res);
    }

    /**
     * Call this method to create a new WetsuitSize value. 
     * @param value  The value of the WetsuitSize to be created.
     * @return       The newly created WetsuitSize value
     */
    public static WetsuitSize create(String value) throws InvalidValueException
    {
        WetsuitSizeImpl.edma_validate(value);
        return new WetsuitSizeImpl(WetsuitSizeImpl.edma_create(value));
    }

    /**
     * Call this method to test if the provided String is a valid WetsuitSize
     * @param value  The String value to be tested
     * @return       true if the provided String is a valid WetsuitSize
     */
    public static boolean isValidWetsuitSize(String value)
    {
        try
        {
            WetsuitSizeImpl.edma_validate(value);
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
     * returns the String value that is stored in this WetsuitSize
     * @return  The String value stored in this WetsuitSize
     */
    public abstract String value();
}
