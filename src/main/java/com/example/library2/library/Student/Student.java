package com.example.library2.library.Student;


import com.example.library2.library.Book.Book;
import com.example.library2.library.Transaction.Transaction;
import com.example.library2.library.User.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @Column(unique = true,nullable = false)
    private String name;
    private int age;
    private String email;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "student")
    @JsonIgnoreProperties("student")
    private List<Book> books;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "student")
    private List<Transaction> transactions;

    @OneToOne(mappedBy = "student")
    @JsonIgnoreProperties({"student","password"})
    private User user;



}
