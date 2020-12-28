package edu.java.spring.dao;

import edu.java.spring.model.Student;

import java.util.List;

public interface StudentDAO {

    void insert(Student student);

    void delete(String id);

    List<Student> list();

    Student get(String id);

    void update(Student student);
}
