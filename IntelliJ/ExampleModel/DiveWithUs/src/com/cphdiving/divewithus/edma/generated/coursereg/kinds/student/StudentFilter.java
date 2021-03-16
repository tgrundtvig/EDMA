package com.cphdiving.divewithus.edma.generated.coursereg.kinds.student;


/**
 * Filter interface for the Student kind.
 */
public interface StudentFilter
{

    /**
     * Returns <tt>true</tt> if the entity is accepted by the filter.
     * @param student  The entity to be tested by the filter.
     * @return         <tt>true</tt> if the entity is accepted by the filter.
     */
    public boolean accept(StudentViewer student);

}
