package com.cphdiving.divewithus.edma.usercode.models.coursereg.views;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseSet;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentSet;
import com.cphdiving.divewithus.edma.generated.coursereg.views.SellWetsuitResult;
import com.cphdiving.divewithus.edma.generated.valuedomains.Date;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseStatus;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.WetsuitSize;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class SellWetsuitUserImpl extends Result implements SellWetsuitResult
{
    private static final int OK = 0;
    private final WetsuitSize in_size;
    private final Date in_date;
    private PersonList out_teachers;



    /**
     * Constructor
     * @param in_size  Input parameter 1
     * @param in_date  Input parameter 2
     */
    public SellWetsuitUserImpl(WetsuitSize in_size, Date in_date)
    {
        this.in_size = in_size;
        this.in_date = in_date;
        out_teachers = null;
    }

    /**
     * Execution of the view
     * @param view  View interface
     * @return      Return 0 if succesful or one of the error codes if not
     */
    public int execute(CourseRegViewer view)
    {
        // Implementation of sellWetsuit
        // Return one of the following error codes:
        // OK
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin
		
		CourseSet courses = view.getCourseKind()
								.getWhereStatusEquals(CourseStatus.create("Ready"));
		
		courses = courses.intersect(view.getCourseKind()
										.getWhereStartDateEquals(in_date));
		
		StudentSet students = view.getStudentKind()
									.getWhereWetsuitSizeEquals(in_size);
		
		courses = courses.intersect(students.asStudentSetGetCourseSet());
		
		out_teachers = courses.asCourseSetGetTeacherSet()
								.asPersonSet()
								.snapshot();
		return OK;
        // EDMA_non-generated_code_end
    }

    /**
     * Returns the output teachers:PersonList
     * @return  The output teachers:PersonList
     */
    public PersonList getTeachers()
    {
        if(errorCode() != 0) return null;
        return out_teachers;
    }
}
