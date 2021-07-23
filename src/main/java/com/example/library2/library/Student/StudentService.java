package com.example.library2.library.Student;


import com.example.library2.library.User.User;
import com.example.library2.library.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.Authenticator;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StudentCacheRepository studentCacheRepository;

    @Value("${person.authority.student}")
    private String STUDENT_AUTHORITY;

    public List<Student> getStudents(){
        return studentRepository.getStudents();
    }
    public Student getStudentByUsername(String username){



        return studentRepository.getStudentByUsername(username);

    }

    public String updateStudent(String username,Student student){

      Student s =getStudentByUsername(username);
      if(s==null)
          return "No Student found!";
      s.setAge(student.getAge());
      s.setEmail(student.getEmail());
      studentRepository.save(s);
      return "Succesfully updated!";

    }
    public String saveStudent(CreateStudentRequest createStudentRequest)  {

        User user=User.builder()
                .password(passwordEncoder.encode(createStudentRequest.getPassword()))
                .username(createStudentRequest.getUsername())
                .authorities(STUDENT_AUTHORITY)
                .build();

        Student student=Student.builder()
                .name(createStudentRequest.getName())
                .age(createStudentRequest.getAge())
                .email(createStudentRequest.getEmail())
                .build();

        user.setStudent(student);
        studentCacheRepository.saveStudentByUsername(user);
        studentRepository.save(student);
        userRepository.save(user);


        return "Successfully created!";


    }
    public void deleteStudent(String username){

        User user=userRepository.findByUsername(username);
        Student student=user.getStudent();
        user.setStudent(null);
        studentRepository.delete(student);
        userRepository.delete(user);
    }

    public Student getDetail(){

        User user= (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Student student=studentCacheRepository.getStudentByUser(user);
        if(student==null) {
            return studentRepository.findById(user.getStudent().getId()).orElse(null);
        }
        return student;
    }
}
