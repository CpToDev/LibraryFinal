package com.example.library2.library.Student;

import com.example.library2.library.User.User;
import com.example.library2.library.User.UserRepository;
import com.example.library2.library.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;

    @GetMapping("/student/list")
    public List<Student> getAllStudents(){

        return studentService.getStudents();


    }
    @GetMapping("/student")
    public Student getStudentByUsername(@RequestParam String username){
        return studentService.getStudentByUsername(username);
    }

    @PostMapping("/student")
    public String saveStudent(@RequestBody CreateStudentRequest createStudentRequest) throws Exception {

       return studentService.saveStudent(createStudentRequest);

    }
    @PutMapping("/student")
    public String updateStudent(@RequestParam String username,@RequestBody Student student){

        return studentService.updateStudent(username,student);

    }
    @DeleteMapping("/student")
    public void deleteStudent(String username){

        studentService.deleteStudent(username);
    }
    @GetMapping("/student/detail")
    public Student getDetail(){

       return studentService.getDetail();

    }

}
