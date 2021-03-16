package com.cphdiving.divewithus.edma.usercode.models.coursereg.actions;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.AddCourseTypeRequirementResult;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.CourseRegUtil;
import java.util.HashSet;
import java.util.Set;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class AddCourseTypeRequirementUserImpl extends Result implements AddCourseTypeRequirementResult
{
    private static final int OK = 0;
    private static final int COURSE_TYPE_DOES_NOT_EXIST = 1;
    private static final int REQUIRED_DOES_NOT_EXIST = 2;
    private static final int DEPENDENCY_ALREADY_EXISTS = 3;
    private static final int CIRCULAR_DEPENDENCY = 4;
    private final CourseTypeID in_courseTypeID;
    private final CourseTypeID in_requiredID;



    /**
     * Constructor
     * @param in_courseTypeID  Input parameter 1
     * @param in_requiredID    Input parameter 2
     */
    public AddCourseTypeRequirementUserImpl(CourseTypeID in_courseTypeID, CourseTypeID in_requiredID)
    {
        this.in_courseTypeID = in_courseTypeID;
        this.in_requiredID = in_requiredID;
    }

    /**
     * Execution of the action
     * @param upd  Update interface
     * @return     Return 0 to commit or one of the error codes to roll back
     */
    public int execute(CourseRegUpdater upd)
    {
        // Implementation of addCourseTypeRequirement
        // Return one of the following error codes:
        // OK
        // COURSE_TYPE_DOES_NOT_EXIST
        // REQUIRED_DOES_NOT_EXIST
        // DEPENDENCY_ALREADY_EXISTS
        // CIRCULAR_DEPENDENCY
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin
    	
		CourseTypeViewer courseType = upd.getCourseTypeKind()
											.getFromID(in_courseTypeID);
		if(courseType == null)
		{
			return COURSE_TYPE_DOES_NOT_EXIST;
		}
		CourseTypeViewer dependency = upd.getCourseTypeKind()
											.getFromID(in_requiredID);
		if(dependency == null)
		{
			return REQUIRED_DOES_NOT_EXIST;
		}

		boolean res = upd.update(courseType).addRequired(dependency);
		if(!res)
		{
			return DEPENDENCY_ALREADY_EXISTS;
		}

		Set<String> visited = new HashSet<>();
		String name = courseType.getName().value();
		visited.add(name);

		String circle = CourseRegUtil.testCircularDependency(	dependency,
																visited,
																name);
		if(circle != null)
		{
			setErrorDescription(circle);
			return CIRCULAR_DEPENDENCY;
		}

		return OK;

        // EDMA_non-generated_code_end
    }
}
