package com.students.management;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by idanm on 18/06/2016.
 */
public class StudentImpel implements StudentAPI {
    private static SessionFactory factory;
    private final static int MAX_STUDENTS_NUMBER = 100;

    static {
        // Create the session factory
        try {
            factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public List<Student> getStudentList() {
        List<Student> studentsList = new LinkedList<Student>();
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            studentsList = session.createQuery("FROM com.students.management.Student").list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return studentsList;
        }
    }

    @Override
    public Student getStudentById(String studentId) {
        Student student = null;
        List<Student> studentsList;
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Student.class);
            criteria.add(Restrictions.eq("id", studentId));
            studentsList = criteria.list();
            if (studentsList.size() > 0) {
                student = studentsList.get(0);
            } else {
                student = null;
            }
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return student;
        }
    }

    @Override
    public boolean deleteStudent(String studentId) {
        int affectedStudents = 0;
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String queryString = "DELETE FROM com.students.management.Student WHERE id = :studentId";
            Query query = session.createQuery(queryString);
            query.setParameter("studentId", studentId);
            affectedStudents = query.executeUpdate();
            transaction.commit();
            System.out.println("Rows affected: " + affectedStudents);
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            if (affectedStudents == 1)
                return true;
            else
                return false;
        }
    }

    @Override
    public boolean addStudent(Student student) {
        boolean studentAdded = true;
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
        } catch (ConstraintViolationException e) {
            studentAdded = false;
        }
        catch (HibernateException e) {
            studentAdded = false;
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return studentAdded;
        }
    }

    @Override
    public int countStudents() {
        List<Student> studentsList = getStudentList();
        return studentsList.size();
    }

    public static int getMaxStudentsNumber() {
        return MAX_STUDENTS_NUMBER;
    }
}

