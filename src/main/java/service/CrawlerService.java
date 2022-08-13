package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import dto.DirectorDto;
import dto.MovieDataDto;
import utils.ListUtil;

/**
 *
 * @author SRamos
 */
@ApplicationScoped
public class CrawlerService {

    @ConfigProperty(name = "imdb-base-url")
    String imdbUrl;

    private final Integer firstChild = 0;

    public MovieDataDto handleCrawl() {
        var listMovieUrl = fetchMoviesUrl();

        return fetchMovieData(listMovieUrl.get(0));
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

    private MovieDataDto fetchMovieData(String movieUrl) {
        try {
            var movieData = Jsoup.connect(imdbUrl.concat(movieUrl))
                    .header("Accept-Language", "en-US")
                    .timeout(40000)
                    .get();

            return getMovieDataDto(movieData);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private MovieDataDto getMovieDataDto(Document movieData) {
        var movieDataDto = new MovieDataDto();
        var fullElement = movieData.getElementsByClass("sc-fa02f843-0 fjLeDR").first();

        var direction = fullElement.child(firstChild).getElementsByTag("li").stream()
                .filter(li -> li.child(firstChild).text().equals("Directors"))
                .flatMap(li -> li.getElementsByTag("div").first().getElementsByTag("li").stream())
                .map(Element::text)
                .map(DirectorDto::new)
                .collect(Collectors.toList());

        // movieDataDto.setComment(getFirstCommentOfStart(movieData, 5));
        movieDataDto.setDirection(direction);

        return movieDataDto;
    }

    private String getFirstCommentOfStart(Document movieData, Integer starts) {
        System.err.println(
                movieData.getElementsByClass("sc-66a20916-0 lQXVY").first().getElementsByTag("a").first().attr("href"));

        return "";
    }

}
