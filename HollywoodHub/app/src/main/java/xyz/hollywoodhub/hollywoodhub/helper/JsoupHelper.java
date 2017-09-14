package xyz.hollywoodhub.hollywoodhub.helper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by rpandey.ppe on 23/07/17.
 */

public class JsoupHelper {

    public static Document parseHTML(String html) {
        return Jsoup.parse(html);
    }

    public static Elements byClass(Document document, String className) {
        return document.getElementsByClass(className);
    }

    public static Elements byClass(Element element, String className) {
        return element.getElementsByClass(className);
    }

    public static Elements byTag(Document document, String tagName) {
        return document.getElementsByTag(tagName);
    }

    public static Elements byTag(Element element, String tagName) {
        return element.getElementsByTag(tagName);
    }

    public static Element byId(Document document, String idName) {
        return document.getElementById(idName);
    }

    public static Element byId(Element element, String idName) {
        return element.getElementById(idName);
    }

    public static boolean hasClass(Element element, String className) {
        return element.getElementsByClass(className).first() != null;
    }
}
