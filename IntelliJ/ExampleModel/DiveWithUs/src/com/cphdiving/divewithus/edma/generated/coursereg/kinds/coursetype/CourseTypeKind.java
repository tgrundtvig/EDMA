package com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseName;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;

/**
 * Interface for the kind: CourseType
 */
public interface CourseTypeKind
{

    /**
     * Get an entity from its id.
     * @param id  The id of the entity to get
     * @return    A viewer on the entity with the given ID or null if none
     *            exists.
     */
    public CourseTypeViewer getFromID(CourseTypeID id);

    /**
     * Returns an empty set of CourseType entities.
     * @return  An empty set of CourseType entities.
     */
    public CourseTypeSet getEmptyCourseTypeSet();

    /**
     * Returns the set of all CourseType entities.
     * @return  The set of all CourseType entities.
     */
    public CourseTypeSet getAll();

    /**
     * Returns the unique courseType from the unique-index on name or
     * <tt>null</tt> if there is no courseType with the given name.
     * @param name  Value for name
     * @return      The unique courseType from the unique-index on name or
     *              <tt>null</tt> if there is no courseType with the given name.
     */
    public CourseTypeViewer getFromName(CourseName name);

}
