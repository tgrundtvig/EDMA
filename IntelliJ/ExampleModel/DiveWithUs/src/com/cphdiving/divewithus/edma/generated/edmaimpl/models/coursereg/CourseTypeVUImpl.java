package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherSet;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseTypeSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseTypeVUImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.StudentSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.TeacherSetImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.Date;
import com.cphdiving.divewithus.edma.generated.valuedomains.PosInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseName;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseType;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseNameImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseSizeImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseTypeIDImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseTypeImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.PosIntImpl;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.valuedomains.IValueInstance;

/**
 * 
 */
public class CourseTypeVUImpl implements CourseTypeUpdater, CourseTypeViewer
{
    protected IEntity edma_entity;
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param edma_entity  Internal entity
     * @param edma_dmview  Internal runtime interface
     */
    public CourseTypeVUImpl(IEntity edma_entity, IDataModelView edma_dmview)
    {
        this.edma_entity = edma_entity;
        this.edma_dmview = edma_dmview;
    }

    /**
     * Create a copy of this CourseType at this instance in time
     * @return  A copy of this CourseType entity as a value  from the value
     *          domain CourseType
     */
    public CourseType snapshot()
    {
        Object[] res = new Object[4];
        Object[] internal = edma_entity.getValue();
        res[0] = internal[0];
        res[1] = internal[1];
        res[2] = internal[2];
        res[3] = internal[3];
        return new CourseTypeImpl(res);
    }

    /**
     * Returns the ID of this CourseType
     * @return  The ID of this CourseType
     */
    public CourseTypeID getID()
    {
        return new CourseTypeIDImpl(edma_entity.getID());
    }

    /**
     * Returns the attribute name of this CourseType
     * @return  The value of the attribute name
     */
    public CourseName getName()
    {
        return new CourseNameImpl(edma_entity.getValue()[1]);
    }

    /**
     * Returns the attribute size of this CourseType
     * @return  The value of the attribute size
     */
    public CourseSize getSize()
    {
        return new CourseSizeImpl(edma_entity.getValue()[2]);
    }

    /**
     * Returns the attribute price of this CourseType
     * @return  The value of the attribute price
     */
    public PosInt getPrice()
    {
        return new PosIntImpl(edma_entity.getValue()[3]);
    }

    /**
     * This methods follows the relation CourseDependency
     * @return  The result of following the relation CourseDependency
     */
    public CourseTypeSet getRequiredSet()
    {
        int setID = edma_dmview.relationAsAGetBSet(0, edma_entity.getID());
        return new CourseTypeSetImpl(setID, edma_dmview);
    }

    /**
     * This methods follows the relation CourseDependency
     * @return  The result of following the relation CourseDependency
     */
    public CourseTypeSet asRequiredGetCourseTypeSet()
    {
        int setID = edma_dmview.relationAsBGetASet(0, edma_entity.getID());
        return new CourseTypeSetImpl(setID, edma_dmview);
    }

    /**
     * This methods follows the relation CourseTypes
     * @return  The result of following the relation CourseTypes
     */
    public CourseSet getCourseSet()
    {
        int setID = edma_dmview.relationAsBGetASet(1, edma_entity.getID());
        return new CourseSetImpl(setID, edma_dmview);
    }

    /**
     * Returns a CourseSet where startDate equals the provided startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate equals the provided
     *                   startDate.
     */
    public CourseSet getCourseSetWhereStartDateEquals(Date startDate)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) startDate).edma_getValue();
        int newSetID = edma_dmview.getRelationIndexOnA(1, 0, edma_entity.getID()).getEquals(edma_values);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a CourseSet where startDate is less than the provided startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate is less than the provided
     *                   startDate.
     */
    public CourseSet getCourseSetWhereStartDateLessThan(Date startDate)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) startDate).edma_getValue();
        int newSetID = edma_dmview.getRelationIndexOnA(1, 0, edma_entity.getID()).getLessThan(edma_values);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a CourseSet where startDate is less than or equal to the
     * provided startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate is less than or equal to
     *                   the provided startDate.
     */
    public CourseSet getCourseSetWhereStartDateLessThanOrEqual(Date startDate)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) startDate).edma_getValue();
        int newSetID = edma_dmview.getRelationIndexOnA(1, 0, edma_entity.getID()).getLessThanOrEqual(edma_values);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a CourseSet where startDate is greater than the provided
     * startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate is greater than the
     *                   provided startDate.
     */
    public CourseSet getCourseSetWhereStartDateGreaterThan(Date startDate)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) startDate).edma_getValue();
        int newSetID = edma_dmview.getRelationIndexOnA(1, 0, edma_entity.getID()).getGreaterThan(edma_values);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a CourseSet where startDate is greater than or equal to the
     * provided startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate is greater than or equal
     *                   to the provided startDate.
     */
    public CourseSet getCourseSetWhereStartDateGreaterThanOrEqual(Date startDate)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) startDate).edma_getValue();
        int newSetID = edma_dmview.getRelationIndexOnA(1, 0, edma_entity.getID()).getGreaterThanOrEqual(edma_values);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns the subset of this CourseSet where startDate is in the given
     * range.
     * @param minStartDate  Minimum value for startDate
     * @param minInclusive  <tt>true</tt> if the minimum value should be
     *                      included.
     * @param maxStartDate  Maximum value for startDate
     * @param maxInclusive  <tt>true</tt> if the maximum value should be
     *                      included.
     * @return              The subset of this CourseSet where startDate is in
     *                      the given range.
     */
    public CourseSet getCourseSetWhereStartDateInRange(Date minStartDate, boolean minInclusive, Date maxStartDate, boolean maxInclusive)
    {
        Object[] edma_minValues = new Object[1];
        Object[] edma_maxValues = new Object[1];
        edma_minValues[0] = ((IValueInstance) minStartDate).edma_getValue();
        edma_maxValues[0] = ((IValueInstance) maxStartDate).edma_getValue();
        int newSetID = edma_dmview.getRelationIndexOnA(1, 0, edma_entity.getID()).getRange(edma_minValues, minInclusive, edma_maxValues, maxInclusive);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a CourseSet where status and startDate equals the provided
     * status and startDate.
     * @param status     Value for status
     * @param startDate  Value for startDate
     * @return           A CourseSet where status and startDate equals the
     *                   provided status and startDate.
     */
    public CourseSet getCourseSetWhereStatusAndStartDateEquals(CourseStatus status, Date startDate)
    {
        Object[] edma_values = new Object[2];
        edma_values[0] = ((IValueInstance) status).edma_getValue();
        edma_values[1] = ((IValueInstance) startDate).edma_getValue();
        int newSetID = edma_dmview.getRelationIndexOnA(1, 1, edma_entity.getID()).getEquals(edma_values);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a CourseSet where status and startDate is less than the provided
     * status and startDate.
     * @param status     Value for status
     * @param startDate  Value for startDate
     * @return           A CourseSet where status and startDate is less than
     *                   the provided status and startDate.
     */
    public CourseSet getCourseSetWhereStatusAndStartDateLessThan(CourseStatus status, Date startDate)
    {
        Object[] edma_values = new Object[2];
        edma_values[0] = ((IValueInstance) status).edma_getValue();
        edma_values[1] = ((IValueInstance) startDate).edma_getValue();
        int newSetID = edma_dmview.getRelationIndexOnA(1, 1, edma_entity.getID()).getLessThan(edma_values);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a CourseSet where status and startDate is less than or equal to
     * the provided status and startDate.
     * @param status     Value for status
     * @param startDate  Value for startDate
     * @return           A CourseSet where status and startDate is less than or
     *                   equal to the provided status and startDate.
     */
    public CourseSet getCourseSetWhereStatusAndStartDateLessThanOrEqual(CourseStatus status, Date startDate)
    {
        Object[] edma_values = new Object[2];
        edma_values[0] = ((IValueInstance) status).edma_getValue();
        edma_values[1] = ((IValueInstance) startDate).edma_getValue();
        int newSetID = edma_dmview.getRelationIndexOnA(1, 1, edma_entity.getID()).getLessThanOrEqual(edma_values);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a CourseSet where status and startDate is greater than the
     * provided status and startDate.
     * @param status     Value for status
     * @param startDate  Value for startDate
     * @return           A CourseSet where status and startDate is greater than
     *                   the provided status and startDate.
     */
    public CourseSet getCourseSetWhereStatusAndStartDateGreaterThan(CourseStatus status, Date startDate)
    {
        Object[] edma_values = new Object[2];
        edma_values[0] = ((IValueInstance) status).edma_getValue();
        edma_values[1] = ((IValueInstance) startDate).edma_getValue();
        int newSetID = edma_dmview.getRelationIndexOnA(1, 1, edma_entity.getID()).getGreaterThan(edma_values);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a CourseSet where status and startDate is greater than or equal
     * to the provided status and startDate.
     * @param status     Value for status
     * @param startDate  Value for startDate
     * @return           A CourseSet where status and startDate is greater than
     *                   or equal to the provided status and startDate.
     */
    public CourseSet getCourseSetWhereStatusAndStartDateGreaterThanOrEqual(CourseStatus status, Date startDate)
    {
        Object[] edma_values = new Object[2];
        edma_values[0] = ((IValueInstance) status).edma_getValue();
        edma_values[1] = ((IValueInstance) startDate).edma_getValue();
        int newSetID = edma_dmview.getRelationIndexOnA(1, 1, edma_entity.getID()).getGreaterThanOrEqual(edma_values);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns the subset of this CourseSet where status and startDate are in
     * the given range.
     * @param minStatus     Minimum value for status
     * @param minStartDate  Minimum value for startDate
     * @param minInclusive  <tt>true</tt> if the minimum value should be
     *                      included.
     * @param maxStatus     Maximum value for status
     * @param maxStartDate  Maximum value for startDate
     * @param maxInclusive  <tt>true</tt> if the maximum value should be
     *                      included.
     * @return              The subset of this CourseSet where status and
     *                      startDate are in the given range.
     */
    public CourseSet getCourseSetWhereStatusAndStartDateInRange(CourseStatus minStatus, Date minStartDate, boolean minInclusive, CourseStatus maxStatus, Date maxStartDate, boolean maxInclusive)
    {
        Object[] edma_minValues = new Object[2];
        Object[] edma_maxValues = new Object[2];
        edma_minValues[0] = ((IValueInstance) minStatus).edma_getValue();
        edma_maxValues[0] = ((IValueInstance) maxStatus).edma_getValue();
        edma_minValues[1] = ((IValueInstance) minStartDate).edma_getValue();
        edma_maxValues[1] = ((IValueInstance) maxStartDate).edma_getValue();
        int newSetID = edma_dmview.getRelationIndexOnA(1, 1, edma_entity.getID()).getRange(edma_minValues, minInclusive, edma_maxValues, maxInclusive);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * This methods follows the relation PassedCourses
     * @return  The result of following the relation PassedCourses
     */
    public StudentSet asPassedCourseGetStudentSet()
    {
        int setID = edma_dmview.relationAsBGetASet(2, edma_entity.getID());
        return new StudentSetImpl(setID, edma_dmview);
    }

    /**
     * This methods follows the relation TeachingAbility
     * @return  The result of following the relation TeachingAbility
     */
    public TeacherSet getTeacherSet()
    {
        int setID = edma_dmview.relationAsBGetASet(4, edma_entity.getID());
        return new TeacherSetImpl(setID, edma_dmview);
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
        if(!(o instanceof CourseTypeViewer)) return false;
        CourseTypeViewer other = (CourseTypeViewer) o;
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
     * Connects the required to this CourseType, if it is not already connected.
     *  This method has been generated from the relation:
     *  CourseDependency
     * @param required  The required to be added. May NOT be null.
     * @return          <tt>true</tt> if this courseType was not already
     *                  connected to the specified required
     */
    public boolean addRequired(CourseTypeViewer required)
    {
        CourseTypeVUImpl B = (CourseTypeVUImpl) required;
        return edma_dmview.getUpdateInterface().relationAdd(0, edma_entity.getID(), B.edma_entity.getID());
    }

    /**
     * Removes the required from this CourseType, if it is connected.
     *  This method has been generated from the relation:
     *  CourseDependency
     * @param required  The required to be connected to this courseType. May
     *                  NOT be null.
     * @return          <tt>true</tt> if the specified required was connected
     *                  to this courseType
     */
    public boolean removeRequired(CourseTypeViewer required)
    {
        CourseTypeVUImpl edma_b = (CourseTypeVUImpl) required;
        return edma_dmview.getUpdateInterface().relationDelete(0, edma_entity.getID(), edma_b.edma_entity.getID());
    }
}
