package com.tochka.testtask.scheduler;

import com.tochka.testtask.domain.Article;
import com.tochka.testtask.domain.ParsingRule;
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

    @Scheduled(fixedRate=5*1000*60)
    private void doTheJob() {
        newsResourceRepository.findAll().forEach(newsResource -> {
            final ParsingRule parsingRule = newsResource.getParsingRule();
            final String url = newsResource.getUrl();
            final Document document;
            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                logger.debug(e);
                return;
            }
            final List<Article> entries = parsingRule.parse(document);
            entries.forEach(articleService::save);

        });
    }
}
