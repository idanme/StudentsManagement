package com.students.management;

import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by IdanM on 21/06/2016.
 */

@Path("/students")
public class StudentsService {
    private StudentAPI studentApi = new StudentImpel();

    @GET
    @Path("/list")
    @Produces("application/json")
    public Response listStudents() {
        System.out.println("List students");
        List<Student> studentsList = studentApi.getStudentList();
        String studentsListAsJson = new Gson().toJson(studentsList);
        return Response.ok(studentsListAsJson, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/find/{studentId}")
    @Produces("application/json")
    public Response listStudentById(@PathParam("studentId") String studentId) {
        System.out.println("Find student with id " + studentId);
        Student student = studentApi.getStudentById(studentId);
        // Student id was found in the database
        if (student != null) {
            return Response.ok(student, MediaType.APPLICATION_JSON).build();
        }
        // Student id was not found in the database
        else {
            ResponseCode responseCode = ResponseCode.fail("Student id " + studentId + " was not found in the database");
            return Response.status(Response.Status.NOT_FOUND).entity(new Gson().toJson(responseCode)).build();
        }
    }

    @DELETE
    @Path("/delete/{studentId}")
    @Produces("application/json")
    public Response deleteStudentById(@PathParam("studentId") String studentId) {
        System.out.println("Delete student with id " + studentId);
        ResponseCode responseCode;
        Gson responseGson = new Gson();

        boolean studentDeleted = studentApi.deleteStudent(studentId);
        // Student id was found and deleted
        if (studentDeleted) {
            responseCode = ResponseCode.success("Student id " + studentId + " has been deleted.");
            return Response.ok(responseCode, MediaType.APPLICATION_JSON).build();
        }
        // Student id was not found in the database and was not deleted
        else {
            responseCode = ResponseCode.fail("Student id " + studentId + " was not found in the database and therefore cannot be deleted.");
            return Response.status(Response.Status.NOT_FOUND).entity(responseGson.toJson(responseCode)).build();
        }
    }

    @POST
    @Path("/add")
    @Consumes("application/json")
    public Response addStudent(Student student) {
        System.out.println("Add student with id " + student.getId());
        ResponseCode responseCode;
        String responseString;
        Gson responseGson = new Gson();

        boolean isValidStudent = StudentInputValidation.isValidStudent(student);

        // Student details are valid
        if (isValidStudent) {
            int numberOfStudentsInDatabase = studentApi.countStudents();
            // Students table is not full
            if (numberOfStudentsInDatabase < StudentImpel.getMaxStudentsNumber()) {
                boolean studentAdded = studentApi.addStudent(student);
                // Student created in the database
                if (studentAdded) {
                    responseString = "Student with id " + student.getId() + " created successfully.";
                    responseCode = ResponseCode.success(responseString, student);
                    return Response.status(Response.Status.CREATED).entity(responseGson.toJson(responseCode)).build();
                }
                // Conflict - Student id was found in the database. Therefore, the student was not created
                else {
                    responseString = "Student with id " + student.getId() + " is already exists in the database and therefore, cannot be added.";
                    responseCode = ResponseCode.fail(responseString);
                    return Response.status(Response.Status.CONFLICT).entity(responseGson.toJson(responseCode)).build();
                }

            }
            // Students table is full
            else {
                responseString = "Students table is full. Therefore, the student cannot be added.";
                responseCode = ResponseCode.fail(responseString);
                return Response.status(Response.Status.CONFLICT).entity(responseGson.toJson(responseCode)).build();
            }
        }
        // Student details are not valid
        else {
            responseString = "Student data is not valid and therefore, cannot be added.";
            responseCode = ResponseCode.fail(responseString);
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(responseGson.toJson(responseCode)).build();
        }
    }
}
