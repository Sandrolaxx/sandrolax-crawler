package dto;

import java.math.BigDecimal;
import java.util.List;

public class MovieDataDto {
    
    private String title;

    private BigDecimal rating;

    private List<DirectorDto> direction;

    private List<CastDto> cast;

    private String comment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }
    
    public List<DirectorDto> getDirection() {
        return direction;
    }

    public void setDirection(List<DirectorDto> direction) {
        this.direction = direction;
    }

    public List<CastDto> getCast() {
        return cast;
    }

    public void setCast(List<CastDto> cast) {
        this.cast = cast;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
}
