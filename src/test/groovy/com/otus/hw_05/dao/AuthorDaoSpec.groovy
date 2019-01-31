package com.otus.hw_05.dao

import com.otus.hw_05.dao.impl.AuthorDaoJpaImpl
import com.otus.hw_05.domain.Author
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import spock.lang.Subject

@DataJpaTest
@Import(AuthorDaoJpaImpl)
class AuthorDaoSpec extends Specification {

    @Subject
    @Autowired
    AuthorDao authorDao

    void setup() {
        assert authorDao != null
    }

    def "can count authors in the table"() {
        expect:
        authorDao.count() == 8
    }

    def "can find author by id"() {
        given:
        def id = 4
        Author author = authorDao.findById(id)

        expect:
        author.getId() == id
        author.getFirstName() == 'Herbert'
        author.getLastName() == 'Wells'
    }

    def "can find author either by the first or last name, case insensitive"() {
        given:
        def herbert = 'rBErt'
        def wells = 'WeL'
        def leo = 'leo'
        def oliver = 'oliver'

        and:
        def herberts = authorDao.findByName("$herbert")
        def wellses = authorDao.findByName("$wells")
        def leos = authorDao.findByName("$leo")
        def olivers = authorDao.findByName("$oliver")

        expect:
        herberts.size() == 1
        herberts.size() == wellses.size()
        herberts == wellses
        leos.size() == 2
        olivers.size() == 0
    }

    def "returns null if author is not found by id"() {
        given:
        Author authorById = authorDao.findById(0)

        expect:
        authorById == null
    }

    def "can find all authors"() {
        given:
        Iterable<Author> authors = authorDao.findAll()

        expect:
        authors.size() == 8
    }

    @DirtiesContext
    def "can save an author"() {
        given:
        def firstName = 'Georg'
        def lastName = 'Hegel'
        Author hegel = new Author(firstName, lastName)

        when:
        hegel = authorDao.save(hegel)

        then:
        hegel.id == 9
        hegel.firstName == 'Georg'
        hegel.lastName == 'Hegel'
    }

    @DirtiesContext
    def "can save a collection of authors"() {
        given:
        def authors = authorDao.findAll()

        and:
        def newAuthors = [['Charles', 'Dickens'], ['Virginia', 'Woolf']].collect {
            it -> new Author(it[0], it[1])
        }

        when:
        authors = authorDao.save(newAuthors)

        then:
        authors.size() == old(authors.size()) + newAuthors.size()
    }

    @DirtiesContext
    def "can delete an author"() {
        given:
        def id = 4
        Author author = authorDao.findById(id)

        when:
        authorDao.delete(author)

        and:
        author = authorDao.findById(id)

        then:
        author == null
    }

    void cleanup() {
        authorDao = null
    }
}
