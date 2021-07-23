package com.example.library2.library.Transaction;


import com.example.library2.library.Book.Book;
import com.example.library2.library.Book.BookRepository;
import com.example.library2.library.Student.Student;
import com.example.library2.library.Student.StudentRepository;
import com.example.library2.library.User.User;
import com.example.library2.library.User.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

        public static final Logger logger=  LoggerFactory.getLogger(TransactionService.class);

        @Autowired
        TransactionRepository transactionRepository;

        @Autowired
        BookRepository bookRepository;
        @Autowired
        StudentRepository studentRepository;
        @Autowired
        UserRepository userRepository;

        @Value("${book.max_allowed}")
        private int MAX_BOOKS_ALLOWED;

        @Value("${book.max_allowed_days}")
        private int MAX_ALLOWED_DAYS;

    @Value("${book.fine}")
        private int FINE_PER_DAY;


        public String issueBook(int bookId){

            Book book=bookRepository.findById(bookId).orElse(null);
            User user=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Student student=studentRepository.findById(user.getStudent().getId()).orElse(null);

            if(book==null){

                logger.info("invalid book id");
                return "No such book with this id";
            }
            if(student==null){
                logger.info("invalid student id");
                return "Student not found";
            }

            Transaction transaction=Transaction.builder()
                    .transactionId(UUID.randomUUID().toString())
                    .student(student)
                    .book(book)
                    .transactionType(TransactionType.ISSUE)
                    .build();

            if(student.getBooks().size()>=MAX_BOOKS_ALLOWED){
                transaction.setTransactionStatus(TransactionStatus.FAILURE);
                transaction.setMessage("Max Book Limit reached");
                return "Max Book limit reached";
            }

            if(book.getStudent()!=null){
                logger.info("book is already issued by someone ");
                transaction.setTransactionStatus(TransactionStatus.FAILURE);
                transaction.setMessage("book is already issued by someone");
                transactionRepository.save(transaction);

                return "book is already issued by someone ";

            }



            book.setStudent(student);
            student.getBooks().add(book);
            bookRepository.save(book);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            transaction.setMessage("Successfully Issued!");
            transactionRepository.save(transaction);
            logger.info("Success");
            return "Success";


        }
        public String returnBook(int bookId){

            Book book=bookRepository.findById(bookId).orElse(null);
            User user=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Student student=studentRepository.findById(user.getStudent().getId()).orElse(null);

            if(book==null){

                logger.info("invalid book id");
                return "No such book with this id";
            }
            if(student==null){
                logger.info("invalid student id");
                return "Student not found";
            }

            Transaction transaction=Transaction.builder()
                    .transactionId(UUID.randomUUID().toString())
                    .student(student)
                    .book(book)
                    .transactionType(TransactionType.RETURN)
                    .build();

            if(book.getStudent()==null){
                logger.info("Not issued by you");
                transaction.setMessage("Not issued by you");
                transaction.setTransactionStatus(TransactionStatus.FAILURE);
                return "Not issued by student "+student;
            }
            if(book.getStudent().getId()!=student.getId()){
                logger.info("Not issued by you");
                transaction.setMessage("Not issued by you");
                transaction.setTransactionStatus(TransactionStatus.FAILURE);
                return "Not issued by student "+student;
            }

            Transaction issueTransaction= transactionRepository.getIssueTransaction(student.getId(), bookId,TransactionType.ISSUE,TransactionStatus.SUCCESS).get(0);
            int no_of_days=(int)((System.currentTimeMillis()-issueTransaction.getDate().getTime())/(1000*60*60*24));

            if(no_of_days>MAX_ALLOWED_DAYS){
                transaction.setFine((no_of_days-MAX_ALLOWED_DAYS)*FINE_PER_DAY);
            }

            book.setStudent(null);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            student.getBooks().remove(book);
            transaction.setMessage("Successfully returned");
            bookRepository.save(book);
            transactionRepository.save(transaction);
            logger.info("Success");
            return "Success";


        }
}
