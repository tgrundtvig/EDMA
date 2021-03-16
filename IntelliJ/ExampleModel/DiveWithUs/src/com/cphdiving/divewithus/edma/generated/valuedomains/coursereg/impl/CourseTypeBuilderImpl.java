package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.PosInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseName;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseType;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseType.CourseTypeBuilderID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseType.CourseTypeBuilderName;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseType.CourseTypeBuilderPrice;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseType.CourseTypeBuilderSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseNameImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseTypeIDImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.PosIntImpl;
import org.abstractica.edma.valuedomains.IValueInstance;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;

/**
 * 
 */
public class CourseTypeBuilderImpl implements CourseTypeBuilderID, CourseTypeBuilderName, CourseTypeBuilderPrice, CourseTypeBuilderSize
{
    private Object[] edma_value;



    /**
     * Constructor
     */
    public CourseTypeBuilderImpl()
    {
        edma_value = new Object[4];
    }

    /**
     * sets the ID field.
     * @param ID  The value to assign to the ID field
     * @return    Builder interface for setting the name field
     */
    public CourseTypeBuilderName ID(CourseTypeID ID)
    {
        edma_value[0] = (ID == null ? null : (((IValueInstance) ID).edma_getValue()));
        return this;
    }

    /**
     * sets the ID field.
     * @param ID  The value to assign to the ID field
     * @return    Builder interface for setting the name field
     */
    public CourseTypeBuilderName ID(Long ID) throws InvalidValueException
    {
        if(ID != null) CourseTypeIDImpl.edma_validate(ID);
        edma_value[0] = (ID == null ? null : (CourseTypeIDImpl.edma_create(ID)));
        return this;
    }

    /**
     * sets the fieldID to null.
     * @return  Builder interface for setting the name field
     */
    public CourseTypeBuilderName noID()
    {
        edma_value[0] = null;
        return this;
    }

    /**
     * sets the name field.
     * @param name  The value to assign to the name field
     * @return      Builder interface for setting the size field
     */
    public CourseTypeBuilderSize name(CourseName name)
    {
        if(name == null) throw new NullPointerException("The field name in CourseType may not be null");
        edma_value[1] = ((IValueInstance) name).edma_getValue();
        return this;
    }

    /**
     * sets the name field.
     * @param name  The value to assign to the name field
     * @return      Builder interface for setting the size field
     */
    public CourseTypeBuilderSize name(String name) throws InvalidValueException
    {
        if(name != null) CourseNameImpl.edma_validate(name);
        if(name == null) throw new NullPointerException();
        edma_value[1] = CourseNameImpl.edma_create(name);
        return this;
    }

    /**
     * sets the size field.
     * @param size  The value to assign to the size field
     * @return      Builder interface for setting the price field
     */
    public CourseTypeBuilderPrice size(CourseSize size)
    {
        if(size == null) throw new NullPointerException("The field size in CourseType may not be null");
        edma_value[2] = ((IValueInstance) size).edma_getValue();
        return this;
    }

    /**
     * sets the price field.
     * @param price  The value to assign to the price field
     * @return       The created CourseType value
     */
    public CourseType price(PosInt price)
    {
        if(price == null) throw new NullPointerException("The field price in CourseType may not be null");
        edma_value[3] = ((IValueInstance) price).edma_getValue();
        return new CourseTypeImpl(CourseTypeImpl.edma_create(edma_value));
    }

    /**
     * sets the price field.
     * @param price  The value to assign to the price field
     * @return       The created CourseType value
     */
    public CourseType price(Integer price) throws InvalidValueException
    {
        if(price != null) PosIntImpl.edma_validate(price);
        if(price == null) throw new NullPointerException();
        edma_value[3] = PosIntImpl.edma_create(price);
        return new CourseTypeImpl(CourseTypeImpl.edma_create(edma_value));
    }
}
