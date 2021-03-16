package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfoList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseProcessResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseProcessResult.CourseProcessResultBuilderCanceled;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseProcessResult.CourseProcessResultBuilderRunning;
import org.abstractica.edma.valuedomains.IValueInstance;

/**
 * 
 */
public class CourseProcessResultBuilderImpl implements CourseProcessResultBuilderCanceled, CourseProcessResultBuilderRunning
{
    private Object[] edma_value;



    /**
     * Constructor
     */
    public CourseProcessResultBuilderImpl()
    {
        edma_value = new Object[2];
    }

    /**
     * sets the canceled field.
     * @param canceled  The value to assign to the canceled field
     * @return          Builder interface for setting the running field
     */
    public CourseProcessResultBuilderRunning canceled(CourseInfoList canceled)
    {
        if(canceled == null) throw new NullPointerException("The field canceled in CourseProcessResult may not be null");
        edma_value[0] = ((IValueInstance) canceled).edma_getValue();
        return this;
    }

    /**
     * sets the running field.
     * @param running  The value to assign to the running field
     * @return         The created CourseProcessResult value
     */
    public CourseProcessResult running(CourseInfoList running)
    {
        if(running == null) throw new NullPointerException("The field running in CourseProcessResult may not be null");
        edma_value[1] = ((IValueInstance) running).edma_getValue();
        return new CourseProcessResultImpl(CourseProcessResultImpl.edma_create(edma_value));
    }
}
