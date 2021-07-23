package com.example.library2.library.Transaction;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction/issue")
    public String issueBook(@RequestParam int bookId){

        return transactionService.issueBook(bookId);

    }
    @PostMapping("/transaction/return")
    public String returnBook(@RequestParam int bookId){

        return transactionService.returnBook(bookId);
    }


}
