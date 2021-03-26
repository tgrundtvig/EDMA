package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.Mobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherInfo;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherInfo.TeacherInfoBuilderEmail;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherInfo.TeacherInfoBuilderMobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherInfo.TeacherInfoBuilderName;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.EmailImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.MobileImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.NameImpl;
import org.abstractica.edma.valuedomains.IValueInstance;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;

/**
 * 
 */
public class TeacherInfoBuilderImpl implements TeacherInfoBuilderEmail, TeacherInfoBuilderMobile, TeacherInfoBuilderName
{
    private Object[] edma_value;



    /**
     * Constructor
     */
    public TeacherInfoBuilderImpl()
    {
        edma_value = new Object[3];
    }

    /**
     * sets the name field.
     * @param name  The value to assign to the name field
     * @return      Builder interface for setting the email field
     */
    public TeacherInfoBuilderEmail name(Name name)
    {
        if(name == null) throw new NullPointerException("The field name in the TeacherInfo ValueDomain may not be null");
        edma_value[0] = ((IValueInstance) name).edma_getValue();
        return this;
    }

    /**
     * sets the name field.
     * @param name  The value to assign to the name field
     * @return      Builder interface for setting the email field
     */
    public TeacherInfoBuilderEmail name(String name) throws InvalidValueException
    {
        if(name != null) NameImpl.edma_validate(name);
        if(name == null) throw new NullPointerException("The field name in the TeacherInfo ValueDomain may not be null");
        edma_value[0] = NameImpl.edma_create(name);
        return this;
    }

    /**
     * sets the email field.
     * @param email  The value to assign to the email field
     * @return       Builder interface for setting the mobile field
     */
    public TeacherInfoBuilderMobile email(Email email)
    {
        if(email == null) throw new NullPointerException("The field email in the TeacherInfo ValueDomain may not be null");
        edma_value[1] = ((IValueInstance) email).edma_getValue();
        return this;
    }

    /**
     * sets the email field.
     * @param email  The value to assign to the email field
     * @return       Builder interface for setting the mobile field
     */
    public TeacherInfoBuilderMobile email(String email) throws InvalidValueException
    {
        if(email != null) EmailImpl.edma_validate(email);
        if(email == null) throw new NullPointerException("The field email in the TeacherInfo ValueDomain may not be null");
        edma_value[1] = EmailImpl.edma_create(email);
        return this;
    }

    /**
     * sets the mobile field.
     * @param mobile  The value to assign to the mobile field
     * @return        The created TeacherInfo value
     */
    public TeacherInfo mobile(Mobile mobile)
    {
        if(mobile == null) throw new NullPointerException("The field mobile in the TeacherInfo ValueDomain may not be null");
        edma_value[2] = ((IValueInstance) mobile).edma_getValue();
        return new TeacherInfoImpl(TeacherInfoImpl.edma_create(edma_value));
    }

    /**
     * sets the mobile field.
     * @param mobile  The value to assign to the mobile field
     * @return        The created TeacherInfo value
     */
    public TeacherInfo mobile(String mobile) throws InvalidValueException
    {
        if(mobile != null) MobileImpl.edma_validate(mobile);
        if(mobile == null) throw new NullPointerException("The field mobile in the TeacherInfo ValueDomain may not be null");
        edma_value[2] = MobileImpl.edma_create(mobile);
        return new TeacherInfoImpl(TeacherInfoImpl.edma_create(edma_value));
    }
}
