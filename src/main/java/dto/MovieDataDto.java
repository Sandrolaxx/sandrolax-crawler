package dto;

import java.math.BigDecimal;
import java.util.List;

public class MovieDataDto {
    
    private String title;

    private String releaseDate;

    private BigDecimal rating;

    private List<DirectorDto> direction;

    private List<CastDto> cast;

    private CommentDto comment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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

    public CommentDto getComment() {
        return comment;
    }

    public void setComment(CommentDto comment) {
        this.comment = comment;
    }
    
}
