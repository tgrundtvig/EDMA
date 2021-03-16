package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherViewer;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseTypeVUImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.StudentSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.StudentVUImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.TeacherVUImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.Date;
import com.cphdiving.divewithus.edma.generated.valuedomains.Time;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Course;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseIDImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseStatusImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.DateImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.TimeImpl;
import java.util.HashMap;
import java.util.Map;
import org.abstractica.edma.runtime.intf.IDataModelUpdate;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.valuedomains.IValueInstance;

/**
 * 
 */
public class CourseVUImpl implements CourseUpdater, CourseViewer
{
    protected IEntity edma_entity;
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param edma_entity  Internal entity
     * @param edma_dmview  Internal runtime interface
     */
    public CourseVUImpl(IEntity edma_entity, IDataModelView edma_dmview)
    {
        this.edma_entity = edma_entity;
        this.edma_dmview = edma_dmview;
    }

    /**
     * Create a copy of this Course at this instance in time
     * @return  A copy of this Course entity as a value  from the value domain
     *          Course
     */
    public Course snapshot()
    {
        Object[] res = new Object[4];
        Object[] internal = edma_entity.getValue();
        res[0] = internal[0];
        res[1] = internal[1];
        res[2] = internal[2];
        res[3] = internal[3];
        return new CourseImpl(res);
    }

    /**
     * Returns the ID of this Course
     * @return  The ID of this Course
     */
    public CourseID getID()
    {
        return new CourseIDImpl(edma_entity.getID());
    }

    /**
     * Returns the attribute startDate of this Course
     * @return  The value of the attribute startDate
     */
    public Date getStartDate()
    {
        return new DateImpl(edma_entity.getValue()[1]);
    }

    /**
     * Returns the attribute startTime of this Course
     * @return  The value of the attribute startTime
     */
    public Time getStartTime()
    {
        return new TimeImpl(edma_entity.getValue()[2]);
    }

    /**
     * Returns the attribute status of this Course
     * @return  The value of the attribute status
     */
    public CourseStatus getStatus()
    {
        return new CourseStatusImpl(edma_entity.getValue()[3]);
    }

    /**
     * This methods follows the relation CourseTypes
     * @return  The result of following the relation CourseTypes
     */
    public CourseTypeViewer getCourseType()
    {
        Long resID = edma_dmview.relationAsAGetB(1, edma_entity.getID());
        if(resID == null) return null;
        IEntity entity = edma_dmview.kindGetFromID(3, resID);
        return new CourseTypeVUImpl(entity, edma_dmview);
    }

    /**
     * This methods follows the relation StudentEnrollment
     * @return  The result of following the relation StudentEnrollment
     */
    public StudentSet getStudentSet()
    {
        int setID = edma_dmview.relationAsAGetBSet(3, edma_entity.getID());
        return new StudentSetImpl(setID, edma_dmview);
    }

    /**
     * This methods follows the relation TeacherEnrollment
     * @return  The result of following the relation TeacherEnrollment
     */
    public TeacherViewer getTeacher()
    {
        Long resID = edma_dmview.relationAsAGetB(5, edma_entity.getID());
        if(resID == null) return null;
        IEntity entity = edma_dmview.kindGetFromID(1, resID);
        return new TeacherVUImpl(entity, edma_dmview);
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
        if(!(o instanceof CourseViewer)) return false;
        CourseViewer other = (CourseViewer) o;
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
     * Begin attribute updates on this Course
     * @return  An attribute update interface for chaining attribute updates
     */
    public CourseAttUpd beginUpdate()
    {
        return new CourseAttUpdImpl(edma_entity.getID(), edma_dmview.getUpdateInterface());
    }

    /**
     * Sets the courseType for this Course. If another courseType is already
     * set, it will be replaced.
     *  This method has been generated from the relation:
     *  CourseTypes
     * @param courseType  The courseType to be set. Use null to remove any
     *                    previous connection.
     * @return            The previous courseType connected to this course or
     *                    null if none was connected.
     */
    public CourseTypeViewer setCourseType(CourseTypeViewer courseType)
    {
        Long b_ID = null;
        if(courseType != null)
        {
            b_ID = ((CourseTypeVUImpl) courseType).edma_entity.getID();
        }
        Long res = edma_dmview.getUpdateInterface().relationReplaceB(1, edma_entity.getID(), b_ID);
        if(res == null) return null;
        IEntity ent = edma_dmview.kindGetFromID(3, res);
        return new CourseTypeVUImpl(ent, edma_dmview);
    }

    /**
     * Connects the student to this Course, if it is not already connected.
     *  This method has been generated from the relation:
     *  StudentEnrollment
     * @param student  The student to be added. May NOT be null.
     * @return         <tt>true</tt> if this course was not already connected
     *                 to the specified student
     */
    public boolean addStudent(StudentViewer student)
    {
        StudentVUImpl B = (StudentVUImpl) student;
        return edma_dmview.getUpdateInterface().relationAdd(3, edma_entity.getID(), B.edma_entity.getID());
    }

    /**
     * Removes the student from this Course, if it is connected.
     *  This method has been generated from the relation:
     *  StudentEnrollment
     * @param student  The student to be connected to this course. May NOT be
     *                 null.
     * @return         <tt>true</tt> if the specified student was connected to
     *                 this course
     */
    public boolean removeStudent(StudentViewer student)
    {
        StudentVUImpl edma_b = (StudentVUImpl) student;
        return edma_dmview.getUpdateInterface().relationDelete(3, edma_entity.getID(), edma_b.edma_entity.getID());
    }

    /**
     * Sets the teacher for this Course. If another teacher is already set, it
     * will be replaced.
     *  This method has been generated from the relation:
     *  TeacherEnrollment
     * @param teacher  The teacher to be set. Use null to remove any previous
     *                 connection.
     * @return         The previous teacher connected to this course or null if
     *                 none was connected.
     */
    public TeacherViewer setTeacher(TeacherViewer teacher)
    {
        Long b_ID = null;
        if(teacher != null)
        {
            b_ID = ((TeacherVUImpl) teacher).edma_entity.getID();
        }
        Long res = edma_dmview.getUpdateInterface().relationReplaceB(5, edma_entity.getID(), b_ID);
        if(res == null) return null;
        IEntity ent = edma_dmview.kindGetFromID(1, res);
        return new TeacherVUImpl(ent, edma_dmview);
    }


    /**
     * 
     */
    private class CourseAttUpdImpl implements CourseAttUpd
    {
        private Long entityID;
        private IDataModelUpdate edma_dmupdate;
        private Map<Integer, Object> updateMap;



        /**
         * Constructor
         * @param entityID       ID of the entity to update
         * @param edma_dmupdate  Internal runtime interface
         */
        private CourseAttUpdImpl(Long entityID, IDataModelUpdate edma_dmupdate)
        {
            this.entityID = entityID;
            this.edma_dmupdate = edma_dmupdate;
            this.updateMap = new HashMap<Integer, Object>();
        }

        /**
         * Sets the attribute status on this Course
         * @param status  The value to set
         * @return        Interface for chaining attribute updates
         */
        public CourseAttUpd setStatus(CourseStatus status)
        {
            updateMap.put(2, ((IValueInstance) status).edma_getValue());
            return this;
        }

        /**
         * Saves the changes to the data model.
         * @return  
         */
        public boolean save()
        {
            return edma_dmupdate.entityUpdate(4, entityID, updateMap);
        }
    }

}
