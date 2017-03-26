package ua.com.zno.online.domain;

import javax.persistence.*;

/**
 * Created by quento on 26.03.17.
 */

@MappedSuperclass
public abstract class AbstractEntity {

    //TODO: add for each entity hashcode, equals, toString

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "deleted", nullable = false, length = 1)
    private boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
