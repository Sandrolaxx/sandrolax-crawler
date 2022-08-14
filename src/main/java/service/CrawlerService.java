package service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.enterprise.context.ApplicationScoped;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import dto.CastDto;
import dto.CommentDto;
import dto.DirectorDto;
import dto.MovieDataDto;
import utils.CrawlerException;
import utils.ListUtil;

/**
 *
 * @author SRamos
 */
@ApplicationScoped
public class CrawlerService extends AbstractCrawler {

    public List<MovieDataDto> handleCrawl(Integer reviewStar) {
        var listMovieUrl = fetchMoviesUrl();
        var listMovieDto = listMovieUrl.stream()
                .map(url -> fetchMovieData(url, reviewStar))
                .sorted((m1, m2) -> m2.getRating().compareTo(m1.getRating()))
                .collect(Collectors.toList());

        return listMovieDto;
    }

    private List<String> fetchMoviesUrl() {
        try {
            var trList = new ArrayList<Element>();
            var pageData = Jsoup.connect(getWorstMoviesUrl())
                    .header("Accept-Language", "en-US")
                    .timeout(40000)
                    .get();

            var elements = pageData.getElementsByClass(CLASS_TITLE_COLUMN);
            IntStream.rangeClosed(0, 9)
                    .forEach(i -> trList.add(elements.get(i)));

            return trList.stream()
                    .map(tr -> ListUtil.first(tr.getElementsByTag(TAG_A)).attr(ATTRIBUTE_HREF))
                    .collect(Collectors.toList());
        } catch (IOException | IllegalArgumentException e) {
            throw new CrawlerException("Erro ao buscar url's dos filmes.", 502);
        }
    }

    private MovieDataDto fetchMovieData(String movieUrl, Integer reviewStar) {
        try {
            var movieData = Jsoup.connect(getMovieUrl(movieUrl))
                    .header("Accept-Language", "en-US")
                    .timeout(40000)
                    .get();

            return getMovieDataDto(movieData, reviewStar);
        } catch (IOException | IllegalArgumentException e) {
            throw new CrawlerException("Erro ao buscar informação do filme com url:".concat(movieUrl), 502);
        }
    }

    private MovieDataDto getMovieDataDto(Document movieData, Integer reviewStar) {
        var movieDataDto = new MovieDataDto();
        var fullElement = movieData.getElementsByClass(CLASS_DIRECTION_DATA).first();
        var releaseDate = movieData.getElementsByClass(CLASS_RELEASE_DATE).first().text();
        var rating = new BigDecimal(movieData.getElementsByClass(CLASS_RATING).first().text());
        var title = movieData.getElementsByClass(CLASS_MOVIE_TITLE).first().getElementsByTag(TAG_H1).first().text();

        var direction = fullElement.child(firstChild).getElementsByTag(TAG_LI).stream()
                .filter(li -> directorsValidation(li))
                .flatMap(li -> li.getElementsByTag(TAG_DIV).first().getElementsByTag(TAG_LI).stream())
                .map(Element::text)
                .map(DirectorDto::new)
                .collect(Collectors.toList());

        var cast = movieData.getElementsByClass(CLASS_CAST_DATA).stream()
                .map(Element::text)
                .map(CastDto::new)
                .collect(Collectors.toList());

        movieDataDto.setDirection(direction);
        movieDataDto.setCast(cast);
        movieDataDto.setReleaseDate(releaseDate);
        movieDataDto.setRating(rating);
        movieDataDto.setTitle(title);
        movieDataDto.setComment(getFirstCommentOfStar(movieData, reviewStar));

        return movieDataDto;
    }

    private CommentDto getFirstCommentOfStar(Document movieData, Integer reviewStar) {
        var commentDto = new CommentDto();
        var starsFilter = getFilterByStarUrl(reviewStar);
        var reviewsUrl = movieData.getElementsByClass(CLASS_COMMENT_DATA)
                .first().getElementsByTag(TAG_A)
                .first().attr(ATTRIBUTE_HREF)
                .replaceAll("\\?.*", starsFilter);

        try {
            var commentsData = Jsoup.connect(imdbUrl.concat(reviewsUrl))
                    .header("Accept-Language", "en-US")
                    .timeout(40000)
                    .get();

            var title = commentsData.getElementsByClass(CLASS_COMMENT_TITLE).first().text();
            var review = commentsData.getElementsByClass(CLASS_COMMENT_REVIEW).first().text();

            commentDto.setTitle(title);
            commentDto.setReview(review);

            return commentDto;
        } catch (IOException | IllegalArgumentException e) {
            var movieName = movieData.getElementsByClass(CLASS_MOVIE_TITLE).first().getElementsByTag(TAG_H1).first().text();

            throw new CrawlerException("Erro ao buscar informação dos comentários do filme: ".concat(movieName), 502);
        }

    }

}
