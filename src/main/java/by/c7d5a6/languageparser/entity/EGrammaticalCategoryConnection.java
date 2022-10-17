package by.c7d5a6.languageparser.entity;


import by.c7d5a6.languageparser.entity.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity(name = EGrammaticalCategoryConnection.ENTITY_NAME)
@Table(name = EGrammaticalCategoryConnection.TABLE_NAME)
public class EGrammaticalCategoryConnection extends BaseEntity implements Serializable {

    protected static final String ENTITY_NAME = "GrammaticalCategoryConnection";
    protected static final String TABLE_NAME = "grammatical_category_connection_tbl";
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name = "language_id")
    private ELanguage language;
    @ManyToOne
    @JoinColumn(name = "gc_id")
    private EGrammaticalCategory grammaticalCategory;
    @ManyToOne
    @JoinColumn(name = "pos_id")
    private EPOS pos;

    public ELanguage getLanguage() {
        return language;
    }

    public EPOS getPos() {
        return pos;
    }

    public void setLanguage(ELanguage language) {
        this.language = language;
    }

    public void setPos(EPOS pos) {
        this.pos = pos;
    }

    public EGrammaticalCategory getGrammaticalCategory() {
        return grammaticalCategory;
    }

    public void setGrammaticalCategory(EGrammaticalCategory grammaticalCategory) {
        this.grammaticalCategory = grammaticalCategory;
    }
}
