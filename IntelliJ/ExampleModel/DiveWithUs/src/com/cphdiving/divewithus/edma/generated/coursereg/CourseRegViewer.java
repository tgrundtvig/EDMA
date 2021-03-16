package com.cphdiving.divewithus.edma.generated.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseKind;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.coursetype.CourseTypeKind;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.person.PersonKind;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.schoolinfo.SchoolInfoViewer;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.student.StudentKind;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.teacher.TeacherKind;

/**
 * This is the viewing interface for the CourseReg data model.
 */
public interface CourseRegViewer
{

    /**
     * Returns the singleton SchoolInfo.
     * @return  The singleton SchoolInfo.
     */
    public SchoolInfoViewer getSchoolInfo();

    /**
     * Returns the interface to the kind Person
     * @return  The interface to the kind Person
     */
    public PersonKind getPersonKind();

    /**
     * Returns the interface to the kind Teacher
     * @return  The interface to the kind Teacher
     */
    public TeacherKind getTeacherKind();

    /**
     * Returns the interface to the kind Student
     * @return  The interface to the kind Student
     */
    public StudentKind getStudentKind();

    /**
     * Returns the interface to the kind CourseType
     * @return  The interface to the kind CourseType
     */
    public CourseTypeKind getCourseTypeKind();

    /**
     * Returns the interface to the kind Course
     * @return  The interface to the kind Course
     */
    public CourseKind getCourseKind();

}
