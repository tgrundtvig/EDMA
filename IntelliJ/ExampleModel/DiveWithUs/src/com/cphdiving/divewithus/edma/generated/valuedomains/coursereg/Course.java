package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.Date;
import com.cphdiving.divewithus.edma.generated.valuedomains.Time;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseBuilderImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: Course
 */
public abstract class Course implements Comparable<Course>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(47);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The Course from the terminal
     */
    public static Course fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new CourseImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The Course from the string representation
     */
    public static Course fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new CourseImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The Course read from the stream
     */
    public static Course fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new CourseImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The Course read from the stream
     */
    public static Course fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new CourseImpl(res);
    }

    /**
     * Starts creation of a new Course
     * @return  Builder interface to set the ID field
     */
    public static CourseBuilderID create()
    {
        return new CourseBuilderImpl();
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
    public abstract CourseID ID();

    /**
     * returns the value of the field startDate
     * @return  The value of the field startDate
     */
    public abstract Date startDate();

    /**
     * returns the value of the field startTime
     * @return  The value of the field startTime
     */
    public abstract Time startTime();

    /**
     * returns the value of the field status
     * @return  The value of the field status
     */
    public abstract CourseStatus status();


    /**
     * Builder interface for setting the ID field of Course
     */
    public interface CourseBuilderID
    {

        /**
         * sets the ID field.
         * @param ID  The value to assign to the ID field
         * @return    Builder interface for setting the startDate field
         */
        public CourseBuilderStartDate ID(CourseID ID);

        /**
         * sets the ID field.
         * @param ID  The value to assign to the ID field
         * @return    Builder interface for setting the startDate field
         */
        public CourseBuilderStartDate ID(Long ID) throws InvalidValueException;

        /**
         * sets the fieldID to null.
         * @return  Builder interface for setting the startDate field
         */
        public CourseBuilderStartDate noID();

    }



    /**
     * Builder interface for setting the startDate field of Course
     */
    public interface CourseBuilderStartDate
    {

        /**
         * sets the startDate field.
         * @param startDate  The value to assign to the startDate field
         * @return           Builder interface for setting the startTime field
         */
        public CourseBuilderStartTime startDate(Date startDate);

    }



    /**
     * Builder interface for setting the startTime field of Course
     */
    public interface CourseBuilderStartTime
    {

        /**
         * sets the startTime field.
         * @param startTime  The value to assign to the startTime field
         * @return           Builder interface for setting the status field
         */
        public CourseBuilderStatus startTime(Time startTime);

    }



    /**
     * Builder interface for setting the status field of Course
     */
    public interface CourseBuilderStatus
    {

        /**
         * sets the status field.
         * @param status  The value to assign to the status field
         * @return        The created Course value
         */
        public Course status(CourseStatus status);

        /**
         * sets the status field.
         * @param status  The value to assign to the status field
         * @return        The created Course value
         */
        public Course status(String status) throws InvalidValueException;

    }

}
