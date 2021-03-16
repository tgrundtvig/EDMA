package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.Mobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.BootSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentInfo;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.WetsuitSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.BootSizeImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.WetsuitSizeImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.EmailImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.MobileImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.NameImpl;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.IValueInstance;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;

/**
 * The implementation of StudentInfo
 */
public class StudentInfoImpl extends StudentInfo implements IValueInstance
{
    private int edma_hash;
    private Object[] value;



    /**
     * Check if a value is valid
     * @param value  The value to validate
     */
    public static void edma_validate(Object value) throws InvalidValueException
    {
        edma_domain.validate(value, EDMA_ExternalConstraints.instance);
    }

    /**
     * create value without checking
     * @param value  The value to check and create
     * @return       <tt>true</tt> The new created value
     */
    public static Object edma_create(Object value)
    {
        return edma_domain.newValue(value);
    }

    /**
     * Constructor
     * @param o  The Object that represents this struct value
     */
    public StudentInfoImpl(Object o)
    {
        edma_hash = 0;
        value = (Object[]) o;
    }

    /**
     * Gets the value domain for this value instance
     * @return  The value domain for this value instance
     */
    public IMetaValueDomain edma_getDomain()
    {
        return edma_domain;
    }

    /**
     * Access to the general Object value
     * @return  The value as a general Object
     */
    public Object edma_getValue()
    {
        return value;
    }

    /**
     * Returns <tt>true</tt> if this value equals the given value
     * @param o  Object to test equality with
     * @return   <tt>true</tt> if this value equals the given value
     */
    public boolean equals(Object o)
    {
        if(!(o instanceof IValueInstance)) return false;
        IValueInstance inst = (IValueInstance) o;
        if(26 != inst.edma_getDomain().getIndex()) return false;
        return edma_domain.valueEqual(value, inst.edma_getValue());
    }

    /**
     * Gets the value hash for this value instance
     * @return  The hash for this value instance
     */
    public int hashCode()
    {
        if(edma_hash == 0) edma_hash = edma_domain.valueHashCode(value);
        return edma_hash;
    }

    /**
     * Returns this value instance as a string
     * @return  this value instance as a string
     */
    public String toString()
    {
        return edma_domain.valueToString(value);
    }

    /**
     * Compare this StudentInfo to another StudentInfo
     * @param studentInfo  The StudentInfo to compare with
     * @return             A negative integer, zero, or a positive integer as
     *                     this StudentInfo is less than, equal to, or greater
     *                     than the specified StudentInfo
     */
    public int compareTo(StudentInfo studentInfo)
    {
        return edma_domain.valueCompare(value, ((StudentInfoImpl) studentInfo).value);
    }

    /**
     * Writes this value to a stream
     * @param out  Interface to data output stream
     */
    public void toStream(DataOutput out) throws IOException
    {
        edma_domain.writeValue(((IValueInstance) this).edma_getValue(), out);
    }

    /**
     * returns the value of the field name
     * @return  The value of the field name
     */
    public Name name()
    {
        return new NameImpl(value[0]);
    }

    /**
     * returns the value of the field email
     * @return  The value of the field email
     */
    public Email email()
    {
        return new EmailImpl(value[1]);
    }

    /**
     * returns the value of the field mobile
     * @return  The value of the field mobile
     */
    public Mobile mobile()
    {
        return new MobileImpl(value[2]);
    }

    /**
     * returns the value of the field wetsuitSize
     * @return  The value of the field wetsuitSize
     */
    public WetsuitSize wetsuitSize()
    {
        return new WetsuitSizeImpl(value[3]);
    }

    /**
     * returns the value of the field bootSize
     * @return  The value of the field bootSize
     */
    public BootSize bootSize()
    {
        return new BootSizeImpl(value[4]);
    }
}
