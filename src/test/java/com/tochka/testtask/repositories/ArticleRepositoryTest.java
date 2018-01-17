package com.tochka.testtask.repositories;

import com.tochka.testtask.domain.Article;
import com.tochka.testtask.domain.Caption;
import com.tochka.testtask.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@Test
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ArticleRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleService articleService;

    public void saveTest() {
        Article article = new Article("www", "news 1");
        Caption caption = new Caption("Caption of news 1");
        article.setCaption(caption);
        caption.setArticle(article);

        articleRepository.save(article);
        assertTrue(article.getId() != 0);
    }

    public void doNotAllowToSaveDuplicates() {
        for (int i = 0; i < 3; i++) {
            Article article = new Article("www", "news 1");
            Caption caption = new Caption("Caption of news 1");
            article.setCaption(caption);
            caption.setArticle(article);

            articleService.save(article);
        }
        assertTrue(articleRepository.count()==1);
    }
}
