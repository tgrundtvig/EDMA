package com.cphdiving.divewithus.edma.usercode.valueconstraints.coursesize;

import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseSize;

/**
 * This class is the implementation class for the CourseSize constraint
 * minLTEMax
 * No description given
 */
public class MinLTEMax
{



    /**
     * Checks the minLTEMax constraint for the CourseSize value domain.
     * No description given
     * @param courseSize  The instance value to be checked
     * @return            the reason the constraint is violated, or
     *                    <tt>null</tt> if the constraint is not violated
     */
    public static String checkMinLTEMax(CourseSize courseSize)
    {
        // Implementation of constraint minLTEMax
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin

		// TODO: Implement the constraint minLTEMax here...
		int min = courseSize.min().value();
		int max = courseSize.max().value();
		if(min > max) return "min (" + min
				+ ") must be less than or equal to max (" + max + ")";
		return null;
        // EDMA_non-generated_code_end
    }


}
