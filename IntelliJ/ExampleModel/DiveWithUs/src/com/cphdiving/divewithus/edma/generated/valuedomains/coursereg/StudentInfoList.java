package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.StudentInfoListBuilderImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.StudentInfoListImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: StudentInfoList
 */
public abstract class StudentInfoList implements Comparable<StudentInfoList>, Iterable<StudentInfo>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(28);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The StudentInfoList from the terminal
     */
    public static StudentInfoList fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new StudentInfoListImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The StudentInfoList from the string representation
     */
    public static StudentInfoList fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new StudentInfoListImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The StudentInfoList read from the stream
     */
    public static StudentInfoList fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new StudentInfoListImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The StudentInfoList read from the stream
     */
    public static StudentInfoList fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new StudentInfoListImpl(res);
    }

    /**
     * Starts creation of a new StudentInfoList
     * @return  Builder interface to build the list
     */
    public static StudentInfoListBuilder begin()
    {
        return new StudentInfoListBuilderImpl();
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
    public abstract StudentInfo get(int index);


    /**
     * Interface to create a list
     */
    public interface StudentInfoListBuilder
    {

        /**
         * Adds an element to the list
         * @param studentInfo  The element to be added to the list
         * @return             An interface to the builder for chaining method
         *                     calls
         */
        public StudentInfoListBuilder add(StudentInfo studentInfo);

        /**
         * Builds the list with the added elements
         * @return  The builded list
         */
        public StudentInfoList end();

    }

}
