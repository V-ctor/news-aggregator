package com.tochka.testtask.repositories;

import com.tochka.testtask.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findByTitle(String title);
    Article findByUrl(String url);
    void deleteByTitle(String title);
    void deleteByUrl(String url);
}
