package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.CourseReg;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.AddCourseTypeRequirementResult;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.AddCourseTypeToTeacherResult;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.AddPassedCourseToStudentResult;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.AddStudentToCourseResult;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.CreateCourseResult;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.CreateCourseTypeResult;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.CreatePersonResult;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.CreateStudentResult;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.CreateTeacherResult;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.SetSchoolNameResult;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.SetTeacherOnCourseResult;
import com.cphdiving.divewithus.edma.generated.coursereg.actions.TestActionResult;
import com.cphdiving.divewithus.edma.generated.coursereg.views.GetAllCourseTypesResult;
import com.cphdiving.divewithus.edma.generated.coursereg.views.GetAllPersonsResult;
import com.cphdiving.divewithus.edma.generated.coursereg.views.GetCourseTypeResult;
import com.cphdiving.divewithus.edma.generated.coursereg.views.GetSchoolNameResult;
import com.cphdiving.divewithus.edma.generated.coursereg.views.GetStudentEnrollmentsResult;
import com.cphdiving.divewithus.edma.generated.coursereg.views.SearchCoursesResult;
import com.cphdiving.divewithus.edma.generated.coursereg.views.SellWetsuit2Result;
import com.cphdiving.divewithus.edma.generated.coursereg.views.SellWetsuitResult;
import com.cphdiving.divewithus.edma.generated.coursereg.views.TestViewResult;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.actions.EDMA_AddCourseTypeRequirement;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.actions.EDMA_AddCourseTypeToTeacher;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.actions.EDMA_AddPassedCourseToStudent;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.actions.EDMA_AddStudentToCourse;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.actions.EDMA_CreateCourse;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.actions.EDMA_CreateCourseType;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.actions.EDMA_CreatePerson;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.actions.EDMA_CreateStudent;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.actions.EDMA_CreateTeacher;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.actions.EDMA_SetSchoolName;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.actions.EDMA_SetTeacherOnCourse;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.actions.EDMA_TestAction;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.views.EDMA_GetAllCourseTypes;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.views.EDMA_GetAllPersons;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.views.EDMA_GetCourseType;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.views.EDMA_GetSchoolName;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.views.EDMA_GetStudentEnrollments;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.views.EDMA_SearchCourses;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.views.EDMA_SellWetsuit;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.views.EDMA_SellWetsuit2;
import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.views.EDMA_TestView;
import com.cphdiving.divewithus.edma.generated.valuedomains.Date;
import com.cphdiving.divewithus.edma.generated.valuedomains.DateAndTime;
import com.cphdiving.divewithus.edma.generated.valuedomains.Email;
import com.cphdiving.divewithus.edma.generated.valuedomains.Mobile;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.PosInt;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.BootSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseName;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.WetsuitSize;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.actions.AddCourseTypeRequirementUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.actions.AddCourseTypeToTeacherUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.actions.AddPassedCourseToStudentUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.actions.AddStudentToCourseUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.actions.CreateCourseTypeUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.actions.CreateCourseUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.actions.CreatePersonUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.actions.CreateStudentUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.actions.CreateTeacherUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.actions.SetSchoolNameUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.actions.SetTeacherOnCourseUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.actions.TestActionUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.views.GetAllCourseTypesUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.views.GetAllPersonsUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.views.GetCourseTypeUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.views.GetSchoolNameUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.views.GetStudentEnrollmentsUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.views.SearchCoursesUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.views.SellWetsuit2UserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.views.SellWetsuitUserImpl;
import com.cphdiving.divewithus.edma.usercode.models.coursereg.views.TestViewUserImpl;
import java.io.IOException;
import org.abstractica.edma.runtime.intf.IDataModelInstance;

/**
 * 
 */
public class CourseRegAPIImpl implements CourseReg
{
    private IDataModelInstance edma_dmi;



    /**
     * Constructor
     * @param edma_dmi  Data model instance runtime interface
     */
    public CourseRegAPIImpl(IDataModelInstance edma_dmi)
    {
        this.edma_dmi = edma_dmi;
    }

    /**
     * Access to the internal data model instance
     * @return  The internal data model instance
     */
    public IDataModelInstance edma_getDMI()
    {
        return edma_dmi;
    }

    /**
     * 
     * @param courseType  Input parameter courseType
     * @param start       Input parameter start
     * @param end         Input parameter end
     * @return            
     */
    public SearchCoursesResult searchCourses(CourseTypeID courseType, Date start, Date end) throws IOException
    {
        if(start == null)
        {
            throw new NullPointerException("start may not be null");
        }
        if(end == null)
        {
            throw new NullPointerException("end may not be null");
        }
        SearchCoursesUserImpl edma_userMethod = new SearchCoursesUserImpl(courseType, start, end);
        EDMA_SearchCourses edma_method = new EDMA_SearchCourses(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @param studentID  Input parameter studentID
     * @return           
     */
    public GetStudentEnrollmentsResult getStudentEnrollments(StudentID studentID) throws IOException
    {
        if(studentID == null)
        {
            throw new NullPointerException("studentID may not be null");
        }
        GetStudentEnrollmentsUserImpl edma_userMethod = new GetStudentEnrollmentsUserImpl(studentID);
        EDMA_GetStudentEnrollments edma_method = new EDMA_GetStudentEnrollments(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @return  
     */
    public GetAllCourseTypesResult getAllCourseTypes() throws IOException
    {
        GetAllCourseTypesUserImpl edma_userMethod = new GetAllCourseTypesUserImpl();
        EDMA_GetAllCourseTypes edma_method = new EDMA_GetAllCourseTypes(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @return  
     */
    public GetAllPersonsResult getAllPersons() throws IOException
    {
        GetAllPersonsUserImpl edma_userMethod = new GetAllPersonsUserImpl();
        EDMA_GetAllPersons edma_method = new EDMA_GetAllPersons(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @return  
     */
    public GetSchoolNameResult getSchoolName() throws IOException
    {
        GetSchoolNameUserImpl edma_userMethod = new GetSchoolNameUserImpl();
        EDMA_GetSchoolName edma_method = new EDMA_GetSchoolName(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @param courseID  Input parameter courseID
     * @return          
     */
    public GetCourseTypeResult getCourseType(CourseID courseID) throws IOException
    {
        if(courseID == null)
        {
            throw new NullPointerException("courseID may not be null");
        }
        GetCourseTypeUserImpl edma_userMethod = new GetCourseTypeUserImpl(courseID);
        EDMA_GetCourseType edma_method = new EDMA_GetCourseType(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @param size  Input parameter size
     * @param date  Input parameter date
     * @return      
     */
    public SellWetsuitResult sellWetsuit(WetsuitSize size, Date date) throws IOException
    {
        if(size == null)
        {
            throw new NullPointerException("size may not be null");
        }
        if(date == null)
        {
            throw new NullPointerException("date may not be null");
        }
        SellWetsuitUserImpl edma_userMethod = new SellWetsuitUserImpl(size, date);
        EDMA_SellWetsuit edma_method = new EDMA_SellWetsuit(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @param size  Input parameter size
     * @param date  Input parameter date
     * @return      
     */
    public SellWetsuit2Result sellWetsuit2(WetsuitSize size, Date date) throws IOException
    {
        if(size == null)
        {
            throw new NullPointerException("size may not be null");
        }
        if(date == null)
        {
            throw new NullPointerException("date may not be null");
        }
        SellWetsuit2UserImpl edma_userMethod = new SellWetsuit2UserImpl(size, date);
        EDMA_SellWetsuit2 edma_method = new EDMA_SellWetsuit2(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @param email  Input parameter email
     * @return       
     */
    public TestViewResult testView(Email email) throws IOException
    {
        if(email == null)
        {
            throw new NullPointerException("email may not be null");
        }
        TestViewUserImpl edma_userMethod = new TestViewUserImpl(email);
        EDMA_TestView edma_method = new EDMA_TestView(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @param name  Input parameter name
     * @return      
     */
    public SetSchoolNameResult setSchoolName(Name name) throws IOException
    {
        if(name == null)
        {
            throw new NullPointerException("name may not be null");
        }
        SetSchoolNameUserImpl edma_userMethod = new SetSchoolNameUserImpl(name);
        EDMA_SetSchoolName edma_method = new EDMA_SetSchoolName(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * Creates a new person
     * @param name    Input parameter name
     * @param email   Input parameter email
     * @param mobile  Input parameter mobile
     * @return        
     */
    public CreatePersonResult createPerson(Name name, Email email, Mobile mobile) throws IOException
    {
        if(name == null)
        {
            throw new NullPointerException("name may not be null");
        }
        if(email == null)
        {
            throw new NullPointerException("email may not be null");
        }
        if(mobile == null)
        {
            throw new NullPointerException("mobile may not be null");
        }
        CreatePersonUserImpl edma_userMethod = new CreatePersonUserImpl(name, email, mobile);
        EDMA_CreatePerson edma_method = new EDMA_CreatePerson(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @param personID     Input parameter personID
     * @param wetsuitSize  Input parameter wetsuitSize
     * @param bootSize     Input parameter bootSize
     * @return             
     */
    public CreateStudentResult createStudent(PersonID personID, WetsuitSize wetsuitSize, BootSize bootSize) throws IOException
    {
        if(personID == null)
        {
            throw new NullPointerException("personID may not be null");
        }
        if(wetsuitSize == null)
        {
            throw new NullPointerException("wetsuitSize may not be null");
        }
        if(bootSize == null)
        {
            throw new NullPointerException("bootSize may not be null");
        }
        CreateStudentUserImpl edma_userMethod = new CreateStudentUserImpl(personID, wetsuitSize, bootSize);
        EDMA_CreateStudent edma_method = new EDMA_CreateStudent(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @param personID  Input parameter personID
     * @param salary    Input parameter salary
     * @return          
     */
    public CreateTeacherResult createTeacher(PersonID personID, PosInt salary) throws IOException
    {
        if(personID == null)
        {
            throw new NullPointerException("personID may not be null");
        }
        if(salary == null)
        {
            throw new NullPointerException("salary may not be null");
        }
        CreateTeacherUserImpl edma_userMethod = new CreateTeacherUserImpl(personID, salary);
        EDMA_CreateTeacher edma_method = new EDMA_CreateTeacher(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @param courseTypeID  Input parameter courseTypeID
     * @param teacherID     Input parameter teacherID
     * @return              
     */
    public AddCourseTypeToTeacherResult addCourseTypeToTeacher(CourseTypeID courseTypeID, TeacherID teacherID) throws IOException
    {
        if(courseTypeID == null)
        {
            throw new NullPointerException("courseTypeID may not be null");
        }
        if(teacherID == null)
        {
            throw new NullPointerException("teacherID may not be null");
        }
        AddCourseTypeToTeacherUserImpl edma_userMethod = new AddCourseTypeToTeacherUserImpl(courseTypeID, teacherID);
        EDMA_AddCourseTypeToTeacher edma_method = new EDMA_AddCourseTypeToTeacher(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @param name   Input parameter name
     * @param size   Input parameter size
     * @param price  Input parameter price
     * @return       
     */
    public CreateCourseTypeResult createCourseType(CourseName name, CourseSize size, PosInt price) throws IOException
    {
        if(name == null)
        {
            throw new NullPointerException("name may not be null");
        }
        if(size == null)
        {
            throw new NullPointerException("size may not be null");
        }
        if(price == null)
        {
            throw new NullPointerException("price may not be null");
        }
        CreateCourseTypeUserImpl edma_userMethod = new CreateCourseTypeUserImpl(name, size, price);
        EDMA_CreateCourseType edma_method = new EDMA_CreateCourseType(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @param courseTypeID  Input parameter courseTypeID
     * @param requiredID    Input parameter requiredID
     * @return              
     */
    public AddCourseTypeRequirementResult addCourseTypeRequirement(CourseTypeID courseTypeID, CourseTypeID requiredID) throws IOException
    {
        if(courseTypeID == null)
        {
            throw new NullPointerException("courseTypeID may not be null");
        }
        if(requiredID == null)
        {
            throw new NullPointerException("requiredID may not be null");
        }
        AddCourseTypeRequirementUserImpl edma_userMethod = new AddCourseTypeRequirementUserImpl(courseTypeID, requiredID);
        EDMA_AddCourseTypeRequirement edma_method = new EDMA_AddCourseTypeRequirement(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @param start         Input parameter start
     * @param courseTypeID  Input parameter courseTypeID
     * @return              
     */
    public CreateCourseResult createCourse(DateAndTime start, CourseTypeID courseTypeID) throws IOException
    {
        if(start == null)
        {
            throw new NullPointerException("start may not be null");
        }
        if(courseTypeID == null)
        {
            throw new NullPointerException("courseTypeID may not be null");
        }
        CreateCourseUserImpl edma_userMethod = new CreateCourseUserImpl(start, courseTypeID);
        EDMA_CreateCourse edma_method = new EDMA_CreateCourse(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @param studentID  Input parameter studentID
     * @param courseID   Input parameter courseID
     * @return           
     */
    public AddStudentToCourseResult addStudentToCourse(StudentID studentID, CourseID courseID) throws IOException
    {
        if(studentID == null)
        {
            throw new NullPointerException("studentID may not be null");
        }
        if(courseID == null)
        {
            throw new NullPointerException("courseID may not be null");
        }
        AddStudentToCourseUserImpl edma_userMethod = new AddStudentToCourseUserImpl(studentID, courseID);
        EDMA_AddStudentToCourse edma_method = new EDMA_AddStudentToCourse(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @param teacherID  Input parameter teacherID
     * @param courseID   Input parameter courseID
     * @return           
     */
    public SetTeacherOnCourseResult setTeacherOnCourse(TeacherID teacherID, CourseID courseID) throws IOException
    {
        if(teacherID == null)
        {
            throw new NullPointerException("teacherID may not be null");
        }
        if(courseID == null)
        {
            throw new NullPointerException("courseID may not be null");
        }
        SetTeacherOnCourseUserImpl edma_userMethod = new SetTeacherOnCourseUserImpl(teacherID, courseID);
        EDMA_SetTeacherOnCourse edma_method = new EDMA_SetTeacherOnCourse(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @param studentID     Input parameter studentID
     * @param courseTypeID  Input parameter courseTypeID
     * @return              
     */
    public AddPassedCourseToStudentResult addPassedCourseToStudent(StudentID studentID, CourseTypeID courseTypeID) throws IOException
    {
        if(studentID == null)
        {
            throw new NullPointerException("studentID may not be null");
        }
        if(courseTypeID == null)
        {
            throw new NullPointerException("courseTypeID may not be null");
        }
        AddPassedCourseToStudentUserImpl edma_userMethod = new AddPassedCourseToStudentUserImpl(studentID, courseTypeID);
        EDMA_AddPassedCourseToStudent edma_method = new EDMA_AddPassedCourseToStudent(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }

    /**
     * 
     * @return  
     */
    public TestActionResult testAction() throws IOException
    {
        TestActionUserImpl edma_userMethod = new TestActionUserImpl();
        EDMA_TestAction edma_method = new EDMA_TestAction(edma_userMethod);
        edma_dmi.execute(edma_method);
        return edma_method.getUserMethod();
    }
}
