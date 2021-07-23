package com.example.library2.library.Student;


import com.example.library2.library.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentCacheRepository{

    private static final String STUDENT_PREFIX="student::";


    @Autowired
    RedisTemplate<String,Object>redisTemplate;


    public String getStudentKey(String username){
        return STUDENT_PREFIX+username;
    }

    public void saveStudentByUsername(User user){

        if(user.getStudent()==null){
            return;
        }
        Student student=user.getStudent();
        redisTemplate.opsForValue().set(getStudentKey(user.getUsername()),student);

    }
    public Student getStudentByUser(User user){

        String username=user.getUsername();
        return (Student) redisTemplate.opsForValue().get(getStudentKey(username));

    }



}
