package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.Mobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.BootSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentInfo;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentInfo.StudentInfoBuilderBootSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentInfo.StudentInfoBuilderEmail;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentInfo.StudentInfoBuilderMobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentInfo.StudentInfoBuilderName;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentInfo.StudentInfoBuilderWetsuitSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.WetsuitSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.BootSizeImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.WetsuitSizeImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.EmailImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.MobileImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.NameImpl;
import org.abstractica.edma.valuedomains.IValueInstance;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;

/**
 * 
 */
public class StudentInfoBuilderImpl implements StudentInfoBuilderBootSize, StudentInfoBuilderEmail, StudentInfoBuilderMobile, StudentInfoBuilderName, StudentInfoBuilderWetsuitSize
{
    private Object[] edma_value;



    /**
     * Constructor
     */
    public StudentInfoBuilderImpl()
    {
        edma_value = new Object[5];
    }

    /**
     * sets the name field.
     * @param name  The value to assign to the name field
     * @return      Builder interface for setting the email field
     */
    public StudentInfoBuilderEmail name(Name name)
    {
        if(name == null) throw new NullPointerException("The field name in StudentInfo may not be null");
        edma_value[0] = ((IValueInstance) name).edma_getValue();
        return this;
    }

    /**
     * sets the name field.
     * @param name  The value to assign to the name field
     * @return      Builder interface for setting the email field
     */
    public StudentInfoBuilderEmail name(String name) throws InvalidValueException
    {
        if(name != null) NameImpl.edma_validate(name);
        if(name == null) throw new NullPointerException();
        edma_value[0] = NameImpl.edma_create(name);
        return this;
    }

    /**
     * sets the email field.
     * @param email  The value to assign to the email field
     * @return       Builder interface for setting the mobile field
     */
    public StudentInfoBuilderMobile email(Email email)
    {
        if(email == null) throw new NullPointerException("The field email in StudentInfo may not be null");
        edma_value[1] = ((IValueInstance) email).edma_getValue();
        return this;
    }

    /**
     * sets the email field.
     * @param email  The value to assign to the email field
     * @return       Builder interface for setting the mobile field
     */
    public StudentInfoBuilderMobile email(String email) throws InvalidValueException
    {
        if(email != null) EmailImpl.edma_validate(email);
        if(email == null) throw new NullPointerException();
        edma_value[1] = EmailImpl.edma_create(email);
        return this;
    }

    /**
     * sets the mobile field.
     * @param mobile  The value to assign to the mobile field
     * @return        Builder interface for setting the wetsuitSize field
     */
    public StudentInfoBuilderWetsuitSize mobile(Mobile mobile)
    {
        if(mobile == null) throw new NullPointerException("The field mobile in StudentInfo may not be null");
        edma_value[2] = ((IValueInstance) mobile).edma_getValue();
        return this;
    }

    /**
     * sets the mobile field.
     * @param mobile  The value to assign to the mobile field
     * @return        Builder interface for setting the wetsuitSize field
     */
    public StudentInfoBuilderWetsuitSize mobile(String mobile) throws InvalidValueException
    {
        if(mobile != null) MobileImpl.edma_validate(mobile);
        if(mobile == null) throw new NullPointerException();
        edma_value[2] = MobileImpl.edma_create(mobile);
        return this;
    }

    /**
     * sets the wetsuitSize field.
     * @param wetsuitSize  The value to assign to the wetsuitSize field
     * @return             Builder interface for setting the bootSize field
     */
    public StudentInfoBuilderBootSize wetsuitSize(WetsuitSize wetsuitSize)
    {
        if(wetsuitSize == null) throw new NullPointerException("The field wetsuitSize in StudentInfo may not be null");
        edma_value[3] = ((IValueInstance) wetsuitSize).edma_getValue();
        return this;
    }

    /**
     * sets the wetsuitSize field.
     * @param wetsuitSize  The value to assign to the wetsuitSize field
     * @return             Builder interface for setting the bootSize field
     */
    public StudentInfoBuilderBootSize wetsuitSize(String wetsuitSize) throws InvalidValueException
    {
        if(wetsuitSize != null) WetsuitSizeImpl.edma_validate(wetsuitSize);
        if(wetsuitSize == null) throw new NullPointerException();
        edma_value[3] = WetsuitSizeImpl.edma_create(wetsuitSize);
        return this;
    }

    /**
     * sets the bootSize field.
     * @param bootSize  The value to assign to the bootSize field
     * @return          The created StudentInfo value
     */
    public StudentInfo bootSize(BootSize bootSize)
    {
        if(bootSize == null) throw new NullPointerException("The field bootSize in StudentInfo may not be null");
        edma_value[4] = ((IValueInstance) bootSize).edma_getValue();
        return new StudentInfoImpl(StudentInfoImpl.edma_create(edma_value));
    }

    /**
     * sets the bootSize field.
     * @param bootSize  The value to assign to the bootSize field
     * @return          The created StudentInfo value
     */
    public StudentInfo bootSize(Integer bootSize) throws InvalidValueException
    {
        if(bootSize != null) BootSizeImpl.edma_validate(bootSize);
        if(bootSize == null) throw new NullPointerException();
        edma_value[4] = BootSizeImpl.edma_create(bootSize);
        return new StudentInfoImpl(StudentInfoImpl.edma_create(edma_value));
    }
}
