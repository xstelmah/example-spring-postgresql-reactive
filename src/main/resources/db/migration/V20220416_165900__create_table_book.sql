CREATE TABLE t_book
(
    id     BIGINT NOT NULL DEFAULT nextval('s_book') PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL
);