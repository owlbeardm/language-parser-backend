package by.c7d5a6.languageparser.entity;


import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.entity.possessors.IdLongVerPossessor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity(name = by.c7d5a6.languageparser.entity.Language.ENTITY_NAME)
@Table(name = by.c7d5a6.languageparser.entity.Language.TABLE_NAME)
public class Language extends BaseEntity implements Serializable, IdLongVerPossessor {

    protected static final String ENTITY_NAME = "Language";
    protected static final String TABLE_NAME = "language_tbl";
    private static final long serialVersionUID = 1L;
    @NotNull
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "native_name")
    private String nativeName;
    @Column(name = "frequency")
    private String frequency;
    @Column(name = "comment")
    private String comment;

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
