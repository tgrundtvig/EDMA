package com.cphdiving.divewithus.edma.usercode.models.coursereg.actions;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.CreateCourseTypeResult;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.PosInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseName;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;
import org.abstractica.edma.runtime.implementations.common.Result;
import org.abstractica.edma.runtime.intf.exceptions.UniqueException;

/**
 * 
 */
public class CreateCourseTypeUserImpl extends Result implements CreateCourseTypeResult
{
    private static final int OK = 0;
    private static final int COURSE_TYPE_ALREADY_EXISTS = 1;
    private final CourseName in_name;
    private final CourseSize in_size;
    private final PosInt in_price;
    private CourseTypeID out_id;



    /**
     * Constructor
     * @param in_name   Input parameter 1
     * @param in_size   Input parameter 2
     * @param in_price  Input parameter 3
     */
    public CreateCourseTypeUserImpl(CourseName in_name, CourseSize in_size, PosInt in_price)
    {
        this.in_name = in_name;
        this.in_size = in_size;
        this.in_price = in_price;
        out_id = null;
    }

    /**
     * Execution of the action
     * @param upd  Update interface
     * @return     Return 0 to commit or one of the error codes to roll back
     */
    public int execute(CourseRegUpdater upd)
    {
        // Implementation of createCourseType
        // Return one of the following error codes:
        // OK
        // COURSE_TYPE_ALREADY_EXISTS
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin

		CourseTypeViewer courseType;
		try
		{
			courseType = upd.newCourseType()
							.name(in_name)
							.size(in_size)
							.price(in_price);
		}
		catch(UniqueException e)
		{
			return COURSE_TYPE_ALREADY_EXISTS;
		}
		out_id = courseType.getID();
		return OK;

        // EDMA_non-generated_code_end
    }

    /**
     * Returns the output id:CourseTypeID
     * @return  The output id:CourseTypeID
     */
    public CourseTypeID getId()
    {
        if(errorCode() != 0) return null;
        return out_id;
    }
}
