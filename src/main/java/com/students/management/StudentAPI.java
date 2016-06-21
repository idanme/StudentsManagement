package com.students.management;

import java.util.List;

/**
 * Created by idanm on 18/06/2016.
 */
public interface StudentAPI {
    public List<Student> getStudentList();
    public Student getStudentById(String studentId);
    public boolean deleteStudent(String studentId);
    public boolean addStudent(Student student);
    public int countStudents();
}
