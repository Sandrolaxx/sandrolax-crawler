package dto;

/**
 *
 * @author SRamos
 */
public class CrawlerExceptionResponseDto {
    
    private String error;

    private String date;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
