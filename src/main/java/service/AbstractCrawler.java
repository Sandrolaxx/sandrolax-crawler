package service;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jsoup.nodes.Element;

/**
 *
 * @author SRamos
 */
public abstract class AbstractCrawler {

    @ConfigProperty(name = "imdb-base-url")
    String imdbUrl;

    protected final Integer firstChild = 0;
    
    private final Integer defaultStarValue = 5;

    private final String PATH_WORST_MOVIES = "/chart/bottom";

    private final String PATH_FILTER_BY_STAR = "?sort=helpfulnessScore&dir=desc&ratingFilter=";

    protected final String CLASS_TITLE_COLUMN = "titleColumn";

    protected final String CLASS_DIRECTION_DATA = "sc-fa02f843-0 fjLeDR";

    protected final String CLASS_CAST_DATA = "sc-36c36dd0-1 QSQgP";

    protected final String CLASS_RELEASE_DATE = "sc-8c396aa2-2 itZqyK";

    protected final String CLASS_RATING = "sc-7ab21ed2-1 jGRxWM";

    protected final String CLASS_MOVIE_TITLE = "sc-80d4314-1 fbQftq";

    protected final String CLASS_COMMENT_DATA = "sc-66a20916-0 lQXVY";

    protected final String CLASS_COMMENT_TITLE = "title";

    protected final String CLASS_COMMENT_REVIEW = "text show-more__control";

    protected final String TAG_A = "a";

    protected final String TAG_LI = "li";

    protected final String TAG_H1 = "h1";

    protected final String TAG_DIV = "div";

    protected final String ATTRIBUTE_HREF = "href";

    protected String getWorstMoviesUrl() {
        return imdbUrl.concat(PATH_WORST_MOVIES);
    }

    protected String getMovieUrl(String moviePath) {
        return imdbUrl.concat(moviePath);
    }

    protected String getFilterByStarUrl(Integer star) {
        return PATH_FILTER_BY_STAR.concat(String.valueOf(star != null ? star : defaultStarValue));
    }

    protected boolean directorsValidation(Element li) {
        return li.child(firstChild).text().equals("Directors")
                || li.child(firstChild).text().equals("Director");
    }

}
