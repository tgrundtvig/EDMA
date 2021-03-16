package com.cphdiving.divewithus.edma.generated.coursereg.kinds.student;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.WetsuitSize;

/**
 * Interface for the kind: Student
 */
public interface StudentKind
{

    /**
     * Get an entity from its id.
     * @param id  The id of the entity to get
     * @return    A viewer on the entity with the given ID or null if none
     *            exists.
     */
    public StudentViewer getFromID(StudentID id);

    /**
     * Returns the set of all Student entities.
     * @return  The set of all Student entities.
     */
    public StudentSet getAll();

    /**
     * Returns a StudentSet where wetsuitSize equals the provided wetsuitSize.
     * @param wetsuitSize  Value for wetsuitSize
     * @return             A StudentSet where wetsuitSize equals the provided
     *                     wetsuitSize.
     */
    public StudentSet getWhereWetsuitSizeEquals(WetsuitSize wetsuitSize);

}
