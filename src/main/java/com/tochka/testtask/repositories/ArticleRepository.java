package com.tochka.testtask.repositories;

import com.tochka.testtask.domain.Article;
import com.tochka.testtask.domain.Caption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findByCaption(Caption caption);
    Article findByUrl(String email);
    void deleteByCaption(Caption caption);
    void deleteByUrl(String email);
}
