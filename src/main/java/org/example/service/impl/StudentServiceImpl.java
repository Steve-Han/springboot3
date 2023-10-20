package org.example.service.impl;

import org.example.dao.StudentRepository;
import org.example.entity.Student;
import org.example.service.HelloService;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentDao;
    @Autowired
    private HelloService helloService;

    @Override
    public Student findById(Integer id) {
        return studentDao.findById(id).get();
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public List<Student> findByName(String name) {
        return studentDao.findByName(name);
    }

    @Override
    @Transactional
    public Student save(String name, Integer age, Date birth) {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setBirth(birth);

        return studentDao.save(student);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws Exception {
        studentDao.deleteById(id);
    }

    @Override
    @Transactional
    public int modifyNameById(String name, Integer id) {
        int i = studentDao.modifyNameById(name, id);
        return i;
    }
}

