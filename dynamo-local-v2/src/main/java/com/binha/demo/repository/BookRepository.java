package com.binha.demo.repository;

import com.binha.demo.model.Book;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface BookRepository extends CrudRepository<Book, String> {
    Optional<Book> findByIsbn(String isbn);
}
