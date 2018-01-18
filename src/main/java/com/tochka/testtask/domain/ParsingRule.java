package com.tochka.testtask.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @Accessors(chain = true)
public class ParsingRule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private long id;

    @OneToOne(cascade = {CascadeType.ALL})
    private NewsResource newsResource;

    private String itemDomValue;
    private String titleDomValue;
    private String articleUrlDomValue;
    private String articleUrlDomAttribute;
    private String articleTextDomValue;

    public List<Article> parse(Document document) {
        final String cssSelector = itemDomValue;
        final Elements articleElements = document.select(cssSelector);
        ArrayList<Article> articles = new ArrayList<>(articleElements.size());
        articleElements.forEach(element -> {
            try {
                articles.add(elementToArticle(element));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return articles;
    }

    private Article elementToArticle(Element element) throws IOException {
        final String title = getTitleFromElement(element);
        final Article article = getArticleFromElement(element);
        article.setTitle(title);
        return article;
    }

    private Article getArticleFromElement(Element element) throws IOException {
        final Element urlElement = element.selectFirst(articleUrlDomValue);
        final String url = urlElement.attr(articleUrlDomAttribute);
        final Document document = Jsoup.connect(url).get();
        final Element textElement = document.selectFirst(articleTextDomValue);
        return new Article(url, textElement.outerHtml());
    }

    private String getTitleFromElement(Element element) {
        return element.selectFirst(titleDomValue).text();
    }
}
