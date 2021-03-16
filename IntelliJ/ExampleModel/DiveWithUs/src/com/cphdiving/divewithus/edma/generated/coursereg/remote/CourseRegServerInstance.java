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
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.abstractica.edma.runtime.remote.IServerInstance;

/**
 * 
 */
public class CourseRegServerInstance implements IServerInstance
{
    private CourseReg edma_inst;



    /**
     * Constructor
     * @param edma_inst  The data model instance
     */
    public CourseRegServerInstance(CourseReg edma_inst)
    {
        this.edma_inst = edma_inst;
    }

    /**
     * call a method
     * @param methodID  ID of the method to call
     * @param edma_in   Stream to read input parameters from
     * @param edma_out  Stream to write output parameters to
     */
    public void call(int methodID, DataInput edma_in, DataOutput edma_out) throws IOException
    {
        switch(methodID)
        {
            case 0:
            {
                CourseTypeID in_courseType = null;
                if(edma_in.readBoolean()) in_courseType = CourseTypeID.fromStreamNoValidate(edma_in);
                Date in_start = Date.fromStreamNoValidate(edma_in);
                Date in_end = Date.fromStreamNoValidate(edma_in);
                SearchCoursesResult edma_res = edma_inst.searchCourses(in_courseType, in_start, in_end);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                CourseInfoList out_courseList = edma_res.getCourseList();
                out_courseList.toStream(edma_out);
                break;
            }
            case 1:
            {
                StudentID in_studentID = StudentID.fromStreamNoValidate(edma_in);
                GetStudentEnrollmentsResult edma_res = edma_inst.getStudentEnrollments(in_studentID);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                CourseInfoList out_courseList = edma_res.getCourseList();
                out_courseList.toStream(edma_out);
                break;
            }
            case 2:
            {
                GetAllCourseTypesResult edma_res = edma_inst.getAllCourseTypes();
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                CourseTypeList out_courseTypeList = edma_res.getCourseTypeList();
                out_courseTypeList.toStream(edma_out);
                break;
            }
            case 3:
            {
                GetAllPersonsResult edma_res = edma_inst.getAllPersons();
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                PersonList out_personList = edma_res.getPersonList();
                out_personList.toStream(edma_out);
                break;
            }
            case 4:
            {
                GetSchoolNameResult edma_res = edma_inst.getSchoolName();
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                Name out_schoolName = edma_res.getSchoolName();
                out_schoolName.toStream(edma_out);
                break;
            }
            case 5:
            {
                CourseID in_courseID = CourseID.fromStreamNoValidate(edma_in);
                GetCourseTypeResult edma_res = edma_inst.getCourseType(in_courseID);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                CourseType out_courseType = edma_res.getCourseType();
                if(out_courseType == null)
                {
                    edma_out.writeBoolean(false);
                }
                else
                {
                    edma_out.writeBoolean(true);
                    out_courseType.toStream(edma_out);
                }
                break;
            }
            case 6:
            {
                WetsuitSize in_size = WetsuitSize.fromStreamNoValidate(edma_in);
                Date in_date = Date.fromStreamNoValidate(edma_in);
                SellWetsuitResult edma_res = edma_inst.sellWetsuit(in_size, in_date);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                PersonList out_teachers = edma_res.getTeachers();
                out_teachers.toStream(edma_out);
                break;
            }
            case 7:
            {
                WetsuitSize in_size = WetsuitSize.fromStreamNoValidate(edma_in);
                Date in_date = Date.fromStreamNoValidate(edma_in);
                SellWetsuit2Result edma_res = edma_inst.sellWetsuit2(in_size, in_date);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                PersonList out_teachers = edma_res.getTeachers();
                out_teachers.toStream(edma_out);
                break;
            }
            case 8:
            {
                Email in_email = Email.fromStreamNoValidate(edma_in);
                TestViewResult edma_res = edma_inst.testView(in_email);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                Person out_person = edma_res.getPerson();
                out_person.toStream(edma_out);
                break;
            }
            case 9:
            {
                Name in_name = Name.fromStreamNoValidate(edma_in);
                SetSchoolNameResult edma_res = edma_inst.setSchoolName(in_name);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                break;
            }
            case 10:
            {
                Name in_name = Name.fromStreamNoValidate(edma_in);
                Email in_email = Email.fromStreamNoValidate(edma_in);
                Mobile in_mobile = Mobile.fromStreamNoValidate(edma_in);
                CreatePersonResult edma_res = edma_inst.createPerson(in_name, in_email, in_mobile);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                PersonID out_id = edma_res.getId();
                out_id.toStream(edma_out);
                break;
            }
            case 11:
            {
                PersonID in_personID = PersonID.fromStreamNoValidate(edma_in);
                WetsuitSize in_wetsuitSize = WetsuitSize.fromStreamNoValidate(edma_in);
                BootSize in_bootSize = BootSize.fromStreamNoValidate(edma_in);
                CreateStudentResult edma_res = edma_inst.createStudent(in_personID, in_wetsuitSize, in_bootSize);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                StudentID out_id = edma_res.getId();
                out_id.toStream(edma_out);
                break;
            }
            case 12:
            {
                PersonID in_personID = PersonID.fromStreamNoValidate(edma_in);
                PosInt in_salary = PosInt.fromStreamNoValidate(edma_in);
                CreateTeacherResult edma_res = edma_inst.createTeacher(in_personID, in_salary);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                TeacherID out_id = edma_res.getId();
                out_id.toStream(edma_out);
                break;
            }
            case 13:
            {
                CourseTypeID in_courseTypeID = CourseTypeID.fromStreamNoValidate(edma_in);
                TeacherID in_teacherID = TeacherID.fromStreamNoValidate(edma_in);
                AddCourseTypeToTeacherResult edma_res = edma_inst.addCourseTypeToTeacher(in_courseTypeID, in_teacherID);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                break;
            }
            case 14:
            {
                CourseName in_name = CourseName.fromStreamNoValidate(edma_in);
                CourseSize in_size = CourseSize.fromStreamNoValidate(edma_in);
                PosInt in_price = PosInt.fromStreamNoValidate(edma_in);
                CreateCourseTypeResult edma_res = edma_inst.createCourseType(in_name, in_size, in_price);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                CourseTypeID out_id = edma_res.getId();
                out_id.toStream(edma_out);
                break;
            }
            case 15:
            {
                CourseTypeID in_courseTypeID = CourseTypeID.fromStreamNoValidate(edma_in);
                CourseTypeID in_requiredID = CourseTypeID.fromStreamNoValidate(edma_in);
                AddCourseTypeRequirementResult edma_res = edma_inst.addCourseTypeRequirement(in_courseTypeID, in_requiredID);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                break;
            }
            case 16:
            {
                DateAndTime in_start = DateAndTime.fromStreamNoValidate(edma_in);
                CourseTypeID in_courseTypeID = CourseTypeID.fromStreamNoValidate(edma_in);
                CreateCourseResult edma_res = edma_inst.createCourse(in_start, in_courseTypeID);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                CourseID out_id = edma_res.getId();
                out_id.toStream(edma_out);
                break;
            }
            case 17:
            {
                StudentID in_studentID = StudentID.fromStreamNoValidate(edma_in);
                CourseID in_courseID = CourseID.fromStreamNoValidate(edma_in);
                AddStudentToCourseResult edma_res = edma_inst.addStudentToCourse(in_studentID, in_courseID);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                break;
            }
            case 18:
            {
                TeacherID in_teacherID = TeacherID.fromStreamNoValidate(edma_in);
                CourseID in_courseID = CourseID.fromStreamNoValidate(edma_in);
                SetTeacherOnCourseResult edma_res = edma_inst.setTeacherOnCourse(in_teacherID, in_courseID);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                break;
            }
            case 19:
            {
                StudentID in_studentID = StudentID.fromStreamNoValidate(edma_in);
                CourseTypeID in_courseTypeID = CourseTypeID.fromStreamNoValidate(edma_in);
                AddPassedCourseToStudentResult edma_res = edma_inst.addPassedCourseToStudent(in_studentID, in_courseTypeID);
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                break;
            }
            case 20:
            {
                TestActionResult edma_res = edma_inst.testAction();
                edma_out.writeBoolean(true);
                edma_out.writeInt(edma_res.errorCode());
                edma_out.writeUTF(edma_res.errorMessage());
                if(edma_res.errorDescription() != null)
                {
                    edma_out.writeBoolean(true);
                    edma_out.writeUTF(edma_res.errorDescription());
                }
                else
                {
                    edma_out.writeBoolean(false);
                }
                break;
            }
            default:
                throw new RuntimeException("Invalid method ID: " + methodID);
        }
    }
}
