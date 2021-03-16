package com.cphdiving.divewithus.edma.usercode.models.coursereg.actions;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.CreateStudentResult;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.BootSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.WetsuitSize;
import org.abstractica.edma.runtime.implementations.common.Result;

/**
 * 
 */
public class CreateStudentUserImpl extends Result implements CreateStudentResult
{
    private static final int OK = 0;
    private static final int PERSON_DOES_NOT_EXIST = 1;
    private static final int PERSON_IS_ALREADY_A_STUDENT = 2;
    private final PersonID in_personID;
    private final WetsuitSize in_wetsuitSize;
    private final BootSize in_bootSize;
    private StudentID out_id;



    /**
     * Constructor
     * @param in_personID     Input parameter 1
     * @param in_wetsuitSize  Input parameter 2
     * @param in_bootSize     Input parameter 3
     */
    public CreateStudentUserImpl(PersonID in_personID, WetsuitSize in_wetsuitSize, BootSize in_bootSize)
    {
        this.in_personID = in_personID;
        this.in_wetsuitSize = in_wetsuitSize;
        this.in_bootSize = in_bootSize;
        out_id = null;
    }

    /**
     * Execution of the action
     * @param upd  Update interface
     * @return     Return 0 to commit or one of the error codes to roll back
     */
    public int execute(CourseRegUpdater upd)
    {
        // Implementation of createStudent
        // Return one of the following error codes:
        // OK
        // PERSON_DOES_NOT_EXIST
        // PERSON_IS_ALREADY_A_STUDENT
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin

		PersonViewer person = upd.getPersonKind().getFromID(in_personID);
		if(person == null)
		{
			return PERSON_DOES_NOT_EXIST;
		}

		if(person.asStudent() != null)
		{
			return PERSON_IS_ALREADY_A_STUDENT;
		}

		StudentViewer student = upd.newStudent(person)
									.wetsuitSize(in_wetsuitSize)
									.bootSize(in_bootSize);
		out_id = student.getID();
		return OK;

        // EDMA_non-generated_code_end
    }

    /**
     * Returns the output id:StudentID
     * @return  The output id:StudentID
     */
    public StudentID getId()
    {
        if(errorCode() != 0) return null;
        return out_id;
    }
}
