package com.cphdiving.divewithus.edma.generated;

import com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg.CourseRegFactoryImpl;
import java.util.ArrayList;
import java.util.Collection;
import org.abstractica.edma.metamodel.IMetaEnvironment;
import org.abstractica.edma.metamodel.IMetaIndex.IndexType;
import org.abstractica.edma.metamodel.IMetaRelation.RelationType;
import org.abstractica.edma.metamodel.impl.MetaAttribute;
import org.abstractica.edma.metamodel.impl.MetaDataModel;
import org.abstractica.edma.metamodel.impl.MetaEnvironment;
import org.abstractica.edma.metamodel.impl.MetaIndex;
import org.abstractica.edma.metamodel.impl.MetaKind;
import org.abstractica.edma.metamodel.impl.MetaMethod;
import org.abstractica.edma.metamodel.impl.MetaRelation;
import org.abstractica.edma.metamodel.impl.MetaSingleton;
import org.abstractica.edma.metamodel.impl.ValueDomainBuilder;
import org.abstractica.edma.metamodel.impl.ValueDomainBuilder.Field;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.newindex.IndexUtil;
import org.abstractica.edma.runtime.intf.IRuntimeFactory;
import org.abstractica.edma.valuedomains.impl.Constraint;

/**
 * 
 */
public class DiveWithUs
{
    public static final IMetaEnvironment environment = generateEnvironment();
    private CourseRegFactoryImpl edma_CourseReg;



    /**
     * generate the environment
     * @return  
     */
    public static IMetaEnvironment generateEnvironment()
    {
        ValueDomainBuilder vdb = new ValueDomainBuilder();
        
        //Integer value domain: Year
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newIntegerDomain("Year", null, 0, 9999, edma_constraints, false);
        }
        
        //Integer value domain: Month
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newIntegerDomain("Month", null, 1, 12, edma_constraints, false);
        }
        
        //Integer value domain: DayOfMonth
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newIntegerDomain("DayOfMonth", null, 1, 31, edma_constraints, false);
        }
        
        //Struct value domain: Date
        {
            ArrayList<Constraint> edma_constraints = new ArrayList<Constraint>(1);
            edma_constraints.add(new Constraint("validDate", "No description given"));
            Collection<Field> fields = new ArrayList<Field>();
            fields.add(vdb.newStructField("year", "Year", false));
            fields.add(vdb.newStructField("month", "Month", false));
            fields.add(vdb.newStructField("day", "DayOfMonth", false));
            vdb.newStructDomain("Date", null, fields, edma_constraints, false);
        }
        
        //Integer value domain: Hour
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newIntegerDomain("Hour", null, 0, 23, edma_constraints, false);
        }
        
        //Integer value domain: Minute
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newIntegerDomain("Minute", null, 0, 59, edma_constraints, false);
        }
        
        //Integer value domain: Second
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newIntegerDomain("Second", null, 0, 59, edma_constraints, false);
        }
        
        //Integer value domain: Millisecond
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newIntegerDomain("Millisecond", null, 0, 999, edma_constraints, false);
        }
        
        //Struct value domain: Time
        {
            ArrayList<Constraint> edma_constraints = null;
            Collection<Field> fields = new ArrayList<Field>();
            fields.add(vdb.newStructField("hour", "Hour", false));
            fields.add(vdb.newStructField("min", "Minute", false));
            fields.add(vdb.newStructField("second", "Second", false));
            fields.add(vdb.newStructField("milliseconds", "Millisecond", false));
            vdb.newStructDomain("Time", null, fields, edma_constraints, false);
        }
        
        //Struct value domain: DateAndTime
        {
            ArrayList<Constraint> edma_constraints = null;
            Collection<Field> fields = new ArrayList<Field>();
            fields.add(vdb.newStructField("date", "Date", false));
            fields.add(vdb.newStructField("time", "Time", false));
            vdb.newStructDomain("DateAndTime", null, fields, edma_constraints, false);
        }
        
        //String value domain: Name
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newStringDomain("Name", null, 1, null, null, edma_constraints, false);
        }
        
        //String value domain: Email
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newStringDomain("Email", null, 3, null, "^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", edma_constraints, false);
        }
        
        //String value domain: Mobile
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newStringDomain("Mobile", null, 8, 8, "[0-9]+", edma_constraints, false);
        }
        
        //String value domain: AddressLine
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newStringDomain("AddressLine", null, 1, null, null, edma_constraints, false);
        }
        
        //String value domain: City
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newStringDomain("City", null, 1, null, null, edma_constraints, false);
        }
        
        //Integer value domain: ZipCode
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newIntegerDomain("ZipCode", null, 1000, 9999, edma_constraints, false);
        }
        
        //Struct value domain: Address
        {
            ArrayList<Constraint> edma_constraints = null;
            Collection<Field> fields = new ArrayList<Field>();
            fields.add(vdb.newStructField("line1", "AddressLine", false));
            fields.add(vdb.newStructField("line2", "AddressLine", true));
            fields.add(vdb.newStructField("city", "City", false));
            fields.add(vdb.newStructField("zipCode", "ZipCode", false));
            vdb.newStructDomain("Address", null, fields, edma_constraints, false);
        }
        
        //Integer value domain: AnyInt
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newIntegerDomain("AnyInt", null, null, null, edma_constraints, false);
        }
        
        //Integer value domain: PosInt
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newIntegerDomain("PosInt", null, 1, null, edma_constraints, false);
        }
        
        //Integer value domain: NotNegInt
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newIntegerDomain("NotNegInt", null, 0, null, edma_constraints, false);
        }
        
        //String value domain: AnyString
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newStringDomain("AnyString", null, null, null, null, edma_constraints, false);
        }
        
        //Enum value domain: WetsuitSize
        {
            ArrayList<Constraint> edma_constraints = null;
            Collection<String> elements = new ArrayList<String>();
            elements.add("XS");
            elements.add("S");
            elements.add("M");
            elements.add("L");
            elements.add("XL");
            elements.add("XXL");
            vdb.newEnumDomain("WetsuitSize", "CourseReg", elements, edma_constraints, false);
        }
        
        //Integer value domain: BootSize
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newIntegerDomain("BootSize", "CourseReg", 25, 48, edma_constraints, false);
        }
        
        //String value domain: CourseName
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newStringDomain("CourseName", "CourseReg", 1, null, null, edma_constraints, false);
        }
        
        //Struct value domain: CourseSize
        {
            ArrayList<Constraint> edma_constraints = new ArrayList<Constraint>(1);
            edma_constraints.add(new Constraint("minLTEMax", "No description given"));
            Collection<Field> fields = new ArrayList<Field>();
            fields.add(vdb.newStructField("min", "PosInt", false));
            fields.add(vdb.newStructField("max", "PosInt", false));
            vdb.newStructDomain("CourseSize", "CourseReg", fields, edma_constraints, false);
        }
        
        //Enum value domain: CourseStatus
        {
            ArrayList<Constraint> edma_constraints = null;
            Collection<String> elements = new ArrayList<String>();
            elements.add("Created");
            elements.add("Ready");
            elements.add("Running");
            elements.add("Cancelled");
            elements.add("Completed");
            vdb.newEnumDomain("CourseStatus", "CourseReg", elements, edma_constraints, false);
        }
        
        //Struct value domain: StudentInfo
        {
            ArrayList<Constraint> edma_constraints = null;
            Collection<Field> fields = new ArrayList<Field>();
            fields.add(vdb.newStructField("name", "Name", false));
            fields.add(vdb.newStructField("email", "Email", false));
            fields.add(vdb.newStructField("mobile", "Mobile", false));
            fields.add(vdb.newStructField("wetsuitSize", "WetsuitSize", false));
            fields.add(vdb.newStructField("bootSize", "BootSize", false));
            vdb.newStructDomain("StudentInfo", "CourseReg", fields, edma_constraints, false);
        }
        
        //Struct value domain: TeacherInfo
        {
            ArrayList<Constraint> edma_constraints = null;
            Collection<Field> fields = new ArrayList<Field>();
            fields.add(vdb.newStructField("name", "Name", false));
            fields.add(vdb.newStructField("email", "Email", false));
            fields.add(vdb.newStructField("mobile", "Mobile", false));
            vdb.newStructDomain("TeacherInfo", "CourseReg", fields, edma_constraints, false);
        }
        
        //List value domain: StudentInfoList
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newListDomain("StudentInfoList", "CourseReg", "StudentInfo", null, null, edma_constraints, false);
        }
        
        //Struct value domain: CourseInfo
        {
            ArrayList<Constraint> edma_constraints = null;
            Collection<Field> fields = new ArrayList<Field>();
            fields.add(vdb.newStructField("id", "CourseID", false));
            fields.add(vdb.newStructField("start", "DateAndTime", false));
            fields.add(vdb.newStructField("type", "CourseName", false));
            fields.add(vdb.newStructField("status", "CourseStatus", false));
            fields.add(vdb.newStructField("students", "StudentInfoList", false));
            fields.add(vdb.newStructField("teacher", "TeacherInfo", true));
            vdb.newStructDomain("CourseInfo", "CourseReg", fields, edma_constraints, false);
        }
        
        //List value domain: CourseInfoList
        {
            ArrayList<Constraint> edma_constraints = null;
            vdb.newListDomain("CourseInfoList", "CourseReg", "CourseInfo", null, null, edma_constraints, false);
        }
        
        //Struct value domain: CourseProcessResult
        {
            ArrayList<Constraint> edma_constraints = null;
            Collection<Field> fields = new ArrayList<Field>();
            fields.add(vdb.newStructField("canceled", "CourseInfoList", false));
            fields.add(vdb.newStructField("running", "CourseInfoList", false));
            vdb.newStructDomain("CourseProcessResult", "CourseReg", fields, edma_constraints, false);
        }
        MetaEnvironment edma_environment = new MetaEnvironment("DiveWithUs");
        {
            MetaDataModel edma_model = new MetaDataModel("CourseReg", "DiveWithUs");
        
            //Singleton : SchoolInfo
            {
                MetaSingleton singleton = new MetaSingleton("SchoolInfo", null);
                new MetaAttribute(singleton, "name", "Name", false, true, null);
                edma_model.addSingleton(singleton);
            }
        
            // Kind : Person
            MetaKind person = new MetaKind("Person", null, null);
            {
                new MetaAttribute(person, "name", "Name", false, true, null);
                new MetaAttribute(person, "email", "Email", false, true, null);
                new MetaAttribute(person, "mobile", "Mobile", false, true, null);
                new MetaAttribute(person, "balance", "NotNegInt", false, true, null);
                //Index:  Unique (email)
                {
                    MetaIndex edma_index = new MetaIndex(IndexType.Unique);
                    edma_index.addAttribute(person.getAttribute(1));
                    person.addIndex(edma_index);
                }
                //Index:  Unique (mobile)
                {
                    MetaIndex edma_index = new MetaIndex(IndexType.Unique);
                    edma_index.addAttribute(person.getAttribute(2));
                    person.addIndex(edma_index);
                }
                //Index:  Compare (name)
                {
                    MetaIndex edma_index = new MetaIndex(IndexType.Compare);
                    edma_index.addAttribute(person.getAttribute(0));
                    person.addIndex(edma_index);
                }
            }
            edma_model.addKind(person);
        
            // Kind : Teacher
            MetaKind teacher = new MetaKind("Teacher", null, person);
            {
                new MetaAttribute(teacher, "salary", "PosInt", false, false, null);
            }
            edma_model.addKind(teacher);
        
            // Kind : Student
            MetaKind student = new MetaKind("Student", null, person);
            {
                new MetaAttribute(student, "wetsuitSize", "WetsuitSize", false, false, null);
                new MetaAttribute(student, "bootSize", "BootSize", false, false, null);
                //Index:  Equal (wetsuitSize)
                {
                    MetaIndex edma_index = new MetaIndex(IndexType.Equal);
                    edma_index.addAttribute(student.getAttribute(0));
                    student.addIndex(edma_index);
                }
            }
            edma_model.addKind(student);
        
            // Kind : CourseType
            MetaKind courseType = new MetaKind("CourseType", null, null);
            {
                new MetaAttribute(courseType, "name", "CourseName", false, false, null);
                new MetaAttribute(courseType, "size", "CourseSize", false, false, null);
                new MetaAttribute(courseType, "price", "PosInt", false, false, null);
                //Index:  Unique (name)
                {
                    MetaIndex edma_index = new MetaIndex(IndexType.Unique);
                    edma_index.addAttribute(courseType.getAttribute(0));
                    courseType.addIndex(edma_index);
                }
            }
            edma_model.addKind(courseType);
        
            // Kind : Course
            MetaKind course = new MetaKind("Course", null, null);
            {
                new MetaAttribute(course, "startDate", "Date", false, false, null);
                new MetaAttribute(course, "startTime", "Time", false, false, null);
                new MetaAttribute(course, "status", "CourseStatus", false, true, null);
                //Index:  Compare (startDate)
                {
                    MetaIndex edma_index = new MetaIndex(IndexType.Compare);
                    edma_index.addAttribute(course.getAttribute(0));
                    course.addIndex(edma_index);
                }
                //Index:  Equal (status)
                {
                    MetaIndex edma_index = new MetaIndex(IndexType.Equal);
                    edma_index.addAttribute(course.getAttribute(2));
                    course.addIndex(edma_index);
                }
            }
            edma_model.addKind(course);
        
            //Relations:
            {
                MetaRelation mr = new MetaRelation("CourseDependency", courseType, "courseType", courseType, "required", RelationType.ManyToMany);
                edma_model.addRelation(mr);
            }
            {
                MetaRelation mr = new MetaRelation("CourseTypes", course, "course", courseType, "courseType", RelationType.ManyToOne);
                edma_model.addRelation(mr);
                {
                    MetaIndex metaIndexOnA0 = new MetaIndex(IndexType.Compare);
                    metaIndexOnA0.setKind(mr.getKindA());
                    mr.addIndexOnA(metaIndexOnA0);
                    MetaAttribute attstartDate = course.getAttribute("startDate");
                    metaIndexOnA0.addAttribute(attstartDate);
                    MetaIndex metaIndexOnA1 = new MetaIndex(IndexType.Compare);
                    metaIndexOnA1.setKind(mr.getKindA());
                    mr.addIndexOnA(metaIndexOnA1);
                    MetaAttribute attstatus = course.getAttribute("status");
                    metaIndexOnA1.addAttribute(attstatus);
                    metaIndexOnA1.addAttribute(attstartDate);
                }
            }
            {
                MetaRelation mr = new MetaRelation("PassedCourses", student, "student", courseType, "passedCourse", RelationType.ManyToMany);
                edma_model.addRelation(mr);
            }
            {
                MetaRelation mr = new MetaRelation("StudentEnrollment", course, "course", student, "student", RelationType.ManyToMany);
                edma_model.addRelation(mr);
            }
            {
                MetaRelation mr = new MetaRelation("TeachingAbility", teacher, "teacher", courseType, "courseType", RelationType.ManyToMany);
                edma_model.addRelation(mr);
            }
            {
                MetaRelation mr = new MetaRelation("TeacherEnrollment", course, "course", teacher, "teacher", RelationType.ManyToOne);
                edma_model.addRelation(mr);
            }
        
            //Views:
            {
                MetaMethod view = new MetaMethod("searchCourses", "");
                view.addInputParameter("courseType", "CourseTypeID", true);
                view.addInputParameter("start", "Date", false);
                view.addInputParameter("end", "Date", false);
                view.addOutputParameter("courseList", "CourseInfoList", false);
                edma_model.addView(view);
            }
            {
                MetaMethod view = new MetaMethod("getStudentEnrollments", "");
                view.addInputParameter("studentID", "StudentID", false);
                view.addOutputParameter("courseList", "CourseInfoList", false);
                edma_model.addView(view);
            }
            {
                MetaMethod view = new MetaMethod("getAllCourseTypes", "");
                view.addOutputParameter("courseTypeList", "CourseTypeList", false);
                edma_model.addView(view);
            }
            {
                MetaMethod view = new MetaMethod("getAllPersons", "");
                view.addOutputParameter("personList", "PersonList", false);
                edma_model.addView(view);
            }
            {
                MetaMethod view = new MetaMethod("getSchoolName", "");
                view.addOutputParameter("schoolName", "Name", false);
                edma_model.addView(view);
            }
            {
                MetaMethod view = new MetaMethod("getCourseType", "");
                view.addInputParameter("courseID", "CourseID", false);
                view.addOutputParameter("courseType", "CourseType", true);
                edma_model.addView(view);
            }
            {
                MetaMethod view = new MetaMethod("sellWetsuit", "");
                view.addInputParameter("size", "WetsuitSize", false);
                view.addInputParameter("date", "Date", false);
                view.addOutputParameter("teachers", "PersonList", false);
                edma_model.addView(view);
            }
            {
                MetaMethod view = new MetaMethod("sellWetsuit2", "");
                view.addInputParameter("size", "WetsuitSize", false);
                view.addInputParameter("date", "Date", false);
                view.addOutputParameter("teachers", "PersonList", false);
                edma_model.addView(view);
            }
            {
                MetaMethod view = new MetaMethod("testView", "");
                view.addInputParameter("email", "Email", false);
                view.addOutputParameter("person", "Person", false);
                edma_model.addView(view);
            }
        
            //Actions:
            {
                MetaMethod action = new MetaMethod("setSchoolName", "");
                action.addInputParameter("name", "Name", false);
                edma_model.addAction(action);
            }
            {
                MetaMethod action = new MetaMethod("createPerson", "Creates a new person");
                action.addInputParameter("name", "Name", false);
                action.addInputParameter("email", "Email", false);
                action.addInputParameter("mobile", "Mobile", false);
                action.addOutputParameter("id", "PersonID", false);
                edma_model.addAction(action);
            }
            {
                MetaMethod action = new MetaMethod("createStudent", "");
                action.addInputParameter("personID", "PersonID", false);
                action.addInputParameter("wetsuitSize", "WetsuitSize", false);
                action.addInputParameter("bootSize", "BootSize", false);
                action.addOutputParameter("id", "StudentID", false);
                edma_model.addAction(action);
            }
            {
                MetaMethod action = new MetaMethod("createTeacher", "");
                action.addInputParameter("personID", "PersonID", false);
                action.addInputParameter("salary", "PosInt", false);
                action.addOutputParameter("id", "TeacherID", false);
                edma_model.addAction(action);
            }
            {
                MetaMethod action = new MetaMethod("addCourseTypeToTeacher", "");
                action.addInputParameter("courseTypeID", "CourseTypeID", false);
                action.addInputParameter("teacherID", "TeacherID", false);
                edma_model.addAction(action);
            }
            {
                MetaMethod action = new MetaMethod("createCourseType", "");
                action.addInputParameter("name", "CourseName", false);
                action.addInputParameter("size", "CourseSize", false);
                action.addInputParameter("price", "PosInt", false);
                action.addOutputParameter("id", "CourseTypeID", false);
                edma_model.addAction(action);
            }
            {
                MetaMethod action = new MetaMethod("addCourseTypeRequirement", "");
                action.addInputParameter("courseTypeID", "CourseTypeID", false);
                action.addInputParameter("requiredID", "CourseTypeID", false);
                edma_model.addAction(action);
            }
            {
                MetaMethod action = new MetaMethod("createCourse", "");
                action.addInputParameter("start", "DateAndTime", false);
                action.addInputParameter("courseTypeID", "CourseTypeID", false);
                action.addOutputParameter("id", "CourseID", false);
                edma_model.addAction(action);
            }
            {
                MetaMethod action = new MetaMethod("addStudentToCourse", "");
                action.addInputParameter("studentID", "StudentID", false);
                action.addInputParameter("courseID", "CourseID", false);
                edma_model.addAction(action);
            }
            {
                MetaMethod action = new MetaMethod("setTeacherOnCourse", "");
                action.addInputParameter("teacherID", "TeacherID", false);
                action.addInputParameter("courseID", "CourseID", false);
                edma_model.addAction(action);
            }
            {
                MetaMethod action = new MetaMethod("addPassedCourseToStudent", "");
                action.addInputParameter("studentID", "StudentID", false);
                action.addInputParameter("courseTypeID", "CourseTypeID", false);
                edma_model.addAction(action);
            }
            {
                MetaMethod action = new MetaMethod("testAction", "");
                edma_model.addAction(action);
            }
            edma_environment.addMetaDataModel(edma_model);
        }
        vdb.buildWithEnvironment(edma_environment);
        //Hack to make serializeable work...
        IndexUtil.setValueDomainDefinitions(edma_environment.getValueDomainDefinitions());
        return edma_environment;
    }

    /**
     * Constructor
     * @param factory  The runtime factory
     */
    public DiveWithUs(IRuntimeFactory factory)
    {
        edma_CourseReg = new CourseRegFactoryImpl(factory.getDataModelInstanceFactory(environment.getMetaDataModel(0)));
    }

    /**
     * Get access to the CourseReg data model factory
     * @return  The CourseReg data model storage
     */
    public CourseRegFactoryImpl getCourseRegFactory()
    {
        return edma_CourseReg;
    }
}
