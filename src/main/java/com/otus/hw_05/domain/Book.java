package com.otus.hw_05.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
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

    @OneToMany(
        mappedBy = "book",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @EqualsAndHashCode.Exclude
    private List<Comment> comments = new ArrayList<>();

    public Book(final String title, final long authorId, final long genreId, final String year) {
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
        this.year = year;
    }

    public void addComment(final Comment comment) {
        comments.add(comment);
        comment.setBook(this);
    }

    public void removeComment(final Comment comment) {
        comments.remove(comment);
        comment.setBook(null);
    }

}
