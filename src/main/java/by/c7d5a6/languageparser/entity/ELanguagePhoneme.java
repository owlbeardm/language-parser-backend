package by.c7d5a6.languageparser.entity;

import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.entity.possessors.IdLongVerPossessor;
import by.c7d5a6.languageparser.rest.model.Language;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = ELanguagePhoneme.ENTITY_NAME)
@Table(name = ELanguagePhoneme.TABLE_NAME)
public class ELanguagePhoneme extends BaseEntity implements Serializable, IdLongVerPossessor {

    protected static final String ENTITY_NAME = "LanguagePhoneme";
    protected static final String TABLE_NAME = "language_phoneme_tbl";
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private ELanguage language;

    @Column(name = "phoneme")
    private String phoneme;

    public ELanguage getLanguage() {
        return language;
    }

    public String getPhoneme() {
        return phoneme;
    }

    public void setLanguage(ELanguage language) {
        this.language = language;
    }

    public void setPhoneme(String phoneme) {
        this.phoneme = phoneme;
    }
}
