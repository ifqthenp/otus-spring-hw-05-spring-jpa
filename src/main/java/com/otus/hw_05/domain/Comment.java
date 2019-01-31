package com.otus.hw_05.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String commentary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_book_id")
    @EqualsAndHashCode.Exclude
    private Book book;

    public Comment(final String comment, final Book book) {
        this.commentary = comment;
        this.book = book;
    }

    public Comment(final String commentary) {
        this.commentary = commentary;
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + id +
            ", commentary='" + commentary + '\'' +
            ", bookId=" + book.getId() +
            '}';
    }

}
