package com.otus.hw_05.domain

import spock.lang.Specification

class CommentSpec extends Specification {

    def "no-args constructor sets attributes to default values"() {
        given:
        Comment comment = new Comment()
        assert comment != null

        expect:
        comment.getId() == 0
        comment.getCommentary() == null
    }

    def "all-args constructor sets attributes to the specified ones"() {
        given:
        def text = "The best book ever!"
        def book = new Book()
        Comment comment = new Comment(3, text, book)
        assert comment != null

        expect:
        comment.getId() == 3
        comment.getCommentary() == text
        comment.getBook() == book
    }

    def "two-args constructor sets attributes to correct values"() {
        given:
        def text = "The best book ever!"
        Comment comment = new Comment(text, new Book())
        assert comment != null

        expect:
        comment.getId() == 0
        comment.getCommentary() == text
    }

    def "all getters and setters work correctly"() {
        given:
        def text = "The best book ever!"
        def book = new Book()
        Comment comment = new Comment(2, text, book)
        assert comment != null

        when:
        comment.setId(3)
        comment.setCommentary("Not the best!")

        then:
        comment.getId() != old(comment.getId())
        comment.getCommentary() != old(comment.getCommentary())
    }
}
