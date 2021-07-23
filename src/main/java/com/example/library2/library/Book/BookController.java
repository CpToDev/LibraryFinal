package com.example.library2.library.Book;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/book/list")
    public List<Book> getBooks(){
        return bookService.getBooks();
    }
   @GetMapping("/book")
   public Book getBook(@RequestParam int id){
        return bookService.getBook(id);
   }
    @PostMapping("/book")
    public void saveBook(@RequestBody Book book){
        bookService.saveBook(book);
    }
    @DeleteMapping("/book")
    public void deleteBook(@RequestParam int id){
        bookService.deleteBook(id);
    }

}
