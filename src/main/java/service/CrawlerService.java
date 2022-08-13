package service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import dto.CastDto;
import dto.CommentDto;
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

    public List<MovieDataDto> handleCrawl() {
        var listMovieUrl = fetchMoviesUrl();
        var listMovieDto = listMovieUrl.stream()
            .map(url -> fetchMovieData(url))
            .sorted(Comparator.comparing(m -> m.getRating()))
            .collect(Collectors.toList());

        return listMovieDto;
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
            IntStream.rangeClosed(0, 5)
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
                .filter(li -> li.child(firstChild).text().equals("Directors") || li.child(firstChild).text().equals("Director"))
                .flatMap(li -> li.getElementsByTag("div").first().getElementsByTag("li").stream())
                .map(Element::text)
                .map(DirectorDto::new)
                .collect(Collectors.toList());

        var cast = movieData.getElementsByClass("sc-36c36dd0-1 QSQgP").stream()
                .map(Element::text)
                .map(CastDto::new)
                .collect(Collectors.toList());

        movieDataDto.setDirection(direction);
        movieDataDto.setCast(cast);
        movieDataDto.setReleaseDate(movieData.getElementsByClass("sc-8c396aa2-2 itZqyK").first().text());
        movieDataDto.setRating(new BigDecimal(movieData.getElementsByClass("sc-7ab21ed2-1 jGRxWM").first().text()));
        movieDataDto.setTitle(movieData.getElementsByClass("sc-80d4314-1 fbQftq").first().getElementsByTag("h1").first().text());
        movieDataDto.setComment(getFirstCommentOfStart(movieData, 5));

        return movieDataDto;
    }

    private CommentDto getFirstCommentOfStart(Document movieData, Integer starts) {
        var commentDto = new CommentDto();
        var starsFilter = "?sort=helpfulnessScore&dir=desc&ratingFilter=".concat(String.valueOf(5));
        var reviewsUrl = movieData.getElementsByClass("sc-66a20916-0 lQXVY").first().getElementsByTag("a").first()
                .attr("href");
        reviewsUrl = reviewsUrl.replaceAll("\\?.*", starsFilter);

        try {
            var commentsData = Jsoup.connect(imdbUrl.concat(reviewsUrl))
                    .header("Accept-Language", "en-US")
                    .timeout(40000)
                    .get();

            var title = commentsData.getElementsByClass("title").first().text();
            var review = commentsData.getElementsByClass("text show-more__control").first().text();

            commentDto.setTitle(title);
            commentDto.setReview(review);

            return commentDto;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

}
