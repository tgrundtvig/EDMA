package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.BootSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Student;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Student.StudentBuilderBootSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Student.StudentBuilderID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Student.StudentBuilderWetsuitSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.WetsuitSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.BootSizeImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.StudentIDImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.WetsuitSizeImpl;
import org.abstractica.edma.valuedomains.IValueInstance;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;

/**
 * 
 */
public class StudentBuilderImpl implements StudentBuilderBootSize, StudentBuilderID, StudentBuilderWetsuitSize
{
    private Object[] edma_value;



    /**
     * Constructor
     */
    public StudentBuilderImpl()
    {
        edma_value = new Object[3];
    }

    /**
     * sets the ID field.
     * @param ID  The value to assign to the ID field
     * @return    Builder interface for setting the wetsuitSize field
     */
    public StudentBuilderWetsuitSize ID(StudentID ID)
    {
        edma_value[0] = (ID == null ? null : (((IValueInstance) ID).edma_getValue()));
        return this;
    }

    /**
     * sets the ID field.
     * @param ID  The value to assign to the ID field
     * @return    Builder interface for setting the wetsuitSize field
     */
    public StudentBuilderWetsuitSize ID(Long ID) throws InvalidValueException
    {
        if(ID != null) StudentIDImpl.edma_validate(ID);
        edma_value[0] = (ID == null ? null : (StudentIDImpl.edma_create(ID)));
        return this;
    }

    /**
     * sets the fieldID to null.
     * @return  Builder interface for setting the wetsuitSize field
     */
    public StudentBuilderWetsuitSize noID()
    {
        edma_value[0] = null;
        return this;
    }

    /**
     * sets the wetsuitSize field.
     * @param wetsuitSize  The value to assign to the wetsuitSize field
     * @return             Builder interface for setting the bootSize field
     */
    public StudentBuilderBootSize wetsuitSize(WetsuitSize wetsuitSize)
    {
        if(wetsuitSize == null) throw new NullPointerException("The field wetsuitSize in the Student ValueDomain may not be null");
        edma_value[1] = ((IValueInstance) wetsuitSize).edma_getValue();
        return this;
    }

    /**
     * sets the wetsuitSize field.
     * @param wetsuitSize  The value to assign to the wetsuitSize field
     * @return             Builder interface for setting the bootSize field
     */
    public StudentBuilderBootSize wetsuitSize(String wetsuitSize) throws InvalidValueException
    {
        if(wetsuitSize != null) WetsuitSizeImpl.edma_validate(wetsuitSize);
        if(wetsuitSize == null) throw new NullPointerException("The field wetsuitSize in the Student ValueDomain may not be null");
        edma_value[1] = WetsuitSizeImpl.edma_create(wetsuitSize);
        return this;
    }

    /**
     * sets the bootSize field.
     * @param bootSize  The value to assign to the bootSize field
     * @return          The created Student value
     */
    public Student bootSize(BootSize bootSize)
    {
        if(bootSize == null) throw new NullPointerException("The field bootSize in the Student ValueDomain may not be null");
        edma_value[2] = ((IValueInstance) bootSize).edma_getValue();
        return new StudentImpl(StudentImpl.edma_create(edma_value));
    }

    /**
     * sets the bootSize field.
     * @param bootSize  The value to assign to the bootSize field
     * @return          The created Student value
     */
    public Student bootSize(Integer bootSize) throws InvalidValueException
    {
        if(bootSize != null) BootSizeImpl.edma_validate(bootSize);
        if(bootSize == null) throw new NullPointerException("The field bootSize in the Student ValueDomain may not be null");
        edma_value[2] = BootSizeImpl.edma_create(bootSize);
        return new StudentImpl(StudentImpl.edma_create(edma_value));
    }
}
