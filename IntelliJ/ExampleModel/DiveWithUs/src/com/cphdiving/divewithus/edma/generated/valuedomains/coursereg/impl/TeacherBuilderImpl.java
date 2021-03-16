package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.PosInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Teacher;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Teacher.TeacherBuilderID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Teacher.TeacherBuilderSalary;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.TeacherIDImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.PosIntImpl;
import org.abstractica.edma.valuedomains.IValueInstance;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;

/**
 * 
 */
public class TeacherBuilderImpl implements TeacherBuilderID, TeacherBuilderSalary
{
    private Object[] edma_value;



    /**
     * Constructor
     */
    public TeacherBuilderImpl()
    {
        edma_value = new Object[2];
    }

    /**
     * sets the ID field.
     * @param ID  The value to assign to the ID field
     * @return    Builder interface for setting the salary field
     */
    public TeacherBuilderSalary ID(TeacherID ID)
    {
        edma_value[0] = (ID == null ? null : (((IValueInstance) ID).edma_getValue()));
        return this;
    }

    /**
     * sets the ID field.
     * @param ID  The value to assign to the ID field
     * @return    Builder interface for setting the salary field
     */
    public TeacherBuilderSalary ID(Long ID) throws InvalidValueException
    {
        if(ID != null) TeacherIDImpl.edma_validate(ID);
        edma_value[0] = (ID == null ? null : (TeacherIDImpl.edma_create(ID)));
        return this;
    }

    /**
     * sets the fieldID to null.
     * @return  Builder interface for setting the salary field
     */
    public TeacherBuilderSalary noID()
    {
        edma_value[0] = null;
        return this;
    }

    /**
     * sets the salary field.
     * @param salary  The value to assign to the salary field
     * @return        The created Teacher value
     */
    public Teacher salary(PosInt salary)
    {
        if(salary == null) throw new NullPointerException("The field salary in Teacher may not be null");
        edma_value[1] = ((IValueInstance) salary).edma_getValue();
        return new TeacherImpl(TeacherImpl.edma_create(edma_value));
    }

    /**
     * sets the salary field.
     * @param salary  The value to assign to the salary field
     * @return        The created Teacher value
     */
    public Teacher salary(Integer salary) throws InvalidValueException
    {
        if(salary != null) PosIntImpl.edma_validate(salary);
        if(salary == null) throw new NullPointerException();
        edma_value[1] = PosIntImpl.edma_create(salary);
        return new TeacherImpl(TeacherImpl.edma_create(edma_value));
    }
}
