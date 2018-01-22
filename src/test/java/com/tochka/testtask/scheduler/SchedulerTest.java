package com.tochka.testtask.scheduler;

import com.tochka.testtask.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.testng.Assert.assertTrue;

@Test
@SpringBootTest
public class SchedulerTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    Scheduler scheduler;

    public void getListOfNewsFromRssTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = Scheduler.class.getDeclaredMethod("getListOfNewsFromRss", String.class);
        method.setAccessible(true);
        final List<Article> articles = (List<Article>) method.invoke(scheduler, "https://stackoverflow.com/feeds/tag?tagnames=rome");
        assertTrue(articles.size() != 0);

    }
}
