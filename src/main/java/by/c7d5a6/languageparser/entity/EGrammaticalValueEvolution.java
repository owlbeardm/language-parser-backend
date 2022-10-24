package by.c7d5a6.languageparser.entity;


import by.c7d5a6.languageparser.entity.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;


@Entity(name = EGrammaticalValueEvolution.ENTITY_NAME)
@Table(name = EGrammaticalValueEvolution.TABLE_NAME)
public class EGrammaticalValueEvolution extends BaseEntity implements Serializable {

    protected static final String ENTITY_NAME = "GrammaticalValueEvolution";
    protected static final String TABLE_NAME = "grammatical_category_value_evolution_tbl";
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "pos_id")
    private EPOS pos;
    @ManyToOne
    @JoinColumn(name = "language_from_id")
    private ELanguage languageFrom;
    @ManyToOne
    @JoinColumn(name = "language_to_id")
    private ELanguage languageTo;
    @ManyToOne
    @JoinColumn(name = "value_from_id")
    private EGrammaticalCategoryValue valueFrom;
    @ManyToOne
    @JoinColumn(name = "value_to_id")
    private EGrammaticalCategoryValue valueTo;

    public EPOS getPos() {
        return pos;
    }

    public void setPos(EPOS pos) {
        this.pos = pos;
    }

    public ELanguage getLanguageFrom() {
        return languageFrom;
    }

    public void setLanguageFrom(ELanguage languageFrom) {
        this.languageFrom = languageFrom;
    }

    public ELanguage getLanguageTo() {
        return languageTo;
    }

    public void setLanguageTo(ELanguage languageTo) {
        this.languageTo = languageTo;
    }

    public EGrammaticalCategoryValue getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(EGrammaticalCategoryValue valueFrom) {
        this.valueFrom = valueFrom;
    }

    public EGrammaticalCategoryValue getValueTo() {
        return valueTo;
    }

    public void setValueTo(EGrammaticalCategoryValue valueTo) {
        this.valueTo = valueTo;
    }
}
