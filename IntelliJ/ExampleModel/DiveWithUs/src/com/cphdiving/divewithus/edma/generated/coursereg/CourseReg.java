package com.cphdiving.divewithus.edma.generated.coursereg;

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
import java.io.IOException;

/**
 * The external interface for the model: CourseReg
 */
public interface CourseReg
{

    /**
     * 
     * @param courseType  Input parameter courseType
     * @param start       Input parameter start
     * @param end         Input parameter end
     * @return            
     */
    public SearchCoursesResult searchCourses(CourseTypeID courseType, Date start, Date end) throws IOException;

    /**
     * 
     * @param studentID  Input parameter studentID
     * @return           
     */
    public GetStudentEnrollmentsResult getStudentEnrollments(StudentID studentID) throws IOException;

    /**
     * 
     * @return  
     */
    public GetAllCourseTypesResult getAllCourseTypes() throws IOException;

    /**
     * 
     * @return  
     */
    public GetAllPersonsResult getAllPersons() throws IOException;

    /**
     * 
     * @return  
     */
    public GetSchoolNameResult getSchoolName() throws IOException;

    /**
     * 
     * @param courseID  Input parameter courseID
     * @return          
     */
    public GetCourseTypeResult getCourseType(CourseID courseID) throws IOException;

    /**
     * 
     * @param size  Input parameter size
     * @param date  Input parameter date
     * @return      
     */
    public SellWetsuitResult sellWetsuit(WetsuitSize size, Date date) throws IOException;

    /**
     * 
     * @param size  Input parameter size
     * @param date  Input parameter date
     * @return      
     */
    public SellWetsuit2Result sellWetsuit2(WetsuitSize size, Date date) throws IOException;

    /**
     * 
     * @param email  Input parameter email
     * @return       
     */
    public TestViewResult testView(Email email) throws IOException;

    /**
     * 
     * @param name  Input parameter name
     * @return      
     */
    public SetSchoolNameResult setSchoolName(Name name) throws IOException;

    /**
     * Creates a new person
     * @param name    Input parameter name
     * @param email   Input parameter email
     * @param mobile  Input parameter mobile
     * @return        
     */
    public CreatePersonResult createPerson(Name name, Email email, Mobile mobile) throws IOException;

    /**
     * 
     * @param personID     Input parameter personID
     * @param wetsuitSize  Input parameter wetsuitSize
     * @param bootSize     Input parameter bootSize
     * @return             
     */
    public CreateStudentResult createStudent(PersonID personID, WetsuitSize wetsuitSize, BootSize bootSize) throws IOException;

    /**
     * 
     * @param personID  Input parameter personID
     * @param salary    Input parameter salary
     * @return          
     */
    public CreateTeacherResult createTeacher(PersonID personID, PosInt salary) throws IOException;

    /**
     * 
     * @param courseTypeID  Input parameter courseTypeID
     * @param teacherID     Input parameter teacherID
     * @return              
     */
    public AddCourseTypeToTeacherResult addCourseTypeToTeacher(CourseTypeID courseTypeID, TeacherID teacherID) throws IOException;

    /**
     * 
     * @param name   Input parameter name
     * @param size   Input parameter size
     * @param price  Input parameter price
     * @return       
     */
    public CreateCourseTypeResult createCourseType(CourseName name, CourseSize size, PosInt price) throws IOException;

    /**
     * 
     * @param courseTypeID  Input parameter courseTypeID
     * @param requiredID    Input parameter requiredID
     * @return              
     */
    public AddCourseTypeRequirementResult addCourseTypeRequirement(CourseTypeID courseTypeID, CourseTypeID requiredID) throws IOException;

    /**
     * 
     * @param start         Input parameter start
     * @param courseTypeID  Input parameter courseTypeID
     * @return              
     */
    public CreateCourseResult createCourse(DateAndTime start, CourseTypeID courseTypeID) throws IOException;

    /**
     * 
     * @param studentID  Input parameter studentID
     * @param courseID   Input parameter courseID
     * @return           
     */
    public AddStudentToCourseResult addStudentToCourse(StudentID studentID, CourseID courseID) throws IOException;

    /**
     * 
     * @param teacherID  Input parameter teacherID
     * @param courseID   Input parameter courseID
     * @return           
     */
    public SetTeacherOnCourseResult setTeacherOnCourse(TeacherID teacherID, CourseID courseID) throws IOException;

    /**
     * 
     * @param studentID     Input parameter studentID
     * @param courseTypeID  Input parameter courseTypeID
     * @return              
     */
    public AddPassedCourseToStudentResult addPassedCourseToStudent(StudentID studentID, CourseTypeID courseTypeID) throws IOException;

    /**
     * 
     * @return  
     */
    public TestActionResult testAction() throws IOException;

}
