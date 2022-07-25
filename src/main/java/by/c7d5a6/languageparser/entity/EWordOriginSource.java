package by.c7d5a6.languageparser.entity;

import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.entity.possessors.IdLongVerPossessor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = EWordOriginSource.ENTITY_NAME)
@Table(name = EWordOriginSource.TABLE_NAME)
public class EWordOriginSource extends BaseEntity implements Serializable, IdLongVerPossessor {

    protected static final String ENTITY_NAME = "WordOriginSource";
    protected static final String TABLE_NAME = "word_origin_source_tbl";
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "word_origin_id")
    private EWord word;

    @OneToOne
    @JoinColumn(name = "word_source_id")
    private EWord wordSource;

    @NotNull
    @Column(name = "source_initial_version_text")
    private String sourceInitialVersion;

    @Column(name = "comment")
    private String comment;

    public EWord getWord() {
        return word;
    }

    public void setWord(EWord word) {
        this.word = word;
    }

    public EWord getWordSource() {
        return wordSource;
    }

    public void setWordSource(EWord wordSource) {
        this.wordSource = wordSource;
    }

    public String getSourceInitialVersion() {
        return sourceInitialVersion;
    }

    public void setSourceInitialVersion(String sourceInitialVersion) {
        this.sourceInitialVersion = sourceInitialVersion;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
