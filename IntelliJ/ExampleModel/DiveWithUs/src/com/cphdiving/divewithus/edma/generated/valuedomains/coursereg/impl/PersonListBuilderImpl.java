package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Person;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonList.PersonListBuilder;
import java.util.ArrayList;
import org.abstractica.edma.valuedomains.IValueInstance;

/**
 * 
 */
public class PersonListBuilderImpl implements PersonListBuilder
{
    private ArrayList<Object> valueList;



    /**
     * Constructor
     */
    public PersonListBuilderImpl()
    {
        valueList = new ArrayList<Object>();
    }

    /**
     * Adds an element to the list
     * @param person  The element to be added to the list
     * @return        An interface to the builder for chaining method calls
     */
    public PersonListBuilder add(Person person)
    {
        if(person == null) throw new NullPointerException();
        valueList.add(((IValueInstance) person).edma_getValue());
        return this;
    }

    /**
     * Builds the list with the added elements
     * @return  The builded list
     */
    public PersonList end()
    {
        Object[] res = new Object[valueList.size()];
        int pos = 0;
        for(Object o : valueList)
        {
            res[pos++] = o;
        }
        return new PersonListImpl(PersonListImpl.edma_create(res));
    }
}
