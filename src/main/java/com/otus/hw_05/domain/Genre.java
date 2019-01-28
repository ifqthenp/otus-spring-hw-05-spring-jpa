package com.otus.hw_05.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "genre")
    private String genre;

    public Genre(final String genre) {
        this.genre = genre;
    }

}
