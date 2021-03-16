package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseKind;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseViewer;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseVUImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.Date;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.valuedomains.IValueInstance;

/**
 * 
 */
public class CourseKindImpl implements CourseKind
{
    private int edma_kindIndex;
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param edma_kindIndex  kind index
     * @param edma_dmview     Internal runtime interface
     */
    public CourseKindImpl(int edma_kindIndex, IDataModelView edma_dmview)
    {
        this.edma_kindIndex = edma_kindIndex;
        this.edma_dmview = edma_dmview;
    }

    /**
     * Get an entity from its id.
     * @param id  The id of the entity to get
     * @return    A viewer on the entity with the given ID or null if none
     *            exists.
     */
    public CourseViewer getFromID(CourseID id)
    {
        IEntity res = edma_dmview.kindGetFromID(edma_kindIndex, id.value());
        if(res == null) return null;
        return new CourseVUImpl(res, edma_dmview);
    }

    /**
     * Returns the set of all Course entities.
     * @return  The set of all Course entities.
     */
    public CourseSet getAll()
    {
        int newSetID = edma_dmview.kindGetAll(4);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a CourseSet where startDate equals the provided startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate equals the provided
     *                   startDate.
     */
    public CourseSet getWhereStartDateEquals(Date startDate)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) startDate).edma_getValue();
        int newSetID = edma_dmview.getKindIndex(4, 0).getEquals(edma_values);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a CourseSet where startDate is less than the provided startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate is less than the provided
     *                   startDate.
     */
    public CourseSet getWhereStartDateLessThan(Date startDate)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) startDate).edma_getValue();
        int newSetID = edma_dmview.getKindIndex(4, 0).getLessThan(edma_values);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a CourseSet where startDate is less than or equal to the
     * provided startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate is less than or equal to
     *                   the provided startDate.
     */
    public CourseSet getWhereStartDateLessThanOrEqual(Date startDate)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) startDate).edma_getValue();
        int newSetID = edma_dmview.getKindIndex(4, 0).getLessThanOrEqual(edma_values);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a CourseSet where startDate is greater than the provided
     * startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate is greater than the
     *                   provided startDate.
     */
    public CourseSet getWhereStartDateGreaterThan(Date startDate)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) startDate).edma_getValue();
        int newSetID = edma_dmview.getKindIndex(4, 0).getGreaterThan(edma_values);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a CourseSet where startDate is greater than or equal to the
     * provided startDate.
     * @param startDate  Value for startDate
     * @return           A CourseSet where startDate is greater than or equal
     *                   to the provided startDate.
     */
    public CourseSet getWhereStartDateGreaterThanOrEqual(Date startDate)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) startDate).edma_getValue();
        int newSetID = edma_dmview.getKindIndex(4, 0).getGreaterThanOrEqual(edma_values);
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
    public CourseSet getWhereStartDateInRange(Date minStartDate, boolean minInclusive, Date maxStartDate, boolean maxInclusive)
    {
        Object[] edma_minValues = new Object[1];
        Object[] edma_maxValues = new Object[1];
        edma_minValues[0] = ((IValueInstance) minStartDate).edma_getValue();
        edma_maxValues[0] = ((IValueInstance) maxStartDate).edma_getValue();
        int newSetID = edma_dmview.getKindIndex(4, 0).getRange(edma_minValues, minInclusive, edma_maxValues, maxInclusive);
        return new CourseSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a CourseSet where status equals the provided status.
     * @param status  Value for status
     * @return        A CourseSet where status equals the provided status.
     */
    public CourseSet getWhereStatusEquals(CourseStatus status)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) status).edma_getValue();
        int newSetID = edma_dmview.getKindIndex(4, 1).getEquals(edma_values);
        return new CourseSetImpl(newSetID, edma_dmview);
    }
}
