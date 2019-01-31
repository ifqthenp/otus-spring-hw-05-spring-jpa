package com.otus.hw_05.dao;

import com.otus.hw_05.domain.Book;

import java.util.List;

public interface BookDao extends CommonRepository<Book> {

    Iterable<Book> findByTitle(String title);

    Iterable<Book> findByAuthor(String author);

    Iterable<Book> findByGenre(String genre);

    List<Object[]> findAllWithDetails();

}
