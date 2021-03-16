package com.cphdiving.divewithus.edma.usercode.models.coursereg.views;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.views.SearchCoursesResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.Date;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfoList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.CourseRegUtil;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class SearchCoursesUserImpl extends Result implements SearchCoursesResult
{
    private static final int OK = 0;
    private static final int COURSE_TYPE_DOES_NOT_EXIST = 1;
    private final CourseTypeID in_courseType;
    private final Date in_start;
    private final Date in_end;
    private CourseInfoList out_courseList;



    /**
     * Constructor
     * @param in_courseType  Input parameter 1
     * @param in_start       Input parameter 2
     * @param in_end         Input parameter 3
     */
    public SearchCoursesUserImpl(CourseTypeID in_courseType, Date in_start, Date in_end)
    {
        this.in_courseType = in_courseType;
        this.in_start = in_start;
        this.in_end = in_end;
        out_courseList = null;
    }

    /**
     * Execution of the view
     * @param view  View interface
     * @return      Return 0 if succesful or one of the error codes if not
     */
    public int execute(CourseRegViewer view)
    {
        // Implementation of searchCourses
        // Return one of the following error codes:
        // OK
        // COURSE_TYPE_DOES_NOT_EXIST
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin
        CourseSet courses;
        if(in_courseType == null)
        {
            courses = view.getCourseKind().getWhereStartDateInRange(in_start, true, in_end, true);
        }
        else
        {
            CourseTypeViewer type = view.getCourseTypeKind().getFromID(in_courseType);
            if(type == null)
            {
                return COURSE_TYPE_DOES_NOT_EXIST;
            }
            courses = type.getCourseSetWhereStartDateInRange(in_start, true, in_end, true);
        }
        out_courseList = CourseRegUtil.extractCourseInfoList(courses.orderByStartDate().subOrderByStartTime());
        return OK;
        // EDMA_non-generated_code_end
    }

    /**
     * Returns the output courseList:CourseInfoList
     * @return  The output courseList:CourseInfoList
     */
    public CourseInfoList getCourseList()
    {
        if(errorCode() != 0) return null;
        return out_courseList;
    }
}
