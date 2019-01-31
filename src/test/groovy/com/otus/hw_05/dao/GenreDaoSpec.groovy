package com.otus.hw_05.dao

import com.otus.hw_05.dao.impl.GenreDaoJpaImpl
import com.otus.hw_05.domain.Genre
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification
import spock.lang.Subject

@DataJpaTest
@Import(GenreDaoJpaImpl)
class GenreDaoSpec extends Specification {

    @Subject
    @Autowired
    GenreDao genreDao

    void setup() {
        assert genreDao != null
    }

    def "can find genre by id"() {
        given:
        Genre genre = genreDao.findById(2)

        expect:
        genre.getId() == 2
        genre.getGenre() == 'Fantasy'
    }

    def "can find genre by its name, case insensitive"() {
        given:
        Genre fantasy = genreDao.findByGenre('fant')
        Genre novel = genreDao.findByGenre('nove')

        expect:
        fantasy.getGenre() == 'Fantasy'
        novel.getGenre() == 'Novel'
    }

    def "can find all genres"() {
        given:
        Iterable<Genre> genres = genreDao.findAll()

        expect:
        genres.size() > 0
    }

    def "returns null if genre is not found"() {
        given:
        Genre genreById = genreDao.findById(10)
        Genre genreByName = genreDao.findByGenre('horror')

        expect:
        genreById == null
        genreByName == null
    }

    def "can count genres in the table"() {
        expect:
        genreDao.count() == 7
    }

    def "can save a genre"() {
        given:
        def genreName = 'Horror'
        def savedGenre = genreDao.save(new Genre(genreName))

        and:
        def foundGenre = genreDao.findByGenre(genreName)

        expect:
        foundGenre == savedGenre
        genreDao.findAll().any { it.genre == genreName }
    }


    def "can save a collection of genres"() {
        given:
        def genres = genreDao.findAll()

        and:
        def genreCollection = ['Drama', 'Comedy', 'Satire'].collect { new Genre(it) }

        when:
        genres = genreDao.save(genreCollection)

        then:
        genres.size() == old(genres.size()) + genreCollection.size()
        genres.any { it.genre == genreCollection.first().genre }
        genres.any() { it.genre == genreCollection.last().genre }
    }

    def "can delete a genre"() {
        given:
        def genreName = 'fantasy'
        def genre = genreDao.findByGenre(genreName)

        and:
        genreDao.delete(genre)

        expect:
        genreDao.findByGenre(genreName) == null
    }

    void cleanup() {
        genreDao = null
    }
}
