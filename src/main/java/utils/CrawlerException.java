package utils;

/**
 *
 * @author SRamos
 */
public class CrawlerException extends RuntimeException {
    
    private final String error;

    private final Integer httpStatus;

    public CrawlerException(String error, Integer httpStatus) {
        this.error = error;
        this.httpStatus = httpStatus;
    }

    public String getError() {
        return error;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

}
