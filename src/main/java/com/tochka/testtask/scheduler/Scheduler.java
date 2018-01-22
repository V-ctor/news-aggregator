package com.tochka.testtask.scheduler;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.tochka.testtask.domain.Article;
import com.tochka.testtask.domain.ParsingRule;
import com.tochka.testtask.domain.ResourceType;
import com.tochka.testtask.repositories.NewsResourceRepository;
import com.tochka.testtask.service.ArticleService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class Scheduler {
    private final Logger logger = LogManager.getLogger(Scheduler.class);
    private NewsResourceRepository newsResourceRepository;
    private ArticleService articleService;

    @Autowired
    public Scheduler(NewsResourceRepository newsResourceRepository, ArticleService articleService) {
        this.newsResourceRepository = newsResourceRepository;
        this.articleService = articleService;
    }

//    @Scheduled(fixedRate = 5 * 1000 * 60)
        @Scheduled(fixedRate = 5 * 1000)
    public void doTheJob() {
        newsResourceRepository.findAll().forEach(newsResource -> {
            List<Article> entries;
            final String url = newsResource.getUrl();
            ResourceType resourceType = newsResource.getResourceType();
            if (resourceType.equals(ResourceType.RSS)) {
                entries = getListOfNewsFromRss(url);
            } else {
                final ParsingRule parsingRule = newsResource.getParsingRule();
                final Document document;
                try {
                    document = Jsoup.connect(url).get();
                } catch (IOException e) {
                    logger.debug(e);
                    return;
                }
                entries = parsingRule.parse(document);
            }
            entries.forEach(articleService::save);

        });
    }

    private List<Article> getListOfNewsFromRss(String url) {
        final List<Article> resultEntries = new ArrayList<>();
        SyndFeed feed = null;
        try {
            feed = new SyndFeedInput().build(new XmlReader(new URL(url)));
        } catch (FeedException | IOException e) {
            logger.debug(e);
        }
        if (feed != null) {
            feed.getEntries().forEach((SyndEntry entries) -> {
                Article article = new Article()
                    .setUrl(entries.getLink())
                    .setTitle(entries.getTitle())
                    .setAuthor(entries.getAuthor())
                    .setDate(entries.getPublishedDate())
                    .setText(entries.getDescription().getValue());
                resultEntries.add(article);
            });
        }
        return resultEntries;
    }
}
