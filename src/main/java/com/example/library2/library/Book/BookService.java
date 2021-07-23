package com.example.library2.library.Book;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> getBooks(){
        return bookRepository.getBooks() ;
    }
   public Book getBook(int id){
        return bookRepository.findById(id).orElse(null);
   }
   public void saveBook(Book book){
        bookRepository.save(book);
   }
   public void deleteBook(int id){
        bookRepository.deleteById(id);
   }


}
