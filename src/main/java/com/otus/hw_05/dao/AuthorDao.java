package com.otus.hw_05.dao;

import com.otus.hw_05.domain.Author;

public interface AuthorDao extends CommonRepository<Author> {

    Iterable<Author> findByName(String name);

}
