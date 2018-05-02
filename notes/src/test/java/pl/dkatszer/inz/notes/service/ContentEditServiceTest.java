package pl.dkatszer.inz.notes.service;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by doka on 2017-12-26.
 */
public class ContentEditServiceTest {
    public static ContentEditService hs = new ContentEditService();

    @Test
    public void colorizeIfNeededOneCode() {
        String test = "<p>test</p>\n" +
                "<p>::java::</p>\n" +
                "<pre>public static void main(String[] args)</pre>";
        hs.colorizeIfNeeded(test);
        //todo - add assertion
    }

    @Test
    public void colorizeIfNeededTwoCode() {
        String test = "<p>test</p>\n" +
                "<p>::java::</p>\n" +
                "<pre>public static void main(String[] args)</pre>" +
                "<p>Normal Text</p>" +
                "<p>::javascript::</p>\n" +
                "<pre><script>\n" +
                "alert(\"Hello! I am an alert box!\");\n" +
                "</script></pre>" +
                "<p>Normal text</p>";
        hs.colorizeIfNeeded(test);
        //todo - add assertion
    }

    @Test
    public void colorizeAgain(){
        String test = "<p>test</p>\n" +
                "<p>::java::</p>\n" +
                "<pre>public static void main(String[] args)</pre>";
        final String afterFirstColorize = hs.colorizeIfNeeded(test);
        final String afterSecondColorize = hs.colorizeIfNeeded(afterFirstColorize);
        assertEquals(afterFirstColorize,afterSecondColorize);
    }
    @Test
    public void colorizeAlreadyColorized(){
        String test = "<p>test</p>\n" +
                "<p>::typescript::</p>\n" +
                "<pre><div class=\"highlight\">" +
                "  <code class=\"java\">public static void main(String[] args)</code>"+
                " </div></pre>";
        final String afterFirstColorize = hs.colorizeIfNeeded(test);
//        assertFalse(afterFirstColorize.equals(test));
        assertEquals(test,afterFirstColorize);
    }

    @Test
    public void setInvisibleMetaData(){
        String test = "<p>test</p>\n" +
                "<p>::java::</p>\n" +
                "<pre>public static void main(String[] args)</pre>";

        final String result = hs.setMetaVisibility(test, false);

        String test2 = "<p>test</p>\n" +
                "<p class=\"notDisplayed\">::java::</p>\n" +
                "<pre>public static void main(String[] args)</pre>";

        final String resul2 = hs.setMetaVisibility(test, false);
    }

    @Test
    public void setVisibleMetaData(){
        String test = "<p>test</p>\n" +
                "<p class=\"notDisplayed\">::java::</p>\n" +
                "<pre>public static void main(String[] args)</pre>";

        final String result = hs.setMetaVisibility(test, true);

        String test2 = "<p>test</p>\n" +
                "<p>::java::</p>\n" +
                "<pre>public static void main(String[] args)</pre>";

        final String result2 = hs.setMetaVisibility(test, true);
    }
}