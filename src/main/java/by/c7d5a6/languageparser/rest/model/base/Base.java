package by.c7d5a6.languageparser.rest.model.base;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Base implements Serializable  {
    @NotNull
    private long id;

    @NotNull
    private long version = 1;

    public Base() {
    }

    public Base(long id, long version) {
        this.id = id;
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}

