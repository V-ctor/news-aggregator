package com.tochka.testtask.controller;

import com.tochka.testtask.domain.NewsResource;
import com.tochka.testtask.repositories.NewsResourceRepository;
import org.mockito.BDDMockito;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Test
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class NewsResourceControllerTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    @MockBean
    private NewsResourceRepository newsResourceRepository;

    public void testAddNewsResource() throws Exception {
        final String parseRuleAsJson = "{\n"
            + "  \"itemDomValue\": null,\n"
            + "  \"titleDomValue\": \".e1-article__tit\",\n"
            + "  \"articleUrlDomValue\": null,\n"
            + "  \"articleUrlDomAttribute\": null,\n"
            + "  \"articleTextDomValue\": null\n"
            + "}";

        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
            "text/plain", parseRuleAsJson.getBytes());
        this.mvc.perform(fileUpload("/news/addResource").file(multipartFile)
            .param("url", "www.aaa.bbb")
            .param("resourceTypeId", "0"))
            .andExpect(status().isFound())
            .andExpect(header().string("Location", "/"));

        BDDMockito.then(this.newsResourceRepository).should().save(Matchers.any(NewsResource.class));
    }
}
