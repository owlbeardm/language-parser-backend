package by.c7d5a6.languageparser.entity;

import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.enums.TranslationType;
import by.c7d5a6.languageparser.entity.possessors.IdLongVerPossessor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = ETranslation.ENTITY_NAME)
@Table(name = ETranslation.TABLE_NAME)
public class ETranslation extends BaseEntity implements Serializable, IdLongVerPossessor {

    protected static final String ENTITY_NAME = "Translation";
    protected static final String TABLE_NAME = "translation_tbl";
    private static final long serialVersionUID = 1L;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "word_from_id")
    private EWord wordFrom;

    @ManyToOne
    @JoinColumn(name = "word_to_id")
    private EWord wordTo;

    @ManyToOne
    @JoinColumn(name = "phrase_to_id")
    private EPhrase phraseTo;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TranslationType type;

    public EWord getWordFrom() {
        return wordFrom;
    }

    public void setWordFrom(EWord wordFrom) {
        this.wordFrom = wordFrom;
    }

    public EWord getWordTo() {
        return wordTo;
    }

    public void setWordTo(EWord wordTo) {
        this.wordTo = wordTo;
    }

    public EPhrase getPhraseTo() {
        return phraseTo;
    }

    public void setPhraseTo(EPhrase phraseTo) {
        this.phraseTo = phraseTo;
    }

    public TranslationType getType() {
        return type;
    }

    public void setType(TranslationType type) {
        this.type = type;
    }
}
