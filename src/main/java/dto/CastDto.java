package dto;

/**
 *
 * @author SRamos
 */
public class CastDto {
    
    private String actor;

    public CastDto() {
    }

    public CastDto(String actor) {
        this.actor = actor;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
    
}
