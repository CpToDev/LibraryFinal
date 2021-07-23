package com.example.library2.library.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BookRepository  extends JpaRepository<Book,Integer> {

    @Query("select b from Book b")
    public List<Book> getBooks();
}
