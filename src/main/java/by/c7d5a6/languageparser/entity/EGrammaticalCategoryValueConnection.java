package by.c7d5a6.languageparser.entity;


import by.c7d5a6.languageparser.entity.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity(name = EGrammaticalCategoryValueConnection.ENTITY_NAME)
@Table(name = EGrammaticalCategoryValueConnection.TABLE_NAME)
public class EGrammaticalCategoryValueConnection extends BaseEntity implements Serializable {

    protected static final String ENTITY_NAME = "GrammaticalCategoryValueConnection";
    protected static final String TABLE_NAME = "grammatical_category_value_connection_tbl";
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name = "value_id")
    private EGrammaticalCategoryValue value;
    @ManyToOne
    @JoinColumn(name = "language_id")
    private ELanguage language;

    public EGrammaticalCategoryValue getValue() {
        return value;
    }

    public void setValue(EGrammaticalCategoryValue value) {
        this.value = value;
    }

    public ELanguage getLanguage() {
        return language;
    }

    public void setLanguage(ELanguage language) {
        this.language = language;
    }
}
