package by.c7d5a6.languageparser.entity;


import by.c7d5a6.languageparser.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity(name = EGrammaticalCategory.ENTITY_NAME)
@Table(name = EGrammaticalCategory.TABLE_NAME)
public class EGrammaticalCategory extends BaseEntity implements Serializable {

    protected static final String ENTITY_NAME = "GrammaticalCategory";
    protected static final String TABLE_NAME = "grammatical_category_tbl";
    private static final long serialVersionUID = 1L;
    @NotNull
    @Column(name = "name")
    private String name;
    @Column(name = "comment")
    private String comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
