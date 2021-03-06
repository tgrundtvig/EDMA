package com.cphdiving.divewithus.edma.generated.valuedomains.external;

import com.cphdiving.divewithus.edma.generated.valuedomains.Date;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.CourseSizeImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.DateImpl;
import com.cphdiving.divewithus.edma.usercode.valueconstraints.coursesize.MinLTEMax;
import com.cphdiving.divewithus.edma.usercode.valueconstraints.date.ValidDate;
import org.abstractica.edma.util.Pair;
import org.abstractica.edma.valuedomains.IExternalConstraints;

/**
 * 
 */
public class EDMA_ExternalConstraints implements IExternalConstraints
{
    public static final IExternalConstraints instance = new EDMA_ExternalConstraints();



    /**
     * Constructor
     */
    public EDMA_ExternalConstraints()
    {
        
    }

    /**
     * check external constraints
     * @param edma_obj    The value object to check
     * @param edma_index  Value domain index
     * @return            A pair of the index of the first constraint that is
     *                    violated and the error message or <tt>null</tt> if no
     *                    constraints are violated
     */
    public Pair<Integer, String> checkConstraints(Object edma_obj, int edma_index)
    {
        switch(edma_index)
        {
            case 3:
            {
                Date date = new DateImpl(edma_obj);
                String edma_reason;
                edma_reason = ValidDate.checkValidDate(date);
                if(edma_reason != null) return new Pair<Integer, String>(0, edma_reason);
                return null;
            }
            case 24:
            {
                CourseSize courseSize = new CourseSizeImpl(edma_obj);
                String edma_reason;
                edma_reason = MinLTEMax.checkMinLTEMax(courseSize);
                if(edma_reason != null) return new Pair<Integer, String>(0, edma_reason);
                return null;
            }
            default :
                throw new RuntimeException("Internal Error!");
        }
    }
}
