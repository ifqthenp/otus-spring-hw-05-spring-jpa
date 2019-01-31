package com.otus.hw_05.dao.impl;

import com.otus.hw_05.dao.BookDao;
import com.otus.hw_05.domain.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

@Repository
public class BookDaoJpaImpl implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Iterable<Book> findByTitle(final String title) {
        TypedQuery<Book> query = em.createQuery(
            "SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(:title)", Book.class
        );
        return query.setParameter("title", "%" + title + "%").getResultList();
    }

    @Override
    public Iterable<Book> findByAuthor(final String author) {
        TypedQuery<Book> query = em.createQuery(
            "SELECT b FROM Book b INNER JOIN Author a ON b.authorId = a.id WHERE" +
                " LOWER(CONCAT(a.firstName, ' ', a.lastName)) LIKE LOWER(:name)", Book.class
        );
        return query.setParameter("name", "%" + author + "%").getResultList();
    }

    @Override
    public Iterable<Book> findByGenre(final String genre) {
        TypedQuery<Book> query = em.createQuery(
            "SELECT b FROM Book b JOIN Genre g ON b.genreId = g.id WHERE " +
                "LOWER(g.genre) LIKE LOWER(:genre) ", Book.class
        );
        return query.setParameter("genre", "%" + genre + "%").getResultList();
    }

    @Override
    public List<Object[]> findAllWithDetails() {
        TypedQuery<Object[]> query = em.createQuery(
            "SELECT b.id, b.title, CONCAT(a.firstName, ' ', a.lastName) AS author, g.genre, b.year" +
                " FROM Book b JOIN Author a ON b.authorId = a.id" +
                " JOIN Genre g ON b.genreId = g.id", Object[].class
        );
        return query.getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query =
            em.createQuery("SELECT COUNT(b) FROM Book b", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Book save(final Book domain) {
        em.persist(domain);
        em.flush();
        return domain;
    }

    @Override
    public Iterable<Book> save(final Collection<Book> domains) {
        domains.forEach(this::save);
        return this.findAll();
    }

    @Override
    public void delete(final Book domain) {
        em.remove(domain);
    }

    @Override
    public Book findById(final long id) {
        return em.find(Book.class, id);
    }

    @Override
    public Iterable<Book> findAll() {
        TypedQuery<Book> query =
            em.createQuery("SELECT b FROM Book b", Book.class);
        return query.getResultList();
    }

}
