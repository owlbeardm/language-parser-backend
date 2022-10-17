package by.c7d5a6.languageparser.entity;


import by.c7d5a6.languageparser.entity.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity(name = EGrammaticalCategoryValue.ENTITY_NAME)
@Table(name = EGrammaticalCategoryValue.TABLE_NAME)
public class EGrammaticalCategoryValue extends BaseEntity implements Serializable {

    protected static final String ENTITY_NAME = "GrammaticalCategoryValue";
    protected static final String TABLE_NAME = "grammatical_category_value_tbl";
    private static final long serialVersionUID = 1L;
    @NotNull
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "gc_id")
    private EGrammaticalCategory grammaticalCategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EGrammaticalCategory getGrammaticalCategory() {
        return grammaticalCategory;
    }

    public void setGrammaticalCategory(EGrammaticalCategory grammaticalCategory) {
        this.grammaticalCategory = grammaticalCategory;
    }
}
