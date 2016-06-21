package com.students.management;

import org.apache.commons.lang.math.NumberUtils;

/**
 * Created by idanm on 20/06/2016.
 */
public class StudentInputValidation {
    public static boolean isValidStudent(Student student) {
        boolean validStudent;
        String studentId = student.getId();
        String studentFirstName = student.getFirstName();
        String studentLastName = student.getLastName();
        String studentGender = student.getGender();
        double studentAverageGrade = student.getAverageGrade();

        if (!isValidStudentId(studentId)) {
            validStudent = false;
        } else if (!isValidStudentName(studentFirstName, studentLastName)) {
            validStudent = false;
        } else if (!isValidStudentGender(studentGender)) {
            validStudent = false;
        } else if (!isValidStudentAverageGrade(studentAverageGrade)) {
            validStudent = false;
        } else {
            validStudent = true;
        }
        return validStudent;
    }

    private static boolean isValidStudentId(String studentId) {
        if (studentId == null) {
            return false;
        } else if (NumberUtils.isNumber(studentId) && studentId.length() == 9) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isValidStudentName(String studentFirstName, String studentLastName) {
        if (isName(studentFirstName) && isName(studentLastName)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isName(String studentName) {
        if (studentName == null) {
            return true;
        } else if (studentName.matches("[a-zA-Z-]+")) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isValidStudentGender(String studentGender) {
        if (studentGender == null) {
            return true;
        }
        else if (studentGender.toLowerCase().equals("male") || studentGender.toLowerCase().equals("female")) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isValidStudentAverageGrade(double studentAverageGrade) {
        if (studentAverageGrade >= 0 && studentAverageGrade <= 100) {
            return true;
        } else {
            return false;
        }
    }
}
