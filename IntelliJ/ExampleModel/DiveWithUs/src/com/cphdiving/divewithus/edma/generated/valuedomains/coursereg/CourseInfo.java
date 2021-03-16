package com.cphdiving.divewithus.edma.generated.valuedomains.coursereg;

import com.cphdiving.divewithus.edma.generated.DiveWithUs;
import com.cphdiving.divewithus.edma.generated.valuedomains.DateAndTime;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseName;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentInfoList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherInfo;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseInfoBuilderImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseInfoImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.external.EDMA_ExternalConstraints;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.userinput.ITerminal;
import org.abstractica.edma.valuedomains.userinput.ValueDomainInput;

/**
 * The representation of a value from the value domain: CourseInfo
 */
public abstract class CourseInfo implements Comparable<CourseInfo>
{
    protected static final IMetaValueDomain edma_domain = DiveWithUs.environment.getValueDomainDefinitions().getValueDomain(29);



    /**
     * Get a value from a terminal
     * @param terminal  The terminal to get the value from
     * @return          The CourseInfo from the terminal
     */
    public static CourseInfo fromTerminal(ITerminal terminal)
    {
        ValueDomainInput vdi = new ValueDomainInput(terminal, EDMA_ExternalConstraints.instance);
        return new CourseInfoImpl(vdi.getValue(edma_domain));
    }

    /**
     * Get a value from its string representation
     * @param s  The String to parse
     * @return   The CourseInfo from the string representation
     */
    public static CourseInfo fromString(String s) throws InvalidValueException
    {
        Object res = edma_domain.valueFromString(s, EDMA_ExternalConstraints.instance);
        return new CourseInfoImpl(res);
    }

    /**
     * Reads and validates a value from a stream
     * @param in  A data input interface for the stream to read from
     * @return    The CourseInfo read from the stream
     */
    public static CourseInfo fromStream(DataInput in) throws IOException, InvalidValueException
    {
        Object res = edma_domain.readValue(in, EDMA_ExternalConstraints.instance);
        return new CourseInfoImpl(res);
    }

    /**
     * Reads a value from a stream without validating the value
     * @param in  A data input interface for the stream to read from
     * @return    The CourseInfo read from the stream
     */
    public static CourseInfo fromStreamNoValidate(DataInput in) throws IOException
    {
        Object res = edma_domain.readValueNoValidate(in);
        return new CourseInfoImpl(res);
    }

    /**
     * Starts creation of a new CourseInfo
     * @return  Builder interface to set the id field
     */
    public static CourseInfoBuilderId create()
    {
        return new CourseInfoBuilderImpl();
    }



    /**
     * Writes this value to a stream
     * @param out  Interface to data output stream
     */
    public abstract void toStream(DataOutput out) throws IOException;

    /**
     * returns the value of the field id
     * @return  The value of the field id
     */
    public abstract CourseID id();

    /**
     * returns the value of the field start
     * @return  The value of the field start
     */
    public abstract DateAndTime start();

    /**
     * returns the value of the field type
     * @return  The value of the field type
     */
    public abstract CourseName type();

    /**
     * returns the value of the field status
     * @return  The value of the field status
     */
    public abstract CourseStatus status();

    /**
     * returns the value of the field students
     * @return  The value of the field students
     */
    public abstract StudentInfoList students();

    /**
     * returns the value of the field teacher
     * @return  The value of the field teacher
     */
    public abstract TeacherInfo teacher();


    /**
     * Builder interface for setting the id field of CourseInfo
     */
    public interface CourseInfoBuilderId
    {

        /**
         * sets the id field.
         * @param id  The value to assign to the id field
         * @return    Builder interface for setting the start field
         */
        public CourseInfoBuilderStart id(CourseID id);

        /**
         * sets the id field.
         * @param id  The value to assign to the id field
         * @return    Builder interface for setting the start field
         */
        public CourseInfoBuilderStart id(Long id) throws InvalidValueException;

    }



    /**
     * Builder interface for setting the start field of CourseInfo
     */
    public interface CourseInfoBuilderStart
    {

        /**
         * sets the start field.
         * @param start  The value to assign to the start field
         * @return       Builder interface for setting the type field
         */
        public CourseInfoBuilderType start(DateAndTime start);

    }



    /**
     * Builder interface for setting the type field of CourseInfo
     */
    public interface CourseInfoBuilderType
    {

        /**
         * sets the type field.
         * @param type  The value to assign to the type field
         * @return      Builder interface for setting the status field
         */
        public CourseInfoBuilderStatus type(CourseName type);

        /**
         * sets the type field.
         * @param type  The value to assign to the type field
         * @return      Builder interface for setting the status field
         */
        public CourseInfoBuilderStatus type(String type) throws InvalidValueException;

    }



    /**
     * Builder interface for setting the status field of CourseInfo
     */
    public interface CourseInfoBuilderStatus
    {

        /**
         * sets the status field.
         * @param status  The value to assign to the status field
         * @return        Builder interface for setting the students field
         */
        public CourseInfoBuilderStudents status(CourseStatus status);

        /**
         * sets the status field.
         * @param status  The value to assign to the status field
         * @return        Builder interface for setting the students field
         */
        public CourseInfoBuilderStudents status(String status) throws InvalidValueException;

    }



    /**
     * Builder interface for setting the students field of CourseInfo
     */
    public interface CourseInfoBuilderStudents
    {

        /**
         * sets the students field.
         * @param students  The value to assign to the students field
         * @return          Builder interface for setting the teacher field
         */
        public CourseInfoBuilderTeacher students(StudentInfoList students);

    }



    /**
     * Builder interface for setting the teacher field of CourseInfo
     */
    public interface CourseInfoBuilderTeacher
    {

        /**
         * sets the teacher field.
         * @param teacher  The value to assign to the teacher field
         * @return         The created CourseInfo value
         */
        public CourseInfo teacher(TeacherInfo teacher);

        /**
         * sets the fieldteacher to null.
         * @return  The created CourseInfo value
         */
        public CourseInfo noTeacher();

    }

}
