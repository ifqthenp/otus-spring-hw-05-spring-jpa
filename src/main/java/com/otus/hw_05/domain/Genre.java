package com.otus.hw_05.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    private long id;
    private String genre;

    public Genre(final String genre) {
        this.genre = genre;
    }

}
