package pl.dkatszer.inz.notes.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import pl.dkatszer.inz.notes.model.SubsectionInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by doka on 2017-12-25.
 * TODO refactor - copy paste
 */
@Service
public class ContentEditService {

    private final static String langRegex = "(::(\\w+)::)";
    private final static String highlightStarting = "<div class=\"highlight\"><code class=\"%s\">";
    private final static String highlightEnding = "</code></div>";
    private final static String notDisplayedClass = "notDisplayed";

    // TODO - remove new lines !!!!!
    public String colorizeIfNeeded(String sectionContent){

        String htmlContent = String.format("<html>%s<html>", sectionContent);
        final Document document = Jsoup.parse(htmlContent);
        document.outputSettings().prettyPrint(false);
        final Elements all = document.select("body").first().children();

        Pattern p = Pattern.compile(langRegex);

        Optional<String> langName = Optional.empty();
        for(Element el: all){
            final Matcher matcher = p.matcher(el.html());
            if(matcher.find()){
                langName = Optional.of(matcher.group(2));
            }
            if(isPre(el) && langName.isPresent()){
                if(!isAlreadyWrapped(el)){
                    wrapContent(langName.get(), el);
                }else if(!getCodeLangWrapper(el).className().contains(langName.get())){ //here is alreadyWrapped
                    getCodeLangWrapper(el).classNames(Set.of(langName.get()));
                }
            }

        }
        return document.select("body").first().html();
    }

    public List<SubsectionInfo> generateToc(String content){
        String htmlContent = String.format("<html>%s<html>", content);
        final Document document = Jsoup.parse(htmlContent);
        document.outputSettings().prettyPrint(false);


        List<SubsectionInfo> toc = new ArrayList<>();
        final Elements allHeaders = document.select("h2,h3");

        int seqNumber = 1;
        for(Element el : allHeaders){
            switch (el.tagName()){
                case "h2":
                    toc.add(createSubsec(el, seqNumber, 0));
                    seqNumber++;
                    break;
                case "h3":
                    toc.add(createSubsec(el, seqNumber, 1));
                    seqNumber++;
                    break;
                default:
                    break;
            }
        }
        return toc;
    }

    public String setMetaVisibility(String content, boolean visible) {
        String htmlContent = String.format("<html>%s<html>", content);
        final Document document = Jsoup.parse(htmlContent);
        final Elements all = document.select("body").first().children();

        Pattern p = Pattern.compile(langRegex);
        for(Element el: all){
            final Matcher matcher = p.matcher(el.html());
            if(matcher.find()){
                if(visible) {
                    el.removeClass(notDisplayedClass);
                }else{
                    el.addClass(notDisplayedClass);
                }
            }
        }

        return document.select("body").first().html();
    }
    public String addScrollSpySections(String content) {
        String htmlContent = String.format("<html>%s<html>", content);
        final Document document = Jsoup.parse(htmlContent);
        document.outputSettings().prettyPrint(false);
        final Elements all = document.select("body").first().children();

        int seqNumber = 1;
        for(Element el : all){
            if(el.tagName().matches("(h2|h3)")){
                if(!isWrappedByScrollSpy(el)){
                    el.wrap("<sn-scroll-spy-section id=\"subsec"+seqNumber+"\" for=\"subsec-content-list\"></sn-scroll-spy-section>");
                }
                seqNumber++;
            }
        }
        return document.select("body").first().html();
    }

    private boolean isWrappedByScrollSpy(Element el) {
        return el.parent().tagName().contains("sn-scroll-spy-section");
    }

    private SubsectionInfo createSubsec(Element el, int seqNumber, int subLevel) {
        final SubsectionInfo subsecInfo = new SubsectionInfo();
        subsecInfo.seqNumber = seqNumber;
        subsecInfo.title = el.text();
        subsecInfo.subLevel = subLevel;
        return subsecInfo;
    }

    private Element getCodeLangWrapper(Element el) {
        return el.child(0).child(0);
    }

    private boolean isAlreadyWrapped(Element el) {
        return containsHighlightWrapper(el) && containsCodeWrapper(el);
    }

    private void wrapContent(String langName, Element el) {
        final String pre = String.format(highlightStarting, langName);
        final String inside = el.html();
        el.html(pre + inside + highlightEnding);
    }

    private boolean isPre(Element el) {
        return el.tagName().contains("pre");
    }

    private boolean containsCodeWrapper(Element pre) {
        final Element highlighWrapper = pre.child(0);
        return highlighWrapper.children().size() == 1 && highlighWrapper.child(0).tagName().contains("code");
    }

    private boolean containsHighlightWrapper(Element pre) {
        return pre.children().size() == 1 && pre.child(0).classNames().contains("highlight");
    }

}
