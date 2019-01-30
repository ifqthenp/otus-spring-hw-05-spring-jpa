package com.otus.hw_05.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "fk_author_id")
    private long authorId;

    @Column(name = "fk_genre_id")
    private long genreId;

    @Column(name = "written")
    private String year;

    public Book(final String title, final long authorId, final long genreId, final String year) {
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
        this.year = year;
    }

}
