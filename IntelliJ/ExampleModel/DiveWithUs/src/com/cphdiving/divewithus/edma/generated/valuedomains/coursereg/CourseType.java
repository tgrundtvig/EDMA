package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.PosInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseName;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseTypeBuilderImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseTypeImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: CourseType
 */
public abstract class CourseType implements Comparable<CourseType>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(44);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The CourseType from the terminal
     */
    public static CourseType fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new CourseTypeImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The CourseType from the string representation
     */
    public static CourseType fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new CourseTypeImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The CourseType read from the stream
     */
    public static CourseType fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new CourseTypeImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The CourseType read from the stream
     */
    public static CourseType fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new CourseTypeImpl(res);
    }

    /**
     * Starts creation of a new CourseType
     * @return  Builder interface to set the ID field
     */
    public static CourseTypeBuilderID create()
    {
        return new CourseTypeBuilderImpl();
    }



    /**
     * Writes this value to a stream
     * @param out  Interface to data output stream
     */
    public abstract void toStream(DataOutput out) throws IOException;

    /**
     * returns the value of the field ID
     * @return  The value of the field ID
     */
    public abstract CourseTypeID ID();

    /**
     * returns the value of the field name
     * @return  The value of the field name
     */
    public abstract CourseName name();

    /**
     * returns the value of the field size
     * @return  The value of the field size
     */
    public abstract CourseSize size();

    /**
     * returns the value of the field price
     * @return  The value of the field price
     */
    public abstract PosInt price();


    /**
     * Builder interface for setting the ID field of CourseType
     */
    public interface CourseTypeBuilderID
    {

        /**
         * sets the ID field.
         * @param ID  The value to assign to the ID field
         * @return    Builder interface for setting the name field
         */
        public CourseTypeBuilderName ID(CourseTypeID ID);

        /**
         * sets the ID field.
         * @param ID  The value to assign to the ID field
         * @return    Builder interface for setting the name field
         */
        public CourseTypeBuilderName ID(Long ID) throws InvalidValueException;

        /**
         * sets the fieldID to null.
         * @return  Builder interface for setting the name field
         */
        public CourseTypeBuilderName noID();

    }



    /**
     * Builder interface for setting the name field of CourseType
     */
    public interface CourseTypeBuilderName
    {

        /**
         * sets the name field.
         * @param name  The value to assign to the name field
         * @return      Builder interface for setting the size field
         */
        public CourseTypeBuilderSize name(CourseName name);

        /**
         * sets the name field.
         * @param name  The value to assign to the name field
         * @return      Builder interface for setting the size field
         */
        public CourseTypeBuilderSize name(String name) throws InvalidValueException;

    }



    /**
     * Builder interface for setting the size field of CourseType
     */
    public interface CourseTypeBuilderSize
    {

        /**
         * sets the size field.
         * @param size  The value to assign to the size field
         * @return      Builder interface for setting the price field
         */
        public CourseTypeBuilderPrice size(CourseSize size);

    }



    /**
     * Builder interface for setting the price field of CourseType
     */
    public interface CourseTypeBuilderPrice
    {

        /**
         * sets the price field.
         * @param price  The value to assign to the price field
         * @return       The created CourseType value
         */
        public CourseType price(PosInt price);

        /**
         * sets the price field.
         * @param price  The value to assign to the price field
         * @return       The created CourseType value
         */
        public CourseType price(Integer price) throws InvalidValueException;

    }

}
