package com.cphdiving.divewithus.edma.generated.coursereg.kinds.schoolinfo;

import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.SchoolInfo;

/**
 * This is the viewing interface for SchoolInfo
 */
public interface SchoolInfoViewer
{

    /**
     * Create a copy of this SchoolInfo at this instance in time
     * @return  A copy of this SchoolInfo
     */
    public SchoolInfo snapshot();

    /**
     * Returns the attribute name of this SchoolInfo
     * @return  The value of the attribute name
     */
    public Name getName();

}
