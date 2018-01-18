package com.tochka.testtask.repositories;

import com.tochka.testtask.domain.Article;
import com.tochka.testtask.domain.Title;
import com.tochka.testtask.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ArticleRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    TitleRepository titleRepository;

    @Autowired
    ArticleService articleService;

    public void saveTest() {
        final Article article = new Article("www", "news 1");
        article.setTitle("Title of news 1");

        articleRepository.save(article);
        assertTrue(article.getId() != 0);
    }

    public void doNotAllowToSaveDuplicates() {
        for (int i = 0; i < 3; i++) {
            Article article = new Article("www", "news 1");
            Title title = new Title("Title of news 1");
            article.setTitle("Title of news 1");

            articleService.save(article);
        }
        assertTrue(articleRepository.count() == 1);
    }

    public void saveArticleAndGetTileTest() {
        final String titleOfNews = "Title of news ";
        final Article article = new Article("www", "news 1");
        article.setTitle(titleOfNews);

        articleRepository.save(article);
        assertEquals(titleRepository.count(), 1);
        assertEquals(titleRepository.findAll().get(0).getTitle(), titleOfNews);
    }


}
