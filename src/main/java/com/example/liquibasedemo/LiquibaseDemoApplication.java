package com.example.liquibasedemo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

@SpringBootApplication
@RequiredArgsConstructor
public class LiquibaseDemoApplication {

    private final ArticleRepository articleRepository;

    public static void main(String[] args) {
        SpringApplication.run(LiquibaseDemoApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(JdbcTemplate template) {
        return args -> articleRepository.getArticles(template);
    }

}

