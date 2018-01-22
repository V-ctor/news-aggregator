package com.tochka.testtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tochka.testtask.domain.NewsResource;
import com.tochka.testtask.domain.ParsingRule;
import com.tochka.testtask.domain.ResourceType;
import com.tochka.testtask.domain.Title;
import com.tochka.testtask.repositories.ArticleRepository;
import com.tochka.testtask.repositories.NewsResourceRepository;
import com.tochka.testtask.repositories.ParsingRuleRepository;
import com.tochka.testtask.service.TitleService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsResourceController {
    private NewsResourceRepository newsResourceRepository;
    private ArticleRepository articleRepository;
    private TitleService titleService;
    private Logger logger = LogManager.getLogger(NewsResourceController.class);

    @Autowired
    public NewsResourceController(NewsResourceRepository newsResourceRepository, ParsingRuleRepository parsingRuleRepository,
        ArticleRepository articleRepository, TitleService titleService) {
        this.newsResourceRepository = newsResourceRepository;
        this.articleRepository = articleRepository;
        this.titleService = titleService;
    }

    @PostMapping("/addResource")
    public String addNewsResource(@RequestParam(name = "file", required = false) MultipartFile parseRuleFile,
        @RequestParam String url, @RequestParam int resourceTypeId) throws IOException {

        final ResourceType resourceType = ResourceType.fromId(resourceTypeId);
        ParsingRule parsingRule = null;
        if (parseRuleFile.getBytes().length != 0) {
            final ObjectMapper objectMapper = new ObjectMapper();
            parsingRule = objectMapper.readValue(parseRuleFile.getBytes(), ParsingRule.class);
        }
        final NewsResource newsResource = new NewsResource(url).setParsingRule(parsingRule);
        newsResource.setResourceType(resourceType);
        if (parsingRule != null)
            parsingRule.setNewsResource(newsResource);

        newsResourceRepository.save(newsResource);
        return "redirect:/";
    }

    @PostMapping("/findTitles")
    @ResponseBody
    public Page<Title> find(@RequestParam("title") String title, @RequestParam int start, @RequestParam int length,
        @RequestParam String sortField, @RequestParam String sortDir) {
        return titleService.findByAnyTitleContaining(title, start, length, sortField, sortDir);
    }

    @PostMapping("/getArticle")
    @ResponseBody
    public String find(@RequestParam long id) {
        return articleRepository.getOne(id).getText();
    }

    @PostMapping("/deleteAllArticles")
    public String deleteAllArticles() {
        articleRepository.deleteAll();
        return "redirect:/";
    }

    @PostMapping("/listOfResources")
    @ResponseBody
    public List<NewsResource> getListOfResources() {
        return newsResourceRepository.findAll();
    }

    @PostMapping("/deleteNewsResource")
    public String deleteNewsResource(@RequestParam long id) {
        newsResourceRepository.delete(id);
        return "redirect:/";
    }

}
