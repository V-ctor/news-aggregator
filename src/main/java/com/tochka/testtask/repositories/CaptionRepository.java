package com.tochka.testtask.repositories;

import com.tochka.testtask.domain.Article;
import com.tochka.testtask.domain.Caption;
import org.springframework.data.repository.CrudRepository;

interface CaptionRepository extends CrudRepository<Caption, Long> {
    Caption findByArticle(Article article);
    void deleteByArticle(Article article);
}
