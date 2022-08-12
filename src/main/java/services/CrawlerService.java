package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import utils.ListUtil;

/**
 *
 * @author SRamos
 */
@ApplicationScoped
public class CrawlerService {

    @ConfigProperty(name = "imdb-base-url")
    String imdbUrl;

    public String handleCrawl() {
        var listMovieUrl = fetchMoviesUrl();
        
        listMovieUrl.forEach(mov -> System.out.println(mov));
        
        parseData();
        createJsonResponse();

        return "Teste";
    }

    private List<String> fetchMoviesUrl() {
        try {
            var trList = new ArrayList<Element>();
            var urls = new ArrayList<String>();
            var pageData = Jsoup.connect(imdbUrl.concat("/chart/bottom"))
                    .header("Accept-Language", "en-US")
                    .timeout(40000)
                    .get();

            var elements = pageData.getElementsByClass("titleColumn");
            IntStream.rangeClosed(0, 9)
                    .forEach(i -> trList.add(elements.get(i)));

            trList.forEach(tr -> urls.add(ListUtil.first(tr.getElementsByTag("a")).attr("href")));

            return urls;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void parseData() {

    }

    private void createJsonResponse() {

    }

}
