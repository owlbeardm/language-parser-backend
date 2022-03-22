package by.c7d5a6.languageparser.rest.model.base;

import java.io.Serializable;
import java.util.Objects;

public class Base implements Serializable {
    protected Long id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Base base = (Base) o;
        return Objects.equals(id, base.id) && Objects.equals(version, base.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}

