package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.PosInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseSizeBuilderImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseSizeImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: CourseSize
 */
public abstract class CourseSize implements Comparable<CourseSize>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(24);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The CourseSize from the terminal
     */
    public static CourseSize fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new CourseSizeImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The CourseSize from the string representation
     */
    public static CourseSize fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new CourseSizeImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The CourseSize read from the stream
     */
    public static CourseSize fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new CourseSizeImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The CourseSize read from the stream
     */
    public static CourseSize fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new CourseSizeImpl(res);
    }

    /**
     * Starts creation of a new CourseSize
     * @return  Builder interface to set the min field
     */
    public static CourseSizeBuilderMin create()
    {
        return new CourseSizeBuilderImpl();
    }



    /**
     * Writes this value to a stream
     * @param out  Interface to data output stream
     */
    public abstract void toStream(DataOutput out) throws IOException;

    /**
     * returns the value of the field min
     * @return  The value of the field min
     */
    public abstract PosInt min();

    /**
     * returns the value of the field max
     * @return  The value of the field max
     */
    public abstract PosInt max();


    /**
     * Builder interface for setting the min field of CourseSize
     */
    public interface CourseSizeBuilderMin
    {

        /**
         * sets the min field.
         * @param min  The value to assign to the min field
         * @return     Builder interface for setting the max field
         */
        public CourseSizeBuilderMax min(PosInt min);

        /**
         * sets the min field.
         * @param min  The value to assign to the min field
         * @return     Builder interface for setting the max field
         */
        public CourseSizeBuilderMax min(Integer min) throws InvalidValueException;

    }



    /**
     * Builder interface for setting the max field of CourseSize
     */
    public interface CourseSizeBuilderMax
    {

        /**
         * sets the max field.
         * @param max  The value to assign to the max field
         * @return     The created CourseSize value
         */
        public CourseSize max(PosInt max) throws InvalidValueException;

        /**
         * sets the max field.
         * @param max  The value to assign to the max field
         * @return     The created CourseSize value
         */
        public CourseSize max(Integer max) throws InvalidValueException;

    }

}
