package by.c7d5a6.languageparser.entity;

import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.entity.enums.WordOriginType;
import by.c7d5a6.languageparser.entity.possessors.IdLongVerPossessor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = EWordOrigin.ENTITY_NAME)
@Table(name = EWordOrigin.TABLE_NAME)
public class EWordOrigin extends BaseEntity implements Serializable, IdLongVerPossessor {

    protected static final String ENTITY_NAME = "WordOrigin";
    protected static final String TABLE_NAME = "word_origin_tbl";
    private static final long serialVersionUID = 1L;

    @OneToOne
    @JoinColumn(name = "word_id")
    private EWord word;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "source_type")
    private WordOriginType sourceType;

    @NotNull
    @Column(name = "word_initial_version")
    private String wordInitialVersion;

    @Column(name = "comment")
    private String comment;

    public EWord getWord() {
        return word;
    }

    public void setWord(EWord word) {
        this.word = word;
    }

    public WordOriginType getSourceType() {
        return sourceType;
    }

    public void setSourceType(WordOriginType sourceType) {
        this.sourceType = sourceType;
    }

    public String getWordInitialVersion() {
        return wordInitialVersion;
    }

    public void setWordInitialVersion(String wordInitialVersion) {
        this.wordInitialVersion = wordInitialVersion;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
