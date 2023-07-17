-- liquibase formatted sql

-- changeset o.baibula:1 contextFilter:prod
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- preconditions-sql-check expectedResult:0 select count(1) from pg_tables where tablename = 'articles'
CREATE TABLE IF NOT EXISTS articles(
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    published TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)

-- changeset o.baibula:2 contextFilter:prod
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- preconditions-sql-check expectedResult:0 select count(1) from pg_tables where tablename = 'comments'
CREATE TABLE IF NOT EXISTS comments(
    id BIGSERIAL PRIMARY KEY,
    comment VARCHAR(255),
    article_id BIGINT NOT NULL REFERENCES articles(id) ON DELETE CASCADE
)

-- changeset o.baibula:3 contextFilter:test
INSERT INTO articles (title)
VALUES('Hello Thier!'), ('Good Bye Thier!');

-- changeset o.baibula:4 contextFilter:test
INSERT INTO comments(comment, article_id)
VALUES('Nice!', 1), ('SHit!', 1), ('Good Shit', 2);