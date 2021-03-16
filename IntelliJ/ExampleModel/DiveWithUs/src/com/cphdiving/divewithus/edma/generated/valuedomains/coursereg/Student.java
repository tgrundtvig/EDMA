package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.BootSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.WetsuitSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.StudentBuilderImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.StudentImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: Student
 */
public abstract class Student implements Comparable<Student>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(41);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The Student from the terminal
     */
    public static Student fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new StudentImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The Student from the string representation
     */
    public static Student fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new StudentImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The Student read from the stream
     */
    public static Student fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new StudentImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The Student read from the stream
     */
    public static Student fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new StudentImpl(res);
    }

    /**
     * Starts creation of a new Student
     * @return  Builder interface to set the ID field
     */
    public static StudentBuilderID create()
    {
        return new StudentBuilderImpl();
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
    public abstract StudentID ID();

    /**
     * returns the value of the field wetsuitSize
     * @return  The value of the field wetsuitSize
     */
    public abstract WetsuitSize wetsuitSize();

    /**
     * returns the value of the field bootSize
     * @return  The value of the field bootSize
     */
    public abstract BootSize bootSize();


    /**
     * Builder interface for setting the ID field of Student
     */
    public interface StudentBuilderID
    {

        /**
         * sets the ID field.
         * @param ID  The value to assign to the ID field
         * @return    Builder interface for setting the wetsuitSize field
         */
        public StudentBuilderWetsuitSize ID(StudentID ID);

        /**
         * sets the ID field.
         * @param ID  The value to assign to the ID field
         * @return    Builder interface for setting the wetsuitSize field
         */
        public StudentBuilderWetsuitSize ID(Long ID) throws InvalidValueException;

        /**
         * sets the fieldID to null.
         * @return  Builder interface for setting the wetsuitSize field
         */
        public StudentBuilderWetsuitSize noID();

    }



    /**
     * Builder interface for setting the wetsuitSize field of Student
     */
    public interface StudentBuilderWetsuitSize
    {

        /**
         * sets the wetsuitSize field.
         * @param wetsuitSize  The value to assign to the wetsuitSize field
         * @return             Builder interface for setting the bootSize field
         */
        public StudentBuilderBootSize wetsuitSize(WetsuitSize wetsuitSize);

        /**
         * sets the wetsuitSize field.
         * @param wetsuitSize  The value to assign to the wetsuitSize field
         * @return             Builder interface for setting the bootSize field
         */
        public StudentBuilderBootSize wetsuitSize(String wetsuitSize) throws InvalidValueException;

    }



    /**
     * Builder interface for setting the bootSize field of Student
     */
    public interface StudentBuilderBootSize
    {

        /**
         * sets the bootSize field.
         * @param bootSize  The value to assign to the bootSize field
         * @return          The created Student value
         */
        public Student bootSize(BootSize bootSize);

        /**
         * sets the bootSize field.
         * @param bootSize  The value to assign to the bootSize field
         * @return          The created Student value
         */
        public Student bootSize(Integer bootSize) throws InvalidValueException;

    }

}
