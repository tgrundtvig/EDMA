package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfoList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseProcessResultBuilderImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseProcessResultImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: CourseProcessResult
 */
public abstract class CourseProcessResult implements Comparable<CourseProcessResult>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(31);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The CourseProcessResult from the terminal
     */
    public static CourseProcessResult fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new CourseProcessResultImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The CourseProcessResult from the string representation
     */
    public static CourseProcessResult fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new CourseProcessResultImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The CourseProcessResult read from the stream
     */
    public static CourseProcessResult fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new CourseProcessResultImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The CourseProcessResult read from the stream
     */
    public static CourseProcessResult fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new CourseProcessResultImpl(res);
    }

    /**
     * Starts creation of a new CourseProcessResult
     * @return  Builder interface to set the canceled field
     */
    public static CourseProcessResultBuilderCanceled create()
    {
        return new CourseProcessResultBuilderImpl();
    }



    /**
     * Writes this value to a stream
     * @param out  Interface to data output stream
     */
    public abstract void toStream(DataOutput out) throws IOException;

    /**
     * returns the value of the field canceled
     * @return  The value of the field canceled
     */
    public abstract CourseInfoList canceled();

    /**
     * returns the value of the field running
     * @return  The value of the field running
     */
    public abstract CourseInfoList running();


    /**
     * Builder interface for setting the canceled field of CourseProcessResult
     */
    public interface CourseProcessResultBuilderCanceled
    {

        /**
         * sets the canceled field.
         * @param canceled  The value to assign to the canceled field
         * @return          Builder interface for setting the running field
         */
        public CourseProcessResultBuilderRunning canceled(CourseInfoList canceled);

    }



    /**
     * Builder interface for setting the running field of CourseProcessResult
     */
    public interface CourseProcessResultBuilderRunning
    {

        /**
         * sets the running field.
         * @param running  The value to assign to the running field
         * @return         The created CourseProcessResult value
         */
        public CourseProcessResult running(CourseInfoList running);

    }

}
