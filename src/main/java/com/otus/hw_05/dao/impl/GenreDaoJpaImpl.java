package com.otus.hw_05.dao.impl;

import com.otus.hw_05.dao.GenreDao;
import com.otus.hw_05.domain.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class GenreDaoJpaImpl implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Genre findByGenre(final String genre) {
        try {
            TypedQuery<Genre> query = em.createQuery(
                "SELECT g FROM Genre g WHERE LOWER(g.genre) LIKE LOWER(:genre)", Genre.class
            );
            return query.setParameter("genre", "%" + genre + "%").getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public long count() {
        TypedQuery<Long> query = em.createQuery(
            "SELECT COUNT(g) FROM " + Genre.class.getName() + " g", Long.class
        );
        return query.getSingleResult();
    }

    @Override
    public Genre save(final Genre domain) {
        em.persist(domain);
        return domain;
    }

    @Override
    public Iterable<Genre> save(final Collection<Genre> domains) {
        domains.forEach(this::save);
        return this.findAll();
    }

    @Override
    public void delete(final Genre domain) {
        em.remove(domain);
    }

    @Override
    public Genre findById(final long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public Iterable<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery("SELECT g FROM Genre g", Genre.class);
        return query.getResultList();
    }

}
