package com.cphdiving.divewithus.edma.usercode.models.coursereg.views;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.views.GetStudentEnrollmentsResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfoList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentID;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.CourseRegUtil;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class GetStudentEnrollmentsUserImpl extends Result implements GetStudentEnrollmentsResult
{
    private static final int OK = 0;
    private static final int STUDENT_DOES_NOT_EXIST = 1;
    private final StudentID in_studentID;
    private CourseInfoList out_courseList;



    /**
     * Constructor
     * @param in_studentID  Input parameter 1
     */
    public GetStudentEnrollmentsUserImpl(StudentID in_studentID)
    {
        this.in_studentID = in_studentID;
        out_courseList = null;
    }

    /**
     * Execution of the view
     * @param view  View interface
     * @return      Return 0 if succesful or one of the error codes if not
     */
    public int execute(CourseRegViewer view)
    {
        // Implementation of getStudentEnrollments
        // Return one of the following error codes:
        // OK
        // STUDENT_DOES_NOT_EXIST
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin
        StudentViewer student = view.getStudentKind().getFromID(in_studentID);
        if(student == null)
        	return STUDENT_DOES_NOT_EXIST;
        
        CourseSet courses = student.getCourseSet();
        CourseInfoList.CourseInfoListBuilder list = CourseInfoList.begin();
        for(CourseViewer course : courses)
        {
        	list.add(CourseRegUtil.extractCourseInfo(course));
        }
        out_courseList = list.end();
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
