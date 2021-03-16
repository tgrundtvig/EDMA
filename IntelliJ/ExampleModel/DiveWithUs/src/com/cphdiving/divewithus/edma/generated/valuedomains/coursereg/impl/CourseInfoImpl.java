package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.DateAndTime;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfo;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseName;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentInfoList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherInfo;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseIDImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseNameImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseStatusImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.StudentInfoListImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.TeacherInfoImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.DateAndTimeImpl;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.IValueInstance;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;

/**
 * The implementation of CourseInfo
 */
public class CourseInfoImpl extends CourseInfo implements IValueInstance
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
    public CourseInfoImpl(Object o)
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
        if(29 != inst.edma_getDomain().getIndex()) return false;
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
     * Compare this CourseInfo to another CourseInfo
     * @param courseInfo  The CourseInfo to compare with
     * @return            A negative integer, zero, or a positive integer as
     *                    this CourseInfo is less than, equal to, or greater
     *                    than the specified CourseInfo
     */
    public int compareTo(CourseInfo courseInfo)
    {
        return edma_domain.valueCompare(value, ((CourseInfoImpl) courseInfo).value);
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
     * returns the value of the field id
     * @return  The value of the field id
     */
    public CourseID id()
    {
        return new CourseIDImpl(value[0]);
    }

    /**
     * returns the value of the field start
     * @return  The value of the field start
     */
    public DateAndTime start()
    {
        return new DateAndTimeImpl(value[1]);
    }

    /**
     * returns the value of the field type
     * @return  The value of the field type
     */
    public CourseName type()
    {
        return new CourseNameImpl(value[2]);
    }

    /**
     * returns the value of the field status
     * @return  The value of the field status
     */
    public CourseStatus status()
    {
        return new CourseStatusImpl(value[3]);
    }

    /**
     * returns the value of the field students
     * @return  The value of the field students
     */
    public StudentInfoList students()
    {
        return new StudentInfoListImpl(value[4]);
    }

    /**
     * returns the value of the field teacher
     * @return  The value of the field teacher
     */
    public TeacherInfo teacher()
    {
        if(value[5] == null) return null;
        return new TeacherInfoImpl(value[5]);
    }
}
