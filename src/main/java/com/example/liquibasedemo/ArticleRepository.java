package com.example.liquibasedemo;

import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ArticleRepository {
    public void getArticles(JdbcTemplate template) {

        var sql = """
                    select  c.id as comment_id,
                            a.id as article_id,
                            c.comment as comment,
                            a.published as published,
                            a.title as title
                    from    articles a
                        left join
                            comments c
                        on a.id = c.article_id
                    """;
        var list = template.query(sql, new ArticleRepository.ArticleRowMapper());
        new HashSet<>(list)
                .forEach(System.out::println);

    }

    record Article(Long id, String title, Date published, List<Comment> comments) {

    }

    static class ArticleRowMapper implements RowMapper<Article> {

        private final Map<Long, Article> articles =
                new ConcurrentHashMap<>();

        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {

            var articleId = rs.getLong("article_id");
            var commentId = rs.getLong(("comment_id"));
            var article = this.articles.computeIfAbsent(articleId,
                    aid -> build(aid, rs));
            if (commentId > 0)
                article.comments().add(new Comment(commentId, rs.getString("comment")));
            return article;
        }

        @SneakyThrows
        private Article build(Long aid, ResultSet rs) {
            return new Article(aid, rs.getString("title"),
                    rs.getDate("published"),
                    new ArrayList<>());
        }
    }

     record Comment(Long id, String comment) {
    }
}
