package com.example.bookapp.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.bookapp.Book;
import org.springframework.stereotype.Repository;


public interface BookRepository extends CrudRepository<Book, Long> {

     Iterable<Book> findByStatusTrue();
    Iterable<Book> findByStatusFalse();


}
