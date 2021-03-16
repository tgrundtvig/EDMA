package com.cphdiving.divewithus.edma.generated.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.schoolinfo.SchoolInfoUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.schoolinfo.SchoolInfoViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherViewer;
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
import org.abstractica.edma.runtime.intf.exceptions.UniqueException;

/**
 * This is the updating interface for the CourseReg data model.
 */
public interface CourseRegUpdater extends CourseRegViewer
{

    /**
     * Updates the singleton: SchoolInfo
     * @param schoolInfo  The singleton to update
     * @return            The updater interface for the singleton: SchoolInfo
     */
    public SchoolInfoUpdater update(SchoolInfoViewer schoolInfo);

    /**
     * Starts creation of a new Person entity.
     * @return  Builder interface to set the name attribute.
     */
    public PersonBuilderName newPerson();

    /**
     * Starts creation of a new Person entity.
     * @param ID  ID of the entity
     * @return    Builder interface to set the name attribute.
     */
    public PersonBuilderName newPerson(Long ID);

    /**
     * Creates a new Person from a value in the value domain Person
     * @param person  The value for the new Person.
     * @return        The newly created Person
     * @throws        Throws a UniqueException, if the new entity violates any
     *                of the unique indexes for this kind.
     */
    public PersonUpdater newPerson(Person person) throws UniqueException;

    /**
     * Deletes an entity of kind Person
     * @param person  The Person to be deleted. All extensions to this entity
     *                will also be deleted.
     * @return        <tt>true</tt> if the entity existed and was deleted
     */
    public boolean delete(PersonViewer person);

    /**
     * Updates an entity of kind: Person
     * @param person  The entity to update
     * @return        The updater interface for the entities of kind: Person
     */
    public PersonUpdater update(PersonViewer person);

    /**
     * Starts creation of a new Teacher entity.
     * @param person  The base entity
     * @return        Builder interface to set the salary attribute.
     */
    public TeacherBuilderSalary newTeacher(PersonViewer person);

    /**
     * Creates a new Teacher from a value in the value domain Teacher
     * @param person   The Person to extend.
     * @param teacher  The value for the new Teacher.
     * @return         The newly created Teacher
     */
    public TeacherUpdater newTeacher(PersonViewer person, Teacher teacher);

    /**
     * Deletes an entity of kind Teacher
     * @param teacher  The Teacher to be deleted. All extensions to this entity
     *                 will also be deleted.
     * @return         <tt>true</tt> if the entity existed and was deleted
     */
    public boolean delete(TeacherViewer teacher);

    /**
     * Updates an entity of kind: Teacher
     * @param teacher  The entity to update
     * @return         The updater interface for the entities of kind: Teacher
     */
    public TeacherUpdater update(TeacherViewer teacher);

    /**
     * Starts creation of a new Student entity.
     * @param person  The base entity
     * @return        Builder interface to set the wetsuitSize attribute.
     */
    public StudentBuilderWetsuitSize newStudent(PersonViewer person);

    /**
     * Creates a new Student from a value in the value domain Student
     * @param person   The Person to extend.
     * @param student  The value for the new Student.
     * @return         The newly created Student
     */
    public StudentUpdater newStudent(PersonViewer person, Student student);

    /**
     * Deletes an entity of kind Student
     * @param student  The Student to be deleted. All extensions to this entity
     *                 will also be deleted.
     * @return         <tt>true</tt> if the entity existed and was deleted
     */
    public boolean delete(StudentViewer student);

    /**
     * Updates an entity of kind: Student
     * @param student  The entity to update
     * @return         The updater interface for the entities of kind: Student
     */
    public StudentUpdater update(StudentViewer student);

    /**
     * Starts creation of a new CourseType entity.
     * @return  Builder interface to set the name attribute.
     */
    public CourseTypeBuilderName newCourseType();

    /**
     * Starts creation of a new CourseType entity.
     * @param ID  ID of the entity
     * @return    Builder interface to set the name attribute.
     */
    public CourseTypeBuilderName newCourseType(Long ID);

    /**
     * Creates a new CourseType from a value in the value domain CourseType
     * @param courseType  The value for the new CourseType.
     * @return            The newly created CourseType
     * @throws            Throws a UniqueException, if the new entity violates
     *                    any of the unique indexes for this kind.
     */
    public CourseTypeUpdater newCourseType(CourseType courseType) throws UniqueException;

    /**
     * Deletes an entity of kind CourseType
     * @param courseType  The CourseType to be deleted. All extensions to this
     *                    entity will also be deleted.
     * @return            <tt>true</tt> if the entity existed and was deleted
     */
    public boolean delete(CourseTypeViewer courseType);

    /**
     * Updates an entity of kind: CourseType
     * @param courseType  The entity to update
     * @return            The updater interface for the entities of kind:
     *                    CourseType
     */
    public CourseTypeUpdater update(CourseTypeViewer courseType);

    /**
     * Starts creation of a new Course entity.
     * @return  Builder interface to set the startDate attribute.
     */
    public CourseBuilderStartDate newCourse();

    /**
     * Starts creation of a new Course entity.
     * @param ID  ID of the entity
     * @return    Builder interface to set the startDate attribute.
     */
    public CourseBuilderStartDate newCourse(Long ID);

    /**
     * Creates a new Course from a value in the value domain Course
     * @param course  The value for the new Course.
     * @return        The newly created Course
     */
    public CourseUpdater newCourse(Course course);

    /**
     * Deletes an entity of kind Course
     * @param course  The Course to be deleted. All extensions to this entity
     *                will also be deleted.
     * @return        <tt>true</tt> if the entity existed and was deleted
     */
    public boolean delete(CourseViewer course);

    /**
     * Updates an entity of kind: Course
     * @param course  The entity to update
     * @return        The updater interface for the entities of kind: Course
     */
    public CourseUpdater update(CourseViewer course);

    /**
     * Builder interface for setting the name attribute
     */
    public interface PersonBuilderName
    {

        /**
         * sets the name attribute.
         * @param name  The value to assign to the name attribute
         * @return      Builder interface for setting the email attribute
         */
        public PersonBuilderEmail name(Name name);

    }

    /**
     * Builder interface for setting the email attribute
     */
    public interface PersonBuilderEmail
    {

        /**
         * sets the email attribute.
         * @param email  The value to assign to the email attribute
         * @return       Builder interface for setting the mobile attribute
         */
        public PersonBuilderMobile email(Email email);

    }

    /**
     * Builder interface for setting the mobile attribute
     */
    public interface PersonBuilderMobile
    {

        /**
         * sets the mobile attribute.
         * @param mobile  The value to assign to the mobile attribute
         * @return        Builder interface for setting the balance attribute
         */
        public PersonBuilderBalance mobile(Mobile mobile);

    }

    /**
     * Builder interface for setting the balance attribute
     */
    public interface PersonBuilderBalance
    {

        /**
         * sets the balance attribute.
         * @param balance  The value to assign to the balance attribute
         * @return         The id of the created Person entity
         */
        public PersonUpdater balance(NotNegInt balance) throws UniqueException;

    }

    /**
     * Builder interface for setting the salary attribute
     */
    public interface TeacherBuilderSalary
    {

        /**
         * sets the salary attribute.
         * @param salary  The value to assign to the salary attribute
         * @return        The id of the created Teacher entity
         */
        public TeacherUpdater salary(PosInt salary);

    }

    /**
     * Builder interface for setting the wetsuitSize attribute
     */
    public interface StudentBuilderWetsuitSize
    {

        /**
         * sets the wetsuitSize attribute.
         * @param wetsuitSize  The value to assign to the wetsuitSize attribute
         * @return             Builder interface for setting the bootSize
         *                     attribute
         */
        public StudentBuilderBootSize wetsuitSize(WetsuitSize wetsuitSize);

    }

    /**
     * Builder interface for setting the bootSize attribute
     */
    public interface StudentBuilderBootSize
    {

        /**
         * sets the bootSize attribute.
         * @param bootSize  The value to assign to the bootSize attribute
         * @return          The id of the created Student entity
         */
        public StudentUpdater bootSize(BootSize bootSize);

    }

    /**
     * Builder interface for setting the name attribute
     */
    public interface CourseTypeBuilderName
    {

        /**
         * sets the name attribute.
         * @param name  The value to assign to the name attribute
         * @return      Builder interface for setting the size attribute
         */
        public CourseTypeBuilderSize name(CourseName name);

    }

    /**
     * Builder interface for setting the size attribute
     */
    public interface CourseTypeBuilderSize
    {

        /**
         * sets the size attribute.
         * @param size  The value to assign to the size attribute
         * @return      Builder interface for setting the price attribute
         */
        public CourseTypeBuilderPrice size(CourseSize size);

    }

    /**
     * Builder interface for setting the price attribute
     */
    public interface CourseTypeBuilderPrice
    {

        /**
         * sets the price attribute.
         * @param price  The value to assign to the price attribute
         * @return       The id of the created CourseType entity
         */
        public CourseTypeUpdater price(PosInt price) throws UniqueException;

    }

    /**
     * Builder interface for setting the startDate attribute
     */
    public interface CourseBuilderStartDate
    {

        /**
         * sets the startDate attribute.
         * @param startDate  The value to assign to the startDate attribute
         * @return           Builder interface for setting the startTime
         *                   attribute
         */
        public CourseBuilderStartTime startDate(Date startDate);

    }

    /**
     * Builder interface for setting the startTime attribute
     */
    public interface CourseBuilderStartTime
    {

        /**
         * sets the startTime attribute.
         * @param startTime  The value to assign to the startTime attribute
         * @return           Builder interface for setting the status attribute
         */
        public CourseBuilderStatus startTime(Time startTime);

    }

    /**
     * Builder interface for setting the status attribute
     */
    public interface CourseBuilderStatus
    {

        /**
         * sets the status attribute.
         * @param status  The value to assign to the status attribute
         * @return        The id of the created Course entity
         */
        public CourseUpdater status(CourseStatus status);

    }

}
