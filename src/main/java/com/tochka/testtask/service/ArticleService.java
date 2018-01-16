package com.tochka.testtask.service;

import com.tochka.testtask.domain.Article;
import com.tochka.testtask.repositories.ArticleRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class ArticleService {
    private final Logger logger = LogManager.getLogger(ArticleService.class);

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article save(Article article) {
        Article savedArticle = null;
        try {
            if (null == articleRepository.findByUrl(article.getUrl())) {
                savedArticle = articleRepository.save(article);
            }
        } catch (DataIntegrityViolationException e) {
            logger.debug(e.getMessage());
        }
        return savedArticle;
    }
}
