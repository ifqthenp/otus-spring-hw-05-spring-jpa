package com.otus.hw_05.dao

import com.otus.hw_05.dao.impl.BookDaoJpaImpl
import com.otus.hw_05.dao.impl.CommentDaoJpaImpl
import com.otus.hw_05.domain.Book
import com.otus.hw_05.domain.Comment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import spock.lang.Subject

@DataJpaTest
@Import([BookDaoJpaImpl, CommentDaoJpaImpl])
class BookDaoSpec extends Specification {

    @Subject
    @Autowired
    BookDao bookDao

    @Autowired
    CommentDao commentDao

    void setup() {
        assert bookDao != null
        assert commentDao != null
    }

    def "can find book by id"() {
        given:
        def bookId = 2

        and:
        Book book = bookDao.findById(bookId)

        expect:
        with(book) {
            id == bookId
            title == 'Jane Eyre'
            authorId == 2
            genreId == 3
            year == '1847'
        }
    }

    def "can find books by title, case insensitive"() {
        given:
        Iterable<Book> childBooks = bookDao.findByTitle('child')
        Iterable<Book> timeBooks = bookDao.findByTitle('time')
        Iterable<Book> emptyBooks = bookDao.findByTitle('test')

        expect:
        childBooks.size() == 1
        timeBooks.size() == 2
        emptyBooks.isEmpty()
    }

    def "returns null if book is not found by id"() {
        given:
        Book book = bookDao.findById(0)

        expect:
        book == null
    }

    def "can find all books"() {
        given:
        Iterable<Book> books = bookDao.findAll()

        expect:
        books.size() == 10
    }

    def "can count books in the table"() {
        expect:
        bookDao.count() == 10
    }

    @DirtiesContext
    def "can save a book"() {
        given:
        Book book = new Book('War and Peace', 5, 1, '1869')
        def allBooks = bookDao.findAll()

        when:
        book = bookDao.save(book)
        allBooks = bookDao.findAll()

        then:
        book.id == 11
        allBooks.size() == old(allBooks.size()) + 1
    }

    @DirtiesContext
    def "can save a collection of books"() {
        given:
        Iterable<Book> allBooks = bookDao.findAll()

        and:
        def newBooks = [['War and Peace', 5, 1, '1869'], ['The Invisible Man', 4, 5, '1933']].collect {
            new Book(it[0] as String, it[1] as Long, it[2] as Long, it[3] as String)
        }

        when:
        allBooks = bookDao.save(newBooks)

        then:
        allBooks != old(allBooks)
        allBooks.size() == old(allBooks.size()) + newBooks.size()
    }

    @DirtiesContext
    def "can delete a book"() {
        given:
        def bookId = 3
        Book book = bookDao.findById(bookId)
        assert book != null

        when:
        bookDao.delete(book)

        then:
        bookDao.findById(bookId) == null
    }

    def "can find books by author"() {
        given:
        def leo = 'leo'
        def emptyTest = 'test'

        when:
        Iterable<Book> leoBooks = bookDao.findByAuthor(leo)
        Iterable<Book> emptyBooks = bookDao.findByAuthor(emptyTest)

        then:
        leoBooks.size() > 1
        emptyBooks.isEmpty()
    }

    def "can find books by genre, case insensitive"() {
        given:
        def bio = 'biography'
        def fantasy = 'fant'
        def emptyTest = 'test'

        when:
        Iterable<Book> bios = bookDao.findByGenre(bio)
        Iterable<Book> fantasies = bookDao.findByGenre(fantasy)
        Iterable<Book> emptyBooks = bookDao.findByGenre(emptyTest)

        then:
        bios.size() == 3
        fantasies.size() == 1
        emptyBooks.isEmpty()
    }

    def "can find books with details on author and genre"() {
        given:
        def books = bookDao.findAllWithDetails()

        expect:
        with(books.first()) {
            it[0] == 1
            it[1] == 'Alice in Wonderland'
            it[2] == 'Lewis Carrol'
            it[3] == 'Fantasy'
            it[4] == '1865'
        }
        books.size() == 10
    }

    @DirtiesContext
    def "can add a comment to a book"() {
        given:
        def bookId = 1
        def text = 'The best book ever'
        Comment comment = new Comment(text)
        Book book = bookDao.findById(bookId)

        when:
        book.addComment(comment)
        book = bookDao.save(book)

        then:
        book.comments.last().commentary == text
        book.getComments().size() == old(book.getComments().size()) + 1
    }

    @DirtiesContext
    def "can delete a comment from a book"() {
        given:
        def bookId = 2
        def commentId = 3
        def comment = commentDao.findById(commentId)
        def book = bookDao.findById(bookId)

        when:
        book.removeComment(comment)
        book = bookDao.save(book)

        then:
        book.comments.size() == old(book.comments.size()) - 1
    }

    void cleanup() {
        bookDao = null
        commentDao = null
    }
}
