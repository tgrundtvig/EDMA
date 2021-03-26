package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentKind;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentViewer;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.StudentSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.StudentVUImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.WetsuitSize;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IEntity;
import org.abstractica.edma.valuedomains.IValueInstance;

/**
 * 
 */
public class StudentKindImpl implements StudentKind
{
    private int edma_kindIndex;
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param edma_kindIndex  kind index
     * @param edma_dmview     Internal runtime interface
     */
    public StudentKindImpl(int edma_kindIndex, IDataModelView edma_dmview)
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
    public StudentViewer getFromID(StudentID id)
    {
        IEntity res = edma_dmview.kindGetFromID(edma_kindIndex, id.value());
        if(res == null) return null;
        return new StudentVUImpl(res, edma_dmview);
    }

    /**
     * Returns an empty set of Student entities.
     * @return  An empty set of Student entities.
     */
    public StudentSet getEmptyStudentSet()
    {
        int newSetID = edma_dmview.kindGetEmptySet(2);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns the set of all Student entities.
     * @return  The set of all Student entities.
     */
    public StudentSet getAll()
    {
        int newSetID = edma_dmview.kindGetAll(2);
        return new StudentSetImpl(newSetID, edma_dmview);
    }

    /**
     * Returns a StudentSet where wetsuitSize equals the provided wetsuitSize.
     * @param wetsuitSize  Value for wetsuitSize
     * @return             A StudentSet where wetsuitSize equals the provided
     *                     wetsuitSize.
     */
    public StudentSet getWhereWetsuitSizeEquals(WetsuitSize wetsuitSize)
    {
        Object[] edma_values = new Object[1];
        edma_values[0] = ((IValueInstance) wetsuitSize).edma_getValue();
        int newSetID = edma_dmview.getKindIndex(2, 0).getEquals(edma_values);
        return new StudentSetImpl(newSetID, edma_dmview);
    }
}
