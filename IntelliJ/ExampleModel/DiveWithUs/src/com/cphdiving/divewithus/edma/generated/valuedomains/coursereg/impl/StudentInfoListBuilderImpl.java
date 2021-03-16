package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentInfo;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentInfoList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentInfoList.StudentInfoListBuilder;
import java.util.ArrayList;
import org.abstractica.edma.valuedomains.IValueInstance;

/**
 * 
 */
public class StudentInfoListBuilderImpl implements StudentInfoListBuilder
{
    private ArrayList<Object> valueList;



    /**
     * Constructor
     */
    public StudentInfoListBuilderImpl()
    {
        valueList = new ArrayList<Object>();
    }

    /**
     * Adds an element to the list
     * @param studentInfo  The element to be added to the list
     * @return             An interface to the builder for chaining method calls
     */
    public StudentInfoListBuilder add(StudentInfo studentInfo)
    {
        if(studentInfo == null) throw new NullPointerException();
        valueList.add(((IValueInstance) studentInfo).edma_getValue());
        return this;
    }

    /**
     * Builds the list with the added elements
     * @return  The builded list
     */
    public StudentInfoList end()
    {
        Object[] res = new Object[valueList.size()];
        int pos = 0;
        for(Object o : valueList)
        {
            res[pos++] = o;
        }
        return new StudentInfoListImpl(StudentInfoListImpl.edma_create(res));
    }
}
