package ua.com.zno.online.dto;

/**
 * Created by quento on 26.03.17.
 */
public abstract class AbstractDTO {

    public AbstractDTO(){}

    public AbstractDTO(Long id) {
        this.id = id;
    }

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
