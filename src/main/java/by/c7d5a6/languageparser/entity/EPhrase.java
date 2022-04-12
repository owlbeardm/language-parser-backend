package by.c7d5a6.languageparser.entity;

import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.entity.possessors.IdLongVerPossessor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = EPhrase.ENTITY_NAME)
@Table(name = EPhrase.TABLE_NAME)
public class EPhrase extends BaseEntity implements Serializable, IdLongVerPossessor {

    protected static final String ENTITY_NAME = "Phrase";
    protected static final String TABLE_NAME = "phrase_tbl";
    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "phrase")
    private String phrase;

    @ManyToOne
    @JoinColumn(name = "lang_id")
    private ELanguage language;

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public ELanguage getLanguage() {
        return language;
    }

    public void setLanguage(ELanguage language) {
        this.language = language;
    }
}
