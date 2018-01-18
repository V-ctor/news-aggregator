package com.tochka.testtask.domain;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.IObjectFactory;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.testng.AssertJUnit.assertEquals;

@Test
@PrepareForTest(Jsoup.class)
public class ParsingRuleTest {

    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new PowerMockObjectFactory();
    }

    public void testElementToTitle() throws Exception {
        final String titleText = "Участники \"Уральских пельменей\" потребовали с бывшего директора 28 миллионов рублей";
        final Element element = new Element("h3").addClass("e1-article__tit").text(titleText);
        final ParsingRule parsingRule = new ParsingRule().setTitleDomValue(".e1-article__tit");

        final Method method = ParsingRule.class.getDeclaredMethod("getTitleFromElement", Element.class);
        method.setAccessible(true);
        final String title = (String) method.invoke(parsingRule, element);

        assertEquals(title, titleText);
    }

    public void testElementToArticle() throws Exception {
        final String url = "www.aaa.bb";

        final Element itemElement = Jsoup.parse("<article class=\"e1-article\">\n"
            + "    <a title=\"Участники &quot;Уральских пельменей&quot; потребовали с бывшего директора 28 миллионов рублей\" class=\"e1-article__link e1-article__link_inline\" href="
            + url + ">\n"
            + "        <figure class=\"e1-article__photo\">\n"
            + "            <img class=\"e1-article__img\" src=\"https://newsapi.ngs.ru/image/www.e1.ru/news/static/e9e305f0da2a26e252beb20d160d2717_2000_2000_70_50_c.jpg\" alt=\"Участники &quot;Уральских пельменей&quot; потребовали с бывшего директора 28 миллионов рублей\">\n"
            + "        </figure>\n"
            + "        <div class=\"e1-article__tags\">\n"
            + "                            </div>\n"
            + "        <header class=\"e1-article__header\">\n"
            + "            <h3 class=\"e1-article__tit\">Участники \"Уральских пельменей\" потребовали с бывшего директора 28 миллионов рублей</h3>\n"
            + "        </header>\n"
            + "        <p class=\"e1-article__text\">Разбираться в скандальном деле будет свердловский арбитраж.</p>\n"
            + "    </a>\n"
            + "</article>");
        final String innerHtml =
            "<div class=\"article-text\">"
                + "    <figure class=\"news-article__figure _left _first-image \">"
                + "        <a class=\"news-article__figure-link fancybox\" itemprop=\"image\" itemscope=\"\" itemtype=\"https://schema.org/ImageObject\" href=\"https://www.e1.ru/news/images/resize_300_204/new1/485/563/images/3232232323.jpg?_900\" title=\"&quot;Уральские пельмени&quot; судятся со своим бывшим директором\" rel=\"lightboxatomium\">    <img class=\"news-article__image\" itemprop=\"image url\" src=\"https://www.e1.ru/news/images/resize_300_204/new1/485/563/images/3232232323.jpg?_700\" alt=\"&quot;Уральские пельмени&quot; судятся со своим бывшим директором\" data-author=\"\" title=\"&quot;Уральские пельмени&quot; судятся со своим бывшим директором\">"
                + "         <meta itemprop=\"width\" content=\"55%\"><meta itemprop=\"height\" content=\"auto\">"
                + "        </a>"
                + "        <figcaption class=\"news-article__figure-text\" style=\"max-width:700px\"> \"Уральские пельмени\" судятся со своим бывшим директором"
                + "        </figcaption>"
                + "    </figure>"
                + "    <p>Внушительную сумму требуют участники \"Уральских пельменей\" со своего бывшего директора Сергея Нетиевского. Речь идёт о 28,3 миллиона&nbsp;рублей. Иск подан в Арбитражный суд Свердловской области Александром Поповым, Сергеем Калугиным, Вячеславом Мясниковым, Максимом Ярицой и Дмитрием Соколовым.</p>\n"
                + "</div>";

        final Document articleDocument = Jsoup.parse(
            "<div class=\"e1-news-item__content e1-news-item-content\">"
                + " <div class=\"news js-mediator-article\">"
                + "  <div id=\"newscontent\">"
                + innerHtml
                + "      <p class=\"news-media-author\">Текст: Сергей ПАНИН<br>Фото: \"Уральские пельмени\" / \"ВКонтакте\", Сергей НЕТИЕВСКИЙ / facebook.com</p>"
                + "  </div>"
                + " </div>"
                + "</div>");

        mockStatic(Jsoup.class);
        final Connection connection = Mockito.mock(Connection.class);
        PowerMockito.when(Jsoup.connect(Mockito.eq(url))).
            thenReturn(connection);

        Mockito.when(connection.get()).thenReturn(articleDocument);

        final ParsingRule parsingRule = new ParsingRule()
            .setTitleDomValue(".e1-article__tit")
            .setArticleUrlDomValue("a.e1-article__link")
            .setArticleUrlDomAttribute("href")
            .setArticleTextDomValue(".article-text");

        final Method method = ParsingRule.class.getDeclaredMethod("elementToArticle", Element.class);
        method.setAccessible(true);
        final Article article = (Article) method.invoke(parsingRule, itemElement);

        assertEquals(Jsoup.parse(article.getText()), Jsoup.parse(innerHtml));
    }
}
