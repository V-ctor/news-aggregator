package com.tochka.testtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tochka.testtask.domain.NewsResource;
import com.tochka.testtask.domain.ParsingRule;
import com.tochka.testtask.domain.Title;
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

@Controller
@RequestMapping("/news")
public class NewsResourceController {
    private NewsResourceRepository newsResourceRepository;
    private TitleService titleService;
    private Logger logger = LogManager.getLogger(NewsResourceController.class);

    @Autowired
    public NewsResourceController(NewsResourceRepository newsResourceRepository, ParsingRuleRepository parsingRuleRepository,
        TitleService titleService) {
        this.newsResourceRepository = newsResourceRepository;
        this.titleService = titleService;
    }

    @PostMapping("/addResource")
    public String addNewsResource(@RequestParam("file") MultipartFile parseRuleFile, @RequestParam String url) throws IOException {

        final ObjectMapper objectMapper = new ObjectMapper();
        final ParsingRule parsingRule = objectMapper.readValue(parseRuleFile.getBytes(), ParsingRule.class);
        final NewsResource newsResource = new NewsResource(url).setParsingRule(parsingRule);
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
}
