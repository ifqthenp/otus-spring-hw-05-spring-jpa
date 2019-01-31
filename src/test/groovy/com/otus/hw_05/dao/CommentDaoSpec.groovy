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
@Import([CommentDaoJpaImpl, BookDaoJpaImpl])
class CommentDaoSpec extends Specification {

    @Subject
    @Autowired
    CommentDao commentDao

    @Autowired
    BookDao bookDao

    void setup() {
        assert bookDao != null
        assert commentDao != null
    }

    def "can count comments in the table"() {
        expect:
        commentDao.count() == 5
    }

    def "can find a comment by its id"() {
        given:
        def commentId = 3

        when:
        Comment comment = commentDao.findById(commentId)

        then:
        comment != null
        comment.getId() == commentId
    }

    def "can find all comments in the database"() {
        given:
        def comments = commentDao.findAll()

        expect:
        comments.size() == 5
    }

    def "returns null if comment is not found by id"() {
        expect:
        commentDao.findById(0) == null
    }

    def "can find comments by book id"() {
        given:
        def one = 1
        def two = 2

        and:
        def commentsForOne = commentDao.findByBookId(one)
        def commentsForTwo = commentDao.findByBookId(two)

        expect:
        commentsForOne.size() == 1
        commentsForTwo.size() == 2
    }

    def "can find a comment by text search, case insensitive"() {
        given:
        def text = 'Good'

        and:
        def goodComments = commentDao.findByText(text)

        expect:
        goodComments.size() == 2
    }

    @DirtiesContext
    def "can save a comment"() {
        given:
        def com1 = 'outstanding'
        def com2 = 'marvellous'
        Book alice = bookDao.findById(1)
        Comment comment1 = new Comment(com1, alice)
        Comment comment2 = new Comment(com2, alice)

        when:
        comment1 = commentDao.save(comment1)
        comment2 = commentDao.save(comment2)

        then:
        comment1.commentary == com1
        comment2.commentary == com2
        commentDao.findAll().size() == 7
    }

    @DirtiesContext
    def "can save a collection of comments"() {
        given:
        Book someBook = bookDao.findById(4)
        Iterable<Comment> comments = commentDao.findAll()

        and:
        def newComments = ['very nice!', 'beautiful story'].collect {
            new Comment(it, someBook)
        }

        when:
        comments = commentDao.save(newComments)

        then:
        comments != old(comments)
        comments.size() == old(comments.size()) + newComments.size()
    }

    @DirtiesContext
    def "can delete a comment"() {
        given:
        def bookId = 1
        Comment comment = commentDao.findById(bookId)

        when:
        commentDao.delete(comment)

        and:
        comment = commentDao.findById(bookId)

        then:
        comment == null
    }

    void cleanup() {
        bookDao = null
        commentDao = null
    }
}
