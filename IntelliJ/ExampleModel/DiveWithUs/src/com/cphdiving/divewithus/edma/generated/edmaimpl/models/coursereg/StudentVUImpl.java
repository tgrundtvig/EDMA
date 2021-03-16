package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentViewer;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseTypeSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseTypeVUImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.PersonVUImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.BootSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Student;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.WetsuitSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.BootSizeImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.StudentIDImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.StudentImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.WetsuitSizeImpl;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IEntity;

/**
 * 
 */
public class StudentVUImpl implements StudentUpdater, StudentViewer
{
    protected IEntity edma_entity;
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param edma_entity  Internal entity
     * @param edma_dmview  Internal runtime interface
     */
    public StudentVUImpl(IEntity edma_entity, IDataModelView edma_dmview)
    {
        this.edma_entity = edma_entity;
        this.edma_dmview = edma_dmview;
    }

    /**
     * Create a copy of this Student at this instance in time
     * @return  A copy of this Student entity as a value  from the value domain
     *          Student
     */
    public Student snapshot()
    {
        Object[] res = new Object[3];
        Object[] internal = edma_entity.getValue();
        res[0] = internal[0];
        res[1] = internal[1];
        res[2] = internal[2];
        return new StudentImpl(res);
    }

    /**
     * Returns the ID of this Student
     * @return  The ID of this Student
     */
    public StudentID getID()
    {
        return new StudentIDImpl(edma_entity.getID());
    }

    /**
     * Returns the attribute wetsuitSize of this Student
     * @return  The value of the attribute wetsuitSize
     */
    public WetsuitSize getWetsuitSize()
    {
        return new WetsuitSizeImpl(edma_entity.getValue()[1]);
    }

    /**
     * Returns the attribute bootSize of this Student
     * @return  The value of the attribute bootSize
     */
    public BootSize getBootSize()
    {
        return new BootSizeImpl(edma_entity.getValue()[2]);
    }

    /**
     * Views this Student as its base kind Person. Will never return
     * <tt>null</tt> since Student is always an extension of Person
     * @return  The Person view of this Student
     */
    public PersonViewer asPerson()
    {
        IEntity base = edma_dmview.kindGetFromID(0, edma_entity.getID());
        return new PersonVUImpl(base, edma_dmview);
    }

    /**
     * This methods follows the relation PassedCourses
     * @return  The result of following the relation PassedCourses
     */
    public CourseTypeSet getPassedCourseSet()
    {
        int setID = edma_dmview.relationAsAGetBSet(2, edma_entity.getID());
        return new CourseTypeSetImpl(setID, edma_dmview);
    }

    /**
     * This methods follows the relation StudentEnrollment
     * @return  The result of following the relation StudentEnrollment
     */
    public CourseSet getCourseSet()
    {
        int setID = edma_dmview.relationAsBGetASet(3, edma_entity.getID());
        return new CourseSetImpl(setID, edma_dmview);
    }

    /**
     * Returns <tt>true</tt> if this entity has the same ID as the provided
     * entity
     * @param o  The object to compare with
     * @return   <tt>true</tt> if this entity has the same ID as the provided
     *           entity
     */
    public boolean equals(Object o)
    {
        if(!(o instanceof StudentViewer)) return false;
        StudentViewer other = (StudentViewer) o;
        return getID().equals(other.getID());
    }

    /**
     * Returns the hash code of this entity
     * @return  The hash code of this entity
     */
    public int hashCode()
    {
        return getID().hashCode();
    }

    /**
     * Connects the passedCourse to this Student, if it is not already
     * connected.
     *  This method has been generated from the relation:
     *  PassedCourses
     * @param passedCourse  The passedCourse to be added. May NOT be null.
     * @return              <tt>true</tt> if this student was not already
     *                      connected to the specified passedCourse
     */
    public boolean addPassedCourse(CourseTypeViewer passedCourse)
    {
        CourseTypeVUImpl B = (CourseTypeVUImpl) passedCourse;
        return edma_dmview.getUpdateInterface().relationAdd(2, edma_entity.getID(), B.edma_entity.getID());
    }

    /**
     * Removes the passedCourse from this Student, if it is connected.
     *  This method has been generated from the relation:
     *  PassedCourses
     * @param passedCourse  The passedCourse to be connected to this student.
     *                      May NOT be null.
     * @return              <tt>true</tt> if the specified passedCourse was
     *                      connected to this student
     */
    public boolean removePassedCourse(CourseTypeViewer passedCourse)
    {
        CourseTypeVUImpl edma_b = (CourseTypeVUImpl) passedCourse;
        return edma_dmview.getUpdateInterface().relationDelete(2, edma_entity.getID(), edma_b.edma_entity.getID());
    }
}
