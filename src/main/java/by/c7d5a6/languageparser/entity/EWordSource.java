package by.c7d5a6.languageparser.entity;

import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.enums.LanguageConnectionType;
import by.c7d5a6.languageparser.entity.possessors.IdLongVerPossessor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = EWordSource.ENTITY_NAME)
@Table(name = EWordSource.TABLE_NAME)
public class EWordSource extends BaseEntity implements Serializable, IdLongVerPossessor {

    protected static final String ENTITY_NAME = "WordSource";
    protected static final String TABLE_NAME = "word_source_tbl";
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "word_id")
    private EWord word;

    @ManyToOne
    @JoinColumn(name = "word_source_id")
    private EWord wordSource;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "source_type")
    private LanguageConnectionType sourceType;

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

    public LanguageConnectionType getSourceType() {
        return sourceType;
    }

    public void setSourceType(LanguageConnectionType sourceType) {
        this.sourceType = sourceType;
    }
}
