package com.example.library2.library.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {


    @Query(value = "select t from Transaction t where t.student.id=:studentId and t.book.id=:bookId and t.transactionType=:transactionType and t.transactionStatus=:status order by t.id desc",nativeQuery = false)
    public List<Transaction> getIssueTransaction(int studentId, int bookId,TransactionType transactionType,TransactionStatus status);

}
