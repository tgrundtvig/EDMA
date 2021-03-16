package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfo;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfoList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfoList.CourseInfoListBuilder;
import java.util.ArrayList;
import org.abstractica.edma.valuedomains.IValueInstance;

/**
 * 
 */
public class CourseInfoListBuilderImpl implements CourseInfoListBuilder
{
    private ArrayList<Object> valueList;



    /**
     * Constructor
     */
    public CourseInfoListBuilderImpl()
    {
        valueList = new ArrayList<Object>();
    }

    /**
     * Adds an element to the list
     * @param courseInfo  The element to be added to the list
     * @return            An interface to the builder for chaining method calls
     */
    public CourseInfoListBuilder add(CourseInfo courseInfo)
    {
        if(courseInfo == null) throw new NullPointerException();
        valueList.add(((IValueInstance) courseInfo).edma_getValue());
        return this;
    }

    /**
     * Builds the list with the added elements
     * @return  The builded list
     */
    public CourseInfoList end()
    {
        Object[] res = new Object[valueList.size()];
        int pos = 0;
        for(Object o : valueList)
        {
            res[pos++] = o;
        }
        return new CourseInfoListImpl(CourseInfoListImpl.edma_create(res));
    }
}
