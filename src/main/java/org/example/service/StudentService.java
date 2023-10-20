package org.example.service;

import org.example.entity.Student;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface StudentService {
    Student findById(Integer id);

    List<Student> findAll();

    List<Student> findByName(String name);

    @Transactional
    Student save(String name, Integer age, Date birth) throws Exception;

    @Transactional
    void delete(Integer id) throws Exception;

    int modifyNameById(String name, Integer id);
}
