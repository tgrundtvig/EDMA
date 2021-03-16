package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherViewer;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseTypeSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseTypeVUImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.PersonVUImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.PosInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Teacher;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.TeacherIDImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.TeacherImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.PosIntImpl;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IEntity;

/**
 * 
 */
public class TeacherVUImpl implements TeacherUpdater, TeacherViewer
{
    protected IEntity edma_entity;
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param edma_entity  Internal entity
     * @param edma_dmview  Internal runtime interface
     */
    public TeacherVUImpl(IEntity edma_entity, IDataModelView edma_dmview)
    {
        this.edma_entity = edma_entity;
        this.edma_dmview = edma_dmview;
    }

    /**
     * Create a copy of this Teacher at this instance in time
     * @return  A copy of this Teacher entity as a value  from the value domain
     *          Teacher
     */
    public Teacher snapshot()
    {
        Object[] res = new Object[2];
        Object[] internal = edma_entity.getValue();
        res[0] = internal[0];
        res[1] = internal[1];
        return new TeacherImpl(res);
    }

    /**
     * Returns the ID of this Teacher
     * @return  The ID of this Teacher
     */
    public TeacherID getID()
    {
        return new TeacherIDImpl(edma_entity.getID());
    }

    /**
     * Returns the attribute salary of this Teacher
     * @return  The value of the attribute salary
     */
    public PosInt getSalary()
    {
        return new PosIntImpl(edma_entity.getValue()[1]);
    }

    /**
     * Views this Teacher as its base kind Person. Will never return
     * <tt>null</tt> since Teacher is always an extension of Person
     * @return  The Person view of this Teacher
     */
    public PersonViewer asPerson()
    {
        IEntity base = edma_dmview.kindGetFromID(0, edma_entity.getID());
        return new PersonVUImpl(base, edma_dmview);
    }

    /**
     * This methods follows the relation TeachingAbility
     * @return  The result of following the relation TeachingAbility
     */
    public CourseTypeSet getCourseTypeSet()
    {
        int setID = edma_dmview.relationAsAGetBSet(4, edma_entity.getID());
        return new CourseTypeSetImpl(setID, edma_dmview);
    }

    /**
     * This methods follows the relation TeacherEnrollment
     * @return  The result of following the relation TeacherEnrollment
     */
    public CourseSet getCourseSet()
    {
        int setID = edma_dmview.relationAsBGetASet(5, edma_entity.getID());
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
        if(!(o instanceof TeacherViewer)) return false;
        TeacherViewer other = (TeacherViewer) o;
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
     * Connects the courseType to this Teacher, if it is not already connected.
     *  This method has been generated from the relation:
     *  TeachingAbility
     * @param courseType  The courseType to be added. May NOT be null.
     * @return            <tt>true</tt> if this teacher was not already
     *                    connected to the specified courseType
     */
    public boolean addCourseType(CourseTypeViewer courseType)
    {
        CourseTypeVUImpl B = (CourseTypeVUImpl) courseType;
        return edma_dmview.getUpdateInterface().relationAdd(4, edma_entity.getID(), B.edma_entity.getID());
    }

    /**
     * Removes the courseType from this Teacher, if it is connected.
     *  This method has been generated from the relation:
     *  TeachingAbility
     * @param courseType  The courseType to be connected to this teacher. May
     *                    NOT be null.
     * @return            <tt>true</tt> if the specified courseType was
     *                    connected to this teacher
     */
    public boolean removeCourseType(CourseTypeViewer courseType)
    {
        CourseTypeVUImpl edma_b = (CourseTypeVUImpl) courseType;
        return edma_dmview.getUpdateInterface().relationDelete(4, edma_entity.getID(), edma_b.edma_entity.getID());
    }
}
