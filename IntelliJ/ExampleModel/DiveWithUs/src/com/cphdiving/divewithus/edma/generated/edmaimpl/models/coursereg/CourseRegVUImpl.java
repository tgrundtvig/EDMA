package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseKind;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeKind;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonKind;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.schoolinfo.SchoolInfoUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.schoolinfo.SchoolInfoViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentKind;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherKind;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherViewer;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseTypeVUImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseVUImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.PersonVUImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.SchoolInfoVUImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.StudentVUImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.TeacherVUImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.Date;
import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.Mobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.NotNegInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.PosInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.Time;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.BootSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Course;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseName;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseType;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Person;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Student;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Teacher;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.WetsuitSize;
import org.abstractica.edma.runtime.intf.IDataModelUpdate;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.runtime.intf.exceptions.UniqueException;
import org.abstractica.edma.valuedomains.IValueInstance;

/**
 * 
 */
public class CourseRegVUImpl implements CourseRegUpdater
{
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param edma_dmview  The internal runtime interface
     */
    public CourseRegVUImpl(IDataModelView edma_dmview)
    {
        this.edma_dmview = edma_dmview;
    }

    /**
     * Returns the singleton SchoolInfo.
     * @return  The singleton SchoolInfo.
     */
    public SchoolInfoViewer getSchoolInfo()
    {
        return new SchoolInfoVUImpl(edma_dmview);
    }

    /**
     * Returns the interface to the kind Person
     * @return  The interface to the kind Person
     */
    public PersonKind getPersonKind()
    {
        return new PersonKindImpl(0, edma_dmview);
    }

    /**
     * Returns the interface to the kind Teacher
     * @return  The interface to the kind Teacher
     */
    public TeacherKind getTeacherKind()
    {
        return new TeacherKindImpl(1, edma_dmview);
    }

    /**
     * Returns the interface to the kind Student
     * @return  The interface to the kind Student
     */
    public StudentKind getStudentKind()
    {
        return new StudentKindImpl(2, edma_dmview);
    }

    /**
     * Returns the interface to the kind CourseType
     * @return  The interface to the kind CourseType
     */
    public CourseTypeKind getCourseTypeKind()
    {
        return new CourseTypeKindImpl(3, edma_dmview);
    }

    /**
     * Returns the interface to the kind Course
     * @return  The interface to the kind Course
     */
    public CourseKind getCourseKind()
    {
        return new CourseKindImpl(4, edma_dmview);
    }

    /**
     * Updates the singleton: SchoolInfo
     * @param schoolInfo  The singleton to update
     * @return            The updater interface for the singleton: SchoolInfo
     */
    public SchoolInfoUpdater update(SchoolInfoViewer schoolInfo)
    {
        return (SchoolInfoVUImpl) schoolInfo;
    }

    /**
     * Starts creation of a new Person entity.
     * @return  Builder interface to set the name attribute.
     */
    public PersonBuilderName newPerson()
    {
        return new PersonBuilder(edma_dmview.getUpdateInterface(), null);
    }

    /**
     * Starts creation of a new Person entity.
     * @param ID  ID of the entity
     * @return    Builder interface to set the name attribute.
     */
    public PersonBuilderName newPerson(Long ID)
    {
        return new PersonBuilder(edma_dmview.getUpdateInterface(), ID);
    }

    /**
     * Creates a new Person from a value in the value domain Person
     * @param person  The value for the new Person.
     * @return        The newly created Person
     * @throws        Throws a UniqueException, if the new entity violates any
     *                of the unique indexes for this kind.
     */
    public PersonUpdater newPerson(Person person) throws UniqueException
    {
        Object[] edma_tmpValues = (Object[]) ((IValueInstance) person).edma_getValue();
        Object[] edma_values = new Object[edma_tmpValues.length];
        for(int i = 0; i < edma_tmpValues.length; ++i)
        {
            edma_values[i] = edma_tmpValues[i];
        }
        IEntity edma_entity = edma_dmview.getUpdateInterface().entityNewUnique(0, edma_values);
        return new PersonVUImpl(edma_entity, edma_dmview);
    }

    /**
     * Deletes an entity of kind Person
     * @param person  The Person to be deleted. All extensions to this entity
     *                will also be deleted.
     * @return        <tt>true</tt> if the entity existed and was deleted
     */
    public boolean delete(PersonViewer person)
    {
        IDataModelUpdate edma_dmupdate = edma_dmview.getUpdateInterface();
        //Delete Teacher extension.
        edma_dmupdate.entityDelete(1, person.getID().value());
        //Delete Student extension.
        edma_dmupdate.entityDelete(2, person.getID().value());
        //Delete the entity.
        return edma_dmupdate.entityDelete(0, person.getID().value());
    }

    /**
     * Updates an entity of kind: Person
     * @param person  The entity to update
     * @return        The updater interface for the entities of kind: Person
     */
    public PersonUpdater update(PersonViewer person)
    {
        return (PersonVUImpl) person;
    }

    /**
     * Starts creation of a new Teacher entity.
     * @param person  The base entity
     * @return        Builder interface to set the salary attribute.
     */
    public TeacherBuilderSalary newTeacher(PersonViewer person)
    {
        return new TeacherBuilder(edma_dmview.getUpdateInterface(), person.getID().value());
    }

    /**
     * Creates a new Teacher from a value in the value domain Teacher
     * @param person   The Person to extend.
     * @param teacher  The value for the new Teacher.
     * @return         The newly created Teacher
     */
    public TeacherUpdater newTeacher(PersonViewer person, Teacher teacher)
    {
        Object[] edma_tmpValues = (Object[]) ((IValueInstance) teacher).edma_getValue();
        Object[] edma_values = new Object[edma_tmpValues.length];
        for(int i = 0; i < edma_tmpValues.length; ++i)
        {
            edma_values[i] = edma_tmpValues[i];
        }
        IEntity edma_entity = edma_dmview.getUpdateInterface().entityNew(1, edma_values);
        return new TeacherVUImpl(edma_entity, edma_dmview);
    }

    /**
     * Deletes an entity of kind Teacher
     * @param teacher  The Teacher to be deleted. All extensions to this entity
     *                 will also be deleted.
     * @return         <tt>true</tt> if the entity existed and was deleted
     */
    public boolean delete(TeacherViewer teacher)
    {
        IDataModelUpdate edma_dmupdate = edma_dmview.getUpdateInterface();
        //Delete the entity.
        return edma_dmupdate.entityDelete(1, teacher.getID().value());
    }

    /**
     * Updates an entity of kind: Teacher
     * @param teacher  The entity to update
     * @return         The updater interface for the entities of kind: Teacher
     */
    public TeacherUpdater update(TeacherViewer teacher)
    {
        return (TeacherVUImpl) teacher;
    }

    /**
     * Starts creation of a new Student entity.
     * @param person  The base entity
     * @return        Builder interface to set the wetsuitSize attribute.
     */
    public StudentBuilderWetsuitSize newStudent(PersonViewer person)
    {
        return new StudentBuilder(edma_dmview.getUpdateInterface(), person.getID().value());
    }

    /**
     * Creates a new Student from a value in the value domain Student
     * @param person   The Person to extend.
     * @param student  The value for the new Student.
     * @return         The newly created Student
     */
    public StudentUpdater newStudent(PersonViewer person, Student student)
    {
        Object[] edma_tmpValues = (Object[]) ((IValueInstance) student).edma_getValue();
        Object[] edma_values = new Object[edma_tmpValues.length];
        for(int i = 0; i < edma_tmpValues.length; ++i)
        {
            edma_values[i] = edma_tmpValues[i];
        }
        IEntity edma_entity = edma_dmview.getUpdateInterface().entityNew(2, edma_values);
        return new StudentVUImpl(edma_entity, edma_dmview);
    }

    /**
     * Deletes an entity of kind Student
     * @param student  The Student to be deleted. All extensions to this entity
     *                 will also be deleted.
     * @return         <tt>true</tt> if the entity existed and was deleted
     */
    public boolean delete(StudentViewer student)
    {
        IDataModelUpdate edma_dmupdate = edma_dmview.getUpdateInterface();
        //Delete the entity.
        return edma_dmupdate.entityDelete(2, student.getID().value());
    }

    /**
     * Updates an entity of kind: Student
     * @param student  The entity to update
     * @return         The updater interface for the entities of kind: Student
     */
    public StudentUpdater update(StudentViewer student)
    {
        return (StudentVUImpl) student;
    }

    /**
     * Starts creation of a new CourseType entity.
     * @return  Builder interface to set the name attribute.
     */
    public CourseTypeBuilderName newCourseType()
    {
        return new CourseTypeBuilder(edma_dmview.getUpdateInterface(), null);
    }

    /**
     * Starts creation of a new CourseType entity.
     * @param ID  ID of the entity
     * @return    Builder interface to set the name attribute.
     */
    public CourseTypeBuilderName newCourseType(Long ID)
    {
        return new CourseTypeBuilder(edma_dmview.getUpdateInterface(), ID);
    }

    /**
     * Creates a new CourseType from a value in the value domain CourseType
     * @param courseType  The value for the new CourseType.
     * @return            The newly created CourseType
     * @throws            Throws a UniqueException, if the new entity violates
     *                    any of the unique indexes for this kind.
     */
    public CourseTypeUpdater newCourseType(CourseType courseType) throws UniqueException
    {
        Object[] edma_tmpValues = (Object[]) ((IValueInstance) courseType).edma_getValue();
        Object[] edma_values = new Object[edma_tmpValues.length];
        for(int i = 0; i < edma_tmpValues.length; ++i)
        {
            edma_values[i] = edma_tmpValues[i];
        }
        IEntity edma_entity = edma_dmview.getUpdateInterface().entityNewUnique(3, edma_values);
        return new CourseTypeVUImpl(edma_entity, edma_dmview);
    }

    /**
     * Deletes an entity of kind CourseType
     * @param courseType  The CourseType to be deleted. All extensions to this
     *                    entity will also be deleted.
     * @return            <tt>true</tt> if the entity existed and was deleted
     */
    public boolean delete(CourseTypeViewer courseType)
    {
        IDataModelUpdate edma_dmupdate = edma_dmview.getUpdateInterface();
        //Delete the entity.
        return edma_dmupdate.entityDelete(3, courseType.getID().value());
    }

    /**
     * Updates an entity of kind: CourseType
     * @param courseType  The entity to update
     * @return            The updater interface for the entities of kind:
     *                    CourseType
     */
    public CourseTypeUpdater update(CourseTypeViewer courseType)
    {
        return (CourseTypeVUImpl) courseType;
    }

    /**
     * Starts creation of a new Course entity.
     * @return  Builder interface to set the startDate attribute.
     */
    public CourseBuilderStartDate newCourse()
    {
        return new CourseBuilder(edma_dmview.getUpdateInterface(), null);
    }

    /**
     * Starts creation of a new Course entity.
     * @param ID  ID of the entity
     * @return    Builder interface to set the startDate attribute.
     */
    public CourseBuilderStartDate newCourse(Long ID)
    {
        return new CourseBuilder(edma_dmview.getUpdateInterface(), ID);
    }

    /**
     * Creates a new Course from a value in the value domain Course
     * @param course  The value for the new Course.
     * @return        The newly created Course
     */
    public CourseUpdater newCourse(Course course)
    {
        Object[] edma_tmpValues = (Object[]) ((IValueInstance) course).edma_getValue();
        Object[] edma_values = new Object[edma_tmpValues.length];
        for(int i = 0; i < edma_tmpValues.length; ++i)
        {
            edma_values[i] = edma_tmpValues[i];
        }
        IEntity edma_entity = edma_dmview.getUpdateInterface().entityNew(4, edma_values);
        return new CourseVUImpl(edma_entity, edma_dmview);
    }

    /**
     * Deletes an entity of kind Course
     * @param course  The Course to be deleted. All extensions to this entity
     *                will also be deleted.
     * @return        <tt>true</tt> if the entity existed and was deleted
     */
    public boolean delete(CourseViewer course)
    {
        IDataModelUpdate edma_dmupdate = edma_dmview.getUpdateInterface();
        //Delete the entity.
        return edma_dmupdate.entityDelete(4, course.getID().value());
    }

    /**
     * Updates an entity of kind: Course
     * @param course  The entity to update
     * @return        The updater interface for the entities of kind: Course
     */
    public CourseUpdater update(CourseViewer course)
    {
        return (CourseVUImpl) course;
    }


    /**
     * 
     */
    public class PersonBuilder implements PersonBuilderBalance, PersonBuilderEmail, PersonBuilderMobile, PersonBuilderName
    {
        private IDataModelUpdate edma_upd;
        private Object[] value;



        /**
         * Constructor
         * @param edma_upd  Internal runtime interface
         * @param edma_ID   ID of the entity
         */
        public PersonBuilder(IDataModelUpdate edma_upd, Long edma_ID)
        {
            this.edma_upd = edma_upd;
            value = new Object[5];
            value[0] = edma_ID;
        }

        /**
         * sets the name attribute.
         * @param name  The value to assign to the name attribute
         * @return      Builder interface for setting the email attribute
         */
        public PersonBuilderEmail name(Name name)
        {
            if(name == null) throw new NullPointerException();
            value[1] = ((IValueInstance) name).edma_getValue();
            return this;
        }

        /**
         * sets the email attribute.
         * @param email  The value to assign to the email attribute
         * @return       Builder interface for setting the mobile attribute
         */
        public PersonBuilderMobile email(Email email)
        {
            if(email == null) throw new NullPointerException();
            value[2] = ((IValueInstance) email).edma_getValue();
            return this;
        }

        /**
         * sets the mobile attribute.
         * @param mobile  The value to assign to the mobile attribute
         * @return        Builder interface for setting the balance attribute
         */
        public PersonBuilderBalance mobile(Mobile mobile)
        {
            if(mobile == null) throw new NullPointerException();
            value[3] = ((IValueInstance) mobile).edma_getValue();
            return this;
        }

        /**
         * sets the balance attribute.
         * @param balance  The value to assign to the balance attribute
         * @return         The id of the created Person entity
         */
        public PersonUpdater balance(NotNegInt balance) throws UniqueException
        {
            if(balance == null) throw new NullPointerException();
            value[4] = ((IValueInstance) balance).edma_getValue();
            IEntity edma_entity = edma_upd.entityNewUnique(0, value);
            return new PersonVUImpl(edma_entity, edma_upd);
        }
    }



    /**
     * 
     */
    public class TeacherBuilder implements TeacherBuilderSalary
    {
        private IDataModelUpdate edma_upd;
        private Object[] value;



        /**
         * Constructor
         * @param edma_upd  Internal runtime interface
         * @param edma_ID   ID of the entity
         */
        public TeacherBuilder(IDataModelUpdate edma_upd, Long edma_ID)
        {
            this.edma_upd = edma_upd;
            value = new Object[2];
            value[0] = edma_ID;
        }

        /**
         * sets the salary attribute.
         * @param salary  The value to assign to the salary attribute
         * @return        The id of the created Teacher entity
         */
        public TeacherUpdater salary(PosInt salary)
        {
            if(salary == null) throw new NullPointerException();
            value[1] = ((IValueInstance) salary).edma_getValue();
            IEntity edma_entity = edma_upd.entityNew(1, value);
            return new TeacherVUImpl(edma_entity, edma_upd);
        }
    }



    /**
     * 
     */
    public class StudentBuilder implements StudentBuilderBootSize, StudentBuilderWetsuitSize
    {
        private IDataModelUpdate edma_upd;
        private Object[] value;



        /**
         * Constructor
         * @param edma_upd  Internal runtime interface
         * @param edma_ID   ID of the entity
         */
        public StudentBuilder(IDataModelUpdate edma_upd, Long edma_ID)
        {
            this.edma_upd = edma_upd;
            value = new Object[3];
            value[0] = edma_ID;
        }

        /**
         * sets the wetsuitSize attribute.
         * @param wetsuitSize  The value to assign to the wetsuitSize attribute
         * @return             Builder interface for setting the bootSize
         *                     attribute
         */
        public StudentBuilderBootSize wetsuitSize(WetsuitSize wetsuitSize)
        {
            if(wetsuitSize == null) throw new NullPointerException();
            value[1] = ((IValueInstance) wetsuitSize).edma_getValue();
            return this;
        }

        /**
         * sets the bootSize attribute.
         * @param bootSize  The value to assign to the bootSize attribute
         * @return          The id of the created Student entity
         */
        public StudentUpdater bootSize(BootSize bootSize)
        {
            if(bootSize == null) throw new NullPointerException();
            value[2] = ((IValueInstance) bootSize).edma_getValue();
            IEntity edma_entity = edma_upd.entityNew(2, value);
            return new StudentVUImpl(edma_entity, edma_upd);
        }
    }



    /**
     * 
     */
    public class CourseTypeBuilder implements CourseTypeBuilderName, CourseTypeBuilderPrice, CourseTypeBuilderSize
    {
        private IDataModelUpdate edma_upd;
        private Object[] value;



        /**
         * Constructor
         * @param edma_upd  Internal runtime interface
         * @param edma_ID   ID of the entity
         */
        public CourseTypeBuilder(IDataModelUpdate edma_upd, Long edma_ID)
        {
            this.edma_upd = edma_upd;
            value = new Object[4];
            value[0] = edma_ID;
        }

        /**
         * sets the name attribute.
         * @param name  The value to assign to the name attribute
         * @return      Builder interface for setting the size attribute
         */
        public CourseTypeBuilderSize name(CourseName name)
        {
            if(name == null) throw new NullPointerException();
            value[1] = ((IValueInstance) name).edma_getValue();
            return this;
        }

        /**
         * sets the size attribute.
         * @param size  The value to assign to the size attribute
         * @return      Builder interface for setting the price attribute
         */
        public CourseTypeBuilderPrice size(CourseSize size)
        {
            if(size == null) throw new NullPointerException();
            value[2] = ((IValueInstance) size).edma_getValue();
            return this;
        }

        /**
         * sets the price attribute.
         * @param price  The value to assign to the price attribute
         * @return       The id of the created CourseType entity
         */
        public CourseTypeUpdater price(PosInt price) throws UniqueException
        {
            if(price == null) throw new NullPointerException();
            value[3] = ((IValueInstance) price).edma_getValue();
            IEntity edma_entity = edma_upd.entityNewUnique(3, value);
            return new CourseTypeVUImpl(edma_entity, edma_upd);
        }
    }



    /**
     * 
     */
    public class CourseBuilder implements CourseBuilderStartDate, CourseBuilderStartTime, CourseBuilderStatus
    {
        private IDataModelUpdate edma_upd;
        private Object[] value;



        /**
         * Constructor
         * @param edma_upd  Internal runtime interface
         * @param edma_ID   ID of the entity
         */
        public CourseBuilder(IDataModelUpdate edma_upd, Long edma_ID)
        {
            this.edma_upd = edma_upd;
            value = new Object[4];
            value[0] = edma_ID;
        }

        /**
         * sets the startDate attribute.
         * @param startDate  The value to assign to the startDate attribute
         * @return           Builder interface for setting the startTime
         *                   attribute
         */
        public CourseBuilderStartTime startDate(Date startDate)
        {
            if(startDate == null) throw new NullPointerException();
            value[1] = ((IValueInstance) startDate).edma_getValue();
            return this;
        }

        /**
         * sets the startTime attribute.
         * @param startTime  The value to assign to the startTime attribute
         * @return           Builder interface for setting the status attribute
         */
        public CourseBuilderStatus startTime(Time startTime)
        {
            if(startTime == null) throw new NullPointerException();
            value[2] = ((IValueInstance) startTime).edma_getValue();
            return this;
        }

        /**
         * sets the status attribute.
         * @param status  The value to assign to the status attribute
         * @return        The id of the created Course entity
         */
        public CourseUpdater status(CourseStatus status)
        {
            if(status == null) throw new NullPointerException();
            value[3] = ((IValueInstance) status).edma_getValue();
            IEntity edma_entity = edma_upd.entityNew(4, value);
            return new CourseVUImpl(edma_entity, edma_upd);
        }
    }

}
