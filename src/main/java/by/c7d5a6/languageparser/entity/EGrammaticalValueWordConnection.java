package by.c7d5a6.languageparser.entity;


import by.c7d5a6.languageparser.entity.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;


@Entity(name = EGrammaticalValueWordConnection.ENTITY_NAME)
@Table(name = EGrammaticalValueWordConnection.TABLE_NAME)
public class EGrammaticalValueWordConnection extends BaseEntity implements Serializable {

    protected static final String ENTITY_NAME = "GrammaticalValueWordConnection";
    protected static final String TABLE_NAME = "grammatical_value_word_connection_tbl";
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "value_id")
    private EGrammaticalCategoryValue value;
    @ManyToOne
    @JoinColumn(name = "word_id")
    private EWord word;

    public EGrammaticalCategoryValue getValue() {
        return value;
    }

    public void setValue(EGrammaticalCategoryValue value) {
        this.value = value;
    }

    public EWord getWord() {
        return word;
    }

    public void setWord(EWord word) {
        this.word = word;
    }
}
