package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.Date;
import com.cphdiving.divewithus.edma.generated.valuedomains.Time;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Course;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Course.CourseBuilderID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Course.CourseBuilderStartDate;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Course.CourseBuilderStartTime;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Course.CourseBuilderStatus;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseIDImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseStatusImpl;
import org.abstractica.edma.valuedomains.IValueInstance;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;

/**
 * 
 */
public class CourseBuilderImpl implements CourseBuilderID, CourseBuilderStartDate, CourseBuilderStartTime, CourseBuilderStatus
{
    private Object[] edma_value;



    /**
     * Constructor
     */
    public CourseBuilderImpl()
    {
        edma_value = new Object[4];
    }

    /**
     * sets the ID field.
     * @param ID  The value to assign to the ID field
     * @return    Builder interface for setting the startDate field
     */
    public CourseBuilderStartDate ID(CourseID ID)
    {
        edma_value[0] = (ID == null ? null : (((IValueInstance) ID).edma_getValue()));
        return this;
    }

    /**
     * sets the ID field.
     * @param ID  The value to assign to the ID field
     * @return    Builder interface for setting the startDate field
     */
    public CourseBuilderStartDate ID(Long ID) throws InvalidValueException
    {
        if(ID != null) CourseIDImpl.edma_validate(ID);
        edma_value[0] = (ID == null ? null : (CourseIDImpl.edma_create(ID)));
        return this;
    }

    /**
     * sets the fieldID to null.
     * @return  Builder interface for setting the startDate field
     */
    public CourseBuilderStartDate noID()
    {
        edma_value[0] = null;
        return this;
    }

    /**
     * sets the startDate field.
     * @param startDate  The value to assign to the startDate field
     * @return           Builder interface for setting the startTime field
     */
    public CourseBuilderStartTime startDate(Date startDate)
    {
        if(startDate == null) throw new NullPointerException("The field startDate in the Course ValueDomain may not be null");
        edma_value[1] = ((IValueInstance) startDate).edma_getValue();
        return this;
    }

    /**
     * sets the startTime field.
     * @param startTime  The value to assign to the startTime field
     * @return           Builder interface for setting the status field
     */
    public CourseBuilderStatus startTime(Time startTime)
    {
        if(startTime == null) throw new NullPointerException("The field startTime in the Course ValueDomain may not be null");
        edma_value[2] = ((IValueInstance) startTime).edma_getValue();
        return this;
    }

    /**
     * sets the status field.
     * @param status  The value to assign to the status field
     * @return        The created Course value
     */
    public Course status(CourseStatus status)
    {
        if(status == null) throw new NullPointerException("The field status in the Course ValueDomain may not be null");
        edma_value[3] = ((IValueInstance) status).edma_getValue();
        return new CourseImpl(CourseImpl.edma_create(edma_value));
    }

    /**
     * sets the status field.
     * @param status  The value to assign to the status field
     * @return        The created Course value
     */
    public Course status(String status) throws InvalidValueException
    {
        if(status != null) CourseStatusImpl.edma_validate(status);
        if(status == null) throw new NullPointerException("The field status in the Course ValueDomain may not be null");
        edma_value[3] = CourseStatusImpl.edma_create(status);
        return new CourseImpl(CourseImpl.edma_create(edma_value));
    }
}
