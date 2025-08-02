package com.feedhub.dao.daoInterfaces;

import java.util.List;

import com.feedhub.model.Student;

public interface StudentDAO {
    boolean addStudent(Student student);
    Student getStudentById(int id);
    Student getStudentByRollNo(String rollNo);
    List<Student> getAllStudents();
    boolean updateStudent(Student student);
    boolean deleteStudent(int id);
}
