package com.example.library2.library.Transaction;


import com.example.library2.library.Book.Book;
import com.example.library2.library.Student.Student;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String transactionId;

    @Enumerated(value=EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(value =EnumType.STRING)
    private TransactionStatus transactionStatus;

    private String message;
    private int fine;

    @CreationTimestamp
    private Date date;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("transactions")
    private Student student;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"transactions","student"})
    private Book book;



}
