package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeKind;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseTypeSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseTypeVUImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseName;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.valuedomains.IValueInstance;

/**
 * 
 */
public class CourseTypeKindImpl implements CourseTypeKind
{
    private int edma_kindIndex;
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param edma_kindIndex  kind index
     * @param edma_dmview     Internal runtime interface
     */
    public CourseTypeKindImpl(int edma_kindIndex, IDataModelView edma_dmview)
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
    public CourseTypeViewer getFromID(CourseTypeID id)
    {
        IEntity res = edma_dmview.kindGetFromID(edma_kindIndex, id.value());
        if(res == null) return null;
        return new CourseTypeVUImpl(res, edma_dmview);
    }

    /**
     * Returns the set of all CourseType entities.
     * @return  The set of all CourseType entities.
     */
    public CourseTypeSet getAll()
    {
        int newSetID = edma_dmview.kindGetAll(3);
        return new CourseTypeSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns the unique courseType from the unique-index on name or
     * <tt>null</tt> if there is no courseType with the given name.
     * @param name  Value for name
     * @return      The unique courseType from the unique-index on name or
     *              <tt>null</tt> if there is no courseType with the given name.
     */
    public CourseTypeViewer getFromName(CourseName name)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) name).edma_getValue();
        IEntity res = edma_dmview.getKindIndex(3, 0).getFromUnique(edma_values);
        if(res == null) return null;
        return new CourseTypeVUImpl(res, edma_dmview);
    }
}
