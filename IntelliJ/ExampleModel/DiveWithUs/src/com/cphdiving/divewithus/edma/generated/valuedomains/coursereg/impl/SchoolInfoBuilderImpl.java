package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.SchoolInfo;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.SchoolInfo.SchoolInfoBuilderName;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.NameImpl;
import org.abstractica.edma.valuedomains.IValueInstance;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;

/**
 * 
 */
public class SchoolInfoBuilderImpl implements SchoolInfoBuilderName
{
    private Object[] edma_value;



    /**
     * Constructor
     */
    public SchoolInfoBuilderImpl()
    {
        edma_value = new Object[1];
    }

    /**
     * sets the name field.
     * @param name  The value to assign to the name field
     * @return      The created SchoolInfo value
     */
    public SchoolInfo name(Name name)
    {
        if(name == null) throw new NullPointerException("The field name in the SchoolInfo ValueDomain may not be null");
        edma_value[0] = ((IValueInstance) name).edma_getValue();
        return new SchoolInfoImpl(SchoolInfoImpl.edma_create(edma_value));
    }

    /**
     * sets the name field.
     * @param name  The value to assign to the name field
     * @return      The created SchoolInfo value
     */
    public SchoolInfo name(String name) throws InvalidValueException
    {
        if(name != null) NameImpl.edma_validate(name);
        if(name == null) throw new NullPointerException("The field name in the SchoolInfo ValueDomain may not be null");
        edma_value[0] = NameImpl.edma_create(name);
        return new SchoolInfoImpl(SchoolInfoImpl.edma_create(edma_value));
    }
}
