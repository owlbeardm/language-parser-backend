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
    private EWordOrigin wordOrigin;

    @OneToOne
    @JoinColumn(name = "word_source_id")
    private EWord wordSource;

    @NotNull
    @Column(name = "source_initial_version")
    private String sourceInitialVersion;

    @Column(name = "comment")
    private String comment;

    public EWordOrigin getWordOrigin() {
        return wordOrigin;
    }

    public void setWordOrigin(EWordOrigin wordOrigin) {
        this.wordOrigin = wordOrigin;
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
