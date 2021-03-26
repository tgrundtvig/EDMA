package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.PosInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseSize.CourseSizeBuilderMax;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseSize.CourseSizeBuilderMin;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.PosIntImpl;
import org.abstractica.edma.valuedomains.IValueInstance;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;

/**
 * 
 */
public class CourseSizeBuilderImpl implements CourseSizeBuilderMax, CourseSizeBuilderMin
{
    private Object[] edma_value;



    /**
     * Constructor
     */
    public CourseSizeBuilderImpl()
    {
        edma_value = new Object[2];
    }

    /**
     * sets the min field.
     * @param min  The value to assign to the min field
     * @return     Builder interface for setting the max field
     */
    public CourseSizeBuilderMax min(PosInt min)
    {
        if(min == null) throw new NullPointerException("The field min in the CourseSize ValueDomain may not be null");
        edma_value[0] = ((IValueInstance) min).edma_getValue();
        return this;
    }

    /**
     * sets the min field.
     * @param min  The value to assign to the min field
     * @return     Builder interface for setting the max field
     */
    public CourseSizeBuilderMax min(Integer min) throws InvalidValueException
    {
        if(min != null) PosIntImpl.edma_validate(min);
        if(min == null) throw new NullPointerException("The field min in the CourseSize ValueDomain may not be null");
        edma_value[0] = PosIntImpl.edma_create(min);
        return this;
    }

    /**
     * sets the max field.
     * @param max  The value to assign to the max field
     * @return     The created CourseSize value
     */
    public CourseSize max(PosInt max) throws InvalidValueException
    {
        if(max == null) throw new NullPointerException("The field max in the CourseSize ValueDomain may not be null");
        edma_value[1] = ((IValueInstance) max).edma_getValue();
        CourseSizeImpl.edma_validate(edma_value);
        return new CourseSizeImpl(CourseSizeImpl.edma_create(edma_value));
    }

    /**
     * sets the max field.
     * @param max  The value to assign to the max field
     * @return     The created CourseSize value
     */
    public CourseSize max(Integer max) throws InvalidValueException
    {
        if(max != null) PosIntImpl.edma_validate(max);
        if(max == null) throw new NullPointerException("The field max in the CourseSize ValueDomain may not be null");
        edma_value[1] = PosIntImpl.edma_create(max);
        CourseSizeImpl.edma_validate(edma_value);
        return new CourseSizeImpl(CourseSizeImpl.edma_create(edma_value));
    }
}
