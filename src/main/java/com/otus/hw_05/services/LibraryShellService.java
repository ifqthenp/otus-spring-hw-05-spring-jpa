package com.otus.hw_05.services;

import com.otus.hw_05.dao.AuthorDao;
import com.otus.hw_05.dao.BookDao;
import com.otus.hw_05.dao.CommentDao;
import com.otus.hw_05.dao.GenreDao;
import com.otus.hw_05.domain.Genre;
import org.h2.tools.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Arrays;

@Service
@ShellComponent
public class LibraryShellService {

    private final GenreDao genreDao;
    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final CommentDao commentDao;

    @Autowired
    public LibraryShellService(final GenreDao genreDao,
                               final AuthorDao authorDao,
                               final BookDao bookDao,
                               final CommentDao commentDao) {
        this.genreDao = genreDao;
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.commentDao = commentDao;
    }

    @ShellMethod("Display all available genres.")
    public void displayGenres() {
        genreDao.findAll().forEach(g -> System.out.println(g.toString()));
    }

    @ShellMethod("Find genre by id.")
    public void findGenreById(final long id) {
        System.out.println(genreDao.findById(id));
    }

    @ShellMethod("Find genre by name.")
    public void findGenreByName(final String genre) {
        System.out.println(genreDao.findByGenre(genre));
    }

    @ShellMethod("Delete a genre by name.")
    public void deleteGenreByName(final String name) {
        final Genre genre = genreDao.findByGenre(name);
        if (genre != null) {
            genreDao.delete(genre);
            System.out.println(name + " genre successfully deleted!");
        } else {
            System.out.println(name + " genre is not found");
        }
    }

    @ShellMethod("Save a genre.")
    public void saveGenre(final Genre genre) {
        genreDao.save(genre);
    }

    @ShellMethod("Display all available authors.")
    public void displayAuthors() {
        authorDao.findAll().forEach(g -> System.out.println(g.toString()));
    }

    @ShellMethod("Display all available books.")
    public void displayBooksWithDetails() {
        bookDao.findAllWithDetails().forEach(g -> System.out.println(Arrays.toString(g)));
    }

    @ShellMethod("Runs database console")
    public void dbConsole() throws SQLException {
        Console.main();
    }

    @ShellMethod("Display all comments")
    public void displayAllComments() {
        commentDao.findAll().forEach(comment -> System.out.println(comment.toString()));
    }

    @ShellMethod("Display comment for book id")
    public void displayCommentForBookId(final long bookId) {
        System.out.println(commentDao.findByBookId(bookId));
    }

}
