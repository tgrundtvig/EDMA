package com.cphdiving.divewithus.edma.generated.coursereg.remote;

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
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseInfoList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseName;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseSize;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseType;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.CourseTypeList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.Person;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.PersonList;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.StudentID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.TeacherID;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.WetsuitSize;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 
 */
public class CourseRegClientInstance implements CourseReg
{
    private String edma_hostname;
    private int edma_port;



    /**
     * Constructor
     * @param edma_hostname  Name of the server host
     * @param edma_port      port to connect to
     */
    public CourseRegClientInstance(String edma_hostname, int edma_port)
    {
        this.edma_hostname = edma_hostname;
        this.edma_port = edma_port;
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(0);
        if(courseType == null)
        {
            edma_out.writeBoolean(false);
        }
        else
        {
            edma_out.writeBoolean(true);
            courseType.toStream(edma_out);
        }
        start.toStream(edma_out);
        end.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        CourseInfoList out_courseList = CourseInfoList.fromStreamNoValidate(edma_in);
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteSearchCoursesResult(errorCode, errorMsg, errorDesc, out_courseList);
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(1);
        studentID.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        CourseInfoList out_courseList = CourseInfoList.fromStreamNoValidate(edma_in);
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteGetStudentEnrollmentsResult(errorCode, errorMsg, errorDesc, out_courseList);
    }

    /**
     * 
     * @return  
     */
    public GetAllCourseTypesResult getAllCourseTypes() throws IOException
    {
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(2);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        CourseTypeList out_courseTypeList = CourseTypeList.fromStreamNoValidate(edma_in);
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteGetAllCourseTypesResult(errorCode, errorMsg, errorDesc, out_courseTypeList);
    }

    /**
     * 
     * @return  
     */
    public GetAllPersonsResult getAllPersons() throws IOException
    {
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(3);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        PersonList out_personList = PersonList.fromStreamNoValidate(edma_in);
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteGetAllPersonsResult(errorCode, errorMsg, errorDesc, out_personList);
    }

    /**
     * 
     * @return  
     */
    public GetSchoolNameResult getSchoolName() throws IOException
    {
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(4);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        Name out_schoolName = Name.fromStreamNoValidate(edma_in);
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteGetSchoolNameResult(errorCode, errorMsg, errorDesc, out_schoolName);
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(5);
        courseID.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        CourseType out_courseType = null;
        if(edma_in.readBoolean()) out_courseType = CourseType.fromStreamNoValidate(edma_in);
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteGetCourseTypeResult(errorCode, errorMsg, errorDesc, out_courseType);
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(6);
        size.toStream(edma_out);
        date.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        PersonList out_teachers = PersonList.fromStreamNoValidate(edma_in);
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteSellWetsuitResult(errorCode, errorMsg, errorDesc, out_teachers);
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(7);
        size.toStream(edma_out);
        date.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        PersonList out_teachers = PersonList.fromStreamNoValidate(edma_in);
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteSellWetsuit2Result(errorCode, errorMsg, errorDesc, out_teachers);
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(8);
        email.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        Person out_person = Person.fromStreamNoValidate(edma_in);
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteTestViewResult(errorCode, errorMsg, errorDesc, out_person);
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(9);
        name.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteSetSchoolNameResult(errorCode, errorMsg, errorDesc);
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(10);
        name.toStream(edma_out);
        email.toStream(edma_out);
        mobile.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        PersonID out_id = PersonID.fromStreamNoValidate(edma_in);
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteCreatePersonResult(errorCode, errorMsg, errorDesc, out_id);
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(11);
        personID.toStream(edma_out);
        wetsuitSize.toStream(edma_out);
        bootSize.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        StudentID out_id = StudentID.fromStreamNoValidate(edma_in);
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteCreateStudentResult(errorCode, errorMsg, errorDesc, out_id);
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(12);
        personID.toStream(edma_out);
        salary.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        TeacherID out_id = TeacherID.fromStreamNoValidate(edma_in);
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteCreateTeacherResult(errorCode, errorMsg, errorDesc, out_id);
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(13);
        courseTypeID.toStream(edma_out);
        teacherID.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteAddCourseTypeToTeacherResult(errorCode, errorMsg, errorDesc);
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(14);
        name.toStream(edma_out);
        size.toStream(edma_out);
        price.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        CourseTypeID out_id = CourseTypeID.fromStreamNoValidate(edma_in);
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteCreateCourseTypeResult(errorCode, errorMsg, errorDesc, out_id);
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(15);
        courseTypeID.toStream(edma_out);
        requiredID.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteAddCourseTypeRequirementResult(errorCode, errorMsg, errorDesc);
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(16);
        start.toStream(edma_out);
        courseTypeID.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        CourseID out_id = CourseID.fromStreamNoValidate(edma_in);
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteCreateCourseResult(errorCode, errorMsg, errorDesc, out_id);
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(17);
        studentID.toStream(edma_out);
        courseID.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteAddStudentToCourseResult(errorCode, errorMsg, errorDesc);
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(18);
        teacherID.toStream(edma_out);
        courseID.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteSetTeacherOnCourseResult(errorCode, errorMsg, errorDesc);
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
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(19);
        studentID.toStream(edma_out);
        courseTypeID.toStream(edma_out);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteAddPassedCourseToStudentResult(errorCode, errorMsg, errorDesc);
    }

    /**
     * 
     * @return  
     */
    public TestActionResult testAction() throws IOException
    {
        Socket edma_socket = new Socket(edma_hostname, edma_port);
        BufferedInputStream buf_in = new BufferedInputStream(edma_socket.getInputStream());
        DataInput edma_in = new DataInputStream(buf_in);
        BufferedOutputStream buf_out = new BufferedOutputStream(edma_socket.getOutputStream());
        DataOutput edma_out = new DataOutputStream(buf_out);
        edma_out.writeInt(20);
        buf_out.flush();
        if(!edma_in.readBoolean())
        {
            String edma_error = edma_in.readUTF();
            buf_out.close();
            buf_in.close();
            edma_socket.close();
            throw new IOException(edma_error);
        }
        int errorCode = edma_in.readInt();
        String errorMsg = edma_in.readUTF();
        String errorDesc = null;
        if(edma_in.readBoolean())
        {
            errorDesc = edma_in.readUTF();
        }
        buf_out.close();
        buf_in.close();
        edma_socket.close();
        return new RemoteTestActionResult(errorCode, errorMsg, errorDesc);
    }
}
