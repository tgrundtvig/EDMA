package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseType;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeList.CourseTypeListBuilder;
import java.util.ArrayList;
import org.abstractica.edma.valuedomains.IValueInstance;

/**
 * 
 */
public class CourseTypeListBuilderImpl implements CourseTypeListBuilder
{
    private ArrayList<Object> valueList;



    /**
     * Constructor
     */
    public CourseTypeListBuilderImpl()
    {
        valueList = new ArrayList<Object>();
    }

    /**
     * Adds an element to the list
     * @param courseType  The element to be added to the list
     * @return            An interface to the builder for chaining method calls
     */
    public CourseTypeListBuilder add(CourseType courseType)
    {
        if(courseType == null) throw new NullPointerException();
        valueList.add(((IValueInstance) courseType).edma_getValue());
        return this;
    }

    /**
     * Builds the list with the added elements
     * @return  The builded list
     */
    public CourseTypeList end()
    {
        Object[] res = new Object[valueList.size()];
        int pos = 0;
        for(Object o : valueList)
        {
            res[pos++] = o;
        }
        return new CourseTypeListImpl(CourseTypeListImpl.edma_create(res));
    }
}
