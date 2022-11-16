package by.c7d5a6.languageparser.entity;


import by.c7d5a6.languageparser.entity.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;


@Entity(name = EDeclensionConnection.ENTITY_NAME)
@Table(name = EDeclensionConnection.TABLE_NAME)
public class EDeclensionConnection extends BaseEntity implements Serializable {

    protected static final String ENTITY_NAME = "DeclensionConnection";
    protected static final String TABLE_NAME = "declension_classes_connection_tbl";
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name = "language_id")
    private ELanguage language;
    @ManyToOne
    @JoinColumn(name = "pos_id")
    private EPOS pos;
    @ManyToOne
    @JoinColumn(name = "grammar_class_id")
    private EGrammaticalCategory grammaticalCategory;

    public ELanguage getLanguage() {
        return language;
    }

    public void setLanguage(ELanguage language) {
        this.language = language;
    }

    public EPOS getPos() {
        return pos;
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
