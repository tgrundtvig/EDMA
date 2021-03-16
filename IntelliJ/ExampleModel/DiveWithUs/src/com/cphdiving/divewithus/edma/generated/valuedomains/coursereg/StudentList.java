package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.StudentListBuilderImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.StudentListImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: StudentList
 */
public abstract class StudentList implements Comparable<StudentList>, Iterable<Student>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(40);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The StudentList from the terminal
     */
    public static StudentList fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new StudentListImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The StudentList from the string representation
     */
    public static StudentList fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new StudentListImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The StudentList read from the stream
     */
    public static StudentList fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new StudentListImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The StudentList read from the stream
     */
    public static StudentList fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new StudentListImpl(res);
    }

    /**
     * Starts creation of a new StudentList
     * @return  Builder interface to build the list
     */
    public static StudentListBuilder begin()
    {
        return new StudentListBuilderImpl();
    }



    /**
     * Writes this value to a stream
     * @param out  Interface to data output stream
     */
    public abstract void toStream(DataOutput out) throws IOException;

    /**
     * Returns the size of this list
     * @return  the size of this list
     */
    public abstract int size();

    /**
     * Returns the element on a given index in this list
     * @param index  The index of the element to be returned
     * @return       the element on the given index in this list
     */
    public abstract Student get(int index);


    /**
     * Interface to create a list
     */
    public interface StudentListBuilder
    {

        /**
         * Adds an element to the list
         * @param student  The element to be added to the list
         * @return         An interface to the builder for chaining method calls
         */
        public StudentListBuilder add(Student student);

        /**
         * Builds the list with the added elements
         * @return  The builded list
         */
        public StudentList end();

    }

}
