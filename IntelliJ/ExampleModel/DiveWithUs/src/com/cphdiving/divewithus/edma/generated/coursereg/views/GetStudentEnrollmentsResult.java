package com.cphdiving.divewithus.edma.generated.coursereg.views;

import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfoList;
import org.abstractica.edma.runtime.intf.IResult;

/**
 * Access to the result of the method: getStudentEnrollments
 */
public interface GetStudentEnrollmentsResult extends IResult
{

    /**
     * Returns the output courseList:CourseInfoList
     * @return  The output courseList:CourseInfoList
     */
    public CourseInfoList getCourseList();

}
