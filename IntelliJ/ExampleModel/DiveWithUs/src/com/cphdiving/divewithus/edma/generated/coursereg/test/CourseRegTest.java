package com.cphdiving.divewithus.edma.generated.coursereg.test;

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
import org.abstractica.edma.valuedomains.userinput.ITerminal;

/**
 * 
 */
public class CourseRegTest
{
    private CourseReg edma_dm;
    private ITerminal edma_terminal;



    /**
     * Constructor
     * @param edma_dm        Interface to an instance of the CourseReg data
     *                       model
     * @param edma_terminal  Terminal to get value domains from
     */
    public CourseRegTest(CourseReg edma_dm, ITerminal edma_terminal)
    {
        this.edma_dm = edma_dm;
        this.edma_terminal = edma_terminal;
    }

    /**
     * Starts the test
     */
    public void start() throws IOException
    {
        while(true)
        {
            menu();
            String val = readInputLine();
            if("exit".equalsIgnoreCase(val)) break;
            try
            {
                int choice = Integer.parseInt(val);
                switch(choice)
                {
                    case 1:
                        callSetSchoolName();
                        break;
                    case 2:
                        callCreatePerson();
                        break;
                    case 3:
                        callCreateStudent();
                        break;
                    case 4:
                        callCreateTeacher();
                        break;
                    case 5:
                        callAddCourseTypeToTeacher();
                        break;
                    case 6:
                        callCreateCourseType();
                        break;
                    case 7:
                        callAddCourseTypeRequirement();
                        break;
                    case 8:
                        callCreateCourse();
                        break;
                    case 9:
                        callAddStudentToCourse();
                        break;
                    case 10:
                        callSetTeacherOnCourse();
                        break;
                    case 11:
                        callAddPassedCourseToStudent();
                        break;
                    case 12:
                        callTestAction();
                        break;
                    case 13:
                        callSearchCourses();
                        break;
                    case 14:
                        callGetStudentEnrollments();
                        break;
                    case 15:
                        callGetAllCourseTypes();
                        break;
                    case 16:
                        callGetAllPersons();
                        break;
                    case 17:
                        callGetSchoolName();
                        break;
                    case 18:
                        callGetCourseType();
                        break;
                    case 19:
                        callSellWetsuit();
                        break;
                    case 20:
                        callSellWetsuit2();
                        break;
                    case 21:
                        callTestView();
                        break;
                    default:
                        edma_terminal.put(choice + " does not exist! Please try again!\n");
                }
            }
            catch(NumberFormatException e)
            {
                edma_terminal.put(val + " is not a valid input! Please try again!\n");
            }
        }
    }

    /**
     * Prints the menu
     */
    private void menu()
    {
        edma_terminal.put("Welcome to the CourseReg interactive test!\n");
        edma_terminal.put("************************************************************************\n");
        edma_terminal.put("  Actions\n  -------\n");
        edma_terminal.put("    1 - setSchoolName(name : Name) -> ()\n");
        edma_terminal.put("    2 - createPerson(name : Name, email : Email, mobile : Mobile) -> (id : PersonID)\n");
        edma_terminal.put("    3 - createStudent(personID : PersonID, wetsuitSize : WetsuitSize, bootSize : BootSize) -> (id : StudentID)\n");
        edma_terminal.put("    4 - createTeacher(personID : PersonID, salary : PosInt) -> (id : TeacherID)\n");
        edma_terminal.put("    5 - addCourseTypeToTeacher(courseTypeID : CourseTypeID, teacherID : TeacherID) -> ()\n");
        edma_terminal.put("    6 - createCourseType(name : CourseName, size : CourseSize, price : PosInt) -> (id : CourseTypeID)\n");
        edma_terminal.put("    7 - addCourseTypeRequirement(courseTypeID : CourseTypeID, requiredID : CourseTypeID) -> ()\n");
        edma_terminal.put("    8 - createCourse(start : DateAndTime, courseTypeID : CourseTypeID) -> (id : CourseID)\n");
        edma_terminal.put("    9 - addStudentToCourse(studentID : StudentID, courseID : CourseID) -> ()\n");
        edma_terminal.put("    10 - setTeacherOnCourse(teacherID : TeacherID, courseID : CourseID) -> ()\n");
        edma_terminal.put("    11 - addPassedCourseToStudent(studentID : StudentID, courseTypeID : CourseTypeID) -> ()\n");
        edma_terminal.put("    12 - testAction() -> ()\n");
        edma_terminal.put("  Views\n  -----\n");
        edma_terminal.put("    13 - searchCourses(courseType : CourseTypeID, start : Date, end : Date) -> (courseList : CourseInfoList)\n");
        edma_terminal.put("    14 - getStudentEnrollments(studentID : StudentID) -> (courseList : CourseInfoList)\n");
        edma_terminal.put("    15 - getAllCourseTypes() -> (courseTypeList : CourseTypeList)\n");
        edma_terminal.put("    16 - getAllPersons() -> (personList : PersonList)\n");
        edma_terminal.put("    17 - getSchoolName() -> (schoolName : Name)\n");
        edma_terminal.put("    18 - getCourseType(courseID : CourseID) -> (courseType : CourseType)\n");
        edma_terminal.put("    19 - sellWetsuit(size : WetsuitSize, date : Date) -> (teachers : PersonList)\n");
        edma_terminal.put("    20 - sellWetsuit2(size : WetsuitSize, date : Date) -> (teachers : PersonList)\n");
        edma_terminal.put("    21 - testView(email : Email) -> (person : Person)\n");
        edma_terminal.put("Please choose a transaction to call (or type exit to exit):\n");
    }

    /**
     * Reads a line from stdin
     * @return  
     */
    private String readInputLine()
    {
        edma_terminal.put(": ");
        return edma_terminal.getString();
    }

    /**
     * Calls the method: setSchoolName
     */
    private void callSetSchoolName() throws IOException
    {
        
        //Input parameter name
        Name in_name;
        edma_terminal.put("Enter input parameter name : Name\n");
        in_name = Name.fromTerminal(edma_terminal);
        
        //Make the call
        SetSchoolNameResult res = edma_dm.setSchoolName(in_name);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: createPerson
     */
    private void callCreatePerson() throws IOException
    {
        
        //Input parameter name
        Name in_name;
        edma_terminal.put("Enter input parameter name : Name\n");
        in_name = Name.fromTerminal(edma_terminal);
        
        //Input parameter email
        Email in_email;
        edma_terminal.put("Enter input parameter email : Email\n");
        in_email = Email.fromTerminal(edma_terminal);
        
        //Input parameter mobile
        Mobile in_mobile;
        edma_terminal.put("Enter input parameter mobile : Mobile\n");
        in_mobile = Mobile.fromTerminal(edma_terminal);
        
        //Make the call
        CreatePersonResult res = edma_dm.createPerson(in_name, in_email, in_mobile);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("\nOutput parameter id:\n");
        edma_terminal.put("  " + res.getId() + "\n");
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: createStudent
     */
    private void callCreateStudent() throws IOException
    {
        
        //Input parameter personID
        PersonID in_personID;
        edma_terminal.put("Enter input parameter personID : PersonID\n");
        in_personID = PersonID.fromTerminal(edma_terminal);
        
        //Input parameter wetsuitSize
        WetsuitSize in_wetsuitSize;
        edma_terminal.put("Enter input parameter wetsuitSize : WetsuitSize\n");
        in_wetsuitSize = WetsuitSize.fromTerminal(edma_terminal);
        
        //Input parameter bootSize
        BootSize in_bootSize;
        edma_terminal.put("Enter input parameter bootSize : BootSize\n");
        in_bootSize = BootSize.fromTerminal(edma_terminal);
        
        //Make the call
        CreateStudentResult res = edma_dm.createStudent(in_personID, in_wetsuitSize, in_bootSize);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("\nOutput parameter id:\n");
        edma_terminal.put("  " + res.getId() + "\n");
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: createTeacher
     */
    private void callCreateTeacher() throws IOException
    {
        
        //Input parameter personID
        PersonID in_personID;
        edma_terminal.put("Enter input parameter personID : PersonID\n");
        in_personID = PersonID.fromTerminal(edma_terminal);
        
        //Input parameter salary
        PosInt in_salary;
        edma_terminal.put("Enter input parameter salary : PosInt\n");
        in_salary = PosInt.fromTerminal(edma_terminal);
        
        //Make the call
        CreateTeacherResult res = edma_dm.createTeacher(in_personID, in_salary);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("\nOutput parameter id:\n");
        edma_terminal.put("  " + res.getId() + "\n");
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: addCourseTypeToTeacher
     */
    private void callAddCourseTypeToTeacher() throws IOException
    {
        
        //Input parameter courseTypeID
        CourseTypeID in_courseTypeID;
        edma_terminal.put("Enter input parameter courseTypeID : CourseTypeID\n");
        in_courseTypeID = CourseTypeID.fromTerminal(edma_terminal);
        
        //Input parameter teacherID
        TeacherID in_teacherID;
        edma_terminal.put("Enter input parameter teacherID : TeacherID\n");
        in_teacherID = TeacherID.fromTerminal(edma_terminal);
        
        //Make the call
        AddCourseTypeToTeacherResult res = edma_dm.addCourseTypeToTeacher(in_courseTypeID, in_teacherID);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: createCourseType
     */
    private void callCreateCourseType() throws IOException
    {
        
        //Input parameter name
        CourseName in_name;
        edma_terminal.put("Enter input parameter name : CourseName\n");
        in_name = CourseName.fromTerminal(edma_terminal);
        
        //Input parameter size
        CourseSize in_size;
        edma_terminal.put("Enter input parameter size : CourseSize\n");
        in_size = CourseSize.fromTerminal(edma_terminal);
        
        //Input parameter price
        PosInt in_price;
        edma_terminal.put("Enter input parameter price : PosInt\n");
        in_price = PosInt.fromTerminal(edma_terminal);
        
        //Make the call
        CreateCourseTypeResult res = edma_dm.createCourseType(in_name, in_size, in_price);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("\nOutput parameter id:\n");
        edma_terminal.put("  " + res.getId() + "\n");
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: addCourseTypeRequirement
     */
    private void callAddCourseTypeRequirement() throws IOException
    {
        
        //Input parameter courseTypeID
        CourseTypeID in_courseTypeID;
        edma_terminal.put("Enter input parameter courseTypeID : CourseTypeID\n");
        in_courseTypeID = CourseTypeID.fromTerminal(edma_terminal);
        
        //Input parameter requiredID
        CourseTypeID in_requiredID;
        edma_terminal.put("Enter input parameter requiredID : CourseTypeID\n");
        in_requiredID = CourseTypeID.fromTerminal(edma_terminal);
        
        //Make the call
        AddCourseTypeRequirementResult res = edma_dm.addCourseTypeRequirement(in_courseTypeID, in_requiredID);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: createCourse
     */
    private void callCreateCourse() throws IOException
    {
        
        //Input parameter start
        DateAndTime in_start;
        edma_terminal.put("Enter input parameter start : DateAndTime\n");
        in_start = DateAndTime.fromTerminal(edma_terminal);
        
        //Input parameter courseTypeID
        CourseTypeID in_courseTypeID;
        edma_terminal.put("Enter input parameter courseTypeID : CourseTypeID\n");
        in_courseTypeID = CourseTypeID.fromTerminal(edma_terminal);
        
        //Make the call
        CreateCourseResult res = edma_dm.createCourse(in_start, in_courseTypeID);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("\nOutput parameter id:\n");
        edma_terminal.put("  " + res.getId() + "\n");
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: addStudentToCourse
     */
    private void callAddStudentToCourse() throws IOException
    {
        
        //Input parameter studentID
        StudentID in_studentID;
        edma_terminal.put("Enter input parameter studentID : StudentID\n");
        in_studentID = StudentID.fromTerminal(edma_terminal);
        
        //Input parameter courseID
        CourseID in_courseID;
        edma_terminal.put("Enter input parameter courseID : CourseID\n");
        in_courseID = CourseID.fromTerminal(edma_terminal);
        
        //Make the call
        AddStudentToCourseResult res = edma_dm.addStudentToCourse(in_studentID, in_courseID);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: setTeacherOnCourse
     */
    private void callSetTeacherOnCourse() throws IOException
    {
        
        //Input parameter teacherID
        TeacherID in_teacherID;
        edma_terminal.put("Enter input parameter teacherID : TeacherID\n");
        in_teacherID = TeacherID.fromTerminal(edma_terminal);
        
        //Input parameter courseID
        CourseID in_courseID;
        edma_terminal.put("Enter input parameter courseID : CourseID\n");
        in_courseID = CourseID.fromTerminal(edma_terminal);
        
        //Make the call
        SetTeacherOnCourseResult res = edma_dm.setTeacherOnCourse(in_teacherID, in_courseID);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: addPassedCourseToStudent
     */
    private void callAddPassedCourseToStudent() throws IOException
    {
        
        //Input parameter studentID
        StudentID in_studentID;
        edma_terminal.put("Enter input parameter studentID : StudentID\n");
        in_studentID = StudentID.fromTerminal(edma_terminal);
        
        //Input parameter courseTypeID
        CourseTypeID in_courseTypeID;
        edma_terminal.put("Enter input parameter courseTypeID : CourseTypeID\n");
        in_courseTypeID = CourseTypeID.fromTerminal(edma_terminal);
        
        //Make the call
        AddPassedCourseToStudentResult res = edma_dm.addPassedCourseToStudent(in_studentID, in_courseTypeID);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: testAction
     */
    private void callTestAction() throws IOException
    {
        
        //Make the call
        TestActionResult res = edma_dm.testAction();
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: searchCourses
     */
    private void callSearchCourses() throws IOException
    {
        
        //Input parameter courseType
        CourseTypeID in_courseType;
        edma_terminal.put("Input parameter courseType? : CourseTypeID is optional.\nWould you like to enter a value for this input parameter (y/n): ");
        if(edma_terminal.getYesNo())
        {
            edma_terminal.put("Enter input parameter courseType? : CourseTypeID\n");
            in_courseType = CourseTypeID.fromTerminal(edma_terminal);
        }
        else
        {
            in_courseType = null;
        }
        
        //Input parameter start
        Date in_start;
        edma_terminal.put("Enter input parameter start : Date\n");
        in_start = Date.fromTerminal(edma_terminal);
        
        //Input parameter end
        Date in_end;
        edma_terminal.put("Enter input parameter end : Date\n");
        in_end = Date.fromTerminal(edma_terminal);
        
        //Make the call
        SearchCoursesResult res = edma_dm.searchCourses(in_courseType, in_start, in_end);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("\nOutput parameter courseList:\n");
        edma_terminal.put("  " + res.getCourseList() + "\n");
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: getStudentEnrollments
     */
    private void callGetStudentEnrollments() throws IOException
    {
        
        //Input parameter studentID
        StudentID in_studentID;
        edma_terminal.put("Enter input parameter studentID : StudentID\n");
        in_studentID = StudentID.fromTerminal(edma_terminal);
        
        //Make the call
        GetStudentEnrollmentsResult res = edma_dm.getStudentEnrollments(in_studentID);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("\nOutput parameter courseList:\n");
        edma_terminal.put("  " + res.getCourseList() + "\n");
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: getAllCourseTypes
     */
    private void callGetAllCourseTypes() throws IOException
    {
        
        //Make the call
        GetAllCourseTypesResult res = edma_dm.getAllCourseTypes();
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("\nOutput parameter courseTypeList:\n");
        edma_terminal.put("  " + res.getCourseTypeList() + "\n");
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: getAllPersons
     */
    private void callGetAllPersons() throws IOException
    {
        
        //Make the call
        GetAllPersonsResult res = edma_dm.getAllPersons();
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("\nOutput parameter personList:\n");
        edma_terminal.put("  " + res.getPersonList() + "\n");
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: getSchoolName
     */
    private void callGetSchoolName() throws IOException
    {
        
        //Make the call
        GetSchoolNameResult res = edma_dm.getSchoolName();
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("\nOutput parameter schoolName:\n");
        edma_terminal.put("  " + res.getSchoolName() + "\n");
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: getCourseType
     */
    private void callGetCourseType() throws IOException
    {
        
        //Input parameter courseID
        CourseID in_courseID;
        edma_terminal.put("Enter input parameter courseID : CourseID\n");
        in_courseID = CourseID.fromTerminal(edma_terminal);
        
        //Make the call
        GetCourseTypeResult res = edma_dm.getCourseType(in_courseID);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("\nOutput parameter courseType:\n");
        edma_terminal.put("  " + res.getCourseType() + "\n");
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: sellWetsuit
     */
    private void callSellWetsuit() throws IOException
    {
        
        //Input parameter size
        WetsuitSize in_size;
        edma_terminal.put("Enter input parameter size : WetsuitSize\n");
        in_size = WetsuitSize.fromTerminal(edma_terminal);
        
        //Input parameter date
        Date in_date;
        edma_terminal.put("Enter input parameter date : Date\n");
        in_date = Date.fromTerminal(edma_terminal);
        
        //Make the call
        SellWetsuitResult res = edma_dm.sellWetsuit(in_size, in_date);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("\nOutput parameter teachers:\n");
        edma_terminal.put("  " + res.getTeachers() + "\n");
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: sellWetsuit2
     */
    private void callSellWetsuit2() throws IOException
    {
        
        //Input parameter size
        WetsuitSize in_size;
        edma_terminal.put("Enter input parameter size : WetsuitSize\n");
        in_size = WetsuitSize.fromTerminal(edma_terminal);
        
        //Input parameter date
        Date in_date;
        edma_terminal.put("Enter input parameter date : Date\n");
        in_date = Date.fromTerminal(edma_terminal);
        
        //Make the call
        SellWetsuit2Result res = edma_dm.sellWetsuit2(in_size, in_date);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("\nOutput parameter teachers:\n");
        edma_terminal.put("  " + res.getTeachers() + "\n");
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }

    /**
     * Calls the method: testView
     */
    private void callTestView() throws IOException
    {
        
        //Input parameter email
        Email in_email;
        edma_terminal.put("Enter input parameter email : Email\n");
        in_email = Email.fromTerminal(edma_terminal);
        
        //Make the call
        TestViewResult res = edma_dm.testView(in_email);
        
        //Print the result
        edma_terminal.put("\n\nResult: " + res.errorCode() + " - " + res.errorMessage() + "\n");
        if(res.errorDescription() != null)
        {
            edma_terminal.put("Extra info: " + res.errorDescription() + "\n");
        }
        edma_terminal.put("\nOutput parameter person:\n");
        edma_terminal.put("  " + res.getPerson() + "\n");
        edma_terminal.put("Press return to continue!\n");
        readInputLine();
    }
}
