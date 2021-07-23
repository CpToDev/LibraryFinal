package com.example.library2.library.Student;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStudentRequest {

    private String name;
    private String email;
    private int age;
    private String username;
    private String password;

}
