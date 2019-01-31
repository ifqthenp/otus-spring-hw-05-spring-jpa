package com.otus.hw_05.dao.impl;

import com.otus.hw_05.dao.AuthorDao;
import com.otus.hw_05.domain.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class AuthorDaoJpaImpl implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Iterable<Author> findByName(final String name) {
        TypedQuery<Author> query = em.createQuery(
            "SELECT a FROM Author a WHERE LOWER(CONCAT(a.firstName, ' ', a.lastName)) LIKE LOWER(:name)", Author.class
        );
        return query.setParameter("name", "%" + name + "%").getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query = em.createQuery(
            "SELECT COUNT(a) FROM Author a", Long.class
        );
        return query.getSingleResult();
    }

    @Override
    public Author save(final Author domain) {
        em.persist(domain);
        return domain;
    }

    @Override
    public Iterable<Author> save(final Collection<Author> domains) {
        domains.forEach(this::save);
        return this.findAll();
    }

    @Override
    public void delete(final Author domain) {
        em.remove(domain);
    }

    @Override
    public Author findById(final long id) {
        return em.find(Author.class, id);
    }

    @Override
    public Iterable<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a", Author.class);
        return query.getResultList();
    }

}
