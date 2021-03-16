package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Student;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentList.StudentListBuilder;
import java.util.ArrayList;
import org.abstractica.edma.valuedomains.IValueInstance;

/**
 * 
 */
public class StudentListBuilderImpl implements StudentListBuilder
{
    private ArrayList<Object> valueList;



    /**
     * Constructor
     */
    public StudentListBuilderImpl()
    {
        valueList = new ArrayList<Object>();
    }

    /**
     * Adds an element to the list
     * @param student  The element to be added to the list
     * @return         An interface to the builder for chaining method calls
     */
    public StudentListBuilder add(Student student)
    {
        if(student == null) throw new NullPointerException();
        valueList.add(((IValueInstance) student).edma_getValue());
        return this;
    }

    /**
     * Builds the list with the added elements
     * @return  The builded list
     */
    public StudentList end()
    {
        Object[] res = new Object[valueList.size()];
        int pos = 0;
        for(Object o : valueList)
        {
            res[pos++] = o;
        }
        return new StudentListImpl(StudentListImpl.edma_create(res));
    }
}
