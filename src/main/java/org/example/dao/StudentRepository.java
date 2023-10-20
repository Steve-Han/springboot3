package org.example.dao;

import org.example.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByName(String name);

    @Modifying
    @Query("update Student stu set stu.name = ?1 where stu.id = ?2")
    int modifyNameById(String name, Integer id);
}
