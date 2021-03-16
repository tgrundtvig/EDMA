package com.cphdiving.divewithus.edma.usercode.models.coursereg.actions;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.CreateTeacherResult;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.PosInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherID;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class CreateTeacherUserImpl extends Result implements CreateTeacherResult
{
    private static final int OK = 0;
    private static final int PERSON_DOES_NOT_EXIST = 1;
    private static final int PERSON_IS_ALREADY_A_TEACHER = 2;
    private final PersonID in_personID;
    private final PosInt in_salary;
    private TeacherID out_id;



    /**
     * Constructor
     * @param in_personID  Input parameter 1
     * @param in_salary    Input parameter 2
     */
    public CreateTeacherUserImpl(PersonID in_personID, PosInt in_salary)
    {
        this.in_personID = in_personID;
        this.in_salary = in_salary;
        out_id = null;
    }

    /**
     * Execution of the action
     * @param upd  Update interface
     * @return     Return 0 to commit or one of the error codes to roll back
     */
    public int execute(CourseRegUpdater upd)
    {
        // Implementation of createTeacher
        // Return one of the following error codes:
        // OK
        // PERSON_DOES_NOT_EXIST
        // PERSON_IS_ALREADY_A_TEACHER
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin
    	
		PersonViewer person = upd.getPersonKind().getFromID(in_personID);
		if(person == null)
		{
			return PERSON_DOES_NOT_EXIST;
		}

		if(person.asTeacher() != null)
		{
			return PERSON_IS_ALREADY_A_TEACHER;
		}

		TeacherViewer teacher = upd.newTeacher(person).salary(in_salary);
		out_id = teacher.getID();
		return OK;
		
		
		
        // EDMA_non-generated_code_end
    }

    /**
     * Returns the output id:TeacherID
     * @return  The output id:TeacherID
     */
    public TeacherID getId()
    {
        if(errorCode() != 0) return null;
        return out_id;
    }
}
