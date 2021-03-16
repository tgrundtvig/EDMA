package com.cphdiving.divewithus.edma.usercode.models.coursereg.actions;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseRegUpdater;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.CreatePersonResult;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonUpdater;
import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.Mobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.NotNegInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonID;
import org.abstractica.edma.runtime.implementations.common.Result;
import org.abstractica.edma.runtime.intf.exceptions.UniqueException;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;

/**
 * 
 */
public class CreatePersonUserImpl extends Result implements CreatePersonResult
{
    private static final int OK = 0;
    private static final int EMAIL_ALREADY_EXISTS = 1;
    private static final int MOBILE_ALREADY_EXISTS = 2;
    private final Name in_name;
    private final Email in_email;
    private final Mobile in_mobile;
    private PersonID out_id;



    /**
     * Constructor
     * @param in_name    Input parameter 1
     * @param in_email   Input parameter 2
     * @param in_mobile  Input parameter 3
     */
    public CreatePersonUserImpl(Name in_name, Email in_email, Mobile in_mobile)
    {
        this.in_name = in_name;
        this.in_email = in_email;
        this.in_mobile = in_mobile;
        out_id = null;
    }

    /**
     * Execution of the action
     * @param upd  Update interface
     * @return     Return 0 to commit or one of the error codes to roll back
     */
    public int execute(CourseRegUpdater upd)
    {
        // Implementation of createPerson
        // Return one of the following error codes:
        // OK
        // EMAIL_ALREADY_EXISTS
        // MOBILE_ALREADY_EXISTS
        
        // If an error needs extra explanation, use: setErrorDescription("Extra info");
        
        // WARNING : Any code outside the following begin and end tags
        // will be lost when re-generation occurs.
        
        // EDMA_non-generated_code_begin

		PersonUpdater person;
		try
		{
			person = upd.newPerson()
						.name(in_name)
						.email(in_email)
						.mobile(in_mobile)
						.balance(NotNegInt.create(0));
		}
		catch(InvalidValueException e)
		{
			throw new RuntimeException("Should not happen since 0 is not negative...");
		}
		catch(UniqueException e)
		{
			if("email".equals(e.getViolatedIndex().getAttribute(0).getName())) return EMAIL_ALREADY_EXISTS;
			else return MOBILE_ALREADY_EXISTS;
		}
		out_id = person.getID();
		return OK;
        // EDMA_non-generated_code_end
    }

    /**
     * Returns the output id:PersonID
     * @return  The output id:PersonID
     */
    public PersonID getId()
    {
        if(errorCode() != 0) return null;
        return out_id;
    }
}
