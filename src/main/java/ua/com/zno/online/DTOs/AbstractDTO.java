package ua.com.zno.online.DTOs;

import ua.com.zno.online.util.Constants;

/**
 * Created by quento on 26.03.17.
 */
public class AbstractDTO {

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
