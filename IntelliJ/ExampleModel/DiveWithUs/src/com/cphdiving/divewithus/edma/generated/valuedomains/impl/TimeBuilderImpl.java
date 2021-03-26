package com.cphdiving.divewithus.edma.generated.valuedomains.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.Hour;
import com.cphdiving.divewithus.edma.generated.valuedomains.Millisecond;
import com.cphdiving.divewithus.edma.generated.valuedomains.Minute;
import com.cphdiving.divewithus.edma.generated.valuedomains.Second;
import com.cphdiving.divewithus.edma.generated.valuedomains.Time;
import com.cphdiving.divewithus.edma.generated.valuedomains.Time.TimeBuilderHour;
import com.cphdiving.divewithus.edma.generated.valuedomains.Time.TimeBuilderMilliseconds;
import com.cphdiving.divewithus.edma.generated.valuedomains.Time.TimeBuilderMin;
import com.cphdiving.divewithus.edma.generated.valuedomains.Time.TimeBuilderSecond;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.HourImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.MillisecondImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.MinuteImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.SecondImpl;
import org.abstractica.edma.valuedomains.IValueInstance;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;

/**
 * 
 */
public class TimeBuilderImpl implements TimeBuilderHour, TimeBuilderMilliseconds, TimeBuilderMin, TimeBuilderSecond
{
    private Object[] edma_value;



    /**
     * Constructor
     */
    public TimeBuilderImpl()
    {
        edma_value = new Object[4];
    }

    /**
     * sets the hour field.
     * @param hour  The value to assign to the hour field
     * @return      Builder interface for setting the min field
     */
    public TimeBuilderMin hour(Hour hour)
    {
        if(hour == null) throw new NullPointerException("The field hour in the Time ValueDomain may not be null");
        edma_value[0] = ((IValueInstance) hour).edma_getValue();
        return this;
    }

    /**
     * sets the hour field.
     * @param hour  The value to assign to the hour field
     * @return      Builder interface for setting the min field
     */
    public TimeBuilderMin hour(Integer hour) throws InvalidValueException
    {
        if(hour != null) HourImpl.edma_validate(hour);
        if(hour == null) throw new NullPointerException("The field hour in the Time ValueDomain may not be null");
        edma_value[0] = HourImpl.edma_create(hour);
        return this;
    }

    /**
     * sets the min field.
     * @param min  The value to assign to the min field
     * @return     Builder interface for setting the second field
     */
    public TimeBuilderSecond min(Minute min)
    {
        if(min == null) throw new NullPointerException("The field min in the Time ValueDomain may not be null");
        edma_value[1] = ((IValueInstance) min).edma_getValue();
        return this;
    }

    /**
     * sets the min field.
     * @param min  The value to assign to the min field
     * @return     Builder interface for setting the second field
     */
    public TimeBuilderSecond min(Integer min) throws InvalidValueException
    {
        if(min != null) MinuteImpl.edma_validate(min);
        if(min == null) throw new NullPointerException("The field min in the Time ValueDomain may not be null");
        edma_value[1] = MinuteImpl.edma_create(min);
        return this;
    }

    /**
     * sets the second field.
     * @param second  The value to assign to the second field
     * @return        Builder interface for setting the milliseconds field
     */
    public TimeBuilderMilliseconds second(Second second)
    {
        if(second == null) throw new NullPointerException("The field second in the Time ValueDomain may not be null");
        edma_value[2] = ((IValueInstance) second).edma_getValue();
        return this;
    }

    /**
     * sets the second field.
     * @param second  The value to assign to the second field
     * @return        Builder interface for setting the milliseconds field
     */
    public TimeBuilderMilliseconds second(Integer second) throws InvalidValueException
    {
        if(second != null) SecondImpl.edma_validate(second);
        if(second == null) throw new NullPointerException("The field second in the Time ValueDomain may not be null");
        edma_value[2] = SecondImpl.edma_create(second);
        return this;
    }

    /**
     * sets the milliseconds field.
     * @param milliseconds  The value to assign to the milliseconds field
     * @return              The created Time value
     */
    public Time milliseconds(Millisecond milliseconds)
    {
        if(milliseconds == null) throw new NullPointerException("The field milliseconds in the Time ValueDomain may not be null");
        edma_value[3] = ((IValueInstance) milliseconds).edma_getValue();
        return new TimeImpl(TimeImpl.edma_create(edma_value));
    }

    /**
     * sets the milliseconds field.
     * @param milliseconds  The value to assign to the milliseconds field
     * @return              The created Time value
     */
    public Time milliseconds(Integer milliseconds) throws InvalidValueException
    {
        if(milliseconds != null) MillisecondImpl.edma_validate(milliseconds);
        if(milliseconds == null) throw new NullPointerException("The field milliseconds in the Time ValueDomain may not be null");
        edma_value[3] = MillisecondImpl.edma_create(milliseconds);
        return new TimeImpl(TimeImpl.edma_create(edma_value));
    }
}
