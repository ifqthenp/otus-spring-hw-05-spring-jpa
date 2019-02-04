package com.otus.hw_05.dao.impl;

import com.otus.hw_05.dao.CommentDao;
import com.otus.hw_05.domain.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class CommentDaoJpaImpl implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Iterable<Comment> findByText(final String text) {
        TypedQuery<Comment> query = em.createQuery(
            "SELECT c FROM Comment c WHERE LOWER(c.commentary) LIKE LOWER(:text)",
            Comment.class);
        return query.setParameter("text", "%" + text + "%").getResultList();
    }

    @Override
    public Iterable<Comment> findByBookId(final long bookId) {
        TypedQuery<Comment> query = em.createQuery(
            "SELECT c FROM Comment c WHERE c.book.id = (:id)", Comment.class
        );
        return query.setParameter("id", bookId).getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query =
            em.createQuery("SELECT COUNT(c) FROM Comment c", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Comment save(final Comment domain) {
        if (domain.getId() == null) {
            em.persist(domain);
        } else {
            em.merge(domain);
        }
        return domain;
    }

    @Override
    public Iterable<Comment> save(final Collection<Comment> domains) {
        domains.forEach(this::save);
        return this.findAll();
    }

    @Override
    public void delete(final Comment domain) {
        if (em.contains(domain)) {
            em.remove(domain);
        } else {
            em.merge(domain);
        }
    }

    @Override
    public Comment findById(final long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public Iterable<Comment> findAll() {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c", Comment.class);
        return query.getResultList();
    }

}
