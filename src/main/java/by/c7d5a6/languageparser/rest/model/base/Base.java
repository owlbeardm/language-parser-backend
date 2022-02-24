package by.c7d5a6.languageparser.rest.model.base;

import java.io.Serializable;

public class Base implements Serializable {
    private Long id;
    private Long version;

    public Base() {
    }

    public Base(long id, long version) {
        this.id = id;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}

