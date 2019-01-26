DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS genres;

CREATE TABLE authors(
    id BIGINT PRIMARY KEY AUTO_INCREMENT
    , first_name VARCHAR(50) NOT NULL
    , last_name VARCHAR(50) NOT NULL
);

CREATE TABLE genres(
    id BIGINT PRIMARY KEY AUTO_INCREMENT
    , genre VARCHAR(50) NOT NULL
);

CREATE TABLE books(
    id BIGINT PRIMARY KEY AUTO_INCREMENT
    , title VARCHAR(255)
    , fk_author_id BIGINT
    , fk_genre_id BIGINT
    , written VARCHAR(4)
    , FOREIGN KEY (fk_author_id) REFERENCES authors(id) ON DELETE CASCADE
    , FOREIGN KEY (fk_genre_id) REFERENCES genres(id) ON DELETE CASCADE
);
