package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl;

import com.cphdiving.divewithus.edma.generated.valuedomains.DateAndTime;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfo;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfo.CourseInfoBuilderId;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfo.CourseInfoBuilderStart;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfo.CourseInfoBuilderStatus;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfo.CourseInfoBuilderStudents;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfo.CourseInfoBuilderTeacher;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfo.CourseInfoBuilderType;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseName;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentInfoList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherInfo;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseIDImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseNameImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseStatusImpl;
import org.abstractica.edma.valuedomains.IValueInstance;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;

/**
 * 
 */
public class CourseInfoBuilderImpl implements CourseInfoBuilderId, CourseInfoBuilderStart, CourseInfoBuilderStatus, CourseInfoBuilderStudents, CourseInfoBuilderTeacher, CourseInfoBuilderType
{
    private Object[] edma_value;



    /**
     * Constructor
     */
    public CourseInfoBuilderImpl()
    {
        edma_value = new Object[6];
    }

    /**
     * sets the id field.
     * @param id  The value to assign to the id field
     * @return    Builder interface for setting the start field
     */
    public CourseInfoBuilderStart id(CourseID id)
    {
        if(id == null) throw new NullPointerException("The field id in the CourseInfo ValueDomain may not be null");
        edma_value[0] = ((IValueInstance) id).edma_getValue();
        return this;
    }

    /**
     * sets the id field.
     * @param id  The value to assign to the id field
     * @return    Builder interface for setting the start field
     */
    public CourseInfoBuilderStart id(Long id) throws InvalidValueException
    {
        if(id != null) CourseIDImpl.edma_validate(id);
        if(id == null) throw new NullPointerException("The field id in the CourseInfo ValueDomain may not be null");
        edma_value[0] = CourseIDImpl.edma_create(id);
        return this;
    }

    /**
     * sets the start field.
     * @param start  The value to assign to the start field
     * @return       Builder interface for setting the type field
     */
    public CourseInfoBuilderType start(DateAndTime start)
    {
        if(start == null) throw new NullPointerException("The field start in the CourseInfo ValueDomain may not be null");
        edma_value[1] = ((IValueInstance) start).edma_getValue();
        return this;
    }

    /**
     * sets the type field.
     * @param type  The value to assign to the type field
     * @return      Builder interface for setting the status field
     */
    public CourseInfoBuilderStatus type(CourseName type)
    {
        if(type == null) throw new NullPointerException("The field type in the CourseInfo ValueDomain may not be null");
        edma_value[2] = ((IValueInstance) type).edma_getValue();
        return this;
    }

    /**
     * sets the type field.
     * @param type  The value to assign to the type field
     * @return      Builder interface for setting the status field
     */
    public CourseInfoBuilderStatus type(String type) throws InvalidValueException
    {
        if(type != null) CourseNameImpl.edma_validate(type);
        if(type == null) throw new NullPointerException("The field type in the CourseInfo ValueDomain may not be null");
        edma_value[2] = CourseNameImpl.edma_create(type);
        return this;
    }

    /**
     * sets the status field.
     * @param status  The value to assign to the status field
     * @return        Builder interface for setting the students field
     */
    public CourseInfoBuilderStudents status(CourseStatus status)
    {
        if(status == null) throw new NullPointerException("The field status in the CourseInfo ValueDomain may not be null");
        edma_value[3] = ((IValueInstance) status).edma_getValue();
        return this;
    }

    /**
     * sets the status field.
     * @param status  The value to assign to the status field
     * @return        Builder interface for setting the students field
     */
    public CourseInfoBuilderStudents status(String status) throws InvalidValueException
    {
        if(status != null) CourseStatusImpl.edma_validate(status);
        if(status == null) throw new NullPointerException("The field status in the CourseInfo ValueDomain may not be null");
        edma_value[3] = CourseStatusImpl.edma_create(status);
        return this;
    }

    /**
     * sets the students field.
     * @param students  The value to assign to the students field
     * @return          Builder interface for setting the teacher field
     */
    public CourseInfoBuilderTeacher students(StudentInfoList students)
    {
        if(students == null) throw new NullPointerException("The field students in the CourseInfo ValueDomain may not be null");
        edma_value[4] = ((IValueInstance) students).edma_getValue();
        return this;
    }

    /**
     * sets the teacher field.
     * @param teacher  The value to assign to the teacher field
     * @return         The created CourseInfo value
     */
    public CourseInfo teacher(TeacherInfo teacher)
    {
        edma_value[5] = (teacher == null ? null : (((IValueInstance) teacher).edma_getValue()));
        return new CourseInfoImpl(CourseInfoImpl.edma_create(edma_value));
    }

    /**
     * sets the fieldteacher to null.
     * @return  The created CourseInfo value
     */
    public CourseInfo noTeacher()
    {
        edma_value[5] = null;
        return new CourseInfoImpl(CourseInfoImpl.edma_create(edma_value));
    }
}
