package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherKind;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherViewer;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.TeacherSetImpl;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.TeacherVUImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherID;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.runtime.intf.IEntity;

/**
 * 
 */
public class TeacherKindImpl implements TeacherKind
{
    private int edma_kindIndex;
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param edma_kindIndex  kind index
     * @param edma_dmview     Internal runtime interface
     */
    public TeacherKindImpl(int edma_kindIndex, IDataModelView edma_dmview)
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
    public TeacherViewer getFromID(TeacherID id)
    {
        IEntity res = edma_dmview.kindGetFromID(edma_kindIndex, id.value());
        if(res == null) return null;
        return new TeacherVUImpl(res, edma_dmview);
    }

    /**
     * Returns the set of all Teacher entities.
     * @return  The set of all Teacher entities.
     */
    public TeacherSet getAll()
    {
        int newSetID = edma_dmview.kindGetAll(1);
        return new TeacherSetImpl(newSetID, edma_dmview);
    }
}
