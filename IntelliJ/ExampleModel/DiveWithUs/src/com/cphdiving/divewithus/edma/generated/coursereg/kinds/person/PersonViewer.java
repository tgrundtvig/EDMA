package com.cphdiving.divewithus.edma.generated.coursereg.kinds.person;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.Mobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.NotNegInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Person;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonID;

/**
 * This is the viewing interface for Person
 */
public interface PersonViewer
{

    /**
     * Create a copy of this Person at this instance in time
     * @return  A copy of this Person entity as a value  from the value domain
     *          Person
     */
    public Person snapshot();

    /**
     * Returns the ID of this Person
     * @return  The ID of this Person
     */
    public PersonID getID();

    /**
     * Returns the attribute name of this Person
     * @return  The value of the attribute name
     */
    public Name getName();

    /**
     * Returns the attribute email of this Person
     * @return  The value of the attribute email
     */
    public Email getEmail();

    /**
     * Returns the attribute mobile of this Person
     * @return  The value of the attribute mobile
     */
    public Mobile getMobile();

    /**
     * Returns the attribute balance of this Person
     * @return  The value of the attribute balance
     */
    public NotNegInt getBalance();

    /**
     * Views this Person as its extension kind Teacher. May return
     * <tt>null</tt> if this Person is not extended to Teacher
     * @return  The Teacher view of this Person or <tt>null</tt> if this Person
     *          is not extended to Teacher
     */
    public TeacherViewer asTeacher();

    /**
     * Views this Person as its extension kind Student. May return
     * <tt>null</tt> if this Person is not extended to Student
     * @return  The Student view of this Person or <tt>null</tt> if this Person
     *          is not extended to Student
     */
    public StudentViewer asStudent();

    /**
     * Returns <tt>true</tt> if this entity has the same ID as the provided
     * entity
     * @param o  The object to compare with
     * @return   <tt>true</tt> if this entity has the same ID as the provided
     *           entity
     */
    public boolean equals(Object o);

    /**
     * Returns the hash code of this entity
     * @return  The hash code of this entity
     */
    public int hashCode();

}
