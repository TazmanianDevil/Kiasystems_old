package ru.kiasystems.model.rest_test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.web.client.RestTemplate;
import ru.kiasystems.model.entity.Theme;
import ru.kiasystems.model.restful.entities.Themes;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

public class ThemeRestServiceTest {
    private static final String URL_GET_ALL_THEMES =
            "http://localhost:8080/Kiasystems/restful/theme/listdata";
    private static final String URL_GET_THEME_BY_ID =
            "http://localhost:8080/Kiasystems/restful/theme/{id}";
    private static final String URL_CREATE_THEME =
            "http://localhost:8080/Kiasystems/restful/theme/";
    private static final String URL_UPDATE_THEME =
            "http://localhost:8080/Kiasystems/restful/theme/{id}";
    private static final String URL_DELETE_THEME =
            "http://localhost:8080/Kiasystems/restful/theme/{id}";
    private GenericXmlApplicationContext context;
    private RestTemplate restTemplate;
    @Before
    public void setUp() {
        context = new GenericXmlApplicationContext("classpath:META-INF/rest-client-app-context.xml");
        restTemplate = context.getBean("restTemplate", RestTemplate.class);
    }

    @Test
    public void testThemeListData() {
        System.out.println("<----------GET ALL THEMES ------------>");
        Themes themes = restTemplate.getForObject(URL_GET_ALL_THEMES, Themes.class);
        assertNotNull(themes);
        System.out.println(themes);
    }

    @Test
    public void testThemeFindById() {
        System.out.println("<-------------- GET THEME WITH ID 1 ------------>");
        Theme theme = restTemplate.getForObject(URL_GET_THEME_BY_ID, Theme.class, 1L);
        System.out.println(theme);
    }

    @Test
    public void testSaveUpdateAndDelete() {
        System.out.println("<---------------INSERT, UPDATE AND DELETE NEW THEME --------->");
        Theme theme = new Theme("Test theme", new Date(), new Date());
        theme = restTemplate.postForObject(URL_CREATE_THEME, theme, Theme.class);
        System.out.println("Theme was successfully posted: " + theme);

        theme.setTitle("New test theme");
        restTemplate.put(URL_UPDATE_THEME, theme, theme.getId());
        Themes themes = restTemplate.getForObject(URL_GET_ALL_THEMES, Themes.class);
        System.out.println(themes);

        restTemplate.delete(URL_DELETE_THEME, theme.getId());
        System.out.println(restTemplate.getForObject(URL_GET_ALL_THEMES, Themes.class));
    }
}
