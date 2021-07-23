package com.example.library2.library.Student;

import com.example.library2.library.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Integer> {


    @Query("select s from Student s")
    public List<Student> getStudents();

    @Query("select s from Student s where s.name=:username")
    public Student getStudentByUsername(String username);

    public Student findByUser(User user);

}
