package by.c7d5a6.languageparser.entity;

import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.enums.WordOriginType;
import by.c7d5a6.languageparser.entity.possessors.IdLongVerPossessor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = EWord.ENTITY_NAME)
@Table(name = EWord.TABLE_NAME)
public class EWord extends BaseEntity implements Serializable, IdLongVerPossessor {

    protected static final String ENTITY_NAME = "Word";
    protected static final String TABLE_NAME = "word_tbl";
    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "word")
    private String word;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "lang_id")
    private ELanguage language;

    @Column(name = "forgotten")
    private boolean forgotten;

    @ManyToOne
    @JoinColumn(name = "pos_id")
    private EPOS partOfSpeech;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "source_type")
    private WordOriginType sourceType;


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ELanguage getLanguage() {
        return language;
    }

    public void setLanguage(ELanguage language) {
        this.language = language;
    }

    public boolean isForgotten() {
        return forgotten;
    }

    public void setForgotten(boolean forgotten) {
        this.forgotten = forgotten;
    }

    public EPOS getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(EPOS partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public WordOriginType getSourceType() {
        return sourceType;
    }

    public void setSourceType(WordOriginType sourceType) {
        this.sourceType = sourceType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
