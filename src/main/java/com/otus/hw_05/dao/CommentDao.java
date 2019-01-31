package com.otus.hw_05.dao;

import com.otus.hw_05.domain.Comment;

public interface CommentDao extends CommonRepository<Comment> {

    Iterable<Comment> findByText(String text);

    Iterable<Comment> findByBookId(long bookId);

}
