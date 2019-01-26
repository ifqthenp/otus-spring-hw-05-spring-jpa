package com.otus.hw_05.dao;

import com.otus.hw_05.domain.Genre;

public interface GenreDao extends CommonRepository<Genre> {

    Genre findByGenre(String genre);

}
