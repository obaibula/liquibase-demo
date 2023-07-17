package com.example.liquibasedemo;


import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.sql.SQLException;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(@Autowired DataSource data) {
        try (Liquibase liquibase = new Liquibase("db/changelog/changelog.sql",
                new ClassLoaderResourceAccessor(),
                DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(data.getConnection())))) {
            liquibase.dropAll();
            liquibase.update("prod, test");
        } catch (LiquibaseException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test(){
        String sql = """
                INSERT INTO articles (title)
                VALUES('Created article2!')
                """;
        jdbcTemplate.update(sql);
        articleRepository.getArticles(jdbcTemplate);
    }

    @Test
    void test2(){
        String sql = """
                INSERT INTO articles (title)
                VALUES('Created article3!')
                """;
        jdbcTemplate.update(sql);
        articleRepository.getArticles(jdbcTemplate);
    }
    @Test
    void test3(){
        String sql = """
                INSERT INTO articles (title)
                VALUES('Created article4!')
                """;
        jdbcTemplate.update(sql);
        articleRepository.getArticles(jdbcTemplate);
    }

}